package LeadTone.Center;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import LeadTone.Log;
import LeadTone.TimeConfig;
import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.CMPPDatabase.CMPPDeliverDatabase;
import LeadTone.CMPPDatabase.CMPPQueryDatabase;
import LeadTone.CMPPDatabase.CMPPSubmitDatabase;
import LeadTone.Database.DatabaseConfig;
import LeadTone.Gateway.ASIAINFOGateway;
import LeadTone.Gateway.BISPGateway;
import LeadTone.Gateway.CMPPGateway;
import LeadTone.Gateway.CMPPServerGateway;
import LeadTone.Gateway.CNGPDCGateway;
import LeadTone.Gateway.CNGPSCGateway;
import LeadTone.Gateway.GatewayConfig;
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
import LeadTone.Port.CMPPXMLExchanger;
import LeadTone.Session.BindType;
import LeadTone.Session.SessionConfig;
import LeadTone.XML.XMLAttribute;
import LeadTone.XML.XMLParser;
import LeadTone.XML.XMLTag;




public class FileConfig
{

    public static XMLTag m_root = null;
    public static DatabaseConfig dc = null;

    public FileConfig()
    {
    }

    public static boolean loadConfig(String strFileName)
    {
        System.out.println("FileConfig.loadConfig : open file " + strFileName);
        try
        {
            String strContent = "";
            FileReader fr = new FileReader(strFileName);
            BufferedReader br = new BufferedReader(fr);
            for(String strLine = null; (strLine = br.readLine()) != null;)
                strContent = strContent + strLine;

            br.close();
            fr.close();
            m_root = XMLParser.parse(strContent);
            if(m_root != null)
            {
                System.out.println("FileConfig.loadConfig : config file loaded !");
                return true;
            }
            System.out.println("FileConfig.loadConfig : invalid config file !");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("FileConfig.loadConfig : unexpected exit !");
        }
        return false;
    }


    public static ProductConfig loadProductConfig()
    {
        ProductConfig pc = new ProductConfig();
        pc.m_strType = XMLParser.getString(m_root, "\\center.type", "union");
        pc.m_strVersion = XMLParser.getString(m_root, "\\center.version", "2.0");
        pc.m_strRelease = XMLParser.getString(m_root, "\\center.release", "2006-03-13 15:43:05");
        pc.m_strAuthorization = XMLParser.getString(m_root, "\\center\\authorization", "Unknown Company");
        pc.m_strAbbreviation = XMLParser.getString(m_root, "\\center\\authorization.abbreviation", "unknown");
        pc.m_strCompanySite = XMLParser.getString(m_root, "\\center\\company-site", "www.leadtone.com");
        pc.m_strTechnologySite = XMLParser.getString(m_root, "\\center\\technology-site", "www.leadtone.com");
        pc.m_strTelephone = XMLParser.getString(m_root, "\\center\\telephone", "86-01-64390136");
        pc.m_strEMail = XMLParser.getString(m_root, "\\center\\email", "support@leadtone.com");
        pc.m_strComment = XMLParser.getString(m_root, "\\center\\comment", "Once encounting problems, please contact us ASAP !");
        return pc;
    }

    public static DatabaseConfig loadDatabaseConfig()
    {
        DatabaseConfig dc = new DatabaseConfig();
        String strType = XMLParser.getString(m_root, "\\database.type", "mysql");
        dc.m_nType = DatabaseConfig.toType(strType);
        dc.m_strDriver = XMLParser.getString(m_root, "\\database\\jdbc\\driver", "org.gjt.mm.mysql.Driver");
        dc.m_strURL = XMLParser.getString(m_root, "\\database\\jdbc\\url", "jdbc:mysql://localhost/cmppe");
        dc.m_strAccount = XMLParser.getString(m_root, "\\database\\jdbc\\account", "leadtone");
        dc.m_strPassword = XMLParser.getString(m_root, "\\database\\jdbc\\password", "leadtone");
        dc.m_nMaxErrorCount = XMLParser.getInteger(m_root, "\\database.error", 5);
        dc.m_nMaxUseCount = XMLParser.getInteger(m_root, "\\database.count", 10);
        return dc;
    }

