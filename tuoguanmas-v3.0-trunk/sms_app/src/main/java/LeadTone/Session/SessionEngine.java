package LeadTone.Session;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import LeadTone.BufferException;
import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.TimeConfig;
import LeadTone.Center.Administrator;
import LeadTone.Center.FileConfig;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Packet.Packet;


/**
 * 会话管理线程，负责建立输入和输出控制两进程
 */
public class SessionEngine extends Engine
{

	 public GatewayEngine m_gateway;
	    /**
	     * 会话线程编号
	     */
	    public int m_nID;
	    /**
	     * 会话属性配置对象
	     */
	    public SessionConfig m_sc;
	    /**
	     * 会话中遇到的错误数
	     */
	    public int m_nErrorCount;
	    /**
	     * 会话建立是否已鉴权的标识
	     */
	    public boolean m_bAuthenticated;
	    /**
	     * 线程是否需要关闭的标识
	     */
	    public boolean m_bNeedTerminate;
	    /**
	     * 线程最近一次启动时间
	     */
	    long m_lOpenTime;
	    /**
	     * 上一次发送连接保持包的时刻
	     */
	    long m_lLastActiveTest;
	    /**
	     * 最后一次发送短消息的时刻
	     */
	    long m_lLastMessage;
	    /**
	     * 用于协议中要求的消息序列号的标识
	     */
	    int m_nSequenceID;
	    /**
	     * 消息队列的容量
	     */
	    int m_nQueueSize;
	    /**
	     * 建立会话连接的端口socket对象
	     */
	    Socket m_socket;
	    /**
	     * 建立于socket的输入流
	     */
	    public InputStream m_is;
	    /**
	     * 建立于输入流之上的数据输入流
	     */
	    public DataInputStream m_dis;
	    /**
	     * 建立于socket上的输出流
	     */
	    public OutputStream m_os;
	    /**
	     * 建立于输出流上的数据输出流
	     */
	    public DataOutputStream m_dos;
	    /**
	     * 用于处理输出的线程对象
	     */
	    OutputEngine m_output;
	    /**
	     * 用于处理输入的线程对象
	     */
	    InputEngine m_input;
	    /**
	     * 输入流量值
	     */
	    int m_nInputFluxCount;
	    /**
	     * 输出流量值
	     */
	    int m_nOutputFluxCount;
	    /**
	     * 最近一次记录流量的时刻
	     */
	    long m_lFluxTime;
	    
	    //连接失败次数 --leh
	    static int errorCount=0;
	    
	    /**
	     * 构造方法初始化类变量
	     * @param sc 会话属性配置对象
	     * @param socket 建立连接的端口号
	     * @param gateway 会话所属的网关对象
	     */
	    public SessionEngine(SessionConfig sc, Socket socket, GatewayEngine gateway)
	    {
	        super("SessionEngine");
	        m_gateway = null;
	        m_nID = 0;
	        m_sc = null;
	        m_nErrorCount = 0;
	        m_bAuthenticated = false;
	        m_bNeedTerminate = false;
	        m_lOpenTime = System.currentTimeMillis();
	        m_lLastActiveTest = System.currentTimeMillis();
	        m_lLastMessage = System.currentTimeMillis();
	        m_nSequenceID = 0;
	        m_nQueueSize = 0;
	        m_socket = null;
	        m_is = null;
	        m_dis = null;
	        m_os = null;
	        m_dos = null;
	        m_output = null;
	        m_input = null;
	        m_nInputFluxCount = 0;
	        m_nOutputFluxCount = 0;
	        m_lFluxTime = 0L;
	        m_gateway = gateway;
	        m_nID = gateway.getUniqueSessionID();
	        m_sc = sc;
	        m_socket = socket;
	        m_nSequenceID = 0;
	    }

	    /**
	     * 构造方法初始化类变量
	     * @param sc
	     * @param gateway
	     */
	    public SessionEngine(SessionConfig sc, GatewayEngine gateway)
	    {
	        super("SessionEngine");
	        m_gateway = null;
	        m_nID = 0;
	        m_sc = null;
	        m_nErrorCount = 0;
	        m_bAuthenticated = false;
	        m_bNeedTerminate = false;
	        m_lOpenTime = System.currentTimeMillis();
	        m_lLastActiveTest = System.currentTimeMillis();
	        m_lLastMessage = System.currentTimeMillis();
	        m_nSequenceID = 0;
	        m_nQueueSize = 0;
	        m_socket = null;
	        m_is = null;
	        m_dis = null;
	        m_os = null;
	        m_dos = null;
	        m_output = null;
	        m_input = null;
	        m_nInputFluxCount = 0;
	        m_nOutputFluxCount = 0;
	        m_lFluxTime = 0L;
	        m_gateway = gateway;
	        m_nID = gateway.getUniqueSessionID();
	        m_sc = sc;
	        m_socket = null;
	    }

