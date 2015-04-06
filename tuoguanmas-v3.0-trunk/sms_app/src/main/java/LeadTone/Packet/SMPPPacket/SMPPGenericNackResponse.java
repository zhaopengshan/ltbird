package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPGenericNackResponse extends SMPPPacket
{

    public SMPPGenericNackResponse()
    {
    }

    public SMPPGenericNackResponse(int sequence_id)
    {
        super(0x80000000, sequence_id);
    }

    public SMPPGenericNackResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000000)
        {
            Log.log("SMPPGenericNackResponse.isValid : not a SMPP_GENERIC_NACK_RESPONSE command !", 0x20600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}