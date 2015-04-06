package LeadTone.Packet.CMPPPacket;

import LeadTone.Log;


/**
 * 参见CMPP协议2.1对保持连接消息的定义
 */
public class CMPPActiveTest extends CMPPPacket
{

    public CMPPActiveTest(int sequence_id)
    {
        super(8, sequence_id);
    }

    public CMPPActiveTest(CMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 8)
        {
            Log.log("CMPPActiveTest.isValid : not a CMPP_ACTIVETEST command !", 0x20600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}