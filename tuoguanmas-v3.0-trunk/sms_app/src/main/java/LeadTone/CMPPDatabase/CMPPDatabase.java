package LeadTone.CMPPDatabase;

import LeadTone.Database.DatabaseConfig;
import LeadTone.Database.DatabasePool;
import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import java.io.PrintStream;
import java.util.Vector;
    
/**
 * 数据库表结构依照CMPP协议实现设计，其他协议例如SMPP,或其他厂商特别实现均
 * 通过转换器Exchanger向此表结构统一；
 *
 * 在转换器与数据库部分有两条消息队列CMPPPacketQueue， 消息输入队列 和 消息输出队列；
 *
 * 可根据不同需求对Submit,Deliver,Query三种类型建立不同条数的线程，
 * 每个线程还有Input和Ouput两条子进程，分别挂接于上面提及的输入和输出消息队列上，并通过
 * CMPPTable包下的类实现数据持久化
 *
 * 任何一条线程对数据库的操作均从连接池(DatabasePool)中获取；
 */
public class CMPPDatabase extends Engine
{
    public DatabasePool m_pool;
    public CMPPPacketQueue m_output;
    public CMPPPacketQueue m_input;
    //private BackupBizDataHandledTask backTask=null;
    private RestoreBizDataUnhandleTask restoreTask=null;
    Vector m_submits;
    Vector m_delivers;
    Vector m_querys;
    public int m_nErrorCount;

    /**
     * 构造方法初始化类变量
     * @param dc 数据库配置信息
     * @param nSubmitCount 启动Submit类型的线程条数
     * @param nDeliverCount 启动Deliver类型的线程条数
     * @param nQueryCount 启动Query类型的线程条数
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public CMPPDatabase(DatabaseConfig dc, int nSubmitCount, int nDeliverCount, int nQueryCount)
        throws ClassNotFoundException
    {
        super("CMPPDatabase");
        m_pool = null;
        m_output = new CMPPPacketQueue(0);
        m_input = new CMPPPacketQueue(0);
        m_submits = new Vector();
        m_delivers = new Vector();
        m_querys = new Vector();
        m_nErrorCount = 0;
        m_pool = new DatabasePool(dc);
        CMPPSubmitDatabase.m_nCount = nSubmitCount;
        for(int i = 0; i < CMPPSubmitDatabase.m_nCount; i++)
        {
        	//循环建立m_nCount个线程来操作db的Submit表。（i+1是每个线程的唯一id值），Submit、Deliver、Query三个表公用同一个输入、输出队列
            CMPPSubmitDatabase database = new CMPPSubmitDatabase(i + 1, m_input, m_output, m_pool);
            m_submits.addElement(database);//放在submits线程容器中
        }

        CMPPDeliverDatabase.m_nCount = nDeliverCount;
        for(int i = 0; i < CMPPDeliverDatabase.m_nCount; i++)
        {
            CMPPDeliverDatabase database = new CMPPDeliverDatabase(i + 1, m_input, m_output, m_pool);
            m_delivers.addElement(database);
        }

        /*CMPPQueryDatabase.m_nCount = nQueryCount;
        for(int i = 0; i < CMPPQueryDatabase.m_nCount; i++)
        {
            CMPPQueryDatabase database = new CMPPQueryDatabase(i + 1, m_input, m_output, m_pool);
            m_querys.addElement(database);
        }*/
        //在此处启动重置和移表备份两个线程
        //backTask=new BackupBizDataHandledTask(1,m_pool);

