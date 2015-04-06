package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Utility;


/**
 * 参见CMPP协议2.1消息格式的定义，此类作为具体类型消息包父类实现共公部分消息头的操作
 */
public class CMPPPacket extends Packet
{
    /**
     * 消息指令
     */
    public int command_id;
    /**
     * 消息序列号
     */
    public int sequence_id;

    /**
     * 构造方法初始化类变量
     */
    public CMPPPacket()
    {
        command_id = 0x80000000;
        sequence_id = 1;
    }

    /**
     * 构造方法初始化类变量
     * @param command_id 消息指令
     * @param sequence_id 消息序列号
     */
    public CMPPPacket(int command_id, int sequence_id)
    {
        this.command_id = 0x80000000;
        this.sequence_id = 1;
        this.command_id = command_id;
        this.sequence_id = sequence_id;
    }

    /**
     * 构造方法初始化类变量
     * @param packet
     */
    public CMPPPacket(Packet packet)
    {
        super(packet);
        command_id = 0x80000000;
        sequence_id = 1;
    }

    /**
     * 构造方法初始化类变量
     * @param packet
     */
    public CMPPPacket(CMPPPacket packet)
    {
        super(packet);
        command_id = 0x80000000;
        sequence_id = 1;
        command_id = packet.command_id;
        sequence_id = packet.sequence_id;
    }

    /**
     * 检查消息头格式是否合法
     * @return 返回消息头是否合法的布尔值
     */
    public boolean isValid()
    {
        if(!CMPPCommandID.isValid(command_id))
        {
            Log.log("CMPPPacket.isValid : not a valid command !", 0x200600000000000L);
            return false;
        }
        if(sequence_id < 0)
        {
            Log.log("CMPPPacket.isValid : invalid sequence_id !", 0x200600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * 输出消息头的内容
     * @param lMethod
     */
    public void dumpCMPPPacket(long lMethod)
    {
        if(Log.isRequested(lMethod))
        {
            Log.log("\tcommand_id = 0x" + Utility.toHexString(command_id) + " (" + CMPPCommandID.toString(command_id) + ")", 0x200000000000000L | lMethod);
            Log.log("\tsequence_id = " + sequence_id, 0x200000000000000L | lMethod);
        }
    }

    /**
     * 组装消息头
     * @throws BufferException
     */
    public void wrapCMPPPacket()
        throws BufferException
    {
        Log.log("CMPPPacket.wrapCMPPPacket : wrap elements !", 0x200800000000000L);
        dumpCMPPPacket(0x800000000000L);
        insertInteger(sequence_id);
        insertInteger(command_id);
    }

    /**
     * 解析消息头
     * @throws BufferException
     */
    public void unwrapCMPPPacket()
        throws BufferException
    {
        Log.log("CMPPPacket.unwrapCMPPPacket : unwrap elements !", 0x200800000000000L);
        command_id = getInteger();
        sequence_id = getInteger();
        dumpCMPPPacket(0x800000000000L);
    }


}