	    /**
	     * 建立socket连接，在连接上建立输入输出流，在输入输出流上建立数据流，设置会话流量，启动输入输出管理进程
	     * @return 返回是否建立连接成功
	     */
	    public boolean buildIO()
	    {
	        try
	        {
	            if(m_socket == null)
	            {
	               //m_socket = new Socket(m_sc.m_address, m_sc.m_nPort);
	            	
	            	//修改开始。根据托管mas规则，当app连接跳板机的ip：port时，需绑定此app占用端口与跳板机端口保持一致。yuqian 20130105
	            	//m_socket = new Socket(m_sc.m_address, m_sc.m_nPort,null,m_sc.m_nPort);
	            	//修改结束
	            	
	            	/**
	            	 * tcp建立连接时需要三次握手，服务器端在第二次握手之前，我们的程序又做了一次握手请求，导致当服务器第二次握手时，无法对应第一次握手ID。
	            	 * 以下代码的目的是建立连接时，设置了一个超时时间，以防错误。yuqian ，20130228
	            	 */
	            	//start
	            	//1.建立socket实例，此时Socket对象未绑定到本地端口，并且未连接远程服务器
	            	m_socket = new Socket();
	            	//2.获得本机IP
	            	InetAddress addr = InetAddress.getLocalHost();
	            	String local_ip = addr.getHostAddress();
	            	
	            	//3.为了确保一个进程关闭了Socket后，即使它还没释放端口，同一个主机上的其他进程还可以立刻重用该端口，可以调用Socket的setResuseAddress(true)方法：
	            	if(!m_socket.getReuseAddress()) 
	            		m_socket.setReuseAddress(true);
	            	
	            	//4.建立本地、远程对象。
	            	InetSocketAddress localAddress = new InetSocketAddress(local_ip,m_sc.m_nPort);//此处不能使用127.0.0.1
	            	InetSocketAddress remoteAddress = new InetSocketAddress(m_sc.m_address, m_sc.m_nPort); 
	            	
	            	//5.本地socket绑定本地对象，即：使用指定ip、端口
	            	m_socket.bind(localAddress);
	            	
	            	Log.log("bind local_name is:"+localAddress.getHostName()+", bind host_port is:"+localAddress.getPort(),1L);
	            	Log.log("remote host_name is:"+remoteAddress.getHostName()+", remote host_port is:"+remoteAddress.getPort(),1L);
	            	
	            	//6.连接远程机器，默认连接的超时时间为15秒
	            	m_socket.connect(remoteAddress,Integer.parseInt(Long.toString(TimeConfig.DEFAULT_SOCKET_TIMEOUT)));
	            	
	            	Log.log("Local SmsApp bind "+m_sc.m_nPort +" port Connecting to ISMG[" + m_sc.m_address + "] on port " + m_sc.m_nPort + " ...", 1L);
	            	//end
	            	
	            	
	            }
	            m_socket.setSoTimeout((int)TimeConfig.DEFAULT_SOCKET_TIMEOUT);
	            m_is = m_socket.getInputStream();
	            m_dis = new DataInputStream(m_is);
	            m_os = m_socket.getOutputStream();
	            m_dos = new DataOutputStream(m_os);
	            m_input = new InputEngine(this);
	            m_input.setTimeout(m_sc.m_lPacketTimeout);
	            m_input.setCapacity(128);
	            m_output = new OutputEngine(this);
	            m_output.setTimeout(m_sc.m_lPacketTimeout);
	            m_output.setCapacity(128);
	            return true;
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        	Log.log(e.getMessage(), 0x2000040000000000L);
	            
	            if( errorCount++ == 10){
	            	// mail warning
	            	String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	            	Administrator admin = FileConfig.loadAdministrator();
	            	admin.sendMail("SmsApp Connecting to gateWay Error", "(" + m_gateway.m_strName + ",["+m_sc.m_address+":"+m_sc.m_nPort+"]" + m_nID + ").buildIO : unexpected exit at "+date+"!\r\n"+e.getMessage()+"\r\n");
	            	errorCount=0;
	            }
	        }
	        Log.log("SessionEngine(" + m_gateway.m_strName + "," + m_nID + ").buildIO : unexpected exit !", 0x2000040000000000L);
	        return false;
	    }

