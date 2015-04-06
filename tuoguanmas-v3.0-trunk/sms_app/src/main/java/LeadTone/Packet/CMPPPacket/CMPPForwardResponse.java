package LeadTone.Packet.CMPPPacket;

import LeadTone.*;


/**
 * 参见CMPP协议2.1对转发消息回复的定义
 */
public class CMPPForwardResponse extends CMPPPacket
{
    public long msg_id;
    public int pk_total;
    public int pk_number;
    public byte result;

    public CMPPForwardResponse(int sequence_id)
    {
        super(0x80000009, sequence_id);
        msg_id = 1L;
        pk_total = 1;
        pk_number = 1;
        result = 0;
    }

    public CMPPForwardResponse(CMPPPacket packet)
    {
        super(packet);
        msg_id = 1L;
        pk_total = 1;
        pk_number = 1;
        result = 0;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000009)
        {
            Log.log("CMPPForwardResponse.isValid : not a CMPP_FORWARD_RESPONSE command !", 0x6600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tmsg_id = 0x" + Utility.toHexString(msg_id), 0x6000000000000L | lMethod);
        Log.log("\tpk_total = " + pk_total, 0x6000000000000L | lMethod);
        Log.log("\tpk_number = " + pk_number, 0x6000000000000L | lMethod);
        Log.log("\tresult = " + result + " (" + CMPPResult.toString(result) + ")", 0x6000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPForwardResponse.wrap : wrap elements !", 0x6800000000000L);
        dump(0x800000000000L);
        addLong(msg_id);
        addByte((byte)(pk_total & 0xff));
        addByte((byte)(pk_number & 0xff));
        addByte(result);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPForwardResponse.unwrap : unwrap elements !", 0x6800000000000L);
        msg_id = getLong();
        pk_total = getByte() & 0xff;
        pk_number = getByte() & 0xff;
        result = getByte();
        dump(0x800000000000L);
    }


}