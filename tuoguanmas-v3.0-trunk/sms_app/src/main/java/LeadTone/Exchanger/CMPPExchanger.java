package LeadTone.Exchanger;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.CMPPDatabase.CMPPDeliverDatabase;
import LeadTone.Engine;
import LeadTone.Gateway.*;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Port.CMPPXMLExchanger;
import LeadTone.Utility;


/**
 * �������ݿⱾ����ǻ���CMPPЭ����Ƶļܹ�����������ٽ����ر����Ϣ��ʽת����
 * ֻ�Ǹ���Э����б�Ҫ��ֵ����
 */
public class CMPPExchanger extends DataExchanger
{
    /**
     * ������Ϣ�������ض���
     */
    CMPPGatewayEngine m_gateway;
    /**
     * ���������Ϣ��ӳ��
     */
    CMPPPacket cmpp_input;
    /**
     * ���������Ϣ��ӳ��
     */
    CMPPPacket cmpp_output;

    /**
     * ���췽����ʼ�������
     * @param gateway
     * @param database
     * @param exchanger
     */
    public CMPPExchanger(CMPPGatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger exchanger)
    {
        super(database, exchanger);
        m_gateway = null;
        cmpp_input = null;
        cmpp_output = null;
        m_gateway = gateway;
    }

