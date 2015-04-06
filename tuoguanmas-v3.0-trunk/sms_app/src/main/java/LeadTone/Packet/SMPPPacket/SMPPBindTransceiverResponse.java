package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPBindTransceiverResponse extends SMPPBindResponse
{

    public SMPPBindTransceiverResponse()
    {
    }

    public SMPPBindTransceiverResponse(int sequence_id)
    {
        super(0x80000009, sequence_id);
    }

    public SMPPBindTransceiverResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000009)
        {
            Log.log("SMPPBindTransceiverResponse.isValid : not a SMPP_BIND_TRANSCEIVER_RESPONSE command !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}