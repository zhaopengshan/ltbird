package LeadTone;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 用于SmsGateway Application中使用的基于CMPP，SMPP等协议实现中时间字段生成的实现
 */
public class SimpleTime extends GregorianCalendar
{
    public static final long MILLIONSECOND = 1L;
    public static final long SECONDS = 1000L;
    public static final long MINUTES = 60000L;
    public static final long HOURS = 3600000L;
    public static final long DAYS = 86400000L;

    public SimpleTime()
    {
    }

    public SimpleTime(long millions)
    {
        setTime(millions);
    }

    public SimpleTime(String strTime)
    {
        setTime(strTime);
    }

    public SimpleTime(int year, int month, int date)
    {
        super(year, month, date);
    }

    public SimpleTime(int year, int month, int date, int hour, int minute)
    {
        super(year, month, date, hour, minute);
    }

    public SimpleTime(int year, int month, int SimpleTime, int hour, int minute, int second)
    {
        super(year, month, SimpleTime, hour, minute, second);
    }

    public int getYear()
    {
        return get(1);
    }

    public int getMonth()
    {
        return get(2);
    }

    public int getDate()
    {
        return get(5);
    }

    public int getHours()
    {
        return get(11);
    }

    public int getMinutes()
    {
        return get(12);
    }

    public int getSeconds()
    {
        return get(13);
    }

    public void setYear(int year)
    {
        set(1, year);
    }

    public void setMonth(int month)
    {
        set(2, month);
    }

    public void setDate(int date)
    {
        set(date, date);
    }

    public void setHours(int hours)
    {
        set(10, hours);
    }

    public void setMinutes(int minutes)
    {
        set(12, minutes);
    }

    public void setSeconds(int seconds)
    {
        set(13, seconds);
    }

    public void setTime(long milliseconds)
    {
        setTimeInMillis(milliseconds);
    }

    public void setTime(String strTime)
    {
        try
        {
            DateFormat df = DateFormat.getDateTimeInstance(2, 2);
            setTime(df.parse(strTime));
        }
        catch(Exception e)
        {
            System.out.println("SimpleTime.setTime : " + e.getMessage());
            e.printStackTrace();
            System.out.println("SimpleTime.setTime : unexpected exit !");
        }
    }

    public String toDateString()
    {
        String strDay = "";
        strDay = strDay + getYear();
        strDay = strDay + (getMonth() + 1 >= 10 ? "" : "0") + (getMonth() + 1);
        strDay = strDay + (getDate() >= 10 ? "" : "0") + getDate();
        return strDay;
    }

    public String toCompactString()
    {
        String strTime = "";
        strTime = strTime + getYear();
        strTime = strTime + (getMonth() + 1 >= 10 ? "" : "0") + (getMonth() + 1);
        strTime = strTime + (getDate() >= 10 ? "" : "0") + getDate();
        strTime = strTime + (getHours() >= 10 ? "" : "0") + getHours();
        strTime = strTime + (getMinutes() >= 10 ? "" : "0") + getMinutes();
        strTime = strTime + (getSeconds() >= 10 ? "" : "0") + getSeconds();
        return strTime;
    }

    public String toStandardString()
    {
        String strTime = "";
        strTime = strTime + getYear() + "-";
        strTime = strTime + (getMonth() + 1 >= 10 ? "" : "0") + (getMonth() + 1) + "-";
        strTime = strTime + (getDate() >= 10 ? "" : "0") + getDate() + " ";
        strTime = strTime + (getHours() >= 10 ? "" : "0") + getHours() + ":";
        strTime = strTime + (getMinutes() >= 10 ? "" : "0") + getMinutes() + ":";
        strTime = strTime + (getSeconds() >= 10 ? "" : "0") + getSeconds();
        return strTime;
    }

    public String toLocaleString()
    {
        return super.toString();
    }

    public static void main(String args[])
    {
        try
        {
            String str = "2006-03-13 15:43:05";
            DateFormat df = DateFormat.getDateTimeInstance(2, 2);
            Date date = df.parse(str);
            System.out.println(date.toString());
        }
        catch(Exception e)
        {
            System.out.println("SimpleTime.main : " + e.getMessage());
            System.out.println("SimpleTime.main : unexpected exit !");
        }
    }



}
