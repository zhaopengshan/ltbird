package LeadTone.Gateway;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CNGPPacket.*;
import LeadTone.Packet.Packet;
import LeadTone.Session.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;



public class CNGPGatewayEngine extends GatewayEngine
{
    public ServiceProvider m_sp;
    public CNGPPacketQueue m_output;
    public CNGPPacketQueue m_input;

    public CNGPGatewayEngine(String strName, int nGatewayType, ServiceProvider sp)
    {
        super(strName, nGatewayType);
        m_sp = null;
        m_output = new CNGPPacketQueue(0);
        m_input = new CNGPPacketQueue(0);
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

    public void accept()
    {
        try
        {
            Socket socket = m_socket.accept();
            Log.log("CNGPGatewayEngine(" + m_strName + ").accept : " + socket.toString(), 0x80000000000L);
            CNGPSessionEngine session = new CNGPSessionEngine(m_sc, socket, this);
            Log.log("CNGPGatewayEngine(" + m_strName + ").accept : begin startup " + session.m_nID + "th session engine !", 0x80000000000L);
            session.startup();
            m_sessions.addElement(session);
        }
        catch(Exception e) { }
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
                CNGPSessionEngine session = new CNGPSessionEngine(sc, this);
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
            Log.log("CNGPGatewayEngine(" + m_strName + ").loadSession : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }

    public synchronized Packet get(int nType, int nID)
    {
        CNGPPacket cngp = (CNGPPacket)m_output.peer();
        if(cngp == null)
            return null;
        if(cngp.session_id > 0 && cngp.session_id != nID)
            return null;
        if(BindType.forTransmitter(nType) && CNGPCommandID.isTransmitterOutput(cngp.command_id) || BindType.forReceiver(nType) && CNGPCommandID.isReceiverOutput(cngp.command_id))
        {
            m_output.pop();
            return cngp;
        } else
        {
            return null;
        }
    }

    public synchronized boolean put(int nType, Packet packet)
    {
        CNGPPacket cngp = (CNGPPacket)packet;
        if(cngp == null)
            return true;
        if(handleActiveTest(cngp) || handleActiveTestResponse(cngp) || handleTerminate(cngp))
        {
            cngp.empty();
            cngp = null;
            return true;
        }
        if(BindType.forTransmitter(nType) && CNGPCommandID.isTransmitterInput(cngp.command_id) || BindType.forReceiver(nType) && CNGPCommandID.isReceiverInput(cngp.command_id))
            return m_input.push(cngp);
        else
            return true;
    }

    public boolean handleActiveTest(CNGPPacket packet)
    {
        if(packet.command_id == 4)
        {
            CNGPSessionEngine session = (CNGPSessionEngine)getSession(packet.session_id);
            if(session != null)
                try
                {
                    CNGPActiveTestResponse response = new CNGPActiveTestResponse(packet.sequence_id);
                    response.gateway_name = packet.gateway_name;
                    response.session_id = packet.session_id;
                    if(!m_output.push(response))
                        session.m_nErrorCount++;
                    session.delayActiveTest();
                }
                catch(Exception e)
                {
                    Log.log(e);
                    Log.log("CNGPGatewayEngine.handleEnquireLink : unexpected exit !", 0x2000040000000000L);
                }
            return true;
        } else
        {
            return false;
        }
    }

    public boolean handleActiveTestResponse(CNGPPacket packet)
    {
        if(packet.command_id == 0x80000004)
        {
            CNGPSessionEngine session = (CNGPSessionEngine)getSession(packet.session_id);
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

    public boolean handleTerminate(CNGPPacket packet)
    {
        if(packet.command_id == 6)
        {
            CNGPSessionEngine session = (CNGPSessionEngine)getSession(packet.session_id);
            if(session != null)
            {
                try
                {
                    CNGPExit response = new CNGPExit(packet.sequence_id);
                    session.writeCNGPPacket(response);
                    session.m_bNeedTerminate = true;
                    return true;
                }
                catch(Exception e)
                {
                    Log.log(e);
                }
                Log.log("CNGPGatewayEngine.handleTerminate : unexpected exit !", 0x2000040000000000L);
            }
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
            CNGPActiveTest request = new CNGPActiveTest(0x7fffffff);
            request.gateway_name = m_strName;
            request.session_id = session.m_nID;
            m_output.push(request);
            session.m_nErrorCount++;
            session.delayActiveTest();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CNGPGatewayEngine.enquireLink : unexpected exit !", 0x2000080000000000L);
        }
    }

    public boolean login(SessionEngine session)
    {
        try
        {
            CNGPLogin connect = m_sp.getCNGPLogin(session.m_sc.m_nType);
            if(connect == null)
            {
                session.m_bAuthenticated = true;
                return true;
            }
            connect.gateway_name = m_strName;
            connect.session_id = session.m_nID;
            connect.sequence_id = session.getSequenceID();
            connect.wrap();
            CNGPPacket packet = ((CNGPSessionEngine)session).request(connect);
            if(packet == null)
                return false;
            CNGPLoginResponse response = new CNGPLoginResponse(packet);
            if(response.command_status == 0)
            {
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CNGPGatewayEngine(" + m_strName + ").login : unexpected exit !", 0x2000080000000000L);
        }
        session.m_bAuthenticated = false;
        return false;
    }

    public void logout(SessionEngine session)
    {
        try
        {
            CNGPExit terminate = new CNGPExit(session.getSequenceID());
            terminate.gateway_name = m_strName;
            terminate.session_id = session.m_nID;
            CNGPPacket packet = ((CNGPSessionEngine)session).request(terminate);
            if(packet == null)
                return;
            CNGPExitResponse response = new CNGPExitResponse(packet);
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CNGPGatewayEngine(" + m_strName + ").logout : unexpected exit !", 0x2000080000000000L);
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