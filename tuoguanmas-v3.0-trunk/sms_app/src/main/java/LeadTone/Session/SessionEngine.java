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
 * �Ự�����̣߳�������������������������
 */
public class SessionEngine extends Engine
{

	 public GatewayEngine m_gateway;
	    /**
	     * �Ự�̱߳��
	     */
	    public int m_nID;
	    /**
	     * �Ự�������ö���
	     */
	    public SessionConfig m_sc;
	    /**
	     * �Ự�������Ĵ�����
	     */
	    public int m_nErrorCount;
	    /**
	     * �Ự�����Ƿ��Ѽ�Ȩ�ı�ʶ
	     */
	    public boolean m_bAuthenticated;
	    /**
	     * �߳��Ƿ���Ҫ�رյı�ʶ
	     */
	    public boolean m_bNeedTerminate;
	    /**
	     * �߳����һ������ʱ��
	     */
	    long m_lOpenTime;
	    /**
	     * ��һ�η������ӱ��ְ���ʱ��
	     */
	    long m_lLastActiveTest;
	    /**
	     * ���һ�η��Ͷ���Ϣ��ʱ��
	     */
	    long m_lLastMessage;
	    /**
	     * ����Э����Ҫ�����Ϣ���кŵı�ʶ
	     */
	    int m_nSequenceID;
	    /**
	     * ��Ϣ���е�����
	     */
	    int m_nQueueSize;
	    /**
	     * �����Ự���ӵĶ˿�socket����
	     */
	    Socket m_socket;
	    /**
	     * ������socket��������
	     */
	    public InputStream m_is;
	    /**
	     * ������������֮�ϵ�����������
	     */
	    public DataInputStream m_dis;
	    /**
	     * ������socket�ϵ������
	     */
	    public OutputStream m_os;
	    /**
	     * ������������ϵ����������
	     */
	    public DataOutputStream m_dos;
	    /**
	     * ���ڴ���������̶߳���
	     */
	    OutputEngine m_output;
	    /**
	     * ���ڴ���������̶߳���
	     */
	    InputEngine m_input;
	    /**
	     * ��������ֵ
	     */
	    int m_nInputFluxCount;
	    /**
	     * �������ֵ
	     */
	    int m_nOutputFluxCount;
	    /**
	     * ���һ�μ�¼������ʱ��
	     */
	    long m_lFluxTime;
	    
	    //����ʧ�ܴ��� --leh
	    static int errorCount=0;
	    
