package LeadTone.Database;

import LeadTone.TimeConfig;
import java.io.PrintStream;
import java.sql.*;


/**
 * ���ݿ����Ӷ��󣬳䵱JDBC�е�Connection�Ĳ���
 */
public class Database
{
    /**
     * ���ӳص�ӳ�����
     */
    public DatabasePool m_pool;
    /**
     * ���ݿ������Ƿ����ڱ�ʹ�õı�ʶ
     */
    boolean m_bInUse;
    /**
     * ���ݿ��������һ�α�ʹ�õ�ʱ��
     */
    long m_lTimestamp;
    /**
     * ���ݿ������Ѿ���ʹ�õĴ���
     */
    int m_nCount;
    /**
     * ���ݿ����ӵĳ�ʱʱ��
     */
    static long m_lTimeout=TimeConfig.DEFAULT_DATABASE_TIMEOUT;
    /**
     * ���ݿ����Ӷ���
     */
    public Connection m_connCurrent;

    /**
     * ���췽����ʼ�������
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
     * ������ݿ����ӵ�״̬
     * @param ps ��ӡ�����
     */
    public void dump(PrintStream ps)
    {
        ps.print("\tdbconn(" + m_connCurrent.toString() + ") : " + (m_bInUse ? "busy" : "idle") + "," + m_nCount + "\r\n");
    }

    /**
     * �ͷ�����ʹ�õ���Դ�����m_poolӳ�����
     */
    public void empty()
    {
        m_pool = null;
    }

    /**
     * �������ݿ����ӳ�ʱʱ��
     * @param lTimeout ��ʱʱ��
     */
    public static void setTimeout(long lTimeout)
    {
        m_lTimeout = lTimeout;
    }

    /**
     * ������ݿ������Ƿ��Ѿ���ʱ
     * @return �����Ƿ��Ѿ���ʱ�Ĳ���ֵ
     */
    public boolean isTimeout()
    {
        return m_lTimeout > 0L && System.currentTimeMillis() - m_lTimestamp > m_lTimeout;
    }

    /**
     * �жϴ�������������������ݿ������Ƿ���ͬ
     * @param database ���������ݿ�����
     * @return �����Ƿ���ͬ�Ĳ���ֵ
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
     * �������ݿ������Ƿ����ڱ�ʹ��
     * @return �����Ƿ����ڱ�ʹ�õĲ���ֵ
     */
    public boolean isInUse()
    {
        return m_bInUse;
    }

    /**
     * ����Ҫʹ�õ����ݿ�����
     * @return �����Ƿ���Գɹ�����
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
     * ���ݿ�����ʹ����Ͻ���
     */
    public void unlock()
    {
        m_bInUse = false;
    }

    /**
     * ͨ��JDBC��getMetaData()�������Connection�Ŀ�����
     * @return �����Ƿ����
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
     * ȡ���ϴ�ʹ�õĿ�ʼʱ��
     * @return  �����ϴ�ʹ�õ�ʱ��
     */
    public long getTimestamp()
    {
        return m_lTimestamp;
    }

    /**
     * �����ӳ��л�ȡ���ݿ�����
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
     * ͨ��JDBCֱ�ӽ������ݿ�����
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
     * �������ݿ����ö���DatabaseConfig�������ݿ�����
     * @param dc
     * @throws SQLException
     */
    public void openConnection(DatabaseConfig dc)
        throws SQLException
    {
        m_connCurrent = DriverManager.getConnection(dc.m_strURL, dc.m_strAccount, dc.m_strPassword);
    }

    /**
     * ������ݿ������Ƿ��Ѿ�������ͨ���ж����Ӷ���m_connCurrent�Ƿ�ΪNULLʵ��
     * @return �������ݿ������Ƿ��Ѿ������Ĳ���ֵ
     */
    public boolean isConnected()
    {
        return m_connCurrent != null;
    }

    /**
     * ���ݿ�����ʹ����Ϻ�黹���ӳ�
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
     * ������ݿ�����еľ�������
     * @param warn ��������
     * @throws SQLException
     */
    static void showWarnings(SQLWarning warn)
        throws SQLException
    {
        for(; warn != null; warn = warn.getNextWarning())
            System.out.println(warn.getMessage());

    }


}