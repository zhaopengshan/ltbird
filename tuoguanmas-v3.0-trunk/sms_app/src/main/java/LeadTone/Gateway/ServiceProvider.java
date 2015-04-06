package LeadTone.Gateway;

import LeadTone.LeadToneDate;
import LeadTone.MD5.MD5;
import LeadTone.Packet.CMPPPacket.CMPPConnect;
import LeadTone.Packet.CNGPPacket.CNGPLogin;
import LeadTone.Packet.NOKIAPacket.NOKIAConnect;
import LeadTone.Packet.SGIPPacket.SGIPBind;
import LeadTone.Packet.SMPPPacket.*;
import LeadTone.Utility;
import java.io.PrintStream;

/**
 * 建立网关连接时的必要参数，
 * 提供生成基于MD5加密算法的建立连接用鉴权码的方法，
 * 生成建立连接包
 */
public class ServiceProvider
{
    /**
     * 参考SMPP协议3.3
     */
    public String system_id;
    /**
     * 参考SMPP协议3.3
     */
    public String system_type;
    /**
     * 参考SMPP协议3.3
     */
    public byte interface_version;
    /**
     * 参考SMPP协议3.3
     */
    public byte addr_ton;
    /**
     * 参考SMPP协议3.3
     */
    public byte addr_npi;
    /**
     * 参考SMPP协议3.3
     */
    public String address_range;
    /**
     * 服务代码，参考CMPP协议2.1
     */
    public String service_code;
    /**
     * 企业代码，参考CMPP协议2.1
     */
    public String enterprise_code;
    /**
     * 登陆用户名，参考CMPP协议2.1
     */
    public String user;
    /**
     * 登录密码，参考CMPP协议2.1
     */
    public String password;

    /**
     * 构造方法初始化类变量
     */
    public ServiceProvider()
    {
        system_id = null;
        system_type = null;
        interface_version = 52;
        addr_ton = 0;
        addr_npi = 0;
        address_range = null;
        service_code = null;
        enterprise_code = null;
        user = null;
        password = null;
    }

    /**
     * 构造方法初始化类变量
     */
    public ServiceProvider(String system_id, String password, String system_type, byte interface_version)
    {
        this.system_id = null;
        this.system_type = null;
        this.interface_version = 52;
        addr_ton = 0;
        addr_npi = 0;
        address_range = null;
        service_code = null;
        enterprise_code = null;
        user = null;
        this.password = null;
        this.system_id = system_id;
        this.password = password;
        this.system_type = system_type;
        this.interface_version = interface_version;
    }

    /**
     * 构造方法初始化类变量
     */
    public ServiceProvider(String service_code, String enterprise_code, String user, String password)
    {
        system_id = null;
        system_type = null;
        interface_version = 52;
        addr_ton = 0;
        addr_npi = 0;
        address_range = null;
        this.service_code = null;
        this.enterprise_code = null;
        this.user = null;
        this.password = null;
        this.service_code = service_code;
        this.enterprise_code = enterprise_code;
        this.user = user;
        this.password = password;
    }

    /**
     * SGIP协议建立连接包的鉴权部分拼装，参见SGIP协议1.2
     * @param nSessionConfig 建立会话的类型
     * @return SGIP协议建立连接包的鉴权部分
     */
    public SGIPBind getSGIPBind(int nSessionConfig)
    {
        if(nSessionConfig == 1)
        {
            SGIPBind bind = new SGIPBind(0);
            if(enterprise_code.length() <= 5)
                bind.node_id = 0x493e0 + Integer.parseInt(enterprise_code);
            else
                bind.node_id = (int)(0xb2d05e00L + (long)Integer.parseInt(enterprise_code));
            bind.time_stamp = Utility.get_time_stamp(Utility.toTimeString(new LeadToneDate()));
            bind.login_type = 1;
            bind.login_name = user;
            bind.login_password = password;
            return bind;
        } else
        {
            return null;
        }
    }

