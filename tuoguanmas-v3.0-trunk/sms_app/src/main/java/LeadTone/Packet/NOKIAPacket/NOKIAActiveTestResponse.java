package LeadTone.Packet.NOKIAPacket;

import LeadTone.Log;


public class NOKIAActiveTestResponse extends NOKIAPacket
{

    public NOKIAActiveTestResponse(int sequence_id)
    {
        super(0x80000008, sequence_id);
    }

    public NOKIAActiveTestResponse(NOKIAPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000008)
        {
            Log.log("NOKIAActiveTestResponse.isValid : not a CMPP_ACTIVETEST_RESPONSE command !", 0x20600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}