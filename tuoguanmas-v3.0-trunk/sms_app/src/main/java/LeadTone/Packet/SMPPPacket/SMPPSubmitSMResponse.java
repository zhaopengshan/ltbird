package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPSubmitSMResponse extends SMPPSMResponse
{

    public SMPPSubmitSMResponse()
    {
    }

    public SMPPSubmitSMResponse(int sequence_id)
    {
        super(0x80000004, sequence_id);
    }

    public SMPPSubmitSMResponse(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
    }

    public SMPPSubmitSMResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000004)
        {
            Log.log("SMPPSubmitSMResponse.isValid : not a SMPP_SUBMIT_SM_RESPONSE command !", 0x6600000000000L);
            return false;
        }
        return super.isValid();
    }
}