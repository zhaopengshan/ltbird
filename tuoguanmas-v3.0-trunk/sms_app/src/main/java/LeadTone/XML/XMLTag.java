package LeadTone.XML;

import LeadTone.Log;
import LeadTone.SimpleTime;
import java.io.*;
import java.util.Vector;

/**
 * 用于读取配置文件config.xml，解析xml，装载配置参数
 * 整个XML包下类实现类似dom4j的功能
 */
public class XMLTag
{
    public String m_strName;
    public Vector m_attributes;
    public String m_strContent;
    public Vector m_tags;

    public XMLTag()
    {
        m_strName = null;
        m_attributes = new Vector();
        m_strContent = null;
        m_tags = new Vector();
    }

    public XMLTag(String strName)
    {
        m_strName = null;
        m_attributes = new Vector();
        m_strContent = null;
        m_tags = new Vector();
        m_strName = strName;
    }

    public void empty()
    {
        m_strName = null;
        for(int i = 0; i < m_attributes.size(); i++)
        {
            XMLAttribute attribute = (XMLAttribute)m_attributes.elementAt(i);
            attribute.empty();
        }

        m_attributes = null;
        m_strContent = null;
        for(int i = 0; i < m_tags.size(); i++)
        {
            XMLTag tag = (XMLTag)m_tags.elementAt(i);
            tag.empty();
        }

        m_tags = null;
    }

    public boolean isValid()
    {
        return m_strName != null && m_strName.length() > 0;
    }

    public boolean isEmpty()
    {
        if(m_tags != null && m_tags.size() > 0)
            return true;
        return m_strContent != null && m_strContent.length() > 0;
    }

    public boolean hasName(String strName)
    {
        if(m_strName == null || m_strName.length() <= 0)
            return false;
        if(strName == null || strName.length() <= 0)
            return false;
        else
            return m_strName.equals(strName);
    }

    public XMLAttribute getAttribute(String strName)
    {
        if(m_attributes == null)
            return null;
        if(strName == null || strName.length() <= 0)
            return null;
        for(int i = 0; i < m_attributes.size(); i++)
        {
            XMLAttribute attribute = (XMLAttribute)m_attributes.elementAt(i);
            if(attribute.m_strName != null && attribute.m_strName.equals(strName))
                return attribute;
        }

        return null;
    }

    public String getString(String strName, String strDefault)
    {
        if(strName == null || strName.length() <= 0)
            return getString(strDefault);
        XMLAttribute attribute = getAttribute(strName);
        if(attribute == null || attribute.isNull())
            return strDefault;
        else
            return attribute.getString(strDefault);
    }

    public byte getByte(String strName, byte bDefault)
    {
        if(strName == null || strName.length() <= 0)
            return getByte(bDefault);
        XMLAttribute attribute = getAttribute(strName);
        if(attribute == null)
            return bDefault;
        else
            return attribute.getByte(bDefault);
    }

    public int getInteger(String strName, int nDefault)
    {
        if(strName == null || strName.length() <= 0)
            return getInteger(nDefault);
        XMLAttribute attribute = getAttribute(strName);
        if(attribute == null)
            return nDefault;
        else
            return attribute.getInteger(nDefault);
    }

    public long getLong(String strName, long lDefault)
    {
        if(strName == null || strName.length() <= 0)
            return getLong(lDefault);
        XMLAttribute attribute = getAttribute(strName);
        if(attribute == null)
            return lDefault;
        else
            return attribute.getLong(lDefault);
    }

    public SimpleTime getDate(String strName, SimpleTime dateDefault)
    {
        if(strName == null || strName.length() <= 0)
            return getDate(dateDefault);
        XMLAttribute attribute = getAttribute(strName);
        if(attribute == null)
            return dateDefault;
        else
            return attribute.getDate(dateDefault);
    }

    public String getString(String strDefault)
    {
        if(m_strContent == null || m_strContent.length() <= 0)
            return strDefault;
        else
            return m_strContent;
    }

    public byte getByte(byte bDefault)
    {
        if(m_strContent == null || m_strContent.length() <= 0)
            return bDefault;
        else
            return Byte.parseByte(m_strContent);
    }

    public int getInteger(int nDefault)
    {
        if(m_strContent == null || m_strContent.length() <= 0)
            return nDefault;
        else
            return Integer.parseInt(m_strContent);
    }

    public long getLong(long lDefault)
    {
        if(m_strContent == null || m_strContent.length() <= 0)
            return lDefault;
        else
            return Long.parseLong(m_strContent);
    }

