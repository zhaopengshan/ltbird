package LeadTone.Packet.CMPPPacket;

import LeadTone.Packet.PacketQueue;
import java.util.Enumeration;
import java.util.Vector;

/**
 * CMPP��Ϣ����ʵ���࣬��չPacketQueue��ʵ��CMPPЭ�鹦��
 */
public class CMPPPacketQueue extends PacketQueue
{

    /**
     * ���췽������ʼ�������
     */
    public CMPPPacketQueue()
    {
    }

    /**
     * ���췽������ʼ��������ܹ���������Ϣ�����������
     * @param nCapacity
     */
    public CMPPPacketQueue(int nCapacity)
    {
        super(nCapacity);
    }

    /**
     * ����Ϣ��������ȡָ����Ϣָ�����Ϣ�����
     * @param command_id ��Ϣָ��
     * @return ����Ϣ��������ȡ����Ϣ�����
     */
    public synchronized CMPPPacket pop(int command_id)
    {
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            CMPPPacket packet = (CMPPPacket)packets.nextElement();
            if(packet.command_id == command_id)
            {
                m_packets.removeElement(packet);
                return packet;
            }
        }

        return null;
    }

    /**
     * ����Ϣ��������ȡָ����Ϣָ����������Ƶ���Ϣ�����
     * @param command_id ��Ϣָ��
     * @param gateway_name ��������
     * @return ����Ϣ��������ȡ����Ϣ�����
     */
    public synchronized CMPPPacket pop(int command_id, String gateway_name)
    {
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            CMPPPacket packet = (CMPPPacket)packets.nextElement();
            if((packet.gateway_name == null || packet.gateway_name.length() <= 0 || packet.gateway_name.equals(gateway_name)) && packet.command_id == command_id)
            {
                m_packets.removeElement(packet);
                return packet;
            }
        }

        return null;
    }

    /**
     *  ��������û����δ�յ���Ϣ�ظ����ȴ��ط�����Ϣ������к������ظ���Ϣ���к�һ�µ��ط���Ϣ���򲻱����ط���ֱ�Ӵ���Ϣ�������Ƴ���
     *  ͨ�����кŽ�Request��Response��ԣ���Ժ�Request��Ϣ������Ϣ�������Ƴ���������Response��Ϣ��������ΪRequest��Ϣ��������
     * @param response �ظ���Ϣ��
     * @return �����Ƿ�Request��Response��Գɹ�
     */
    public synchronized boolean recover(CMPPPacket response)
    {
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            CMPPPacket request = (CMPPPacket)packets.nextElement();
            if(request.sequence_id == response.sequence_id)
            {
                response.guid = request.guid;
                m_packets.removeElement(request);
                return true;
            }
        }

        return false;
    }
}