    public static CMPPXMLExchanger loadXMLExchanger()
    {
        try
        {	//建立监听端口：服务端以该端口建立socket连接，供调度服务监听。
        	//调度已不再监听listen端口，改为Gw_port端口 yuqian.20130207
        	int nPort = XMLParser.getInteger(m_root, "\\database.port", 5678);
        	/*if(Center.configData != null && Center.configData.getListen_port() != 0){
        		nPort = Center.configData.getListen_port();
        	}*/
        	if(Center.configData != null && Center.configData.getGw_port() != 0){
        		nPort = Center.configData.getGw_port();
        	}
            CMPPXMLExchanger exchanger = new CMPPXMLExchanger(nPort);
            return exchanger;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        System.out.println("FileConfig.loadXMLExchanger : unexpected exit !");
        return null;
    }

    public static CMPPDatabase loadCMPPDatabase()
    {
        dc = loadDatabaseConfig();
        int nSubmit = XMLParser.getInteger(m_root, "\\database\\pipe\\submit", 1);
        int nDeliver = XMLParser.getInteger(m_root, "\\database\\pipe\\deliver", 1);
        int nQuery = XMLParser.getInteger(m_root, "\\database\\pipe\\query", 1);
        String submit_input_switch = XMLParser.getString(m_root, "\\database\\switch\\input\\submit", "on");
        CMPPSubmitDatabase.m_input_switch = submit_input_switch.equalsIgnoreCase("on");
        String submit_output_switch = XMLParser.getString(m_root, "\\database\\switch\\output\\submit", "on");
        CMPPSubmitDatabase.m_output_switch = submit_output_switch.equalsIgnoreCase("on");
        String submit_clean_switch = XMLParser.getString(m_root, "\\database\\switch\\clean\\submit", "on");
        CMPPSubmitDatabase.m_clean_switch = submit_clean_switch.equalsIgnoreCase("on");
        
        String deliver_input_switch = XMLParser.getString(m_root, "\\database\\switch\\input\\deliver", "on");
        CMPPDeliverDatabase.m_input_switch = deliver_input_switch.equalsIgnoreCase("on");
        String deliver_output_switch = XMLParser.getString(m_root, "\\database\\switch\\output\\deliver", "on");
        CMPPDeliverDatabase.m_output_switch = deliver_output_switch.equalsIgnoreCase("on");
        
        String query_input_switch = XMLParser.getString(m_root, "\\database\\switch\\input\\query", "on");
        CMPPQueryDatabase.m_input_switch = query_input_switch.equalsIgnoreCase("on");
        String query_output_switch = XMLParser.getString(m_root, "\\database\\switch\\output\\query", "on");
        CMPPQueryDatabase.m_output_switch = query_output_switch.equalsIgnoreCase("on");
        
        try
        {
            CMPPDatabase database = new CMPPDatabase(dc, nSubmit, nDeliver, nQuery);
            return database;
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("FileConfig.loadCMPPDatabase : unexpected exit !");
        return null;
    }

    public static void loadTimeConfig()
    {
        TimeConfig.DEFAULT_SURVIVE_TIME = XMLParser.getLong(m_root, "\\time\\packet\\survive", 5000L);
        TimeConfig.DEFAULT_WAIT_TIME = XMLParser.getLong(m_root, "\\time\\engine\\wait", 10000L);
        TimeConfig.DEFAULT_NAP_TIME = XMLParser.getLong(m_root, "\\time\\engine\\nap", 5L);
        TimeConfig.DEFAULT_NAP_SUBMIT_TIME = XMLParser.getLong(m_root, "\\time\\engine\\nap_submit", 500L);
        TimeConfig.DEFAULT_NAP_DELIVER_TIME = XMLParser.getLong(m_root, "\\time\\engine\\nap_deliver", 500L);
        TimeConfig.DEFAULT_NAP_QUERY_TIME = XMLParser.getLong(m_root, "\\time\\engine\\nap_query", 500L);
        TimeConfig.DEFAULT_SUBMIT_CLEAN_TIMEOUT = XMLParser.getLong(m_root, "\\time\\engine\\nap_submit_clean", 3600000L);
        TimeConfig.DEFAULT_SLEEP_TIME = XMLParser.getLong(m_root, "\\time\\engine\\sleep", 1000L);
        TimeConfig.DEFAULT_SOCKET_TIMEOUT = XMLParser.getLong(m_root, "\\time\\session\\socket", 15000L);
        TimeConfig.DEFAULT_LISTEN_TIMEOUT = XMLParser.getLong(m_root, "\\time\\session\\listen", 1000L);
        TimeConfig.DEFAULT_PACKET_TIMEOUT = XMLParser.getLong(m_root, "\\time\\session\\packet", 60000L);
        TimeConfig.DEFAULT_MESSAGE_TIMEOUT = XMLParser.getLong(m_root, "\\time\\session\\message", 0xdbba0L);
        TimeConfig.DEFAULT_ACTIVETEST_TIMEOUT = XMLParser.getLong(m_root, "\\time\\session\\activetest", 30000L);
        TimeConfig.DEFAULT_EXCEPTION_TIMEOUT = XMLParser.getLong(m_root, "\\time\\gateway\\exception", 0x1b7740L);
        TimeConfig.DEFAULT_DATABASE_TIMEOUT = XMLParser.getLong(m_root, "\\time\\database\\connection", 0x1d4c0L);
        TimeConfig.DEFAULT_RETRY_TIMEOUT = XMLParser.getLong(m_root, "\\time\\database\\retry", 30000L);
    }

    public static void loadLeadToneLogicConfig()
    {
        LeadToneLogicConfig.LOGICSWITCH = XMLParser.getString(m_root, "\\leadtonelogic.switch", "true");
    }


     public static void loadUpdateFinalResultConfig()
    {
        UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH = XMLParser.getString(m_root, "\\updatefinalresult.switch", "true"); 
    }

     public static void loadBackupTableConfig()
    {
        BackupTableConfig.DYNAMICBACKUPTABLE = XMLParser.getString(m_root, "\\dynamicbackuptable.switch", "true"); 
    }

    public static Administrator loadAdministrator()
    {
        Administrator admin = new Administrator();
        admin.m_nPort = XMLParser.getInteger(m_root, "\\administrator.listen", 9000);
        admin.m_strAccount = XMLParser.getString(m_root, "\\administrator.account", null);
        admin.m_strPassword = XMLParser.getString(m_root, "\\administrator.password", null);
        admin.m_strSMTPHost = XMLParser.getString(m_root, "\\administrator\\email.smtp", "smtp.21cn.com");
        admin.m_nSMTPPort = XMLParser.getInteger(m_root, "\\administrator\\email.port", 25);
        String temp = XMLParser.getString(m_root, "\\administrator\\email.switch", "off");
        if(temp.equalsIgnoreCase("on"))
            admin.mail_switch = true;
        else
            admin.mail_switch = false;
        temp = XMLParser.getString(m_root, "\\administrator\\email.needauth", "false");
        if(temp.equalsIgnoreCase("true"))
            admin.need_auth = true;
        else
            admin.need_auth = false;
        admin.m_strFrom = XMLParser.getString(m_root, "\\administrator\\email\\from", "support@leadtone.com");
        admin.m_strMailAccount = XMLParser.getString(m_root, "\\administrator\\email\\from.account", "support@leadtone.com");
        admin.m_strMailPassword = XMLParser.getString(m_root, "\\administrator\\email\\from.password", "leadtone");
        admin.m_strTo = XMLParser.getString(m_root, "\\administrator\\email\\to", "han@leadtone.com");
        admin.m_strMobile = XMLParser.getString(m_root, "\\administrator\\mobile", "13910139946");
        admin.m_strLog = XMLParser.getString(m_root, "\\administrator\\log", "LOG_ALL");
        return admin;
    }

    public static ServiceProvider loadServiceProvider(XMLTag gateway)
    {
        ServiceProvider sp = new ServiceProvider();
        sp.system_id = XMLParser.getString(gateway, "\\service-provider\\system-id", null);
//        sp.password = XMLParser.getString(gateway, "\\service-provider\\password", null);
        sp.system_type = XMLParser.getString(gateway, "\\service-provider\\system-type", null);
        sp.interface_version = XMLParser.getByte(gateway, "\\service-provider\\interface-version", (byte)52);
        sp.addr_ton = XMLParser.getByte(gateway, "\\service-provider\\addr-ton", (byte)0);
        sp.addr_npi = XMLParser.getByte(gateway, "\\service-provider\\addr-npi", (byte)0);
        sp.address_range = XMLParser.getString(gateway, "\\service-provider\\address-range", null);
        
        if(Center.configData == null) {
        	sp.service_code = XMLParser.getString(gateway, "\\service-provider\\service-code", null);
        	sp.enterprise_code = XMLParser.getString(gateway, "\\service-provider\\enterprise-code", null);
	        sp.user = XMLParser.getString(gateway, "\\service-provider\\user", null);
	        sp.password = XMLParser.getString(gateway, "\\service-provider\\password", null);
        } else {
        	sp.service_code = Center.configData.getSenderCode();
        	sp.enterprise_code = Center.configData.getEnterprise_code();
        	sp.user = Center.configData.getGw_user();
        	sp.password = Center.configData.getGw_passwd();
        	
        }
        if(sp.enterprise_code == null || sp.enterprise_code.equals(""))
        {
            sp.enterprise_code = sp.system_id;
            if(sp.service_code == null || sp.service_code.equals(""))
                sp.enterprise_code += sp.password;
        }
        return sp;
    }

    public static XMLTag getConnect(XMLTag gateway, String strType)
    {
        return XMLParser.getTag(gateway, "connect", new XMLAttribute("bind", strType));
    }

    public static SessionConfig loadSessionConfig(XMLTag connect)
    {
        SessionConfig sc = new SessionConfig();
        sc.m_nCount = XMLParser.getInteger(connect, ".count", 1);
        String strType = XMLParser.getString(connect, ".bind", "transmitter");
        sc.m_nType = BindType.toType(strType);
        if(Center.configData == null) {

        	sc.m_nMaxFlux = XMLParser.getInteger(connect, ".flux", 65535);
        } else {

        	sc.m_nMaxFlux = Integer.parseInt(Center.configData.getSendSpeed());
        }
        
        //根据配置文件，选择使用直连还是连接转发机，true为转发机，false为直连
        //连接跳板机或网关，都属于直连方式，所以配置文件中【HTTP_URI】一直为空，此时会使用gw_port的数值。yuqian 20130124
        if(Center.needForward && Center.configData != null){
        	//连接转发机的映射端口,即：把package投递到转发机ip+端口上
        	sc.m_nPort = Center.configData.getGw_port();
        }else{
        	//连接网关端口        	
        	sc.m_nPort = Center.configData.getGw_port();
        }
        
        sc.m_nQueueSize = XMLParser.getInteger(connect, ".queue", 0);
        
        if(sc.m_nMaxFlux <= 0)
            sc.m_nMaxFlux = 65535;
        sc.m_nMaxErrorCount = XMLParser.getInteger(connect, ".error", 3);
        sc.m_lPacketTimeout = XMLParser.getLong(connect, "\\timeout\\packet", TimeConfig.DEFAULT_PACKET_TIMEOUT);
        sc.m_lMessageTimeout = XMLParser.getLong(connect, "\\timeout\\message", TimeConfig.DEFAULT_MESSAGE_TIMEOUT);
        sc.m_lActiveTestTimeout = XMLParser.getLong(connect, "\\timeout\\activetest", TimeConfig.DEFAULT_ACTIVETEST_TIMEOUT);
        return sc;
    }

    public static GatewayConfig loadGatewayConfig(XMLTag gateway)
    {
        GatewayConfig gc = new GatewayConfig();
        String strType = XMLParser.getString(gateway, ".type", null);
        if(strType != null)
            gc.m_nType = GatewayType.toType(strType);
        gc.m_nExchanger = XMLParser.getInteger(gateway, ".exchange", 1);
        if(Center.configData == null) {
        	gc.m_strName = XMLParser.getString(gateway, ".name", null);
        	//gc.m_strHost = XMLParser.getString(gateway, ".host", null);
        } else {
        	gc.m_strName = Center.configData.getProvince_code();
        }
        
        //根据配置文件内容，选择使用直连还是连接转发机，true为转发机，false为直连
        if(Center.needForward){
        	gc.m_strHost = Center.httpUrl;
        }else{
        	gc.m_strHost = Center.configData.getGw_ip();
        }
        gc.m_strBind = XMLParser.getString(gateway, ".bind", null);
        gc.m_strLicence = XMLParser.getString(gateway, ".licence", null);
        ServiceProvider sp = loadServiceProvider(gateway);
        if(gc.m_nType != 0x50000 && gc.m_nType != 0x50001 && gc.m_nType != 0x50002)
            gc.enterprise_code = sp.enterprise_code;
        else
            gc.enterprise_code = sp.service_code;
        gc.service_code = sp.service_code;
        return gc;
    }

    public static Vector getAllGateways()
    {
        return XMLParser.getTags(m_root, "gateway");
    }

    public static XMLTag getGateway(String strName)
    {
        return XMLParser.getTag(m_root, "gateway", new XMLAttribute("name", strName));
    }

    public static String getLicence(String strName)
    {
        XMLTag gateway = getGateway(strName);
        if(gateway != null)
            return XMLParser.getString(gateway, ".licence", null);
        else
            return null;
    }

    public static CMPPCenter loadCMPPCenter(ProductConfig pc, Administrator admin)
    {
        CMPPDatabase database = loadCMPPDatabase();
        if(database == null)
        {
            Log.log("FileConfig.loadCMPPCenter : fail to load database !", 0x2000000000000001L);
            return null;
        }
        CMPPXMLExchanger exchanger = loadXMLExchanger();
        if(exchanger == null)
        {
            Log.log("FileConfig.loadCMPPCenter : fail to load xml exchanger !", 0x2000000000000001L);
            return null;
        }
        try
        {
            CMPPCenter center = new CMPPCenter(pc, admin);
            center.m_database = database;
            center.m_exchanger = exchanger;
            return center;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("FileConfig.loadCMPPCenter : unexpected exit !", 0x2000000000000001L);
        return null;
    }

    public static UnionCenter loadUnionCenter(ProductConfig pc, Administrator admin)
    {
        CMPPDatabase database = loadCMPPDatabase();
        if(database == null)
        {
            Log.log("FileConfig.loadUnionCenter : fail to load database !", 0x2000000000000001L);
            return null;
        }
        CMPPXMLExchanger exchanger = loadXMLExchanger();
        if(exchanger == null)
        {
            Log.log("FileConfig.loadUnionCenter : fail to load xml exchanger !", 0x2000000000000001L);
            return null;
        }
        try
        {
            UnionCenter center = new UnionCenter(pc, admin);
            center.m_database = database;
            center.m_exchanger = exchanger;
            return center;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("FileConfig.loadUnionCenter : unexpected exit !", 0x2000000000000001L);
        return null;
    }

    public static ExchangeCenter loadExchangeCenter(ProductConfig pc, Administrator admin)
    {
        try
        {
            ExchangeCenter center = new ExchangeCenter(pc, admin);
            return center;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("FileConfig.loadExchangeCenter : unexpected exit !", 0x2000000000000001L);
        return null;
    }

    public static MISCGateway loadMISCGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        MISCGateway misc = new MISCGateway(gc.m_strName, sp);
        misc.m_gc = gc;
        XMLTag transceiver = getConnect(gateway, "transceiver");
        if(transceiver != null)
            misc.m_scTransceiver = loadSessionConfig(transceiver);
        XMLTag receiver = getConnect(gateway, "receiver");
        if(receiver != null)
            misc.m_scReceiver = loadSessionConfig(receiver);
        return misc;
    }

    public static ASIAINFOGateway loadASIAINFOGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        ASIAINFOGateway asiainfo = new ASIAINFOGateway(gc.m_strName, sp);
        asiainfo.m_gc = gc;
        XMLTag transmitter = getConnect(gateway, "transmitter");
        if(transmitter != null)
            asiainfo.m_scTransmitter = loadSessionConfig(transmitter);
        XMLTag receiver = getConnect(gateway, "receiver");
        if(receiver != null)
            asiainfo.m_scReceiver = loadSessionConfig(receiver);
        return asiainfo;
    }

    public static BISPGateway loadBISPGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        BISPGateway bisp = new BISPGateway(gc.m_strName, sp);
        bisp.m_gc = gc;
        XMLTag transceiver = getConnect(gateway, "transceiver");
        if(transceiver != null)
            bisp.m_scTransceiver = loadSessionConfig(transceiver);
        return bisp;
    }

    public static INTRINSICGateway loadINTRINSICGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        INTRINSICGateway intrinsic = new INTRINSICGateway(gc.m_strName, sp);
        intrinsic.m_gc = gc;
        XMLTag transmitter = getConnect(gateway, "transmitter");
        if(transmitter != null)
            intrinsic.m_scTransmitter = loadSessionConfig(transmitter);
        XMLTag activater = getConnect(gateway, "activater");
        if(activater != null)
            intrinsic.m_scActivater = loadSessionConfig(activater);
        return intrinsic;
    }

    public static CMPPGateway loadCMPPGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        CMPPGateway standard = new CMPPGateway(gc.m_strName, sp);
        standard.m_gc = gc;
        XMLTag transceiver = getConnect(gateway, "transceiver");
        if(transceiver != null)
            standard.m_scTransceiver = loadSessionConfig(transceiver);
        return standard;
    }

