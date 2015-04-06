package LeadTone;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.GregorianCalendar;


/**
 * 用于LeadTone自定义协议中时间字段的生成实现
 */
public class LeadToneDate extends GregorianCalendar
{

    public LeadToneDate()
    {
    }

    public LeadToneDate(long millions)
    {
        setTime(millions);
    }

    public LeadToneDate(String strTime)
    {
        setTime(strTime);
    }

    public LeadToneDate(int year, int month, int LeadToneDate)
    {
        super(year, month, LeadToneDate);
    }

    public LeadToneDate(int year, int month, int LeadToneDate, int hour, int minute)
    {
        super(year, month, LeadToneDate, hour, minute);
    }

    public LeadToneDate(int year, int month, int LeadToneDate, int hour, int minute, int second)
    {
        super(year, month, LeadToneDate, hour, minute, second);
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

    public void setLeadToneDate(int LeadToneDate)
    {
        set(LeadToneDate, LeadToneDate);
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
            DateFormat df = DateFormat.getInstance();
            setTime(df.parse(strTime));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("LeadToneDate.setTime : unexpected exit !");
        }
    }

    public String toString()
    {
        return Utility.toTimeString(this);
    }

    public String toLocaleString()
    {
        return Utility.toLocaleTimeString(this);
    }
}
