package LeadTone.Session;

import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.TimeConfig;
import java.net.InetAddress;


/**
 * 会话类型及参数，以及提供向服务器查询获取系统工作属性参数的方法
 */
public class SessionConfig extends BindType
{
    /**
     * 消息队列的默认最大容量默认值
     */
    public static final int DEFAULT_PACKET_QUEUE_SIZE = 128;
    /**
     * 会话中最大错误数默认值
     */
    public static final int DEFAULT_MAX_ERROR_COUNT = 3;
    /**
     * 会话中流量值默认值
     */
    public static final int DEFAULT_FLUX_CONTROL = 65535;
    /**
     * 会话中流量总数
     */
    public int m_nCount;
    /**
     * 建立会话连接的IP地址
     */
    public InetAddress m_address;
    /**
     * 建立会话连接的端口号
     */
    public int m_nPort;
    /**
     * 会话类型，参考父类BindType
     */
    public int m_nType;
    /**
     * 消息包超时时限
     */
    public long m_lPacketTimeout;
    /**
     * 下发短信超时时限
     */
    public long m_lMessageTimeout;
    /**
     * 保持连接消息超时时限
     */
    public long m_lActiveTestTimeout;
    /**
     * 会话容许的最大错误数
     */
    public int m_nMaxErrorCount;
    /**
     * 消息队列的容量
     */
    public int m_nQueueSize;
    /**
     * 会话中流量的最大值
     */
    public int m_nMaxFlux;
    /**
     * 会话中流量的峰值
     */
    public int m_nPeakFlux;
    /**
     * 会话中流量的当前值
     */
    public int m_nCurrFlux;

    /**
     * 构造方法初始化类变量
     */
    public SessionConfig()
    {
        m_nCount = 1;
        m_address = null;
        m_nPort = 7890;
        m_nType = 1;
        m_lPacketTimeout = TimeConfig.DEFAULT_PACKET_TIMEOUT;
        m_lMessageTimeout = TimeConfig.DEFAULT_MESSAGE_TIMEOUT;
        m_lActiveTestTimeout = TimeConfig.DEFAULT_ACTIVETEST_TIMEOUT;
        m_nMaxErrorCount = 3;
        m_nQueueSize = 128;
        m_nMaxFlux = 65535;
        m_nPeakFlux = 0;
        m_nCurrFlux = 0;
    }

    /**
     * 构造方法初始化类变量
     * @param nType 会话类型代码
     * @param address 建立会话连接的IP地址
     * @param nPort 建立会话连接的端口号
     */
    public SessionConfig(int nType, InetAddress address, int nPort)
    {
        m_nCount = 1;
        m_address = null;
        m_nPort = 7890;
        m_nType = 1;
        m_lPacketTimeout = TimeConfig.DEFAULT_PACKET_TIMEOUT;
        m_lMessageTimeout = TimeConfig.DEFAULT_MESSAGE_TIMEOUT;
        m_lActiveTestTimeout = TimeConfig.DEFAULT_ACTIVETEST_TIMEOUT;
        m_nMaxErrorCount = 3;
        m_nQueueSize = 128;
        m_nMaxFlux = 65535;
        m_nPeakFlux = 0;
        m_nCurrFlux = 0;
        m_nType = nType;
        m_address = address;
        m_nPort = nPort;
    }

    /**
     * 参考父类同名方法
     */
    public boolean isTransmitter()
    {
        return (m_nType & 3) == 1;
    }

    /**
     * 参考父类同名方法
     */
    public boolean isReceiver()
    {
        return (m_nType & 3) == 2;
    }

    /**
     * 参考父类同名方法
     */
    public boolean isTransceiver()
    {
        return (m_nType & 3) == 3;
    }

    /**
     * 参考父类同名方法
     */
    public boolean isActivater()
    {
        return (m_nType & 3) == 0;
    }

