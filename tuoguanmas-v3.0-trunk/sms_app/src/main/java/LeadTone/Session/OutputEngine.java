package LeadTone.Session;

import LeadTone.Engine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Packet.PacketQueue;
import LeadTone.TimeConfig;


/**
 * SessionEngine����socket���ӣ���socket�����Ͻ����������������
 * InputEngine����һ�����̴߳���Ϣ�����ж�ȡ��Ϣ��д�������
 */
public class OutputEngine extends Engine
{
    public int m_nCount;
    SessionEngine m_session;
    long m_lTimeout;
    long m_lLastPacket;
    PacketQueue m_queue;

    /**
     * ���췽����ʼ�������
     * @param session
     */
    public OutputEngine(SessionEngine session)
    {
        super("OutputEngine");
        m_nCount = 0;
        m_session = null;
        m_lTimeout = TimeConfig.DEFAULT_PACKET_TIMEOUT;
        m_lLastPacket = System.currentTimeMillis();
        m_queue = new PacketQueue(128);
        m_session = session;
    }

    /**
     * ������Ϣ���е��������
     * @param nCapacity
     */
    public void setCapacity(int nCapacity)
    {
        m_queue.setCapacity(nCapacity);
    }

    /**
     * ���ó�ʱʱ��
     * @param lTimeout
     */
    public void setTimeout(long lTimeout)
    {
        m_lTimeout = lTimeout;
    }

    /**
     * ������������ȡ�����Ƿ�ʱ
     * @return �����Ƿ�ʱ�Ĳ���ֵ
     */
    public boolean isTimeout()
    {
        return m_lTimeout > 0L && System.currentTimeMillis() - m_lLastPacket > m_lTimeout;
    }

    /**
     * �����ʹ�õ�ϵͳ��Դ
     */
    public void empty()
    {
        m_session = null;
        m_queue.empty();
        m_queue = null;
    }

    /**
     *
     */
    public void run()
    {
        try
        {
            Log.log("OutputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : thread startup !", 0x20000000000L);
            m_nStatus = 1;
            while(isRunning()) 
            {
                if(isTimeout())
                {
                    Log.log("OutputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : no packet for a long time !", 0x20000000000L);
                    break;
                }
                //�Ӷ����ж�ȡ���·�����Ϣ�����������������Ϣ������ȡ������Ϣ���Ѿ���ʱ�����߳�С˯������ѭ
                Packet packet = m_queue.pop();
                if(packet == null || packet.isTimeout())
                {
                    nap();
                    continue;
                }
                if(!m_session.writePacket(packet))
                {
                    Log.log("OutputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : write packet error !", 0x2000020000000000L);
                    break;
                }
                Log.log("OutputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : " + "total_length = " + packet.total_length, 0x20000000000L);
                //�������һ�ζ�ȡ���ݵ�ʱ��
                m_lLastPacket = System.currentTimeMillis();
                packet.empty();
                //���´�����Ϣ������
                m_nCount++;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("OutputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : unexpected exit !", 0x2000020000000000L);
        }
        m_nStatus = 3;
        Log.log("OutputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : thread stopped !", 0x20000000000L);
        empty();
    }

    /**
     * ����Ϣ��������Ϣ����
     * @param packet
     * @return ���ؽ���Ϣ��������Ϣ�����Ƿ�ɹ��Ĳ���ֵ
     */
    public boolean put(Packet packet)
    {
        return m_queue.push(packet);
    }


}
