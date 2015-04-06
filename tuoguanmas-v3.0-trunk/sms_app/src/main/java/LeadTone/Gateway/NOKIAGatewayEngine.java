package LeadTone.Gateway;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPCommandID;
import LeadTone.Packet.NOKIAPacket.*;
import LeadTone.Packet.Packet;
import LeadTone.Session.*;
import java.util.Vector;



public class NOKIAGatewayEngine extends GatewayEngine
{
    public ServiceProvider m_sp;
    public NOKIAPacketQueue m_output;
    public NOKIAPacketQueue m_input;

    public NOKIAGatewayEngine(String strName, int nGatewayType, ServiceProvider sp)
    {
        super(strName, nGatewayType);
        m_sp = null;
        m_output = new NOKIAPacketQueue(0);
        m_input = new NOKIAPacketQueue(0);
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
            if(sc.m_nType == 1 || sc.m_nType == 2 || sc.m_nType == 3)
            {
                NOKIASessionEngine session = new NOKIASessionEngine(sc, this);
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
            Log.log("NOKIAGatewayEngine(" + m_strName + ").loadSession : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }

    public synchronized Packet get(int nType, int nID)
    {
        NOKIAPacket cmpp = (NOKIAPacket)m_output.peer();
        if(cmpp == null)
            return null;
        if(cmpp.session_id > 0 && cmpp.session_id != nID)
            return null;
        if(BindType.forTransmitter(nType) && CMPPCommandID.isTransmitterOutput(cmpp.command_id) || BindType.forReceiver(nType) && CMPPCommandID.isReceiverOutput(cmpp.command_id))
        {
            m_output.pop();
            return cmpp;
        } else
        {
            return null;
        }
    }

    public synchronized boolean put(int nType, Packet packet)
    {
        NOKIAPacket cmpp = (NOKIAPacket)packet;
        if(cmpp == null)
            return true;
        if(handleActiveTest(cmpp) || handleActiveTestResponse(cmpp) || handleTerminate(cmpp) || handlGenericNack(cmpp))
        {
            cmpp.empty();
            cmpp = null;
            return true;
        }
        if(BindType.forTransmitter(nType) && CMPPCommandID.isTransmitterInput(cmpp.command_id) || BindType.forReceiver(nType) && CMPPCommandID.isReceiverInput(cmpp.command_id))
            return m_input.push(cmpp);
        else
            return true;
    }

    public boolean handleActiveTest(NOKIAPacket packet)
    {
        if(packet.command_id == 8)
        {
            NOKIASessionEngine session = (NOKIASessionEngine)getSession(packet.session_id);
            if(session != null)
                try
                {
                    NOKIAActiveTestResponse response = new NOKIAActiveTestResponse(packet.sequence_id);
                    response.gateway_name = packet.gateway_name;
                    response.session_id = packet.session_id;
                    if(!m_output.push(response))
                        session.m_nErrorCount++;
                }
                catch(Exception e)
                {
                    Log.log(e);
                    Log.log("NOKIAGatewayEngine.handleEnquireLink : unexpected exit !", 0x2000040000000000L);
                }
            return true;
        } else
        {
            return false;
        }
    }

    public boolean handleActiveTestResponse(NOKIAPacket packet)
    {
        if(packet.command_id == 0x80000008)
        {
            NOKIASessionEngine session = (NOKIASessionEngine)getSession(packet.session_id);
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

    public boolean handleTerminate(NOKIAPacket packet)
    {
        if(packet.command_id == 2)
        {
            NOKIASessionEngine session = (NOKIASessionEngine)getSession(packet.session_id);
            if(session != null)
            {
                try
                {
                    NOKIATerminateResponse response = new NOKIATerminateResponse(packet.sequence_id);
                    session.writeNOKIAPacket(response);
                    session.m_bNeedTerminate = true;
                    return true;
                }
                catch(Exception e)
                {
                    Log.log(e);
                }
                Log.log("NOKIAGatewayEngine.handleTerminate : unexpected exit !", 0x2000040000000000L);
            }
            return true;
        } else
        {
            return false;
        }
    }

    public boolean handlGenericNack(NOKIAPacket packet)
    {
        if(packet.command_id == 0x80000000)
        {
            NOKIASessionEngine session = (NOKIASessionEngine)getSession(packet.session_id);
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
            NOKIAActiveTest request = new NOKIAActiveTest(0x7fffffff);
            request.gateway_name = m_strName;
            request.session_id = session.m_nID;
            m_output.push(request);
            session.m_nErrorCount++;
            session.delayActiveTest();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("NOKIAGatewayEngine.enquireLink : unexpected exit !", 0x2000080000000000L);
        }
    }

    public boolean login(SessionEngine session)
    {
        try
        {
            NOKIAConnect connect = m_sp.getNOKIAConnect(session.m_sc.m_nType);
            if(connect == null)
            {
                session.m_bAuthenticated = true;
                return true;
            }
            connect.gateway_name = m_strName;
            connect.session_id = session.m_nID;
            connect.sequence_id = session.getSequenceID();
            connect.wrap();
            NOKIAPacket packet = ((NOKIASessionEngine)session).request(connect);
            if(packet == null)
                return false;
            NOKIAConnectResponse response = new NOKIAConnectResponse(packet);
            if(response.command_status == 0)
            {
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("NOKIAGatewayEngine(" + m_strName + ").login : unexpected exit !", 0x2000080000000000L);
        }
        session.m_bAuthenticated = false;
        return false;
    }

    public void logout(SessionEngine session)
    {
        try
        {
            NOKIATerminate terminate = new NOKIATerminate(session.getSequenceID());
            terminate.gateway_name = m_strName;
            terminate.session_id = session.m_nID;
            NOKIAPacket packet = ((NOKIASessionEngine)session).request(terminate);
            if(packet == null)
                return;
            NOKIATerminateResponse response = new NOKIATerminateResponse(packet);
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("NOKIAGatewayEngine(" + m_strName + ").logout : unexpected exit !", 0x2000080000000000L);
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