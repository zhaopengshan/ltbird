package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPBindReceiverResponse extends SMPPBindResponse
{

    public SMPPBindReceiverResponse()
    {
    }

    public SMPPBindReceiverResponse(int sequence_id)
    {
        super(0x80000001, sequence_id);
    }

    public SMPPBindReceiverResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000001)
        {
            Log.log("SMPPBindReceiverResponse.isValid : not a SMPP_BIND_RECEIVER_RESPONSE command !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}