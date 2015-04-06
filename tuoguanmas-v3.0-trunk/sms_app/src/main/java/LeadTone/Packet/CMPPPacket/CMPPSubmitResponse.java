package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Utility;


/**
 * 参见CMPP协议2.1对发送消息回复的定义
 */
public class CMPPSubmitResponse extends CMPPPacket
{
    public long msg_id;
    public byte result;

    public CMPPSubmitResponse(int sequence_id)
    {
        super(0x80000004, sequence_id);
        msg_id = 1L;
        result = 0;
    }

    public CMPPSubmitResponse(CMPPPacket packet)
    {
        super(packet);
        msg_id = 1L;
        result = 0;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000004)
        {
            Log.log("CMPPSubmitResponse.isValid : not a CMPP_SUBMIT_RESPONSE command !", 0x2600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
    	
    	Log.log("消息类型＝CMPP_SubmitAck,网关响应报文为： ", 0x4000000000000L | lMethod);
        Log.log("\tmsg_id = 0x" + Utility.toHexString(msg_id), 0x2000000000000L | lMethod);
        Log.log("\tresult = " + result + " (" + CMPPResult.toString(result) + ")", 0x2000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPSubmitResponse.wrap : wrap elements !", 0x2800000000000L);
        dump(0x800000000000L);
        addLong(msg_id);
        addByte(result);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPSubmitResponse.unwrap : unwrap elements !", 0x2800000000000L);
        msg_id = getLong();
        result = getByte();
        dump(0x800000000000L);
    }


}