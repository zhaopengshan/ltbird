package LeadTone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import LeadTone.Packet.CMPPPacket.CMPPDeliver;



/**
 * �����࣬Ϊʵ��һЩ�ַ���֡�ʱ��ת���ȡ��ַ�ת��������ת���ȹ����ṩ����
 */
public class CopyOfUtility
{

    public CopyOfUtility()
    {
    }

    /**
     * �ж��Ƿ�Ϊ��������������ͨ����1�������ʵ��
     * @param n ���жϵ�ֵ
     * @return  �����Ƿ�Ϊ����
     */
    public static boolean isOdd(int n)
    {
        return (n & 0x1) == 1;
    }
    /**
     * �ж��Ƿ�Ϊ˫����ż������ͨ����1�������ʵ��
     * @param n ���жϵ�ֵ
     * @return  �����Ƿ�Ϊż��
     */
    public static boolean isEven(int n)
    {
        return (n & 0x1) == 0;
    }

    /**
     * ����ַ���
     * @param str ������ַ���
     * @param separater �ָ��
     * @return  ����ö�����͵Ĳ�ֺ�Ĵ�
     */
    public static Enumeration splitSeparater(String str, String separater)
    {
        String tempStr = str.trim();
        Vector container = new Vector();
        StringTokenizer st = new StringTokenizer(tempStr, separater);
        String temp = "";
        while(st.hasMoreElements()) 
        {
            temp = st.nextToken();
            temp = temp.trim();
            if(!temp.equalsIgnoreCase(""))
                container.add(temp);
        }
        return container.elements();
    }


    public static String get_time_stamp(int time_stamp)
    {
        int nMonth = time_stamp / 100000000;
        time_stamp %= 100000000;
        int nDay = time_stamp / 1000000;
        time_stamp %= 1000000;
        int nHour = time_stamp / 10000;
        time_stamp %= 10000;
        int nMinute = time_stamp / 100;
        int nSecond = time_stamp % 100;
        String strTemp = "";
        strTemp = strTemp + (nMonth >= 10 ? "" : "0") + nMonth;
        strTemp = strTemp + (nDay >= 10 ? "" : "0") + nDay;
        strTemp = strTemp + (nHour >= 10 ? "" : "0") + nHour;
        strTemp = strTemp + (nMinute >= 10 ? "" : "0") + nMinute;
        strTemp = strTemp + (nSecond >= 10 ? "" : "0") + nSecond;
        return new String(strTemp);
    }

    public static int get_time_stamp(String strTime)
    {
        int time_stamp = 0;
        time_stamp += Integer.parseInt(strTime.substring(4, 6));
        time_stamp *= 100;
        time_stamp += Integer.parseInt(strTime.substring(6, 8));
        time_stamp *= 100;
        time_stamp += Integer.parseInt(strTime.substring(8, 10));
        time_stamp *= 100;
        time_stamp += Integer.parseInt(strTime.substring(10, 12));
        time_stamp *= 100;
        time_stamp += Integer.parseInt(strTime.substring(12, 14));
        return time_stamp;
    }

    public static String toDayString(LeadToneDate date)
    {
        String strDay = "";
        strDay = strDay + date.getYear();
        strDay = strDay + (date.getMonth() + 1 >= 10 ? "" : "0") + (date.getMonth() + 1);
        strDay = strDay + (date.getDate() >= 10 ? "" : "0") + date.getDate();
        return new String(strDay);
    }

    public static String toTimeString(LeadToneDate date)
    {
        String strTime = "";
        strTime = strTime + date.getYear();
        strTime = strTime + (date.getMonth() + 1 >= 10 ? "" : "0") + (date.getMonth() + 1);
        strTime = strTime + (date.getDate() >= 10 ? "" : "0") + date.getDate();
        strTime = strTime + (date.getHours() >= 10 ? "" : "0") + date.getHours();
        strTime = strTime + (date.getMinutes() >= 10 ? "" : "0") + date.getMinutes();
        strTime = strTime + (date.getSeconds() >= 10 ? "" : "0") + date.getSeconds();
        return new String(strTime);
    }

    public static String toTimeString1(LeadToneDate date)
    {
        String strTime = "";
        strTime = strTime + (date.getMonth() + 1 >= 10 ? "" : "0") + (date.getMonth() + 1);
        strTime = strTime + (date.getDate() >= 10 ? "" : "0") + date.getDate();
        strTime = strTime + (date.getHours() >= 10 ? "" : "0") + date.getHours();
        strTime = strTime + (date.getMinutes() >= 10 ? "" : "0") + date.getMinutes();
        strTime = strTime + (date.getSeconds() >= 10 ? "" : "0") + date.getSeconds();
        return new String(strTime);
    }

