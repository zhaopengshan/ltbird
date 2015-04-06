package LeadTone.Session;

import LeadTone.Engine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Packet.PacketQueue;
import LeadTone.TimeConfig;
import java.io.InputStream;

/**
 * SessionEngine����socket���ӣ���socket�����Ͻ����������������
 * InputEngine����һ�����̴߳��������϶�ȡ��Ϣ��������Ϣ����
 */
public class InputEngine extends Engine
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
    public InputEngine(SessionEngine session)
    {
        super("InputEngine");
        m_nCount = 0;
        m_session = null;
        m_lTimeout = TimeConfig.DEFAULT_PACKET_TIMEOUT;
        m_lLastPacket = System.currentTimeMillis();
        m_queue = new PacketQueue(128);
        m_session = session;
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
     * ������Ϣ���е��������
     * @param nCapacity
     */
    public void setCapacity(int nCapacity)
    {
        m_queue.setCapacity(nCapacity);
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
     * ���ϴ��������м�����ݣ���ʱ�˳���
     * �������Ϣ������Ϣ��������Ϣ����
     */
    public void run()
    {
        try
        {
            Log.log("InputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : thread startup !", 0x10000000000L);
            m_nStatus = 1;
            while(isRunning()) 
            {
                if(isTimeout())
                {
                    Log.log("InputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : no packet for a long time !", 0x2000010000000000L);
                    break;
                }
                if(m_session.m_is.available() < 4)
                {
                    nap();
                    continue;
                }
                Packet packet = m_session.readPacket();
                if(packet == null)
                {
                    Log.log("InputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : read packet error !", 0x2000010000000000L);
                    break;
                }
                Log.log("InputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : " + "total_length = " + packet.total_length, 0x10000000000L);
                //ѭ�������������ж�ȡ����Ϣ��������У�ֱ������Ϊֹ
                for(; isRunning() && !m_queue.push(packet); nap());
                //�������һ�ζ�ȡ���ݵ�ʱ��
                m_lLastPacket = System.currentTimeMillis();
                //���´�����Ϣ������
                m_nCount++;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("InputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : unexpected exit !", 0x2000010000000000L);
        }
        m_nStatus = 3;
        Log.log("InputEngine(" + m_session.m_gateway.m_strName + "," + SessionConfig.toString(m_session.m_sc.m_nType) + "," + m_session.m_nID + ").run : thread stopped !", 0x10000000000L);
        empty();
    }


    /**
     * �Ӷ����л�ȡ��Ϣ��
     * @return  ��ȡ����Ϣ��
     */
    public Packet get()
    {
        return m_queue.pop();
    }


}
