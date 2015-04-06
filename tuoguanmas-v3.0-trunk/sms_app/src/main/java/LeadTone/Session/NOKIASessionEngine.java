package LeadTone.Session;

import LeadTone.BufferException;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Gateway.NOKIAGatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPCommandID;
import LeadTone.Packet.NOKIAPacket.NOKIAPacket;
import LeadTone.Packet.NOKIAPacket.NOKIAPacketQueue;
import LeadTone.Packet.Packet;
import java.net.Socket;


/**
 * NOKIA的CMPP协议实现，由于其和CMPP标准协议差异较大，故单独处理，没有继承自CMPPSessionEngine
 */
public class NOKIASessionEngine extends SessionEngine
{
    NOKIAPacketQueue m_queue;
    NOKIAPacket nokia_input;
    NOKIAPacket nokia_output;

    public NOKIASessionEngine(SessionConfig sc, Socket socket, NOKIAGatewayEngine gateway)
    {
        super(sc, socket, gateway);
        m_queue = new NOKIAPacketQueue();
        nokia_input = null;
        nokia_output = null;
    }

    public NOKIASessionEngine(SessionConfig sc, NOKIAGatewayEngine gateway)
    {
        super(sc, gateway);
        m_queue = new NOKIAPacketQueue();
        nokia_input = null;
        nokia_output = null;
    }

    public void empty()
    {
        m_queue.empty();
        m_queue = null;
        super.empty();
    }

    public boolean put()
        throws BufferException
    {
        if(nokia_output == null)
        {
            nokia_output = (NOKIAPacket)m_gateway.get(m_sc.m_nType, m_nID);
            if(nokia_output == null)
                return false;
            if(CMPPCommandID.isRequest(nokia_output.command_id))
            {
                nokia_output.session_id = m_nID;
                nokia_output.sequence_id = getSequenceID();
                m_queue.push(nokia_output);
            }
            if(CMPPCommandID.isMessage(nokia_output.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            nokia_output.wrapNOKIAPacket();
        }
        if(m_output.put(nokia_output))
        {
            nokia_output = null;
            return true;
        } else
        {
            return false;
        }
    }

    public boolean get()
        throws BufferException
    {
        if(nokia_input == null)
        {
            Packet packet = m_input.get();
            if(packet == null)
                return false;
            nokia_input = new NOKIAPacket(packet);
            nokia_input.unwrapNOKIAPacket();
            if(nokia_input.command_id == 0x80000000)
                m_bNeedTerminate = true;
            if(CMPPCommandID.isResponse(nokia_input.command_id) && !m_queue.recover(nokia_input))
                nokia_input.guid = -1L;
            if(CMPPCommandID.isMessage(nokia_input.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            packet.empty();
        }
        if(m_gateway.put(m_sc.m_nType, nokia_input))
        {
            nokia_input = null;
            return true;
        } else
        {
            return false;
        }
    }

    public void checkPacketQueue()
    {
        m_nQueueSize = m_queue.getSize();
        if(m_sc.m_nQueueSize > 0 && m_nQueueSize > m_sc.m_nQueueSize)
            m_bNeedTerminate = true;
        m_nErrorCount += m_queue.checkTimeout();
    }

    public NOKIAPacket readNOKIAPacket()
    {
        try
        {
            NOKIAPacket packet = new NOKIAPacket();
            packet.gateway_name = m_gateway.m_strName;
            packet.session_id = m_nID;
            packet.inputPacket(m_dis);
            packet.unwrapNOKIAPacket();
            return packet;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000040000000000L);
            Log.log(e);
        }
        Log.log("NOKIASessionEngine.readPacket : unexpected exit !", 0x2000040000000000L);
        return null;
    }

    public boolean writeNOKIAPacket(NOKIAPacket packet)
    {
        try
        {
            packet.wrapNOKIAPacket();
            packet.outputPacket(m_dos);
            return true;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000040000000000L);
        }
        Log.log("NOKIASessionEngine.writePacket : unexpected exit !", 0x2000040000000000L);
        return false;
    }

    public NOKIAPacket request(NOKIAPacket packet)
    {
        if(writeNOKIAPacket(packet))
            return readNOKIAPacket();
        else
            return null;
    }


}