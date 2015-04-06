package LeadTone.Database;

import LeadTone.Engine;
import LeadTone.Log;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 连接池管理类，继承自线程类，用一个单独线程监控连接池运行情况，充当JDBC的Driver部分
 */
public class DatabasePool extends Engine
{
    public DatabaseConfig m_dc;
    Vector m_databases;

    /**
     * 构造方法初始化类变量
     * @throws ClassNotFoundException
     */
    public DatabasePool()
        throws ClassNotFoundException
    {
        super("DatabasePool");
        m_dc = new DatabaseConfig();
        m_databases = new Vector();
        Class.forName(m_dc.m_strDriver);
    }

    /**
     * 构造方法初始化类变量
     * @throws ClassNotFoundException
     */
    public DatabasePool(DatabasePool pool)
        throws ClassNotFoundException
    {
        super("DatabasePool");
        m_dc = new DatabaseConfig();
        m_databases = new Vector();
        m_dc = pool.m_dc;
        Class.forName(m_dc.m_strDriver);
    }

    /**
     * 构造方法，由传入的数据库配置对象初始化类变量
     * @param dc
     * @throws ClassNotFoundException
     */
    public DatabasePool(DatabaseConfig dc)
        throws ClassNotFoundException
    {
        super("DatabasePool");
        m_dc = new DatabaseConfig();
        m_databases = new Vector();
        m_dc = dc;
        Class.forName(m_dc.m_strDriver);
    }

    /**
     * 构造方法，由传入的参数初始化类变量
     * @param strDriver 数据库连接驱动
     * @param strURL    数据库连接URL
     * @param strAccount 数据库连接帐号
     * @param strPassword 数据库连接密码
     * @throws ClassNotFoundException
     */
    public DatabasePool(String strDriver, String strURL, String strAccount, String strPassword)
        throws ClassNotFoundException
    {
        super("DatabasePool");
        m_dc = new DatabaseConfig();
        m_databases = new Vector();
        m_dc.m_strDriver = strDriver;
        m_dc.m_strURL = strURL;
        m_dc.m_strAccount = strAccount;
        m_dc.m_strPassword = strPassword;
        Class.forName(m_dc.m_strDriver);
    }

    /**
     * 输出数据库运行情况
     * @param ps 系统打印输出流
     */
    public void dump(PrintStream ps)
    {
        ps.print("\tdbpool(" + (isRunning() ? "+" : "-") + ") : count(" + m_databases.size() + ")\r\n");
        Database database;
        for(Enumeration databases = m_databases.elements(); databases != null && databases.hasMoreElements(); database.dump(ps))
            database = (Database)databases.nextElement();

    }

    /**
     * 关闭所有数据库，有synchronized修饰词，确保此方法的现程安全
     * @throws SQLException
     */
    public synchronized void closeDatabases()
        throws SQLException
    {
        for(Enumeration databases = m_databases.elements(); databases != null && databases.hasMoreElements();)
        {
            Database database = (Database)databases.nextElement();
            database.closeConnection();
            m_databases.removeElement(database);
            database = null;
        }

    }

    /**
     * 从连接池中移除某个数据库连接，有synchronized修饰词，确保此方法的现程安全
     * @param database 要从连接池中移除的数据库连接
     */
    public synchronized void removeDatabase(Database database)
    {
        m_databases.removeElement(database);
    }

    /**
     * 从连接池中获取数据库连接，有synchronized修饰词，确保此方法的现程安全
     * @return 返回从连接池中取出的数据库对象
     * @throws SQLException
     */
    public synchronized Database retrieveDatabase()
        throws SQLException
    {
        Database database;
        for(Enumeration databases = m_databases.elements(); databases != null && databases.hasMoreElements();)
        {
            database = (Database)databases.nextElement();
            if(database.isInUse())
            {
                if(database.isTimeout())
                {
                    database.unlock();
                    database.closeConnection();
                    m_databases.removeElement(database);
                    database = null;
                }
            } else
            if(database.m_nCount >= m_dc.m_nMaxUseCount)
            {
                database.unlock();
                database.closeConnection();
                m_databases.removeElement(database);
                database = null;
            } else
            if(database.lock())
                return database;
        }

        database = new Database();
        database.m_pool = this;
        database.openConnection(m_dc);
        database.lock();
        m_databases.addElement(database);
        return database;
    }

    /**
     * 释放被锁定的数据库连接
     * @param database 要解锁的数据库连接
     */
    public synchronized void releaseDatabase(Database database)
    {
        for(Enumeration databases = m_databases.elements(); databases != null && databases.hasMoreElements();)
        {
            Database db = (Database)databases.nextElement();
            if(db.equals(database))
                db.unlock();
        }

    }

    /**
     * 检验连接池的配置是否可用，尝试成功建立并关闭一个数据库连接
     * @return 返回连接池配置是否可用的布尔值
     */
    public boolean isValid()
    {
        try
        {
            Database database = new Database();
            database.openConnection(m_dc);
            database.closeConnection();
            database = null;
            return true;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("DatabasePool.isValid : unexpected exit !", 0x2000000000000002L);
        return false;
    }

    /**
     * 线程工作中不断检验连接池中连接的使用情况，
     * 检验达到最大使用次数或超时的数据库连接，将其释放并关闭
     */
    public void run()
    {
        try
        {
            m_nStatus = 3;
            Log.log("DatabasePool.run : thread startup !", 2L);
            m_nStatus = 1;
            while(isRunning()) 
            {
                Engine.sleep();
                for(Enumeration databases = m_databases.elements(); databases != null && databases.hasMoreElements();)
                {
                    Database database = (Database)databases.nextElement();
                    if(database.isInUse() ? database.isTimeout() : database.m_nCount >= m_dc.m_nMaxUseCount || !database.isValid())
                    {
                        removeDatabase(database);
                        database.unlock();
                        database.closeConnection();
                        database = null;
                    }
                }

            }
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000002L);
            Log.log(e);
            Log.log("DatabasePool.run : unexpected exit !", 0x2000000000000002L);
        }
        m_nStatus = 3;
        Log.log("DatabasePool.run : thread stopped !", 2L);
    }


}