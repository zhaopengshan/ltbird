package LeadTone.Packet.NOKIAPacket;

import LeadTone.Log;


public class NOKIAActiveTest extends NOKIAPacket
{

    public NOKIAActiveTest(int sequence_id)
    {
        super(8, sequence_id);
    }

    public NOKIAActiveTest(NOKIAPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 8)
        {
            Log.log("NOKIAActiveTest.isValid : not a CMPP_ACTIVETEST command !", 0x20600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}