    public SimpleTime getDate(SimpleTime dateDefault)
    {
        if(m_strContent == null || m_strContent.length() <= 0)
            return dateDefault;
        else
            return new SimpleTime(m_strContent);
    }

    public XMLTag getTag(String strName)
    {
        if(m_tags == null || m_tags.size() <= 0)
            return null;
        if(strName == null || strName.length() <= 0)
            return null;
        for(int i = 0; i < m_tags.size(); i++)
        {
            XMLTag tag = (XMLTag)m_tags.elementAt(i);
            if(tag.hasName(strName))
                return tag;
        }

        return null;
    }

    public Vector getTags(String strName)
    {
        if(m_tags == null || m_tags.size() <= 0)
            return null;
        Vector tags = new Vector();
        for(int i = 0; i < m_tags.size(); i++)
        {
            XMLTag tag = (XMLTag)m_tags.elementAt(i);
            if(tag.hasName(strName))
                tags.add(tag);
        }

        return tags;
    }

    public void add(XMLAttribute attribute)
    {
        m_attributes.addElement(attribute);
    }

    public void add(XMLTag tag)
    {
        m_tags.addElement(tag);
    }

    public int hasAttribute(XMLAttribute attribute)
    {
        for(int i = 0; m_attributes != null && i < m_attributes.size(); i++)
        {
            XMLAttribute attrib = (XMLAttribute)m_attributes.elementAt(i);
            if(attrib.equals(attribute))
                return i;
        }

        return -1;
    }

    private String getName(String strContent, int nStart)
    {
        int nTemp = nStart;
        if(nTemp < strContent.length())
        {
            char c;
            do
                c = strContent.charAt(nTemp);
            while((Character.isLetter(c) || Character.isDigit(c) || c == ':' || c == '-' || c == '_') && ++nTemp < strContent.length());
            return strContent.substring(nStart, nTemp);
        } else
        {
            return null;
        }
    }

    private int parseAttribute(String strContent, int nStart)
        throws XMLException
    {
        int nTemp = nStart;
        do
        {
            XMLAttribute attribute = new XMLAttribute();
            int nNext = attribute.parse(strContent, nTemp);
            if(nNext <= nTemp)
                break;
            nTemp = nNext;
            add(attribute);
        } while(nTemp < strContent.length());
        return nTemp;
    }

    private int parseHead(String strContent, int nStart)
        throws XMLException
    {
        int nTemp = nStart;
        int nNext = 0;
        XMLParser.check(strContent, nTemp);
        if(strContent.charAt(nTemp) != '<')
            return nStart;
        nTemp++;
        XMLParser.check(strContent, nTemp);
        m_strName = getName(strContent, nTemp);
        if(m_strName == null || m_strName.length() <= 0)
            return nStart;
        if(m_strName.length() > 64)
            throw new XMLException("XMLTag.parseHead(" + nTemp + ") : name too long !");
        nTemp += m_strName.length();
        XMLParser.check(strContent, nTemp);
        nNext = parseAttribute(strContent, nTemp);
        if(nNext < nTemp)
            return nStart;
        nTemp = nNext;
        nTemp = XMLParser.jumpBlankspace(strContent, nTemp);
        XMLParser.check(strContent, nTemp);
        if(strContent.charAt(nTemp) != '>')
            return nStart;
        else
            return ++nTemp;
    }

    private int parseTail(String strContent, int nStart)
        throws XMLException
    {
        int nTemp = nStart;
        int nNext = 0;
        XMLParser.check(strContent, nTemp);
        if(strContent.charAt(nTemp) != '<')
            return nStart;
        nTemp++;
        XMLParser.check(strContent, nTemp);
        if(strContent.charAt(nTemp) != '/')
            return nStart;
        nTemp++;
        XMLParser.check(strContent, nTemp);
        String strName = getName(strContent, nTemp);
        if(strName == null || strName.length() <= 0)
            return nStart;
        nTemp += strName.length();
        if(!strName.equals(m_strName))
            return nStart;
        nTemp = XMLParser.jumpBlankspace(strContent, nTemp);
        XMLParser.check(strContent, nTemp);
        if(strContent.charAt(nTemp) != '>')
            return nStart;
        else
            return ++nTemp;
    }

    private String getContent(String strContent, int nStart)
    {
        int nTemp = nStart;
        boolean bEndFound = false;
        for(; nTemp < strContent.length(); nTemp++)
        {
            if(strContent.charAt(nTemp) != '<')
                continue;
            bEndFound = true;
            break;
        }

        if(bEndFound)
            return strContent.substring(nStart, nTemp);
        else
            return null;
    }

