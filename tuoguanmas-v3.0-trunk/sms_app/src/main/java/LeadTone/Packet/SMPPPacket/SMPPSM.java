package LeadTone.Packet.SMPPPacket;

import LeadTone.*;


public class SMPPSM extends SMPPDataSM
{
    public byte protocol_id;
    public byte priority_flag;
    public String schedule_delivery_time;
    public String validity_period;
    public byte replace_if_present_flag;
    public int sm_default_msg_id;
    public int sm_length;
    public byte short_message[];

    public SMPPSM()
    {
        protocol_id = 0;
        priority_flag = 0;
        schedule_delivery_time = null;
        validity_period = null;
        replace_if_present_flag = 0;
        sm_default_msg_id = 0;
        sm_length = 0;
        short_message = null;
    }

    public SMPPSM(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
        protocol_id = 0;
        priority_flag = 0;
        schedule_delivery_time = null;
        validity_period = null;
        replace_if_present_flag = 0;
        sm_default_msg_id = 0;
        sm_length = 0;
        short_message = null;
    }

    public SMPPSM(SMPPPacket packet)
    {
        super(packet);
        protocol_id = 0;
        priority_flag = 0;
        schedule_delivery_time = null;
        validity_period = null;
        replace_if_present_flag = 0;
        sm_default_msg_id = 0;
        sm_length = 0;
        short_message = null;
    }

    public boolean isValid()
    {
        if(service_type != null && service_type.length() > 5)
        {
            Log.log("SMPPSM.isValid : invalid service_type !", 0x6600000000000L);
            return false;
        }
        if(!source.isValid())
        {
            Log.log("SMPPSM.isValid : invalid source !", 0x6600000000000L);
            return false;
        }
        if(!destination.isValid())
        {
            Log.log("SMPPSM.isValid : invalid destination !", 0x6600000000000L);
            return false;
        }
        if(schedule_delivery_time != null && schedule_delivery_time.length() != 16)
        {
            Log.log("SMPPSM.isValid : invalid schedule_delivery_time !", 0x6600000000000L);
            return false;
        }
        if(validity_period != null && validity_period.length() != 16)
        {
            Log.log("SMPPSM.isValid : invalid validity_period !", 0x6600000000000L);
            return false;
        }
        if(sm_length < 0 || sm_length > 254)
        {
            Log.log("SMPPSM.isValid : invalid sm_length !", 0x6600000000000L);
            return false;
        }
        if(sm_length != (short_message != null ? short_message.length : 0))
        {
            Log.log("SMPPSM.isValid : invalid short_message !", 0x6600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tservice_type = \"" + service_type + "\"", 0x6000000000000L | lMethod);
        source.dump(lMethod);
        destination.dump(lMethod);
        Log.log("\tesm_class = 0x" + Utility.toHexString(esm_class) + " (" + ESMClass.toString(esm_class) + ")", 0x6000000000000L | lMethod);
        Log.log("\tprotocol_id = 0x" + Utility.toHexString(protocol_id), 0x6000000000000L | lMethod);
        Log.log("\tpriority_flag = " + priority_flag, 0x6000000000000L | lMethod);
        Log.log("\tschedule_delivery_time = " + schedule_delivery_time, 0x6000000000000L | lMethod);
        Log.log("\tvalidity_period = " + validity_period, 0x6000000000000L | lMethod);
        Log.log("\tregistered_delivery = 0x" + Utility.toHexString(registered_delivery) + " (" + RegisteredDelivery.toString(registered_delivery) + ")", 0x6000000000000L | lMethod);
        Log.log("\treplace_if_present_flag = 0x" + Utility.toHexString(replace_if_present_flag), 0x6000000000000L | lMethod);
        Log.log("\tdata_coding = 0x" + Utility.toHexString(data_coding) + " (" + DataCoding.toString(data_coding) + ")", 0x6000000000000L | lMethod);
        Log.log("\tsm_default_msg_id = " + sm_default_msg_id, 0x6000000000000L | lMethod);
        Log.log("\tsm_length = " + sm_length, 0x6000000000000L | lMethod);
        Log.log("\tshort_message = 0x" + Utility.toHexString(short_message), 0x6000000000000L | lMethod);
        if(data_coding == 0 || data_coding == 15)
            Log.log("\t              = \"" + Utility.get_msg_content(short_message) + "\"", 0x4000000000000L | lMethod);
        m_ops.dump(lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPSM.wrap : wrap elements !", 0x6800000000000L);
        dump(0x800000000000L);
        addCString(service_type);
        source.wrap(this);
        destination.wrap(this);
        addByte(esm_class);
        addByte(protocol_id);
        addByte(priority_flag);
        addCString(schedule_delivery_time);
        addCString(validity_period);
        addByte(registered_delivery);
        addByte(replace_if_present_flag);
        addByte(data_coding);
        addByte((byte)(sm_default_msg_id & 0xff));
        addByte((byte)(sm_length & 0xff));
        if(short_message != null && short_message.length > 0)
            addBytes(short_message);
        m_ops.wrap(this);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPSM.unwrap : unwrap elements !", 0x6800000000000L);
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