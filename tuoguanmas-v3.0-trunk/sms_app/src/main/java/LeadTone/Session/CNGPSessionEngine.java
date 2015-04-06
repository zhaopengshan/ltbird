package LeadTone.Session;

import LeadTone.BufferException;
import LeadTone.Gateway.CNGPGatewayEngine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.CNGPPacket.*;
import LeadTone.Packet.Packet;
import java.net.Socket;


/**
 * CNGP中国网通的短消息协议实现
 */
public class CNGPSessionEngine extends SessionEngine
{

    CNGPPacketQueue m_queue;
    CNGPPacket cngp_input;
    CNGPPacket cngp_output;

    public CNGPSessionEngine(SessionConfig sc, Socket socket, CNGPGatewayEngine gateway)
    {
        super(sc, socket, gateway);
        m_queue = new CNGPPacketQueue();
        cngp_input = null;
        cngp_output = null;
    }

    public CNGPSessionEngine(SessionConfig sc, CNGPGatewayEngine gateway)
    {
        super(sc, gateway);
        m_queue = new CNGPPacketQueue();
        cngp_input = null;
        cngp_output = null;
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
        if(cngp_output == null)
        {
            cngp_output = (CNGPPacket)m_gateway.get(m_sc.m_nType, m_nID);
            if(cngp_output == null)
                return false;
            if(CNGPCommandID.isRequest(cngp_output.command_id))
            {
                cngp_output.session_id = m_nID;
                cngp_output.sequence_id = getSequenceID();
                cngp_output.generate_time = System.currentTimeMillis();
                m_queue.push(cngp_output);
            }
            if(CNGPCommandID.isMessage(cngp_output.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            Log.log("CNGPSessionEngine[" + BindType.toString(m_sc.m_nType) + "].sendPakcet", 0x500800000000000L);
            cngp_output.wrapCNGPPacket();
        }
        if(m_output.put(cngp_output))
        {
            cngp_output = null;
            return true;
        } else
        {
            return false;
        }
    }

    public boolean get()
        throws BufferException
    {
        if(cngp_input == null)
        {
            Packet packet = m_input.get();
            if(packet == null)
                return false;
            cngp_input = new CNGPPacket(packet);
            Log.log("CNGPSessionEngine[" + BindType.toString(m_sc.m_nType) + "].recievePakcet", 0x500800000000000L);
            cngp_input.unwrapCNGPPacket();
            if(CNGPCommandID.isResponse(cngp_input.command_id) && !m_queue.recover(cngp_input))
                cngp_input.guid = 0L;
            if(CNGPCommandID.isMessage(cngp_input.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            packet.empty();
        }
        if(m_gateway.put(m_sc.m_nType, cngp_input))
        {
            cngp_input = null;
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

    public CNGPPacket readCNGPPacket()
    {
        try
        {
            CNGPPacket packet = new CNGPPacket();
            packet.gateway_name = m_gateway.m_strName;
            packet.session_id = m_nID;
            packet.inputPacket(m_dis);
            packet.unwrapCNGPPacket();
            return packet;
        }
        catch(Exception e)
        {
            Log.log(e);
            e.printStackTrace();
            Log.log("CNGPSessionEngine.readPacket : unexpected exit !", 0x2000040000000000L);
            return null;
        }
    }

    public boolean writeCNGPPacket(CNGPPacket packet)
    {
        try
        {
            packet.wrapCNGPPacket();
            packet.outputPacket(m_dos);
            return true;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CNGPSessionEngine.writePacket : unexpected exit !", 0x2000040000000000L);
        return false;
    }

    public CNGPPacket request(CNGPPacket packet)
    {
        if(writeCNGPPacket(packet))
            return readCNGPPacket();
        else
            return null;
    }


}