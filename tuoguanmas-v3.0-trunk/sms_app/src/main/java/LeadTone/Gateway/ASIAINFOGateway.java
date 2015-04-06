package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.Session.SessionConfig;
import java.net.InetAddress;


/**
 * ���Ŷ�CMPPЭ���ʵ�֣����ͺͽ��շֱ�Ϊһ����������
 */
public class ASIAINFOGateway extends CMPPGatewayEngine
{
    /**
     * ����Ĭ�Ϸ������ӷ������˿ں� 7890
     */
    static final int ASIAINFO_TRANSMITTER_PORT = 7890;
    /**
     * ����Ĭ�Ͻ������ӷ������˿ں� 7890
     */
    static final int ASIAINFO_RECEIVER_PORT = 7890;
    /**
     * ���ͻỰ����
     */
    public SessionConfig m_scTransmitter;
    /**
     * ���ջỰ����
     */
    public SessionConfig m_scReceiver;

    /**
     * ���췽����ʼ�������
     * @param strName
     * @param sp
     */
    public ASIAINFOGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x20200, sp);
        m_scTransmitter = null;
        m_scReceiver = null;
    }

    /**
     * װ�����лỰ�߳�
     * @param strHostAddress
     * @return �����Ƿ�װ�سɹ�
     */
    public boolean loadAllSessions(String strHostAddress)
    {
        if(!loadTransmitterSession(strHostAddress))
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

    /**
     * װ�ط��ͻỰ�߳�
     * @param strHostAddress
     * @return  �����Ƿ�װ�سɹ�
     */
    public boolean loadTransmitterSession(String strHostAddress)
    {
        try
        {



            if (m_scTransmitter == null){   // for asiainfo gateway only starting  send process or receive process
             return true;
            }
            else{
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
        Log.log("ASIAINFOGateway(" + m_strName + ").loadTransmitterSessions : unexpected exit !", 0x2000080000000000L);
        return false;
    }

    /**
     * װ�ؽ��ջỰ�߳�
     * @param strHostAddress
     * @return �����Ƿ�װ�سɹ�
     */
    public boolean loadReceiverSession(String strHostAddress)
    {

        try
        {

            if (m_scReceiver == null){   // for asiainfo gateway only starting  send process or receive process
                return true;
            }else{
            SessionConfig sc = m_scReceiver;
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
        Log.log("ASIAINFOGateway(" + m_strName + ").loadReceiverSessions : unexpected exit !", 0x2000080000000000L);
        return false;
    }

    /**
     * ��ȡĬ�Ϸ������ͻỰ���ö���
     * @return Ĭ�Ϸ������ͻỰ���ö���
     */
    public static SessionConfig getTransmitter()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 7890;
        sc.m_nType = 1;
        return sc;
    }

    /**
     * ��ȡĬ�Ͻ������ͻỰ���ö���
     * @return Ĭ�Ͻ������ͻỰ���ö���
     */
    public static SessionConfig getReceiver()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 7890;
        sc.m_nType = 2;
        sc.m_lActiveTestTimeout = 0L;
        return sc;
    }

    /**
     * �Ӳ�ѯ�ظ��еõ����Բ�������SP���Ự����
     * @param response ��ѯ�ظ�
     */
    public void setConfig(CMPPQueryResponse response)
    {
        setServiceProvider(response, m_sp);
        if(m_scTransmitter != null)
            m_scTransmitter.setConfig(response);
        if(m_scReceiver != null)
            m_scReceiver.setConfig(response);
    }

    /**
     * �����ء��Ự��SP��Ϣ��װ���ѯ�ظ�������
     * @return ��ѯ�ظ�����
     */
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
        Log.log("ASIAINFOGateway.getConfig : unexpected exit !", 0x2000080000000000L);
        return null;
    }



}
