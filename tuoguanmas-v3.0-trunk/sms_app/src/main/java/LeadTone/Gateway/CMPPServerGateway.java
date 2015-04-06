package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Session.SessionConfig;


/**
 * CMPP标准协议服务器模式的实现，会话类型服务器监听类型（Activater）
 */
public class CMPPServerGateway extends CMPPServerGatewayEngine
{
    /**
     * CMPP协议默认服务器端口7890
     */
    static final int CMPP_TRANSCEIVER_PORT = 7890;
    /**
     * 会话配置信息对象
     */
    public SessionConfig m_scActivater;


    /**
     * 构造方法初始化类变量
     * @param strName
     * @param sp
     */
    public CMPPServerGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x20010, sp);
        m_scActivater = null;
    }

    /**
     * 装载所有会话线程
     * @param strHostAddress 服务器IP地址
     * @return 返回是否装载成功的布尔值
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
     * 获取默认服务器监听类型会话配置对象
     * @return 默认服务器监听类型会话配置对象
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
