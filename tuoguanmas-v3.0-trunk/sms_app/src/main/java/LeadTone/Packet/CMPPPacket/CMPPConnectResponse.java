package LeadTone.Packet.CMPPPacket;

import LeadTone.*;


/**
 * 参见CMPP协议2.1对建立连接消息回复的定义
 */
public class CMPPConnectResponse extends CMPPPacket
{
    /**
     * 参见CMPP协议2.1对连接回复消息的定义
     */
    public byte status;
    /**
     * 参见CMPP协议2.1对连接回复消息的定义
     */
    public byte authenticator_ismg[];
    /**
     * 参见CMPP协议2.1对连接回复消息的定义
     */
    public byte version;

    /**
     * 构造方法初始化类变量
     * @param sequence_id
     */
    public CMPPConnectResponse(int sequence_id)
    {
        super(0x80000001, sequence_id);
        status = 0;
        authenticator_ismg = null;
        version = 18;
    }

    /**
     * 构造方法初始化类变量
     * @param packet
     */
    public CMPPConnectResponse(CMPPPacket packet)
    {
        super(packet);
        status = 0;
        authenticator_ismg = null;
        version = 18;
    }

    /**
     * 检验连接回复消息包格式是否合法
     * @return 返回格式是否合法
     */
    public boolean isValid()
    {
        if(command_id != 0x80000001)
        {
            Log.log("CMPPConnectResponse.isValid : not a CMPP_CONNECT_RESPONSE command !", 0x80600000000000L);
            return false;
        }
        if(authenticator_ismg == null || authenticator_ismg.length != 16)
        {
            Log.log("CMPPConnectResponse.isValid : invalid ismg_auth length!", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * 输出连接消息包内容
     * @param lMethod
     */
    public void dump(long lMethod)
    {
        Log.log("\tstatus = " + status + " (" + CMPPStatus.toString(status) + ") ", 0x80000000000000L | lMethod);
        Log.log("\tauthenticator_ismg = " + Utility.toHexString(authenticator_ismg), 0x80000000000000L | lMethod);
        Log.log("\tversion = 0x" + Utility.toHexString(version), 0x80000000000000L | lMethod);
    }

    /**
     * 组装连接消息包
     * @throws BufferException
     */
    public void wrap()
        throws BufferException
    {
        Log.log("CMPPConnectResponse.wrap : wrap elements !", 0x80800000000000L);
        dump(0x800000000000L);
        addByte(status);
        addBytes(authenticator_ismg);
        addByte(version);
    }

    /**
     * 解析连接消息包
     * @throws BufferException
     */
    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPConnectResponse.unwrap : unwrap elements !", 0x80800000000000L);
        status = getByte();
        authenticator_ismg = getBytes(16);
        version = getByte();
        dump(0x800000000000L);
    }


}