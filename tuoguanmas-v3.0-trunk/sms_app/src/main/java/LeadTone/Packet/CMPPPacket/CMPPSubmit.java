package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Utility;


/**
 * 
 */
public class CMPPSubmit extends CMPPPacket
{
    public long msg_id;
    public byte pk_total;
    public byte pk_number;
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
    public String reserve;
    public String given_value;
    public byte agent_flag;
    public byte mo_relate_to_mt_flag;
    public String apid;
    public String srcapid;
    public String masmsgid;

    public CMPPSubmit(int sequence_id)
    {
        super(4, sequence_id);
        msg_id = 1L;
        pk_total = 1;
        pk_number = 1;
        registered_delivery = 0;
        msg_level = 0;
        service_id = "888888";
        fee_user_type = 1;
        fee_terminal_id = null;
        tp_pid = 0;
        tp_udhi = 0;
        msg_fmt = 0;
        msg_src = "888888";
        fee_type = "01";
        fee_code = "000000";
        valid_time = null;
        at_time = null;
        src_terminal_id = null;
        dest_usr_tl = 0;
        msg_length = 0;
        msg_content = null;
        reserve = "19811028";
        given_value = null;
        agent_flag = 0;
        mo_relate_to_mt_flag = 2;
        apid = null;
        srcapid = null;
        masmsgid = null;
    }

    public CMPPSubmit(CMPPPacket packet)
    {
        super(packet);
        msg_id = 1L;
        pk_total = 1;
        pk_number = 1;
        registered_delivery = 0;
        msg_level = 0;
        service_id = "888888";
        fee_user_type = 1;
        fee_terminal_id = null;
        tp_pid = 0;
        tp_udhi = 0;
        msg_fmt = 0;
        msg_src = "888888";
        fee_type = "01";
        fee_code = "000000";
        valid_time = null;
        at_time = null;
        src_terminal_id = null;
        dest_usr_tl = 0;
        msg_length = 0;
        msg_content = null;
        reserve = "19811028";
        given_value = null;
        agent_flag = 0;
        mo_relate_to_mt_flag = 2;
        apid = null;
        srcapid = null;
        masmsgid = null;
    }

