package LeadTone.Packet.SGIPPacket;

import LeadTone.*;



public class SGIPDeliver extends SGIPPacket
{
    public String user_number;
    public String sp_number;
    public byte tp_pid;
    public byte tp_udhi;
    public byte message_coding;
    public int message_length;
    public byte message_content[];
    public String reserve;

    public SGIPDeliver(int sequence_id)
    {
        super(4, sequence_id);
        user_number = null;
        sp_number = null;
        tp_pid = 0;
        tp_udhi = 0;
        message_coding = 0;
        message_length = 0;
        message_content = null;
        reserve = "19751204";
    }

    public SGIPDeliver(SGIPPacket packet)
    {
        super(packet);
        user_number = null;
        sp_number = null;
        tp_pid = 0;
        tp_udhi = 0;
        message_coding = 0;
        message_length = 0;
        message_content = null;
        reserve = "19751204";
    }

    public int checkValid()
    {
        if(command_id != 4)
        {
            Log.log("SGIPDeliver.checkValid : not a SGIP_DELIVER command !", 0x4600000000000L);
            return 7;
        }
        if(user_number != null && (user_number.length() > 21 || !user_number.startsWith("86")))
        {
            Log.log("SGIPDeliver.checkValid : invalid user_number !", 0x4600000000000L);
            return 6;
        }
        if(sp_number != null && sp_number.length() > 21)
            Log.log("SGIPDeliver.checkValid : invalid sp_number !", 0x4600000000000L);
        if(message_coding != 0 && message_coding != 3 && message_coding != 4 && message_coding != 8 && message_coding != 15)
            Log.log("SGIPDeliver.checkValid : message_coding out of range !", 0x4600000000000L);
        if(message_length < 0 || message_coding == 0 && message_length > 160 || (message_coding == 8 || message_coding == 15) && message_length > 140)
        {
            Log.log("SGIPDeliver.checkValid : invalid message_length !", 0x4600000000000L);
            return 8;
        }
        if(message_content != null && message_content.length != message_length || message_content == null && message_length != 0)
            Log.log("SGIPDeliver.checkValid : message_length not according with message_content.length !", 0x4600000000000L);
        return 0;
    }

    public boolean isValid()
    {
        if(command_id != 4)
        {
            Log.log("SGIPDeliver.isValid : not a SGIP_DELIVER command !", 0x4600000000000L);
            return false;
        }
        if(user_number != null && (user_number.length() > 21 || !user_number.startsWith("86")))
        {
            Log.log("SGIPDeliver.isValid : invalid user_number !", 0x4600000000000L);
            return false;
        }
        if(sp_number != null && sp_number.length() > 21)
        {
            Log.log("SGIPDeliver.isValid : invalid sp_number !", 0x4600000000000L);
            return false;
        }
        if(message_coding != 0 && message_coding != 3 && message_coding != 4 && message_coding != 8 && message_coding != 15)
            Log.log("SGIPDeliver.isValid : message_coding out of range !", 0x4600000000000L);
        if(message_length < 0 || message_coding == 0 && message_length > 160 || (message_coding == 8 || message_coding == 15) && message_length > 140)
        {
            Log.log("SGIPDeliver.isValid : invalid message_length !", 0x4600000000000L);
            return false;
        }
        if(message_content != null && message_content.length != message_length || message_content == null && message_length != 0)
        {
            Log.log("SGIPDeliver.isValid : message_length not according with message_content.length !", 0x4600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tuser_number = \"" + user_number + "\"", 0x4000000000000L | lMethod);
        Log.log("\tsp_number = \"" + sp_number + "\"", 0x4000000000000L | lMethod);
        Log.log("\ttp_pid = 0x" + Utility.toHexString(tp_pid), 0x4000000000000L | lMethod);
        Log.log("\ttp_udhi = 0x" + Utility.toHexString(tp_udhi), 0x4000000000000L | lMethod);
        Log.log("\tmessage_coding = " + message_coding + " (" + SGIPMessageCoding.toString(message_coding) + ")", 0x4000000000000L | lMethod);
        Log.log("\tmessage_length = " + message_length, 0x4000000000000L | lMethod);
        String temp = Utility.toHexString(message_content);
        if(temp != null)
        {
            Log.log("\tmessage_content = 0x" + temp, 0x4000000000000L | lMethod);
            if(message_coding == 0 || message_coding == 15)
                Log.log("\t            = \"" + Utility.get_msg_content(message_content) + "\"", 0x4000000000000L | lMethod);
        } else
        {
            Log.log("\tmessage_content = 0x" + temp, 0x4000000000000L | lMethod);
        }
        Log.log("\treserve = \"" + reserve + "\"", 0x4000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SGIPDeliver.wrap : wrap elements !", 0x4800000000000L);
        dump(0x800000000000L);
        addString(user_number, 21);
        addString(sp_number, 21);
        addByte(tp_pid);
        addByte(tp_udhi);
        addByte(message_coding);
        addInteger(message_length);
        addBytes(message_content);
        addString(reserve, 8);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SGIPDeliver.unwrap : unwrap elements !", 0x4800000000000L);
        user_number = getString(21);
        sp_number = getString(21);
        tp_pid = getByte();
        tp_udhi = getByte();
        message_coding = getByte();
        message_length = getInteger();
        message_content = getBytes(message_length);
        reserve = getString(8);
        dump(0x800000000000L);
    }


}