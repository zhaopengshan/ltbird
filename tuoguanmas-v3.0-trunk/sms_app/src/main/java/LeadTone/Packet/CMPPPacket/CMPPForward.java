package LeadTone.Packet.CMPPPacket;

import LeadTone.*;


/**
 * 参见CMPP协议2.1对转发消息的定义
 */
public class CMPPForward extends CMPPPacket
{
    public String source_id;
    public String destination_id;
    public byte nodes_count;
    public byte msg_fwd_type;
    public long msg_id;
    public int pk_total;
    public int pk_number;
    public byte registered_delivery;
    public byte msg_level;
    public String service_id;
    public byte fee_user_type;
    public String fee_terminal_id;
    public byte tp_pid;
    public byte tp_udhi;
    public byte msg_fmt;
    public String msg_src;
    public String fee_type;
    public String fee_code;
    public String valid_time;
    public String at_time;
    public String src_terminal_id;
    public byte dest_usr_tl;
    public String dest_terminal_id[];
    public int msg_length;
    public byte msg_content[];
    public CMPPStatusReport status_report;
    public byte reserve[];

    public CMPPForward(int sequence_id)
    {
        super(9, sequence_id);
        source_id = null;
        destination_id = null;
        nodes_count = 1;
        msg_fwd_type = 0;
        msg_id = 1L;
        pk_total = 1;
        pk_number = 1;
        registered_delivery = 1;
        msg_level = 0;
        service_id = "88888888";
        fee_user_type = 1;
        fee_terminal_id = null;
        tp_pid = 0;
        tp_udhi = 0;
        msg_fmt = 0;
        msg_src = null;
        fee_type = "02";
        fee_code = null;
        valid_time = null;
        at_time = null;
        src_terminal_id = null;
        dest_usr_tl = 0;
        msg_length = 0;
        msg_content = null;
        status_report = new CMPPStatusReport();
        reserve = new byte[8];
    }

    public CMPPForward(CMPPPacket packet)
    {
        super(packet);
        source_id = null;
        destination_id = null;
        nodes_count = 1;
        msg_fwd_type = 0;
        msg_id = 1L;
        pk_total = 1;
        pk_number = 1;
        registered_delivery = 1;
        msg_level = 0;
        service_id = "88888888";
        fee_user_type = 1;
        fee_terminal_id = null;
        tp_pid = 0;
        tp_udhi = 0;
        msg_fmt = 0;
        msg_src = null;
        fee_type = "02";
        fee_code = null;
        valid_time = null;
        at_time = null;
        src_terminal_id = null;
        dest_usr_tl = 0;
        msg_length = 0;
        msg_content = null;
        status_report = new CMPPStatusReport();
        reserve = new byte[8];
    }