    public int parse(String strContent, int nStart)
        throws XMLException
    {
        int nTemp = nStart;
        int nNext = 0;
        nTemp = XMLParser.jumpBlankspace(strContent, nTemp);
        nNext = parseHead(strContent, nTemp);
        if(nNext <= nTemp)
            return nStart;
        nTemp = nNext;
        do
        {
            XMLTag tag = new XMLTag();
            nNext = tag.parse(strContent, nTemp);
            if(nNext <= nTemp)
            {
                tag.empty();
                tag = null;
                XMLParser.check(strContent, nTemp);
                m_strContent = getContent(strContent, nTemp);
                if(m_strContent != null && m_strContent.length() > 0)
                {
                    nTemp += m_strContent.length();
                    m_strContent = XMLParser.trimBlankspace(m_strContent);
                }
                break;
            }
            nTemp = nNext;
            add(tag);
        } while(nTemp < strContent.length());
        nNext = parseTail(strContent, nTemp);
        if(nNext <= nTemp)
        {
            return nStart;
        } else
        {
            nTemp = nNext;
            return nTemp;
        }
    }

    public String toString()
    {
        String string = "";
        if(m_strName != null && m_strName.length() > 0)
            string = string + m_strName;
        if(m_strContent != null && m_strContent.length() > 0)
        {
            string = string + "=";
            string = string + '"';
            for(int i = 0; i < m_strContent.length(); i++)
                switch(m_strContent.charAt(i))
                {
                case 9:
                    string = string + "\\t";
                    break;

                case 10:
                    string = string + "\\n";
                    break;

                case 13:
                    string = string + "\\r";
                    break;

                case 32: 
                    string = string + "\u25A1";
                    break;

                default:
                    string = string + m_strContent.charAt(i);
                    break;
                }

            string = string + '"';
        }
        if(m_attributes != null)
        {
            for(int i = 0; i < m_attributes.size(); i++)
            {
                XMLAttribute attribute = (XMLAttribute)m_attributes.elementAt(i);
                string = string + " ";
                string = string + attribute.toString();
            }

        }
        return string;
    }

    public String fileFormat(int n)
    {
        String strFile = "";
        strFile = strFile + "<";
        if(m_strName != null && m_strName.length() > 0)
            strFile = strFile + m_strName;
        if(m_attributes != null)
        {
            for(int i = 0; i < m_attributes.size(); i++)
            {
                XMLAttribute attribute = (XMLAttribute)m_attributes.elementAt(i);
                strFile = strFile + " ";
                strFile = strFile + attribute.toString();
            }

        }
        strFile = strFile + ">";
        if(m_strContent != null && m_strContent.length() > 0)
            strFile = strFile + m_strContent;
        if(m_tags != null && m_tags.size() > 0)
        {
            for(int i = 0; i < m_tags.size(); i++)
            {
                XMLTag tag = (XMLTag)m_tags.elementAt(i);
                strFile = strFile + "\r\n";
                for(int j = 0; j <= n; j++)
                    strFile = strFile + "\t";

                n++;
                strFile = strFile + tag.fileFormat(n);
                n--;
            }

            strFile = strFile + "\r\n";
            for(int j = 0; j < n; j++)
                strFile = strFile + "\t";

        }
        strFile = strFile + "</";
        if(m_strName != null && m_strName.length() > 0)
            strFile = strFile + m_strName;
        strFile = strFile + ">";
        return strFile;
    }

    public boolean saveFile(File file)
    {
        try
        {
            int n = 0;
            String strFile = fileFormat(n);
            if(strFile == null || strFile.length() <= 0)
            {
                return false;
            } else
            {
                FileOutputStream fos = new FileOutputStream(file);
                PrintStream ps = new PrintStream(fos);
                ps.print(strFile);
                ps.close();
                fos.close();
                return true;
            }
        }
        catch(Exception e)
        {
            System.out.println("XMLTag.saveFile : " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("XMLTag.saveFile : unexpected exit !");
        return false;
    }

    public void print(boolean array[], int n)
    {
        System.out.println(toString());
        if(m_tags != null)
        {
            for(int i = 0; i < m_tags.size(); i++)
            {
                XMLTag tag = (XMLTag)m_tags.elementAt(i);
                for(int j = 0; j <= n; j++)
                    if(array[j])
                        System.out.print("│");
                    else
                        System.out.print("  ");

                System.out.print(i >= m_tags.size() - 1 ? "└" : "├");
                n++;
                array[n] = i < m_tags.size() - 1;
                tag.print(array, n);
                n--;
            }

        }
    }
}
