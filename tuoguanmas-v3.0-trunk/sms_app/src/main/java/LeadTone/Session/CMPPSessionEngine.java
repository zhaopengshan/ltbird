package LeadTone.Session;

import LeadTone.BufferException;
import LeadTone.Gateway.CMPPGatewayEngine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.Packet;
import java.net.Socket;


/**
 * CMPP�й��ƶ��Ķ���ϢЭ��ʵ��
 */
public class CMPPSessionEngine extends SessionEngine
{
    /**
     * ��Ϣ���е�ӳ��
     */
    CMPPPacketQueue m_queue;
    /**
     * ������Ϣ������
     */
    CMPPPacket cmpp_input;
    /**
     * �����Ϣ������
     */
    CMPPPacket cmpp_output;

    /**
     * ���췽����ʼ����������ο����෽��
     * @param sc
     * @param socket
     * @param gateway
     */
    public CMPPSessionEngine(SessionConfig sc, Socket socket, CMPPGatewayEngine gateway)
    {
        super(sc, socket, gateway);
        m_queue = new CMPPPacketQueue();
        cmpp_input = null;
        cmpp_output = null;
    }

    /**
     * ���췽����ʼ����������ο����෽��
     * @param sc
     * @param gateway
     */
    public CMPPSessionEngine(SessionConfig sc, CMPPGatewayEngine gateway)
    {
        super(sc, gateway);
        m_queue = new CMPPPacketQueue();
        cmpp_input = null;
        cmpp_output = null;
    }

    /**
     * �̳��Ը��෽�������ʹ�õ���Դ
     */
    public void empty()
    {
        m_queue.empty();
        m_queue = null;
        super.empty();
    }

    /**
     * �̳��Ը��෽������ɷ�����Ϣ�����ع����̵߳���������߳�InputEngine�Ĺ���
     * @return �����Ƿ����ɹ�
     * @throws BufferException
     */
    public boolean put()
        throws BufferException
    {
        if(cmpp_output == null)
        {
            //�����ض����л�ȡ��������Ϣ��
            cmpp_output = (CMPPPacket)m_gateway.get(m_sc.m_nType, m_nID);
            if(cmpp_output == null)
                return false;
            if(CMPPCommandID.isRequest(cmpp_output.command_id))
            {
                cmpp_output.session_id = m_nID;
                cmpp_output.sequence_id = getSequenceID();
                cmpp_output.generate_time = System.currentTimeMillis();
                m_queue.push(cmpp_output);
            }
            if(CMPPCommandID.isMessage(cmpp_output.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            Log.log("CMPPSessionEngine[" + BindType.toString(m_sc.m_nType) + "].sendPakcet", 0x200800000000000L);
            cmpp_output.wrapCMPPPacket();
        }
        if(m_output.put(cmpp_output))
        {
            cmpp_output = null;
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * �̳��Ը��෽������ɽ�����Ϣ����������߳�InputEngine�����ع����̵߳Ĺ���
     * @return �����Ƿ�ȡ���ɹ�
     * @throws BufferException
     */
    public boolean get()
        throws BufferException
    {
        if(cmpp_input == null)
        {
            //�������߳�InputEngine�������Ϣ�����ж�ȡ��Ϣ��
            Packet packet = m_input.get();
            if(packet == null)
                return false;
            cmpp_input = new CMPPPacket(packet);
            Log.log("CMPPSessionEngine[" + BindType.toString(m_sc.m_nType) + "].recievePakcet", 0x200800000000000L);
            cmpp_input.unwrapCMPPPacket();
            //��ȡ��������֮�����Ϊ�ظ���Ϣ����Ϣ������Ѱ����֮��Ӧ��������Ϣ��ͨ�����кŽ�����Ӧ��ϵ����������guid����
            if(CMPPCommandID.isResponse(cmpp_input.command_id) && !m_queue.recover(cmpp_input))
                cmpp_input.guid = 0L;//����Ҳ�����Ӧ��������Ϣ�����������Ϊ��
            if(CMPPCommandID.isMessage(cmpp_input.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            packet.empty();
        }
        //�ѻ�ȡ����Ϣ�������������ع����߳�
        if(m_gateway.put(m_sc.m_nType, cmpp_input))
        {
            cmpp_input = null;
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * �����Ϣ���е�״̬������Ϣ���е������������Ƶ��������ʱ�ر��̣߳�
     * ��������г�ʱ��Ϣ�ĸ��������ۼƵ��Ự�ܴ�������
     */
    public void checkPacketQueue()
    {
        m_nQueueSize = m_queue.getSize();
        if(m_sc.m_nQueueSize > 0 && m_nQueueSize > m_sc.m_nQueueSize)
            m_bNeedTerminate = true;
        m_nErrorCount += m_queue.checkTimeout();
    }

    /**
     * �ɲο��Ը��෽�� ���������������ж�ȡCMPPP��Ϣ��
     * @return ���ش��������ж�ȡ��CMPP��Ϣ��
     */
    public CMPPPacket readCMPPPacket()
    {
        try
        {
            CMPPPacket packet = new CMPPPacket();
            //��ʶ��Ϣ����������������
            packet.gateway_name = m_gateway.m_strName;
            //�������Ϣ���ĻỰ���
            packet.session_id = m_nID;
            //�����ж�ȡ��Ϣ��
            packet.inputPacket(m_dis);
            //������Ϣ��
            packet.unwrapCMPPPacket();
            return packet;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPSessionEngine.readPacket : unexpected exit !", 0x2000040000000000L);
        return null;
    }

    /**
     * �ɲο��Ը��෽�����������������д����Ϣ��
     * @param packet ��д�����Ϣ��
     * @return �����Ƿ�д��ɹ�
     */
    public boolean writeCMPPPacket(CMPPPacket packet)
    {
        try
        {
            //��֯��Ϣ����������Ϣͷ
            packet.wrapCMPPPacket();
            //����Ϣ��д�����������
            packet.outputPacket(m_dos);
            return true;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPSessionEngine.writePacket : unexpected exit !", 0x2000040000000000L);
        return false;
    }

    /**
     * ����Ϣ���ɹ�д������������󼴴���������ȡ��Ϣ�������أ�����ʵ����Э����أ���ο���Ӧ�̳���
     * ��д��ʧ���򷵻�NULL
     * @param packet ��д�����Ϣ��
     * @return �������������ж�ȡ����Ϣ��
     */
    public CMPPPacket request(CMPPPacket packet)
    {
        if(writeCMPPPacket(packet))
            return readCMPPPacket();
        else
            return null;
    }


}
