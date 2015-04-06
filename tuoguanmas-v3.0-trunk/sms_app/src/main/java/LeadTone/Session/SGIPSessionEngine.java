package LeadTone.Session;

import java.net.Socket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Gateway.SGIPGatewayEngine;
import LeadTone.Packet.Packet;
import LeadTone.Packet.SGIPPacket.SGIPCommandID;
import LeadTone.Packet.SGIPPacket.SGIPPacket;
import LeadTone.Packet.SGIPPacket.SGIPPacketQueue;

/**
 * SGIP�й���ͨ�Ķ���ϢЭ��ʵ��
 */
public class SGIPSessionEngine extends SessionEngine
{
    SGIPPacketQueue m_queue;
    SGIPPacket sgip_input;
    SGIPPacket sgip_output;

    public SGIPSessionEngine(SessionConfig sc, Socket socket, SGIPGatewayEngine gateway)
    {
        super(sc, socket, gateway);
        m_queue = new SGIPPacketQueue();
        sgip_input = null;
        sgip_output = null;
    }

    public SGIPSessionEngine(SessionConfig sc, SGIPGatewayEngine gateway)
    {
        super(sc, gateway);
        m_queue = new SGIPPacketQueue();
        sgip_input = null;
        sgip_output = null;
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
        if(sgip_output == null)
        {
            sgip_output = (SGIPPacket)m_gateway.get(m_sc.m_nType, m_nID);
            if(sgip_output == null)
                return false;
            if(SGIPCommandID.isRequest(sgip_output.command_id))
            {
                sgip_output.session_id = m_nID;
                sgip_output.sequence_id = getSequenceID();
                m_queue.push(sgip_output);
            }
            if(SGIPCommandID.isMessage(sgip_output.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            sgip_output.wrapSGIPPacket();
        }
        if(m_output.put(sgip_output))
        {
            sgip_output = null;
            return true;
        } else
        {
            return false;
        }
    }

    public boolean get()
        throws BufferException
    {
        if(sgip_input == null)
        {
            Packet packet = m_input.get();
            if(packet == null)
                return false;
            sgip_input = new SGIPPacket(packet);
            sgip_input.unwrapSGIPPacket();
            if(SGIPCommandID.isResponse(sgip_input.command_id) && !m_queue.recover(sgip_input))
                sgip_input.guid = -1L;
            if(SGIPCommandID.isMessage(sgip_input.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            packet.empty();
        }
        if(m_gateway.put(m_sc.m_nType, sgip_input))
        {
            sgip_input = null;
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

    public SGIPPacket readSGIPPacket()
    {
        try
        {
            SGIPPacket packet = new SGIPPacket();
            packet.gateway_name = m_gateway.m_strName;
            packet.session_id = m_nID;
            packet.inputPacket(m_dis);
            packet.unwrapSGIPPacket();
            return packet;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000040000000000L);
            Log.log(e);
        }
        Log.log("SGIPSessionEngine.readPacket : unexpected exit !", 0x2000040000000000L);
        return null;
    }

    public boolean writeSGIPPacket(SGIPPacket packet)
    {
        try
        {
            packet.wrapSGIPPacket();
            packet.outputPacket(m_dos);
            return true;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000040000000000L);
        }
        Log.log("SGIPSessionEngine[" + SessionConfig.toString(m_sc.m_nType) + "].writePacket : unexpected exit !", 0x2000040000000000L);
        return false;
    }

    public SGIPPacket request(SGIPPacket packet)
    {
        if(writeSGIPPacket(packet))
            return readSGIPPacket();
        else
            return null;
    }


}