    /**
     * ����Ϣ���ݽ��б���ת�� UCS2���뵽GB2312����
     * @param deliver ������Ϣ���ݽ�����Ϣ�������Ϣ����
     */
    public void reencode_msg_content(CMPPDeliver deliver)
    {
        String content = Utility.ucs2_to_gb2312(deliver.msg_content);
        if(content == null)
            return;
        try
        {
            deliver.msg_content = content.getBytes("GB2312");
            deliver.msg_fmt = 15;
            deliver.msg_length = deliver.msg_content.length;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000004L);
            Log.log(e);
            Log.log("CMPPExchanger.reencode_msg_content : unexpected exit !", 0x2000000000000004L);
        }
    }


    /**
     * ��Ϣת�����̵Ĺ���ʵ�֣������ݿ��ȡ�����Ϣ��ת����ݽ����ش��������ػ�ȡ�������Ϣ��ת����������ݿ�
     */
    public void run()
    {
        try
        {
            Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread startup !", 4L);
            m_nStatus = 1;
            for(; isRunning(); nap())
            {
                if(m_gateway.isStopped())
                {
                    Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : gateway is stopped !", 0x2000000000000004L);
                    break;
                }
                //�����ݿ��ȡ�����Ϣ��ת����ݽ����ش���
                if(cmpp_output == null)
                    handleOutput();
                toGateway();
                if(m_database.isStopped())
                {
                    Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : database is stopped !", 0x2000000000000004L);
                    break;
                }
                //�����ػ�ȡ�������Ϣ��ת����������ݿ�
                if(cmpp_input == null)
                    handleInput();
                toDatabase();
            }

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : unexpected exit !", 0x2000000000000004L);
        }
        m_nStatus = 3;
        Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread stopped !", 4L);
    }

    /**
     * �������ݿⱾ����ǻ���CMPPЭ����Ƶļܹ�����������ٽ����ر����Ϣ��ʽת����
     * ֻ�Ǹ���Э����б�Ҫ��ֵ����
     * @param submit ���ݿ���ȡ���ķ�����Ϣ����
     * @param service_code ������룬�ο�CMPPЭ��2.1
     * @return ����ת�������Ϣ����
     */
    public CMPPPacket wrapSubmit(CMPPSubmit submit, String service_code)
    {
        try
        {
            //����Э��������Ϣ��ԴΪ��ҵ����
            if(m_gateway.m_sp != null && m_gateway.m_sp.enterprise_code != null)
                submit.msg_src = m_gateway.m_sp.enterprise_code;

            //���������·��ֻ����룬�޳��ֻ���ǰ��86ǰ׺
            int length = submit.dest_terminal_id != null ? submit.dest_terminal_id.length : 0;
            for(int i = 0; i < length; i++)
                if(submit.dest_terminal_id[i].startsWith("86"))
                    submit.dest_terminal_id[i] = submit.dest_terminal_id[i].substring(2);
            //tp_udhi��ֵ�Ĺ�����ο�GSM��SMPPЭ�麬�壬������Ի�Ϊ���������������⻯������ο���Ӧ����
            if(m_gateway != null && m_gateway.m_gc != null && m_gateway.m_gc.m_nType == 0x20900 && submit.tp_udhi != 0)
                submit.tp_udhi = 1;
            submit.wrap(service_code);
            return submit;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapSubmit : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    /**
     * �������ݿⱾ����ǻ���CMPPЭ����Ƶļܹ�����������ٽ����ر����Ϣ��ʽת����
     * ֻ�Ǹ���Э����б�Ҫ��ֵ����
     * @param query ���ݿ���ȡ������Ϣ����
     * @return ����ת�������Ϣ����
     */
    public CMPPPacket wrapQuery(CMPPQuery query)
    {
        try
        {
            query.wrap();
            return query;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapQuery : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    /**
     * �������ݿⱾ����ǻ���CMPPЭ����Ƶļܹ�����������ٽ����ر����Ϣ��ʽת����
     * ֻ�Ǹ���Э����б�Ҫ��ֵ����
     * @param response �����ݿ��ȡ����Ϣ����
     * @return ����ת�������Ϣ����
     */
    public CMPPPacket wrapDeliverResponse(CMPPDeliverResponse response)
    {
        try
        {
            response.wrap();
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapDeliverResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    /**
     * �������ݿⱾ����ǻ���CMPPЭ����Ƶļܹ�����������ٽ����ر����Ϣ��ʽת����
     * ֻ�Ǹ���Э����б�Ҫ��ֵ����
     * @param packet �����ػ�ȡ����Ϣ����
     * @return ����ת�������Ϣ����
     */
    public CMPPSubmitResponse unwrapSubmitResponse(CMPPPacket packet)
    {
        try
        {
            CMPPSubmitResponse response = new CMPPSubmitResponse(packet);
            response.unwrap();
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapSubmitResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    /**
     * �������ݿⱾ����ǻ���CMPPЭ����Ƶļܹ�����������ٽ����ر����Ϣ��ʽת����
     * ֻ�Ǹ���Э����б�Ҫ��ֵ����
     * @param packet �����ػ�ȡ����Ϣ����
     * @return ����ת�������Ϣ����
     */
    public CMPPQueryResponse unwrapQueryResponse(CMPPPacket packet)
    {
        try
        {
            CMPPQueryResponse response = new CMPPQueryResponse(packet);
            response.unwrap();
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapQueryResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    /**
     * �������ݿⱾ����ǻ���CMPPЭ����Ƶļܹ�����������ٽ����ر����Ϣ��ʽת����
     * ֻ�Ǹ���Э����б�Ҫ��ֵ����
     * @param packet �����ػ�ȡ����Ϣ����
     * @return ����ת�������Ϣ����
     */
    public CMPPDeliver unwrapDeliver(CMPPPacket packet)
    {
        try
        {
            CMPPDeliver deliver = new CMPPDeliver(packet);
            deliver.unwrap();

            
            //������δ���ʵ�����յ�Deliver��Ϣ��������֯DeliverResponse��Ϣ�����뷢�Ͷ��У�
            //�����������ݿ���ѭCMPPDeliver����ih_process�ֶ�Ϊinsert_cmpp_deliver״̬�ļ�¼,
            //��deliver���п��عر�ʱ�򣬲������ݿ���ѭ�ظ�response,����exchangerֱ�ӻظ�response
            if (!CMPPDeliverDatabase.m_output_switch){
            CMPPDeliverResponse response = new CMPPDeliverResponse(deliver.sequence_id);
            response.gateway_name = deliver.gateway_name;
            response.sequence_id = deliver.sequence_id;
            response.result = 0;
            response.session_id = deliver.session_id;
            //��֤deliver response �� deliver��Ϣ�е�msg_idһ��
            response.msg_id = deliver.msg_id;
            response.wrap();
            for(; !m_gateway.send(response); nap());
            }

            //ԭ�д���ʱ���������´��룬Ŀ����ת���ַ�������sp5��Ҫ��MO�����źϲ�Ϊһ�������ԣ��������´��룬����ԭ�ַ�����ʽ����������ȡ��050003 XX MM NN��ʶ�����ںϲ�������֮�� �����20120922.
            /*if(deliver.msg_fmt == 8)
                reencode_msg_content(deliver);*/
            
            return deliver;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapDeliver : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    /**
     * �������ݿ��ȡ��ת�������Ϣ���͵����ش���
     */
    public void toGateway()
    {
        if(cmpp_output != null && m_gateway.send(cmpp_output))
            cmpp_output = null;
    }

    /**
     * �����ػ�ȡ��ת�������Ϣ�־û������ݿ���
     */
    public void toDatabase()
    {
        if(cmpp_input != null && m_database.m_input.push(cmpp_input))
        {
            if(cmpp_input.command_id == 0x00000005)
                m_exchanger.m_output.push(cmpp_input);
            cmpp_input = null;
        }
        super.toDatabase();
    }

    /**
     * �����ݿ�����Ķ�������ȡ��Ϣ���и�ʽת��
     */
    public void handleOutput()
    {
        CMPPPacket packet = (CMPPPacket)m_database.m_output.pop(m_gateway.m_strName);
        if(packet == null)
            return;
        //���ط������ͳ��
        m_gateway.statistic(packet);
        if(packet.command_id == 0x80000005)
            cmpp_output = wrapDeliverResponse((CMPPDeliverResponse)packet);
        else
        if(packet.command_id == 0x00000004)
            cmpp_output = wrapSubmit((CMPPSubmit)packet, m_gateway.m_sp.service_code);
        else
        if(packet.command_id == 0x00000006)
            cmpp_output = wrapQuery((CMPPQuery)packet);
        packet = null;
    }

    /**
     * �����ز����Ķ�������ȡ��Ϣ���и�ʽת��
     */
    public void handleInput()
    {
        CMPPPacket packet = (CMPPPacket)m_gateway.receive();
        if(packet == null)
            return;
        if(packet.command_id == 0x00000005)
            cmpp_input = unwrapDeliver(packet);
        else
        if(packet.command_id == 0x80000006)
            cmpp_input = unwrapQueryResponse(packet);
        else
        if(packet.command_id == 0x80000004)
            cmpp_input = unwrapSubmitResponse(packet);
        //���ط������ͳ��
        m_gateway.statistic(cmpp_input);
        packet = null;
    }


}
