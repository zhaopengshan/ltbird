package LeadTone.Gateway;

import LeadTone.*;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.Packet;
import LeadTone.Session.*;
import LeadTone.Engine;
import LeadTone.TimeConfig;
import LeadTone.Utility;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.Vector;

/**
 * 网关线程类，连接消息队列到最终发送接收会话的管理类，
 * 其子类实现具体协议差异，实现差异的具体细节
 */
public class GatewayEngine extends Engine
{
    /**
     * 消息队列的默认容量值
     */
    static final int DEFAULT_PACKET_QUEUE_SIZE = 0;
    /**
     * 网关名称
     */
    public String m_strName;
    /**
     * 网关类型
     */
    public int m_nType;
    /**
     * 网关的配置信息对象
     */
    public GatewayConfig m_gc;
    /**
     * 网关建立的会话集合
     */
    public Vector m_sessions;
    /**
     * 网关的最大流量
     */
    public int m_nMaxFlux;
    /**
     * 网关运行时的流量峰值
     */
    public int m_nPeakFlux;
    /**
     * 网关运行时的当前流量
     */
    public int m_nCurrFlux;
    /**
     * 网关是否支持发送的标识
     */
    public boolean m_bTransmittable;
    /**
     * 网关是否支持接收的标识
     */
    public boolean m_bReceivable;
    /**
     * 网关最近一次发生异常的时间
     */
    long m_lExceptionTime;
    /**
     * 根据协议要求的消息递送中携带的唯一序列号
     */
    public int m_SequenceID;
    /**
     * 服务器端口号
     */
    ServerSocket m_socket;
    /**
     * 会话配置属性
     */
    SessionConfig m_sc;
    /**
     * 网关监听器对象
     */
    GatewayListener m_listener;

    /**
     * 构造方法初始化类变量
     * @param strName
     * @param nType
     */
    public GatewayEngine(String strName, int nType)
    {
        super("GatewayEngine");
        m_strName = null;
        m_nType = 0;
        m_gc = null;
        m_sessions = new Vector();
        m_nMaxFlux = 0;
        m_nPeakFlux = 0;
        m_nCurrFlux = 0;
        m_bTransmittable = false;
        m_bReceivable = false;
        m_lExceptionTime = System.currentTimeMillis();
        m_SequenceID = 0;
        m_socket = null;
        m_sc = null;
        m_listener = new GatewayListener(this);
        m_strName = strName;
        m_nType = nType;
    }

    /**
     * 输出网关运行状态及隶属网关的所有会话的运行状态
     * @param ps
     */
    public void dump(PrintStream ps)
    {
        ps.print("\tgateway(" + m_strName + "," + (m_bTransmittable ? "T" : "") + (m_bReceivable ? "R" : "") + ") : " + "mt(" + m_gc.m_nSubmitSuccess + "," + (m_gc.m_nSubmit - (m_gc.m_nSubmitSuccess + m_gc.m_nSubmitFailure)) + "," + m_gc.m_nSubmitFailure + ")," + "mo(" + m_gc.m_nDeliverSuccess + "," + (m_gc.m_nDeliver - m_gc.m_nDeliverSuccess) + ",0)," + "flux(" + m_nMaxFlux + "," + m_nPeakFlux + "," + m_nCurrFlux + ")\r\n");
        for(int i = 0; i < m_sessions.size(); i++)
        {
            SessionEngine session = (SessionEngine)m_sessions.elementAt(i);
            if(session.isRunning())
                session.dump(ps);
        }

    }

    /**
     * 设置消息队列最大容量
     * @param i
     */
    public void setCapacity(int i)
    {
    }

    /**
     * 关闭服务器端口
     */
    public void close()
    {
        try
        {
            if(m_socket != null)
                m_socket.close();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("GatewayEngine(" + m_strName + ").close : unexpected exit !", 0x2000080000000000L);
        }
    }

    /**
     * 清空系统使用的所有资源
     */
    public void empty()
    {
        m_sessions.removeAllElements();
        m_sessions = null;
        m_socket = null;
    }

    /**
     * 通过会话线程的线程编号获取会话线程对象
     * @param nID
     * @return 获取的会话线程对象
     */
    public SessionEngine getSession(int nID)
    {
        for(int i = 0; i < m_sessions.size(); i++)
        {
            SessionEngine session = (SessionEngine)m_sessions.elementAt(i);
            if(session.m_nID == nID)
                return session;
        }

        return null;
    }

