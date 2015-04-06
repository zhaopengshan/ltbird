package LeadTone.Packet.CMPPPacket;

import LeadTone.Packet.PacketQueue;
import java.util.Enumeration;
import java.util.Vector;

/**
 * CMPP消息队列实现类，扩展PacketQueue，实现CMPP协议功能
 */
public class CMPPPacketQueue extends PacketQueue
{

    /**
     * 构造方法，初始化类变量
     */
    public CMPPPacketQueue()
    {
    }

    /**
     * 构造方法，初始化类变来能够，设置消息队列最大容量
     * @param nCapacity
     */
    public CMPPPacketQueue(int nCapacity)
    {
        super(nCapacity);
    }

    /**
     * 从消息队列中提取指定消息指令的消息体对象
     * @param command_id 消息指令
     * @return 从消息队列中提取的消息体对象
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
     * 从消息队列中提取指定消息指令和网关名称的消息体对象
     * @param command_id 消息指令
     * @param gateway_name 网关名称
     * @return 从消息队列中提取的消息体对象
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
     *  队列中有没有因未收到消息回复而等待重发的消息，如果有和这条回复消息序列号一致的重发消息，则不必再重发，直接从消息队列中移除，
     *  通过序列号将Request和Response配对，配对后将Request消息包从消息队列中移出，并更新Response消息包的主键为Request消息包的主键
     * @param response 回复消息包
     * @return 返回是否将Request和Response配对成功
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