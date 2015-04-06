package LeadTone.Database;

import LeadTone.TimeConfig;
import java.io.PrintStream;
import java.sql.*;


/**
 * 数据库连接对象，充当JDBC中的Connection的部分
 */
public class Database
{
    /**
     * 连接池的映射对象
     */
    public DatabasePool m_pool;
    /**
     * 数据库连接是否正在被使用的标识
     */
    boolean m_bInUse;
    /**
     * 数据库连接最近一次被使用的时刻
     */
    long m_lTimestamp;
    /**
     * 数据库连接已经被使用的次数
     */
    int m_nCount;
    /**
     * 数据库连接的超时时限
     */
    static long m_lTimeout=TimeConfig.DEFAULT_DATABASE_TIMEOUT;
    /**
     * 数据库连接对象
     */
    public Connection m_connCurrent;

    /**
     * 构造方法初始化类变量
     */
    public Database()
    {
        m_pool = null;
        m_bInUse = false;
        m_lTimestamp = 0L;
        m_nCount = 0;
        m_connCurrent = null;
        m_lTimestamp = System.currentTimeMillis();
    }

    /**
     * 输出数据库连接的状态
     * @param ps 打印输出流
     */
    public void dump(PrintStream ps)
    {
        ps.print("\tdbconn(" + m_connCurrent.toString() + ") : " + (m_bInUse ? "busy" : "idle") + "," + m_nCount + "\r\n");
    }

    /**
     * 释放所有使用的资源，清除m_pool映射对象
     */
    public void empty()
    {
        m_pool = null;
    }

    /**
     * 设置数据库连接超时时间
     * @param lTimeout 超时时限
     */
    public static void setTimeout(long lTimeout)
    {
        m_lTimeout = lTimeout;
    }

    /**
     * 检查数据库连接是否已经超时
     * @return 返回是否已经超时的布尔值
     */
    public boolean isTimeout()
    {
        return m_lTimeout > 0L && System.currentTimeMillis() - m_lTimestamp > m_lTimeout;
    }

    /**
     * 判断此数据连接与给定的数据库连接是否相同
     * @param database 给定的数据库连接
     * @return 返回是否相同的布尔值
     */
    public boolean equals(Database database)
    {
        if(m_bInUse != database.m_bInUse)
            return false;
        if(m_lTimestamp != database.m_lTimestamp)
            return false;
        return m_connCurrent == database.m_connCurrent;
    }

    /**
     * 检查此数据库连接是否正在被使用
     * @return 返回是否正在被使用的布尔值
     */
    public boolean isInUse()
    {
        return m_bInUse;
    }

    /**
     * 锁定要使用的数据库连接
     * @return 返回是否可以成功锁定
     */
    public boolean lock()
    {
        if(!m_bInUse)
        {
            m_bInUse = true;
            m_lTimestamp = System.currentTimeMillis();
            m_nCount++;
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 数据库连接使用完毕解锁
     */
    public void unlock()
    {
        m_bInUse = false;
    }

    /**
     * 通过JDBC中getMetaData()方法检查Connection的可用性
     * @return 返回是否可用
     */
    public boolean isValid()
    {
        try
        {
            m_connCurrent.getMetaData();
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Database.isValid : unexpected exit !");
        return false;
    }

    /**
     * 取得上次使用的开始时间
     * @return  返回上次使用的时刻
     */
    public long getTimestamp()
    {
        return m_lTimestamp;
    }

    /**
     * 从连接池中获取数据库连接
     * @throws SQLException
     */
    public void openConnection()
        throws SQLException
    {
        Database database = m_pool.retrieveDatabase();
        m_bInUse = database.m_bInUse;
        m_lTimestamp = database.m_lTimestamp;
        m_connCurrent = database.m_connCurrent;
    }

    /**
     * 通过JDBC直接建立数据库连接
     * @param strURL
     * @param strAccount
     * @param strPassword
     * @throws SQLException
     */
    public void openConnection(String strURL, String strAccount, String strPassword)
        throws SQLException
    {
        m_connCurrent = DriverManager.getConnection(strURL, strAccount, strPassword);
    }

    /**
     * 根据数据库配置对象DatabaseConfig建立数据库连接
     * @param dc
     * @throws SQLException
     */
    public void openConnection(DatabaseConfig dc)
        throws SQLException
    {
        m_connCurrent = DriverManager.getConnection(dc.m_strURL, dc.m_strAccount, dc.m_strPassword);
    }

    /**
     * 检查数据库连接是否已经建立，通过判断连接对象m_connCurrent是否为NULL实现
     * @return 返回数据库连接是否已经建立的布尔值
     */
    public boolean isConnected()
    {
        return m_connCurrent != null;
    }

    /**
     * 数据库连接使用完毕后归还连接池
     * @throws SQLException
     */
    public void closeConnection()
        throws SQLException
    {
        if(m_bInUse)
        {
            m_pool.releaseDatabase(this);
            m_bInUse = false;
            m_lTimestamp = 0L;
        } else
        if(m_connCurrent != null)
            m_connCurrent.close();
        m_connCurrent = null;
    }

    /**
     * 输出数据库操作中的警告内容
     * @param warn 警告内容
     * @throws SQLException
     */
    static void showWarnings(SQLWarning warn)
        throws SQLException
    {
        for(; warn != null; warn = warn.getNextWarning())
            System.out.println(warn.getMessage());

    }


}