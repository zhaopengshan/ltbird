package LeadTone.Packet.CMPPPacket;

import LeadTone.Log;


/**
 * 参见CMPP协议2.1对断开连接消息回复的定义
 */
public class CMPPTerminateResponse extends CMPPPacket
{

    public CMPPTerminateResponse(int sequence_id)
    {
        super(0x80000002, sequence_id);
    }

    public CMPPTerminateResponse(CMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000002)
        {
            Log.log("CMPPTerminateResponse.isValid : not a CMPP_TERMINATE_RESPONSE command !", 0x40600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}