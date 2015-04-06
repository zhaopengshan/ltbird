package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPUnbindResponse extends SMPPPacket
{

    public SMPPUnbindResponse()
    {
    }

    public SMPPUnbindResponse(int sequence_id)
    {
        super(0x80000006, sequence_id);
    }

    public SMPPUnbindResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000006)
        {
            Log.log("SMPPUnbindResponse.isValid : not a SMPP_UNBIND_RESPONSE command !", 0x40600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}