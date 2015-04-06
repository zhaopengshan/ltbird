package LeadTone;


/**
 * 线程基类，所有线程类 均继承自此类，拥有 加速、运行，减速，停止 四种线程状态
 */
public class Engine extends Thread
{
    public static final int THREAD_ACCELERATE = 0;
    public static final int THREAD_RUNNING = 1;
    public static final int THREAD_DECELERATE = 2;
    public static final int THREAD_STOPPED = 3;
   
    //0:加速，1：运行,2：减速，3：停止
    public int m_nStatus;
    
    public String strName;
    
    /**
     *  构造方法，复写父类Thread方法，初始化线程初始状态为停止状态
     *  @param strName 线程名称
     */
    public Engine(String strName)
    {
        super(strName);
        m_nStatus = 3;

        this.strName = "";
        this.strName = new String(strName);

    }

    
    /**
     * 当前线程是否处于启动过程状态
     * @return 返回是否处于启动过程状态的布尔值
     */
    public boolean isBeginToRunning()
    {
        return ((m_nStatus == 1) || (m_nStatus == 0));
    }
    
    
    /**
     * 当前线程是否处于运行状态
     * @return 返回是否处于运行状态的布尔值
     */
    public boolean isRunning()
    {
        return m_nStatus == 1;
    }

    /**
     * 当前线程是否处于停止状态
     * @return 返回是否处于停止状态的布尔值
     */
    public boolean isStopped()
    {
        return m_nStatus == 3;
    }

    /**
     * 如果线程没有启动则启动线程
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
     * 如果线程没有关闭则关闭线程
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
     * 线程睡眠 默认值1秒
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
     *  线程睡眠
     * @param n 线程睡眠时间，单位毫秒
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
     * 线程睡眠 默认值5毫秒
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
     * 线程睡眠 
     * @param n 线程睡眠时间，单位毫秒
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
     * 等待线程从加速状态进入成功运行状态
     * @param engine 要等待的线程
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

    //sleep（）时，线程虽然休眠了，但是对象的机锁没有被释放，其他线程仍然无法访问这个对象。
    //而wait()方法则会在线程休眠的同时释放掉机锁，其他线程可以访问该对象。


}
