package LeadTone.Session;

import LeadTone.Engine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Packet.PacketQueue;
import LeadTone.TimeConfig;
import java.io.InputStream;

/**
 * SessionEngine建立socket连接，在socket连接上建立输入流输出流，
 * InputEngine建立一单独线程从输入流上读取消息包放入消息队列
 */
public class InputEngine extends Engine
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
     * 设置超时时限
     * @param lTimeout
     */
    public void setTimeout(long lTimeout)
    {
        m_lTimeout = lTimeout;
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
     * 不断从输入流中检查数据，超时退出，
     * 如读到消息包则将消息包放入消息队列
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
                //循环将从输入流中读取的消息包放入队列，直到放入为止
                for(; isRunning() && !m_queue.push(packet); nap());
                //更新最近一次读取数据的时间
                m_lLastPacket = System.currentTimeMillis();
                //更新处理消息的数量
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
     * 从队列中获取消息包
     * @return  获取的消息包
     */
    public Packet get()
    {
        return m_queue.pop();
    }


}
