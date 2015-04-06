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
 * 由于数据库本身就是基于CMPP协议设计的架构，因此无需再进行特别的消息格式转换，
 * 只是根据协议进行必要的值设置
 */
public class CMPPExchanger extends DataExchanger
{
    /**
     * 处理消息包的网关对象
     */
    CMPPGatewayEngine m_gateway;
    /**
     * 待输入的消息包映射
     */
    CMPPPacket cmpp_input;
    /**
     * 待输出的消息包映射
     */
    CMPPPacket cmpp_output;

    /**
     * 构造方法初始化类变量
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
     * 对消息内容进行编码转换 UCS2编码到GB2312编码
     * @param deliver 待对消息内容进行消息编码的消息对象
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
     * 消息转换进程的功能实现，从数据库获取输出消息，转换后递交网关处理，从网关获取输入的消息，转换后放入数据库
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
                //从数据库获取输出消息，转换后递交网关处理
                if(cmpp_output == null)
                    handleOutput();
                toGateway();
                if(m_database.isStopped())
                {
                    Log.log("CMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : database is stopped !", 0x2000000000000004L);
                    break;
                }
                //从网关获取输入的消息，转换后放入数据库
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
     * 由于数据库本身就是基于CMPP协议设计的架构，因此无需再进行特别的消息格式转换，
     * 只是根据协议进行必要的值设置
     * @param submit 数据库中取出的发送消息对象
     * @param service_code 服务代码，参考CMPP协议2.1
     * @return 返回转换后的消息对象
     */
    public CMPPPacket wrapSubmit(CMPPSubmit submit, String service_code)
    {
        try
        {
            //根据协议设置消息来源为企业代码
            if(m_gateway.m_sp != null && m_gateway.m_sp.enterprise_code != null)
                submit.msg_src = m_gateway.m_sp.enterprise_code;

            //遍历所有下发手机号码，剔除手机号前的86前缀
            int length = submit.dest_terminal_id != null ? submit.dest_terminal_id.length : 0;
            for(int i = 0; i < length; i++)
                if(submit.dest_terminal_id[i].startsWith("86"))
                    submit.dest_terminal_id[i] = submit.dest_terminal_id[i].substring(2);
            //tp_udhi的值的规则请参考GSM或SMPP协议含义，这里针对华为的网关类型有特殊化处理，请参考相应资料
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
     * 由于数据库本身就是基于CMPP协议设计的架构，因此无需再进行特别的消息格式转换，
     * 只是根据协议进行必要的值设置
     * @param query 数据库中取出的消息对象
     * @return 返回转换后的消息对象
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
     * 由于数据库本身就是基于CMPP协议设计的架构，因此无需再进行特别的消息格式转换，
     * 只是根据协议进行必要的值设置
     * @param response 从数据库获取的消息对象
     * @return 返回转换后的消息对象
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
     * 由于数据库本身就是基于CMPP协议设计的架构，因此无需再进行特别的消息格式转换，
     * 只是根据协议进行必要的值设置
     * @param packet 从网关获取的消息对象
     * @return 返回转换后的消息对象
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
     * 由于数据库本身就是基于CMPP协议设计的架构，因此无需再进行特别的消息格式转换，
     * 只是根据协议进行必要的值设置
     * @param packet 从网关获取的消息对象
     * @return 返回转换后的消息对象
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
     * 由于数据库本身就是基于CMPP协议设计的架构，因此无需再进行特别的消息格式转换，
     * 只是根据协议进行必要的值设置
     * @param packet 从网关获取的消息对象
     * @return 返回转换后的消息对象
     */
    public CMPPDeliver unwrapDeliver(CMPPPacket packet)
    {
        try
        {
            CMPPDeliver deliver = new CMPPDeliver(packet);
            deliver.unwrap();

            
            //以下这段代码实现在收到Deliver消息后立即组织DeliverResponse消息并放入发送队列，
            //并不交给数据库伦循CMPPDeliver表中ih_process字段为insert_cmpp_deliver状态的记录,
            //当deliver下行开关关闭时候，不从数据库轮循回复response,而从exchanger直接回复response
            if (!CMPPDeliverDatabase.m_output_switch){
            CMPPDeliverResponse response = new CMPPDeliverResponse(deliver.sequence_id);
            response.gateway_name = deliver.gateway_name;
            response.sequence_id = deliver.sequence_id;
            response.result = 0;
            response.session_id = deliver.session_id;
            //保证deliver response 与 deliver消息中的msg_id一致
            response.msg_id = deliver.msg_id;
            response.wrap();
            for(; !m_gateway.send(response); nap());
            }

            //原有代码时调用了以下代码，目的是转换字符集；但sp5需要将MO长短信合并为一条，所以，屏蔽以下代码，保留原字符集格式，这样可以取出050003 XX MM NN标识，便于合并长短信之用 。于骞20120922.
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
     * 将从数据库读取并转换后的消息递送到网关处理
     */
    public void toGateway()
    {
        if(cmpp_output != null && m_gateway.send(cmpp_output))
            cmpp_output = null;
    }

    /**
     * 将网关获取并转换后的消息持久化到数据库中
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
     * 从数据库操作的队列中提取消息进行格式转换
     */
    public void handleOutput()
    {
        CMPPPacket packet = (CMPPPacket)m_database.m_output.pop(m_gateway.m_strName);
        if(packet == null)
            return;
        //网关发送情况统计
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
     * 从网关操作的队列中提取消息进行格式转换
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
        //网关发送情况统计
        m_gateway.statistic(cmpp_input);
        packet = null;
    }


}