    /**
     * 将系统的支持的属性配置包装为查询回复(QueryResponse)
     * 用于系统充当服务器时向客户端返回查询结果
     * @param response  查询结果回复
     */
    public void getConfig(CMPPQueryResponse response)
    {
        if(m_nType == 3)
            getTransceiverConfig(response);
        else
        if(m_nType == 1)
            getTransmitterConfig(response);
        else
        if(m_nType == 2)
            getReceiverConfig(response);
        else
        if(m_nType == 0)
            getActivaterConfig(response);
    }

    /**
     * 根据查询结果回复设置系统属性配置
     * 用于系统充当客户端时根据查询结果更新自己的工作属性
     * @param response  查询结果回复
     */
    public void setConfig(CMPPQueryResponse response)
    {
        if(m_nType == 3)
            setTransceiverConfig(response);
        else
        if(m_nType == 1)
            setTransmitterConfig(response);
        else
        if(m_nType == 2)
            setReceiverConfig(response);
        else
        if(m_nType == 0)
            setActivaterConfig(response);
    }

    /**
     * 参考getConfig(CMPPQueryResponse response)方法
     * @param response
     */
    public void getTransceiverConfig(CMPPQueryResponse response)
    {
        response.MT_TLUsr = 3;
        response.MT_TLMsg = 3;
        response.MT_Scs = m_nPort;
        response.MT_WT = m_nCount;
        response.MT_FL = m_nMaxFlux;
        response.MO_Scs = m_nPort;
        response.MO_WT = m_nCount;
        response.MO_FL = m_nMaxFlux;
    }

    /**
     * 参考getConfig(CMPPQueryResponse response)方法
     * @param response
     */
    public void setTransceiverConfig(CMPPQueryResponse response)
    {
        m_nType = 3;
        if(response.MT_TLUsr == 3)
        {
            m_nPort = response.MT_Scs;
            m_nCount = response.MT_WT;
            m_nMaxFlux = response.MT_FL;
        } else
        if(response.MT_TLMsg == 3)
        {
            m_nPort = response.MO_Scs;
            m_nCount = response.MO_WT;
            m_nMaxFlux = response.MO_FL;
        }
    }

    /**
     * 参考getConfig(CMPPQueryResponse response)方法
     * @param response
     */
    public void getTransmitterConfig(CMPPQueryResponse response)
    {
        response.MT_TLUsr = 1;
        response.MT_Scs = m_nPort;
        response.MT_WT = m_nCount;
        response.MT_FL = m_nMaxFlux;
    }

    /**
     * 参考setConfig(CMPPQueryResponse response)方法
     * @param response
     */
    public void setTransmitterConfig(CMPPQueryResponse response)
    {
        m_nType = 1;
        m_nPort = response.MT_Scs;
        m_nCount = response.MT_WT;
        m_nMaxFlux = response.MT_FL;
    }

    /**
     * 参考setConfig(CMPPQueryResponse response)方法
     * @param response
     */
    public void getReceiverConfig(CMPPQueryResponse response)
    {
        response.MT_TLMsg = 2;
        response.MO_Scs = m_nPort;
        response.MO_WT = m_nCount;
        response.MO_FL = m_nMaxFlux;
    }

    /**
     * 参考setConfig(CMPPQueryResponse response)方法
     * @param response
     */
    public void setReceiverConfig(CMPPQueryResponse response)
    {
        m_nType = 2;
        m_nPort = response.MO_Scs;
        m_nCount = response.MO_WT;
        m_nMaxFlux = response.MO_FL;
    }

    /**
     * 参考getConfig(CMPPQueryResponse response)方法
     * @param response
     */
    public void getActivaterConfig(CMPPQueryResponse response)
    {
        response.MT_TLMsg = 0;
        response.MO_Scs = m_nPort;
        response.MO_WT = m_nCount;
        response.MO_FL = m_nMaxFlux;
    }

    /**
     * 参考setConfig(CMPPQueryResponse response)方法
     * @param response
     */
    public void setActivaterConfig(CMPPQueryResponse response)
    {
        m_nType = 0;
        m_nPort = response.MO_Scs;
        m_nCount = response.MO_WT;
        m_nMaxFlux = response.MO_FL;
    }



}