	    /**
	     * ���췽����ʼ�������
	     * @param sc �Ự�������ö���
	     * @param socket �������ӵĶ˿ں�
	     * @param gateway �Ự���������ض���
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
	     * ���췽����ʼ�������
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
	     * ����socket���ӣ��������Ͻ��������������������������Ͻ��������������ûỰ������������������������
	     * @return �����Ƿ������ӳɹ�
	     */
	    public boolean buildIO()
	    {
	        try
	        {
	            if(m_socket == null)
	            {
	               //m_socket = new Socket(m_sc.m_address, m_sc.m_nPort);
	            	
	            	//�޸Ŀ�ʼ�������й�mas���򣬵�app�����������ip��portʱ����󶨴�appռ�ö˿���������˿ڱ���һ�¡�yuqian 20130105
	            	//m_socket = new Socket(m_sc.m_address, m_sc.m_nPort,null,m_sc.m_nPort);
	            	//�޸Ľ���
	            	
	            	/**
	            	 * tcp��������ʱ��Ҫ�������֣����������ڵڶ�������֮ǰ�����ǵĳ���������һ���������󣬵��µ��������ڶ�������ʱ���޷���Ӧ��һ������ID��
	            	 * ���´����Ŀ���ǽ�������ʱ��������һ����ʱʱ�䣬�Է�����yuqian ��20130228
	            	 */
	            	//start
	            	//1.����socketʵ������ʱSocket����δ�󶨵����ض˿ڣ�����δ����Զ�̷�����
	            	m_socket = new Socket();
	            	//2.��ñ���IP
	            	InetAddress addr = InetAddress.getLocalHost();
	            	String local_ip = addr.getHostAddress();
	            	
	            	//3.Ϊ��ȷ��һ�����̹ر���Socket�󣬼�ʹ����û�ͷŶ˿ڣ�ͬһ�������ϵ��������̻������������øö˿ڣ����Ե���Socket��setResuseAddress(true)������
	            	if(!m_socket.getReuseAddress()) 
	            		m_socket.setReuseAddress(true);
	            	
	            	//4.�������ء�Զ�̶���
	            	InetSocketAddress localAddress = new InetSocketAddress(local_ip,m_sc.m_nPort);//�˴�����ʹ��127.0.0.1
	            	InetSocketAddress remoteAddress = new InetSocketAddress(m_sc.m_address, m_sc.m_nPort); 
	            	
	            	//5.����socket�󶨱��ض��󣬼���ʹ��ָ��ip���˿�
	            	m_socket.bind(localAddress);
	            	
	            	Log.log("bind local_name is:"+localAddress.getHostName()+", bind host_port is:"+localAddress.getPort(),1L);
	            	Log.log("remote host_name is:"+remoteAddress.getHostName()+", remote host_port is:"+remoteAddress.getPort(),1L);
	            	
	            	//6.����Զ�̻�����Ĭ�����ӵĳ�ʱʱ��Ϊ15��
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
	     * ����ϵͳ���ⲿ�Ļ���XML�Ľӿڣ���װ�Ự����״����XML����
	     * @return ����XML����
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
	     * ����Ự����״̬
	     * @param ps ��ӡ�����
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
	     * ���ʹ�õ���Դ���������ӳ�����
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
	     * �ر����������رն˿�����
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
	     * ����Э��Ҫ������ѭ��ʹ�õ����кţ� ��synchronized���δʣ���֤�̰߳�ȫ
	     * @return �������ɵ����к�
	     */
	    public synchronized int getSequenceID()
	    {
	        int seq = 0;
	        //�ο�GatewayType�࣬���ΪSGIPЭ����Ự���������������������ع��ü���
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
	     * ������Ϣ����ʱʱ��
	     * @param lTimeout ��ʱʱ��
	     */
	    public void setPacketTimeout(long lTimeout)
	    {
	        m_sc.m_lPacketTimeout = lTimeout;
	        m_output.setTimeout(lTimeout);
	        m_input.setTimeout(lTimeout);
	    }

	    /**
	     * ���÷��Ͷ���Ϣ��ʱʱ�ޣ������ڳ�ʱ���޷�����Ϣʱ���������߳�
	     * @param lTimeout ��ʱʱ��
	     */
	    public void setMessageTimeout(long lTimeout)
	    {
	        m_sc.m_lMessageTimeout = lTimeout;
	    }

	    /**
	     * �������ӱ�����Ϣ��ʱʱ�ޣ����ڴﵽ��ʱʱ�޺������ӱ�����Ϣ
	     * @param lTimeout ��ʱʱ��
	     */
	    public void setActiveTestTimeout(long lTimeout)
	    {
	        m_sc.m_lActiveTestTimeout = lTimeout;
	    }

	    /**
	     * �ж����ӱ�����Ϣ�Ƿ��Ѿ���ʱ������ά�����ӱ�����Ϣ�Ķ�ʱ����
	     * @return �����Ƿ����ӱ�����Ϣ�Ѿ���ʱ
	     */
	    public boolean needActiveTest()
	    {
	        return m_sc.m_lActiveTestTimeout > 0L && System.currentTimeMillis() - m_lLastActiveTest > m_sc.m_lActiveTestTimeout;
	    }

	    /**
	     * ������ӱ�����Ϣ�ķ��ͺ��������һ�η��͵�ʱ��
	     */
	    public void delayActiveTest()
	    {
	        m_lLastActiveTest = System.currentTimeMillis();
	    }

	    /**
	     * �ж��Ƿ��Ѿ���ʱ��û�ж���Ϣ�·�
	     * @return �����Ƿ��Ѿ���ʱ��û�ж���Ϣ�·�
	     */
	    public boolean noMessage()
	    {
	        return m_sc.m_lMessageTimeout > 0L && System.currentTimeMillis() - m_lLastMessage > m_sc.m_lMessageTimeout;
	    }

	    /**
	     * ��ʱ��δ�ж���Ϣ�·��������·��̺߳��������һ�ε��·�ʱ��
	     */
	    public void delayMessageTimeout()
	    {
	        m_lLastMessage = System.currentTimeMillis();
	    }

	    /**
	     * �־��������������������ͳ�ƴ˻Ự����ĵ�ǰ�������ͷ�ֵ����
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
	     * ���������������������
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
	     * �Ự�����̹߳��ܣ�
	     */
	    public void run()
	    {
	        try
	        {
	            //�������socket����ʧ�ܻ��޷���½�ɹ���رմ��߳�
	            if(!buildIO() || !m_gateway.login(this))
	            {
	                close();
	                m_nStatus = 3;
	                //Log.log("SessionEngine.run(" + m_gateway.m_strName + "," + m_nID + ") : fail to build up connection with the gateway !", 0x40000000000L);
	                Log.log("SessionEngine.run(" + m_gateway.m_strName + "," + m_nID +",["+m_sc.m_address+":"+m_sc.m_nPort+"] ) : fail to build up connection with the gateway !", 0x40000000000L);
	                
	                empty();
	                //���Ӷ�������ʧ�ܺ󣬷��͸澯
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
	            //���߳�˯��ʱ���������ֵ����������ֵ����1���ӵķ������������˯��ʱ��Ϊ1���ӳ�������ֵ
	            if(m_sc.m_nMaxFlux > 0)
	                nSleepTime = 1000 / m_sc.m_nMaxFlux;
	            else
	                nSleepTime = (int)TimeConfig.DEFAULT_NAP_TIME;
	            Log.log("SessionEngine(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ").run : thread startup !", 0x40000000000L);
	            m_nStatus = 1;
	            //ͨ�������̵߳�˯��ʱ��ﵽ����������Ŀ��
	            for(; isRunning(); sleep(nSleepTime))
	            {
	                //�ж��Ƿ�ﵽ����ݴ�ֵ���ﵽ��ʼ�رմ��߳�
	                if(m_nErrorCount >= m_sc.m_nMaxErrorCount)
	                {
	                    Log.log("SessionEngine.run(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : too many errors, and count errors is "+m_nErrorCount+ ",config errors is "+ m_sc.m_nMaxErrorCount +"!", 0x2000040000000000L);
	                    break;
	                }
	                //�ж��Ƿ���Ҫ�رմ��̣߳��ﵽ��ʼ�رմ��߳�
	                if(m_bNeedTerminate)
	                {
	                    Log.log("SessionEngine.run(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : need terminate !", 0x40000000000L);
	                    break;
	                }
	                //�ж���������߳��Ƿ��������У��ﵽ��ʼ�رմ��߳�
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

	                //�����ʱ��û�ж���Ϣ������رմ��߳�
	                if(noMessage())
	                {
	                    Log.log("SessionEngine.run(" + m_gateway.m_strName + "[" + SessionConfig.toString(m_sc.m_nType) + "]," + m_nID + ") : no message for a long time !", 0x40000000000L);
	                    break;
	                }
	                //�������������߳�����
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
     * ��������������ȡ��Ϣ��������ʵ����Э����أ���ο���Ӧ�̳���
     * @return ���ش������������ж�ȡ�İ���������쳣�򷵻�NULL
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
     * ����Ϣ��д���������������ɷ��ͣ�����ʵ����Э����أ���ο���Ӧ�̳���
     * @param packet
     * @return �����Ƿ��ͳɹ�
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
     * ����Ϣ���ɹ�д������������󼴴���������ȡ��Ϣ�������أ�����ʵ����Э����أ���ο���Ӧ�̳���
     * ��д��ʧ���򷵻�NULL
     * @param packet Ҫд�����Ϣ��
     * @return  ����������ȡ�����ݰ�
     */
	    public Packet request(Packet packet)
	    {
	        if(writePacket(packet))
	            return readPacket();
	        else
	            return null;
	    }




	    /**
	     * �ο��̳���ʵ��
	     * @return �����Ƿ����ɹ�
	     * @throws BufferException
	     */
	    public boolean put()
	        throws BufferException
	    {
	        return false;
	    }

	    /**
	     * �ο��̳���ʵ��
	     * @return �����Ƿ�ȡ���ɹ�
	     * @throws BufferException
	     */
	    public boolean get()
	        throws BufferException
	    {
	        return false;
	    }

	    /**
	     * �ο��̳���ʵ��
	     */
	    public void checkPacketQueue()
	    {
	        m_nQueueSize = 0;
	    }
}