    public boolean isValid()
    {
        if(command_id != 4)
        {
            Log.log("CMPPSubmit.isValid : not a CMPP_SUBMIT command !", 0x2600000000000L);
            return false;
        }
        if(pk_number <= 0 || pk_number > pk_total)
        {
            Log.log("CMPPSubmit.isValid : invalid pk_total or pk_number !", 0x2600000000000L);
            return false;
        }
        if(registered_delivery < 0 || registered_delivery > 2)
        {
            Log.log("CMPPSubmit.isValid : invalid registered_delivery !", 0x2600000000000L);
            return false;
        }
        if(msg_level < 0 || msg_level > 9)
        {
            Log.log("CMPPSubmit.isValid : invalid msg_level !", 0x2600000000000L);
            return false;
        }
        if(service_id != null && service_id.length() > 10)
        {
            Log.log("CMPPSubmit.isValid : service_id length exceed !", 0x2600000000000L);
            return false;
        }
        if(fee_user_type < 0 || fee_user_type > 3)
        {
            Log.log("CMPPSubmit.isValid : invalid fee_user_type !", 0x2600000000000L);
            return false;
        }
        if((fee_user_type == 1 || fee_user_type == 2) && fee_terminal_id != null && fee_terminal_id.length() > 0)
        {
            Log.log("CMPPSubmit.isValid : fee_terminal_id is not null !", 0x2600000000000L);
            return false;
        }
        if(fee_user_type == 3 && (fee_terminal_id == null || fee_terminal_id.length() == 0))
        {
            Log.log("CMPPSubmit.isValid : null fee_terminal_id !", 0x2600000000000L);
            return false;
        }
        if(fee_terminal_id != null && fee_terminal_id.length() > 21)
        {
            Log.log("CMPPSubmit.isValid : fee_terminal_id length exceed !", 0x2600000000000L);
            return false;
        }
        if(msg_fmt != 0 && msg_fmt != 3 && msg_fmt != 4 && msg_fmt != 8 && msg_fmt != 15 && msg_fmt != 64)
            Log.log("CMPPSubmit.isValid : msg_fmt not defined in CMPP !", 0x2600000000000L);
        if(msg_src != null && msg_src.length() > 6)
            Log.log("CMPPSubmit.isValid : msg_src length exceed !", 0x2600000000000L);
        if(fee_type == null || fee_type.length() != 2)
        {
            Log.log("CMPPSubmit.isValid : invalid fee_type !", 0x2600000000000L);
            return false;
        }
        if(fee_code != null && fee_code.length() > 6)
        {
            Log.log("CMPPSubmit.isValid : fee_code exceed !", 0x2600000000000L);
            return false;
        }
        if(valid_time != null && valid_time.length() != 16 && valid_time.length() != 0)
        {
            Log.log("CMPPSubmit.isValid : invalid valid_time !", 0x2600000000000L);
            return false;
        }
        if(at_time != null && at_time.length() != 16 && at_time.length() != 0)
        {
            Log.log("CMPPSubmit.isValid : invalid at_time !", 0x2600000000000L);
            return false;
        }
        if(src_terminal_id != null && src_terminal_id.length() > 21)
        {
            Log.log("CMPPSubmit.isValid : src_terminal_id length exceed !", 0x2600000000000L);
            return false;
        }
        if(dest_usr_tl <= 0 || dest_usr_tl >= 100)
            Log.log("CMPPSubmit.isValid : invalid dest_usr_tl or too much dest_usr_tl !", 0x2600000000000L);
        if(dest_terminal_id == null || dest_terminal_id.length != dest_usr_tl)
        {
            Log.log("CMPPSubmit.isValid : null dest_terminal_id or not according to dest_usr_tl !", 0x2600000000000L);
            return false;
        }
        for(int i = 0; i < dest_usr_tl; i++)
            if(dest_terminal_id[i] != null && dest_terminal_id[i].length() > 21)
            {
                Log.log("CMPPSubmit.isValid : dest_terminal_id length exceed !", 0x2600000000000L);
                return false;
            }

        if(msg_length <= 0 || msg_fmt == 0 && msg_length > 160 || (msg_fmt == 8 || msg_fmt == 15) && msg_length > 140)
        {
            Log.log("CMPPSubmit.isValid : invalid msg_length !", 0x2600000000000L);
            return false;
        }
        if(msg_content == null || msg_content.length != msg_length)
        {
            Log.log("CMPPSubmit.isValid : invalid msg_content !", 0x2600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
    
    

    public void dump(long lMethod)
    {
    	// 常军根据3.0规范输出日志
    	
		Log.log("消息类型=CMPP_Submit。报文为： ", 0x2000000000000L | lMethod);
        Log.log("\tmsg_id = 0x" + Utility.toHexString(msg_id), 0x2000000000000L | lMethod);
        Log.log("\tpk_total = " + pk_total, 0x2000000000000L | lMethod);
        Log.log("\tpk_number = " + pk_number, 0x2000000000000L | lMethod);
        Log.log("\tregistered_delivery = " + registered_delivery + " (" + CMPPRegisteredDelivery.toString(registered_delivery) + ")", 0x2000000000000L | lMethod);
        Log.log("\tmsg_level = " + msg_level, 0x2000000000000L | lMethod);
        Log.log("\tservice_id = \"" + service_id + "\"", 0x2000000000000L | lMethod);
        Log.log("\tfee_user_type = 0x" + Utility.toHexString(fee_user_type) + " (" + CMPPFeeUserType.toString(fee_user_type) + ")", 0x2000000000000L | lMethod);
        Log.log("\tfee_terminal_id = \"" + fee_terminal_id + "\"", 0x2000000000000L | lMethod);
        Log.log("\ttp_pid = 0x" + Utility.toHexString(tp_pid), 0x2000000000000L | lMethod);
        Log.log("\ttp_udhi = 0x" + Utility.toHexString(tp_udhi), 0x2000000000000L | lMethod);
        Log.log("\tmsg_fmt = " + msg_fmt + " (" + CMPPMessageFormat.toString(msg_fmt) + ")", 0x2000000000000L | lMethod);
        Log.log("\tmsg_src = \"" + msg_src + "\"", 0x2000000000000L | lMethod);
        Log.log("\tfee_type = \"" + fee_type + "\"" + " (" + CMPPFeeType.toString(fee_type) + ")", 0x2000000000000L | lMethod);
        Log.log("\tfee_code = \"" + fee_code + "\"", 0x2000000000000L | lMethod);
        Log.log("\tvalid_time = \"" + valid_time + "\"", 0x2000000000000L | lMethod);
        Log.log("\tat_time = \"" + at_time + "\"", 0x2000000000000L | lMethod);
        Log.log("\tsrc_terminal_id = \"" +  src_terminal_id + "\"", 0x2000000000000L | lMethod);
        Log.log("\tdest_usr_tl = " + dest_usr_tl, 0x2000000000000L | lMethod);
        for(int i = 0; i < dest_usr_tl; i++)
            Log.log("\tdest_terminal_id[" + i + "] = \"" + dest_terminal_id[i] + "\"", 0x2000000000000L | lMethod);

        Log.log("\tmsg_length = " + msg_length, 0x2000000000000L | lMethod);
        Log.log("\tmsg_content = 0x" + Utility.toHexString(msg_content), 0x2000000000000L | lMethod);
        if(msg_fmt == 0 || msg_fmt == 15)
            Log.log("\t            = \"" + Utility.get_msg_content(msg_content) + "\"", 0x2000000000000L | lMethod);
        Log.log("\treserve = \"" + reserve + "\"", 0x2000000000000L | lMethod);
    }

    public void wrap(String service_code)
        throws BufferException
    {
        Log.log("CMPPSubmit.wrap : wrap elements !", 0x2800000000000L);
        //src_terminal_id = service_code + src_terminal_id; //modify for new p2 ,get service_code from database,not from config file
        dump(0x2800000000000L);
        addLong(msg_id);
        addByte(pk_total);
        addByte(pk_number);
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
        addBytes(msg_content);
        addString(reserve, 8);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPSubmit.unwrap : unwrap elements !", 0x2800000000000L);
        msg_id = getLong();
        pk_total = getByte();
        pk_number = getByte();
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

        msg_length = getByte();
        msg_content = getBytes(msg_length);
        reserve = getString(8);
        dump(0x2800000000000L);
    }


}