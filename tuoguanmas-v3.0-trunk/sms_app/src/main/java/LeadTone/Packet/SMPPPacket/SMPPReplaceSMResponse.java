package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPReplaceSMResponse extends SMPPCommandSMResponse
{

    public SMPPReplaceSMResponse()
    {
    }

    public SMPPReplaceSMResponse(int sequence_id)
    {
        super(0x80000007, sequence_id);
    }

    public SMPPReplaceSMResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000007)
        {
            Log.log("SMPPCancelSM.isValid : not a SMPP_REPLACE_SM_RESPONSE command !", 0x18600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}