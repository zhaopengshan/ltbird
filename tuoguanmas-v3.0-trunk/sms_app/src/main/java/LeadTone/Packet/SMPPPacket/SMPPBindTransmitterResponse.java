package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;


public class SMPPBindTransmitterResponse extends SMPPBindResponse
{

    public SMPPBindTransmitterResponse()
    {
    }

    public SMPPBindTransmitterResponse(int sequence_id)
    {
        super(0x80000002, sequence_id);
    }

    public SMPPBindTransmitterResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000002)
        {
            Log.log("SMPPBindTransmitterResponse.isValid : not a SMPP_BIND_TRANSMITTER_RESPONSE command !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}