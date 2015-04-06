package LeadTone.Packet.SMPPPacket;

import LeadTone.*;


public class SMPPReplaceSM extends SMPPCommandSM
{
    public String schedule_delivery_time;
    public String validity_period;
    public byte registered_delivery;
    public int sm_default_message_id;
    public int sm_length;
    public byte short_message[];

    public SMPPReplaceSM()
    {
        schedule_delivery_time = null;
        validity_period = null;
        registered_delivery = 0;
        sm_default_message_id = 0;
        sm_length = 0;
        short_message = null;
    }

    public SMPPReplaceSM(int sequence_id)
    {
        super(7, sequence_id);
        schedule_delivery_time = null;
        validity_period = null;
        registered_delivery = 0;
        sm_default_message_id = 0;
        sm_length = 0;
        short_message = null;
    }

    public SMPPReplaceSM(SMPPPacket packet)
    {
        super(packet);
        schedule_delivery_time = null;
        validity_period = null;
        registered_delivery = 0;
        sm_default_message_id = 0;
        sm_length = 0;
        short_message = null;
    }

    public boolean isValid()
    {
        if(command_id != 7)
        {
            Log.log("SMPPReplaceSM.isValid : not a SMPP_REPLACE_SM command !", 0x18600000000000L);
            return false;
        }
        if(!super.isValid())
            return false;
        if(schedule_delivery_time != null && schedule_delivery_time.length() != 16)
        {
            Log.log("SMPPReplaceSM.isValid : invalid schedule_delivery_time !", 0x18600000000000L);
            return false;
        }
        if(validity_period != null && validity_period.length() != 16)
        {
            Log.log("SMPPReplaceSM.isValid : invalid validity_period !", 0x18600000000000L);
            return false;
        }
        if(sm_default_message_id <= 0 || sm_default_message_id >= 255)
        {
            Log.log("SMPPReplaceSM.isValid : invalid sm_default_message_id !", 0x18600000000000L);
            return false;
        }
        if(sm_length < 0 || sm_length > 254)
        {
            Log.log("SMPPReplaceSM.isValid : invalid sm_length !", 0x18600000000000L);
            return false;
        }
        if(sm_length != (short_message != null ? short_message.length : 0))
        {
            Log.log("SMPPReplaceSM.isValid : invalid short_message !", 0x18600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tmessage_id = \"" + message_id + "\"", 0x18000000000000L | lMethod);
        source.dump(lMethod);
        Log.log("\tschedule_delivery_time = " + schedule_delivery_time, 0x18000000000000L | lMethod);
        Log.log("\tvalidity_period = " + validity_period, 0x18000000000000L | lMethod);
        Log.log("\tregistered_delivery = 0x" + Utility.toHexString(registered_delivery) + " (" + RegisteredDelivery.toString(registered_delivery) + ")", 0x18000000000000L | lMethod);
        Log.log("\tsm_default_message_id = " + sm_default_message_id, 0x18000000000000L | lMethod);
        Log.log("\tsm_length = " + sm_length, 0x18000000000000L | lMethod);
        Log.log("\tshort_message(binary) = 0x" + Utility.toHexString(short_message), 0x18000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPReplaceSM.wrap : wrap elements !", 0x18800000000000L);
        dump(0x800000000000L);
        addCString(message_id);
        source.wrap(this);
        addCString(schedule_delivery_time);
        addCString(validity_period);
        addByte(registered_delivery);
        addByte((byte)(sm_default_message_id & 0xff));
        addByte((byte)(sm_length & 0xff));
        addBytes(short_message);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPReplaceSM.unwrap : unwrap elements !", 0x18800000000000L);
        message_id = getCString();
        source.unwrap(this);
        schedule_delivery_time = getCString();
        validity_period = getCString();
        registered_delivery = getByte();
        sm_default_message_id = getByte();
        sm_length = getByte();
        short_message = getBytes(sm_length);
        dump(0x800000000000L);
    }


}