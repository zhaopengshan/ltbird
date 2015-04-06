package LeadTone.CMPPDatabase;

import LeadTone.Database.DatabaseConfig;
import LeadTone.Database.DatabasePool;
import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import java.io.PrintStream;
import java.util.Vector;
    
/**
 * ���ݿ��ṹ����CMPPЭ��ʵ����ƣ�����Э������SMPP,�����������ر�ʵ�־�
 * ͨ��ת����Exchanger��˱�ṹͳһ��
 *
 * ��ת���������ݿⲿ����������Ϣ����CMPPPacketQueue�� ��Ϣ������� �� ��Ϣ������У�
 *
 * �ɸ��ݲ�ͬ�����Submit,Deliver,Query�������ͽ�����ͬ�������̣߳�
 * ÿ���̻߳���Input��Ouput�����ӽ��̣��ֱ�ҽ��������ἰ������������Ϣ�����ϣ���ͨ��
 * CMPPTable���µ���ʵ�����ݳ־û�
 *
 * �κ�һ���̶߳����ݿ�Ĳ����������ӳ�(DatabasePool)�л�ȡ��
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
     * ���췽����ʼ�������
     * @param dc ���ݿ�������Ϣ
     * @param nSubmitCount ����Submit���͵��߳�����
     * @param nDeliverCount ����Deliver���͵��߳�����
     * @param nQueryCount ����Query���͵��߳�����
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
        	//ѭ������m_nCount���߳�������db��Submit����i+1��ÿ���̵߳�Ψһidֵ����Submit��Deliver��Query��������ͬһ�����롢�������
            CMPPSubmitDatabase database = new CMPPSubmitDatabase(i + 1, m_input, m_output, m_pool);
            m_submits.addElement(database);//����submits�߳�������
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
        //�ڴ˴��������ú��Ʊ��������߳�
        //backTask=new BackupBizDataHandledTask(1,m_pool);

        /*�ж��ط��Ļ����ǣ���300��֮ǰ���ŵ�ih_process=wait_for_response״̬����Ϊcmpp_submit_insert.
        	���ڸ���ʡ����δ����response��ֱ�ӷ���Deliver�������ѷ��͵Ķ���һֱΪih_process=wait_for_response״̬��
        	��ʱ�ظ����ƻ�һֱ��Ч���ֻ��ظ�������ͬ����.
                        ������������������˴������ط����ܡ�  yuqian  20130112*/
        //restoreTask=new RestoreBizDataUnhandleTask(1,m_pool);
    }

    /**
     * ������ݿ�������������
     * @param ps ��ӡ�����
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
     * ���ùܵ��������
     * @param nCapacity �������
     */
    public void setCapacity(int nCapacity)
    {
        m_output.setCapacity(nCapacity);
        m_input.setCapacity(nCapacity);
    }

    /**
     * ���ʹ�õ���Դ���Ƴ������Ӷ����������ӳ�����
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
     * ������ӳ��Ƿ����
     * @return  �������ӳ��Ƿ���õĲ���ֵ
     */
    public boolean isValid()
    {
        if(m_pool == null)
            return false;
        else
            return m_pool.isValid();
    }

    /**
     * �ر����ݿ�
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
     * ���쳣����ʱ����������Ӧ�Ľ���
     * @param nID  ���̱��
     * @return ��������CMPPSubmitDatabase����
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
     * ���������������ݿ��������
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
     * ����������ݿ���������Ƿ�ֹͣ����ֹͣ������������Ӧ�Ķ���
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
     * �ر����е����ݿ��������
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
     * �߳������У���ѭ������ӳ��Ƿ�ֹͣ������������ݿ��������
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