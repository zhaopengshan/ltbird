package LeadTone.Packet.SGIPPacket;

import LeadTone.*;


public class SGIPSubmit extends SGIPPacket
{
    public String sp_number;
    public String charge_number;
    public int user_count;
    public String user_number[];
    public String corporation_id;
    public String service_type;
    public byte fee_type;
    public String fee_value;
    public String given_value;
    public byte agent_flag;
    public byte mo_relate_to_mt_flag;
    public byte priority;
    public String expire_time;
    public String schedule_time;
    public byte report_flag;
    public byte tp_pid;
    public byte tp_udhi;
    public byte message_coding;
    public byte message_type;
    public int message_length;
    public byte message_content[];
    public String reserve;

    public SGIPSubmit(int sequence_id)
    {
        super(3, sequence_id);
        sp_number = null;
        charge_number = null;
        user_count = 0;
        user_number = null;
        corporation_id = null;
        service_type = null;
        fee_type = 0;
        fee_value = null;
        given_value = null;
        agent_flag = 0;
        mo_relate_to_mt_flag = 2;
        priority = 0;
        expire_time = null;
        schedule_time = null;
        report_flag = 2;
        tp_pid = 0;
        tp_udhi = 0;
        message_coding = 0;
        message_type = 0;
        message_length = 0;
        message_content = null;
        reserve = "19751204";
    }

    public SGIPSubmit(SGIPPacket packet)
    {
        super(packet);
        sp_number = null;
        charge_number = null;
        user_count = 0;
        user_number = null;
        corporation_id = null;
        service_type = null;
        fee_type = 0;
        fee_value = null;
        given_value = null;
        agent_flag = 0;
        mo_relate_to_mt_flag = 2;
        priority = 0;
        expire_time = null;
        schedule_time = null;
        report_flag = 2;
        tp_pid = 0;
        tp_udhi = 0;
        message_coding = 0;
        message_type = 0;
        message_length = 0;
        message_content = null;
        reserve = "19751204";
    }

