package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPSubmitSM extends SMPPSM
{

    public SMPPSubmitSM()
    {
    }

    public SMPPSubmitSM(int sequence_id)
    {
        super(4, sequence_id);
    }

    public SMPPSubmitSM(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
    }

    public SMPPSubmitSM(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 4)
        {
            Log.log("SMPPSubmitSM.isValid : not a SMPP_SUBMIT_SM command !", 0x6600000000000L);
            return false;
        }
        return super.isValid();
    }
}