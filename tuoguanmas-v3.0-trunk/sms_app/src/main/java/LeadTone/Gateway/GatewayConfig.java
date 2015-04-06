package LeadTone.Gateway;

import LeadTone.MD5.MD5;


/**
 * 网关的配置参数类，同步配置文件中的配置参数
 */
public class GatewayConfig
{
    /**
     * 网关名称
     */
    public String m_strName;
    /**
     * 网关类型
     */
    public int m_nType;
    /**
     * 消息转换线程的线程数
     */
    public int m_nExchanger;
    /**
     * 建立连接的服务器IP地址
     */
    public String m_strHost;
    /**
     * 本地绑定地址
     */
    public String m_strBind;
    /**
     * 企业代码
     */
    public String enterprise_code;
    /**
     * 服务代码
     */
    public String service_code;
    /**
     * 系统产品化时的启动鉴权序列号
     */
    public String m_strLicence;
    /**
     * 网关下发消息总数
     */
    public int m_nSubmit;
    /**
     * 网关下发消息成功的数量
     */
    public int m_nSubmitSuccess;
    /**
     * 网关下发消息失败的数量
     */
    public int m_nSubmitFailure;
    /**
     * 网关接收消息总数
     */
    public int m_nDeliver;
    /**
     * 网关接收消息失败的数量
     */
    public int m_nDeliverSuccess;
    /**
     * 网关接收消息失败的数量
     */
    public int m_nDeliverFailure;

    /**
     * 构造方法初始化类变量
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
     * 用于系统产品化，保证信息产权，启动鉴权序列号的生成
     * @return 返回生成的鉴权序列号
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
