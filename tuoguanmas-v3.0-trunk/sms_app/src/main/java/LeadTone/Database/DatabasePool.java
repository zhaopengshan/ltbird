package LeadTone.Database;

import LeadTone.Engine;
import LeadTone.Log;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * ���ӳع����࣬�̳����߳��࣬��һ�������̼߳�����ӳ�����������䵱JDBC��Driver����
 */
public class DatabasePool extends Engine
{
    public DatabaseConfig m_dc;
    Vector m_databases;

    /**
     * ���췽����ʼ�������
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
     * ���췽����ʼ�������
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
     * ���췽�����ɴ�������ݿ����ö����ʼ�������
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
     * ���췽�����ɴ���Ĳ�����ʼ�������
     * @param strDriver ���ݿ���������
     * @param strURL    ���ݿ�����URL
     * @param strAccount ���ݿ������ʺ�
     * @param strPassword ���ݿ���������
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
     * ������ݿ��������
     * @param ps ϵͳ��ӡ�����
     */
    public void dump(PrintStream ps)
    {
        ps.print("\tdbpool(" + (isRunning() ? "+" : "-") + ") : count(" + m_databases.size() + ")\r\n");
        Database database;
        for(Enumeration databases = m_databases.elements(); databases != null && databases.hasMoreElements(); database.dump(ps))
            database = (Database)databases.nextElement();

    }

    /**
     * �ر��������ݿ⣬��synchronized���δʣ�ȷ���˷������ֳ̰�ȫ
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
     * �����ӳ����Ƴ�ĳ�����ݿ����ӣ���synchronized���δʣ�ȷ���˷������ֳ̰�ȫ
     * @param database Ҫ�����ӳ����Ƴ������ݿ�����
     */
    public synchronized void removeDatabase(Database database)
    {
        m_databases.removeElement(database);
    }

    /**
     * �����ӳ��л�ȡ���ݿ����ӣ���synchronized���δʣ�ȷ���˷������ֳ̰�ȫ
     * @return ���ش����ӳ���ȡ�������ݿ����
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
     * �ͷű����������ݿ�����
     * @param database Ҫ���������ݿ�����
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
     * �������ӳص������Ƿ���ã����Գɹ��������ر�һ�����ݿ�����
     * @return �������ӳ������Ƿ���õĲ���ֵ
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
     * �̹߳����в��ϼ������ӳ������ӵ�ʹ�������
     * ����ﵽ���ʹ�ô�����ʱ�����ݿ����ӣ������ͷŲ��ر�
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