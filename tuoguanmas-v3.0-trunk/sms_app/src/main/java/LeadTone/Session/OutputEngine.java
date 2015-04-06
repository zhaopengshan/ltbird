package LeadTone.Session;

import LeadTone.Engine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Packet.PacketQueue;
import LeadTone.TimeConfig;


/**
 * SessionEngine建立socket连接，在socket连接上建立输入流输出流，
 * InputEngine建立一单独线程从消息队列中读取消息包写入输出流
 */
public class OutputEngine extends Engine
{
    public int m_nCount;
    SessionEngine m_session;
    long m_lTimeout;
    long m_lLastPacket;
    PacketQueue m_queue;

    /**
     * 构造方法初始化类变量
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
     * 设置消息队列的最大容量
     * @param nCapacity
     */
    public void setCapacity(int nCapacity)
    {
        m_queue.setCapacity(nCapacity);
    }

    /**
     * 设置超时时限
     * @param lTimeout
     */
    public void setTimeout(long lTimeout)
    {
        m_lTimeout = lTimeout;
    }

    /**
     * 检查从输入流读取数据是否超时
     * @return 返回是否超时的布尔值
     */
    public boolean isTimeout()
    {
        return m_lTimeout > 0L && System.currentTimeMillis() - m_lLastPacket > m_lTimeout;
    }

    /**
     * 清空所使用的系统资源
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
                //从队列中读取待下发的消息，如果队列中暂无消息包或提取出的消息包已经超时，则线程小睡重新伦循
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
                //更新最近一次读取数据的时间
                m_lLastPacket = System.currentTimeMillis();
                packet.empty();
                //更新处理消息的数量
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
     * 把消息包放入消息队列
     * @param packet
     * @return 返回将消息包放入消息队列是否成功的布尔值
     */
    public boolean put(Packet packet)
    {
        return m_queue.push(packet);
    }


}
