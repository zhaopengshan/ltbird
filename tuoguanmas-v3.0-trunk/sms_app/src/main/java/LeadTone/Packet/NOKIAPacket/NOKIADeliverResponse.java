package LeadTone.Packet.NOKIAPacket;

import LeadTone.Log;



public class NOKIADeliverResponse extends NOKIAPacket
{

    public NOKIADeliverResponse(int sequence_id)
    {
        super(0x80000005, sequence_id);
    }

    public NOKIADeliverResponse(NOKIAPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000005)
        {
            Log.log("NOKIADeliverResponse.isValid : not a CMPP_DELIVER_RESPONSE command !", 0x4600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}