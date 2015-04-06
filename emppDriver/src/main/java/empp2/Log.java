package empp2;

import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;


/**
 * 用于记录系统工作日志，实现类似log4j的功能
 * 类中定义的常量为实现日志订阅功能使用
 */
public class Log
{

    public static final long LOG_ALL = -1L;
    public static final long LOG_NONE = 0L;
    public static final long LOG_TO_SCREEN = 0x8000000000000000L;
    public static final long LOG_TO_FILE = 0x4000000000000000L;
    public static final long LOG_EXCEPTION = 0x2000000000000000L;
    public static final long LOG_BUFFER = 0x800000000000000L;
    public static final long LOG_PACKET = 0x400000000000000L;
    public static final long LOG_CMPP_PACKET = 0x200000000000000L;
    public static final long LOG_CMPP_CONNECT = 0x80000000000000L;
    public static final long LOG_CMPP_CONNECT_RESPONSE = 0x80000000000000L;
    public static final long LOG_CMPP_TERMINATE = 0x40000000000000L;
    public static final long LOG_CMPP_TERMINATE_RESPONSE = 0x40000000000000L;
    public static final long LOG_CMPP_ACTIVETEST = 0x20000000000000L;
    public static final long LOG_CMPP_ACTIVETEST_RESPONSE = 0x20000000000000L;
    public static final long LOG_CMPP_CANCEL = 0x10000000000000L;
    public static final long LOG_CMPP_CANCEL_RESPONSE = 0x10000000000000L;
    public static final long LOG_CMPP_QUERY = 0x8000000000000L;
    public static final long LOG_CMPP_QUERY_RESPONSE = 0x8000000000000L;
    public static final long LOG_CMPP_DELIVER = 0x4000000000000L;
    public static final long LOG_CMPP_DELIVER_RESPONSE = 0x4000000000000L;
    public static final long LOG_CMPP_SUBMIT = 0x2000000000000L;
    public static final long LOG_CMPP_SUBMIT_RESPONSE = 0x2000000000000L;
    public static final long LOG_CMPP_FORWARD = 0x6000000000000L;
    public static final long LOG_CMPP_FORWARD_RESPONSE = 0x6000000000000L;
    public static final long LOG_CMPP_NACK_RESPONSE = 0x1000000000000L;
    public static final long LOG_CNGP_PACKET = 0x500000000000000L;
    public static final long LOG_SMGP_PACKET = 0x600000000000000L;
    public static final long LOG_SMPP_PACKET = 0x200000000000000L;
    public static final long LOG_SMPP_OPTIONAL_PARAMETER = 0x100000000000000L;
    public static final long LOG_SMPP_ADDRESS = 0x100000000000000L;
    public static final long LOG_SMPP_BIND = 0x80000000000000L;
    public static final long LOG_SMPP_BIND_RESPONSE = 0x80000000000000L;
    public static final long LOG_SMPP_UNBIND = 0x40000000000000L;
    public static final long LOG_SMPP_UNBIND_RESPONSE = 0x40000000000000L;
    public static final long LOG_SMPP_ENQUIRE_LINK = 0x20000000000000L;
    public static final long LOG_SMPP_ENQUIRE_LINK_RESPONSE = 0x20000000000000L;
    public static final long LOG_SMPP_COMMAND = 0x18000000000000L;
    public static final long LOG_SMPP_COMMAND_RESPONSE = 0x18000000000000L;
    public static final long LOG_SMPP_SM = 0x6000000000000L;
    public static final long LOG_SMPP_SM_RESPONSE = 0x6000000000000L;
    public static final long LOG_SMPP_GENERIC_NACK_RESPONSE = 0x1000000000000L;
    public static final long LOG_SMPP_OUTBIND = 0x1000000000000L;
    public static final long LOG_SMPP_ALERT_NOTIFICATION = 0x1000000000000L;
    public static final long LOG_SGIP_PACKET = 0x200000000000000L;
    public static final long LOG_SGIP_BIND = 0x80000000000000L;
    public static final long LOG_SGIP_BIND_RESPONSE = 0x80000000000000L;
    public static final long LOG_SGIP_UNBIND = 0x40000000000000L;
    public static final long LOG_SGIP_UNBIND_RESPONSE = 0x40000000000000L;
    public static final long LOG_SGIP_TRACE = 0x8000000000000L;
    public static final long LOG_SGIP_TRACE_RESPONSE = 0x8000000000000L;
    public static final long LOG_SGIP_DELIVER = 0x4000000000000L;
    public static final long LOG_SGIP_DELIVER_RESPONSE = 0x4000000000000L;
    public static final long LOG_SGIP_REPORT = 0x4000000000000L;
    public static final long LOG_SGIP_REPORT_RESPONSE = 0x4000000000000L;
    public static final long LOG_SGIP_SUBMIT = 0x2000000000000L;
    public static final long LOG_SGIP_SUBMIT_RESPONSE = 0x2000000000000L;
    public static final long LOG_SGIP_USERRPT = 0x1000000000000L;
    public static final long LOG_SGIP_USERRPT_RESPONSE = 0x1000000000000L;
    public static final long LOG_UNWRAP_METHOD = 0x800000000000L;
    public static final long LOG_WRAP_METHOD = 0x800000000000L;
    public static final long LOG_ISVALID_METHOD = 0x600000000000L;
    public static final long LOG_OUTPUT_METHOD = 0x100000000000L;
    public static final long LOG_INPUT_METHOD = 0x100000000000L;
    public static final long LOG_GATEWAY_ENGINE = 0x80000000000L;
    public static final long LOG_SESSION_ENGINE = 0x40000000000L;
    public static final long LOG_OUTPUT_ENGINE = 0x20000000000L;
    public static final long LOG_INPUT_ENGINE = 0x10000000000L;
    public static final long LOG_ENGINE = 0L;
    public static final long LOG_CMPP_DATABASE = 0x8000000000L;
    public static final long LOG_CMPP_DATABASE_QUERY = 0x4000000000L;
    public static final long LOG_CMPP_DATABASE_DELIVER = 0x2000000000L;
    public static final long LOG_CMPP_DATABASE_SUBMIT = 0x1000000000L;
    public static final long LOG_CMPP_XML_EXCHANGER = 0x800000000L;
    public static final long LOG_CMPP_XML_PORTER = 0x400000000L;
    public static final long LOG_CMPP_XML_WRITER = 0x200000000L;
    public static final long LOG_CMPP_XML_READER = 0x100000000L;
    public static final long LOG_SMPP_EXCHANGER = 0x80000000L;
    public static final long LOG_SMPP_PORTER = 0x40000000L;
    public static final long LOG_SMPP_WRITER = 0x20000000L;
    public static final long LOG_SMPP_READER = 0x10000000L;
    public static final long LOG_DATA_EXCHANGER = 4L;
    public static final long LOG_DATABASE_POOL = 2L;
    public static final long LOG_CENTER = 1L;
    public static final long LOG_CMPP_CENTER = 1L;
    public static final long LOG_UNION_CENTER = 1L;
    public static final long LOG_EXCHANGE_CENTER = 1L;
    public static final long LOG_LEADTONE_LOGIC = 0x4000000000000L;
    static long m_lLog = -1L;
    static FileLog m_fl = null;
    static String m_strLastException = "no exceptions";

