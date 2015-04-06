package LeadTone.Gateway;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.Packet;
import LeadTone.Session.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


/**
 * CMPP网关线程类，继承自GatewayEngine，完成CMPP消息的接受下发，
 * 连接会话连接与输入输出消息队列，
 * 处理上下行消息包，处理连接保持等附属类消息包，
 * 用于服务器模式时创建服务端口
 */
public class CMPPGatewayEngine extends GatewayEngine
{
    /**
     * SP信息
     */
    public ServiceProvider m_sp;
    /**
     * 输出消息队列
     */
    public CMPPPacketQueue m_output;
    /**
     * 输入消息队列
     */
    public CMPPPacketQueue m_input;

    /**
     * 构造方法初始化类变量
     * @param strName
     * @param nType
     * @param sp
     */
    public CMPPGatewayEngine(String strName, int nType, ServiceProvider sp)
    {
        super(strName, nType);
        m_sp = null;
        m_output = new CMPPPacketQueue(0);
        m_input = new CMPPPacketQueue(0);
        m_sp = sp;
    }

    /**
     * 设置消息队列最大容量
     * @param nCapacity
     */
    public void setCapacity(int nCapacity)
    {
        m_output.setCapacity(nCapacity);
        m_input.setCapacity(nCapacity);
    }

    /**
     * 清空系统使用的所有资源
     */
    public void empty()
    {
        m_sp = null;
        m_output.empty();
        m_output = null;
        m_input.empty();
        m_input = null;
        super.empty();
    }

    /**
     * 检查输入队列和输出队列的超时情况
     */
    public void checkAll()
    {
        m_output.checkTimeout();
        m_input.checkTimeout();
    }

    /**
     * 建立socket连接，启动会话线程
     */
    public void accept()
    {
        try
        {
            Socket socket = m_socket.accept();
            Log.log("CMPPGatewayEngine(" + m_strName + ").accept : " + socket.toString(), 0x80000000000L);
            CMPPSessionEngine session = new CMPPSessionEngine(m_sc, socket, this);
            Log.log("CMPPGatewayEngine(" + m_strName + ").accept : begin startup " + session.m_nID + "th session engine !", 0x80000000000L);
            session.startup();
            Engine.wait(session);
            m_sessions.addElement(session);
        }
        catch(Exception e) { }
    }

    /**
     * 参考子类方法，根据不同的会话类型配置装载会话线程
     * @param strHostAddress
     */
    public boolean loadAllSessions(String strHostAddress)
    {
        return false;
    }