    public boolean isValid()
    {
        if(command_id != 9)
        {
            Log.log("CMPPForward.isValid : not a CMPP_FORWARD command !", 0x6600000000000L);
            return false;
        }
        if(source_id != null || source_id.length() > 6)
        {
            Log.log("CMPPForward.isValid : invalid source_id !", 0x6600000000000L);
            return false;
        }
        if(destination_id != null || destination_id.length() > 6)
        {
            Log.log("CMPPForward.isValid : invalid destination_id !", 0x6600000000000L);
            return false;
        }
        if(nodes_count != 1)
        {
            Log.log("CMPPForward.isValid : invalid nodes_count !", 0x6600000000000L);
            return false;
        }
        if(msg_fwd_type != 0 && msg_fwd_type != 2)
        {
            Log.log("CMPPForward.isValid : invalid msg_fwd_type !", 0x6600000000000L);
            return false;
        }
        if(pk_number <= 0 || pk_number > pk_total)
        {
            Log.log("CMPPForward.isValid : invalid pk_total or pk_number !", 0x6600000000000L);
            return false;
        }
        if(registered_delivery != 1)
        {
            Log.log("CMPPForward.isValid : invalid registered_delivery !", 0x6600000000000L);
            return false;
        }
        if(msg_level < 0 || msg_level > 9)
        {
            Log.log("CMPPForward.isValid : invalid msg_level !", 0x6600000000000L);
            return false;
        }
        if(service_id != null && service_id.length() > 10)
        {
            Log.log("CMPPForward.isValid : service_id length exceed !", 0x6600000000000L);
            return false;
        }
        if(fee_user_type != 1)
        {
            Log.log("CMPPForward.isValid : invalid fee_user_type !", 0x6600000000000L);
            return false;
        }
        if(fee_terminal_id != null && fee_terminal_id.length() > 0)
        {
            Log.log("CMPPForward.isValid : fee_terminal_id is not null !", 0x6600000000000L);
            return false;
        }
        if(fee_terminal_id != null && fee_terminal_id.length() > 21)
        {
            Log.log("CMPPForward.isValid : fee_terminal_id length exceed !", 0x6600000000000L);
            return false;
        }
        if(msg_fmt != 0 && msg_fmt != 3 && msg_fmt != 4 && msg_fmt != 8 && msg_fmt != 15)
            Log.log("CMPPForward.isValid : msg_fmt not defined in CMPP !", 0x6600000000000L);
        if(msg_src != null && msg_src.length() > 6)
        {
            Log.log("CMPPForward.isValid : msg_src length exceed !", 0x6600000000000L);
            return false;
        }
        if(fee_type == null || fee_type.length() != 2 || !fee_type.equals("02"))
        {
            Log.log("CMPPForward.isValid : invalid fee_type !", 0x6600000000000L);
            return false;
        }
        if(fee_code != null && fee_code.length() > 6)
        {
            Log.log("CMPPForward.isValid : fee_code exceed !", 0x6600000000000L);
            return false;
        }
        if(valid_time != null && valid_time.length() != 17 && valid_time.length() != 0)
        {
            Log.log("CMPPForward.isValid : invalid valid_time !", 0x6600000000000L);
            return false;
        }
        if(at_time != null && at_time.length() != 17 && at_time.length() != 0)
        {
            Log.log("CMPPForward.isValid : invalid at_time !", 0x6600000000000L);
            return false;
        }
        if(src_terminal_id != null && (src_terminal_id.length() > 21 || !src_terminal_id.startsWith("86")))
        {
            Log.log("CMPPForward.isValid : invalid src_terminal_id !", 0x6600000000000L);
            return false;
        }
        if(dest_usr_tl != 1)
        {
            Log.log("CMPPForward.isValid : invalid dest_usr_tl or too much dest_usr_tl !", 0x6600000000000L);
            return false;
        }
        if(dest_terminal_id == null || dest_terminal_id.length != dest_usr_tl)
        {
            Log.log("CMPPForward.isValid : null dest_terminal_id or not according to dest_usr_tl !", 0x6600000000000L);
            return false;
        }
        for(int i = 0; i < dest_usr_tl; i++)
            if(dest_terminal_id[i] != null && dest_terminal_id[i].length() > 21)
            {
                Log.log("CMPPForward.isValid : dest_terminal_id length exceed !", 0x6600000000000L);
                return false;
            }

        if(msg_length <= 0 || msg_fmt == 0 && msg_length > 160 || (msg_fmt == 8 || msg_fmt == 15) && msg_length > 140)
        {
            Log.log("CMPPForward.isValid : invalid msg_length !", 0x6600000000000L);
            return false;
        }
        if(msg_fwd_type == 0)
        {
            if(msg_content == null || msg_content.length != msg_length)
            {
                Log.log("CMPPForward.isValid : invalid msg_content !", 0x6600000000000L);
                return false;
            }
        } else
        if(msg_fwd_type == 2 && status_report.isValid())
        {
            Log.log("CMPPForward.isValid : invalid status_report !", 0x6600000000000L);
            return false;
        }
        return true;
    }

