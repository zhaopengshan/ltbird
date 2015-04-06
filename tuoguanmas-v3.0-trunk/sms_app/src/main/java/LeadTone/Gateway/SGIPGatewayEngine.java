package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Packet.SGIPPacket.*;
import LeadTone.Session.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class SGIPGatewayEngine extends GatewayEngine
{
    public ServiceProvider m_sp;
    public SGIPPacketQueue m_output;
    public SGIPPacketQueue m_input;

    public SGIPGatewayEngine(String strName, ServiceProvider sp)
    {
        super(strName, 0x30000);
        m_sp = null;
        m_output = new SGIPPacketQueue(0);
        m_input = new SGIPPacketQueue(0);
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
            if(sc.m_nType == 0)
            {
                m_sc = sc;
                if(setListen(sc.m_nPort))
                    return true;
            } else
            if(sc.m_nType == 1 || sc.m_nType == 2)
            {
                SGIPSessionEngine session = new SGIPSessionEngine(sc, this);
                m_sessions.addElement(session);
                if(bNeedStartup)
                    session.startup();
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000080000000000L);
            Log.log(e);
            Log.log("SGIPGatewayEngine(" + m_strName + ").loadSession : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }

    public void accept()
    {
        try
        {
            Socket socket = m_socket.accept();
            Log.log("SGIPGatewayEngine(" + m_strName + ").accept : " + socket.toString(), 0x80000000000L);
            SGIPSessionEngine session = new SGIPSessionEngine(m_sc, socket, this);
            Log.log("SGIPGatewayEngine(" + m_strName + ").accept : begin startup " + session.m_nID + "th session engine !", 0x80000000000L);
            session.startup();
            m_sessions.addElement(session);
        }
        catch(Exception e) { }
    }

    public synchronized Packet get(int nType, int nID)
    {
        SGIPPacket sgip = (SGIPPacket)m_output.peer();
        if(sgip == null)
            return null;
        if(sgip.session_id > 0 && sgip.session_id != nID)
            return null;
        if(BindType.forTransmitter(nType) && SGIPCommandID.isTransmitterOutput(sgip.command_id) || BindType.forReceiver(nType) && SGIPCommandID.isReceiverOutput(sgip.command_id))
        {
            m_output.pop();
            return sgip;
        } else
        {
            return null;
        }
    }

    public synchronized boolean put(int nType, Packet packet)
    {
        SGIPPacket sgip = (SGIPPacket)packet;
        if(sgip == null)
            return true;
        if(handleTerminate(sgip))
        {
            sgip.empty();
            sgip = null;
            return true;
        }
        if(BindType.forTransmitter(nType) && SGIPCommandID.isTransmitterInput(sgip.command_id))
            return m_input.push(sgip);
        if(BindType.forReceiver(nType) && SGIPCommandID.isReceiverInput(sgip.command_id))
        {
            if(m_input.m_packets.size() < 250)
            {
                m_input.m_packets.addElement(sgip);
                return true;
            } else
            {
                return false;
            }
        } else
        {
            return true;
        }
    }

    public boolean handleTerminate(SGIPPacket packet)
    {
        if(packet.command_id == 2)
        {
            SGIPSessionEngine session = (SGIPSessionEngine)getSession(packet.session_id);
            if(session != null)
            {
                try
                {
                    SGIPUnbindResponse response = new SGIPUnbindResponse(packet.sequence_id);
                    response.node_id = packet.node_id;
                    response.time_stamp = packet.time_stamp;
                    session.writeSGIPPacket(response);
                    session.m_bNeedTerminate = true;
                    return true;
                }
                catch(Exception e)
                {
                    Log.log(e);
                }
                Log.log("SGIPGatewayEngine.handleTerminate : unexpected exit !", 0x2000040000000000L);
            }
            return true;
        } else
        {
            return false;
        }
    }

    public boolean login(SessionEngine session)
    {
        try
        {
            SGIPBindResponse response;
            if(session.m_sc.m_nType == 0)
            {
                SGIPPacket packet = ((SGIPSessionEngine)session).readSGIPPacket();
                if(packet == null)
                {
                    session.m_bAuthenticated = false;
                    return false;
                } else
                {
                    SGIPBind bind = new SGIPBind(packet);
                    bind.unwrap();
                    response = new SGIPBindResponse(bind.sequence_id);
                    response.gateway_name = bind.gateway_name;
                    response.sequence_id = bind.sequence_id;
                    response.guid = bind.guid;
                    response.node_id = bind.node_id;
                    response.time_stamp = bind.time_stamp;
                    response.result = 0;
                    response.wrap();
                    ((SGIPSessionEngine)session).writeSGIPPacket(response);
                    session.m_bAuthenticated = true;
                    return true;
                }
            }
            SGIPBind bind = m_sp.getSGIPBind(session.m_sc.m_nType);
            if(bind == null)
            {
                session.m_bAuthenticated = true;
                return true;
            }
            bind.gateway_name = m_strName;
            bind.session_id = session.m_nID;
            bind.sequence_id = session.getSequenceID();
            bind.wrap();
            SGIPPacket packet = ((SGIPSessionEngine)session).request(bind);
            if(packet == null)
                return false;
            response = new SGIPBindResponse(packet);
            response.unwrap();
            if(response.result == 0)
            {
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("SGIPGatewayEngine(" + m_strName + ").login : unexpected exit !", 0x2000080000000000L);
        }
        session.m_bAuthenticated = false;
        return false;
    }

    public void logout(SessionEngine session)
    {
        try
        {
            SGIPUnbind unbind = new SGIPUnbind(session.getSequenceID());
            unbind.gateway_name = m_strName;
            unbind.session_id = session.m_nID;
            if(m_sp != null && m_sp.enterprise_code != null)
                if(m_sp.enterprise_code.length() <= 5)
                    unbind.node_id = 0x493e0 + Integer.parseInt(m_sp.enterprise_code);
                else
                    unbind.node_id = (int)(0xb2d05e00L + (long)Integer.parseInt(m_sp.enterprise_code));
            SGIPPacket packet = ((SGIPSessionEngine)session).request(unbind);
            if(packet == null)
                return;
            SGIPUnbindResponse response = new SGIPUnbindResponse(packet);
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("SGIPGatewayEngine(" + m_strName + ").logout : unexpected exit !", 0x2000080000000000L);
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