package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Packet.SMPPPacket.*;
import LeadTone.Session.*;


public class SMPPServerGatewayEngine extends SMPPGatewayEngine
{

    public SMPPServerGatewayEngine(String strName, int nType, ServiceProvider sp)
    {
        super(strName, nType, sp);
    }

    public Packet get(int nType, int nID)
    {
        SMPPPacket smpp = (SMPPPacket)m_output.peer();
        if(smpp == null)
            return null;
        if(smpp.session_id > 0 && smpp.session_id != nID)
            return null;
        if(BindType.forTransmitter(nType) && SMPPCommandID.isTransmitterInput(smpp.command_id) || BindType.forReceiver(nType) && SMPPCommandID.isReceiverInput(smpp.command_id))
        {
            m_output.pop();
            return smpp;
        } else
        {
            return null;
        }
    }

    public boolean put(int nType, Packet packet)
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
        if(BindType.forTransmitter(nType) && SMPPCommandID.isTransmitterOutput(smpp.command_id) || BindType.forReceiver(nType) && SMPPCommandID.isReceiverOutput(smpp.command_id))
            return m_input.push(smpp);
        else
            return true;
    }

    public int checkParameters(SMPPBind login, SMPPBind bind)
    {
        if(login.system_id == null && bind.system_id != null)
            return 15;
        if(login.system_id != null && (bind.system_id == null || !login.system_id.equals(bind.system_id)))
            return 15;
        if(login.password == null && bind.password != null)
            return 14;
        if(login.password != null && (bind.password == null || !login.password.equals(bind.password)))
            return 14;
        if(login.system_type == null && bind.system_type != null)
            return 83;
        return login.system_type == null || bind.system_type != null && login.system_type.equals(bind.system_type) ? 0 : 83;
    }

    public boolean bindTransmitter(SMPPBind login, SessionEngine session)
    {
        try
        {
            SMPPPacket packet = ((SMPPSessionEngine)session).readSMPPPacket();
            if(packet == null || packet.command_id != 2)
                return false;
            SMPPBindTransmitter bind = new SMPPBindTransmitter(packet);
            bind.unwrap();
            if(!bind.isValid())
                return false;
            SMPPBindTransmitterResponse response = new SMPPBindTransmitterResponse(bind.sequence_id);
            response.gateway_name = bind.gateway_name;
            response.session_id = bind.session_id;
            response.command_status = checkParameters(login, bind);
            response.wrap();
            ((SMPPSessionEngine)session).writeSMPPPacket(response);
            if(response.command_status != 0)
            {
                session.m_bAuthenticated = false;
                return false;
            } else
            {
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPServerGatewayEngine(" + m_strName + ").bindTransmitter : unexpected exit !", 0x2000080000000000L);
        session.m_bAuthenticated = false;
        return false;
    }

    public boolean bindReceiver(SMPPBind login, SessionEngine session)
    {
        try
        {
            SMPPPacket packet = ((SMPPSessionEngine)session).readSMPPPacket();
            if(packet == null || packet.command_id != 1)
                return false;
            SMPPBindReceiver bind = new SMPPBindReceiver(packet);
            bind.unwrap();
            if(!bind.isValid())
                return false;
            SMPPBindReceiverResponse response = new SMPPBindReceiverResponse(bind.sequence_id);
            response.gateway_name = bind.gateway_name;
            response.session_id = bind.session_id;
            response.command_status = checkParameters(login, bind);
            response.wrap();
            ((SMPPSessionEngine)session).writeSMPPPacket(response);
            if(response.command_status != 0)
            {
                session.m_bAuthenticated = false;
                return false;
            } else
            {
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPServerGatewayEngine(" + m_strName + ").bindReceiver : unexpected exit !", 0x2000080000000000L);
        session.m_bAuthenticated = false;
        return false;
    }

    public boolean bindTransceiver(SMPPBind login, SessionEngine session)
    {
        try
        {
            SMPPPacket packet = ((SMPPSessionEngine)session).readSMPPPacket();
            if(packet == null || packet.command_id != 9)
                return false;
            SMPPBindTransceiver bind = new SMPPBindTransceiver(packet);
            bind.unwrap();
            if(!bind.isValid())
                return false;
            SMPPBindTransceiverResponse response = new SMPPBindTransceiverResponse(bind.sequence_id);
            response.gateway_name = bind.gateway_name;
            response.session_id = bind.session_id;
            response.command_status = checkParameters(login, bind);
            response.wrap();
            ((SMPPSessionEngine)session).writeSMPPPacket(response);
            if(response.command_status != 0)
            {
                session.m_bAuthenticated = false;
                return false;
            } else
            {
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPServerGatewayEngine(" + m_strName + ").bindTransceiver : unexpected exit !", 0x2000080000000000L);
        session.m_bAuthenticated = false;
        return false;
    }

    public boolean login(SessionEngine session)
    {
        SMPPBind login = m_sp.getSMPPBind(session.m_sc.m_nType);
        if(login == null)
            return false;
        if(session.m_sc.m_nType == 1)
            return bindTransmitter(login, session);
        if(session.m_sc.m_nType == 2)
            return bindReceiver(login, session);
        if(session.m_sc.m_nType == 3)
        {
            return bindTransceiver(login, session);
        } else
        {
            session.m_bAuthenticated = false;
            return false;
        }
    }
}