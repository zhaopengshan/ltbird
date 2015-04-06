package LeadTone.Center;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Exchanger.ASIAINFOExchanger;
import LeadTone.Exchanger.CMPPExchanger;
import LeadTone.Exchanger.DataExchanger;
import LeadTone.Exchanger.MISCExchanger;
import LeadTone.Exchanger.NOKIAExchanger;
import LeadTone.Gateway.ASIAINFOGateway;
import LeadTone.Gateway.CMPPGatewayEngine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Gateway.GatewayType;
import LeadTone.Gateway.MISCGateway;
import LeadTone.Gateway.NOKIAGatewayEngine;
import LeadTone.Port.CMPPXMLExchanger;
import LeadTone.XML.XMLTag;

/**
 * �̳���Center,ϵͳ����ģʽCMPPCenter
 * �˹���ģʽ��ֻ֧�ֱ�׼CMPPЭ��Ͳ�ͬ�����������š�׿����CMPPЭ��Ĳ�ͬʵ��
 */
public class CMPPCenter extends Center
{
    /**
     * ���ݿ��������
     */
    CMPPDatabase m_database;
    /**
     * ����XML���ⲿͨ����չ�ӿڶ���
     */
    CMPPXMLExchanger m_exchanger;
    /**
     * ������Ϣת���̶߳���
     */
    static Vector m_exchangers = new Vector();

    /**
     * ���췽����ʼ�������
     * @param pc
     * @param admin
     * @throws IOException
     */
    public CMPPCenter(ProductConfig pc, Administrator admin)
        throws IOException
    {
        super(pc, admin);
        m_database = null;
        m_exchanger = null;
    }

    /**
     * �̳��Ը��෽�����������ϵͳ����״̬��Ϣ�⣬
     * ��������ݿ��������ͻ���XML���ⲿͨ����չ�ӿڶ���
     * @param ps
     */
    public void dump(PrintStream ps)
    {
        super.dump(ps);
        m_exchanger.dump(ps);
        m_database.dump(ps);
    }

    /**
     * ���ϵͳʹ�õ�������Դ
     */
    public void empty()
    {
        m_database.empty();
        m_database = null;
        m_exchanger.empty();
        m_exchanger = null;
        m_exchangers.removeAllElements();
        m_exchangers = null;
        super.empty();
    }

