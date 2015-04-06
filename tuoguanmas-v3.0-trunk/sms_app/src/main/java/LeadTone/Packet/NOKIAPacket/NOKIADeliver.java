package LeadTone.Packet.NOKIAPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPMessageFormat;
import LeadTone.Utility;



public class NOKIADeliver extends NOKIAPacket
{
    public String source_address;
    public String destination_address;
    public String service_type;
    public byte protocol_id;
    public byte message_mode;
    public byte priority;
    public byte data_coding;
    public int sm_length;
    public byte short_message[];
    public NOKIAStatusReport status_report;

    public NOKIADeliver(int sequence_id)
    {
        super(5, sequence_id);
        source_address = null;
        destination_address = null;
        service_type = null;
        protocol_id = 0;
        message_mode = 0;
        priority = 0;
        data_coding = 0;
        sm_length = 0;
        short_message = null;
        status_report = new NOKIAStatusReport();
    }

    public NOKIADeliver(NOKIAPacket packet)
    {
        super(packet);
        source_address = null;
        destination_address = null;
        service_type = null;
        protocol_id = 0;
        message_mode = 0;
        priority = 0;
        data_coding = 0;
        sm_length = 0;
        short_message = null;
        status_report = new NOKIAStatusReport();
    }

    public boolean isValid()
    {
        if(command_id != 5)
        {
            Log.log("NOKIADeliver.isValid : not a CMPP_DELIVER command !", 0x4600000000000L);
            return false;
        }
        if(destination_address != null && destination_address.length() >= 21)
        {
            Log.log("NOKIADeliver.isValid : invalid destination_address !", 0x4600000000000L);
            return false;
        }
        if(service_type != null && service_type.length() >= 11)
        {
            Log.log("NOKIADeliver.isValid : invalid service_type !", 0x4600000000000L);
            return false;
        }
        if(data_coding != 0 && data_coding != 3 && data_coding != 4 && data_coding != 8 && data_coding != 15)
            Log.log("NOKIADeliver.isValid : data_coding is not defined in CMPP !", 0x4600000000000L);
        if(source_address != null && source_address.length() >= 21)
        {
            Log.log("NOKIADeliver.isValid : invalid source_address !", 0x4600000000000L);
            return false;
        }
        if((message_mode & 1) == 1)
        {
            if(!status_report.isValid())
            {
                Log.log("NOKIADeliver.isValid : invalid status_report !", 0x4600000000000L);
                return false;
            }
        } else
        {
            if(sm_length < 0 || data_coding == 0 && sm_length > 160 || (data_coding == 0 || data_coding == 15) && sm_length > 140)
            {
                Log.log("NOKIADeliver.isValid : invalid sm_length !", 0x4600000000000L);
                return false;
            }
            if(short_message != null && short_message.length != sm_length)
            {
                Log.log("NOKIADeliver.isValid : invalid short_message !", 0x4600000000000L);
                return false;
            }
        }
        return true;
    }

    public void dump(long lMethod)
    {
        Log.log("\tcommand_status = 0x" + Utility.toHexString(command_status) + " (" + NOKIACommandStatus.toString(command_status) + ")", 0x4000000000000L | lMethod);
        Log.log("\tsource_address = \"" + source_address + "\"", 0x4000000000000L | lMethod);
        Log.log("\tdestination_address = \"" + destination_address + "\"", 0x4000000000000L | lMethod);
        Log.log("\tservice_type = \"" + service_type + "\"", 0x4000000000000L | lMethod);
        Log.log("\tprotocol_id = 0x" + Utility.toHexString(protocol_id), 0x4000000000000L | lMethod);
        Log.log("\tmessage_mode = 0x" + Utility.toHexString(message_mode), 0x4000000000000L | lMethod);
        Log.log("\tpriority = 0x" + Utility.toHexString(priority), 0x4000000000000L | lMethod);
        Log.log("\tdata_coding = " + data_coding + " (" + CMPPMessageFormat.toString(data_coding) + ")", 0x4000000000000L | lMethod);
        Log.log("\tsm_length = " + sm_length, 0x4000000000000L | lMethod);
        Log.log("\tbinary_content = 0x" + Utility.toHexString(short_message), 0x4000000000000L | lMethod);
        if((message_mode & 0) == 0)
        {
            if(data_coding == 0 || data_coding == 15)
                Log.log("\ttext_content = \"" + Utility.get_msg_content(short_message) + "\"", 0x4000000000000L | lMethod);
        } else
        if((message_mode & 1) == 1)
            status_report.dump(lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("NOKIADeliver.wrap : wrap elements !", 0x4800000000000L);
        dump(0x800000000000L);
        addCString(source_address);
        addCString(destination_address);
        addCString(service_type);
        addByte(protocol_id);
        addByte(message_mode);
        addByte(priority);
        addByte(data_coding);
        addByte((byte)(sm_length & 0xff));
        if((message_mode & 1) == 1)
            status_report.wrap(this);
        else
            addBytes(short_message);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("NOKIADeliver.unwrap : unwrap elements !", 0x4800000000000L);
        source_address = getCString();
        destination_address = getCString();
        service_type = getCString();
        protocol_id = getByte();
        message_mode = getByte();
        priority = getByte();
        data_coding = getByte();
        sm_length = getByte() & 0xff;
        short_message = getBytes(sm_length);
        if((message_mode & 1) == 1)
        {
            setOffset(false, -sm_length);
            status_report.unwrap(this);
        }
        dump(0x4800000000000L);
    }


}