package LeadTone.Exchanger;

import LeadTone.Engine;
import LeadTone.Gateway.GatewayEngine;

/**
 * 实现网关间不同协议格式转换的功能
 */
public class GatewayExchanger extends Engine
{
    /**
     * 配合m_nID完成线程号唯一性标识
     */
    static int m_nUniqueID = 0;
    /**
     * 消息转换线程的线程号，用于多线程时
     */
    int m_nID;
    /**
     * 源网关对象
     */
    GatewayEngine m_source;
    /**
     * 目的网关对象
     */
    GatewayEngine m_destination;

    /**
     * 构造方法初始化类变量
     * @param source 源网关对象
     * @param destination 目的网关对象
     */
    public GatewayExchanger(GatewayEngine source, GatewayEngine destination)
    {
        super("GatewayExchanger");
        m_source = null;
        m_destination = null;
        m_nID = ++m_nUniqueID;
        m_source = source;
        m_destination = destination;
    }



}
