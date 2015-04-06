package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPEnquireLinkResponse extends SMPPPacket
{

    public SMPPEnquireLinkResponse()
    {
    }

    public SMPPEnquireLinkResponse(int sequence_id)
    {
        super(0x80000015, sequence_id);
    }

    public SMPPEnquireLinkResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000015)
        {
            Log.log("SMPPEnquireLinkResponse.isValid : not a SMPP_ENQUIRE_LINK_RESPONSE command !", 0x20600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}