    public boolean isValid()
    {
        if(command_id != 3)
        {
            Log.log("SGIPSubmit.isValid : not a SGIP_SUBMIT command !", 0x2600000000000L);
            return false;
        }
        if(sp_number != null && sp_number.length() > 21)
        {
            Log.log("SGIPSubmit.isValid : invalid sp_number !", 0x2600000000000L);
            return false;
        }
        if(charge_number != null && charge_number.length() > 21)
        {
            Log.log("SGIPSubmit.isValid : invalid charge_number !", 0x2600000000000L);
            return false;
        }
        if(user_count < 0 || user_count > 100)
        {
            Log.log("SGIPSubmit.isValid : invalid user_count !", 0x2600000000000L);
            return false;
        }
        if(user_number != null && user_number.length != user_count || user_number == null && user_number.length != 0)
        {
            Log.log("SGIPSubmit.isValid : user_count not according with user_number.length !", 0x2600000000000L);
            return false;
        }
        for(int i = 0; i < user_count; i++)
            if(user_number[i] != null && (user_number[i].length() > 21 || !user_number[i].startsWith("86")))
            {
                Log.log("SGIPSubmit.isValid : invalid user_number !", 0x2600000000000L);
                return false;
            }

        if(corporation_id != null && corporation_id.length() > 5)
        {
            Log.log("SGIPSubmit.isValid : invalid corporation_id !", 0x2600000000000L);
            return false;
        }
        if(service_type != null && service_type.length() > 10)
        {
            Log.log("SGIPSubmit.isValid : invalid service_type !", 0x2600000000000L);
            return false;
        }
        if(fee_type < 0 || fee_type > 4)
        {
            Log.log("SGIPSubmit.isValid : invalid fee_type !", 0x2600000000000L);
            return false;
        }
        if(fee_value != null && fee_value.length() > 6)
        {
            Log.log("SGIPSubmit.isValid : invalid fee_value !", 0x2600000000000L);
            return false;
        }
        if(given_value != null && given_value.length() > 6)
        {
            Log.log("SGIPSubmit.isValid : invalid given_value !", 0x2600000000000L);
            return false;
        }
        if(agent_flag < 0 || agent_flag > 1)
        {
            Log.log("SGIPSubmit.isValid : invalid given_value !", 0x2600000000000L);
            return false;
        }
        if(mo_relate_to_mt_flag < 0 || mo_relate_to_mt_flag > 3)
        {
            Log.log("SGIPSubmit.isValid : invalid mo_relate_to_mt_flag !", 0x2600000000000L);
            return false;
        }
        if(priority < 0 || priority > 9)
            Log.log("SGIPSubmit.isValid : priority out of range !", 0x2600000000000L);
        if(expire_time != null && expire_time.length() != 16 && expire_time.length() != 0)
        {
            Log.log("SGIPSubmit.isValid : invalid expire_time !", 0x2600000000000L);
            return false;
        }
        if(schedule_time != null && schedule_time.length() != 16 && schedule_time.length() != 0)
        {
            Log.log("SGIPSubmit.isValid : invalid schedule_time !", 0x2600000000000L);
            return false;
        }
        if(report_flag < 0 || report_flag > 3)
        {
            Log.log("SGIPSubmit.isValid : invalid report_flag !", 0x2600000000000L);
            return false;
        }
        if(message_coding != 0 && message_coding != 3 && message_coding != 4 && message_coding != 8 && message_coding != 15)
            Log.log("SGIPSubmit.isValid : message_coding out of range !", 0x2600000000000L);
        if(message_length <= 0 || message_coding == 0 && message_length > 160 || (message_coding == 8 || message_coding == 15) && message_length > 140)
        {
            Log.log("SGIPSubmit.isValid : invalid message_length !", 0x2600000000000L);
            return false;
        }
        if(message_content != null && message_content.length != message_length || message_content == null && message_length != 0)
        {
            Log.log("SGIPSubmit.isValid : message_length not according with message_content.length !", 0x2600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tsp_number = \"" + sp_number + "\"", 0x2000000000000L | lMethod);
        Log.log("\tcharge_number = \"" + charge_number + "\"", 0x2000000000000L | lMethod);
        Log.log("\tuser_count = " + user_count, 0x2000000000000L | lMethod);
        for(int i = 0; i < user_count; i++)
            Log.log("\tuser_number[" + i + "] = \"" + user_number[i] + "\"", 0x2000000000000L | lMethod);

        Log.log("\tcorporation_id = \"" + corporation_id + "\"", 0x2000000000000L | lMethod);
        Log.log("\tservice_type = \"" + service_type + "\"", 0x2000000000000L | lMethod);
        Log.log("\tfee_type = " + fee_type + " (" + SGIPFeeType.toString(fee_type) + ")", 0x2000000000000L | lMethod);
        Log.log("\tfee_value = \"" + fee_value + "\"", 0x2000000000000L | lMethod);
        Log.log("\tgiven_value = \"" + given_value + "\"", 0x2000000000000L | lMethod);
        Log.log("\tagent_flag = " + agent_flag + " (" + SGIPAgentFlag.toString(agent_flag) + ")", 0x2000000000000L | lMethod);
        Log.log("\tmo_relate_to_mt_flag = " + mo_relate_to_mt_flag + " (" + SGIPMORelateToMTFlag.toString(mo_relate_to_mt_flag) + ")", 0x2000000000000L | lMethod);
        Log.log("\tpriority = " + priority, 0x2000000000000L | lMethod);
        Log.log("\texpire_time = \"" + expire_time + "\"", 0x2000000000000L | lMethod);
        Log.log("\tschedule_time = \"" + schedule_time + "\"", 0x2000000000000L | lMethod);
        Log.log("\treport_flag = " + report_flag + " (" + SGIPReportFlag.toString(report_flag) + ")", 0x2000000000000L | lMethod);
        Log.log("\ttp_pid = " + tp_pid, 0x2000000000000L | lMethod);
        Log.log("\ttp_udhi = " + tp_udhi, 0x2000000000000L | lMethod);
        Log.log("\tmessage_coding = " + message_coding + " (" + SGIPMessageCoding.toString(message_coding) + ")", 0x2000000000000L | lMethod);
        Log.log("\tmessage_type = " + message_type + " (" + SGIPMessageType.toString(message_type) + ")", 0x2000000000000L | lMethod);
        Log.log("\tmessage_length = " + message_length, 0x2000000000000L | lMethod);
        Log.log("\tmessage_content = 0x" + Utility.toHexString(message_content), 0x2000000000000L | lMethod);
        if(message_coding == 0 || message_coding == 15)
            Log.log("\t            = \"" + Utility.get_msg_content(message_content) + "\"", 0x2000000000000L | lMethod);
        Log.log("\treserve = \"" + reserve + "\"", 0x2000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SGIPSubmit.wrap : wrap elements !", 0x2800000000000L);
        dump(0x800000000000L);
        addString(sp_number, 21);
        addString(charge_number, 21);
        addByte((byte)user_count);
        for(int i = 0; i < user_count; i++)
            addString(user_number[i], 21);

        addString(corporation_id, 5);
        addString(service_type, 10);
        addByte(fee_type);
        addString(fee_value, 6);
        addString(given_value, 6);
        addByte(agent_flag);
        addByte(mo_relate_to_mt_flag);
        addByte(priority);
        addString(expire_time, 16);
        addString(schedule_time, 16);
        addByte(report_flag);
        addByte(tp_pid);
        addByte(tp_udhi);
        addByte(message_coding);
        addByte(message_type);
        addInteger(message_length);
        addBytes(message_content);
        addString(reserve, 8);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SGIPSubmit.unwrap : unwrap elements !", 0x2800000000000L);
        sp_number = getString(21);
        charge_number = getString(21);
        user_count = getByte() & 0xff;
        user_number = new String[user_count];
        for(int i = 0; i < user_count; i++)
            user_number[i] = getString(21);

        corporation_id = getString(5);
        service_type = getString(10);
        fee_type = getByte();
        fee_value = getString(6);
        given_value = getString(6);
        agent_flag = getByte();
        mo_relate_to_mt_flag = getByte();
        priority = getByte();
        expire_time = getString(16);
        schedule_time = getString(16);
        report_flag = getByte();
        tp_pid = getByte();
        tp_udhi = getByte();
        message_coding = getByte();
        message_type = getByte();
        message_length = getInteger();
        message_content = getBytes(message_length);
        reserve = getString(8);
    }


}