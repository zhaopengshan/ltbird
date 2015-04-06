package LeadTone.XML;

import LeadTone.SimpleTime;
import java.io.PrintStream;


/**
 * 用于读取配置文件config.xml，解析xml，装载配置参数
 * 整个XML包下类实现类似dom4j的功能
 */
public class XMLAttribute
{
    public String m_strName;
    public String m_strValue;

    public XMLAttribute()
    {
        m_strName = null;
        m_strValue = null;
    }

    public XMLAttribute(String strName, String strValue)
    {
        m_strName = null;
        m_strValue = null;
        m_strName = strName;
        m_strValue = strValue;
    }

    public XMLAttribute(XMLAttribute attribute)
    {
        m_strName = null;
        m_strValue = null;
        m_strName = attribute.m_strName;
        m_strValue = attribute.m_strValue;
    }

    public void empty()
    {
        m_strName = null;
        m_strValue = null;
    }

    public boolean equals(XMLAttribute attribute)
    {
        if(m_strName == null || !m_strName.equals(attribute.m_strName))
            return false;
        return m_strValue != null && m_strValue.equals(attribute.m_strValue);
    }

    private int parseName(String strContent, int nStart)
    {
        int nTemp = nStart;
        do
        {
            char c = strContent.charAt(nTemp);
            if(c == '=')
                break;
            if(!Character.isLetter(c) && !Character.isDigit(c) && c != '-' && c != '_')
                return nStart;
        } while(++nTemp < strContent.length());
        m_strName = strContent.substring(nStart, nTemp);
        return nTemp;
    }

    private int parseValue(String strContent, int nStart)
    {
        int nTemp = nStart;
        if(strContent.charAt(nTemp) == '"')
        {
            nTemp++;
            boolean bEndFound = false;
            for(; nTemp < strContent.length(); nTemp++)
            {
                char c = strContent.charAt(nTemp);
                if(c != '"')
                    continue;
                bEndFound = true;
                break;
            }

            if(bEndFound)
            {
                m_strValue = strContent.substring(nStart + 1, nTemp);
                return nTemp + 1;
            } else
            {
                return nStart;
            }
        } else
        {
            return nStart;
        }
    }

    public int parse(String strContent, int nStart)
        throws XMLException
    {
        XMLParser.check(strContent, nStart);
        int nTemp = nStart;
        int nNext = 0;
        nTemp = XMLParser.jumpBlankspace(strContent, nTemp);
        XMLParser.check(strContent, nStart);
        nNext = parseName(strContent, nTemp);
        if(nNext == nTemp)
            return nStart;
        nTemp = nNext;
        if(m_strName.length() > 64)
            return nStart;
        XMLParser.check(strContent, nTemp);
        if(strContent.charAt(nTemp) != '=')
            return nStart;
        nTemp++;
        XMLParser.check(strContent, nTemp);
        nNext = parseValue(strContent, nTemp);
        if(nNext == nTemp)
        {
            return nStart;
        } else
        {
            nTemp = nNext;
            return nTemp;
        }
    }

    public boolean isValid()
    {
        return m_strName != null && m_strName.length() > 0;
    }

    public boolean isNull()
    {
        return m_strValue == null || m_strValue.length() <= 0;
    }

    public String toString()
    {
        String string = "";
        string = string + (m_strName != null ? m_strName : "");
        string = string + "=";
        string = string + (m_strValue != null ? "\"" + m_strValue + "\"" : "\"\"");
        return string;
    }

    public String getString(String strDefault)
    {
        if(m_strValue == null || m_strValue.length() <= 0)
            return strDefault;
        else
            return m_strValue;
    }

    public byte getByte(byte bDefault)
    {
        if(m_strValue == null || m_strValue.length() <= 0)
            return bDefault;
        else
            return Byte.parseByte(m_strValue);
    }

    public int getInteger(int nDefault)
    {
        if(m_strValue == null || m_strValue.length() <= 0)
            return nDefault;
        else
            return Integer.parseInt(m_strValue);
    }

    public long getLong(long lDefault)
    {
        if(m_strValue == null || m_strValue.length() <= 0)
            return lDefault;
        else
            return Long.parseLong(m_strValue);
    }

    public SimpleTime getDate(SimpleTime dateDefault)
    {
        if(m_strValue == null || m_strValue.length() <= 0)
            return dateDefault;
        else
            return new SimpleTime(m_strValue);
    }

    public static void main(String args[])
    {
        try
        {
            String strAttribute = "date=\"2001-10-12 10:23:08\"";
            XMLAttribute attribute = new XMLAttribute();
            int nStart = 0;
            int nNext = attribute.parse(strAttribute, nStart);
            if(nNext > nStart)
                System.out.println(attribute.toString());
            else
                System.out.println("XMLAttribute.main : invalid attribute !");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("XMLAttribute.main : unexpected exit !");
            
        }
    }


}
