package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Utility;

/**
 * 参见CMPP协议2.1对接收消息的定义
 */
public class CMPPDeliver extends CMPPPacket
{
    public long msg_id;
    public String destination_id;
    public String service_id;
    public byte tp_pid;
    public byte tp_udhi;
    public byte msg_fmt;
    public String src_terminal_id;
    public byte registered_delivery;
    public int msg_length;
    public byte msg_content[];
    public CMPPStatusReport status_report;
    public String reserve;

    public CMPPDeliver(int sequence_id)
    {
        super(5, sequence_id);
        destination_id = null;
        service_id = null;
        tp_pid = 0;
        tp_udhi = 0;
        msg_fmt = 0;
        src_terminal_id = null;
        registered_delivery = 0;
        msg_length = 0;
        msg_content = null;
        status_report = new CMPPStatusReport();
        reserve = "19751204";
    }

    public CMPPDeliver(CMPPPacket packet)
    {
        super(packet);
        destination_id = null;
        service_id = null;
        tp_pid = 0;
        tp_udhi = 0;
        msg_fmt = 0;
        src_terminal_id = null;
        registered_delivery = 0;
        msg_length = 0;
        msg_content = null;
        status_report = new CMPPStatusReport();
        reserve = "19751204";
    }

    public boolean isValid()
    {
        if(command_id != 5)
        {
            Log.log("CMPPDeliver.isValid : not a CMPP_DELIVER command !", 0x4600000000000L);
            return false;
        }
        if(destination_id != null && destination_id.length() > 21)
        {
            Log.log("CMPPDeliver.isValid : invalid destination_id !", 0x4600000000000L);
            return false;
        }
        if(service_id != null && service_id.length() > 10)
        {
            Log.log("CMPPDeliver.isValid : invalid service_id !", 0x4600000000000L);
            return false;
        }
        if(msg_fmt != 0 && msg_fmt != 3 && msg_fmt != 4 && msg_fmt != 8 && msg_fmt != 15)
            Log.log("CMPPDeliver.isValid : msg_fmt is not defined in CMPP !", 0x4600000000000L);
        if(src_terminal_id != null && src_terminal_id.length() > 21)
        {
            Log.log("CMPPDeliver.isValid : invalid src_terminal_id !", 0x4600000000000L);
            return false;
        }
        if(registered_delivery < 0 || registered_delivery > 1)
        {
            Log.log("CMPPDeliver.isValid : invalid registered_delivery !", 0x4600000000000L);
            return false;
        }
        if(registered_delivery == 1)
        {
            if(!status_report.isValid())
            {
                Log.log("CMPPDeliver.isValid : invalid status_report !", 0x4600000000000L);
                return false;
            }
        } else
        {
            if(msg_length < 0 || msg_fmt == 0 && msg_length > 160 || (msg_fmt == 0 || msg_fmt == 15) && msg_length > 140)
            {
                Log.log("CMPPDeliver.isValid : invalid msg_length !", 0x4600000000000L);
                return false;
            }
            if(msg_content != null && msg_content.length != msg_length)
            {
                Log.log("CMPPDeliver.isValid : invalid msg_content !", 0x4600000000000L);
                return false;
            }
        }
        return true;
    }

    public void dump(long lMethod)
    {
    	
    	Log.log("消息类型＝CMPP_Deliver,报文为： ", 0x4000000000000L | lMethod);
        Log.log("\tmsg_id = 0x" + Utility.toHexString(msg_id), 0x4000000000000L | lMethod);
        Log.log("\tdestination_id = \"" + destination_id + "\"", 0x4000000000000L | lMethod);
        Log.log("\tservice_id = \"" + service_id + "\"", 0x4000000000000L | lMethod);
        Log.log("\ttp_pid = 0x" + Utility.toHexString(tp_pid), 0x4000000000000L | lMethod);
        Log.log("\ttp_udhi = 0x" + Utility.toHexString(tp_udhi), 0x4000000000000L | lMethod);
        Log.log("\tmsg_fmt = " + msg_fmt + " (" + CMPPMessageFormat.toString(msg_fmt) + ")", 0x4000000000000L | lMethod);
        Log.log("\tsrc_terminal_id = \"" + src_terminal_id + "\"", 0x4000000000000L | lMethod);
        Log.log("\tregistered_delivery = " + registered_delivery + " (" + CMPPRegisteredDelivery.toString(registered_delivery) + ")", 0x4000000000000L | lMethod);
        Log.log("\tmsg_length = " + msg_length, 0x4000000000000L | lMethod);
        Log.log("\tbinary_content = 0x" + Utility.toHexString(msg_content), 0x4000000000000L | lMethod);
        if(registered_delivery == 0 && (msg_fmt == 0 || msg_fmt == 15))
            Log.log("\ttext_content = \"" + Utility.get_msg_content(msg_content) + "\"", 0x4000000000000L | lMethod);
        if(registered_delivery == 1)
            status_report.dump(lMethod);
        Log.log("\treserve = \"" + reserve + "\"", 0x2000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPDeliver.wrap : wrap elements !", 0x4800000000000L);
        dump(0x800000000000L);
        addLong(msg_id);
        addString(destination_id, 21);
        addString(service_id, 10);
        addByte(tp_pid);
        addByte(tp_udhi);
        addByte(msg_fmt);
        addString(src_terminal_id, 21);
        addByte(registered_delivery);
        addByte((byte)(msg_length & 0xff));
        if(registered_delivery == 1)
            status_report.wrap(this);
        else
            addBytes(msg_content);
        addString(reserve, 8);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPDeliver.unwrap : unwrap elements !", 0x4800000000000L);
        msg_id = getLong();
        destination_id = getString(21);
        service_id = getString(10);
        tp_pid = getByte();
        tp_udhi = getByte();
        msg_fmt = getByte();
        src_terminal_id = getString(21);
        registered_delivery = getByte();
        msg_length = getByte() & 0xff;
        msg_content = getBytes(msg_length);
        if(registered_delivery == 1)
        {
            setOffset(false, -msg_length);
            status_report.unwrap(this);
        }
        reserve = getString(8);
        dump(0x800000000000L);
    }


}