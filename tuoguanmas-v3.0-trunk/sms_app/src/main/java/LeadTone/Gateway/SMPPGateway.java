package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.Session.SessionConfig;
import java.net.InetAddress;


public class SMPPGateway extends SMPPGatewayEngine
{
    static final int SMPP_TRANSMITTER_PORT = 8890;
    static final int SMPP_RECEIVER_PORT = 8890;
    public SessionConfig m_scTransmitter;
    public SessionConfig m_scReceiver;

    public SMPPGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x10000, sp);
        m_scTransmitter = null;
        m_scReceiver = null;
    }

    public boolean loadAllSessions(String strHostAddress)
    {
        if(!loadTransmitterSession(strHostAddress))
            return false;
        if(!loadReceiverSession(strHostAddress))
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
            if(m_scTransmitter != null)
            {
                SessionConfig sc = m_scTransmitter;
                sc.m_address = InetAddress.getByName(strHostAddress);
                for(int i = 0; i < sc.m_nCount; i++)
                    if(!loadSession(sc, false))
                        return false;

            }
            return true;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPGateway(" + m_strName + ").loadTransmitterSessions : unexpected exit !", 0x2000080000000000L);
        return false;
    }

    public boolean loadReceiverSession(String strHostAddress)
    {
        try
        {
            if(m_scReceiver != null)
            {
                SessionConfig sc = m_scReceiver == null ? getReceiver() : m_scReceiver;
                sc.m_address = InetAddress.getByName(strHostAddress);
                for(int i = 0; i < sc.m_nCount; i++)
                    if(!loadSession(sc, false))
                        return false;

            }
            return true;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPGateway(" + m_strName + ").loadReceiverSessions : unexpected exit !", 0x2000080000000000L);
        return false;
    }

    public static SessionConfig getTransmitter()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 8890;
        sc.m_nType = 1;
        return sc;
    }

    public static SessionConfig getReceiver()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 8890;
        sc.m_nType = 2;
        return sc;
    }

    public void setConfig(CMPPQueryResponse response)
    {
        setServiceProvider(response, m_sp);
        if(m_scTransmitter != null)
            m_scTransmitter.setConfig(response);
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
            if(m_scTransmitter != null)
                m_scTransmitter.getConfig(response);
            if(m_scReceiver != null)
                m_scReceiver.getConfig(response);
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPGateway.getConfig : unexpected exit !", 0x2000080000000000L);
        return null;
    }



}