    /**
     * ������ݿ��������Ĺ���״̬��
     * �����ݿ���������ﵽ����ݴ�ֵ��ʱ�򣬷��ͱ����ʼ���
     * �����ݿ�ֹͣ������ʱ�򣬷��ͱ����ʼ�
     * @return �������ݿ��Ƿ�ֹͣ�����Ĳ���ֵ
     */
    public boolean checkDatabase()
    {
        if(m_database.m_nErrorCount > m_database.m_pool.m_dc.m_nMaxErrorCount)
        {
            m_admin.sendMail("Alert(" + m_pc.m_strAbbreviation + ") : Database encounts too many errors !", "Letter is from " + m_pc.m_strAuthorization + "\r\n" + "Database encounts too many errors and try to recover ! \r\n" + "The last exception is : " + Log.getLastException() + "\r\n" + "Please check the error as soon as possible !\r\n");
            m_database.m_nErrorCount = 0;
        }
        if(m_database.isStopped())
        {
            m_admin.sendMail("Emergency(" + m_pc.m_strAbbreviation + ") : Database has stopped working !", "Letter is from " + m_pc.m_strAuthorization + "\r\n" + "Database has stopped working ! \r\n" + "The last exception is : " + Log.getLastException() + "\r\n" + "Please check the error as soon as possible !\r\n");
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * ͨ��ϵͳ��ǰ����ʱ��������ֵ�������ݿ���������������Ϣ���е�������
     * ����ϵͳ���������Զ���
     */
    public void setDatabase()
    {
        m_database.setCapacity(m_nPeakFlux + 1);
    }

    /**
     * ���������Ϣת���̣߳�����߳�ֹͣ�������ͱ����ʼ�
     * @return ����������Ϣת���߳��Ƿ��������Ĳ���ֵ
     */
    public boolean checkExchanger()
    {
        for(int i = 0; i < m_exchangers.size(); i++)
        {
            DataExchanger exchanger = (DataExchanger)m_exchangers.elementAt(i);
            if(exchanger.isStopped())
            {
                m_admin.sendMail("Emergency(" + m_pc.m_strAbbreviation + ") : Exchanger(" + exchanger.m_nID + ") has stopped working !", "Letter is from " + m_pc.m_strAuthorization + "\r\n" + "Exchanger(" + exchanger.m_nID + ") has stopped working ! \r\n" + "The last exception is : " + Log.getLastException() + "\r\n" + "Please check the error as soon as possible !\r\n");
                return false;
            }
        }

        return true;
    }

    /**
     * ����������Ϣת���߳�
     */
    public void startupAllExchangers()
    {
        for(int i = 0; i < m_exchangers.size(); i++)
        {
            DataExchanger exchanger = (DataExchanger)m_exchangers.elementAt(i);
            exchanger.startup();
            if(i == m_exchangers.size() - 1)
                Engine.wait(exchanger);
        }

    }

    /**
     * �ر�������Ϣת���߳�
     */
    public void shutdownAllExchangers()
    {
        for(int i = 0; i < m_exchangers.size(); i++)
        {
            DataExchanger exchanger = (DataExchanger)m_exchangers.elementAt(i);
            exchanger.shutdown();
            if(i == m_exchangers.size() - 1)
                Engine.wait(exchanger);
        }

    }

    /**
     * װ������ת��CMPP��׼ʵ�ֵ���Ϣת���߳�
     * @param gateway
     * @param database
     * @param xml_porter
     */
    public static void loadCMPPExchanger(CMPPGatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        for(int i = 0; i < gateway.m_gc.m_nExchanger; i++)
        {
            CMPPExchanger exchanger = new CMPPExchanger(gateway, database, xml_porter);
            m_exchangers.addElement(exchanger);
        }

    }

    /**
     * װ������ת�����Ŷ�CMPPʵ�ֵ���Ϣת���߳�
     * @param gateway
     * @param database
     * @param xml_porter
     */
    public static void loadASIAINFOExchanger(ASIAINFOGateway gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        for(int i = 0; i < gateway.m_gc.m_nExchanger; i++)
        {
            ASIAINFOExchanger exchanger = new ASIAINFOExchanger(gateway, database, xml_porter);
            m_exchangers.addElement(exchanger);
        }

    }

    /**
     * װ������ת��׿����CMPPʵ�ֵ���Ϣת���߳�
     * @param gateway
     * @param database
     * @param xml_porter
     */
    public static void loadMISCExchanger(MISCGateway gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        for(int i = 0; i < gateway.m_gc.m_nExchanger; i++)
        {
            MISCExchanger exchanger = new MISCExchanger(gateway, database, xml_porter);
            m_exchangers.addElement(exchanger);
        }

    }

    /**
     * װ������ת��NOKIA��CMPPʵ�ֵ���Ϣת���߳�
     * @param gateway
     * @param database
     * @param xml_porter
     */
    public static void loadNOKIAExchanger(NOKIAGatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        for(int i = 0; i < gateway.m_gc.m_nExchanger; i++)
        {
            NOKIAExchanger exchanger = new NOKIAExchanger(gateway, database, xml_porter);
            m_exchangers.addElement(exchanger);
        }

    }

    /**
     * װ��������Ϣת���̶߳���
     * @param gateway
     * @param database
     * @param xml_porter
     * @return ����װ���Ƿ�ɹ��Ĳ���ֵ
     */
    public static boolean loadExchanger(GatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger xml_porter)
    {
        if(gateway.m_nType == 0x20000 || gateway.m_nType == 0x20400 || gateway.m_nType == 0x20300 || gateway.m_nType == 0x20500 || gateway.m_nType == 0x20700 || gateway.m_nType == 0x20100 || gateway.m_nType == 0x20900)
        {
            loadCMPPExchanger((CMPPGatewayEngine)gateway, database, xml_porter);
            return true;
        }
        if(gateway.m_nType == 0x20200)
        {
            loadASIAINFOExchanger((ASIAINFOGateway)gateway, database, xml_porter);
            return true;
        }
        if(gateway.m_nType == 0x20800)
        {
            loadMISCExchanger((MISCGateway)gateway, database, xml_porter);
            return true;
        }
        if(gateway.m_nType == 0x20601 || gateway.m_nType == 0x20602)
        {
            loadNOKIAExchanger((NOKIAGatewayEngine)gateway, database, xml_porter);
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
    public static boolean loadAllGateways(CMPPCenter center)
    {
        //װ��֮ǰ����գ������쳣��������װ��ʱ��������ʷ���س�Ա
    	Center.m_gateways.removeAllElements();
    	      
        //�������ļ��й�ȥ�������ؽڵ�
        Vector gateways = FileConfig.getAllGateways();
        //�����������ؽڵ㣬
        for(int i = 0; i < gateways.size(); i++)
        {
            XMLTag gateway = (XMLTag)gateways.elementAt(i);
            GatewayEngine engine = loadGateway(gateway);
            //�жϱ�����CMPP���͵����ز�����װ��
            if(engine == null || !GatewayType.isISMG(engine.m_nType))
            {
                Log.log("CMPPCenter.loadAllGateways : fail to load all gateways !", 1L);
                return false;
            }
            //װ����Ϣת�����У������ӷ����н�װ�سɹ�����Ϣת���������Center����Ϣת��������
            if(!loadExchanger(engine, center.m_database, center.m_exchanger))
            {
                Log.log("CMPPCenter.loadAllGateways : fail to load all exchangers !", 1L);
                return false;
            }
            //��װ�ص����ط���Center�����ؼ�����
            m_gateways.addElement(engine);

        }

        Log.log("CMPPCenter.loadAllGateways : success to load all gateways !", 1L);
        gateways.removeAllElements();
        return true;
    }

    /**
     * CMPPCenterģʽ�Ĺ����̣߳�
     * �������ݿ�����̣߳���������XML���ⲿͨ�Žӿ��̣߳������������أ�����������Ϣת���̣߳�
     *
     */
    public void run()
    {
        try
        {
            //�������ݿ�����߳�
            Log.log("CMPPCenter.run : database begins to startup !", 1L);
            m_database.startup();
            Engine.wait(m_database);
            //��������XML���ⲿͨ�Žӿ��߳�
            Log.log("CMPPCenter.run : xml exchanger begins to startup !", 1L);
            m_exchanger.startup();
            Engine.wait(m_exchanger);
            //������������
            Log.log("CMPPCenter.run : all gateways begin to startup !", 1L);
            startupAllGateways();
            //����������Ϣת���߳�
            Log.log("CMPPCenter.run : all data exchangers begin to startup !", 1L);
            startupAllExchangers();
            Log.log("CMPPCenter.run : thread startup !", 1L);
            m_nStatus = 1;
            for(; isRunning(); sleep())
            {
                //���ܸ�������������
                statistic();
                //�������ݿ���������������Ϣ���е�����
                setDatabase();
                //������ݿ�����̹߳������
                if(!checkDatabase())
                {
                    Log.log("CMPPCenter.run : database stopped unexpectedly !", 0x2000000000000001L);
                    break;
                }
                //�������ع������
                if(!checkGateway())
                {
                    Log.log("CMPPCenter.run : gateway stopped unexpectedly !", 0x2000000000000001L);
                    break;
                }
                //������Ϣת���̵߳Ĺ������
                if(!checkExchanger())
                {
                    Log.log("CMPPCenter.run : exchanger stopped unexpectedly !", 0x2000000000000001L);
                    break;
                }
                //����ϵͳ����˿ڣ�����ϵͳ�����߳�
                accept();
                //���ϵͳ��ʶ�ر�������ѭ��
                if(!m_bNeedTerminate)
                    continue;
                Log.log("CMPPCenter.run : administrator has already stopped the system !", 1L);
                break;
            }

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPCenter.run : unexpected exit !", 0x2000000000000001L);
        }
        close();
        Log.log("CMPPCenter.run : exchangers begin to shutdown !", 1L);
        shutdownAllExchangers();
        Log.log("CMPPCenter.run : gateways begin to shutdown !", 1L);
        shutdownAllGateways();
        Log.log("CMPPCenter.run : xml exchanger begin to shutdown !", 1L);
        m_exchanger.shutdown();
        Engine.wait(m_exchanger);
        Log.log("CMPPCenter.run : database begin to shutdown !", 1L);
        m_database.shutdown();
        Engine.wait(m_database);
        m_nStatus = 3;
        Log.log("CMPPCenter.run : thread stopped !", 1L);
    }

    /**
     * CMPPCenterģʽ��ϵͳ�����
     * @param args
     */
    public static void main(String args[])
    {
    	Center.main(args);
    }



}