package LeadTone.Packet.NOKIAPacket;

import LeadTone.Log;


public class NOKIAConnectResponse extends NOKIAPacket
{

    public NOKIAConnectResponse(int sequence_id)
    {
        super(0x80000001, sequence_id);
    }

    public NOKIAConnectResponse(NOKIAPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000001)
        {
            Log.log("NOKIAConnectResponse.isValid : not a CMPP_CONNECT_RESPONSE command !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}