package LeadTone.Gateway;

import LeadTone.MD5.MD5;


/**
 * ���ص����ò����࣬ͬ�������ļ��е����ò���
 */
public class GatewayConfig
{
    /**
     * ��������
     */
    public String m_strName;
    /**
     * ��������
     */
    public int m_nType;
    /**
     * ��Ϣת���̵߳��߳���
     */
    public int m_nExchanger;
    /**
     * �������ӵķ�����IP��ַ
     */
    public String m_strHost;
    /**
     * ���ذ󶨵�ַ
     */
    public String m_strBind;
    /**
     * ��ҵ����
     */
    public String enterprise_code;
    /**
     * �������
     */
    public String service_code;
    /**
     * ϵͳ��Ʒ��ʱ��������Ȩ���к�
     */
    public String m_strLicence;
    /**
     * �����·���Ϣ����
     */
    public int m_nSubmit;
    /**
     * �����·���Ϣ�ɹ�������
     */
    public int m_nSubmitSuccess;
    /**
     * �����·���Ϣʧ�ܵ�����
     */
    public int m_nSubmitFailure;
    /**
     * ���ؽ�����Ϣ����
     */
    public int m_nDeliver;
    /**
     * ���ؽ�����Ϣʧ�ܵ�����
     */
    public int m_nDeliverSuccess;
    /**
     * ���ؽ�����Ϣʧ�ܵ�����
     */
    public int m_nDeliverFailure;

    /**
     * ���췽����ʼ�������
     */
    public GatewayConfig()
    {
        m_strName = null;
        m_nType = 0x20000;
        m_nExchanger = 1;
        m_strHost = null;
        m_strBind = null;
        enterprise_code = null;
        service_code = null;
        m_strLicence = null;
        m_nSubmit = 0;
        m_nSubmitSuccess = 0;
        m_nSubmitFailure = 0;
        m_nDeliver = 0;
        m_nDeliverSuccess = 0;
        m_nDeliverFailure = 0;
    }

    /**
     * ����ϵͳ��Ʒ������֤��Ϣ��Ȩ��������Ȩ���кŵ�����
     * @return �������ɵļ�Ȩ���к�
     */
    public byte[] getLicence()
    {
        int nLength = 0;
        nLength += m_strName != null ? m_strName.length() : 0;
        nLength += 9;
        String strType = GatewayType.toString(m_nType);
        nLength += strType != null ? strType.length() : 0;
        nLength = (nLength += 9) + (m_strHost != null ? m_strHost.length() : 0);
        nLength = (nLength += 9) + (m_strBind != null ? m_strBind.length() : 0);
        nLength += 9;
        String strSecret = enterprise_code;
        nLength += strSecret.length();
        byte bBytes[] = new byte[nLength];
        int nOffset = 0;
        if(m_strName != null)
        {
            System.arraycopy(m_strName.getBytes(), 0, bBytes, nOffset, m_strName.length());
            nOffset += m_strName.length();
        }
        nOffset += 9;
        if(strType != null)
        {
            System.arraycopy(strType.getBytes(), 0, bBytes, nOffset, strType.length());
            nOffset += strType.length();
        }
        nOffset += 9;
        if(m_strHost != null)
        {
            System.arraycopy(m_strHost.getBytes(), 0, bBytes, nOffset, m_strHost.length());
            nOffset += m_strHost.length();
        }
        nOffset += 9;
        if(m_strBind != null)
        {
            System.arraycopy(m_strBind.getBytes(), 0, bBytes, nOffset, m_strBind.length());
            nOffset += m_strBind.length();
        }
        nOffset += 9;
        System.arraycopy(strSecret.getBytes(), 0, bBytes, nOffset, strSecret.length());
        MD5 md5 = new MD5();
        md5.Init();
        md5.Update(bBytes, 0, nLength);
        return md5.Final();
    }


}
