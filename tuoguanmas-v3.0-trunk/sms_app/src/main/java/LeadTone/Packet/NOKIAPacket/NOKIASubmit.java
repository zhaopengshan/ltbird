package LeadTone.Packet.NOKIAPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Utility;



public class NOKIASubmit extends NOKIAPacket
{
    public String sp_id;
    public String service_type;
    public byte fee_type;
    public int info_fee;
    public byte protocol_id;
    public byte message_mode;
    public byte priority;
    public String validity_period;
    public String schedule;
    public byte fee_user_type;
    public String fee_user;
    public String source_address;
    public byte count_of_destination;
    public String destination_address[];
    public byte data_coding;
    public int sm_length;
    public byte short_message[];

    public NOKIASubmit(int sequence_id)
    {
        super(4, sequence_id);
        sp_id = "888888";
        service_type = null;
        fee_type = 1;
        info_fee = 0;
        protocol_id = 0;
        message_mode = 0;
        priority = 0;
        validity_period = null;
        schedule = null;
        fee_user_type = 1;
        fee_user = null;
        source_address = null;
        count_of_destination = 0;
        data_coding = 0;
        sm_length = 0;
        short_message = null;
    }

    public NOKIASubmit(NOKIAPacket packet)
    {
        super(packet);
        sp_id = "888888";
        service_type = null;
        fee_type = 1;
        info_fee = 0;
        protocol_id = 0;
        message_mode = 0;
        priority = 0;
        validity_period = null;
        schedule = null;
        fee_user_type = 1;
        fee_user = null;
        source_address = null;
        count_of_destination = 0;
        data_coding = 0;
        sm_length = 0;
        short_message = null;
    }

