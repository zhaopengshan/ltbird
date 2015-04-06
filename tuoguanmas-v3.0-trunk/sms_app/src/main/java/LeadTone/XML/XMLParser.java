package LeadTone.XML;

import LeadTone.SimpleTime;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 用于读取配置文件config.xml，解析xml，装载配置参数
 * 整个XML包下类实现类似dom4j的功能
 */
public class XMLParser
{
    public static final int MAX_LEVEL = 32;
    public static final int MAX_NAME_LENGTH = 64;

    public XMLParser()
    {
    }

    public static XMLTag parse(String strContent)
        throws XMLException
    {
        XMLTag root = new XMLTag();
        int nStart = 0;
        int nNext = root.parse(strContent, nStart);
        if(nNext <= nStart)
            return null;
        else
            return root;
    }

    public static boolean saveFile(XMLTag root, File file)
    {
        return root.saveFile(file);
    }

    public static boolean saveFile(String strXML, File file)
    {
        try
        {
            XMLTag root = parse(strXML);
            return saveFile(root, file);
        }
        catch(Exception e)
        {
            System.out.println("XMLParser.saveFile : " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("XMLParser.saveFile : unexpected exit !");
        return false;
    }

    public static void print(XMLTag root)
    {
        if(root != null)
        {
            boolean array[] = new boolean[32];
            int n = -1;
            root.print(array, n);
        }
    }

    public static String[] split(String strWholePath)
    {
        String args[] = new String[2];
        args[0] = null;
        args[1] = null;
        if(strWholePath == null || strWholePath.length() <= 0)
            return args;
        int nIndex = strWholePath.indexOf(".");
        if(nIndex < 0)
        {
            args[0] = strWholePath;
        } else
        {
            args[0] = strWholePath.substring(0, nIndex);
            args[1] = strWholePath.substring(nIndex + 1, strWholePath.length());
        }
        return args;
    }

    public static XMLTag getTag(XMLTag root, String strPath)
    {
        if(root == null)
            return null;
        if(strPath == null || strPath.length() <= 0)
            return root;
        StringTokenizer st = new StringTokenizer(strPath, "\\");
        XMLTag tag;
        String strName;
        for(tag = root; st.hasMoreTokens() && tag != null; tag = tag.getTag(strName))
            strName = st.nextToken();

        return tag;
    }

    public static String getString(XMLTag root, String strWholePath, String strDefault)
    {
        String args[] = split(strWholePath);
        XMLTag tag = getTag(root, args[0]);
        if(tag == null)
            return strDefault;
        else
            return tag.getString(args[1], strDefault);
    }

    public static byte getByte(XMLTag root, String strWholePath, byte bDefault)
    {
        String args[] = split(strWholePath);
        XMLTag tag = getTag(root, args[0]);
        if(tag == null)
            return bDefault;
        else
            return tag.getByte(args[1], bDefault);
    }

    public static int getInteger(XMLTag root, String strWholePath, int nDefault)
    {
        String args[] = split(strWholePath);
        XMLTag tag = getTag(root, args[0]);
        if(tag == null)
            return nDefault;
        else
            return tag.getInteger(args[1], nDefault);
    }

    public static long getLong(XMLTag root, String strWholePath, long lDefault)
    {
        String args[] = split(strWholePath);
        XMLTag tag = getTag(root, args[0]);
        if(tag == null)
            return lDefault;
        else
            return tag.getLong(args[1], lDefault);
    }

    public static SimpleTime getDate(XMLTag root, String strWholePath, SimpleTime dateDefault)
    {
        String args[] = split(strWholePath);
        XMLTag tag = getTag(root, args[0]);
        if(tag == null)
            return dateDefault;
        else
            return tag.getDate(args[1], dateDefault);
    }

    public static XMLTag getTag(XMLTag root, String strName, XMLAttribute attribute)
    {
        if(root == null)
            return null;
        if(root.m_tags == null || root.m_tags.size() <= 0)
            return null;
        for(int i = 0; i < root.m_tags.size(); i++)
        {
            XMLTag tag = (XMLTag)root.m_tags.elementAt(i);
            if(tag.hasName(strName) && tag.hasAttribute(attribute) >= 0)
                return tag;
        }

        return null;
    }

    public static Vector getTags(XMLTag root, String strName, XMLAttribute attribute)
    {
        if(root == null)
            return null;
        if(root.m_tags == null || root.m_tags.size() <= 0)
            return null;
        Vector tags = new Vector();
        for(int i = 0; i < root.m_tags.size(); i++)
        {
            XMLTag tag = (XMLTag)root.m_tags.elementAt(i);
            if(tag.hasName(strName) && tag.hasAttribute(attribute) >= 0)
                tags.add(tag);
        }

        return tags;
    }

    public static Vector getTags(XMLTag root, String strName)
    {
        if(root == null)
            return null;
        else
            return root.getTags(strName);
    }

    public static void check(String strContent, int nOffset)
        throws XMLException
    {
        if(strContent == null || strContent.length() <= 0)
            throw new XMLException("XMLParser.check : null content !");
        if(nOffset < 0 || nOffset >= strContent.length())
            throw new XMLException("XMLParser.check : out of range !");
        else
            return;
    }

    public static int jumpBlankspace(String strContent, int nStart)
    {
        int nTemp = nStart;
        char c;
        if(nTemp < strContent.length())
            do
                c = strContent.charAt(nTemp);
            while((c == ' ' || c == '\t' || c == '\r' || c == '\n') && ++nTemp < strContent.length());
        return nTemp;
    }

    public static String trimBlankspace(String strContent)
    {
        if(strContent == null || strContent.length() <= 0)
            return null;
        int nTemp;
        for(nTemp = 0; nTemp < strContent.length(); nTemp++)
        {
            char c = strContent.charAt(nTemp);
            if(c != ' ' && c != '\t' && c != '\r' && c != '\n')
                break;
        }

        int nStart = nTemp;
        for(nTemp = strContent.length(); nTemp > 0; nTemp--)
        {
            char c = strContent.charAt(nTemp - 1);
            if(c != ' ' && c != '\t' && c != '\r' && c != '\n')
                break;
        }

        int nEnd = nTemp;
        if(nEnd <= nStart)
            return null;
        else
            return strContent.substring(nStart, nEnd);
    }

    public static XMLTag loadFile(File file)
    {
        try
        {
            String strContent = "";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            for(String strLine = null; (strLine = br.readLine()) != null;)
                strContent = strContent + strLine;

            br.close();
            fr.close();
            XMLTag root = parse(strContent);
            return root;
        }
        catch(Exception e)
        {
            System.out.println("XMLParser.main : " + e.getMessage());
        }
        System.out.println("XMLParser.main : unexpected exit !");
        return null;
    }

    public static void main(String args[])
    {
        try
        {
            if(args.length != 1)
            {
                System.out.println("Usage : java XMLParser [filename]");
                return;
            }
            String strContent = "";
            FileReader fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr);
            for(String strLine = null; (strLine = br.readLine()) != null;)
                strContent = strContent + strLine;

            br.close();
            fr.close();
            XMLTag root = parse(strContent);
            if(root != null)
                print(root);
            else
                System.out.println("XMLParser.main : invalid xml !");
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.out.println("XMLParser.main : unexpected exit !");
        }
    }



}