	    /**
	     * 用于系统与外部的基于XML的接口，组装会话连接状况的XML报文
	     * @return 返回XML报文
	     */
	    public String getXML()
	    {
	        String strXML = "<session id=\"" + m_nID + "\" type=\"" + BindType.toString(m_sc.m_nType) + "\">";
	        strXML = strXML + "<error>" + m_nErrorCount + "</error>";
	        strXML = strXML + "<flux><max>" + m_sc.m_nMaxFlux + "</max>";
	        strXML = strXML + "<current>" + m_sc.m_nCurrFlux + "</current>";
	        strXML = strXML + "<peak>" + m_sc.m_nPeakFlux + "</peak></flux>";
	        strXML = strXML + "<queue>" + m_nQueueSize + "</queue>";
	        strXML = strXML + "</session>";
	        return strXML;
	    }

	    /**
	     * 输出会话工作状态
	     * @param ps 打印输出流
	     */
	    public void dump(PrintStream ps)
	    {
	        String strType = "";
	        if(isRunning())
	        {
	            if(BindType.forTransmitter(m_sc.m_nType))
	                strType = strType + "T";
	            if(BindType.forReceiver(m_sc.m_nType))
	                strType = strType + "R";
	        }
	        ps.print("\tsession(" + m_gateway.m_strName + "," + m_nID + "," + strType + ") : " + "error(" + m_nErrorCount + ")," + "flux(" + m_sc.m_nMaxFlux + "," + m_sc.m_nPeakFlux + "," + m_sc.m_nCurrFlux + ")," + "queue(" + m_nQueueSize + ")\r\n");
	    }

	    /**
	     * 清空使用的资源，清空所有映射对象
	     */
	    public void empty()
	    {
	        m_gateway = null;
	        m_is = null;
	        m_dis = null;
	        m_os = null;
	        m_dos = null;
	        m_socket = null;
	        m_output = null;
	        m_input = null;
	    }

	    /**
	     * 关闭数据流，关闭端口连接
	     */
	    public void close()
	    {
	        try
	        {
	            if(m_is != null && m_os != null)
	            {
	                m_dis.close();
	                m_is.close();
	                m_dos.close();
	                m_os.close();
	            }
	            if(m_socket != null)
	                m_socket.close();
	        }
	        catch(Exception e)
	        {
	            Log.log(e.getMessage(), 0x2000040000000000L);
	            Log.log("SessionEngine(" + m_gateway.m_strName + "," + m_nID + ").close : unexpected exit !", 0x2000040000000000L);
	        }
	    }

	    /**
	     * 根据协议要求，生成循环使用的序列号， 有synchronized修饰词，保证线程安全
	     * @return 返回生成的序列号
	     */
	    public synchronized int getSequenceID()
	    {
	        int seq = 0;
	        //参考GatewayType类，如果为SGIP协议则会话单独计数，否则整个网关共用计数
	        if(m_gateway.m_nType != 0x30000)
	        {
	            if(m_nSequenceID == 0x7fffffff)
	                m_nSequenceID = 0;
	            m_nSequenceID++;
	            seq = m_nSequenceID;
	        } else
	        {
	            if(m_gateway.m_SequenceID == 0x7fffffff)
	                m_gateway.m_SequenceID = 0;
	            m_gateway.m_SequenceID++;
	            seq = m_gateway.m_SequenceID;
	        }
	        return seq;
	    }

	    /**
	     * 设置消息包超时时限
	     * @param lTimeout 超时时限
	     */
	    public void setPacketTimeout(long lTimeout)
	    {
	        m_sc.m_lPacketTimeout = lTimeout;
	        m_output.setTimeout(lTimeout);
	        m_input.setTimeout(lTimeout);
	    }

	    /**
	     * 设置发送短消息超时时限，用于在长时间无法送消息时充启发送线程
	     * @param lTimeout 超时时限
	     */
	    public void setMessageTimeout(long lTimeout)
	    {
	        m_sc.m_lMessageTimeout = lTimeout;
	    }

	    /**
	     * 设置连接保持消息超时时限，用于达到超时时限后发送连接保持消息
	     * @param lTimeout 超时时限
	     */
	    public void setActiveTestTimeout(long lTimeout)
	    {
	        m_sc.m_lActiveTestTimeout = lTimeout;
	    }

