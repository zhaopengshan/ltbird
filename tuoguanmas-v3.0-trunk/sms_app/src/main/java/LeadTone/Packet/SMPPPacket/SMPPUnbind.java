package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPUnbind extends SMPPPacket
{

    public SMPPUnbind()
    {
    }

    public SMPPUnbind(int sequence_id)
    {
        super(6, sequence_id);
    }

    public SMPPUnbind(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 6)
        {
            Log.log("SMPPUnbind.isValid : not a SMPP_UNBIND command !", 0x40600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}