    public boolean isValid()
    {
        if(command_id != 4)
        {
            Log.log("NOKIASubmit.isValid : not a CMPP_SUBMIT command !", 0x2600000000000L);
            return false;
        }
        if(priority < 0 || priority > 9)
        {
            Log.log("NOKIASubmit.isValid : invalid priority !", 0x2600000000000L);
            return false;
        }
        if(service_type != null && service_type.length() > 10)
        {
            Log.log("NOKIASubmit.isValid : service_type length exceed !", 0x2600000000000L);
            return false;
        }
        if(fee_user_type < 0 || fee_user_type > 3)
        {
            Log.log("NOKIASubmit.isValid : invalid fee_user_type !", 0x2600000000000L);
            return false;
        }
        if((fee_user_type == 1 || fee_user_type == 2) && fee_user != null)
        {
            Log.log("NOKIASubmit.isValid : fee_user is not null !", 0x2600000000000L);
            return false;
        }
        if(fee_user_type == 3 && (fee_user == null || fee_user.length() == 0))
        {
            Log.log("NOKIASubmit.isValid : null fee_user !", 0x2600000000000L);
            return false;
        }
        if(fee_user != null && fee_user.length() > 21)
        {
            Log.log("NOKIASubmit.isValid : fee_user length exceed !", 0x2600000000000L);
            return false;
        }
        if(sp_id != null && sp_id.length() > 6)
        {
            Log.log("NOKIASubmit.isValid : sp_id length exceed !", 0x2600000000000L);
            return false;
        }
        if(fee_type < 0 || fee_type > 5)
        {
            Log.log("NOKIASubmit.isValid : invalid fee_type !", 0x2600000000000L);
            return false;
        }
        if(info_fee < 0 || info_fee > 0xf423f)
        {
            Log.log("NOKIASubmit.isValid : info_fee exceed !", 0x2600000000000L);
            return false;
        }
        if(validity_period != null && validity_period.length() != 16 && validity_period.length() != 0)
        {
            Log.log("NOKIASubmit.isValid : invalid validity_period !", 0x2600000000000L);
            return false;
        }
        if(schedule != null && schedule.length() != 16 && schedule.length() != 0)
        {
            Log.log("NOKIASubmit.isValid : invalid schedule !", 0x2600000000000L);
            return false;
        }
        if(source_address != null && source_address.length() > 20)
        {
            Log.log("NOKIASubmit.isValid : source_address length exceed !", 0x2600000000000L);
            return false;
        }
        if(count_of_destination <= 0 || count_of_destination >= 256)
        {
            Log.log("NOKIASubmit.isValid : invalid count_of_destination or too much count_of_destination !", 0x2600000000000L);
            return false;
        }
        if(destination_address == null || destination_address.length != count_of_destination)
        {
            Log.log("NOKIASubmit.isValid : null destination_address or not according to count_of_destination !", 0x2600000000000L);
            return false;
        }
        for(int i = 0; i < count_of_destination; i++)
            if(destination_address[i] != null && destination_address[i].length() > 20)
            {
                Log.log("NOKIASubmit.isValid : destination_address length exceed !", 0x2600000000000L);
                return false;
            }

        if(data_coding != 0 && data_coding != 3 && data_coding != 4 && data_coding != 8 && data_coding != 15)
            Log.log("NOKIASubmit.isValid : data_coding not defined in CMPP !", 0x2600000000000L);
        if(sm_length <= 0 || data_coding == 0 && sm_length > 160 || (data_coding == 8 || data_coding == 15) && sm_length > 140)
        {
            Log.log("NOKIASubmit.isValid : invalid sm_length !", 0x2600000000000L);
            return false;
        }
        if(short_message == null || short_message.length != sm_length)
        {
            Log.log("NOKIASubmit.isValid : invalid short_message !", 0x2600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tsp_id = \"" + sp_id + "\"", 0x2000000000000L | lMethod);
        Log.log("\tservice_type = \"" + service_type + "\"", 0x2000000000000L | lMethod);
        Log.log("\tfee_type = " + fee_type + " (" + CMPPFeeType.toString(fee_type) + ")", 0x2000000000000L | lMethod);
        Log.log("\tinfo_fee = " + info_fee, 0x2000000000000L | lMethod);
        Log.log("\tprotocol_id = 0x" + Utility.toHexString(protocol_id), 0x2000000000000L | lMethod);
        Log.log("\tmessage_mode = 0x" + Utility.toHexString(message_mode), 0x2000000000000L | lMethod);
        Log.log("\tpriority = " + priority, 0x2000000000000L | lMethod);
        Log.log("\tvalidity_period = \"" + validity_period + "\"", 0x2000000000000L | lMethod);
        Log.log("\tschedule = \"" + schedule + "\"", 0x2000000000000L | lMethod);
        Log.log("\tfee_user_type = 0x" + Utility.toHexString(fee_user_type) + " (" + CMPPFeeUserType.toString(fee_user_type) + ")", 0x2000000000000L | lMethod);
        Log.log("\tfee_user = \"" + fee_user + "\"", 0x2000000000000L | lMethod);
        Log.log("\tsource_address = \"" + source_address + "\"", 0x2000000000000L | lMethod);
        Log.log("\tcount_of_destination = " + count_of_destination, 0x2000000000000L | lMethod);
        for(int i = 0; i < count_of_destination; i++)
            Log.log("\tdestination_address[" + i + "] = \"" + destination_address[i] + "\"", 0x2000000000000L | lMethod);

        Log.log("\tdata_coding = " + data_coding + " (" + CMPPMessageFormat.toString(data_coding) + ")", 0x2000000000000L | lMethod);
        Log.log("\tsm_length = " + sm_length, 0x2000000000000L | lMethod);
        Log.log("\tshort_message = 0x" + Utility.toHexString(short_message), 0x4000000000000L | lMethod);
        if(data_coding == 0 || data_coding == 15)
            Log.log("\t            = \"" + Utility.get_msg_content(short_message) + "\"", 0x4000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("NOKIASubmit.wrap : wrap elements !", 0x2800000000000L);
        dump(0x800000000000L);
        addCString(sp_id);
        addCString(service_type);
        addByte(fee_type);
        addInteger(info_fee);
        addByte(protocol_id);
        addByte(message_mode);
        addByte(priority);
        addCString(validity_period);
        addCString(schedule);
        addByte(fee_user_type);
        addCString(fee_user);
        addCString(source_address);
        addByte(count_of_destination);
        for(int i = count_of_destination; i > 0; i--)
            addCString(destination_address[i - 1]);

        addByte(data_coding);
        addByte((byte)(sm_length & 0xff));
        addBytes(short_message);
        if(data_coding == 0 || data_coding == 15)
            addByte((byte)0);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("NOKIASubmit.unwrap : unwrap elements !", 0x2800000000000L);
        sp_id = getCString();
        service_type = getCString();
        fee_type = getByte();
        info_fee = getInteger();
        protocol_id = getByte();
        message_mode = getByte();
        priority = getByte();
        validity_period = getCString();
        schedule = getCString();
        fee_user_type = getByte();
        fee_user = getCString();
        source_address = getCString();
        count_of_destination = getByte();
        destination_address = new String[count_of_destination];
        for(int i = 0; i < count_of_destination; i++)
            destination_address[i] = getCString();

        data_coding = getByte();
        sm_length = getByte();
        short_message = getBytes(sm_length);
        dump(0x800000000000L);
    }


}