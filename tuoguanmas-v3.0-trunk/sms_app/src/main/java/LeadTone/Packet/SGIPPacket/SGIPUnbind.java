package LeadTone.Packet.SGIPPacket;

import LeadTone.Log;


public class SGIPUnbind extends SGIPPacket
{

    public SGIPUnbind(int sequence_id)
    {
        super(2, sequence_id);
    }

    public SGIPUnbind(SGIPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 2)
        {
            Log.log("SGIPUnbind.isValid : not a SGIP_UNBIND command !", 0x40600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}