    /**
     * SMPP协议建立连接包的鉴权部分拼装，参见SMPP协议3.3
     * @param nSessionConfig 建立会话的类型
     * @return SMPP协议建立连接包的鉴权部分
     */
    public SMPPBind getSMPPBind(int nSessionConfig)
    {
        SMPPBind bind = null;
        if(nSessionConfig == 2)
            bind = new SMPPBindReceiver(0);
        else
        if(nSessionConfig == 1)
            bind = new SMPPBindTransmitter(0);
        else
        if(nSessionConfig == 3)
            bind = new SMPPBindTransceiver(0);
        else
            return null;
        bind.system_id = system_id;
        bind.password = password;
        bind.system_type = system_type;
        bind.interface_version = interface_version;
        bind.esme.TON = addr_ton;
        bind.esme.NPI = addr_npi;
        bind.esme.address = address_range;
        return bind;
    }

    /**
     * NOKIA建立连接包的鉴权部分拼装，参见NOKIA对于CMPP协议的特殊化
     * @param nSessionConfig 建立会话的类型
     * @return NOKIA建立连接包的鉴权部分
     */
    public NOKIAConnect getNOKIAConnect(int nSessionConfig)
    {
        NOKIAConnect connect = new NOKIAConnect(0);
        connect.source_addr = enterprise_code;
        connect.time_stamp = 0x3b47fcbd;
        connect.version = 18;
        connect.authenticator_sp = get_authenticator_sp(enterprise_code, password, connect.time_stamp);
        if(nSessionConfig == 2)
            connect.bind_type = 1;
        else
        if(nSessionConfig == 1)
            connect.bind_type = 0;
        else
        if(nSessionConfig == 3)
            connect.bind_type = 2;
        else
            connect.bind_type = 0;
        return connect;
    }


    /**
     * CMPP协议建立连接包的鉴权部分拼装，参见CMPP协议2.1，
     * 其中包含不同厂商对CMPP协议实现的差异化，请具体参考具体厂家的特殊化,
     * 网关类型请参考Gateway包下的GatewayType类详细描述
     * @param nGatewayType
     * @param nSessionConfig
     * @param time_stamp
     * @return  CMPP协议建立连接包的鉴权部分
     */
    public CMPPConnect getCMPPConnect(int nGatewayType, int nSessionConfig, int time_stamp)
    {
        CMPPConnect connect = new CMPPConnect(0);
        connect.time_stamp = time_stamp;
        connect.version = 32;
        if(nGatewayType == 0x20100)
        {
            if(nSessionConfig == 1)
            {
                String strTime = Utility.get_time_stamp(time_stamp);
                connect.version = 32;
                connect.source_addr = user;
                connect.authenticator_sp = get_authenticator_sp(user, password, strTime);
            } else
            {
                return null;
            }
        } else
        if(nGatewayType == 0x20000 || nGatewayType == 0x20400 || nGatewayType == 0x20500 || nGatewayType == 0x20700)
        {
            if(nSessionConfig == 3)
            {
                String strTime = Utility.get_time_stamp(time_stamp);
                connect.source_addr = enterprise_code;
                connect.authenticator_sp = get_authenticator_sp(enterprise_code, password, strTime);
            } else
            {
                return null;
            }
        } else
        if(nGatewayType == 0x20900)
        {
            connect.version = 32;
            if(nSessionConfig == 3)
            {
                String strTime = Utility.get_time_stamp(time_stamp);
                connect.source_addr = user;
                connect.authenticator_sp = get_authenticator_sp(user, password, strTime);
            } else
            {
                return null;
            }
        } else
        if(nGatewayType == 0x20010)
        {
            if(nSessionConfig == 0)
            {
                String strTime = Utility.get_time_stamp(time_stamp);
                connect.source_addr = enterprise_code;
                connect.authenticator_sp = get_authenticator_sp(enterprise_code, password, strTime);
            } else
            {
                return null;
            }
        } else
        if(nGatewayType == 0x20300)
        {
            if(nSessionConfig == 1)
            {
                connect.source_addr = enterprise_code;
                connect.authenticator_sp = get_authenticator_sp(enterprise_code, password);
            } else
            {
                return null;
            }
        } else
        if(nGatewayType == 0x20800)
        {
            if(nSessionConfig == 3 || nSessionConfig == 2)
            {
                String strTime = Utility.get_time_stamp(time_stamp);
                connect.source_addr = enterprise_code;
                connect.authenticator_sp = get_authenticator_sp(enterprise_code, password, strTime);
            } else
            {
                return null;
            }
        } else
        //如果为亚信网关，需使用Connection包中的版本Version字段表示要建立的连接类型，Version=1表示接收 ，Version=0表示发送
        if(nGatewayType == 0x20200)
        {
            byte bBytes[] = new byte[10];
            for(int i = 0; i < bBytes.length; i++)
                bBytes[i] = 48;

            if(nSessionConfig == 2)
                connect.version = 1;
            else
                connect.version = 0;
            connect.source_addr = enterprise_code;
            connect.authenticator_sp = get_authenticator_sp(enterprise_code, password, new String(bBytes));
            connect.time_stamp = 0;
        } else
        {
            connect.source_addr = enterprise_code;
            connect.authenticator_sp = new byte[16];
        }
        return connect;
    }