    public void dump(long lMethod)
    {
        Log.log("\tsource_id = \"" + source_id + "\"", 0x6000000000000L | lMethod);
        Log.log("\tdestination_id = \"" + destination_id + "\"", 0x6000000000000L | lMethod);
        Log.log("\tnotes_count = " + nodes_count, 0x6000000000000L | lMethod);
        Log.log("\tmsg_fwd_type = " + msg_fwd_type + " (" + CMPPForwardType.toString(msg_fwd_type) + ")", 0x6000000000000L | lMethod);
        Log.log("\tmsg_id = 0x" + Utility.toHexString(msg_id), 0x6000000000000L | lMethod);
        Log.log("\tpk_total = " + pk_total, 0x6000000000000L | lMethod);
        Log.log("\tpk_number = " + pk_number, 0x6000000000000L | lMethod);
        Log.log("\tregistered_delivery = " + registered_delivery + " (" + CMPPRegisteredDelivery.toString(registered_delivery) + ")", 0x6000000000000L | lMethod);
        Log.log("\tmsg_level = " + msg_level, 0x6000000000000L | lMethod);
        Log.log("\tservice_id = \"" + service_id + "\"", 0x6000000000000L | lMethod);
        Log.log("\tfee_user_type = 0x" + Utility.toHexString(fee_user_type) + " (" + CMPPFeeUserType.toString(fee_user_type) + ")", 0x6000000000000L | lMethod);
        Log.log("\tfee_terminal_id = \"" + fee_terminal_id + "\"", 0x6000000000000L | lMethod);
        Log.log("\ttp_pid = 0x" + Utility.toHexString(tp_pid), 0x6000000000000L | lMethod);
        Log.log("\ttp_udhi = 0x" + Utility.toHexString(tp_udhi), 0x6000000000000L | lMethod);
        Log.log("\tmsg_fmt = " + msg_fmt + " (" + CMPPMessageFormat.toString(msg_fmt) + ")", 0x6000000000000L | lMethod);
        Log.log("\tmsg_src = \"" + msg_src + "\"", 0x6000000000000L | lMethod);
        Log.log("\tfee_type = \"" + fee_type + "\"" + " (" + CMPPFeeType.toString(fee_type) + ")", 0x6000000000000L | lMethod);
        Log.log("\tfee_code = \"" + fee_code + "\"", 0x6000000000000L | lMethod);
        Log.log("\tvalid_time = \"" + valid_time + "\"", 0x6000000000000L | lMethod);
        Log.log("\tat_time = \"" + at_time + "\"", 0x6000000000000L | lMethod);
        Log.log("\tsrc_terminal_id = \"" + src_terminal_id + "\"", 0x6000000000000L | lMethod);
        Log.log("\tdest_usr_tl = " + dest_usr_tl, 0x6000000000000L | lMethod);
        for(int i = 0; i < dest_usr_tl; i++)
            Log.log("\tdest_terminal_id[" + i + "] = \"" + dest_terminal_id[i] + "\"", 0x6000000000000L | lMethod);

        Log.log("\tmsg_length = " + msg_length, 0x6000000000000L | lMethod);
        if(msg_fwd_type == 0)
        {
            Log.log("\tmsg_content = 0x" + Utility.toHexString(msg_content), 0x6000000000000L | lMethod);
            if(msg_fmt == 0 || msg_fmt == 15)
                Log.log("\t            = \"" + Utility.get_msg_content(msg_content) + "\"", 0x6000000000000L | lMethod);
        } else
        if(msg_fwd_type == 2)
            status_report.dump(lMethod);
        Log.log("\treserve = 0x" + Utility.toHexString(reserve), 0x6000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPForward.wrap : wrap elements !", 0x6800000000000L);
        dump(0x6800000000000L);
        addString(6, source_id);
        addString(6, destination_id);
        addByte(nodes_count);
        addByte(msg_fwd_type);
        addLong(msg_id);
        addByte((byte)(pk_total & 0xff));
        addByte((byte)(pk_number & 0xff));
        addByte(registered_delivery);
        addByte(msg_level);
        addString(service_id, 10);
        addByte(fee_user_type);
        addString(fee_terminal_id, 21);
        addByte(tp_pid);
        addByte(tp_udhi);
        addByte(msg_fmt);
        addString(msg_src, 6);
        addString(2, fee_type);
        addString(6, fee_code);
        addString(valid_time, 17);
        addString(at_time, 17);
        addString(src_terminal_id, 21);
        addByte(dest_usr_tl);
        for(int i = dest_usr_tl; i > 0; i--)
            addString(dest_terminal_id[i - 1], 21);

        addByte((byte)(msg_length & 0xff));
        if(msg_fwd_type == 0)
            addBytes(msg_content);
        else
        if(msg_fwd_type == 2)
            status_report.wrap(this);
        addBytes(reserve);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPForward.unwrap : unwrap elements !", 0x6800000000000L);
        source_id = getString(6);
        destination_id = getString(6);
        nodes_count = getByte();
        msg_fwd_type = getByte();
        msg_id = getLong();
        pk_total = getByte() & 0xff;
        pk_number = getByte() & 0xff;
        registered_delivery = getByte();
        msg_level = getByte();
        service_id = getString(10);
        fee_user_type = getByte();
        fee_terminal_id = getString(21);
        tp_pid = getByte();
        tp_udhi = getByte();
        msg_fmt = getByte();
        msg_src = getString(6);
        fee_type = getString(2);
        fee_code = getString(6);
        valid_time = getString(17);
        at_time = getString(17);
        src_terminal_id = getString(21);
        dest_usr_tl = getByte();
        dest_terminal_id = new String[dest_usr_tl];
        for(int i = 0; i < dest_usr_tl; i++)
            dest_terminal_id[i] = getString(21);

        if(msg_fwd_type == 0)
        {
            msg_length = getByte();
            msg_content = getBytes(msg_length);
        } else
        if(msg_fwd_type == 2)
        {
            msg_length = 60;
            status_report.unwrap(this);
        }
        reserve = getBytes(8);
        dump(0x6800000000000L);
    }


}