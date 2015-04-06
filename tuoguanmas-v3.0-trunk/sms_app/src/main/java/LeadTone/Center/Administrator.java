package LeadTone.Center;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Utility;
import LeadTone.Mail.SendMail;
import java.util.StringTokenizer;

/**
 * ϵͳ�����ܸ������ܵĻ���������
 */
public class Administrator extends Engine
{
    /**
     * ����ϵͳԶ�̹����ܵĵ�½�˿�
     */
    public int m_nPort;
    /**
     * ����ϵͳԶ�̹����ܵĵ�½�ʺ�
     */
    public String m_strAccount;
    /**
     * ����ϵͳԶ�̹����ܵĵ�½����
     */
    public String m_strPassword;
    /**
     * �ʼ�������SMTP��ַ
     */
    public String m_strSMTPHost;
    /**
     * �ʼ�������SMTP�˿�
     */
    public int m_nSMTPPort;
    /**
     * �ʼ��������Ƿ���Ҫ��Ȩ
     */
    public boolean need_auth;
    /**
     * �ʼ����ѹ����Ƿ���
     */
    public boolean mail_switch;
    /**
     * �������ʼ���ַ
     */
    public String m_strFrom;
    /**
     * �������ʼ���ַ
     */
    public String m_strTo;
    /**
     *  ���ڷ��͵������ʺ�
     */
    public String m_strMailAccount;
    /**
     * ���ڷ��͵���������
     */
    public String m_strMailPassword;
    /**
     * ϵͳ����Ա�ֻ�����
     */
    public String m_strMobile;
    /**
     * ϵͳ��־״̬
     */
    public String m_strLog;
    /**
     * ��׺
     */
    public String m_strSuffix;

    /**
     * ���췽����ʼ�������
     */
    public Administrator()
    {
        super("Administrator");
        m_nPort = 9000;
        m_strAccount = null;
        m_strPassword = null;
        m_strSMTPHost = "corp.leadtone.com";
        m_nSMTPPort = 25;
        need_auth = true;
        mail_switch = false;
        m_strFrom = "sunlei@corp.leadtone.com";
        m_strTo = "sunlei@corp.leadtone.com";
        m_strMailAccount = null;
        m_strMailPassword = null;
        m_strMobile = "13701299715";
        m_strLog = "LOG_ALL";
        m_strSuffix = "";
    }

    /**
     * ʹ��Java Mailʵ���ʼ����͹��ܣ����ڱ�����ϵͳ״̬��ʾ
     * @param strSubject �ʼ�����
     * @param strContent �ʼ�����
     * @return �����ʼ������Ƿ�ɹ��Ĳ���ֵ
     */
    public boolean sendMail(String strSubject, String strContent)
    {
        if(!mail_switch)
            return true;
        try
        {
        	strContent = strContent + Utility.getHostAddressAndHostName();
            SendMail themail = new SendMail(m_strSMTPHost);
            if(need_auth)
            {
                themail.setNeedAuth(true);
                themail.setNamePass(m_strMailAccount, m_strMailPassword);
            }
            if(!themail.setSubject(strSubject))
                return false;
            if(!themail.setBody(strContent))
                return false;
            if(!themail.setFrom(m_strFrom))
                return false;
            String reciever = "";
            boolean is_success = false;
            for(StringTokenizer st = new StringTokenizer(m_strTo, ";"); st.hasMoreTokens();)
            {
                reciever = st.nextToken();
                if(!themail.setTo(reciever) || !themail.sendout())
                {
                    Log.log("Administrator.sendMail : send to " + reciever + " fail !", 1L);

                } else
                {
                    is_success = true;
                }
            }

            return is_success;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000001L);
            Log.log(e);
        }
        Log.log("Administrator.sendMail : unexpected exit !", 0x2000000000000001L);
        return false;
    }


}