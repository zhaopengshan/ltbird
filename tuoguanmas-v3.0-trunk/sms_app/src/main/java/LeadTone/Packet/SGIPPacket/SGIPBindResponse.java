package LeadTone.Packet.SGIPPacket;

import LeadTone.Log;



public class SGIPBindResponse extends SGIPResponse
{

    public SGIPBindResponse(SGIPPacket packet)
    {
        super(packet);
    }

    public SGIPBindResponse(int sequence_id)
    {
        super(0x80000001, sequence_id);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000001)
        {
            Log.log("SGIPBindResponse.isValid : not a SGIP_BIND_RESPONSE command !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}