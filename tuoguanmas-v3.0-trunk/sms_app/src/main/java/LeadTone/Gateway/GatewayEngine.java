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
 * �����߳��࣬������Ϣ���е����շ��ͽ��ջỰ�Ĺ����࣬
 * ������ʵ�־���Э����죬ʵ�ֲ���ľ���ϸ��
 */
public class GatewayEngine extends Engine
{
    /**
     * ��Ϣ���е�Ĭ������ֵ
     */
    static final int DEFAULT_PACKET_QUEUE_SIZE = 0;
    /**
     * ��������
     */
    public String m_strName;
    /**
     * ��������
     */
    public int m_nType;
    /**
     * ���ص�������Ϣ����
     */
    public GatewayConfig m_gc;
    /**
     * ���ؽ����ĻỰ����
     */
    public Vector m_sessions;
    /**
     * ���ص��������
     */
    public int m_nMaxFlux;
    /**
     * ��������ʱ��������ֵ
     */
    public int m_nPeakFlux;
    /**
     * ��������ʱ�ĵ�ǰ����
     */
    public int m_nCurrFlux;
    /**
     * �����Ƿ�֧�ַ��͵ı�ʶ
     */
    public boolean m_bTransmittable;
    /**
     * �����Ƿ�֧�ֽ��յı�ʶ
     */
    public boolean m_bReceivable;
    /**
     * �������һ�η����쳣��ʱ��
     */
    long m_lExceptionTime;
    /**
     * ����Э��Ҫ�����Ϣ������Я����Ψһ���к�
     */
    public int m_SequenceID;
    /**
     * �������˿ں�
     */
    ServerSocket m_socket;
    /**
     * �Ự��������
     */
    SessionConfig m_sc;
    /**
     * ���ؼ���������
     */
    GatewayListener m_listener;

    /**
     * ���췽����ʼ�������
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
     * �����������״̬���������ص����лỰ������״̬
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
     * ������Ϣ�����������
     * @param i
     */
    public void setCapacity(int i)
    {
    }

    /**
     * �رշ������˿�
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
     * ���ϵͳʹ�õ�������Դ
     */
    public void empty()
    {
        m_sessions.removeAllElements();
        m_sessions = null;
        m_socket = null;
    }

    /**
     * ͨ���Ự�̵߳��̱߳�Ż�ȡ�Ự�̶߳���
     * @param nID
     * @return ��ȡ�ĻỰ�̶߳���
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
     * ��ȡѭ��ʹ�õ��߳�Ψһ��ţ����������̣߳��Ա����»���Ѿ��˳����̺߳ŵ��ٴ�ʹ��Ȩ
     * @return ѭ��ʹ�õ�Ψһ�̱߳��
     */
    public synchronized int getUniqueSessionID()
    {
        for(int i = 1; i <= m_sessions.size(); i++)
            if(getSession(i) == null)
                return i;

        return m_sessions.size() + 1;
    }

    /**
     * ����쳣�Ƿ�ʱ
     * @return �����쳣�Ƿ�ʱ�Ƿ�ʱ�Ĳ���ֵ
     */
    public boolean isExceptionTimeout()
    {
        return System.currentTimeMillis() - m_lExceptionTime > TimeConfig.DEFAULT_EXCEPTION_TIMEOUT;
    }

    /**
     * �����������һ�ε��쳣ʱ��
     */
    public void delayExceptionTimeout()
    {
        m_lExceptionTime = System.currentTimeMillis();
    }

    /**
     * ���÷������˿ںţ��������ö˿ڳ�ʱʱ��
     * @param nListen �˿ں�
     * @return  �Ƿ����óɹ�
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
     * װ�ػỰ�̶߳���
     * @param sc �Ự�̵߳����ö���
     * @param bNeedStartup  �Ƿ���Ҫ����
     * @return  �����Ƿ�װ�سɹ��Ĳ���ֵ
     */
    public boolean loadSession(SessionConfig sc, boolean bNeedStartup)
    {
        return false;
    }

