package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Session.SessionConfig;


public class SMPPServerGateway extends SMPPServerGatewayEngine
{
    static final int SMPP_TRANSCEIVER_PORT = 7777;
    public SessionConfig m_scActivater;

    public SMPPServerGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x10010, sp);
        m_scActivater = null;
    }

    public boolean loadAllSessions(String strHostAddress)
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
            Log.log("SMPPServerGateway(" + m_strName + ").loadActivaterSessions : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }

    public static SessionConfig getActivater()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 7777;
        sc.m_nType = 0;
        return sc;
    }



}