    public static String toLocaleTimeString(LeadToneDate date)
    {
        String strTime = "";
        strTime = strTime + date.getYear() + "-";
        strTime = strTime + (date.getMonth() + 1 >= 10 ? "" : "0") + (date.getMonth() + 1) + "-";
        strTime = strTime + (date.getDate() >= 10 ? "" : "0") + date.getDate() + " ";
        strTime = strTime + (date.getHours() >= 10 ? "" : "0") + date.getHours() + ":";
        strTime = strTime + (date.getMinutes() >= 10 ? "" : "0") + date.getMinutes() + ":";
        strTime = strTime + (date.getSeconds() >= 10 ? "" : "0") + date.getSeconds();
        return new String(strTime);
    }

    /**
     * ���ֽ�ת��Ϊ16���Ʊ�ʾ���ַ���
     * @param bValue ��ת���ֽ�
     * @return  ת������ַ���
     */
    public static String toHexString(byte bValue)
    {
        String strHex = "";
        strHex = strHex + Character.forDigit(bValue >> 4 & 0xf, 16);
        strHex = strHex + Character.forDigit(bValue & 0xf, 16);
        return new String(strHex);
    }

    public static String toHexString(short sValue)
    {
        String strTemp = "";
        for(int i = 0; i < 4; i++)
        {
            strTemp = strTemp + Character.forDigit(sValue & 0xf, 16);
            sValue >>= 4;
        }

        String strHex = "";
        for(int j = 4; j > 0; j--)
            strHex = strHex + strTemp.charAt(j - 1);

        return new String(strHex);
    }

    public static String toHexString(int nValue)
    {
        String strTemp = "";
        for(int i = 0; i < 8; i++)
        {
            strTemp = strTemp + Character.forDigit(nValue & 0xf, 16);
            nValue >>= 4;
        }

        String strHex = "";
        for(int j = 8; j > 0; j--)
            strHex = strHex + strTemp.charAt(j - 1);

        return new String(strHex);
    }

    public static String toHexString(long lValue)
    {
        String strHex = toHexString((int)(lValue >> 32 & -1L)) + toHexString((int)(lValue & -1L));
        return new String(strHex);
    }

    public static String toHexString(byte bBytes[])
    {
        String strBytes = "";
        if(bBytes == null)
            return null;
        for(int i = 0; i < bBytes.length; i++)
            strBytes = strBytes + toHexString(bBytes[i]);

        return new String(strBytes);
    }

    public static String toHexString(byte bBytes[], int nLength)
    {
        String strBytes = "";
        if(bBytes == null)
            return null;
        for(int i = 0; i < nLength && i < bBytes.length; i++)
            strBytes = strBytes + toHexString(bBytes[i]);

        return new String(strBytes);
    }

    /**
     * ��16���Ʊ�ʾ���ַ���ת��Ϊ�ֽ�����
     * @param strHex ��ת����16���Ʊ�ʾ���ַ���
     * @return  ת������ֽ�����
     */
    public static String toHexString(byte bBytes[], int beginIndex, int endIndex)
    {
        String strBytes = "";
        if(bBytes == null)
            return null;
        if(beginIndex < 0)
            beginIndex = 0;
        if(endIndex > bBytes.length)
            endIndex = bBytes.length;
        for(int i = beginIndex; i < endIndex; i++)
            strBytes = strBytes + toHexString(bBytes[i]);

        return new String(strBytes);
    }

    public static byte[] toBytesValue(String strHex)
    {
        int nLength = strHex.length() + 1 >> 1;
        byte bBytes[] = new byte[nLength];
        for(int i = 0; i < strHex.length(); i++)
        {
            byte bTemp = (byte)Character.digit(strHex.charAt(i), 16);
            bBytes[i >> 1] |= bTemp << (isOdd(i) ? 0 : 4);
        }

        return bBytes;
    }

    public static byte toHexByte(String strHex)
    {
        if(strHex == null || strHex.length() <= 0)
            return 0;
        byte bTemp = 0;
        for(int i = 0; i < strHex.length(); i++)
        {
            bTemp <<= 4;
            bTemp |= (byte)(Character.digit(strHex.charAt(i), 16) & 0xf);
        }

        return bTemp;
    }

