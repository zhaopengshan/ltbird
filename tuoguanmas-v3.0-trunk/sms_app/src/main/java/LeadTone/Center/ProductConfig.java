package LeadTone.Center;

import LeadTone.Log;

/**
 * ϵͳ����ģʽ�Ͳ�Ʒ��Ϣ������
 */
public class ProductConfig
{
    /**
     * ϵͳ����ģʽ CMPPCenter��UnionCenter��ExchangeCenter
     */
    String m_strType;
    /**
     * �汾��Ϣ
     */
    String m_strVersion;
    /**
     * ������Ϣ
     */
    String m_strRelease;
    /**
     * ��Ȩ��Ϣ
     */
    String m_strAuthorization;
    /**
     * ��д
     */
    String m_strAbbreviation;
    /**
     * ��˾��վ
     */
    String m_strCompanySite;
    /**
     * ������վ
     */
    String m_strTechnologySite;
    /**
     * �绰
     */
    String m_strTelephone;
    /**
     * �ʼ���ַ
     */
    String m_strEMail;
    /**
     * ע��
     */
    String m_strComment;

    /**
     * ���췽����ʼ�������
     */
    public ProductConfig()
    {
        m_strType = "union";
        m_strVersion = "SmsGateway2.11";
        m_strRelease = "2006-03-13 15:43:05";
        m_strAuthorization = "LeadTone";
        m_strAbbreviation = "LeadTone";
        m_strCompanySite = "www.leadtone.com";
        m_strTechnologySite = "www.leadtone.com";
        m_strTelephone = "010-58635700";
        m_strEMail = "sunlei@corp.leadtone.com";
        m_strComment = "LeadTone";
    }

    /**
     * �����Ʒ��Ϣ
     * @param lMethod
     */
    public void dump(long lMethod)
    {
        Log.log("\tProductConfig : " + m_strVersion, 1L | lMethod);
        Log.log("\tVersion : " + m_strVersion, 1L | lMethod);
        Log.log("\tRelease : " + m_strRelease, 1L | lMethod);
        Log.log("\tCompany Site : " + m_strCompanySite, 1L | lMethod);
        Log.log("\tTechnology Site : " + m_strTechnologySite, 1L | lMethod);
        Log.log("\tTelephone : " + m_strTelephone, 1L | lMethod);
        Log.log("\tNote : " + m_strComment, 1L | lMethod);
    }


}
