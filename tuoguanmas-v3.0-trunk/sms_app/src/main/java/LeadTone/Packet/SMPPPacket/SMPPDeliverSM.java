package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;


public class SMPPDeliverSM extends SMPPSM
{

    public SMPPDeliverSM()
    {
    }

    public SMPPDeliverSM(int sequence_id)
    {
        super(5, sequence_id);
    }

    public SMPPDeliverSM(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 5)
        {
            Log.log("SMPPDeliverSM.isValid : not a SMPP_DELIVER_SM command !", 0x6600000000000L);
            return false;
        }
        return super.isValid();
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPDeliverSM.unwrap : unwrap elements !", 0x6800000000000L);
        service_type = getCString();
        source.unwrap(this);
        destination.unwrap(this);
        esm_class = getByte();
        protocol_id = getByte();
        priority_flag = getByte();
        schedule_delivery_time = getCString();
        validity_period = getCString();
        registered_delivery = getByte();
        replace_if_present_flag = getByte();
        data_coding = getByte();
        sm_default_msg_id = getByte() & 0xff;
        sm_length = getByte() & 0xff;
        short_message = getBytes(sm_length);
        m_ops.unwrap(this);
        dump(0x800000000000L);
    }
}