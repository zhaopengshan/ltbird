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
 * 系统中心类，也是系统的根节点，由子类继承实现不同的工作模式
 * 具体工作模式的解释参考具体的子类描述
 */
public class Center extends Engine
{
    /**
     * 系统CMPP_CENTER工作模式编号
     */
    public static final int CMPP_CENTER = 1;
    /**
     * 系统UNION_CENTER工作模式编号
     */
    public static final int UNION_CENTER = 2;
    /**
     * 系统EXCHANGE_CENTER工作模式编号
     */
    public static final int EXCHANGE_CENTER = 3;

    /**
     * 网关用监听器的默认工作频率时限
     */
    static final int DEFAULT_LISTEN_TIME = 1000;
    /**
     * 系统是否需要启动鉴权的标识，用于系统产品化时的产权保护
     */
    static final boolean CHECK_LISCENCE = false;
    /**
     * 产品信息对象 Center
     */
    public ProductConfig m_pc;
    /**
     * 系统管理功能附属功能的基本配置项对象
     */
    public Administrator m_admin;
    /**
     * 系统管理端口对象
     */
    ServerSocket m_socket;
    /**
     * 系统容许的最大流量
     */
    public static int m_nMaxFlux = 0;
    /**
     * 系统工作时的最大峰值
     */
    public static int m_nPeakFlux = 0;
    /**
     * 系统工作时的当前流量
     */
    public static int m_nCurrFlux = 0;
    /**
     * 系统启动时选择的工作模式的标识
     */
    public static int m_nType = 1;
    /**
     * 系统是否需要关闭的标识
     */
    public static boolean m_bNeedTerminate = false;
    /**
     * 系统中包含的所有网关对象
     */
    public static Vector m_gateways = new Vector();
    /**
     * 系统配置文件 config.xml
     */
    public static String m_config_file = null;
    
    //日志文件名
    public static String log_file_name = "smsApp2";
    
    //短信参数配置对象
    static ConfigData configData;
    
    //转发机参数
    static boolean needForward = false;
    //转发机ip地址
    static String httpUrl = null;

    /**
     * 构造方法初始化类变量
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
     * 输出当前系统的工作状态参数
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
     * 清空系统使用的资源
     */
    public void empty()
    {
        m_admin = null;
        m_socket = null;
        m_gateways.removeAllElements();
        m_gateways = null;
    }

    /**
     * 关闭管理端口
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
     * 生成描述网关状态的XML文件，用于向基于XML的外部通信接口提供网关工作状态数据
     * @param gateway_name
     * @return 生成的描述网关状态的XML文件
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
     * 遍历所有网关，生成网关数据的XML描述，用于外部基于XML的接口数据交换
     * @return 生成网关数据的XML描述
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
     * 遍历所有可发送或可接收的网关，生成遍历所有网关的查询用SQL
     * @return 生成遍历所有网关的查询用SQL
     */
    public static String generateWhere()
    {
        if(m_gateways.size() <= 0)
            return new String("ih_gateway = 'unavailable'");
        String strWhere = new String();
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            //遍历所有可发送或可接收的网关，生成遍历所有网关的查询用SQL
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
     * 初始化管理端口，启动管理线程Controller
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
     * 系统总体容量统计，将所有网关的容量数据汇总到系统容量
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
     * 检查所有的网关工作情况，并发送报警邮件
     * @return 返回检查网关工作情况的结果
     */
    public boolean checkGateway()
    {
        for(int i = 0; i < m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)m_gateways.elementAt(i);
            //如果网关停止工作并且邮件提示功能开关开启，则发送报警邮件，提示网关已经停止工作，并返回false,如果邮件提示功能开关关闭的话返回true
            if(gateway.isStopped() && m_admin.mail_switch)
            {
                m_admin.sendMail("Emergency(" + m_pc.m_strAbbreviation + ") : Gateway(" + gateway.m_strName + ") has stopped working !", "Letter is from " + m_pc.m_strAuthorization + "\r\n" + "Gateway(" + gateway.m_strName + ") has stopped working ! \r\n" + "The last exception is : " + Log.getLastException() + "\r\n" + "Please check the error as soon as possible !\r\n");
                return false;
            }
            //如果网关超时并且邮件提示功能开关开启，判断为网关工作异常，则发送报警邮件，提示网关工作异常，并返回true
            if(gateway.isExceptionTimeout() && m_admin.mail_switch)
            {
                m_admin.sendMail("Emergency(" + m_pc.m_strAbbreviation + ") : Gateway(" + gateway.m_strName + ") is abnormal !", "Letter is from " + m_pc.m_strAuthorization + "\r\n" + "Gateway(" + gateway.m_strName + ") is abnormal ! \r\n" + "The last exception is : " + Log.getLastException() + "\r\n" + "Please check the error as soon as possible !\r\n");
                gateway.delayExceptionTimeout();
            }
        }

        return true;
    }