    /**
     * CMPP协议建立连接包的鉴权部分拼装，参见CMPP协议2.1，
     * 其中包含不同厂商对CMPP协议实现的差异化，请具体参考具体厂家的特殊化,
     * 网关类型请参考Gateway包下的GatewayType类详细描述
     * @param nGatewayType
     * @param nSessionConfig
     * @return CMPP协议建立连接包
     */
    public CMPPConnect getCMPPConnect(int nGatewayType, int nSessionConfig)
    {
        String strTime = Utility.toTimeString(new LeadToneDate());
        int time_stamp = Utility.get_time_stamp(strTime);
        return getCMPPConnect(nGatewayType, nSessionConfig, time_stamp);
    }


    public CNGPLogin getCNGPLogin(int nSessionConfig)
    {
        LeadToneDate now = new LeadToneDate();
        String strTime = Utility.toTimeString(now);
        String strTime1 = Utility.toTimeString1(now);
        int time_stamp = Utility.get_time_stamp(strTime);
        CNGPLogin login = new CNGPLogin(1, 0);
        login.client_id = enterprise_code;
        user = enterprise_code;
        login.authenticator_sp = get_cngp_auth_sp(user, password, strTime1);
        if(nSessionConfig == 1)
            login.login_type = 0;
        else
        if(nSessionConfig == 2)
            login.login_type = 1;
        else
            login.login_type = 2;
        login.timestamp = time_stamp;
        login.version = 16;
        return login;
    }

    /**
     * 生成CMPP协议要求的基于MD5加密算法的建立连接用鉴权码
     * @param icp_id
     * @param password
     * @return 基于MD5加密算法的建立连接用鉴权码
     */
    public byte[] get_authenticator_sp(String icp_id, String password)
    {
        int nLength = 42 + password.length();
        byte bBytes[] = new byte[nLength];
        System.arraycopy(icp_id.getBytes(), 0, bBytes, 0, icp_id.length());
        System.arraycopy(password.getBytes(), 0, bBytes, 42, password.length());
        MD5 md5 = new MD5();
        md5.Init();
        md5.Update(bBytes, 0, nLength);
        return md5.Final();
    }

