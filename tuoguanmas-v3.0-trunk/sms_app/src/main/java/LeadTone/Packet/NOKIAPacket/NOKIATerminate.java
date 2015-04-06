package LeadTone.Packet.NOKIAPacket;

import LeadTone.Log;



public class NOKIATerminate extends NOKIAPacket
{

    public NOKIATerminate(int sequence_id)
    {
        super(2, sequence_id);
    }

    public NOKIATerminate(NOKIAPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 2)
        {
            Log.log("NOKIATerminate.isValid : not a CMPP_TERMINATE command !", 0x40600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}