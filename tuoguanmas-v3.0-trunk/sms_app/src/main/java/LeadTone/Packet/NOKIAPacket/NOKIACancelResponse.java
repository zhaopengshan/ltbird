package LeadTone.Packet.NOKIAPacket;

import LeadTone.Log;



public class NOKIACancelResponse extends NOKIAPacket
{

    public NOKIACancelResponse(int sequence_id)
    {
        super(0x80000007, sequence_id);
    }

    public NOKIACancelResponse(NOKIAPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000007)
        {
            Log.log("NOKIACancelResponse.isValid : not a CMPP_CANCEL_RESPONSE command !", 0x10600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}