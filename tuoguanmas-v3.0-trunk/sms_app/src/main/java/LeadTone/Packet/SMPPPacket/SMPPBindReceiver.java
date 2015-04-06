package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPBindReceiver extends SMPPBind
{

    public SMPPBindReceiver()
    {
    }

    public SMPPBindReceiver(int sequence_id)
    {
        super(1, sequence_id);
    }

    public SMPPBindReceiver(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 1)
        {
            Log.log("SMPPBindReceiver.isValid : not a SMPP_BIND_RECEIVER command !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}