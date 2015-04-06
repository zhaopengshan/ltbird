package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Session.SessionConfig;


/**
 * CMPP��׼Э�������ģʽ��ʵ�֣��Ự���ͷ������������ͣ�Activater��
 */
public class CMPPServerGateway extends CMPPServerGatewayEngine
{
    /**
     * CMPPЭ��Ĭ�Ϸ������˿�7890
     */
    static final int CMPP_TRANSCEIVER_PORT = 7890;
    /**
     * �Ự������Ϣ����
     */
    public SessionConfig m_scActivater;


    /**
     * ���췽����ʼ�������
     * @param strName
     * @param sp
     */
    public CMPPServerGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x20010, sp);
        m_scActivater = null;
    }

    /**
     * װ�����лỰ�߳�
     * @param strHostAddress ������IP��ַ
     * @return �����Ƿ�װ�سɹ��Ĳ���ֵ
     */
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
            Log.log("CMPPServerGateway(" + m_strName + ").loadActivaterSessions : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }

    /**
     * ��ȡĬ�Ϸ������������ͻỰ���ö���
     * @return Ĭ�Ϸ������������ͻỰ���ö���
     */
    public static SessionConfig getActivater()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = 1;
        sc.m_nPort = 7890;
        sc.m_nType = 0;
        return sc;
    }



}
