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
 * 继承自Center,系统工作模式CMPPCenter
 * 此工作模式下只支持标准CMPP协议和不同厂商例如亚信、卓望对CMPP协议的不同实现
 */
public class CMPPCenter extends Center
{
    /**
     * 数据库操作对象
     */
    CMPPDatabase m_database;
    /**
     * 基于XML的外部通信扩展接口对象
     */
    CMPPXMLExchanger m_exchanger;
    /**
     * 所有消息转换线程对象
     */
    static Vector m_exchangers = new Vector();

    /**
     * 构造方法初始化类变量
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
     * 继承自父类方法，除了输出系统工作状态信息外，
     * 还输出数据库操作对象和基于XML的外部通信扩展接口对象
     * @param ps
     */
    public void dump(PrintStream ps)
    {
        super.dump(ps);
        m_exchanger.dump(ps);
        m_database.dump(ps);
    }

    /**
     * 清空系统使用的所有资源
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
     * 检查数据库操作对象的工作状态，
     * 当数据库错误总数达到最大容错值的时候，发送报警邮件，
     * 当数据库停止工作的时候，发送报警邮件
     * @return 返回数据库是否停止工作的布尔值
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
     * 通过系统当前工作时的流量峰值设置数据库操作的输入输出消息队列的容量，
     * 用于系统流量控制自动化
     */
    public void setDatabase()
    {
        m_database.setCapacity(m_nPeakFlux + 1);
    }

    /**
     * 检查所有消息转换线程，如果线程停止工作发送报警邮件
     * @return 返回所有消息转换线程是否工作正常的布尔值
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
     * 启动所有消息转换线程
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
     * 关闭所有消息转换线程
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
     * 装载用于转换CMPP标准实现的消息转换线程
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
     * 装载用于转换亚信对CMPP实现的消息转换线程
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
     * 装载用于转换卓望对CMPP实现的消息转换线程
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
     * 装载用于转换NOKIA对CMPP实现的消息转换线程
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
     * 装载所有消息转换线程对象
     * @param gateway
     * @param database
     * @param xml_porter
     * @return 返回装载是否成功的布尔值
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
     * 装载所有网关对象
     * @param center
     * @return 返回是否装载成功的布尔值
     */
    public static boolean loadAllGateways(CMPPCenter center)
    {
        //装载之前先清空，避免异常重启重新装载时还存在历史网关成员
    	Center.m_gateways.removeAllElements();
    	      
        //从配置文件中过去所有网关节点
        Vector gateways = FileConfig.getAllGateways();
        //遍历所有网关节点，
        for(int i = 0; i < gateways.size(); i++)
        {
            XMLTag gateway = (XMLTag)gateways.elementAt(i);
            GatewayEngine engine = loadGateway(gateway);
            //判断必须是CMPP类型的网关才允许装载
            if(engine == null || !GatewayType.isISMG(engine.m_nType))
            {
                Log.log("CMPPCenter.loadAllGateways : fail to load all gateways !", 1L);
                return false;
            }
            //装载消息转换队列，将在子方法中讲装载成功的消息转换对象放入Center的消息转换集合中
            if(!loadExchanger(engine, center.m_database, center.m_exchanger))
            {
                Log.log("CMPPCenter.loadAllGateways : fail to load all exchangers !", 1L);
                return false;
            }
            //将装载的网关放入Center的网关集合中
            m_gateways.addElement(engine);

        }

        Log.log("CMPPCenter.loadAllGateways : success to load all gateways !", 1L);
        gateways.removeAllElements();
        return true;
    }

    /**
     * CMPPCenter模式的工作线程，
     * 启动数据库操作线程，启动基于XML的外部通信接口线程，启动所有网关，启动所有消息转换线程，
     *
     */
    public void run()
    {
        try
        {
            //启动数据库操作线程
            Log.log("CMPPCenter.run : database begins to startup !", 1L);
            m_database.startup();
            Engine.wait(m_database);
            //启动基于XML的外部通信接口线程
            Log.log("CMPPCenter.run : xml exchanger begins to startup !", 1L);
            m_exchanger.startup();
            Engine.wait(m_exchanger);
            //启动所有网关
            Log.log("CMPPCenter.run : all gateways begin to startup !", 1L);
            startupAllGateways();
            //启动所有消息转换线程
            Log.log("CMPPCenter.run : all data exchangers begin to startup !", 1L);
            startupAllExchangers();
            Log.log("CMPPCenter.run : thread startup !", 1L);
            m_nStatus = 1;
            for(; isRunning(); sleep())
            {
                //汇总各网关流量数据
                statistic();
                //设置数据库操作的输入输出消息队列的容量
                setDatabase();
                //检查数据库操作线程工作情况
                if(!checkDatabase())
                {
                    Log.log("CMPPCenter.run : database stopped unexpectedly !", 0x2000000000000001L);
                    break;
                }
                //检查各网关工作情况
                if(!checkGateway())
                {
                    Log.log("CMPPCenter.run : gateway stopped unexpectedly !", 0x2000000000000001L);
                    break;
                }
                //检查各消息转换线程的工作情况
                if(!checkExchanger())
                {
                    Log.log("CMPPCenter.run : exchanger stopped unexpectedly !", 0x2000000000000001L);
                    break;
                }
                //开启系统管理端口，启动系统管理线程
                accept();
                //如果系统标识关闭则跳出循环
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
     * CMPPCenter模式的系统进入点
     * @param args
     */
    public static void main(String args[])
    {
    	Center.main(args);
    }



}