    public static short toHexShort(String strHex)
    {
        if(strHex == null || strHex.length() <= 0)
            return 0;
        short sTemp = 0;
        for(int i = 0; i < strHex.length(); i++)
        {
            sTemp <<= 4;
            sTemp |= (byte)(Character.digit(strHex.charAt(i), 16) & 0xf);
        }

        return sTemp;
    }

    public static int toHexInteger(String strHex)
    {
        if(strHex == null || strHex.length() <= 0)
            return 0;
        int nTemp = 0;
        for(int i = 0; i < strHex.length(); i++)
        {
            nTemp <<= 4;
            nTemp |= (byte)(Character.digit(strHex.charAt(i), 16) & 0xf);
        }

        return nTemp;
    }

    public static long toHexLong(String strHex)
    {
        if(strHex == null || strHex.length() <= 0)
            return 0L;
        long lTemp = 0L;
        for(int i = 0; i < strHex.length(); i++)
        {
            lTemp <<= 4;
            lTemp |= (byte)(Character.digit(strHex.charAt(i), 16) & 0xf);
        }

        return lTemp;
    }

    /**
     * ����������ת��Ϊ�ֽ�
     * @param strDigit ����������
     * @return ת������ֽ�
     */
    public static byte toDigitByte(String strDigit)
    {
        byte bTemp = 0;
        for(int i = 0; i < strDigit.length(); i++)
        {
            bTemp *= 10;
            bTemp += Character.digit(strDigit.charAt(i), 10);
        }

        return bTemp;
    }

    public static short toDigitShort(String strDigit)
    {
        short sTemp = 0;
        for(int i = 0; i < strDigit.length(); i++)
        {
            sTemp *= 10;
            sTemp += Character.digit(strDigit.charAt(i), 10);
        }

        return sTemp;
    }

    public static int toDigitInteger(String strDigit)
    {
        int nTemp = 0;
        for(int i = 0; i < strDigit.length(); i++)
        {
            nTemp *= 10;
            nTemp += Character.digit(strDigit.charAt(i), 10);
        }

        return nTemp;
    }

    public static long toDigitLong(String strDigit)
    {
        long lTemp = 0L;
        for(int i = 0; i < strDigit.length(); i++)
        {
            lTemp *= 10L;
            lTemp += Character.digit(strDigit.charAt(i), 10);
        }

        return lTemp;
    }

    public static String get_msg_content(byte msg_content[])
    {
        if(msg_content != null)
            return new String(msg_content);
        else
            return null;
    }

    /**
     * ��GB2312����ת��ΪUCS2����
     * @param msg_content
     * @return ����ת������ֽ�����
     */
    public static byte[] gb2312_to_ucs2(String msg_content)
    {
        if(msg_content != null && msg_content.length() > 0)
        {
            byte temp[] = new byte[2 * msg_content.length()];
            for(int i = 0; i < msg_content.length(); i++)
            {
                char c = msg_content.charAt(i);
                temp[2 * i] = (byte)(c >> 8 & 0xff);
                temp[2 * i + 1] = (byte)(c & 0xff);
            }

            return temp;
        } else
        {
            return null;
        }
    }

    /**
     * ��UCS2����ת��ΪGB2312����
     * @param msg_content
     * @return ����ת������ַ���
     */
    public static String ucs2_to_gb2312(byte msg_content[])
    {
        if(msg_content == null || msg_content.length <= 0)
            return null;
        int nLength = msg_content.length / 2;
        char chars[] = new char[nLength];
        for(int i = 0; i < nLength; i++)
        {
            chars[i] = '\0';
            chars[i] |= ((char)msg_content[2 * i] & 0xff) << 8;
            chars[i] |= (char)msg_content[2 * i + 1] & 0xff;
        }

        return new String(chars);
    }

    /**
     * �ַ������
     * @param strContent
     * @param strDelimiter
     * @return ������������ַ�������
     */
    public static String[] split(String strContent, String strDelimiter)
    {
        if(strContent == null || strDelimiter == null)
            return null;
        StringTokenizer st = new StringTokenizer(strContent, strDelimiter);
        int nCount = st.countTokens();
        String contents[] = new String[nCount];
        for(int i = 0; i < nCount && st.hasMoreTokens(); i++)
            contents[i] = st.nextToken().trim();

        return contents;
    }

    /**
     * ���ڷָ����ָ��
     * @param strCommand
     * @return �ָ����ַ�������
     */
    public static String[] parseCommand(String strCommand)
    {
        return split(strCommand, " ");
    }

