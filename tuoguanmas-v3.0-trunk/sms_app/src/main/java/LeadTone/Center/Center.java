package LeadTone.Center;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.PropertyConfigurator;

import LeadTone.ClassLoaderUtils;
import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Utility;
import LeadTone.Gateway.ASIAINFOGateway;
import LeadTone.Gateway.BISPGateway;
import LeadTone.Gateway.CMPPGateway;
import LeadTone.Gateway.CMPPServerGateway;
import LeadTone.Gateway.CNGPDCGateway;
import LeadTone.Gateway.CNGPSCGateway;
import LeadTone.Gateway.GatewayConfig;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Gateway.GatewayType;
import LeadTone.Gateway.HUAWEIGateway;
import LeadTone.Gateway.INTRINSICGateway;
import LeadTone.Gateway.MISCGateway;
import LeadTone.Gateway.NOKIACMPPGateway;
import LeadTone.Gateway.NOKIAGateway;
import LeadTone.Gateway.SGIPGateway;
import LeadTone.Gateway.SITECHGateway;
import LeadTone.Gateway.SMPPGateway;
import LeadTone.Gateway.SMPPServerGateway;
import LeadTone.Gateway.ServiceProvider;
import LeadTone.Gateway.TSSXGateway;
import LeadTone.Gateway.TUOWEIGateway;
import LeadTone.LeadToneLogic.BackupTableConfig;
import LeadTone.LeadToneLogic.LeadToneLogicConfig;
import LeadTone.LeadToneLogic.UpdateFinalResultConfig;
import LeadTone.LeadToneLogic.MO.Constant;
import LeadTone.Packet.CMPPPacket.CMPPConnect;
import LeadTone.XML.XMLTag;

import com.leadtone.hostmas.smsapp.dao.CustomBeanFactory;
import com.leadtone.hostmas.smsapp.domain.ConfigData;
import com.leadtone.hostmas.smsapp.service.SmsService;


/**
 * ϵͳ�����࣬Ҳ��ϵͳ�ĸ��ڵ㣬������̳�ʵ�ֲ�ͬ�Ĺ���ģʽ
 * ���幤��ģʽ�Ľ��Ͳο��������������
 */
public class Center extends Engine
{
    /**
     * ϵͳCMPP_CENTER����ģʽ���
     */
    public static final int CMPP_CENTER = 1;
    /**
     * ϵͳUNION_CENTER����ģʽ���
     */
    public static final int UNION_CENTER = 2;
    /**
     * ϵͳEXCHANGE_CENTER����ģʽ���
     */
    public static final int EXCHANGE_CENTER = 3;

    /**
     * �����ü�������Ĭ�Ϲ���Ƶ��ʱ��
     */
    static final int DEFAULT_LISTEN_TIME = 1000;
    /**
     * ϵͳ�Ƿ���Ҫ������Ȩ�ı�ʶ������ϵͳ��Ʒ��ʱ�Ĳ�Ȩ����
     */
    static final boolean CHECK_LISCENCE = false;
    /**
     * ��Ʒ��Ϣ���� Center
     */
    public ProductConfig m_pc;
    /**
     * ϵͳ�����ܸ������ܵĻ������������
     */
    public Administrator m_admin;
    /**
     * ϵͳ����˿ڶ���
     */
    ServerSocket m_socket;
    /**
     * ϵͳ������������
     */
    public static int m_nMaxFlux = 0;
    /**
     * ϵͳ����ʱ������ֵ
     */
    public static int m_nPeakFlux = 0;
    /**
     * ϵͳ����ʱ�ĵ�ǰ����
     */
    public static int m_nCurrFlux = 0;
    /**
     * ϵͳ����ʱѡ��Ĺ���ģʽ�ı�ʶ
     */
    public static int m_nType = 1;
    /**
     * ϵͳ�Ƿ���Ҫ�رյı�ʶ
     */
    public static boolean m_bNeedTerminate = false;
    /**
     * ϵͳ�а������������ض���
     */
    public static Vector m_gateways = new Vector();
    /**
     * ϵͳ�����ļ� config.xml
     */
    public static String m_config_file = null;
    
    //��־�ļ���
    public static String log_file_name = "smsApp2";
    
