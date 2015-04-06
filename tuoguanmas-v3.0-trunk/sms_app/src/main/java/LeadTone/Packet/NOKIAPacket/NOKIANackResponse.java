package LeadTone.Packet.NOKIAPacket;

import LeadTone.Log;



public class NOKIANackResponse extends NOKIAPacket
{

    public NOKIANackResponse(int sequence_id)
    {
        super(0x80000000, sequence_id);
    }

    public NOKIANackResponse(NOKIAPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000000)
        {
            Log.log("NOKIANackResponse.isValid : not a CMPP_NACK_RESPONSE command !", 0x1600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}