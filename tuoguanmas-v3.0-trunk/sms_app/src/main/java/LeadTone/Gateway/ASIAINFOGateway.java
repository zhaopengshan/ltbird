package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.Session.SessionConfig;
import java.net.InetAddress;


/**
 * 亚信对CMPP协议的实现，发送和接收分别为一个独立连接
 */
public class ASIAINFOGateway extends CMPPGatewayEngine
{
    /**
     * 亚信默认发送连接服务器端口号 7890
     */
    static final int ASIAINFO_TRANSMITTER_PORT = 7890;
    /**
     * 亚信默认接收连接服务器端口号 7890
     */
    static final int ASIAINFO_RECEIVER_PORT = 7890;
    /**
     * 发送会话配置
     */
    public SessionConfig m_scTransmitter;
    /**
     * 接收会话配置
     */
    public SessionConfig m_scReceiver;

    /**
     * 构造方法初始化类变量
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
     * 装载所有会话线程
     * @param strHostAddress
     * @return 返回是否装载成功
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
     * 装载发送会话线程
     * @param strHostAddress
     * @return  返回是否装载成功
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
     * 装载接收会话线程
     * @param strHostAddress
     * @return 返回是否装载成功
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
     * 获取默认发送类型会话配置对象
     * @return 默认发送类型会话配置对象
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
     * 获取默认接收类型会话配置对象
     * @return 默认接收类型会话配置对象
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
     * 从查询回复中得到属性参数设置SP、会话配置
     * @param response 查询回复
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
