package LeadTone.Packet.NOKIAPacket;

import LeadTone.Log;



public class NOKIATerminateResponse extends NOKIAPacket
{

    public NOKIATerminateResponse(int sequence_id)
    {
        super(0x80000002, sequence_id);
    }

    public NOKIATerminateResponse(NOKIAPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000002)
        {
            Log.log("NOKIATerminateResponse.isValid : not a CMPP_TERMINATE_RESPONSE command !", 0x40600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}