package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPEnquireLink extends SMPPPacket
{

    public SMPPEnquireLink()
    {
    }

    public SMPPEnquireLink(int sequence_id)
    {
        super(21, sequence_id);
    }

    public SMPPEnquireLink(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 21)
        {
            Log.log("SMPPEnquireLink.isValid : not a SMPP_PENQUIRE_LINK command !", 0x20600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}