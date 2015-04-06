package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;


public class SMPPBindTransmitter extends SMPPBind
{

    public SMPPBindTransmitter()
    {
    }

    public SMPPBindTransmitter(int sequence_id)
    {
        super(2, sequence_id);
    }

    public SMPPBindTransmitter(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 2)
        {
            Log.log("SMPPBindTransmitter.isValid : not a SMPP_BIND_TRANSMITTER command !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}