    //���Ų������ö���
    static ConfigData configData;
    
    //ת��������
    static boolean needForward = false;
    //ת����ip��ַ
    static String httpUrl = null;

    /**
     * ���췽����ʼ�������
     * @param pc
     * @param admin
     * @throws IOException
     */
    public Center(ProductConfig pc, Administrator admin)
        throws IOException
    {
        super("Center");
        m_pc = null;
        m_admin = null;
        m_socket = null;
        m_pc = pc;
        m_admin = admin;
       // m_socket = new ServerSocket(m_admin.m_nPort);
       // m_socket.setSoTimeout(1000);
    }

    /**
     * �����ǰϵͳ�Ĺ���״̬����
     * @param ps
     */
    public void dump(PrintStream ps)
    {
        String strException = Log.getLastException();
        if(strException.length() > 60)
            strException = strException.substring(0, 60) + "...";
        ps.print("\tlast exception : " + strException + "\r\n");
        ps.print("\tcenter(" + (isRunning() ? "running" : "stopped") + ") : " + "flux(" + m_nMaxFlux + "," + m_nPeakFlux + "," + m_nCurrFlux + ")\r\n");
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            if(gateway.isRunning())
                gateway.dump(ps);
        }

    }

    /**
     * ���ϵͳʹ�õ���Դ
     */
    public void empty()
    {
        m_admin = null;
        m_socket = null;
        m_gateways.removeAllElements();
        m_gateways = null;
    }

    /**
     * �رչ���˿�
     */
    public void close()
    {
        try
        {
        	if(m_socket!=null)
            m_socket.close();
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000001L);
            Log.log(e);
            Log.log("UnionCenter.close : unexpected exit !", 0x2000000000000001L);
        }
    }

    /**
     * ������������״̬��XML�ļ������������XML���ⲿͨ�Žӿ��ṩ���ع���״̬����
     * @param gateway_name
     * @return ���ɵ���������״̬��XML�ļ�
     */
    public static String getGatewayStatus(String gateway_name)
    {
        if(gateway_name == null || gateway_name.length() <= 0)
            return null;
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            if(gateway.m_strName != null && gateway.m_strName.equals(gateway_name))
            {
                String strTemp = "<gateway ";
                strTemp = strTemp + "name=\"" + gateway_name + "\" ";
                strTemp = strTemp + "type=\"" + GatewayType.toString(gateway.m_nType) + "\" ";
                strTemp = strTemp + "transmittable=\"" + (gateway.m_bTransmittable ? "yes" : "no") + "\" ";
                strTemp = strTemp + "receivable=\"" + (gateway.m_bReceivable ? "yes" : "no") + "\">";
                strTemp = strTemp + "<flux>";
                strTemp = strTemp + "<max>" + gateway.m_nMaxFlux + "</max>";
                strTemp = strTemp + "<peak>" + gateway.m_nPeakFlux + "</peak>";
                strTemp = strTemp + "<current>" + gateway.m_nCurrFlux + "</current>";
                strTemp = strTemp + "</flux>";
                strTemp = strTemp + "</gateway>";
                return new String(strTemp);
            }
        }

        return null;
    }

    /**
     * �����������أ������������ݵ�XML�����������ⲿ����XML�Ľӿ����ݽ���
     * @return �����������ݵ�XML����
     */
    public static String getGwXMLStr()
    {
        String strXML = "";
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            strXML = strXML + gateway.getXML();
        }

        return strXML;
    }

    /**
     * �������пɷ��ͻ�ɽ��յ����أ����ɱ����������صĲ�ѯ��SQL
     * @return ���ɱ����������صĲ�ѯ��SQL
     */
    public static String generateWhere()
    {
        if(m_gateways.size() <= 0)
            return new String("ih_gateway = 'unavailable'");
        String strWhere = new String();
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            //�������пɷ��ͻ�ɽ��յ����أ����ɱ����������صĲ�ѯ��SQL
            if(gateway.m_bTransmittable || gateway.m_bReceivable)
            {
                strWhere = strWhere + "ih_gateway = '" + gateway.m_strName + "'";
                strWhere = strWhere + " or ";
            }
        }

        strWhere = strWhere + "ih_gateway = ''";
        return strWhere;
    }

    /**
     * ��ʼ������˿ڣ����������߳�Controller
     */
    public void accept()
    {
        try
        {
        	if(m_socket!=null){
        		Socket client = m_socket.accept();
                Controller controller = new Controller(client, this);
                controller.startup();
        	}            
        }
        catch(Exception e) { }
    }

    /**
     * ϵͳ��������ͳ�ƣ����������ص��������ݻ��ܵ�ϵͳ����
     */
    public void statistic()
    {
        int nPeakFlux = 0;
        int nMaxFlux = 0;
        int nCurrFlux = 0;
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            nMaxFlux += gateway.m_nMaxFlux;
            if(gateway.isRunning())
            {
                nPeakFlux += gateway.m_nPeakFlux;
                nCurrFlux += gateway.m_nCurrFlux;
            }
        }

        m_nMaxFlux = nMaxFlux;
        m_nPeakFlux = nPeakFlux;
        m_nCurrFlux = nCurrFlux;
    }

    /**
     * ������е����ع�������������ͱ����ʼ�
     * @return ���ؼ�����ع�������Ľ��
     */
    public boolean checkGateway()
    {
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            //�������ֹͣ���������ʼ���ʾ���ܿ��ؿ��������ͱ����ʼ�����ʾ�����Ѿ�ֹͣ������������false,����ʼ���ʾ���ܿ��عرյĻ�����true
            if(gateway.isStopped() && m_admin.mail_switch)
            {
                m_admin.sendMail("Emergency(" + m_pc.m_strAbbreviation + ") : Gateway(" + gateway.m_strName + ") has stopped working !", "Letter is from " + m_pc.m_strAuthorization + "\r\n" + "Gateway(" + gateway.m_strName + ") has stopped working ! \r\n" + "The last exception is : " + Log.getLastException() + "\r\n" + "Please check the error as soon as possible !\r\n");
                return false;
            }
            //������س�ʱ�����ʼ���ʾ���ܿ��ؿ������ж�Ϊ���ع����쳣�����ͱ����ʼ�����ʾ���ع����쳣��������true
            if(gateway.isExceptionTimeout() && m_admin.mail_switch)
            {
                m_admin.sendMail("Emergency(" + m_pc.m_strAbbreviation + ") : Gateway(" + gateway.m_strName + ") is abnormal !", "Letter is from " + m_pc.m_strAuthorization + "\r\n" + "Gateway(" + gateway.m_strName + ") is abnormal ! \r\n" + "The last exception is : " + Log.getLastException() + "\r\n" + "Please check the error as soon as possible !\r\n");
                gateway.delayExceptionTimeout();
            }
        }

        return true;
    }

    /**
     * ������������
     */
    public void startupAllGateways()
    {
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            gateway.startup();
            if(i == m_gateways.size() - 1)
                Engine.wait(gateway);
        }

    }

    /**
     * �ر���������������
     */
    public void shutdownAllGateways()
    {
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            gateway.shutdown();
            if(i == m_gateways.size() - 1)
                Engine.wait(gateway);
        }

        m_gateways.removeAllElements();
    }

    /**
     * ���ڼ�Ȩ���кţ�����ϵͳ��Ʒ��ʱ�Ĳ�Ȩ����
     * @return ���ؼ���Ȩ�ɹ����Ĳ���ֵ
     */
    public boolean checkLicence()
    {
//        for(int i = 0; i < m_gateways.size() && isRunning(); i++)
//        {
//            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
//
//          //  Log.log("Center.checkLicence : Successed to check gateway(" + gateway.m_strName + ") licence !", 1L);
//        }

        return true;
    }

    /**
     * �����������ӵ�һЩ��Ҫ�������ɼ�Ȩ�����кţ�����ϵͳ��Ʒ��ʱ�Ĳ�Ȩ����
     * @param strBind
     * @param nPort
     * @param strName
     * @return �������ɵļ�Ȩ���к�
     */
    public String getLicence(String strBind, int nPort, String strName)
    {
        String strLicence = FileConfig.getLicence(strName);
        byte licence[] = Utility.toBytesValue(strLicence);
        if(licence == null || licence.length != 16)
            return null;
        byte m_key[] = setKey();
        for(int i = 0; i < 16; i++)
            licence[i] ^= Controller.m_key[i];

        strLicence = Utility.toHexString(licence);
        return strLicence;
    }

    /**
     * ���ڼ�Ȩ���кţ�����ϵͳ��Ʒ��ʱ�Ĳ�Ȩ����
     */
    public byte[] setKey()
    {
        ServiceProvider sp = new ServiceProvider("8888", "989898", m_admin.m_strAccount, m_admin.m_strPassword);
        long lTime = System.currentTimeMillis();
        if((lTime & 3L) == 0L)
        {
            CMPPConnect connect = sp.getCMPPConnect(0x20100, 1);
            Controller.m_key = connect.authenticator_sp;
        } else
        if((lTime & 3L) == 1L)
        {
            CMPPConnect connect = sp.getCMPPConnect(0x20400, 3);
            Controller.m_key = connect.authenticator_sp;
        } else
        if((lTime & 3L) == 2L)
        {
            CMPPConnect connect = sp.getCMPPConnect(0x20300, 1);
            Controller.m_key = connect.authenticator_sp;
        } else
        {
            CMPPConnect connect = sp.getCMPPConnect(0x20200, 1);
            Controller.m_key = connect.authenticator_sp;
        }
        return Controller.m_key;
    }

    /**
     * ���ڼ�Ȩ���кţ�����ϵͳ��Ʒ��ʱ�Ĳ�Ȩ����
     */
    public String recoverLicence(String strLicence)
    {
        byte licence[] = Utility.toBytesValue(strLicence);
        if(licence == null || licence.length <= 0)
            return null;
        for(int i = 0; i < 16; i++)
            licence[i] ^= Controller.m_key[i];

        return Utility.toHexString(licence);
    }

    /**
     * װ���������أ�����ϵͳ����ģʽ������Ӧ�����װ�ط���
     * @param center
     * @return ����װ�������Ƿ�ɹ��Ĳ���ֵ
     */
    public static boolean loadAllGateways(Center center)
    {
        if(m_nType == 1)
            return CMPPCenter.loadAllGateways((CMPPCenter)center);
        if(m_nType == 2)
            return UnionCenter.loadAllGateways((UnionCenter)center);
        if(m_nType == 3)
            return ExchangeCenter.loadAllGateways((ExchangeCenter)center);
        else
            return false;
    }

    /**
     * װ��ϵͳ��Center
     * @return ����װ�ص�Center����
     */
    public static Center loadCenter()
    {
        ProductConfig pc = FileConfig.loadProductConfig(); //center
        //�����Ʒ��Ϣ
        pc.dump(-1L);
        
        Administrator admin = FileConfig.loadAdministrator();//Administrator
        //��Administrator���л�ȡ������־�����ò�������ʼ����־��Ĺ���ģʽ
        Log.setLog(admin.m_strLog);
        
        //����ProductConfig���е�ϵͳ����ģʽ���ò�����װ����Ӧ��Center����
        if(pc.m_strType.equals("cmpp"))
        {
            m_nType = 1;
            return FileConfig.loadCMPPCenter(pc, admin);
        }
        if(pc.m_strType.equals("union"))
        {
            m_nType = 2;
            return FileConfig.loadUnionCenter(pc, admin);
        }
        if(pc.m_strType.equals("exchange"))
        {
            m_nType = 3;
            return FileConfig.loadExchangeCenter(pc, admin);
        } else
        {
            return null;
        }
    }


    /**
     * �������ļ���װ��һ�����ض��󲢷��أ������漰��ͬ���͵����أ����ò�ͬ��װ�ط�����
     * װ�����ض����ͬʱ��װ�����������صĻỰ���󣬾����������Ͳο�Gateway���µ�GatewayType��
     * @param gateway
     * @return װ�سɹ������ض���
     */
    public static GatewayEngine loadGateway(XMLTag gateway)
    {
        //װ���������ö���
        GatewayConfig gc = FileConfig.loadGatewayConfig(gateway);
        //װ����������SP��Ϣ����
        ServiceProvider sp = FileConfig.loadServiceProvider(gateway);

        if(gc.m_nType == 0x10010)
        {
            SMPPServerGateway server = FileConfig.loadSMPPServerGateway(gateway, gc, sp);
            if(server.loadAllSessions(gc.m_strHost))
                return server;
        } else
        if(GatewayType.isSMSC(gc.m_nType))
        {
            SMPPGateway smsc = FileConfig.loadSMPPGateway(gateway, gc, sp);
            if(smsc.loadAllSessions(gc.m_strHost))
                return smsc;
        } else
        if(gc.m_nType == 0x20000)
        {
            CMPPGateway standard = FileConfig.loadCMPPGateway(gateway, gc, sp);
            if(standard.loadAllSessions(gc.m_strHost))
                return standard;
        } else
        if(gc.m_nType == 0x20010)
        {
            CMPPServerGateway server = FileConfig.loadCMPPServerGateway(gateway, gc, sp);
            if(server.loadAllSessions(gc.m_strHost))
                return server;
        } else
        if(gc.m_nType == 0x20800)
        {
            MISCGateway misc = FileConfig.loadMISCGateway(gateway, gc, sp);
            if(misc.loadAllSessions(gc.m_strHost))
                return misc;
        } else
        if(gc.m_nType == 0x20200)
        {
            ASIAINFOGateway asiainfo = FileConfig.loadASIAINFOGateway(gateway, gc, sp);
            if(asiainfo.loadAllSessions(gc.m_strHost))
                return asiainfo;
        } else
        if(gc.m_nType == 0x20400)
        {
            BISPGateway bisp = FileConfig.loadBISPGateway(gateway, gc, sp);
            if(bisp.loadAllSessions(gc.m_strHost))
                return bisp;
        } else
        if(gc.m_nType == 0x20300)
        {
            INTRINSICGateway intrinsic = FileConfig.loadINTRINSICGateway(gateway, gc, sp);
            if(intrinsic.loadAllSessions(gc.m_strHost))
                return intrinsic;
        } else
        if(gc.m_nType == 0x20601)
        {
            NOKIAGateway nokia = FileConfig.loadNOKIAGateway(gateway, gc, sp);
            if(nokia.loadAllSessions(gc.m_strHost))
                return nokia;
        } else
        if(gc.m_nType == 0x20602)
        {
            NOKIACMPPGateway nokia = FileConfig.loadNOKIACMPPGateway(gateway, gc, sp);
            if(nokia.loadAllSessions(gc.m_strHost))
                return nokia;
        } else
        if(gc.m_nType == 0x20500)
        {
            SITECHGateway sitech = FileConfig.loadSITECHGateway(gateway, gc, sp);
            if(sitech.loadAllSessions(gc.m_strHost))
                return sitech;
        } else
        if(gc.m_nType == 0x20900)
        {
            HUAWEIGateway huawei = FileConfig.loadHUAWEIGateway(gateway, gc, sp);
            if(huawei.loadAllSessions(gc.m_strHost))
                return huawei;
        } else
        if(gc.m_nType == 0x20700)
        {
            TUOWEIGateway tuowei = FileConfig.loadTUOWEIGateway(gateway, gc, sp);
            if(tuowei.loadAllSessions(gc.m_strHost))
                return tuowei;
        } else
        if(gc.m_nType == 0x20100)
        {
            TSSXGateway tssx = FileConfig.loadTSSXGateway(gateway, gc, sp);
            if(tssx.loadAllSessions(gc.m_strHost))
                return tssx;
        } else
        if(gc.m_nType == 0x30000)
        {
            SGIPGateway sgip = FileConfig.loadSGIPGateway(gateway, gc, sp);
            if(sgip.loadAllSessions(gc.m_strHost))
                return sgip;
        } else
        if(gc.m_nType == 0x40000)
        {
            CNGPSCGateway cngp = FileConfig.loadCNGPSCGateway(gateway, gc, sp);
            if(cngp.loadAllSessions(gc.m_strHost))
                return cngp;
        } else
        if(gc.m_nType == 0x40001)
        {
            CNGPDCGateway cngp = FileConfig.loadCNGPDCGateway(gateway, gc, sp);
            if(cngp.loadAllSessions(gc.m_strHost))
                return cngp;
        } else
        if(gc.m_nType != 0x40002)
            Log.log("Center.loadGateway : gateway(" + gc.m_strName + ") not supported by system !", 1L);
        return null;
    }


    /**
     * ��ʾlog4j�Ƿ��ѳ�ʼ���ı�ʶ�������ⲿϵͳ�����û����в��ֵ���־��ʼ�����ⲿϵͳ������log4j��־ϵͳ��
     * û�кͱ�ϵͳʹ��ͬ������־ϵͳ
     */
    public static boolean _log4jConfiged = false;

    /**
     * ��ʼ���ⲿϵͳ��log4j
     * @param str log4j�����ļ�·��
     */
    public static void configLog4j(String str) {

        if (!_log4jConfiged) {
            PropertyConfigurator.configure(str);

            _log4jConfiged = true;
            Log.log("config log4j !", 1L);
        }
    }


    /**
     * ϵͳ�������
     * @param args
     */
    public static void main(String args[])
    {
        
	
            //�ж�ϵͳ����ʱ���봫�����
    	    if(args.length != 4)
            {
                //System.out.println("Usage : java LeadTone.Center.Center [config.xml] [log4j.properties] [config.properties]");
    	    	
            	//��supervise��run�ļ���ȥ��applicationContext.xml��config.properties����������ʡ�룬���磺bj
            	System.out.println("Usage : java LeadTone.Center.Center [config.xml] [log4j.properties] [transfer] [bj]");
                return;
            }
        	
            //����ʡ�룬��ȡ��ʡ��Ӧ��������Ϣ
            String provinceName = args[3];
            
            SmsService service = (SmsService) CustomBeanFactory.getBean("smsSubmitService");
            configData = service.querySmsConfigDataByProvinceName(provinceName);
            //�滻config.xml�е���������
            
            //װ�ر�ϵͳ������Ҫ�������ļ�
            m_config_file = args[0];
            if(!FileConfig.loadConfig(m_config_file))
            {
                Log.log("Center.main : fail to load config file !", 0x2000000000000001L);
                return;
            }

            //��ȡһЩ��������������Ϣ
            FileConfig.loadTimeConfig();
            
            FileConfig.loadLeadToneLogicConfig();
            LeadToneLogicConfig.dump();
            
            FileConfig.loadUpdateFinalResultConfig();
            UpdateFinalResultConfig.dump();

            FileConfig.loadBackupTableConfig();
            BackupTableConfig.dump();
            
            
            
            //Log.open("smsApp2","log");
            Log.open(provinceName,"log");
            Log.setLog(-1L);
        	
            //��ȡת������ַ
            Properties properties = ClassLoaderUtils.getProperties(args[2]); //[config.properties]
            httpUrl = properties.getProperty("HTTP_URI");
            if(httpUrl != null && !"".equals(httpUrl)){
            	needForward = true;
            }
        	
            if (LeadToneLogicConfig.LOGICSWITCH.equalsIgnoreCase("true")){
            //�ж�ϵͳ����ʱ����Ĵ������
            
                	
            //���������û����е��ⲿϵͳ������Hibernate������ڴ˶�ȡ�ⲿϵͳ�����Hibernate�����ļ�
            /*File applicationContextFile = new File(args[1]);
            if (!applicationContextFile.exists())
            {
                Log.log("Center.main : spring config file is not exist !", 0x2000000000000001L);
                return;
            }
            if (!applicationContextFile.canRead())
            {
                Log.log("Center.main : fail to load spring config file !", 0x2000000000000001L);
                return;
            }*/

            //���������û����е��ⲿϵͳʹ����log4j����־ϵͳ������ڴ˶�ȡ�ⲿϵͳ�����logj�������ļ�
            File log4jConfigFile = new File(args[1]);
            if (!log4jConfigFile.exists())
            {
                Log.log("Center.main : log4j config file is not exist !", 0x2000000000000001L);
                return;
            }
            if (!log4jConfigFile.canRead())
            {
                Log.log("Center.main : fail to load log4j config file !", 0x2000000000000001L);
                return;
            }

            try{

            //��ʼ���ⲿϵͳ�����log4j��Spring���������ⲿϵͳ��Spring�����Ƿ�ɹ���
            //���ɹ��Ļ���Ĭ��ǿ�ƹرմ����û����е���Ҫ�ⲿϵͳ֧�ֵĹ���
            Log.log("Center.main : Test Database Status Begin !", 0x2000000000000001L);
            configLog4j(log4jConfigFile.getPath());
           /* Log.log(" =========== MO CONFIG:"+applicationContextFile.getPath(), 0x2000000000000001L);
            ContextInitializeUtil.initialize(applicationContextFile.getPath());*/
            Log.log("Center.main : Test Database Status End !", 0x2000000000000001L);
            }catch(Exception e){
               LeadToneLogicConfig.LOGICSWITCH = "false";
               Log.log("Center.main : Test Database Status Error, ResendEmn and LeadToneLogic Function has been canceled !", 0x2000000000000001L);
               e.printStackTrace();
            }
            }





                //װ��Center����
                Center center = loadCenter();
                //װ���������ض���
                if(center == null || !loadAllGateways(center))
                {
                    Log.log("Center.main : fail to load Center !", 0x2000000000000001L);
                    return;
                }
                
                
                try
                { 

                //����ϵͳ��ϵͳ��������ʽΪ���νṹ���������ɳ���󣬳��򽫰���������ϵ��
                //�������̣߳��̼߳�Ҳ�м�⹦�ܣ���ĳһ���߳�ֹͣ���쳣��ֻ���������̣߳�
                //��Ӱ��ϵͳ��������
                center.startup();
                //�ȴ��߳��������
                Engine.wait(center);
                center.m_admin.sendMail("Information(" + center.m_pc.m_strAbbreviation + "):  system startup !", "Letter is from " + center.m_pc.m_strAuthorization + "\r\n" + "This message will be sent whenever system startup ! \r\n");
                //long lCheckLicenceTime = 0L;

                if(center.checkLicence()){
                    //���ϵͳ�Ƿ������У���ǿ�ƶ�������Դ���л���
                    for(; center.isRunning(); Engine.sleep()){
                       System.gc();
                    }
                }
                else{
                    Log.log("Center.main : invalid licence !", 0x2000000000000001L);
                center.shutdown();
                Engine.wait(center);
                }
                }catch(Exception e)
                {
                    Log.log(e);
                }
                finally
                {
                    center.shutdown();
                    Engine.wait(center);
                } 
                
                
                if(!m_bNeedTerminate){
                    center.m_admin.sendMail("Alert(" + center.m_pc.m_strAbbreviation + ") : unexpected restart for this system !", "Letter is from " + center.m_pc.m_strAuthorization + "\r\n" + "This message will be sent whenever system running thread stopped ! \r\n" + "The last exception is : " + Log.getLastException() + "\r\n" + "System will try to restart up !\r\n");
                }
                else{
                    center.m_admin.sendMail("Information(" + center.m_pc.m_strAbbreviation + "):  system shutdown !", "Letter is from " + center.m_pc.m_strAuthorization + "\r\n" + "This message will be sent whenever system shutdown ! \r\n");
                }

                
                
                if(!m_bNeedTerminate){
                    Log.log("Center.main : unexpected exit !", 0x2000000000000001L);
                }else{
                    Log.close();
                    return;
                }

    }
    
    //���Ӳ�����������������
    public static void main()
    {
    
    		m_config_file = Constant.CONFIG_PATH + Constant.SMS_FILE;
            if(!FileConfig.loadConfig(m_config_file))
            {
                Log.log("Center.main : fail to load config file !", 0x2000000000000001L);
                return;
            }

            //��ȡһЩ��������������Ϣ
            FileConfig.loadTimeConfig();
            
            FileConfig.loadLeadToneLogicConfig();
            LeadToneLogicConfig.dump();
            
            FileConfig.loadUpdateFinalResultConfig();
            UpdateFinalResultConfig.dump();

            FileConfig.loadBackupTableConfig();
            BackupTableConfig.dump();
            
         
        	Properties properties = ClassLoaderUtils.getProperties(Constant.CONFIG_PATH+Constant.LOGNAME_CONFIG_FILE); //[config.properties]
        	log_file_name = properties.getProperty("log.MT.fileName", "smsApp2");
        	
            Log.open(log_file_name,"log");
            Log.setLog(-1L);
        	
        	
            if (LeadToneLogicConfig.LOGICSWITCH.equalsIgnoreCase("true")){
            //�ж�ϵͳ����ʱ����Ĵ������
            /*if(args.length != 3){
                System.out.println("Usage : java LeadTone.Center.Center [config.xml] [log4j.properties]  [config.properties]");
                return;
            }*/
                	
            
            //���������û����е��ⲿϵͳʹ����log4j����־ϵͳ������ڴ˶�ȡ�ⲿϵͳ�����logj�������ļ�
            File log4jConfigFile = new File(Constant.CONFIG_PATH+Constant.LOG4J_CONFIG_FILE);
            if (!log4jConfigFile.exists())
            {
                Log.log("Center.main : log4j config file is not exist !", 0x2000000000000001L);
                return;
            }
            if (!log4jConfigFile.canRead())
            {
                Log.log("Center.main : fail to load log4j config file !", 0x2000000000000001L);
                return;
            }

            try{

            //��ʼ���ⲿϵͳ�����log4j��Spring���������ⲿϵͳ��Spring�����Ƿ�ɹ���
            //���ɹ��Ļ���Ĭ��ǿ�ƹرմ����û����е���Ҫ�ⲿϵͳ֧�ֵĹ���
            Log.log("Center.main : Test Database Status Begin !", 0x2000000000000001L);
            configLog4j(log4jConfigFile.getPath());
           /* Log.log(" =========== MO CONFIG:"+applicationContextFile.getPath(), 0x2000000000000001L);
            ContextInitializeUtil.initialize(applicationContextFile.getPath());*/
            Log.log("Center.main : Test Database Status End !", 0x2000000000000001L);
            }catch(Exception e){
               LeadToneLogicConfig.LOGICSWITCH = "false";
               Log.log("Center.main : Test Database Status Error, ResendEmn and LeadToneLogic Function has been canceled !", 0x2000000000000001L);
               e.printStackTrace();
            }
            }

                //װ��Center����
                Center center = loadCenter();
                //װ���������ض���
                if(center == null || !loadAllGateways(center))
                {
                    Log.log("Center.main : fail to load Center !", 0x2000000000000001L);
                    return;
                }
                
                
                try
                { 

                //����ϵͳ��ϵͳ��������ʽΪ���νṹ���������ɳ���󣬳��򽫰���������ϵ��
                //�������̣߳��̼߳�Ҳ�м�⹦�ܣ���ĳһ���߳�ֹͣ���쳣��ֻ���������̣߳�
                //��Ӱ��ϵͳ��������
                center.startup();
                //�ȴ��߳��������
                Engine.wait(center);
                center.m_admin.sendMail("Information(" + center.m_pc.m_strAbbreviation + "):  system startup !", "Letter is from " + center.m_pc.m_strAuthorization + "\r\n" + "This message will be sent whenever system startup ! \r\n");
                //long lCheckLicenceTime = 0L;

                if(center.checkLicence()){
                    //���ϵͳ�Ƿ������У���ǿ�ƶ�������Դ���л���
                    for(; center.isRunning(); Engine.sleep()){
                       System.gc();
                    }
                }
                else{
                    Log.log("Center.main : invalid licence !", 0x2000000000000001L);
                center.shutdown();
                Engine.wait(center);
                }
                }catch(Exception e)
                {
                    Log.log(e);
                }
                finally
                {
                    center.shutdown();
                    Engine.wait(center);
                } 
                
                if(!m_bNeedTerminate){
                    center.m_admin.sendMail("Alert(" + center.m_pc.m_strAbbreviation + ") : unexpected restart for this system !", "Letter is from " + center.m_pc.m_strAuthorization + "\r\n" + "This message will be sent whenever system running thread stopped ! \r\n" + "The last exception is : " + Log.getLastException() + "\r\n" + "System will try to restart up !\r\n");
                }
                else{
                    center.m_admin.sendMail("Information(" + center.m_pc.m_strAbbreviation + "):  system shutdown !", "Letter is from " + center.m_pc.m_strAuthorization + "\r\n" + "This message will be sent whenever system shutdown ! \r\n");
                }

                
                
                if(!m_bNeedTerminate){
                    Log.log("Center.main : unexpected exit !", 0x2000000000000001L);
                }else{
                    Log.close();
                    return;
                }

    }

}
