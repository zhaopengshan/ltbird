package LeadTone.Gateway;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Packet.SMPPPacket.*;
import LeadTone.Session.*;
import java.util.Vector;


public class SMPPGatewayEngine extends GatewayEngine
{
    public ServiceProvider m_sp;
    public SMPPPacketQueue m_output;
    public SMPPPacketQueue m_input;

    public SMPPGatewayEngine(String strName, int nType, ServiceProvider sp)
    {
        super(strName, nType);
        m_sp = null;
        m_output = new SMPPPacketQueue(0);
        m_input = new SMPPPacketQueue(0);
        m_sp = sp;
    }

    public void setCapacity(int nCapacity)
    {
        m_output.setCapacity(nCapacity);
        m_input.setCapacity(nCapacity);
    }

    public void empty()
    {
        m_sp = null;
        m_output.empty();
        m_output = null;
        m_input.empty();
        m_input = null;
        super.empty();
    }

    public void checkAll()
    {
        m_output.checkTimeout();
        m_input.checkTimeout();
    }

    public boolean loadSession(SessionConfig sc, boolean bNeedStartup)
    {
        try
        {
            if(sc.m_nType == 1 || sc.m_nType == 2)
            {
                SMPPSessionEngine session = new SMPPSessionEngine(sc, this);
                m_sessions.addElement(session);
                if(bNeedStartup)
                {
                    session.startup();
                    Engine.wait(session);
                }
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000080000000000L);
            Log.log(e);
            Log.log("SMPPGatewayEngine(" + m_strName + ").loadSession : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }

    public synchronized Packet get(int nType, int nID)
    {
        SMPPPacket smpp = (SMPPPacket)m_output.peer();
        if(smpp == null)
            return null;
        if(smpp.session_id > 0 && smpp.session_id != nID)
            return null;
        if(BindType.forTransmitter(nType) && SMPPCommandID.isTransmitterOutput(smpp.command_id) || BindType.forReceiver(nType) && SMPPCommandID.isReceiverOutput(smpp.command_id))
        {
            m_output.pop();
            return smpp;
        } else
        {
            return null;
        }
    }

    public synchronized boolean put(int nType, Packet packet)
    {
        SMPPPacket smpp = (SMPPPacket)packet;
        if(smpp == null)
            return true;
        if(handleActiveTest(smpp) || handleActiveTestResponse(smpp) || handleTerminate(smpp) || handlGenericNack(smpp))
        {
            smpp.empty();
            smpp = null;
            return true;
        }
        if(BindType.forTransmitter(nType) && SMPPCommandID.isTransmitterInput(smpp.command_id) || BindType.forReceiver(nType) && SMPPCommandID.isReceiverInput(smpp.command_id))
            return m_input.push(smpp);
        else
            return true;
    }

    public boolean handleActiveTest(SMPPPacket packet)
    {
        if(packet.command_id == 21)
        {
            SMPPSessionEngine session = (SMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
                try
                {
                    SMPPEnquireLinkResponse response = new SMPPEnquireLinkResponse(packet.sequence_id);
                    response.gateway_name = packet.gateway_name;
                    response.session_id = packet.session_id;
                    if(!m_output.push(response))
                        session.m_nErrorCount++;
                }
                catch(Exception e)
                {
                    Log.log(e);
                    Log.log("SMPPGatewayEngine.handleEnquireLink : unexpected exit !", 0x2000040000000000L);
                }
            return true;
        } else
        {
            return false;
        }
    }

    public boolean handleActiveTestResponse(SMPPPacket packet)
    {
        if(packet.command_id == 0x80000015)
        {
            SMPPSessionEngine session = (SMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
            {
                session.m_nErrorCount--;
                if(session.m_nErrorCount < 0)
                    session.m_nErrorCount = 0;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public boolean handleTerminate(SMPPPacket packet)
    {
        if(packet.command_id == 6)
        {
            SMPPSessionEngine session = (SMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
            {
                try
                {
                    SMPPUnbindResponse response = new SMPPUnbindResponse(packet.sequence_id);
                    session.writeSMPPPacket(response);
                    session.m_bNeedTerminate = true;
                    return true;
                }
                catch(Exception e)
                {
                    Log.log(e);
                }
                Log.log("SMPPGatewayEngine.handleUnbind : unexpected exit !", 0x2000040000000000L);
            }
            return true;
        } else
        {
            return false;
        }
    }

    public boolean handlGenericNack(SMPPPacket packet)
    {
        if(packet.command_id == 0x80000000)
        {
            SMPPSessionEngine session = (SMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
                session.m_nErrorCount++;
            return true;
        } else
        {
            return false;
        }
    }

    public void enquireLink(SessionEngine session)
    {
        try
        {
            SMPPEnquireLink request = new SMPPEnquireLink(0x7fffffff);
            request.gateway_name = m_strName;
            request.session_id = session.m_nID;
            m_output.push(request);
            session.m_nErrorCount++;
            session.delayActiveTest();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("SMPPGatewayEngine.enquireLink : unexpected exit !", 0x2000080000000000L);
        }
    }

    public boolean login(SessionEngine session)
    {
        try
        {
            SMPPBind bind = m_sp.getSMPPBind(session.m_sc.m_nType);
            if(bind == null)
            {
                session.m_bAuthenticated = true;
                return true;
            }
            bind.gateway_name = m_strName;
            bind.session_id = session.m_nID;
            bind.sequence_id = session.getSequenceID();
            bind.wrap();
            SMPPPacket packet = ((SMPPSessionEngine)session).request(bind);
            if(packet == null)
                return false;
            SMPPBindResponse response = new SMPPBindResponse(packet);
            if(response.command_status == 0)
            {
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("SMPPGatewayEngine(" + m_strName + ").login : unexpected exit !", 0x2000080000000000L);
        }
        session.m_bAuthenticated = false;
        return false;
    }

    public void logout(SessionEngine session)
    {
        try
        {
            SMPPUnbind unbind = new SMPPUnbind(session.getSequenceID());
            unbind.gateway_name = m_strName;
            unbind.session_id = session.m_nID;
            SMPPPacket packet = ((SMPPSessionEngine)session).request(unbind);
            if(packet == null)
                return;
            SMPPUnbindResponse response = new SMPPUnbindResponse(packet);
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("SMPPGatewayEngine(" + m_strName + ").logout : unexpected exit !", 0x2000080000000000L);
        }
        session.m_bAuthenticated = false;
    }

    public Packet receive()
    {
        return m_input.pop();
    }

    public boolean send(Packet packet)
    {
        return m_output.push(packet);
    }


}