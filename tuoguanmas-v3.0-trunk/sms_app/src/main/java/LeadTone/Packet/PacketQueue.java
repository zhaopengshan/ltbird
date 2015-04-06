
package LeadTone.Packet;

import java.util.Enumeration;
import java.util.Vector;


/**
 * ʵ���Ͼ��Ƕ�Vector��һЩ����
 */
public class PacketQueue
{
    /**
     * �Ƿ����������ֵ����
     */
    static final int NO_LIMIT = -1;
    /**
     * ��Ϣ�����������
     */
    int m_nCapacity;
    /**
     * ��Ϣ�����е���Ϣ��
     */
    public Vector m_packets;

    /**
     * ���췽����ʼ�������
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
     * ��ȡ��Ϣ�����������
     * @return �����������
     */
    public int getSize()
    {
        return m_packets.size();
    }

    /**
     * ��ѯ��Ϣ�����Ƿ��Ѵﵽ�������
     * @return �����Ƿ�ﵽ��������Ĳ���ֵ
     */
    public boolean isFull()
    {
        return m_nCapacity >= 0 && m_packets.size() >= m_nCapacity;
    }

    /**
     * ��ѯ��Ϣ�����Ƿ�����Ϣ
     * @return �����Ƿ�����Ϣ�Ĳ���ֵ
     */
    public boolean isEmpty()
    {
        return m_packets.size() <= 0;
    }

    /**
     * �����Ϣ����
     */
    public void empty()
    {
        m_packets.removeAllElements();
    }

    /**
     * ���ö��������������synchronized���δʣ���֤�̰߳�ȫ
     * @param nCapacity �������ֵ
     */
    public synchronized void setCapacity(int nCapacity)
    {
        m_nCapacity = nCapacity;
    }

    /**
     * �Ӷ�������ȡ��Ϣ�壬�����Ӷ������Ƴ���ȡ����Ϣ�壬��synchronized���δʣ���֤�̰߳�ȫ
     * @return ���شӶ�������ȡ����Ϣ�壬�������������Ϣ�򷵻�NULL
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
     * �Ӷ�������ȡ��Ϣ�壬���Ӷ������Ƴ���ȡ����Ϣ�壬��synchronized���δʣ���֤�̰߳�ȫ
     * @return ���شӶ�������ȡ����Ϣ�壬�������������Ϣ�򷵻�NULL
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
     * �Ӷ�������ȡ������ָ�����ص���Ϣ�壬���Ӷ������Ƴ���ȡ����Ϣ�壬��synchronized���δʣ���֤�̰߳�ȫ
     * @return ���شӶ�������ȡ����Ϣ�壬���������������ָ�����ص���Ϣ�򷵻�NULL
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
     * ����Ϣ�������Ϣ�����У�����Ϣ���������򷵻�false,����ɹ������򷵻�true����synchronized���δʣ���֤�̰߳�ȫ
     * @param packet ��������е���Ϣ��
     * @return �����Ƿ�ɹ�����Ϣ�������Ϣ�����еĲ���ֵ
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
     * ������Ϣ�����е�������Ϣ�壬��鳬ʱ��Ϣ�彫�����Ϣ�������Ƴ��������س�ʱ��Ϣ����������synchronized���δʣ���֤�̰߳�ȫ
     * @return ������Ϣ�����г�ʱ��Ϣ��ĸ���
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
