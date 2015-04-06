package LeadTone.Packet.SGIPPacket;

import LeadTone.Log;



public class SGIPDeliverResponse extends SGIPResponse
{

    public SGIPDeliverResponse(SGIPPacket packet)
    {
        super(packet);
    }

    public SGIPDeliverResponse(int sequence_id)
    {
        super(0x80000004, sequence_id);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000004)
        {
            Log.log("SGIPDeliverResponse.isValid : not a SGIP_DELIVER_RESPONSE command !", 0x4600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}