package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.Session.SessionConfig;
import java.net.InetAddress;


public class INTRINSICGateway extends CMPPGatewayEngine
{
    static final int INTRINSIC_TRANSMITTER_PORT = 7890;
    static final int INTRINSIC_ACTIVATER_PORT = 7900;
    public SessionConfig m_scTransmitter;
    public SessionConfig m_scActivater;

    public INTRINSICGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x20300, sp);
        m_scTransmitter = null;
        m_scActivater = null;
    }

    public boolean loadAllSessions(String strHostAddress)
    {
        if(!loadActivaterSession())
            return false;
        if(!loadTransmitterSession(strHostAddress))
        {
            closeAllSessions();
            return false;
        } else
        {
            return true;
        }
    }

    public boolean loadTransmitterSession(String strHostAddress)
    {
        try
        {
            SessionConfig sc = m_scTransmitter == null ? getTransmitter() : m_scTransmitter;
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
        Log.log("INTRINSICGateway(" + m_strName + ").loadTransmitterSessions : unexpected exit !", 0x2000080000000000L);
        return false;
    }

    public boolean loadActivaterSession()
    {
        try
        {
            SessionConfig sc = m_scActivater == null ? getActivater() : m_scActivater;
            if(loadSession(sc, false))
                return true;
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("INTRINSICGateway(" + m_strName + ").loadActivaterSessions : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }

    public static SessionConfig getTransmitter()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 7890;
        sc.m_nType = 1;
        return sc;
    }

    public static SessionConfig getActivater()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 8;
        sc.m_nPort = 7900;
        sc.m_nType = 0;
        sc.m_lActiveTestTimeout = 0L;
        return sc;
    }

    public void setConfig(CMPPQueryResponse response)
    {
        setServiceProvider(response, m_sp);
        if(m_scTransmitter != null)
            m_scTransmitter.setConfig(response);
        if(m_scActivater != null)
            m_scActivater.setConfig(response);
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
            if(m_scTransmitter != null)
                m_scTransmitter.getConfig(response);
            if(m_scActivater != null)
                m_scActivater.getConfig(response);
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("INTRINSICGateway.getConfig : unexpected exit !", 0x2000080000000000L);
        return null;
    }



}
