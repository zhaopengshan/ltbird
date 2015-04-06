package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.Session.SessionConfig;
import java.net.InetAddress;

/**
 * CMPP标准协议的实现，会话类型为一个连接上同时完成发送和接收（Transceiver）
 */
public class CMPPGateway extends CMPPGatewayEngine
{
    /**
     * CMPP协议默认收发端口7890
     */
    static final int CMPP_TRANSCEIVER_PORT = 7890;
    /**
     * 会话配置信息对象
     */
    public SessionConfig m_scTransceiver;

    /**
     * 构造方法初始化类变量
     * @param strName
     * @param sp
     */
    public CMPPGateway(String strName, ServiceProvider sp)
    {
        super(strName, 0x20000, sp);
        m_scTransceiver = null;
    }

    /**
     * 装载所有会话线程
     * @param strHostAddress 远程服务器IP地址
     * @return 返回是否装载成功的布尔值
     */
    public boolean loadAllSessions(String strHostAddress)
    {
        try
        {
            //如果会话配置对象为空，则生成默认配置对象，否则根据实际配置项装载会话线程
            SessionConfig sc = m_scTransceiver == null ? getTransceiver() : m_scTransceiver;
            sc.m_address = InetAddress.getByName(strHostAddress);
            //根据配置的会话线程数装载
            for(int i = 0; i < sc.m_nCount; i++)
            //如装载失败返回false
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
     * 获取默认发送接收类型会话配置对象
     * @return 默认发送接收类型会话配置对象
     */
    public static SessionConfig getTransceiver()
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nPort = 7890;
        sc.m_nType = 3;
        return sc;
    }

    /**
     * 从查询回复中得到属性参数设置SP、会话配置
     * @param response 查询回复
     */
    public void setConfig(CMPPQueryResponse response)
    {
        setServiceProvider(response, m_sp);
        if(m_scTransceiver != null)
            m_scTransceiver.setConfig(response);
    }

    /**
     * 将网关、会话、SP信息包装入查询回复对象中
     * @return 查询回复对象
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
