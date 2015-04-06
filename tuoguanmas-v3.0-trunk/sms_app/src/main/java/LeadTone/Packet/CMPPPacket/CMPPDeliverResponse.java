package LeadTone.Packet.CMPPPacket;

import LeadTone.*;


/**
 * 参见CMPP协议2.1对接收消息回复的定义
 */
public class CMPPDeliverResponse extends CMPPPacket
{
    public long msg_id;
    public byte result;
    public byte reserve[];

    public CMPPDeliverResponse(int sequence_id)
    {
        super(0x80000005, sequence_id);
        msg_id = 1L;
        result = 0;
        reserve = null;
    }

    public CMPPDeliverResponse(CMPPPacket packet)
    {
        super(packet);
        msg_id = 1L;
        result = 0;
        reserve = null;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000005)
        {
            Log.log("CMPPDeliverResponse.isValid : not a CMPP_DELIVER_RESPONSE command !", 0x4600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tmsg_id = 0x" + Utility.toHexString(msg_id), 0x4000000000000L | lMethod);
        Log.log("\tresult = " + result + " (" + CMPPResult.toString(result) + ")", 0x4000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPDeliverResponse.wrap : wrap elements !", 0x4800000000000L);
        dump(0x800000000000L);
        addLong(msg_id);
        addByte(result);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPDeliverResponse.unwrap : unwrap elements !", 0x4800000000000L);
        msg_id = getLong();
        result = getByte();
        dump(0x800000000000L);
    }


}