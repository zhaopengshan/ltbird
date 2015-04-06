package LeadTone.Packet.CMPPPacket;

import LeadTone.Log;


/**
 * 参见CMPP协议2.1对断开连接消息的定义
 */
public class CMPPTerminate extends CMPPPacket
{

    public CMPPTerminate(int sequence_id)
    {
        super(2, sequence_id);
    }

    public CMPPTerminate(CMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 2)
        {
            Log.log("CMPPTerminate.isValid : not a CMPP_TERMINATE command !", 0x40600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}