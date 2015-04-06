package LeadTone.Packet.SMPPPacket;

import LeadTone.*;


public class SMPPSubmitMulti extends SMPPSubmitSM
{
    public int number_of_dests;
    public DestinationAddressList destinations;

    public SMPPSubmitMulti()
    {
        number_of_dests = 0;
        destinations = new DestinationAddressList();
    }

    public SMPPSubmitMulti(int sequence_id)
    {
        super(33, sequence_id);
        number_of_dests = 0;
        destinations = new DestinationAddressList();
    }

    public SMPPSubmitMulti(SMPPPacket packet)
    {
        super(packet);
        number_of_dests = 0;
        destinations = new DestinationAddressList();
    }

    public void empty()
    {
        super.empty();
        destinations.empty();
    }

    public boolean isValid()
    {
        if(command_id != 33)
        {
            Log.log("SMPPSubmitMulti.isValid : not a SMPP_SUBMIT_MULTI command !", 0x6600000000000L);
            return false;
        }
        if(service_type != null && service_type.length() > 5)
        {
            Log.log("SMPPSubmitMulti.isValid : invalid service_type !", 0x6600000000000L);
            return false;
        }
        if(!source.isValid())
        {
            Log.log("SMPPSubmitMulti.isValid : invalid source !", 0x6600000000000L);
            return false;
        }
        if(number_of_dests <= 0 || number_of_dests > 254)
        {
            Log.log("SMPPSubmitMulti.isValid : invalid number_of_dests !", 0x6600000000000L);
            return false;
        }
        if(destinations == null || destinations.size() != number_of_dests)
        {
            Log.log("SMPPSubmitMulti.isValid : invalid destinationes !", 0x6600000000000L);
            return false;
        }
        if(!destinations.isValid())
        {
            Log.log("SMPPSubmitMulti.isValid : invalid destinationes !", 0x6600000000000L);
            return false;
        }
        if(schedule_delivery_time != null && schedule_delivery_time.length() != 16)
        {
            Log.log("SMPPSubmitMulti.isValid : invalid schedule_delivery_time !", 0x6600000000000L);
            return false;
        }
        if(validity_period != null && validity_period.length() != 16)
        {
            Log.log("SMPPSubmitMulti.isValid : invalid validity_period !", 0x6600000000000L);
            return false;
        }
        if(sm_default_msg_id <= 0 || sm_default_msg_id >= 255)
        {
            Log.log("SMPPSubmitMulti.isValid : invalid sm_default_msg_id !", 0x6600000000000L);
            return false;
        }
        if(sm_length < 0 || sm_length > 254)
        {
            Log.log("SMPPSubmitMulti.isValid : invalid sm_length !", 0x6600000000000L);
            return false;
        }
        if(sm_length != (short_message != null ? short_message.length : 0))
        {
            Log.log("SMPPSubmitMulti.isValid : invalid short_message !", 0x6600000000000L);
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
        Log.log("\tnumber_of_dests = " + number_of_dests, 0x6000000000000L | lMethod);
        destinations.dump(lMethod);
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
        Log.log("\tshort_message(binary) = 0x" + Utility.toHexString(short_message), 0x6000000000000L | lMethod);
        m_ops.dump(lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPSubmitMulti.wrap : wrap elements !", 0x6800000000000L);
        dump(0x800000000000L);
        addCString(service_type);
        source.wrap(this);
        addByte((byte)(number_of_dests & 0xff));
        destinations.wrap(this);
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
        addBytes(short_message);
        m_ops.wrap(this);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPSubmitMulti.unwrap : unwrap elements !", 0x6800000000000L);
        service_type = getCString();
        source.unwrap(this);
        number_of_dests = getByte();
        destinations.setSize(number_of_dests);
        destinations.unwrap(this);
        esm_class = getByte();
        protocol_id = getByte();
        priority_flag = getByte();
        schedule_delivery_time = getCString();
        validity_period = getCString();
        registered_delivery = getByte();
        replace_if_present_flag = getByte();
        data_coding = getByte();
        sm_default_msg_id = getByte();
        sm_length = getByte();
        short_message = getBytes(sm_length);
        m_ops.unwrap(this);
        dump(0x800000000000L);
    }


}