    public static CMPPServerGateway loadCMPPServerGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        CMPPServerGateway server = new CMPPServerGateway(gc.m_strName, sp);
        server.m_gc = gc;
        XMLTag activater = getConnect(gateway, "activater");
        if(activater != null)
            server.m_scActivater = loadSessionConfig(activater);
        return server;
    }

    public static SMPPGateway loadSMPPGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        SMPPGateway smsc = new SMPPGateway(gc.m_strName, sp);
        smsc.m_gc = gc;
        XMLTag transmitter = getConnect(gateway, "transmitter");
        if(transmitter != null)
            smsc.m_scTransmitter = loadSessionConfig(transmitter);
        XMLTag receiver = getConnect(gateway, "receiver");
        if(receiver != null)
            smsc.m_scReceiver = loadSessionConfig(receiver);
        return smsc;
    }

    public static SMPPServerGateway loadSMPPServerGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        SMPPServerGateway server = new SMPPServerGateway(gc.m_strName, sp);
        server.m_gc = gc;
        XMLTag activater = getConnect(gateway, "activater");
        if(activater != null)
            server.m_scActivater = loadSessionConfig(activater);
        return server;
    }

    public static CNGPSCGateway loadCNGPSCGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        CNGPSCGateway cngp = new CNGPSCGateway(gc.m_strName, sp);
        cngp.m_gc = gc;
        XMLTag transceiver = getConnect(gateway, "transceiver");
        if(transceiver != null)
            cngp.m_scTransceiver = loadSessionConfig(transceiver);
        return cngp;
    }

    public static CNGPDCGateway loadCNGPDCGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        CNGPDCGateway cngp = new CNGPDCGateway(gc.m_strName, sp);
        cngp.m_gc = gc;
        XMLTag transmitter = getConnect(gateway, "transmitter");
        if(transmitter != null)
            cngp.m_scTransmitter = loadSessionConfig(transmitter);
        XMLTag receiver = getConnect(gateway, "receiver");
        if(receiver != null)
            cngp.m_scReceiver = loadSessionConfig(receiver);
        return cngp;
    }

    public static NOKIAGateway loadNOKIAGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        NOKIAGateway nokia = new NOKIAGateway(gc.m_strName, sp);
        nokia.m_gc = gc;
        XMLTag transmitter = getConnect(gateway, "transmitter");
        if(transmitter != null)
            nokia.m_scTransmitter = loadSessionConfig(transmitter);
        XMLTag receiver = getConnect(gateway, "receiver");
        if(receiver != null)
            nokia.m_scReceiver = loadSessionConfig(receiver);
        return nokia;
    }

    public static NOKIACMPPGateway loadNOKIACMPPGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        NOKIACMPPGateway nokia = new NOKIACMPPGateway(gc.m_strName, sp);
        nokia.m_gc = gc;
        XMLTag transceiver = getConnect(gateway, "transceiver");
        if(transceiver != null)
            nokia.m_scTransceiver = loadSessionConfig(transceiver);
        return nokia;
    }

    public static SITECHGateway loadSITECHGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        SITECHGateway sitech = new SITECHGateway(gc.m_strName, sp);
        sitech.m_gc = gc;
        XMLTag transceiver = getConnect(gateway, "transceiver");
        if(transceiver != null)
            sitech.m_scTransceiver = loadSessionConfig(transceiver);
        return sitech;
    }

    public static HUAWEIGateway loadHUAWEIGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        HUAWEIGateway huawei = new HUAWEIGateway(gc.m_strName, sp);
        huawei.m_gc = gc;
        XMLTag transceiver = getConnect(gateway, "transceiver");
        if(transceiver != null)
            huawei.m_scTransceiver = loadSessionConfig(transceiver);
        return huawei;
    }

    public static TUOWEIGateway loadTUOWEIGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        TUOWEIGateway tuowei = new TUOWEIGateway(gc.m_strName, sp);
        tuowei.m_gc = gc;
        XMLTag transceiver = getConnect(gateway, "transceiver");
        if(transceiver != null)
            tuowei.m_scTransceiver = loadSessionConfig(transceiver);
        return tuowei;
    }

    public static TSSXGateway loadTSSXGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        TSSXGateway tssx = new TSSXGateway(gc.m_strName, sp);
        tssx.m_gc = gc;
        XMLTag transmitter = getConnect(gateway, "transmitter");
        if(transmitter != null)
            tssx.m_scTransmitter = loadSessionConfig(transmitter);
        XMLTag receiver = getConnect(gateway, "receiver");
        if(receiver != null)
            tssx.m_scReceiver = loadSessionConfig(receiver);
        return tssx;
    }

    public static SGIPGateway loadSGIPGateway(XMLTag gateway, GatewayConfig gc, ServiceProvider sp)
    {
        SGIPGateway sgip = new SGIPGateway(gc.m_strName, sp);
        sgip.m_gc = gc;
        XMLTag transmitter = getConnect(gateway, "transmitter");
        if(transmitter != null)
            sgip.m_scTransmitter = loadSessionConfig(transmitter);
        XMLTag activater = getConnect(gateway, "activater");
        if(activater != null)
            sgip.m_scActivater = loadSessionConfig(activater);
        return sgip;
    }



}