    public Log()
    {
    }

    public static String getLastException()
    {
        return m_strLastException;
    }

    public static void setLog(long lLog)
    {
        m_lLog = lLog;
    }

    public static long getLogs()
    {
        return m_lLog;
    }

    public static boolean isRequested(long lLog)
    {
        return (m_lLog & lLog) != 0L;
    }

    public static void addRequest(long lLog)
    {
        m_lLog |= lLog;
    }

    public static void removeRequest(long lLog)
    {
        m_lLog &= ~lLog;
    }

    public static boolean open(String strFileName,String strFileType)
    {
        try
        {
            m_fl = new FileLog(strFileName,strFileType);
            m_fl.open();
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Log.open : unexpected exit !");
        return false;
    }

    public static void close()
    {
        try
        {
            if(m_fl != null)
                m_fl.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Log.close : unexpected exit !");
        }
        m_fl = null;
    }

    public static synchronized void toScreen(String strLine, long lLog)
    {
        if(isRequested(lLog))
            if(strLine.charAt(0) == '\t' && strLine.length() > 69)
                System.out.println(strLine.substring(0, 69) + "...");
            else
            if(strLine.length() > 79)
                System.out.println(strLine.substring(0, 76) + "...");
            else
                System.out.println(strLine);
    }

    public static synchronized void toFile(FileLog fl, String strLine, long lLog)
        throws IOException
    {
        if(fl != null && isRequested(lLog))
        {
            LeadToneDate date = new LeadToneDate();
            fl.log(date.toLocaleString() + ">" + strLine);
//            fl.log( strLine);
        }
    }

    public static void log(Exception e)
    {
        if(e == null)
            return;
        String strException = e.getMessage();
        if(strException == null)
            m_strLastException = "null exception !";
        else
            m_strLastException = new String(strException);
        if(!isRequested(0x2000000000000000L))
            return;
        if(isRequested(0x8000000000000000L))
            e.printStackTrace();
        if(m_fl != null && isRequested(0x4000000000000000L))
            m_fl.log(e);
    }

    public static void log(String strLine, long lLog)
    {
        try
        {
            if(isRequested(0x8000000000000000L))
                toScreen(strLine, lLog);
            if(isRequested(0x4000000000000000L))
                toFile(m_fl, strLine, lLog);
        }
        catch(Exception e)
        {
            System.out.println("Log.log : " + e.getMessage());
            e.printStackTrace();
            System.out.println("Log.log : unexpected exit !");
        }
    }

    public static void setLog(String strLogRequests)
    {
        m_lLog = getLogRequests(strLogRequests);
    }

    public static long getLogRequests(String strLogRequests)
    {
        long lLogRequests = 0L;
        for(StringTokenizer st = new StringTokenizer(strLogRequests, "|"); st.hasMoreTokens();)
        {
            String strLog = st.nextToken();
            lLogRequests |= getLog(strLog);
        }

        return lLogRequests;
    }

    public static long getLog(String strLog)
    {
        if(strLog == null || strLog.length() <= 0)
            return 0L;
        if(strLog.equals("LOG_ALL"))
            return -1L;
        if(strLog.equals("LOG_NONE"))
            return 0L;
        return !strLog.equals("LOG_NORMAL") ? 0L : 0x2000600000000001L;
    }



}
