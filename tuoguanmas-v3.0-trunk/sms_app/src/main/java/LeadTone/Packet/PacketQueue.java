
package LeadTone.Packet;

import java.util.Enumeration;
import java.util.Vector;


/**
 * 实际上就是对Vector的一些操作
 */
public class PacketQueue
{
    /**
     * 是否有容量最大值限制
     */
    static final int NO_LIMIT = -1;
    /**
     * 消息队列最大容量
     */
    int m_nCapacity;
    /**
     * 消息队列中的消息体
     */
    public Vector m_packets;

    /**
     * 构造方法初始化类变量
     */
    public PacketQueue()
    {
        m_nCapacity = 0;
        m_packets = null;
        m_nCapacity = -1;
        m_packets = new Vector();
    }

    public PacketQueue(int nCapacity)
    {
        m_nCapacity = 0;
        m_packets = null;
        m_nCapacity = nCapacity;
        m_packets = new Vector(nCapacity);
    }

    /**
     * 获取消息队列最大容量
     * @return 返回最大容量
     */
    public int getSize()
    {
        return m_packets.size();
    }

    /**
     * 查询消息队列是否已达到最大容量
     * @return 返回是否达到最大容量的布尔值
     */
    public boolean isFull()
    {
        return m_nCapacity >= 0 && m_packets.size() >= m_nCapacity;
    }

    /**
     * 查询消息队列是否无消息
     * @return 返回是否无消息的布尔值
     */
    public boolean isEmpty()
    {
        return m_packets.size() <= 0;
    }

    /**
     * 清空消息队列
     */
    public void empty()
    {
        m_packets.removeAllElements();
    }

    /**
     * 设置队列最大容量，有synchronized修饰词，保证线程安全
     * @param nCapacity 最大容量值
     */
    public synchronized void setCapacity(int nCapacity)
    {
        m_nCapacity = nCapacity;
    }

    /**
     * 从队列中提取消息体，但不从队列中移除提取的消息体，有synchronized修饰词，保证线程安全
     * @return 返回从队列中提取的消息体，如果队列中无消息则返回NULL
     */
    public synchronized Packet peer()
    {
        if(m_packets.size() > 0)
        {
            Packet packet = (Packet)m_packets.elementAt(0);
            return packet;
        } else
        {
            return null;
        }
    }

    /**
     * 从队列中提取消息体，并从队列中移除提取的消息体，有synchronized修饰词，保证线程安全
     * @return 返回从队列中提取的消息体，如果队列中无消息则返回NULL
     */
    public synchronized Packet pop()
    {
        if(m_packets.size() > 0)
        {
            Packet packet = (Packet)m_packets.remove(0);
            return packet;
        } else
        {
            return null;
        }
    }

    /**
     * 从队列中提取隶属于指定网关的消息体，并从队列中移除提取的消息体，有synchronized修饰词，保证线程安全
     * @return 返回从队列中提取的消息体，如果队列中无隶属指定网关的消息则返回NULL
     */
    public synchronized Packet pop(String gateway_name)
    {
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            Packet packet = (Packet)packets.nextElement();
            if(packet.gateway_name == null || packet.gateway_name.length() <= 0 || packet.gateway_name.equals(gateway_name))
            {
                m_packets.removeElement(packet);
                return packet;
            }
        }

        return null;
    }

    /**
     * 将消息体放入消息队列中，如消息队列已满则返回false,如果成功放入则返回true，有synchronized修饰词，保证线程安全
     * @param packet 待放入队列的消息体
     * @return 返回是否成功将消息体放入消息队列中的布尔值
     */
    public synchronized boolean push(Packet packet)
    {
        if(m_nCapacity >= 0 && m_packets.size() >= m_nCapacity)
        {
            return false;
        } else
        {
            m_packets.addElement(packet);
            return true;
        }
    }

    public synchronized void pushRudely(Packet packet)
    {
        m_packets.addElement(packet);
    }
    /**
     * 遍历消息队列中的所有消息体，检查超时消息体将其从消息队列中移除，并返回超时消息的总数，有synchronized修饰词，保证线程安全
     * @return 返回消息队列中超时消息体的个数
     */
    public synchronized int checkTimeout()
    {
        int nCount = 0;
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            Packet packet = (Packet)packets.nextElement();
            if(packet.isTimeout())
            {
                m_packets.removeElement(packet);
                packet.empty();
                nCount++;
            }
        }

        return nCount;
    }



}
