package LeadTone.Packet.SGIPPacket;

import LeadTone.Log;


public class SGIPUnbindResponse extends SGIPPacket
{

    public SGIPUnbindResponse(int sequence_id)
    {
        super(0x80000002, sequence_id);
    }

    public SGIPUnbindResponse(SGIPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000002)
        {
            Log.log("SGIPUnbindResponse.isValid : not a SGIP_UNBIND_RESPONSE command !", 0x40600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}