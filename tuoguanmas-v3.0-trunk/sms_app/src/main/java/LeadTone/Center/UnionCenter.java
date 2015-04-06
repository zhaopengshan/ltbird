package LeadTone.Center;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Exchanger.*;
import LeadTone.Gateway.*;
import LeadTone.Log;
import LeadTone.Port.CMPPXMLExchanger;
import LeadTone.XML.XMLTag;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;


/**
 * 继承自CMPPCenter,系统工作模式UnionCenter
 * 此工作模式下支持所有协议的非服务器工作方式，
 * 包括中国移动、中国联通、中国网通短消息协议（CMPP，SGIP，CNGP）和短消息点对点协议（SMPP）
 */
public class UnionCenter extends CMPPCenter
{
    /**
     * 构造方法初始化类变量
     * @param pc
     * @param admin
     * @throws IOException
     */
    public UnionCenter(ProductConfig pc, Administrator admin)
        throws IOException
    {
        super(pc, admin);
    }

    /**
     * 扩展CMPPCenter类的装载消息转换线程的方法，此为用于中国网通短消息协议CNGP的方法
     * @param gateway
     * @param database
     * @param xml_porter
     */
    public static void loadCNGPExchanger(CNGPGatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        for(int i = 0; i < gateway.m_gc.m_nExchanger; i++)
        {
            CNGPExchanger exchanger = new CNGPExchanger(gateway, database, xml_porter);
            CMPPCenter.m_exchangers.addElement(exchanger);
        }

    }

    /**
     * 扩展CMPPCenter类的装载消息转换线程的方法，此为用于短消息点对点协议SMPP的方法
     * @param gateway
     * @param database
     * @param xml_porter
     */
    public static void loadSMPPExchanger(SMPPGateway gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        for(int i = 0; i < gateway.m_gc.m_nExchanger; i++)
        {
            SMPPExchanger exchanger = new SMPPExchanger(gateway, database, xml_porter);
            CMPPCenter.m_exchangers.addElement(exchanger);
        }

    }

    /**
     * 扩展CMPPCenter类的装载消息转换线程的方法，此为用于中国联通短消息协议SGIP的方法
     * @param gateway
     * @param database
     * @param xml_porter
     */
    public static void loadSGIPExchanger(SGIPGateway gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        for(int i = 0; i < gateway.m_gc.m_nExchanger; i++)
        {
            SGIPExchanger exchanger = new SGIPExchanger(gateway, database, xml_porter);
            CMPPCenter.m_exchangers.addElement(exchanger);
        }

    }

    /**
     * 扩展父类装载所有消息转换线程对象的方法
     * @param gateway
     * @param database
     * @param xml_porter
     * @return 返回是否装载成功
     */
    public static boolean loadExchanger(GatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        //如果属于父类能处理的CMPP协议范畴的消息转换线程，则交由父类的load方法处理
        if(CMPPCenter.loadExchanger(gateway, database, xml_porter))
            return true;
        if(gateway.m_nType == 0x30000)
        {
            loadSGIPExchanger((SGIPGateway)gateway, database, xml_porter);
            return true;
        }
        if(gateway.m_nType == 0x10000)
        {
            loadSMPPExchanger((SMPPGateway)gateway, database, xml_porter);
            return true;
        }
        if(gateway.m_nType == 0x40000 || gateway.m_nType == 0x40001 || gateway.m_nType == 0x40002)
        {
            loadCNGPExchanger((CNGPGatewayEngine)gateway, database, xml_porter);
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 装载所有网关对象
     * @param center
     * @return 返回是否装载成功的布尔值
     */
    public static boolean loadAllGateways(UnionCenter center)
    {
        Vector gateways = FileConfig.getAllGateways();
        for(int i = 0; i < gateways.size(); i++)
        {
            XMLTag gateway = (XMLTag)gateways.elementAt(i);
            GatewayEngine engine = Center.loadGateway(gateway);
            //判断必须是非服务器类的网关才可装载
            if(engine == null || GatewayType.isServer(engine.m_nType))
            {
                Log.log("UnionCenter.loadAllGateways : fail to load all gateways !", 1L);
                return false;
            }
            if(!loadExchanger(engine, center.m_database, center.m_exchanger))
            {
                Log.log("UnionCenter.loadAllGateways : fail to load all exchangers !", 1L);
                return false;
            }
            Center.m_gateways.addElement(engine);
        }

        Log.log("UnionCenter.loadAllGateways : success to load all gateways !", 1L);
        gateways.removeAllElements();
        return true;
    }


    /**
     * UnionCenter模式的系统进入点
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