        /*判断重发的机制是：将300秒之前短信的ih_process=wait_for_response状态，改为cmpp_submit_insert.
        	由于个别省网关未返回response，直接返回Deliver，导致已发送的短信一直为ih_process=wait_for_response状态。
        	此时重复机制会一直起效，手机重复接收相同短信.
                        避免以上情况发生，此处屏蔽重发功能。  yuqian  20130112*/
        //restoreTask=new RestoreBizDataUnhandleTask(1,m_pool);
    }

    /**
     * 输出数据库操作的运行情况
     * @param ps 打印输出流
     */
    public void dump(PrintStream ps)
    {
        ps.print("\tdatabase(" + (isRunning() ? "+" : "-") + ") : " + "error(" + m_nErrorCount + ")," + "iqs(" + m_input.getSize() + ")," + "oqs(" + m_output.getSize() + ")\r\n");
        for(int i = 0; i < m_submits.size(); i++)
        {
            CMPPSubmitDatabase submit = (CMPPSubmitDatabase)m_submits.elementAt(i);
            submit.dump(ps);
        }

        for(int i = 0; i < m_delivers.size(); i++)
        {
            CMPPDeliverDatabase deliver = (CMPPDeliverDatabase)m_delivers.elementAt(i);
            deliver.dump(ps);
        }

        for(int i = 0; i < m_querys.size(); i++)
        {
            CMPPQueryDatabase query = (CMPPQueryDatabase)m_querys.elementAt(i);
            query.dump(ps);
        }

        //backTask.dump(ps);
        if(restoreTask!=null)
        restoreTask.dump(ps);
        m_pool.dump(ps);
    }

    /**
     * 设置管道最大容量
     * @param nCapacity 最大容量
     */
    public void setCapacity(int nCapacity)
    {
        m_output.setCapacity(nCapacity);
        m_input.setCapacity(nCapacity);
    }

    /**
     * 清空使用的资源，移除所有子对象，清空所有映射对象
     */
    public void empty()
    {
        m_submits.removeAllElements();
        m_delivers.removeAllElements();
        m_querys.removeAllElements();
        m_input.empty();
        m_input = null;
        m_output.empty();
        m_output = null;
        m_pool = null;
        //backTask.empty();
        //backTask=null;
        if(restoreTask!=null)
        restoreTask.empty();
        restoreTask=null;
    }

    /**
     * 检查连接池是否可用
     * @return  返回连接池是否可用的布尔值
     */
    public boolean isValid()
    {
        if(m_pool == null)
            return false;
        else
            return m_pool.isValid();
    }

    /**
     * 关闭数据库
     */
    public void close()
    {
        try
        {
            if(m_pool != null)
                m_pool.closeDatabases();
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000008000000000L);
            Log.log(e);
            Log.log("CMPPDatabase.main : unexpected exit", 0x2000008000000000L);
        }
    }

    /**
     * 当异常出现时重新启动相应的进程
     * @param nID  进程编号
     * @return 新启动的CMPPSubmitDatabase对象
     */
    public CMPPSubmitDatabase restartSubmitDatabase(int nID)
    {
        CMPPSubmitDatabase submit = new CMPPSubmitDatabase(nID, m_input, m_output, m_pool);
        submit.startup();
        Engine.wait(submit);
        return submit;
    }

    public CMPPDeliverDatabase restartDeliverDatabase(int nID)
    {
        CMPPDeliverDatabase deliver = new CMPPDeliverDatabase(nID, m_input, m_output, m_pool);
        deliver.startup();
        Engine.wait(deliver);
        return deliver;
    }

    public CMPPQueryDatabase restartQueryDatabase(int nID)
    {
        CMPPQueryDatabase query = new CMPPQueryDatabase(nID, m_input, m_output, m_pool);
        query.startup();
        Engine.wait(query);
        return query;
    }

    /**
     * 启动所有类型数据库操作对象
     */
    public void startupAllDatabase()
    {
        for(int i = 0; i < m_submits.size(); i++)
        {
            CMPPSubmitDatabase database = (CMPPSubmitDatabase)m_submits.elementAt(i);
            database.startup();
            if(i == m_submits.size() - 1)
                Engine.wait(database);
        }

        for(int i = 0; i < m_delivers.size(); i++)
        {
            CMPPDeliverDatabase database = (CMPPDeliverDatabase)m_delivers.elementAt(i);
            database.startup();
            if(i == m_delivers.size() - 1)
                Engine.wait(database);
        }

        for(int i = 0; i < m_querys.size(); i++)
        {
            CMPPQueryDatabase database = (CMPPQueryDatabase)m_querys.elementAt(i);
            database.startup();
            if(i == m_querys.size() - 1)
                Engine.wait(database);
        }

    }

    /**
     * 检查所有数据库操作对象是否停止，如停止则重新启动相应的对象
     */
    public void checkAllDatabase()
    {
        for(int i = 0; i < m_submits.size() && isRunning(); i++)
        {
            CMPPSubmitDatabase database = (CMPPSubmitDatabase)m_submits.elementAt(i);
            if(database.isStopped())
            {
                m_nErrorCount++;
                m_submits.removeElementAt(i);
                database.empty();
                database = restartSubmitDatabase(database.m_nID);
                m_submits.addElement(database);
            }
        }

        for(int i = 0; i < m_delivers.size() && isRunning(); i++)
        {
            CMPPDeliverDatabase database = (CMPPDeliverDatabase)m_delivers.elementAt(i);
            if(database.isStopped())
            {
                m_nErrorCount++;
                m_delivers.removeElementAt(i);
                database.empty();
                database = restartDeliverDatabase(database.m_nID);
                m_delivers.addElement(database);
            }
        }

        for(int i = 0; i < m_querys.size() && isRunning(); i++)
        {
            CMPPQueryDatabase database = (CMPPQueryDatabase)m_querys.elementAt(i);
            if(database.isStopped())
            {
                m_nErrorCount++;
                m_querys.removeElementAt(i);
                database.empty();
                database = restartQueryDatabase(database.m_nID);
                m_querys.addElement(database);
            }
        }

    }

    /**
     * 关闭所有的数据库操作对象
     */
    public void shutdownAllDatabase()
    {
        for(int i = 0; i < m_submits.size(); i++)
        {
            CMPPSubmitDatabase database = (CMPPSubmitDatabase)m_submits.elementAt(i);
            database.shutdown();
            if(i == m_submits.size() - 1)
                Engine.wait(database);
        }

        for(int i = 0; i < m_delivers.size(); i++)
        {
            CMPPDeliverDatabase database = (CMPPDeliverDatabase)m_delivers.elementAt(i);
            database.shutdown();
            if(i == m_delivers.size() - 1)
                Engine.wait(database);
        }

        for(int i = 0; i < m_querys.size(); i++)
        {
            CMPPQueryDatabase database = (CMPPQueryDatabase)m_querys.elementAt(i);
            database.shutdown();
            if(i == m_querys.size() - 1)
                Engine.wait(database);
        }

    }

    /**
     * 线程运行中，伦循检查连接池是否停止，检查所有数据库操作对象
     */
    public void run()
    {
        try
        {
        	Log.log("CMPPDatabase.run : database pool begin startup !", 0x8000000000L);
            m_pool.startup();
            Engine.wait(m_pool);
            //backTask.startup();
        	//Engine.wait(backTask);
        	Log.log("CMPPDatabase.run : database backTask !", 0x8000000000L);

        	
        	if(restoreTask!=null){
        		restoreTask.startup();
              	Engine.wait(restoreTask);
              	Log.log("CMPPDatabase.run : database restoreTask !", 0x8000000000L);
        	}
        	

            startupAllDatabase();
            Log.log("CMPPDatabase.run : database startup !", 0x8000000000L);
            m_nStatus = 1;
            for(; isRunning(); Engine.sleep())
            {
                if(m_pool.isStopped())
                {
                    Log.log("CMPPDatabase.run : database pool unexpected stopped !", 0x2000008000000000L);
                    break;
                }
                checkAllDatabase();
            }

        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000008000000000L);
            Log.log(e);
            Log.log("CMPPDatabase.run : unexpected exit !", 0x2000008000000000L);
        }
        shutdownAllDatabase();
        m_pool.shutdown();
        close();
        empty();
        m_nStatus = 3;
        Log.log("CMPPDatabase.run : thread stopped !", 0x8000000000L);
    }


}