    /**
     * 获取循环使用的线程唯一编号，遍历所有线程，以便重新获得已经退出的线程号的再次使用权
     * @return 循环使用的唯一线程编号
     */
    public synchronized int getUniqueSessionID()
    {
        for(int i = 1; i <= m_sessions.size(); i++)
            if(getSession(i) == null)
                return i;

        return m_sessions.size() + 1;
    }

    /**
     * 检查异常是否超时
     * @return 返回异常是否超时是否超时的布尔值
     */
    public boolean isExceptionTimeout()
    {
        return System.currentTimeMillis() - m_lExceptionTime > TimeConfig.DEFAULT_EXCEPTION_TIMEOUT;
    }

    /**
     * 重置网关最近一次的异常时间
     */
    public void delayExceptionTimeout()
    {
        m_lExceptionTime = System.currentTimeMillis();
    }

    /**
     * 设置服务器端口号，并且设置端口超时时间
     * @param nListen 端口号
     * @return  是否设置成功
     * @throws IOException
     */
    public boolean setListen(int nListen)
        throws IOException
    {
        if(nListen > 0)
        {
            try
            {
                m_socket = new ServerSocket(nListen);
                m_socket.setSoTimeout((int)TimeConfig.DEFAULT_LISTEN_TIMEOUT);
                return true;
            }
            catch(Exception e)
            {
                Log.log(e);
            }
            Log.log("GatewayEngine(" + m_strName + ").setListen : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }

    /**
     * 装载会话线程对象
     * @param sc 会话线程的配置对象
     * @param bNeedStartup  是否需要启动
     * @return  返回是否装载成功的布尔值
     */
    public boolean loadSession(SessionConfig sc, boolean bNeedStartup)
    {
        return false;
    }

    /**
     * 关闭所有会话线程
     */
    public void closeAllSessions()
    {
        for(int i = 0; i < m_sessions.size(); i++)
        {
            SessionEngine session = (SessionEngine)m_sessions.elementAt(i);
            session.close();
        }

    }

    /**
     * 启动所有会话线程
     */
    public void startupAllSessions()
    {
        for(int i = 0; i < m_sessions.size(); i++)
        {
            SessionEngine session = (SessionEngine)m_sessions.elementAt(i);
            session.startup();
        }

    }

    /**
     * 检查所有会话，汇总所有会话流量数据
     */
    public void checkAllSessions()
    {
        boolean bTransmittable = false;
        boolean bReceivable = false;
        int nMaxFlux = 0;
        int nCurrFlux = 0;
        int nPeakFlux = 0;
        for(int i = 0; i < m_sessions.size() && isRunning(); i++)
        {
            SessionEngine session = (SessionEngine)m_sessions.elementAt(i);
            nMaxFlux += session.m_sc.m_nMaxFlux;
            //如果线程为停止状态，则判断是否为服务器模式，
            //如果是则从会话线程集合中移除，如果不是则重新装载并启动该会话线程
            if(session.isStopped())
            {
                if(session.m_sc.isActivater())
                    m_sessions.removeElement(session);
                else
                if(loadSession(session.m_sc, true))
                    m_sessions.removeElement(session);
            } else
            if(session.isRunning())
            {
                //查询会话线程中消息队列工作状态
                session.checkPacketQueue();
                //如果需要发送连接保持消息则发送
                if(session.needActiveTest())
                    enquireLink(session);
                //如果该会话支持发送功能则更新网关的属性也为支持发送
                if(BindType.forTransmitter(session.m_sc.m_nType))
                    bTransmittable = true;
                //如果该会话支持接收功能则更新网关的属性也为支持接收
                if(BindType.forReceiver(session.m_sc.m_nType))
                    bReceivable = true;
                //汇总会话的流量数据
                nPeakFlux += session.m_sc.m_nPeakFlux;
                nCurrFlux += session.m_sc.m_nCurrFlux;
            }
        }
        //更新网关属性数据
        m_nMaxFlux = nMaxFlux;
        m_nPeakFlux = nPeakFlux;
        m_nCurrFlux = nCurrFlux;
        m_bTransmittable = bTransmittable;
        m_bReceivable = bReceivable;
        //更新最近一次异常时间标识
        if(!GatewayType.isServer(m_nType))
        {
            if(m_bTransmittable || m_bReceivable)
                m_lExceptionTime = System.currentTimeMillis();
        } else
        {
            m_lExceptionTime = System.currentTimeMillis();
        }
    }

    /**
     * 关闭所有会话线程
     */
    public void shutdownAllSessions()
    {
        m_bTransmittable = false;
        m_bReceivable = false;
        for(int i = 0; i < m_sessions.size(); i++)
        {
            SessionEngine session = (SessionEngine)m_sessions.elementAt(i);
            session.shutdown();
            if(i == m_sessions.size() - 1)
                Engine.wait(session);
        }

    }


    public void startupAll()
    {
    }

    public void shutdownAll()
    {
    }

    /**
     * 参照子类方法实现
     */
    public void checkAll()
    {
    }

    /**
     * 参照子类方法实现
     */
    public boolean login(SessionEngine session)
    {
        return false;
    }

    /**
     * 参照子类方法实现
     */
    public void logout(SessionEngine session)
    {
        session.m_bAuthenticated = false;
    }

    /**
     * 参照子类方法实现
     */
    public void enquireLink(SessionEngine sessionengine)
    {
    }

    /**
     * 参照子类方法实现
     */
    public void accept()
    {
    }

    /**
     * 网关线程，启动所有会话线程，启动监听器线程，
     * 检查会话线程和监听器线程
     */
    public void run()
    {
        try
        {
            Log.log("GatewayEngine(" + m_strName + ").run : begin startup all session engines !", 0x80000000000L);
            //启动所有会话线程
            startupAllSessions();
            startupAll();
            
            //启动端口监听线程
            Log.log("GatewayEngine(" + m_strName + ").run : begin startup gateway listener !", 0x80000000000L);
            m_listener.startup();
            Engine.wait(m_listener);
            
            Log.log("GatewayEngine(" + m_strName + ").run : thread startup !", 0x80000000000L);
            m_nStatus = 1;
            for(; isRunning(); sleep())
            {
                //设置最大容量限制为峰值加一
                setCapacity(m_nPeakFlux + 1);
                //检查所会话线程
                checkAllSessions();
                checkAll();
                //如果监听器线程停止跳出循环
                if(!m_listener.isStopped())
                    continue;
                Log.log("GatewayEngine.run(" + m_strName + ") : listener stopped !", 0x2000080000000000L);
                break;
            }

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("GatewayEngine.run(" + m_strName + ") : unexpected exit !", 0x2000080000000000L);
        }
        m_listener.shutdown();
        Log.log("GatewayEngine(" + m_strName + ").run : begin shutdown all session engines !", 0x80000000000L);
        shutdownAll();
        shutdownAllSessions();
        close();
        empty();
        m_nStatus = 3;
        Log.log("GatewayEngine.run(" + m_strName + ") : thread stopped !", 0x80000000000L);
        
    }

    /**
     * 参照子类方法实现
     */
    public CMPPQueryResponse getConfig()
    {
        return null;
    }

    /**
     * 参照子类方法实现
     */
    public void setConfig(CMPPQueryResponse cmppqueryresponse)
    {
    }

    /**
     *  根据SP信息，这是查询回复的内容
     * @param response
     * @param sp
     */
    public void getServiceProvider(CMPPQueryResponse response, ServiceProvider sp)
    {
        if(response == null || sp == null)
            return;
        response.query_code = "";
        if(GatewayType.isISMG(m_gc.m_nType) || GatewayType.isSGIP(m_gc.m_nType))
        {
            response.query_code += (sp.service_code != null ? sp.service_code : "") + ";";
            response.query_code += (sp.enterprise_code != null ? sp.enterprise_code : "") + ";";
            response.query_code += (sp.user != null ? sp.user : "") + ";";
            response.query_code += (sp.password != null ? sp.password : "") + ";";
        } else
        if(GatewayType.isSMSC(m_gc.m_nType))
        {
            response.query_code += (sp.system_id != null ? sp.system_id : "") + ";";
            response.query_code += (sp.system_type != null ? sp.system_type : "") + ";";
            response.query_code += (sp.password != null ? sp.password : "") + ";";
            response.query_code += sp.interface_version + ";";
        }
    }

    /**
     * 将SP状态生成XML形式的报文，用于和外部系统通信
     * @return 生成的XML报文
     */
    public String getSPXML()
    {
        String strXML = "<service_provider>";
        strXML = strXML + "<service_code>" + m_gc.service_code + "</service_code>";
        strXML = strXML + "<enterprise_code>" + m_gc.enterprise_code + "</enterprise_code>";
        strXML = strXML + "</service_provider>";
        return strXML;
    }

    /**
     * 将网关状态生成XML形式的报文，用于和外部系统通信
     * @return  生成的XML报文
     */
    public String getXML()
    {
        String strXML = "<gateway name=\"" + m_gc.m_strName + "\" type=\"" + GatewayType.toString(m_gc.m_nType) + "\" host=\"" + m_gc.m_strHost + "\" transmittable=\"" + (m_bTransmittable ? "yes" : "no") + "\" ";
        strXML = strXML + "receivable=\"" + (m_bReceivable ? "yes" : "no") + "\">";
        strXML = strXML + getSPXML();
        strXML = strXML + "<flux>";
        strXML = strXML + "<mt_total>" + m_gc.m_nSubmit + "</mt_total>";
        strXML = strXML + "<mt_successed>" + m_gc.m_nSubmitSuccess + "</mt_successed>";
        strXML = strXML + "<mt_failed>" + m_gc.m_nSubmitFailure + "</mt_failed>";
        strXML = strXML + "<mo>" + m_gc.m_nDeliverSuccess + "</mo>";
        strXML = strXML + "</flux>";
        if(m_sessions != null)
        {
            SessionEngine sTemp = null;
            for(int i = 0; i < m_sessions.size(); i++)
            {
                sTemp = (SessionEngine)m_sessions.elementAt(i);
                strXML = strXML + sTemp.getXML();
            }

        }
        strXML = strXML + "</gateway>";
        return strXML;
    }

    /**
     * 通过查询回复结果，设置SP信息
     * @param response
     * @param sp
     */
    public void setServiceProvider(CMPPQueryResponse response, ServiceProvider sp)
    {
        if(response == null || sp == null)
            return;
        if(response.query_code == null || response.query_code.length() <= 0)
            return;
        String args[] = Utility.split(response.query_code, ";");
        if(args.length < 4)
            return;
        if(GatewayType.isISMG(m_gc.m_nType) || GatewayType.isSGIP(m_gc.m_nType))
        {
            sp.service_code = args[0];
            sp.enterprise_code = args[1];
            sp.user = args[2];
            sp.password = args[3];
        } else
        if(GatewayType.isSMSC(m_gc.m_nType))
        {
            sp.system_id = args[0];
            sp.system_type = args[1];
            sp.password = args[2];
            if(args[3] != null && args[3].length() > 0)
                sp.interface_version = Byte.parseByte(args[3]);
        }
    }

    /**
     * 将网关属性包装成查询回复对象
     * @return  返回查询回复对象
     */
    public CMPPQueryResponse generateStatusReport()
    {
        try
        {
            CMPPQueryResponse response = new CMPPQueryResponse(0);
            response.gateway_name = m_strName;
            response.session_id = 0;
            response.guid = 0L;
            response.query_time = Utility.toTimeString(new LeadToneDate());
            response.query_type = 3;
            response.query_code = "STATRPT";
            response.MT_TLUsr = (m_bTransmittable ? 1 : 0) | (m_bReceivable ? 2 : 0);
            response.MT_Scs = m_gc.m_nSubmitSuccess;
            response.MT_WT = m_gc.m_nSubmit - (m_gc.m_nSubmitSuccess + m_gc.m_nSubmitFailure);
            response.MT_FL = m_gc.m_nSubmitFailure;
            response.MO_Scs = m_gc.m_nDeliverSuccess;
            response.MO_WT = m_gc.m_nDeliver - m_gc.m_nDeliverSuccess;
            response.MO_FL = 0;
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("GatewayEngine.generateStatusReport : unexpected exit !", 0x2000080000000000L);
        return null;
    }

    /**
     * 根据消息包类型，更新网关统计数据
     * @param packet
     */
    public void statistic(CMPPPacket packet)
    {
        if(packet == null)
            return;
        if(packet.command_id == 4)
            m_gc.m_nSubmit++;
        else
        if(packet.command_id == 5)
            m_gc.m_nDeliver++;
        else
        if(packet.command_id == 0x80000004)
        {
            CMPPSubmitResponse response = (CMPPSubmitResponse)packet;
            if(response.result == 0)
                m_gc.m_nSubmitSuccess++;
            else
                m_gc.m_nSubmitFailure++;
        } else
        if(packet.command_id == 0x80000005)
            m_gc.m_nDeliverSuccess++;
    }

    /**
     * 参见继承子类
     * @param nType
     * @param nID
     */
    public synchronized Packet get(int nType, int nID)
    {
        return null;
    }

    /**
     * 参见继承子类
     * @param nType
     * @param packet
     */
    public synchronized boolean put(int nType, Packet packet)
    {
        return false;
    }

    /**
     * 接收消息
     * @return  返回是接收到消息包对象
     */
    public Packet receive()
    {
        return null;
    }

    /**
     * 发送消息包
     * @param packet
     * @return  返回是否发送成功的布尔值
     */
    public boolean send(Packet packet)
    {
        return false;
    }



}
