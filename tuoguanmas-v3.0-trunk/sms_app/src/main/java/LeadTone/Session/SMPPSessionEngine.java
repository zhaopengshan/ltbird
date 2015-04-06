package LeadTone.Session;

import LeadTone.BufferException;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Gateway.SMPPGatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Packet.SMPPPacket.*;
import java.net.Socket;


/**
 * Short Message Per To Per 标准短消息点对点协议实现，与CMPP协议基本一致
 */
public class SMPPSessionEngine extends SessionEngine
{
    SMPPPacketQueue m_queue;
    SMPPPacket smpp_input;
    SMPPPacket smpp_output;

    public SMPPSessionEngine(SessionConfig sc, Socket socket, SMPPGatewayEngine gateway)
    {
        super(sc, socket, gateway);
        m_queue = new SMPPPacketQueue();
        smpp_input = null;
        smpp_output = null;
    }

    public SMPPSessionEngine(SessionConfig sc, SMPPGatewayEngine gateway)
    {
        super(sc, gateway);
        m_queue = new SMPPPacketQueue();
        smpp_input = null;
        smpp_output = null;
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
        if(smpp_output == null)
        {
            smpp_output = (SMPPPacket)m_gateway.get(m_sc.m_nType, m_nID);
            if(smpp_output == null)
                return false;
            if(SMPPCommandID.isRequest(smpp_output.command_id))
            {
                smpp_output.session_id = m_nID;
                smpp_output.sequence_id = getSequenceID();
                m_queue.push(smpp_output);
            }
            if(SMPPCommandID.isMessage(smpp_output.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            smpp_output.wrapSMPPPacket();
        }
        if(m_output.put(smpp_output))
        {
            smpp_output = null;
            return true;
        } else
        {
            return false;
        }
    }

    public boolean get()
        throws BufferException
    {
        if(smpp_input == null)
        {
            Packet packet = m_input.get();
            if(packet == null)
                return false;
            smpp_input = new SMPPPacket(packet);
            smpp_input.unwrapSMPPPacket();
            if(SMPPCommandID.isResponse(smpp_input.command_id) && !m_queue.recover(smpp_input))
                smpp_input.guid = 0L;
            if(SMPPCommandID.isMessage(smpp_input.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            packet.empty();
        }
        if(m_gateway.put(m_sc.m_nType, smpp_input))
        {
            smpp_input = null;
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

    public SMPPPacket readSMPPPacket()
    {
        try
        {
            SMPPPacket packet = new SMPPPacket();
            packet.gateway_name = m_gateway.m_strName;
            packet.session_id = m_nID;
            packet.inputPacket(m_dis);
            packet.unwrapSMPPPacket();
            return packet;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000040000000000L);
            Log.log(e);
        }
        Log.log("SMPPSessionEngine.readPacket : unexpected exit !", 0x2000040000000000L);
        return null;
    }

    public boolean writeSMPPPacket(SMPPPacket packet)
    {
        try
        {
            packet.wrapSMPPPacket();
            packet.outputPacket(m_dos);
            return true;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000040000000000L);
        }
        Log.log("SMPPSessionEngine.writePacket : unexpected exit !", 0x2000040000000000L);
        return false;
    }

    public SMPPPacket request(SMPPPacket packet)
    {
        if(writeSMPPPacket(packet))
            return readSMPPPacket();
        else
            return null;
    }


}