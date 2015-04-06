package LeadTone.Center;

import LeadTone.Engine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Gateway.GatewayType;
import LeadTone.Log;
import LeadTone.XML.XMLTag;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;


/**
 * 继承自Center,系统工作模式ExchangeCenter
 * 此工作模式下支持所有协议的包括服务器工作方式，但没有数据库持久化部分及系统与外部的基于XML的接口
 * 包括中国移动、中国联通、中国网通短消息协议（CMPP，SGIP，CNGP）和短消息点对点协议（SMPP）
 */
public class ExchangeCenter extends Center
{
    /**
     * 所有服务器类网关对象集合
     */
    static Vector m_servers = new Vector();

    /**
     * 构造方法初始化类变量
     * @param pc
     * @param admin
     * @throws IOException
     */
    public ExchangeCenter(ProductConfig pc, Administrator admin)
        throws IOException
    {
        super(pc, admin);
    }

    /**
     * 输出所有服务器类网关工作状态
     * @param ps
     */
    public void dump(PrintStream ps)
    {
        super.dump(ps);
        for(int i = 0; i < m_servers.size(); i++)
        {
            GatewayEngine server = (GatewayEngine)m_servers.elementAt(i);
            server.dump(ps);
        }

    }

    /**
     * 启动所有服务器类网关
     */
    public void startupAllServers()
    {
        for(int i = 0; i < m_servers.size(); i++)
        {
            GatewayEngine server = (GatewayEngine)m_servers.elementAt(i);
            server.startup();
            if(i == m_servers.size() - 1)
                Engine.wait(server);
        }

    }

    /**
     * 关闭所有服务器类网关
     */
    public void shutdownAllServers()
    {
        for(int i = 0; i < m_servers.size(); i++)
        {
            GatewayEngine server = (GatewayEngine)m_servers.elementAt(i);
            server.shutdown();
            if(i == m_servers.size() - 1)
                Engine.wait(server);
        }

    }

    /**
     * 装载所有网关对象和服务器类网关对象
     * @param center
     * @return 返回是否装载网关、服务器类网关成功的布尔值
     */
    public static boolean loadAllGateways(ExchangeCenter center)
    {
        Vector gateways = FileConfig.getAllGateways();
        for(int i = 0; i < gateways.size(); i++)
        {
            XMLTag gateway = (XMLTag)gateways.elementAt(i);
            GatewayEngine engine = Center.loadGateway(gateway);
            if(engine == null)
            {
                Log.log("ExchangeCenter.loadAllGateways : fail to load all gateways !", 1L);
                return false;
            }
            //在此不对网关类型进行限制，所有网关均允许被装载，
            //但是如果是服务器类网关则放到服务器集合中，其他放到网关集合中
            if(GatewayType.isServer(engine.m_nType))
                m_servers.addElement(engine);
            else
                Center.m_gateways.addElement(engine);
        }

        Log.log("ExchangeCenter.loadAllGateways : success to load all gateways !", 1L);
        gateways.removeAllElements();
        return true;
    }

    /**
     * 启动所有网关和服务器类网关，
     * 与CMPPCenter模式和UnionCenter模式不同的是未启动数据库操作线程和消息转换线程，
     * 也没有启动基于XML的用于外部通信接口的线程
     */
    public void run()
    {
        try
        {
            //启动所有网关线程
            Log.log("ExchangeCenter.run : all gateways begin to startup !", 1L);
            startupAllGateways();
            Log.log("ExchangeCenter.run : thread startup !", 1L);
            //启动所有服务器类网关线程
            Log.log("ExchangeCenter.run : all servers begin to startup !", 1L);
            startupAllServers();
            Log.log("ExchangeCenter.run : thread startup !", 1L);
            m_nStatus = 1;
            for(; isRunning(); Engine.sleep())
            {
                //汇总所有网关流量数据
                statistic();
                //检查所有网关工作情况
                if(!checkGateway())
                {
                    Log.log("ExchangeCenter.run : gateway stopped unexpectedly !", 0x2000000000000001L);
                    break;
                }
                //初始化管理端口，启动管理线程
                accept();
                //如果启动状态标识关闭，则跳出循环
                if(!Center.m_bNeedTerminate)
                    continue;
                Log.log("ExchangeCenter.run : administrator has already stopped the system !", 1L);
                break;
            }

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("ExchangeCenter.run : unexpected exit !", 0x2000000000000001L);
        }
        Log.log("ExchangeCenter.run : servers begin to shutdown !", 1L);
        shutdownAllServers();
        Log.log("ExchangeCenter.run : gateways begin to shutdown !", 1L);
        shutdownAllGateways();
        m_nStatus = 3;
        Log.log("ExchangeCenter.run : thread stopped !", 1L);
    }

    /**
     * ExchangeCenter模式的系统进入点
     * @param args
     */
    public static void main(String args[])
    {
    	if(args.length < 1)
        {
            System.out.println("Usage : java LeadTone.Center.Center [config.xml] [hibernate.cfg.xml] [log4j.properties]");
            return;
        } else
        {
            Center.main(args);
            return;
        }
    }



}