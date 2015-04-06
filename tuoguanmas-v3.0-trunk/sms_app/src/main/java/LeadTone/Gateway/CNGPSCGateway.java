package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.Session.SessionConfig;
import java.net.InetAddress;


public class CNGPSCGateway extends CNGPGatewayEngine
{
    static final int CNGP_TRANSCEIVER_PORT = 9890;
    public SessionConfig m_scTransceiver;

    public CNGPSCGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x40001, sp);
        m_scTransceiver = null;
    }

    public boolean loadAllSessions(String strHostAddress)
    {
        return loadTransceiverSession(strHostAddress);
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
        Log.log("NOKIAGateway(" + m_strName + ").loadTrasnsceiverSessions : unexpected exit !", 0x2000080000000000L);
        return false;
    }

    public static SessionConfig getTransceiver()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 9890;
        sc.m_nType = 3;
        return sc;
    }

    public void setConfig(CMPPQueryResponse response)
    {
        setServiceProvider(response, m_sp);
        m_scTransceiver.setConfig(response);
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
            m_scTransceiver.getConfig(response);
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("NOKIACMPPGateway.getConfig : unexpected exit !", 0x2000080000000000L);
        return null;
    }



}