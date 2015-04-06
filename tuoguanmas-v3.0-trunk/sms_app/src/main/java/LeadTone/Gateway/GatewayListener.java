package LeadTone.Gateway;

import LeadTone.Engine;
import LeadTone.Log;

/**
 * 网关用监听器，用于监听连接端口
 */
public class GatewayListener extends Engine
{
    /**
     * 建立监听器的网关对象
     */
     GatewayEngine m_gateway;

    /**
     * 构造方法初始化类变量
     * @param gateway
     */
    public GatewayListener(GatewayEngine gateway)
    {
        super("GatewayListener");
        m_gateway = null;
        m_gateway = gateway;
    }

    /**
     * 监听器线程，检查所属网关是否工作正常，监听连接端口等待建立连接
     */
    public void run()
    {
        try
        {
            Log.log("GatewayListener(" + m_gateway.m_strName + ").run : thread startup !", 0x80000000000L);
            m_nStatus = 1;

            for(; isRunning() && m_gateway.isBeginToRunning(); Engine.nap())
                if(m_gateway.m_socket != null)
                    m_gateway.accept();

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("GatewayListener.run(" + m_gateway.m_strName + ") : unexpected exit !", 0x2000080000000000L);
        }
        m_nStatus = 3;
        Log.log("GatewayListener(" + m_gateway.m_strName + ").run : thread stopped !", 0x80000000000L);
    }


}