	    /**
	     * 判断连接保持消息是否已经超时，用于维持连接保持消息的定时发送
	     * @return 返回是否连接保持消息已经超时
	     */
	    public boolean needActiveTest()
	    {
	        return m_sc.m_lActiveTestTimeout > 0L && System.currentTimeMillis() - m_lLastActiveTest > m_sc.m_lActiveTestTimeout;
	    }

	    /**
	     * 完成连接保持消息的发送后，重置最近一次发送的时间
	     */
	    public void delayActiveTest()
	    {
	        m_lLastActiveTest = System.currentTimeMillis();
	    }

	    /**
	     * 判断是否已经长时间没有短消息下发
	     * @return 返回是否已经长时间没有短消息下发
	     */
	    public boolean noMessage()
	    {
	        return m_sc.m_lMessageTimeout > 0L && System.currentTimeMillis() - m_lLastMessage > m_sc.m_lMessageTimeout;
	    }

	    /**
	     * 长时间未有短消息下发，重启下发线程后重置最近一次的下发时间
	     */
	    public void delayMessageTimeout()
	    {
	        m_lLastMessage = System.currentTimeMillis();
	    }

	    /**
	     * 分局输入和输出的流量情况，统计此会话整体的当前流量，和峰值流量
	     * @param nMaxFlux
	     */
	    public void statistic(int nMaxFlux)
	    {
	        int nInputFluxCount = nMaxFlux - m_nInputFluxCount;
	        int nOutputFluxCount = nMaxFlux - m_nOutputFluxCount;
	        m_sc.m_nCurrFlux = nInputFluxCount <= nOutputFluxCount ? nOutputFluxCount : nInputFluxCount;
	        if(m_sc.m_nCurrFlux > m_sc.m_nPeakFlux)
	            m_sc.m_nPeakFlux = m_sc.m_nCurrFlux;
	    }

	    /**
	     * 重置输入和输出的最大流量
	     */
	    public void resetFluxCounter()
	    {
	        if(System.currentTimeMillis() - m_lFluxTime >= 1000L)
	        {
	            m_lFluxTime = System.currentTimeMillis();
	            statistic(m_sc.m_nMaxFlux);
	            m_nInputFluxCount = m_sc.m_nMaxFlux;
	            m_nOutputFluxCount = m_sc.m_nMaxFlux;
	        }
	    }

