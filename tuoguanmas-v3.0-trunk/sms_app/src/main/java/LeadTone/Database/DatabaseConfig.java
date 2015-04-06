package LeadTone.Database;

/**
 * ���ݿ����������Ϣ��
 */
public class DatabaseConfig
{

    public static final int DEFAULT_MAX_USE_COUNT = 256;
    public static final int DATABASE_STANDARD = 0;
    public static final int DATABASE_ODBC = 256;
    public static final int DATABASE_MYSQL = 512;
    /**
     * �ο�isMSSQLServer()������ʵ�����ݿ������ж�
     */
    public static final int DATABASE_MSSQL = 768;
    /**
     * �ο�isMSSQLServer()������ʵ�����ݿ������ж�
     */
    public static final int DATABASE_MSSQL_6_5 = 769;
    /**
     * �ο�isMSSQLServer()������ʵ�����ݿ������ж�
     */
    public static final int DATABASE_MSSQL_7 = 770;
    /**
     * �ο�isMSSQLServer()������ʵ�����ݿ������ж�
     */
    public static final int DATABASE_MSSQL_2000 = 771;
    /**
     * �ο�isMSSQLServer()������ʵ�����ݿ������ж�
     */
    public static final int DATABASE_ORACLE = 1024;
    /**
     * �ο�isMSSQLServer()������ʵ�����ݿ������ж�
     */
    public static final int DATABASE_ORACLE_7 = 1025;
    /**
     * �ο�isMSSQLServer()������ʵ�����ݿ������ж�
     */
    public static final int DATABASE_ORACLE_8 = 1026;
    /**
     * �ο�isMSSQLServer()������ʵ�����ݿ������ж�
     */
    public static final int DATABASE_ORACLE_9 = 1027;
    /**
     * �ο�isMSSQLServer()������ʵ�����ݿ������ж�
     */
    public static final int DATABASE_INFORMIX = 1280;
    public static final int DATABASE_DB2 = 1536;
    /**
     * ���ݿ���������
     */
    public int m_nType;
    /**
     * ���ݿ���������
     */
    public String m_strDriver;
    /**
     * ���ݿ�����URL
     */
    public String m_strURL;
    /**
     * ���ݿ������ʺ�
     */
    public String m_strAccount;
    /**
     * ���ݿ���������
     */
    public String m_strPassword;
    /**
     * ���ݿ�����������������
     */
    public int m_nMaxErrorCount;
    /**
     * һ�����ݿ��������ʹ�ô���
     */
    public int m_nMaxUseCount;

    /**
     * ���췽����ʼ�������
     */
    public DatabaseConfig()
    {
        m_nType = 512;
        m_strDriver = "oracle.jdbc.driver.OracleDriver";
        m_strURL = "jdbc:oracle:oci8:@ORCL";
        m_strAccount = "cmppe";
        m_strPassword = "leadtone";
        m_nMaxErrorCount = 5;
        m_nMaxUseCount = 256;
    }

    /**
     * ���췽����ʼ�������
     */
    public DatabaseConfig(String strDriver, String strURL, String strAccount, String strPassword)
    {
        m_nType = 512;
        m_strDriver = "oracle.jdbc.driver.OracleDriver";
        m_strURL = "jdbc:oracle:oci8:@ORCL";
        m_strAccount = "cmppe";
        m_strPassword = "leadtone";
        m_nMaxErrorCount = 5;
        m_nMaxUseCount = 256;
        m_strDriver = strDriver;
        m_strURL = strURL;
        m_strAccount = strAccount;
        m_strPassword = strPassword;
    }

    /**
     * �ο�isMSSQLServer()����
     * @return �����Ƿ���ODBC��������
     */
    public boolean isODBC()
    {
        return (m_nType & 0xff00) == 256;
    }

    /**
     * �ο�isMSSQLServer()����
     * @return �����Ƿ���MYSQL���ݿ���������
     */
    public boolean isMySQL()
    {
        return (m_nType & 0xff00) == 512;
    }

     /**
     * �����������ͬ�����ݿ�İ汾���죬���� 769 & 0xff00 = 768������769��ʾMSSQLServer�еĲ�ͬ�汾����
     * @return �����Ƿ���MSSQLServer���ݿ���������
     */
    public boolean isMSSQLServer()
    {
        return (m_nType & 0xff00) == 768;
    }

    /**
     * �ο�isMSSQLServer()����
     * @return �����Ƿ���Oracle���ݿ���������
     */
    public boolean isOracle()
    {
        return (m_nType & 0xff00) == 1024;
    }

    /**
     * �ж����ݿ���������
     * @return �����Ƿ���MSSQLServer2000���ݿ�����
     */
    public boolean isMSSQLServer2000()
    {
        return m_nType == 771;
    }

    /**
     * �ж����ݿ���������
     * @return �����Ƿ���MSSQLServer 6.5���ݿ�����
     */
    public boolean isMSSQLServer65()
    {
        return m_nType == 769;
    }

    /**
     * �ж����ݿ���������
     * @return �����Ƿ���MSSQLServer 7.0���ݿ�����
     */
    public boolean isMSSQLServer70()
    {
        return m_nType == 770;
    }

    /**
     * �ο�isMSSQLServer()����
     * @return �����Ƿ���DB2���ݿ���������
     */
    public boolean isDB2()
    {
        return (m_nType & 0xff00) == 1536;
    }

    /**
     * �������ݿ����͵����ݿ����ʹ���ת������
     * @param strType ���ݿ�����
     * @return ���ʹ���
     */
    public static int toType(String strType)
    {
        if(strType.equals("odbc"))
            return 256;
        if(strType.equals("mysql"))
            return 512;
        if(strType.equals("mssql"))
            return 768;
        if(strType.equals("mssql_6_5"))
            return 769;
        if(strType.equals("mssql_7"))
            return 770;
        if(strType.equals("mssql_2000"))
            return 771;
        if(strType.equals("oracle_7"))
            return 1025;
        if(strType.equals("oracle_8"))
            return 1026;
        if(strType.equals("oracle_9"))
            return 1027;
        if(strType.equals("infomix"))
            return 1280;
        return !strType.equals("db2") ? 0 : 1536;
    }



}