    /**
     * 装载会话线程，当启动标识为开启时，装载同时启动会话线程
     * @param sc 会话线程配置
     * @param bNeedStartup 装载同时是否启动的标识
     * @return  返回是否装载成功的布尔值
     */
    public boolean loadSession(SessionConfig sc, boolean bNeedStartup)
    {
        try
        {
            //如果为服务器类会话线程，设置监听器端口，由GatewayEngine线程的run方法重启
            if(sc.m_nType == 0)
            {
                m_sc = sc;
                if(setListen(sc.m_nPort))
                    return true;
            } else
            //如果为非服务器类会话线程则装载后放入会话线程集合中
            if(sc.m_nType == 1 || sc.m_nType == 2 || sc.m_nType == 3)
            {
                CMPPSessionEngine session = new CMPPSessionEngine(sc, this);
                m_sessions.addElement(session);
                //如果装载同时启动的标识开启，则立即启动该线程
                if(bNeedStartup)
                    session.startup();
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000080000000000L);
            Log.log(e);
            Log.log("CMPPGatewayEngine(" + m_strName + ").loadSession : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }


    /**
     * 从输出队列提取消息包，如果会话类型为发送并且消息包属于发送类的消息，或者会话类型为接收并且消息包属于接收类的消息，
     * 则将消息从输出队列取出，并从消息队列中移除
     * @param nType 会话类型
     * @param nID 会话线程编号
     * @return 返回提取到的消息包
     */
    public synchronized Packet get(int nType, int nID)
    {
        if(m_output == null)
            return null;
        CMPPPacket cmpp = (CMPPPacket)m_output.peer();
        if(cmpp == null)
            return null;
        if(cmpp.session_id > 0 && cmpp.session_id != nID)
            return null;
        if(BindType.forTransmitter(nType) && CMPPCommandID.isTransmitterOutput(cmpp.command_id) || BindType.forReceiver(nType) && CMPPCommandID.isReceiverOutput(cmpp.command_id))
        {
            m_output.pop();
            return cmpp;
        } else
        {
            return null;
        }
    }

    /**
     * 输入会话线程从端口捕获消息包后，交给网关做过滤，
     * 如果为连接保持断开连接等类消息就直接处理，如果为上下行类消息则放入输入消息队列
     * 方法有synchronized修饰词，保证线程安全
     * @param nType 会话类型
     * @param packet 待处理的消息包
     * @return  返回是否操作成功的布尔值
     */
    public synchronized boolean put(int nType, Packet packet)
    {
        CMPPPacket cmpp = (CMPPPacket)packet;
        if(cmpp == null)
            return true;
        //如果消息包为保持连接消息包，或保持连接回复消息包，或断开连接包，则清空消息包返回成功
        if(handleActiveTest(cmpp) || handleActiveTestResponse(cmpp) || handleTerminate(cmpp))
        {
            cmpp.empty();
            cmpp = null;
            return true;
        }
        //如果会话类型为发送并且消息包属于发送类的消息，或者会话类型为接收并且消息包属于接收类的消息，
        //则将消息放入输入消息队列中
        if((BindType.forTransmitter(nType) && CMPPCommandID.isTransmitterInput(cmpp.command_id) || BindType.forReceiver(nType) && CMPPCommandID.isReceiverInput(cmpp.command_id)) && m_input != null)
            return m_input.push(cmpp);
        else
            return true;
    }


     /**
     * 捕获连接保持回复消息包，直接向会话写保持连接回复消息
     * 控制会话错误数，当收到一个连接保持消息回复包时会话错误总数减一
     * @param packet
     * @return 返回是否处理成功的布尔值
     */
    public boolean handleActiveTest(CMPPPacket packet)
    {
        if(packet.command_id == 8)
        {
            CMPPSessionEngine session = (CMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
                try
                {
                    CMPPActiveTestResponse response = new CMPPActiveTestResponse(packet.sequence_id);
                    response.gateway_name = packet.gateway_name;
                    response.session_id = packet.session_id;
                    response.wrap();
                    //当发送连接保持消息包失败的时候，会话错误总数加一
                    if(!m_output.push(response))
                        session.m_nErrorCount++;
                    session.delayActiveTest();
                }
                catch(Exception e)
                {
                    Log.log(e);
                    Log.log("CMPPGatewayEngine.handleEnquireLink : unexpected exit !", 0x2000040000000000L);
                }
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 捕获连接保持回复消息包
     * 控制会话错误数，当收到一个连接保持消息回复包时会话错误总数减一
     * @param packet
     * @return 返回是否处理成功的布尔值
     */
    public boolean handleActiveTestResponse(CMPPPacket packet)
    {
        if(packet.command_id == 0x80000008)
        {
            CMPPSessionEngine session = (CMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
            {
                //用于控制会话错误数，当收到一个连接保持消息回复包时错误数减一
                session.m_nErrorCount--;
                session.delayActiveTest();
                if(session.m_nErrorCount < 0)
                    session.m_nErrorCount = 0;
            }
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 捕获断开连接消息，直接向会话写回断开连接回复消息
     * @param packet
     * @return 返回是否处理成功的布尔值
     */
    public boolean handleTerminate(CMPPPacket packet)
    {
        if(packet.command_id == 2)
        {
            CMPPSessionEngine session = (CMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
            {
                try
                {
                    //捕获断开连接消息，直接向网关写回断开连接回复消息
                    CMPPTerminateResponse response = new CMPPTerminateResponse(packet.sequence_id);
                    session.writeCMPPPacket(response);
                    session.m_bNeedTerminate = true;
                    return true;
                }
                catch(Exception e)
                {
                    Log.log(e);
                }
                Log.log("CMPPGatewayEngine.handleUnbind : unexpected exit !", 0x2000040000000000L);
            }
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 保持连接操作
     * @param session
     */
    public void enquireLink(SessionEngine session)
    {
        try
        {
            CMPPActiveTest request = new CMPPActiveTest(0x7fffffff);
            request.gateway_name = m_strName;
            request.session_id = session.m_nID;
            m_output.push(request);
            session.m_nErrorCount++;
            session.delayActiveTest();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPGatewayEngine.enquireLink : unexpected exit !", 0x2000080000000000L);
        }
    }

    /**
     * 请求建立会话连接操作
     * @param session
     * @return 返回是否建立会话连接成功
     */
    public boolean login(SessionEngine session)
    {
        try
        {
            //组织建立连接包
            CMPPConnect connect = m_sp.getCMPPConnect(m_nType, session.m_sc.m_nType);
            //如果无法生成建立连接包为空，则认为无需鉴权连接
            if(connect == null)
            {
                session.m_bAuthenticated = true;
                return true;
            }
            connect.gateway_name = m_strName;
            connect.session_id = session.m_nID;
            connect.sequence_id = session.getSequenceID();
            connect.wrap();
            //写入建立连接包，得到建立连接回复包
            CMPPPacket packet = ((CMPPSessionEngine)session).request(connect);
            if(packet == null)
                return false;
            CMPPConnectResponse response = new CMPPConnectResponse(packet);
            response.unwrap();
            if(response.status == 0)
            {   //设置会话授权状态为已授权
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPGatewayEngine(" + m_strName + ").login : unexpected exit !", 0x2000080000000000L);
        }
        session.m_bAuthenticated = false;
        return false;
    }

    /**
     * 请求断开会话连接操作
     * @param session
     */
    public void logout(SessionEngine session)
    {
        try
        {
            CMPPTerminate terminate = new CMPPTerminate(session.getSequenceID());
            terminate.gateway_name = m_strName;
            terminate.session_id = session.m_nID;
            //直接向网关写入断开连接包
            CMPPPacket packet = ((CMPPSessionEngine)session).request(terminate);
            if(packet == null)
                return;
            CMPPTerminateResponse response = new CMPPTerminateResponse(packet);
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPGatewayEngine(" + m_strName + ").logout : unexpected exit !", 0x2000080000000000L);
        }
        session.m_bAuthenticated = false;
    }

    /**
     * 接收消息包，从输入消息队列中提取消息包
     * @return 提取的消息包
     */
    public Packet receive()
    {
        return m_input.pop();
    }

    /**
     * 发送消息包，将消息包放入输出消息队列
     * @param packet
     * @return 返回是否发送成功
     */
    public boolean send(Packet packet)
    {
        return m_output.push(packet);
    }


}
