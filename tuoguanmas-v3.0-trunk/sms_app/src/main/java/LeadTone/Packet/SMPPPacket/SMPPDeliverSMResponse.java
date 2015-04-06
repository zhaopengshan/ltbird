package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPDeliverSMResponse extends SMPPSMResponse
{

    public SMPPDeliverSMResponse()
    {
    }

    public SMPPDeliverSMResponse(int sequence_id)
    {
        super(0x80000005, sequence_id);
    }

    public SMPPDeliverSMResponse(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000005)
        {
            Log.log("SMPPDeliverSMResponse.isValid : not a SMPP_DELIVER_SM_RESPONSE command !", 0x6600000000000L);
            return false;
        }
        return super.isValid();
    }
}