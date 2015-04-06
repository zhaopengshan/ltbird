package LeadTone.Packet.SGIPPacket;

import LeadTone.Packet.PacketQueue;
import java.util.Enumeration;
import java.util.Vector;



public class SGIPPacketQueue extends PacketQueue
{

    public SGIPPacketQueue()
    {
    }

    public SGIPPacketQueue(int nCapacity)
    {
        super(nCapacity);
    }

    public synchronized SGIPPacket pop(int command_id)
    {
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            SGIPPacket packet = (SGIPPacket)packets.nextElement();
            if(packet.command_id == command_id)
            {
                m_packets.removeElement(packet);
                return packet;
            }
        }

        return null;
    }

    public synchronized SGIPPacket pop(int command_id, String gateway_name)
    {
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            SGIPPacket packet = (SGIPPacket)packets.nextElement();
            if((packet.gateway_name == null || packet.gateway_name.equals(gateway_name)) && packet.command_id == command_id)
            {
                m_packets.removeElement(packet);
                return packet;
            }
        }

        return null;
    }

    public synchronized boolean recover(SGIPPacket response)
    {
        for(int i = 0; i < m_packets.size(); i++)
        {
            SGIPPacket request = (SGIPPacket)m_packets.elementAt(i);
            if((request.command_id | 0x80000000) == response.command_id && request.sequence_id == response.sequence_id)
            {
                response.guid = request.guid;
                m_packets.removeElementAt(i);
                request.empty();
                return true;
            }
        }

        return false;
    }
}