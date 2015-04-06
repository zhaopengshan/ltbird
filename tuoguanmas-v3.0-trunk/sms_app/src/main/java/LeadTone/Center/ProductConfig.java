package LeadTone.Center;

import LeadTone.Log;

/**
 * 系统工作模式和产品信息配置类
 */
public class ProductConfig
{
    /**
     * 系统工作模式 CMPPCenter，UnionCenter，ExchangeCenter
     */
    String m_strType;
    /**
     * 版本信息
     */
    String m_strVersion;
    /**
     * 发布信息
     */
    String m_strRelease;
    /**
     * 授权信息
     */
    String m_strAuthorization;
    /**
     * 缩写
     */
    String m_strAbbreviation;
    /**
     * 公司网站
     */
    String m_strCompanySite;
    /**
     * 技术网站
     */
    String m_strTechnologySite;
    /**
     * 电话
     */
    String m_strTelephone;
    /**
     * 邮件地址
     */
    String m_strEMail;
    /**
     * 注释
     */
    String m_strComment;

    /**
     * 构造方法初始化类变量
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
     * 输出产品信息
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
