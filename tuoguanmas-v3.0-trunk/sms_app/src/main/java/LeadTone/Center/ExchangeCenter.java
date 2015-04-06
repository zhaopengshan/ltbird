package LeadTone.Center;

import LeadTone.Engine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Gateway.GatewayType;
import LeadTone.Log;
import LeadTone.XML.XMLTag;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;


/**
 * �̳���Center,ϵͳ����ģʽExchangeCenter
 * �˹���ģʽ��֧������Э��İ���������������ʽ����û�����ݿ�־û����ּ�ϵͳ���ⲿ�Ļ���XML�Ľӿ�
 * �����й��ƶ����й���ͨ���й���ͨ����ϢЭ�飨CMPP��SGIP��CNGP���Ͷ���Ϣ��Ե�Э�飨SMPP��
 */
public class ExchangeCenter extends Center
{
    /**
     * ���з����������ض��󼯺�
     */
    static Vector m_servers = new Vector();

    /**
     * ���췽����ʼ�������
     * @param pc
     * @param admin
     * @throws IOException
     */
    public ExchangeCenter(ProductConfig pc, Administrator admin)
        throws IOException
    {
        super(pc, admin);
    }

    /**
     * ������з����������ع���״̬
     * @param ps
     */
    public void dump(PrintStream ps)
    {
        super.dump(ps);
        for(int i = 0; i < m_servers.size(); i++)
        {
            GatewayEngine server = (GatewayEngine)m_servers.elementAt(i);
            server.dump(ps);
        }

    }

    /**
     * �������з�����������
     */
    public void startupAllServers()
    {
        for(int i = 0; i < m_servers.size(); i++)
        {
            GatewayEngine server = (GatewayEngine)m_servers.elementAt(i);
            server.startup();
            if(i == m_servers.size() - 1)
                Engine.wait(server);
        }

    }

    /**
     * �ر����з�����������
     */
    public void shutdownAllServers()
    {
        for(int i = 0; i < m_servers.size(); i++)
        {
            GatewayEngine server = (GatewayEngine)m_servers.elementAt(i);
            server.shutdown();
            if(i == m_servers.size() - 1)
                Engine.wait(server);
        }

    }

    /**
     * װ���������ض���ͷ����������ض���
     * @param center
     * @return �����Ƿ�װ�����ء������������سɹ��Ĳ���ֵ
     */
    public static boolean loadAllGateways(ExchangeCenter center)
    {
        Vector gateways = FileConfig.getAllGateways();
        for(int i = 0; i < gateways.size(); i++)
        {
            XMLTag gateway = (XMLTag)gateways.elementAt(i);
            GatewayEngine engine = Center.loadGateway(gateway);
            if(engine == null)
            {
                Log.log("ExchangeCenter.loadAllGateways : fail to load all gateways !", 1L);
                return false;
            }
            //�ڴ˲����������ͽ������ƣ��������ؾ�����װ�أ�
            //��������Ƿ�������������ŵ������������У������ŵ����ؼ�����
            if(GatewayType.isServer(engine.m_nType))
                m_servers.addElement(engine);
            else
                Center.m_gateways.addElement(engine);
        }

        Log.log("ExchangeCenter.loadAllGateways : success to load all gateways !", 1L);
        gateways.removeAllElements();
        return true;
    }

    /**
     * �����������غͷ����������أ�
     * ��CMPPCenterģʽ��UnionCenterģʽ��ͬ����δ�������ݿ�����̺߳���Ϣת���̣߳�
     * Ҳû����������XML�������ⲿͨ�Žӿڵ��߳�
     */
    public void run()
    {
        try
        {
            //�������������߳�
            Log.log("ExchangeCenter.run : all gateways begin to startup !", 1L);
            startupAllGateways();
            Log.log("ExchangeCenter.run : thread startup !", 1L);
            //�������з������������߳�
            Log.log("ExchangeCenter.run : all servers begin to startup !", 1L);
            startupAllServers();
            Log.log("ExchangeCenter.run : thread startup !", 1L);
            m_nStatus = 1;
            for(; isRunning(); Engine.sleep())
            {
                //��������������������
                statistic();
                //����������ع������
                if(!checkGateway())
                {
                    Log.log("ExchangeCenter.run : gateway stopped unexpectedly !", 0x2000000000000001L);
                    break;
                }
                //��ʼ������˿ڣ����������߳�
                accept();
                //�������״̬��ʶ�رգ�������ѭ��
                if(!Center.m_bNeedTerminate)
                    continue;
                Log.log("ExchangeCenter.run : administrator has already stopped the system !", 1L);
                break;
            }

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("ExchangeCenter.run : unexpected exit !", 0x2000000000000001L);
        }
        Log.log("ExchangeCenter.run : servers begin to shutdown !", 1L);
        shutdownAllServers();
        Log.log("ExchangeCenter.run : gateways begin to shutdown !", 1L);
        shutdownAllGateways();
        m_nStatus = 3;
        Log.log("ExchangeCenter.run : thread stopped !", 1L);
    }

    /**
     * ExchangeCenterģʽ��ϵͳ�����
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