    /**
     * 启动所有网关
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
     * 关闭所有启动的网关
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
     * 用于鉴权序列号，用于系统产品化时的产权保护
     * @return 返回检查鉴权成功与否的布尔值
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
     * 根据网关连接的一些必要参数生成鉴权用序列号，用于系统产品化时的产权保护
     * @param strBind
     * @param nPort
     * @param strName
     * @return 返回生成的鉴权序列号
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
     * 用于鉴权序列号，用于系统产品化时的产权保护
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
     * 用于鉴权序列号，用于系统产品化时的产权保护
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
     * 装载所有网关，根据系统工作模式调用相应子类的装载方法
     * @param center
     * @return 返回装载网关是否成功的布尔值
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
     * 装载系统类Center
     * @return 返回装载的Center对象
     */
    public static Center loadCenter()
    {
        ProductConfig pc = FileConfig.loadProductConfig(); //center
        //输出产品信息
        pc.dump(-1L);
        
        Administrator admin = FileConfig.loadAdministrator();//Administrator
        //从Administrator类中获取关于日志的配置参数，初始化日志类的工作模式
        Log.setLog(admin.m_strLog);
        
        //根据ProductConfig类中的系统工作模式配置参数，装载相应的Center对象
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
     * 从配置文件中装载一个网关对象并返回，其中涉及不同类型的网关，调用不同的装载方法，
     * 装载网关对象的同时，装载隶属于网关的会话对象，具体网关类型参考Gateway包下的GatewayType类
     * @param gateway
     * @return 装载成功的网关对象
     */
    public static GatewayEngine loadGateway(XMLTag gateway)
    {
        //装载网关配置对象
        GatewayConfig gc = FileConfig.loadGatewayConfig(gateway);
        //装载网关连接SP信息对象
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
     * 表示log4j是否已初始化的标识，用于外部系统处理用户上行部分的日志初始化，外部系统采用了log4j日志系统，
     * 没有和本系统使用同样的日志系统
     */
    public static boolean _log4jConfiged = false;

    /**
     * 初始化外部系统的log4j
     * @param str log4j配置文件路径
     */
    public static void configLog4j(String str) {

        if (!_log4jConfiged) {
            PropertyConfigurator.configure(str);

            _log4jConfiged = true;
            Log.log("config log4j !", 1L);
        }
    }


    /**
     * 系统主进入点
     * @param args
     */
    public static void main(String args[])
    {
        
	
            //判断系统启动时必须传入参数
    	    if(args.length != 4)
            {
                //System.out.println("Usage : java LeadTone.Center.Center [config.xml] [log4j.properties] [config.properties]");
    	    	
            	//在supervise的run文件中去掉applicationContext.xml、config.properties参数，增加省码，例如：bj
            	System.out.println("Usage : java LeadTone.Center.Center [config.xml] [log4j.properties] [transfer] [bj]");
                return;
            }
        	
            //根据省码，获取该省对应的网关信息
            String provinceName = args[3];
            
            SmsService service = (SmsService) CustomBeanFactory.getBean("smsSubmitService");
            configData = service.querySmsConfigDataByProvinceName(provinceName);
            //替换config.xml中的网关数据
            
            //装载本系统启动需要的配置文件
            m_config_file = args[0];
            if(!FileConfig.loadConfig(m_config_file))
            {
                Log.log("Center.main : fail to load config file !", 0x2000000000000001L);
                return;
            }

            //读取一些基本常量配置信息
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
        	
            //获取转发机地址
            Properties properties = ClassLoaderUtils.getProperties(args[2]); //[config.properties]
            httpUrl = properties.getProperty("HTTP_URI");
            if(httpUrl != null && !"".equals(httpUrl)){
            	needForward = true;
            }
        	
            if (LeadToneLogicConfig.LOGICSWITCH.equalsIgnoreCase("true")){
            //判断系统启动时必须的传入参数
            
                	
            //由于用于用户上行的外部系统采用了Hibernate，因此在此读取外部系统所需的Hibernate配置文件
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

            //由于用于用户上行的外部系统使用了log4j的日志系统，因此在此读取外部系统所需的logj的配置文件
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

            //初始化外部系统所需的log4j和Spring，并测试外部系统的Spring启动是否成功，
            //不成功的话则默认强制关闭处理用户上行的需要外部系统支持的功能
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





                //装载Center对象
                Center center = loadCenter();
                //装载所有网关对象
                if(center == null || !loadAllGateways(center))
                {
                    Log.log("Center.main : fail to load Center !", 0x2000000000000001L);
                    return;
                }
                
                
                try
                { 

                //启动系统，系统的启动方式为树形结构，启动主干程序后，程序将按照隶属关系，
                //启动子线程，线程间也有检测功能，当某一子线程停止或异常将只重启该子线程，
                //不影响系统整体运行
                center.startup();
                //等待线程启动完毕
                Engine.wait(center);
                center.m_admin.sendMail("Information(" + center.m_pc.m_strAbbreviation + "):  system startup !", "Letter is from " + center.m_pc.m_strAuthorization + "\r\n" + "This message will be sent whenever system startup ! \r\n");
                //long lCheckLicenceTime = 0L;

                if(center.checkLicence()){
                    //检查系统是否在运行，并强制对垃圾资源进行回收
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
    
    //增加不带参数的启动方法
    public static void main()
    {
    
    		m_config_file = Constant.CONFIG_PATH + Constant.SMS_FILE;
            if(!FileConfig.loadConfig(m_config_file))
            {
                Log.log("Center.main : fail to load config file !", 0x2000000000000001L);
                return;
            }

            //读取一些基本常量配置信息
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
            //判断系统启动时必须的传入参数
            /*if(args.length != 3){
                System.out.println("Usage : java LeadTone.Center.Center [config.xml] [log4j.properties]  [config.properties]");
                return;
            }*/
                	
            
            //由于用于用户上行的外部系统使用了log4j的日志系统，因此在此读取外部系统所需的logj的配置文件
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

            //初始化外部系统所需的log4j和Spring，并测试外部系统的Spring启动是否成功，
            //不成功的话则默认强制关闭处理用户上行的需要外部系统支持的功能
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

                //装载Center对象
                Center center = loadCenter();
                //装载所有网关对象
                if(center == null || !loadAllGateways(center))
                {
                    Log.log("Center.main : fail to load Center !", 0x2000000000000001L);
                    return;
                }
                
                
                try
                { 

                //启动系统，系统的启动方式为树形结构，启动主干程序后，程序将按照隶属关系，
                //启动子线程，线程间也有检测功能，当某一子线程停止或异常将只重启该子线程，
                //不影响系统整体运行
                center.startup();
                //等待线程启动完毕
                Engine.wait(center);
                center.m_admin.sendMail("Information(" + center.m_pc.m_strAbbreviation + "):  system startup !", "Letter is from " + center.m_pc.m_strAuthorization + "\r\n" + "This message will be sent whenever system startup ! \r\n");
                //long lCheckLicenceTime = 0L;

                if(center.checkLicence()){
                    //检查系统是否在运行，并强制对垃圾资源进行回收
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
