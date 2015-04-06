package LeadTone;

import java.io.*;

/**
 * 用于向日志文件中写入日志信息
 */
public class FileLog
{

	String m_strFileName;
    String m_strFileType;
    LeadToneDate m_opendate;
    FileOutputStream m_fos;
    PrintStream m_ps;

    public FileLog(String strFileName,String strFileType)
    {
        m_strFileName = null;
        m_opendate = null;
        m_fos = null;
        m_ps = null;
        m_strFileName = strFileName;
        m_strFileType = strFileType;
    }


	public void open()
        throws IOException
    {
        m_opendate = new LeadToneDate();
        m_fos = new FileOutputStream(m_strFileName+"_"+Utility.toDayString(m_opendate) + "." + m_strFileType, true);
        m_ps = new PrintStream(m_fos);
    }

    public void log(String strLine)
        throws IOException
    {
        LeadToneDate date = new LeadToneDate();
        if(date.getDate() != m_opendate.getDate())
        {
            close();
            open();
        }
        m_ps.println(strLine);
    }

    public void log(Exception e)
    {
        e.printStackTrace(m_ps);
    }

    public void close()
        throws IOException
    {
        m_ps.close();
        m_fos.close();
    }


}
