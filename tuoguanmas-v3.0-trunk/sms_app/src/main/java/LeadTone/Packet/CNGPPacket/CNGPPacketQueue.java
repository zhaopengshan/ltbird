package LeadTone.Packet.CNGPPacket;

import LeadTone.Packet.PacketQueue;
import java.util.Enumeration;
import java.util.Vector;



public class CNGPPacketQueue extends PacketQueue
{

    public CNGPPacketQueue()
    {
    }

    public CNGPPacketQueue(int nCapacity)
    {
        super(nCapacity);
    }

    public synchronized CNGPPacket pop(int command_id)
    {
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            CNGPPacket packet = (CNGPPacket)packets.nextElement();
            if(packet.command_id == command_id)
            {
                m_packets.removeElement(packet);
                return packet;
            }
        }

        return null;
    }

    public synchronized CNGPPacket pop(int command_id, String gateway_name)
    {
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            CNGPPacket packet = (CNGPPacket)packets.nextElement();
            if((packet.gateway_name == null || packet.gateway_name.equals(gateway_name)) && packet.command_id == command_id)
            {
                m_packets.removeElement(packet);
                return packet;
            }
        }

        return null;
    }

    public synchronized boolean recover(CNGPPacket response)
    {
        for(Enumeration packets = m_packets.elements(); packets != null && packets.hasMoreElements();)
        {
            CNGPPacket request = (CNGPPacket)packets.nextElement();
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