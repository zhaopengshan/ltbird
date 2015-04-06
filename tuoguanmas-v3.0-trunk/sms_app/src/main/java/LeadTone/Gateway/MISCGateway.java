package LeadTone.Gateway;

import java.net.InetAddress;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.Session.SessionConfig;


public class MISCGateway extends CMPPGatewayEngine
{
    static final int MISC_TRANSCEIVER_PORT = 7890;
    static final int MISC_RECEIVER_PORT = 7891;
    public SessionConfig m_scTransceiver;
    public SessionConfig m_scReceiver;

    public MISCGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x20800, sp);
        m_scTransceiver = null;
        m_scReceiver = null;
    }

    public boolean loadAllSessions(String strHostAddress)
    {
        if(!loadTransceiverSession(strHostAddress))
        {
            closeAllSessions();
            return false;
        }
        if(!loadReceiverSession(strHostAddress))
        {
            closeAllSessions();
            return false;
        } else
        {
            return true;
        }
    }

    public boolean loadTransceiverSession(String strHostAddress)
    {
        try
        {
            SessionConfig sc = m_scTransceiver == null ? getTransceiver() : m_scTransceiver;
            sc.m_address = InetAddress.getByName(strHostAddress);
            for(int i = 0; i < sc.m_nCount; i++)
                if(!loadSession(sc, false))
                    return false;

            return true;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("MISCGateway(" + m_strName + ").loadTransceiverSessions : unexpected exit !", 0x2000080000000000L);
        return false;
    }

    public boolean loadReceiverSession(String strHostAddress)
    {
        try
        {
            SessionConfig sc = m_scReceiver == null ? getReceiver() : m_scReceiver;
            sc.m_address = InetAddress.getByName(strHostAddress);
            for(int i = 0; i < sc.m_nCount; i++)
                if(!loadSession(sc, false))
                    return false;

            return true;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("MISCGateway(" + m_strName + ").loadReceiverSessions : unexpected exit !", 0x2000080000000000L);
        return false;
    }

    public static SessionConfig getTransceiver()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 7890;
        sc.m_nType = 3;
        return sc;
    }

    public static SessionConfig getReceiver()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 7891;
        sc.m_nType = 2;
        return sc;
    }

    public void setConfig(CMPPQueryResponse response)
    {
        setServiceProvider(response, m_sp);
        if(m_scTransceiver != null)
            m_scTransceiver.setConfig(response);
        if(m_scReceiver != null)
            m_scReceiver.setConfig(response);
    }

    public CMPPQueryResponse getConfig()
    {
        try
        {
            CMPPQueryResponse response = new CMPPQueryResponse(0);
            response.gateway_name = m_strName;
            response.session_id = 0;
            response.guid = 0L;
            response.query_time = GatewayType.toString(m_gc.m_nType);
            response.query_type = 4;
            getServiceProvider(response, m_sp);
            if(m_scTransceiver != null)
                m_scTransceiver.getConfig(response);
            if(m_scReceiver != null)
                m_scReceiver.getConfig(response);
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("MISCGateway.getConfig : unexpected exit !", 0x2000080000000000L);
        return null;
    }



}
