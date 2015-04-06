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
 * �̳���CMPPCenter,ϵͳ����ģʽUnionCenter
 * �˹���ģʽ��֧������Э��ķǷ�����������ʽ��
 * �����й��ƶ����й���ͨ���й���ͨ����ϢЭ�飨CMPP��SGIP��CNGP���Ͷ���Ϣ��Ե�Э�飨SMPP��
 */
public class UnionCenter extends CMPPCenter
{
    /**
     * ���췽����ʼ�������
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
     * ��չCMPPCenter���װ����Ϣת���̵߳ķ�������Ϊ�����й���ͨ����ϢЭ��CNGP�ķ���
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
     * ��չCMPPCenter���װ����Ϣת���̵߳ķ�������Ϊ���ڶ���Ϣ��Ե�Э��SMPP�ķ���
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
     * ��չCMPPCenter���װ����Ϣת���̵߳ķ�������Ϊ�����й���ͨ����ϢЭ��SGIP�ķ���
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
     * ��չ����װ��������Ϣת���̶߳���ķ���
     * @param gateway
     * @param database
     * @param xml_porter
     * @return �����Ƿ�װ�سɹ�
     */
    public static boolean loadExchanger(GatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        //������ڸ����ܴ����CMPPЭ�鷶�����Ϣת���̣߳����ɸ����load��������
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
     * װ���������ض���
     * @param center
     * @return �����Ƿ�װ�سɹ��Ĳ���ֵ
     */
    public static boolean loadAllGateways(UnionCenter center)
    {
        Vector gateways = FileConfig.getAllGateways();
        for(int i = 0; i < gateways.size(); i++)
        {
            XMLTag gateway = (XMLTag)gateways.elementAt(i);
            GatewayEngine engine = Center.loadGateway(gateway);
            //�жϱ����ǷǷ�����������زſ�װ��
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
     * UnionCenterģʽ��ϵͳ�����
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