    /**
     * 生成CNGP协议要求的基于MD5加密算法的建立连接用鉴权码
     * @param user
     * @param share_secret
     * @param strDate
     * @return 基于MD5加密算法的建立连接用鉴权码
     */
    public byte[] get_cngp_auth_sp(String user, String share_secret, String strDate)
    {
        int nLength = (user != null ? user.length() : 0) + 7 + (share_secret != null ? share_secret.length() : 0) + (strDate != null ? strDate.length() : 0);
        byte bBytes[] = new byte[nLength];
        int nOffset = 0;
        if(user != null)
            System.arraycopy(user.getBytes(), 0, bBytes, nOffset, user.length());
        nOffset += (user != null ? user.length() : 0) + 7;
        if(share_secret != null)
            System.arraycopy(share_secret.getBytes(), 0, bBytes, nOffset, share_secret.length());
        nOffset += share_secret != null ? share_secret.length() : 0;
        if(strDate != null)
            System.arraycopy(strDate.getBytes(), 0, bBytes, nOffset, strDate.length());
        MD5 md5 = new MD5();
        md5.Init();
        md5.Update(bBytes, 0, nLength);
        return md5.Final();
    }

    /**
     * 生成CMPP协议要求的基于MD5加密算法的建立连接用鉴权码
     * @param user
     * @param share_secret
     * @param strDate
     * @return 基于MD5加密算法的建立连接用鉴权码
     */
    public byte[] get_authenticator_sp(String user, String share_secret, String strDate)
    {
        int nLength = (user != null ? user.length() : 0) + 9 + (share_secret != null ? share_secret.length() : 0) + (strDate != null ? strDate.length() : 0);
        byte bBytes[] = new byte[nLength];
        int nOffset = 0;
        if(user != null)
            System.arraycopy(user.getBytes(), 0, bBytes, nOffset, user.length());
        nOffset += (user != null ? user.length() : 0) + 9;
        if(share_secret != null)
            System.arraycopy(share_secret.getBytes(), 0, bBytes, nOffset, share_secret.length());
        nOffset += share_secret != null ? share_secret.length() : 0;
        if(strDate != null)
            System.arraycopy(strDate.getBytes(), 0, bBytes, nOffset, strDate.length());
        MD5 md5 = new MD5();
        md5.Init();
        md5.Update(bBytes, 0, nLength);
        return md5.Final();
    }

    /**
     * 生成NOKIA要求的基于MD5加密算法的建立连接用鉴权码
     * @param user
     * @param share_secret
     * @param timestamp
     * @return 基于MD5加密算法的建立连接用鉴权码
     */
    public byte[] get_authenticator_sp(String user, String share_secret, int timestamp)
    {
        int nLength = 15 + (share_secret != null ? share_secret.length() : 0) + 4;
        byte bBytes[] = new byte[nLength];
        int nOffset = 0;
        if(user != null)
            System.arraycopy(user.getBytes(), 0, bBytes, nOffset, user.length());
        nOffset = 15;
        if(share_secret != null)
            System.arraycopy(share_secret.getBytes(), 0, bBytes, nOffset, share_secret.length());
        nOffset += share_secret != null ? share_secret.length() : 0;
        bBytes[nOffset + 0] = (byte)(timestamp >> 24 & 0xff);
        bBytes[nOffset + 1] = (byte)(timestamp >> 16 & 0xff);
        bBytes[nOffset + 2] = (byte)(timestamp >> 8 & 0xff);
        bBytes[nOffset + 3] = (byte)(timestamp & 0xff);
        MD5 md5 = new MD5();
        md5.Init();
        md5.Update(bBytes, 0, nLength);
        return md5.Final();
    }

    /**
     * 测试方法
     * @param args
     */
    public static void main(String args[])
    {
        int nLength = 9;
        byte bBytes[] = new byte[nLength];
        bBytes[0] = 0;
        int nOffset = 1;
        System.arraycopy("hcyd".getBytes(), 0, bBytes, 1, 4);
        System.arraycopy("hcyd".getBytes(), 0, bBytes, 5, 4);
        MD5 md5 = new MD5();
        md5.Init();
        md5.Update(bBytes, 0, nLength);
        byte results[] = md5.Final();
        System.out.print(Utility.toHexString(results));
    }


}