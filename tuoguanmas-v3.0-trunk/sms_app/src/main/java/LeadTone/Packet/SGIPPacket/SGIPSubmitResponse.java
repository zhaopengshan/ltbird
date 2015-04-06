package LeadTone.Packet.SGIPPacket;

import LeadTone.Log;


public class SGIPSubmitResponse extends SGIPResponse
{

    public SGIPSubmitResponse(SGIPPacket packet)
    {
        super(packet);
    }

    public SGIPSubmitResponse(int sequence_id)
    {
        super(0x80000003, sequence_id);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000003)
        {
            Log.log("SGIPSubmitResponse.isValid : not a SGIP_SUBMIT_RESPONSE command !", 0x2600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}