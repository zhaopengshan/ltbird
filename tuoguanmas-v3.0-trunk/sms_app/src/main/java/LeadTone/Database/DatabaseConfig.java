package LeadTone.Database;

/**
 * 数据库基本配置信息类
 */
public class DatabaseConfig
{

    public static final int DEFAULT_MAX_USE_COUNT = 256;
    public static final int DATABASE_STANDARD = 0;
    public static final int DATABASE_ODBC = 256;
    public static final int DATABASE_MYSQL = 512;
    /**
     * 参考isMSSQLServer()方法，实现数据库类型判断
     */
    public static final int DATABASE_MSSQL = 768;
    /**
     * 参考isMSSQLServer()方法，实现数据库类型判断
     */
    public static final int DATABASE_MSSQL_6_5 = 769;
    /**
     * 参考isMSSQLServer()方法，实现数据库类型判断
     */
    public static final int DATABASE_MSSQL_7 = 770;
    /**
     * 参考isMSSQLServer()方法，实现数据库类型判断
     */
    public static final int DATABASE_MSSQL_2000 = 771;
    /**
     * 参考isMSSQLServer()方法，实现数据库类型判断
     */
    public static final int DATABASE_ORACLE = 1024;
    /**
     * 参考isMSSQLServer()方法，实现数据库类型判断
     */
    public static final int DATABASE_ORACLE_7 = 1025;
    /**
     * 参考isMSSQLServer()方法，实现数据库类型判断
     */
    public static final int DATABASE_ORACLE_8 = 1026;
    /**
     * 参考isMSSQLServer()方法，实现数据库类型判断
     */
    public static final int DATABASE_ORACLE_9 = 1027;
    /**
     * 参考isMSSQLServer()方法，实现数据库类型判断
     */
    public static final int DATABASE_INFORMIX = 1280;
    public static final int DATABASE_DB2 = 1536;
    /**
     * 数据库连接类型
     */
    public int m_nType;
    /**
     * 数据库连接驱动
     */
    public String m_strDriver;
    /**
     * 数据库连接URL
     */
    public String m_strURL;
    /**
     * 数据库连接帐号
     */
    public String m_strAccount;
    /**
     * 数据库连接密码
     */
    public String m_strPassword;
    /**
     * 数据库连接容许最大错误数
     */
    public int m_nMaxErrorCount;
    /**
     * 一个数据库连接最大使用次数
     */
    public int m_nMaxUseCount;

    /**
     * 构造方法初始化类变量
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
     * 构造方法初始化类变量
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
     * 参考isMSSQLServer()方法
     * @return 返回是否是ODBC连接类型
     */
    public boolean isODBC()
    {
        return (m_nType & 0xff00) == 256;
    }

    /**
     * 参考isMSSQLServer()方法
     * @return 返回是否是MYSQL数据库连接类型
     */
    public boolean isMySQL()
    {
        return (m_nType & 0xff00) == 512;
    }

     /**
     * 做与操作忽略同种数据库的版本差异，例如 769 & 0xff00 = 768，其中769表示MSSQLServer中的不同版本类型
     * @return 返回是否是MSSQLServer数据库连接类型
     */
    public boolean isMSSQLServer()
    {
        return (m_nType & 0xff00) == 768;
    }

    /**
     * 参考isMSSQLServer()方法
     * @return 返回是否是Oracle数据库连接类型
     */
    public boolean isOracle()
    {
        return (m_nType & 0xff00) == 1024;
    }

    /**
     * 判断数据库连接类型
     * @return 返回是否是MSSQLServer2000数据库类型
     */
    public boolean isMSSQLServer2000()
    {
        return m_nType == 771;
    }

    /**
     * 判断数据库连接类型
     * @return 返回是否是MSSQLServer 6.5数据库类型
     */
    public boolean isMSSQLServer65()
    {
        return m_nType == 769;
    }

    /**
     * 判断数据库连接类型
     * @return 返回是否是MSSQLServer 7.0数据库类型
     */
    public boolean isMSSQLServer70()
    {
        return m_nType == 770;
    }

    /**
     * 参考isMSSQLServer()方法
     * @return 返回是否是DB2数据库连接类型
     */
    public boolean isDB2()
    {
        return (m_nType & 0xff00) == 1536;
    }

    /**
     * 根据数据库类型到数据库类型代码转换函数
     * @param strType 数据库类型
     * @return 类型代码
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