	    /**
	     * 会话管理线程功能，
	     */
	    public void run()
	    {
	        try
	        {
	            //如果建立socket连接失败或无法登陆成功则关闭此线程
	            if(!buildIO() || !m_gateway.login(this))
	            {
	                close();
	                m_nStatus = 3;
	                //Log.log("SessionEngine.run(" + m_gateway.m_strName + "," + m_nID + ") : fail to build up connection with the gateway !", 0x40000000000L);
	                Log.log("SessionEngine.run(" + m_gateway.m_strName + "," + m_nID +",["+m_sc.m_address+":"+m_sc.m_nPort+"] ) : fail to build up connection with the gateway !", 0x40000000000L);
	                
	                empty();
	                //连接短信网关失败后，发送告警
	                return;
	            }
	            Log.log("SessionEngine(" + m_gateway.m_strName + "," + m_nID + ").run : begin startup output engine !", 0x40000000000L);
	            m_output.startup();
	            Engine.wait(m_output);
	            Log.log("SessionEngine(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ").run : begin startup input engine !", 0x40000000000L);
	            m_input.startup();
	            Engine.wait(m_input);
	            m_nInputFluxCount = m_sc.m_nMaxFlux;
	            m_nOutputFluxCount = m_sc.m_nMaxFlux;
	            int nSleepTime = 0;
	            //此线程睡眠时间根据流量值决定，流量值代表1秒钟的发送条数，因此睡眠时间为1秒钟除以流量值
	            if(m_sc.m_nMaxFlux > 0)
	                nSleepTime = 1000 / m_sc.m_nMaxFlux;
	            else
	                nSleepTime = (int)TimeConfig.DEFAULT_NAP_TIME;
	            Log.log("SessionEngine(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ").run : thread startup !", 0x40000000000L);
	            m_nStatus = 1;
	            //通过控制线程的睡眠时间达到控制流量的目的
	            for(; isRunning(); sleep(nSleepTime))
	            {
	                //判断是否达到最大容错值，达到则开始关闭此线程
	                if(m_nErrorCount >= m_sc.m_nMaxErrorCount)
	                {
	                    Log.log("SessionEngine.run(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : too many errors, and count errors is "+m_nErrorCount+ ",config errors is "+ m_sc.m_nMaxErrorCount +"!", 0x2000040000000000L);
	                    break;
	                }
	                //判断是否需要关闭此线程，达到则开始关闭此线程
	                if(m_bNeedTerminate)
	                {
	                    Log.log("SessionEngine.run(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : need terminate !", 0x40000000000L);
	                    break;
	                }
	                //判断输入控制线程是否仍在运行，达到则开始关闭此线程
	                if(!m_input.isRunning())
	                {
	                    Log.log("SessionEngine.run(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : input engine unexpected exit !", 0x2000040000000000L);
	                    break;
	                }
	                if(m_nInputFluxCount > 0 && get())
	                    m_nInputFluxCount--;

	                if(!m_output.isRunning())
	                {
	                    Log.log("SessionEngine.run(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : output engine unexpected exit !", 0x2000040000000000L);
	                    break;
	                }
	                if(m_nOutputFluxCount > 0 && put())
	                    m_nOutputFluxCount--;

	                //如果长时间没有短消息发送则关闭此线程
	                if(noMessage())
	                {
	                    Log.log("SessionEngine.run(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : no message for a long time !", 0x40000000000L);
	                    break;
	                }
	                //重置输入和输出线程流量
	                resetFluxCounter();
	            }

	        }
	        catch(Exception e)
	        {
	            Log.log(e);
	            Log.log("SessionEngine.run(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : unexpected exit !", 0x2000040000000000L);
	        }
	        m_nInputFluxCount = 0;
	        m_nOutputFluxCount = 0;
	        Log.log("SessionEngine(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ").run : begin shutdown input engine !", 0x40000000000L);
	        m_input.shutdown();
	        Engine.wait(m_input);
	        Log.log("SessionEngine(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ").run : begin shutdown output engine !", 0x40000000000L);
	        m_output.shutdown();
	        Engine.wait(m_output);
	        try
	        {
	        if(!m_bNeedTerminate)
	            m_gateway.logout(this);
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	            close();
	            empty();
	            m_nStatus = 3;
	            Log.log("SessionEngine.run([" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : thread stopped !", 0x40000000000L);
	        }
	        return;
	    }
    /**
     * 从输入数据流读取消息包，具体实现与协议相关，请参考相应继承类
     * @return 返回从输入数据流中读取的包对象，如果异常则返回NULL
     */
	    public Packet readPacket()
	    {
	        try
	        {
	            Packet packet = new Packet();
	            packet.gateway_name = m_gateway.m_strName;
	            packet.session_id = m_nID;
	            packet.inputPacket(m_dis);
	            return packet;
	        }
	        catch(Exception e)
	        {
	            Log.log(e);
	        }
	        Log.log("SessionEngine[" + SessionConfig.toString(m_sc.m_nType) + "].readPacket : unexpected exit !", 0x2000040000000000L);
	        return null;
	    }

    /**
     * 将消息包写入输出数据流，完成发送，具体实现与协议相关，请参考相应继承类
     * @param packet
     * @return 返回是否发送成功
     */
	    public boolean writePacket(Packet packet)
	    {
	        try
	        {
	            packet.outputPacket(m_dos);
	            return true;
	        }
	        catch(Exception e)
	        {
	            Log.log(e);
	        }
	        Log.log("SessionEngine[" + SessionConfig.toString(m_sc.m_nType) + "].writePacket : unexpected exit !", 0x2000040000000000L);
	        return false;
	    }

    /**
     * 将消息包成功写入输出数据流后即从输入流读取消息包并返回，具体实现与协议相关，请参考相应继承类
     * 如写入失败则返回NULL
     * @param packet 要写入的消息包
     * @return  从输入流读取的数据包
     */
	    public Packet request(Packet packet)
	    {
	        if(writePacket(packet))
	            return readPacket();
	        else
	            return null;
	    }




	    /**
	     * 参考继承类实现
	     * @return 返回是否放入成功
	     * @throws BufferException
	     */
	    public boolean put()
	        throws BufferException
	    {
	        return false;
	    }

	    /**
	     * 参考继承类实现
	     * @return 返回是否取出成功
	     * @throws BufferException
	     */
	    public boolean get()
	        throws BufferException
	    {
	        return false;
	    }

	    /**
	     * 参考继承类实现
	     */
	    public void checkPacketQueue()
	    {
	        m_nQueueSize = 0;
	    }
}
