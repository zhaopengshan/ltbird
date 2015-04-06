package LeadTone.Center;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Utility;
import LeadTone.Mail.SendMail;
import java.util.StringTokenizer;

/**
 * 系统管理功能附属功能的基本配置项
 */
public class Administrator extends Engine
{
    /**
     * 用于系统远程管理功能的登陆端口
     */
    public int m_nPort;
    /**
     * 用于系统远程管理功能的登陆帐号
     */
    public String m_strAccount;
    /**
     * 用于系统远程管理功能的登陆密码
     */
    public String m_strPassword;
    /**
     * 邮件服务器SMTP地址
     */
    public String m_strSMTPHost;
    /**
     * 邮件服务器SMTP端口
     */
    public int m_nSMTPPort;
    /**
     * 邮件服务器是否需要鉴权
     */
    public boolean need_auth;
    /**
     * 邮件提醒功能是否开启
     */
    public boolean mail_switch;
    /**
     * 发送者邮件地址
     */
    public String m_strFrom;
    /**
     * 接收者邮件地址
     */
    public String m_strTo;
    /**
     *  用于发送的邮箱帐号
     */
    public String m_strMailAccount;
    /**
     * 用于发送的邮箱密码
     */
    public String m_strMailPassword;
    /**
     * 系统管理员手机号码
     */
    public String m_strMobile;
    /**
     * 系统日志状态
     */
    public String m_strLog;
    /**
     * 后缀
     */
    public String m_strSuffix;

    /**
     * 构造方法初始化类变量
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
     * 使用Java Mail实现邮件发送功能，用于报警等系统状态提示
     * @param strSubject 邮件主题
     * @param strContent 邮件内容
     * @return 返回邮件发送是否成功的布尔值
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