    /**
     * ���ڷָ���ʼ���ַ
     * @param dest_terminal_id
     * @return �ָ����ַ�������
     */
    public static String[] parseTerminalID(String dest_terminal_id)
    {
        return split(dest_terminal_id, ";");
    }
    
    /**
     * ��ȡ����IP��������
     * @return ����IP�ͱ�������ɵ��ַ����������ʼ�����ʱ��¼�ʼ���Դ
     */
    public static String getHostAddressAndHostName(){ 
        String suffix = null;
    	try {
		InetAddress address = InetAddress.getLocalHost();
	    String ip = address.getHostAddress().toString();//��ñ���IP
        String hostname = address.getHostName().toString();//��ñ������� 
        suffix = "\nThe Server IP is " + ip + ",The Server HostName is " + hostname + ".";
	} catch (UnknownHostException e) {
		Log.log("Utility.getHostAddressAndHostName : can not get HostAddress and HostName !", 1L);
		Log.log(e);
	}
	   return suffix;
    }
    
    private static String CEF_HOME = null;
	private static int OS_TYPE = -1;
	private static String OS_NAME = null;
	public static final int WINDOWS = 0;
	public static final int LINUX = 1;
	public static final int SUNOS = 2;
	public static final int OTHER = 9;

	class OsType
	{
		public int value;
		public final OsType WINDOWS = new OsType(0);
		public final OsType LINUX = new OsType(1);
		public final OsType SUNOS = new OsType(2);
		public final OsType OTHER = new OsType(9);

		private OsType(int osType)
		{
			this.value = osType;
		}
	}

	public static int getOsType()
	{
		if (OS_TYPE != -1)
			return OS_TYPE;
		String os = getOsName();
		if (os.startsWith("linux"))
		{
			OS_TYPE = LINUX;
		}
		else if (os.startsWith("windows"))
		{
			OS_TYPE = WINDOWS;
		}
		else if (os.startsWith("sun_os"))
		{
			OS_TYPE = SUNOS;
		}
		else
			OS_TYPE = OTHER;
		return OS_TYPE;
	}

	/**
	 * Get the Operate System's name
	 */
	public static String getOsName()
	{
		if (OS_NAME != null)
			return OS_NAME;
		String[] os_name = System.getProperty("os.name").split(" ");
		OS_NAME = os_name[0].toLowerCase();
		return OS_NAME;
	}

	/**
	 * get the CEF install directory, throught the CEF_HOME environment
	 * 
	 * @return system environment variable CEF_HOME's value
	 */
	public static String getCefHome()
	{
		if (CEF_HOME == null)
			CEF_HOME = getEnvVariable("CEF_HOME");
		// System.out.println("CEF_HOME = " + CEF_HOME);
		return CEF_HOME;
	}

	/**
	 * get the special environment variable's value
	 * 
	 * @param key
	 * @return
	 */
	public static String getEnvVariable(String key)
	{
		String command = null;
		switch (getOsType())
		{
			case WINDOWS:
				command = "cmd /c set";
				break;
			case SUNOS:
			case LINUX:
				command = "env";
		}
		Properties prop = new Properties();
		try
		{
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.startsWith("CEF_HOME"))
				{
					return line.substring(line.indexOf("=") + 1);
				}
			}
		}
		catch (IOException e)
		{
			System.err.println("IOException Message: " + e.getMessage());
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
	public static byte[] reencode_msg_content(byte[] msg_content)
    {
        String content = CopyOfUtility.ucs2_to_gb2312(msg_content);
        if(content == null)
            return null;
        try
        {
            msg_content = content.getBytes("GB2312");
            return msg_content;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000004L);
            Log.log(e);
            Log.log("CMPPExchanger.reencode_msg_content : unexpected exit !", 0x2000000000000004L);
        }
        return null;
    }
	 
	public static void main(String[] args) throws UnsupportedEncodingException{
		String contentbyte = "676d30303520e5908ce8a18c";
		byte[] aa = toBytesValue(contentbyte);
		String a =CopyOfUtility.toHexString(aa);
		
		
		byte[] bytes =reencode_msg_content(aa);
		
		//String ab = Utility.toHexString(bytes);
		String content = CopyOfUtility.ucs2_to_gb2312(aa);
		 
		String strContent = new String(aa,"GBK");
       // logger.info("UTF16==="+strContent);
        strContent = strContent.substring(3);//ת��UTF16��GBKʱ�������ŵ�ռλ��3�����ֳ��ȼ�6���ַ���
       // smsBean.setMsgcontent(strContent);
		
		// System.out.println(ab);
	}

}
