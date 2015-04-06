package LeadTone.Mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import LeadTone.Log;

/**
 * Java Mail的标准实现
 */
public class SendMail
{
    private MimeMessage mimeMsg;
    private Session session;
    private Properties props;
    private boolean needAuth;
    private String username;
    private String password;
    private Multipart mp;
    public String status;

    public SendMail(String smtp)
    {
        needAuth = false;
        username = "";
        password = "";
        status = "发送邮件成功";  //UTF-8表示 \u53D1\u9001\u90AE\u4EF6\u6210\u529F
        setSmtpHost(smtp);
        createMimeMessage();
    }

    public void setSmtpHost(String hostName)
    {
        if(props == null)
            props = System.getProperties();
        props.put("mail.smtp.host", hostName);
    }

    public boolean createMimeMessage()
    {
        try
        {
            session = Session.getDefaultInstance(props, null);
        }
        catch(Exception e)
        {
            return false;
        }
        try
        {
            mimeMsg = new MimeMessage(session);
            mp = new MimeMultipart();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public void setNeedAuth(boolean need)
    {
        if(props == null)
            props = System.getProperties();
        if(need)
            props.put("mail.smtp.auth", "true");
        else
            props.put("mail.smtp.auth", "false");
    }

    public void setNamePass(String name, String pass)
    {
        username = name;
        password = pass;
    }

    public boolean setSubject(String mailSubject)
    {
        try
        {
            mimeMsg.setSubject(mailSubject);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean setBody(String mailBody)
    {
        try
        {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Content-Type content=text/html charset=gb2312>" + mailBody, "text/html;charset=GB2312");
            mp.addBodyPart(bp);
            return true;
        }
        catch(Exception e)
        {
            System.err.println("设置邮件正文时发生错误！" + e); //UTF-8表示 \u8BBE\u7F6E\u90AE\u4EF6\u6B63\u6587\u65F6\u53D1\u751F\u9519\u8BEF\uFF01
        }
        return false;
    }

    public boolean addFileAffix(String filename)
    {
        try
        {
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filename);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(fileds.getName());
            mp.addBodyPart(bp);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean setFrom(String from)
    {
        try
        {
            mimeMsg.setFrom(new InternetAddress(from));
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean setTo(String to)
    {
        if(to == null)
            return false;
        try
        {
            mimeMsg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to));
            return true;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000001L);
            Log.log(e);
        }
        return false;
    }

    public boolean setCopyTo(String copyto)
    {
        if(copyto == null)
            return false;
        try
        {
            mimeMsg.setRecipients(javax.mail.Message.RecipientType.CC, (javax.mail.Address[])InternetAddress.parse(copyto));
            return true;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000001L);
            Log.log(e);
        }
        return false;
    }

    public boolean sendout()
    {
        try
        {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            System.out.println("正在发送邮件...."); //UTF-8表示 \u6B63\u5728\u53D1\u9001\u90AE\u4EF6....
            Session mailSession = Session.getInstance(props, null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String)props.get("mail.smtp.host"), username, password);
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(javax.mail.Message.RecipientType.TO));
            transport.close();
            return true;
        }
        catch(Exception e)
        {
            Log.log("邮件发送失败:" + e.getMessage(), 0x2000000000000001L); //UTF-8表示 \u90AE\u4EF6\u53D1\u9001\u5931\u8D25:
            Log.log(e);
            return false;
        }
    }

    public static void main(String args[])
    {
        String mailbody = "<html>\r\n<body>\r\n<h3 align=center>测试</h3>\r\n</body></html>\r\n";
        SendMail themail = new SendMail("mail.leadtone.com");
        themail.setNeedAuth(true);
        if(!themail.setSubject("标题")) //UTF-8表示 \u6807\u9898
            return;
        if(!themail.setBody(mailbody))
            return;
        if(!themail.setTo("sunlei@leadtone.com"))
            return;
        if(!themail.setFrom("sunlei@leadtone.com"))
            return;
        themail.setNamePass("sunlei@leadtone.com", "leadtone");
        if(!themail.sendout())
            return;
        else
            return;
    }


}
