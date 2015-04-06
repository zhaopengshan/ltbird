package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPSMResponse extends SMPPDataSMResponse
{

    public SMPPSMResponse()
    {
    }

    public SMPPSMResponse(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
    }

    public SMPPSMResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(message_id != null && message_id.length() > 64)
        {
            Log.log("SMPPSMResponse.isValid : invalid message_id !", 0x6600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}