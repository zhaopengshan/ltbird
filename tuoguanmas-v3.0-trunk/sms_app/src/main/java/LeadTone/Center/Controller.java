package LeadTone.Center;

import LeadTone.Engine;
import LeadTone.Gateway.ServiceProvider;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPConnect;
import LeadTone.Utility;
import LeadTone.LeadToneLogic.BackupTableConfig;
import LeadTone.LeadToneLogic.LeadToneLogic;
import LeadTone.LeadToneLogic.LeadToneLogicConfig;
import LeadTone.LeadToneLogic.UpdateFinalResultConfig;
import LeadTone.LeadToneLogic.MO.SmsServiceMO;

import java.io.*;
import java.net.Socket;

/**
 * 对系统外部提供远程管理功能，
 * 开启一管理端口，供外部Telnet登陆上来，查看系统工作状况，修改系统工作参数
 */
public class Controller extends Engine
{
    /**
     * 管理端口输入超时时限
     */
    static final int DEFAULT_INPUT_TIMEOUT = 60000;
    /**
     * 用于鉴权的Key，用于系统产品化时对外保护产权
     */
    static byte m_key[] = null;
    /**
     * 管理端口号
     */
    Socket m_socket;
    /**
     * 主系统对象
     */
    Center m_center;
    /**
     * 建立于管理端口上的输入流
     */
    InputStreamReader m_isr;
    /**
     * 建立于输入流上的字符级输入流
     */
    BufferedReader m_br;
    /**
     * 用于打印输出的输出流
     */
    PrintStream m_ps;

    /**
     * 构造方法初始化类变量
     * @param socket
     * @param center
     * @throws IOException
     */
    public Controller(Socket socket, Center center)
        throws IOException
    {
        super("Controller");
        m_socket = null;
        m_center = null;
        m_isr = null;
        m_br = null;
        m_ps = null;
        m_socket = socket;
        m_socket.setSoTimeout(60000);
        m_center = center;
        m_isr = new InputStreamReader(m_socket.getInputStream());
        m_br = new BufferedReader(m_isr);
        m_ps = new PrintStream(m_socket.getOutputStream());
    }

    /**
     * 清空使用的资源
     */
    public void empty()
    {
        m_socket = null;
        m_ps = null;
        m_br = null;
        m_isr = null;
    }

    /**
     * 关闭开启的流、端口等资源
     */
    public void close()
    {
        try
        {
            m_ps.close();
            m_br.close();
            m_isr.close();
            m_socket.close();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("Controller.close : unexpected exit !", 0x2000000000000001L);
        }
    }

