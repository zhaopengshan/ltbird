package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPBindTransceiver extends SMPPBind
{

    public SMPPBindTransceiver()
    {
    }

    public SMPPBindTransceiver(int sequence_id)
    {
        super(9, sequence_id);
    }

    public SMPPBindTransceiver(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 9)
        {
            Log.log("SMPPBindTransceiver.isValid : not a SMPP_BIND_TRANSCEIVER command !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}