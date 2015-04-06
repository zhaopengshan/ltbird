package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;

/**
 * 参见CMPP协议2.1对保持连接消息回复的定义
 */
public class CMPPActiveTestResponse extends CMPPPacket
{
    public byte success_id;

    public CMPPActiveTestResponse(int sequence_id)
    {
        super(0x80000008, sequence_id);
        success_id = 0;
    }

    public CMPPActiveTestResponse(CMPPPacket packet)
    {
        super(packet);
        success_id = 0;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000008)
        {
            Log.log("CMPPActiveTestResponse.isValid : not a CMPP_ACTIVETEST_RESPONSE command !", 0x20600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tsuccess_id = " + success_id + " (" + CMPPSuccessID.toString(success_id) + ") ", 0x20000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPActiveTestResponse.wrap : wrap elements !", 0x20800000000000L);
        dump(0x800000000000L);
        addByte(success_id);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPActiveTestResponse.unwrap : unwrap elements !", 0x20800000000000L);
        success_id = getByte();
        dump(0x800000000000L);
    }


}