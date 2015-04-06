package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.Session.SessionConfig;
import java.net.InetAddress;

/**
 * CMPP��׼Э���ʵ�֣��Ự����Ϊһ��������ͬʱ��ɷ��ͺͽ��գ�Transceiver��
 */
public class CMPPGateway extends CMPPGatewayEngine
{
    /**
     * CMPPЭ��Ĭ���շ��˿�7890
     */
    static final int CMPP_TRANSCEIVER_PORT = 7890;
    /**
     * �Ự������Ϣ����
     */
    public SessionConfig m_scTransceiver;

    /**
     * ���췽����ʼ�������
     * @param strName
     * @param sp
     */
    public CMPPGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x20000, sp);
        m_scTransceiver = null;
    }

    /**
     * װ�����лỰ�߳�
     * @param strHostAddress Զ�̷�����IP��ַ
     * @return �����Ƿ�װ�سɹ��Ĳ���ֵ
     */
    public boolean loadAllSessions(String strHostAddress)
    {
        try
        {
            //����Ự���ö���Ϊ�գ�������Ĭ�����ö��󣬷������ʵ��������װ�ػỰ�߳�
            SessionConfig sc = m_scTransceiver == null ? getTransceiver() : m_scTransceiver;
            sc.m_address = InetAddress.getByName(strHostAddress);
            //�������õĻỰ�߳���װ��
            for(int i = 0; i < sc.m_nCount; i++)
            //��װ��ʧ�ܷ���false
                if(!loadSession(sc, false))
                    return false;

            return true;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPGateway(" + m_strName + ").loadAllSessions : unexpected exit !", 0x2000080000000000L);
        return false;
    }

    /**
     * ��ȡĬ�Ϸ��ͽ������ͻỰ���ö���
     * @return Ĭ�Ϸ��ͽ������ͻỰ���ö���
     */
    public static SessionConfig getTransceiver()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nPort = 7890;
        sc.m_nType = 3;
        return sc;
    }

    /**
     * �Ӳ�ѯ�ظ��еõ����Բ�������SP���Ự����
     * @param response ��ѯ�ظ�
     */
    public void setConfig(CMPPQueryResponse response)
    {
        setServiceProvider(response, m_sp);
        if(m_scTransceiver != null)
            m_scTransceiver.setConfig(response);
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
            if(m_scTransceiver != null)
                m_scTransceiver.getConfig(response);
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPGateway.getConfig : unexpected exit !", 0x2000080000000000L);
        return null;
    }



}