    /**
     * �ر����лỰ�߳�
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
     * �������лỰ�߳�
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
     * ������лỰ���������лỰ��������
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
            //����߳�Ϊֹͣ״̬�����ж��Ƿ�Ϊ������ģʽ��
            //�������ӻỰ�̼߳������Ƴ����������������װ�ز������ûỰ�߳�
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
                //��ѯ�Ự�߳�����Ϣ���й���״̬
                session.checkPacketQueue();
                //�����Ҫ�������ӱ�����Ϣ����
                if(session.needActiveTest())
                    enquireLink(session);
                //����ûỰ֧�ַ��͹�����������ص�����ҲΪ֧�ַ���
                if(BindType.forTransmitter(session.m_sc.m_nType))
                    bTransmittable = true;
                //����ûỰ֧�ֽ��չ�����������ص�����ҲΪ֧�ֽ���
                if(BindType.forReceiver(session.m_sc.m_nType))
                    bReceivable = true;
                //���ܻỰ����������
                nPeakFlux += session.m_sc.m_nPeakFlux;
                nCurrFlux += session.m_sc.m_nCurrFlux;
            }
        }
        //����������������
        m_nMaxFlux = nMaxFlux;
        m_nPeakFlux = nPeakFlux;
        m_nCurrFlux = nCurrFlux;
        m_bTransmittable = bTransmittable;
        m_bReceivable = bReceivable;
        //�������һ���쳣ʱ���ʶ
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
     * �ر����лỰ�߳�
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
     * �������෽��ʵ��
     */
    public void checkAll()
    {
    }

    /**
     * �������෽��ʵ��
     */
    public boolean login(SessionEngine session)
    {
        return false;
    }

    /**
     * �������෽��ʵ��
     */
    public void logout(SessionEngine session)
    {
        session.m_bAuthenticated = false;
    }

    /**
     * �������෽��ʵ��
     */
    public void enquireLink(SessionEngine sessionengine)
    {
    }

    /**
     * �������෽��ʵ��
     */
    public void accept()
    {
    }

    /**
     * �����̣߳��������лỰ�̣߳������������̣߳�
     * ���Ự�̺߳ͼ������߳�
     */
    public void run()
    {
        try
        {
            Log.log("GatewayEngine(" + m_strName + ").run : begin startup all session engines !", 0x80000000000L);
            //�������лỰ�߳�
            startupAllSessions();
            startupAll();
            
            //�����˿ڼ����߳�
            Log.log("GatewayEngine(" + m_strName + ").run : begin startup gateway listener !", 0x80000000000L);
            m_listener.startup();
            Engine.wait(m_listener);
            
            Log.log("GatewayEngine(" + m_strName + ").run : thread startup !", 0x80000000000L);
            m_nStatus = 1;
            for(; isRunning(); sleep())
            {
                //���������������Ϊ��ֵ��һ
                setCapacity(m_nPeakFlux + 1);
                //������Ự�߳�
                checkAllSessions();
                checkAll();
                //����������߳�ֹͣ����ѭ��
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
     * �������෽��ʵ��
     */
    public CMPPQueryResponse getConfig()
    {
        return null;
    }

    /**
     * �������෽��ʵ��
     */
    public void setConfig(CMPPQueryResponse cmppqueryresponse)
    {
    }

    /**
     *  ����SP��Ϣ�����ǲ�ѯ�ظ�������
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
     * ��SP״̬����XML��ʽ�ı��ģ����ں��ⲿϵͳͨ��
     * @return ���ɵ�XML����
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
     * ������״̬����XML��ʽ�ı��ģ����ں��ⲿϵͳͨ��
     * @return  ���ɵ�XML����
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
     * ͨ����ѯ�ظ����������SP��Ϣ
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
     * ���������԰�װ�ɲ�ѯ�ظ�����
     * @return  ���ز�ѯ�ظ�����
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
     * ������Ϣ�����ͣ���������ͳ������
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
     * �μ��̳�����
     * @param nType
     * @param nID
     */
    public synchronized Packet get(int nType, int nID)
    {
        return null;
    }

    /**
     * �μ��̳�����
     * @param nType
     * @param packet
     */
    public synchronized boolean put(int nType, Packet packet)
    {
        return false;
    }

    /**
     * ������Ϣ
     * @return  �����ǽ��յ���Ϣ������
     */
    public Packet receive()
    {
        return null;
    }

    /**
     * ������Ϣ��
     * @param packet
     * @return  �����Ƿ��ͳɹ��Ĳ���ֵ
     */
    public boolean send(Packet packet)
    {
        return false;
    }



}
