package LeadTone;


/**
 * �̻߳��࣬�����߳��� ���̳��Դ��࣬ӵ�� ���١����У����٣�ֹͣ �����߳�״̬
 */
public class Engine extends Thread
{
    public static final int THREAD_ACCELERATE = 0;
    public static final int THREAD_RUNNING = 1;
    public static final int THREAD_DECELERATE = 2;
    public static final int THREAD_STOPPED = 3;
   
    //0:���٣�1������,2�����٣�3��ֹͣ
    public int m_nStatus;
    
    public String strName;
    
    /**
     *  ���췽������д����Thread��������ʼ���̳߳�ʼ״̬Ϊֹͣ״̬
     *  @param strName �߳�����
     */
    public Engine(String strName)
    {
        super(strName);
        m_nStatus = 3;

        this.strName = "";
        this.strName = new String(strName);

    }

    
    /**
     * ��ǰ�߳��Ƿ�����������״̬
     * @return �����Ƿ�����������״̬�Ĳ���ֵ
     */
    public boolean isBeginToRunning()
    {
        return ((m_nStatus == 1) || (m_nStatus == 0));
    }
    
    
    /**
     * ��ǰ�߳��Ƿ�������״̬
     * @return �����Ƿ�������״̬�Ĳ���ֵ
     */
    public boolean isRunning()
    {
        return m_nStatus == 1;
    }

    /**
     * ��ǰ�߳��Ƿ���ֹͣ״̬
     * @return �����Ƿ���ֹͣ״̬�Ĳ���ֵ
     */
    public boolean isStopped()
    {
        return m_nStatus == 3;
    }

    /**
     * ����߳�û�������������߳�
     */
    public void startup()
    {
        if(m_nStatus == 0 || m_nStatus == 1)
        {
            return;
        } else
        {
            m_nStatus = 0;
            start();
            return;
        }
    }

    /**
     * ����߳�û�йر���ر��߳�
     */
    public void shutdown()
    {
        if(m_nStatus == 2 || m_nStatus == 3)
        {
            return;
        } else
        {
            m_nStatus = 2;
            return;
        }
    }

    /**
     * �߳�˯�� Ĭ��ֵ1��
     */
    public static void sleep()
    {
        try
        {
            Thread.sleep(TimeConfig.DEFAULT_SLEEP_TIME);
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000000L);
            Log.log(e);
            Log.log("Engine.sleep : unepected exit !", 0x2000000000000000L);
        }
    }

    /**
     *  �߳�˯��
     * @param n �߳�˯��ʱ�䣬��λ����
     */
    public static void sleep(int n)
    {
        try
        {
            Thread.sleep(n);
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000000L);
            Log.log(e);
            Log.log("Engine.sleep : unepected exit !", 0x2000000000000000L);
        }
    }

    /**
     * �߳�˯�� Ĭ��ֵ5����
     */
    public static void nap()
    {
        try
        {
            Thread.sleep(TimeConfig.DEFAULT_NAP_TIME);
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000000L);
            Log.log(e);
            Log.log("Engine.nap : unepected exit !", 0x2000000000000000L);
        }
    }

    
    /**
     * �߳�˯�� 
     * @param n �߳�˯��ʱ�䣬��λ����
     */
    public static void nap(long n)
    {
        try
        {
            Thread.sleep(n);
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000000L);
            Log.log(e);
            Log.log("Engine.nap : unepected exit !", 0x2000000000000000L);
        }
    }
    
    /**
     * �ȴ��̴߳Ӽ���״̬����ɹ�����״̬
     * @param engine Ҫ�ȴ����߳�
     */
    public static void wait(Engine engine)
    {
        int nStatus = engine.m_nStatus;
        if(nStatus == 3 || nStatus == 1)
            return;
        long lStartTime = System.currentTimeMillis();
        while(engine.m_nStatus == nStatus) 
        {
            if(TimeConfig.DEFAULT_WAIT_TIME > 0L && System.currentTimeMillis() - lStartTime > TimeConfig.DEFAULT_WAIT_TIME)
                break;
            nap();
        }
    }

    //sleep����ʱ���߳���Ȼ�����ˣ����Ƕ���Ļ���û�б��ͷţ������߳���Ȼ�޷������������
    //��wait()����������߳����ߵ�ͬʱ�ͷŵ������������߳̿��Է��ʸö���


}
