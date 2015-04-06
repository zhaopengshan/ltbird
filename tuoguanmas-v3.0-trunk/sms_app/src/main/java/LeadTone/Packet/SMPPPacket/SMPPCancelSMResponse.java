package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPCancelSMResponse extends SMPPCommandSMResponse
{

    public SMPPCancelSMResponse()
    {
    }

    public SMPPCancelSMResponse(int sequence_id)
    {
        super(0x80000008, sequence_id);
    }

    public SMPPCancelSMResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000008)
        {
            Log.log("SMPPCancelSM.isValid : not a SMPP_CANCEL_SM_RESPONSE command !", 0x18600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}