    /**
     * 管理端口登陆时的用户鉴权
     * @param br
     * @param ps
     * @return 返回用户鉴权的结果
     * @throws IOException
     */
    public boolean checkAccount(BufferedReader br, PrintStream ps)
        throws IOException
    {
        if(m_center.m_admin.m_strAccount == null)
        {
            ps.print("> no account requested !\r\n");
            return true;
        }
        ps.print("> Please enter your account !\r\n");
        String strLine = br.readLine();
        if(strLine == null || strLine.length() <= 0)
        {
            ps.print("> empty account !\r\n");
            return false;
        }
        ps.print("> " + strLine + "\r\n");
        if(!m_center.m_admin.m_strAccount.equals(strLine.trim()))
        {
            ps.print("> invalid account !\r\n");
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * 管理端口登陆时的用户鉴权
     * @param br
     * @param ps
     * @return 返回用户鉴权的结果
     * @throws IOException
     */
    public boolean checkPassword(BufferedReader br, PrintStream ps)
        throws IOException
    {
        if(m_center.m_admin.m_strPassword == null)
        {
            ps.print("> no password requested !\r\n");
            return true;
        }
        ps.print("> Please enter your password !\r\n");
        String strLine = br.readLine();
        if(strLine == null || strLine.length() <= 0)
        {
            ps.print("> empty password !\r\n");
            return false;
        }
        if(!m_center.m_admin.m_strPassword.equals(strLine.trim()))
        {
            ps.print("> invalid password !\r\n");
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * 修改日志记录方式为记录全部日志
     * @param ps
     */
    public void logAll(PrintStream ps)
    {
        ps.print(">log all !\r\n");
        Log.setLog(-1L);
    }

    /**
     * 修改日志记录方式为不记录任何日志
     * @param ps
     */
    public void logNone(PrintStream ps)
    {
        ps.print(">log none !\r\n");
        Log.setLog(0L);
    }

    /**
     * 修改日志记录方式为只记录重要日志
     * @param ps
     */
    public void logNormally(PrintStream ps)
    {
        ps.print(">log normally !\r\n");
        Log.setLog(0xe000600000000001L);
    }

    /**
     * 控制日志信息是否写入日志文件
     * @param ps
     * @param bLogToFile  控制开关 true写入日志文件，false不写入日志文件
     */
    public void logToFile(PrintStream ps, boolean bLogToFile)
    {
        if(bLogToFile)
        {
            ps.print(">log to file on !\r\n");
            Log.addRequest(0x4000000000000000L);
        } else
        {
            ps.print(">log to file off !\r\n");
            Log.removeRequest(0x4000000000000000L);
        }
    }

    /**
     * 控制日志信息是否输出到屏幕上
     * @param ps
     * @param bLogToScreen 控制开关 true输出到屏幕上，false不输出到屏幕上
     */
    public void logToScreen(PrintStream ps, boolean bLogToScreen)
    {
        if(bLogToScreen)
        {
            ps.print(">log to screen on !\r\n");
            Log.addRequest(0x8000000000000000L);
        } else
        {
            ps.print(">log to screen off !\r\n");
            Log.removeRequest(0x8000000000000000L);
        }
    }

    /**
     * 输出产品信息
     * @param ps
     */
    public void printProductConfig(PrintStream ps)
    {
        ps.print("> product config !\r\n");
        ps.print("\tVersion : " + m_center.m_pc.m_strVersion + "\r\n");
        ps.print("\tRelease : " + m_center.m_pc.m_strRelease + "\r\n");
        ps.print("\tCompany Site : " + m_center.m_pc.m_strCompanySite + "\r\n");
        ps.print("\tTechnology Site : " + m_center.m_pc.m_strTechnologySite + "\r\n");
        ps.print("\tTelephone : " + m_center.m_pc.m_strTelephone + "\r\n");
        ps.print("\tNote : " + m_center.m_pc.m_strComment + "\r\n");
    }

    /**
     * 输出授权序列号，用于系统产品化时对外保留产权
     * @param strName
     * @param ps
     */
    public void printLicence(String strName, PrintStream ps)
    {
        String strLicence = FileConfig.getLicence(strName);
        byte licence[] = Utility.toBytesValue(strLicence);
        if(licence == null || licence.length != 16)
            ps.print("> invalid licence !\r\n");
        setKey();
        for(int i = 0; i < 16; i++)
            licence[i] ^= m_key[i];

        strLicence = Utility.toHexString(licence);
        ps.print("> licence " + strLicence + "\r\n");
    }

    /**
     * 输出系统当前工作状态
     * @param ps
     */
    public void printStatus(PrintStream ps)
    {
        ps.print("> print status !\r\n");
        m_center.dump(ps);
    }


    /**
     * 控制由状态报告更新消息最终发送状态机制是否开启
     * @param ps
     * @param updatecmppsubmit 控制开关 true开启更新，false关闭更新
     */
    public void updateCmppsubmit(PrintStream ps, boolean updatecmppsubmit)
    {
        if(updatecmppsubmit)
        {
            ps.print(">update cmpp_submit on !\r\n");
            UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH = "true";
        } else
        {
            ps.print(">update cmpp_submit off !\r\n");
            UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH = "false";

        }
    }

   /**
     * 控制用户上行支持机制是否开启
     * @param ps
     * @param usermo 控制开关 true开启用户上行支持，false关闭用户上行支持
     */
    public void userMo(PrintStream ps, boolean usermo)
    {
        if(usermo)
        {
            ps.print(">user mo on !\r\n");
            LeadToneLogicConfig.LOGICSWITCH = "true";
        } else
        {
            ps.print(">user mo off !\r\n");
            LeadToneLogicConfig.LOGICSWITCH = "false";

        }
    }

    /**
     * 控制备份表数据机制是否开启
     * @param ps
     * @param backuptable 控制开关 true开启备份，false关闭备份
     */
    public void backuptable(PrintStream ps, boolean backuptable)
    {
        if(backuptable)
        {
            ps.print(">backup table on !\r\n");
            BackupTableConfig.DYNAMICBACKUPTABLE = "true";
        } else
        {
            ps.print(">backup table off !\r\n");
            BackupTableConfig.DYNAMICBACKUPTABLE = "false";

        }
    }
    
    
    public void motest(PrintStream ps, String phoneNumber, String mo_content){
    	ps.print(">motest mo_content " + mo_content + "!\r\n");
    	LeadToneLogic ltl = new LeadToneLogic();
    	ps.print(">the result:");
    	ps.print(ltl.callSend(phoneNumber, mo_content));
    }
   

    /**
     * 输出当前系统支持的功能的开启情况列表
     * @param ps
     */
     public void status(PrintStream ps)
    {
        try{
           ps.print(">status list !\r\n");
           ps.print("\tupdate cmpp_submit switch : " + (UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH.equalsIgnoreCase("true") ? "on":"off") + "\r\n");
           ps.print("\tusermo switch : " + (LeadToneLogicConfig.LOGICSWITCH.equalsIgnoreCase("true") ? "on":"off") + "\r\n");  
           ps.print("\tbackup table switch : " + (BackupTableConfig.DYNAMICBACKUPTABLE.equalsIgnoreCase("true") ? "on":"off") + "\r\n");

        }catch(Exception e){
           ps.print(">the value of frequency is not correct !\r\n");
        }

    }
    

    /**
     * 输出系统支持的管理功能指令列表
     * @param ps
     * @throws IOException
     */
    public void printCommands(PrintStream ps)
        throws IOException
    {
        ps.print("> commands list !\r\n\n");

        ps.print("\thelp               -- print this list.\r\n");
        ps.print("\tproduct            -- print product configuration.\r\n");
        ps.print("\tterminate          -- stop the system.\r\n");
        ps.print("\tdump               -- dump status.\r\n");
        ps.print("\ttrace              -- trace status until you press any key.\r\n");
        ps.print("\tlog                -- [all | normal | none] config for the events.\r\n");
        ps.print("\tscreen             -- log to the screen [on | off].\r\n");
        ps.print("\tfile               -- log to the file [on | off].\r\n\n");
        
        ps.print("\tstatus             -- other status .\r\n");
        ps.print("\tupdatecmppsubmit   -- update cmpp_submit [on | off].\r\n");
        ps.print("\tusermo             -- user mo [on | off].\r\n");
        ps.print("\tbackuptable        -- backup table [on | off].\r\n\n");
        ps.print("\tmo                 -- mo test [mo phonenumber content].\r\n\n");
        
        
        ps.print("\texit               -- exit controller.\r\n");
    }

    /**
     * 用于管理线程监听管理员登陆，处理用户管理指令输入，根据用户的输入调用相应的管理方法，
     * 或向用户输出系统状态
     */
    public void run()
    {
        try
        {
            Log.log("Controller.run : thread startup !", 1L);
            m_ps.print("> welcom to controller !\r\n");
            if(checkAccount(m_br, m_ps) && checkPassword(m_br, m_ps))
            {
                Log.log("Controller.run : administrator (" + m_socket.toString() + ") authenticated !", 1L);
                printCommands(m_ps);
                m_nStatus = 1;
                while(isRunning()) 
                {
                    if(!m_br.ready())
                    {
                        nap();
                        continue;
                    }
                    String strLine = m_br.readLine();
                    if(strLine == null || strLine.length() <= 0)
                    {
                        nap();
                        continue;
                    }
                    String args[] = Utility.parseCommand(strLine);
                    if(args.length == 1 && args[0].equals("terminate"))
                    {
                        Center.m_bNeedTerminate = true;
                        break;
                    }
                    if(args.length == 1 && args[0].equals("help"))
                    {
                        printCommands(m_ps);
                        continue;
                    }
                    if(args.length == 1 && args[0].equals("product"))
                    {
                        printProductConfig(m_ps);
                        continue;
                    }
                    if(args.length == 1 && args[0].equals("dump"))
                    {
                        printStatus(m_ps);
                        continue;
                    }
                    if(args.length == 1 && args[0].equals("exit"))
                        break;
                    if(args.length == 2 && args[0].equals("licence"))
                        printLicence(args[1], m_ps);
                    else
                    if(args.length == 2 && args[0].equals("log"))
                    {
                        if(args[1].equals("all"))
                            logAll(m_ps);
                        else
                        if(args[1].equals("none"))
                            logNone(m_ps);
                        else
                            logNormally(m_ps);
                    } else
                    if(args.length == 2 && args[0].equals("screen"))
                    {
                        if(args[1].equals("on"))
                            logToScreen(m_ps, true);
                        else
                            logToScreen(m_ps, false);
                    } else
                    if(args.length == 2 && args[0].equals("file"))
                    {
                        if(args[1].equals("on"))
                            logToFile(m_ps, true);
                        else
                            logToFile(m_ps, false);
                    } else
                    if(args.length == 1 && args[0].equals("trace"))
                    {
                        for(; !m_br.ready(); sleep(3000))
                            printStatus(m_ps);
                    } else
                    if(args.length == 2 && args[0].equals("updatecmppsubmit"))
                        {
                            if(args[1].equals("on"))
                                updateCmppsubmit(m_ps, true);
                            else
                                updateCmppsubmit(m_ps, false);
                        } else
                        if(args.length == 2 && args[0].equals("usermo"))
                        {
                            if(args[1].equals("on"))
                                userMo(m_ps, true);
                            else
                                userMo(m_ps, false);
                        } else
                        if(args.length == 1 && args[0].equals("status"))
                        {

                             status(m_ps);
                        }else
                            if(args.length == 2 && args[0].equals("backuptable"))
                            {
                                if(args[1].equals("on"))
                                    backuptable(m_ps, true);
                                else
                                    backuptable(m_ps, false);
                            }
                            else
                                if(args.length == 3 && args[0].equals("mo"))
                                {
                                   
                                        motest(m_ps,args[1],args[2]);
                                }
                }
            }
        }
        catch(Exception e) { }
        close();
        m_nStatus = 3;
        Log.log("Controller.run : thread stopped !", 1L);
        empty();
    }

    /**
     * 用于鉴权序列号，用于系统产品化时的产权保护
     */
    public void setKey()
    {
        ServiceProvider sp = new ServiceProvider("8888", "989898", m_center.m_admin.m_strAccount, m_center.m_admin.m_strPassword);
        long lTime = System.currentTimeMillis();
        if((lTime & 3L) == 0L)
        {
            CMPPConnect connect = sp.getCMPPConnect(0x20100, 1);
            m_key = connect.authenticator_sp;
        } else
        if((lTime & 3L) == 1L)
        {
            CMPPConnect connect = sp.getCMPPConnect(0x20400, 3);
            m_key = connect.authenticator_sp;
        } else
        if((lTime & 3L) == 2L)
        {
            CMPPConnect connect = sp.getCMPPConnect(0x20300, 1);
            m_key = connect.authenticator_sp;
        } else
        {
            CMPPConnect connect = sp.getCMPPConnect(0x20200, 1);
            m_key = connect.authenticator_sp;
        }
    }


}
