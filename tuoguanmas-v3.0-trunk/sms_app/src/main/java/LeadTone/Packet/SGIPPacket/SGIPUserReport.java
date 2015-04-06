package LeadTone.Packet.SGIPPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class SGIPUserReport extends SGIPPacket
{
    public String sp_number;
    public String user_number;
    public byte user_condition;
    public String reserve;

    public SGIPUserReport(int sequence_id)
    {
        super(17, sequence_id);
        sp_number = null;
        user_number = null;
        reserve = null;
    }

    public SGIPUserReport(SGIPPacket packet)
    {
        super(packet);
        sp_number = null;
        user_number = null;
        reserve = null;
    }

    public boolean isValid()
    {
        if(command_id != 17)
        {
            Log.log("SGIPUserReport.isValid : not a SGIP_USERRPT command !", 0x1600000000000L);
            return false;
        }
        if(sp_number != null && sp_number.length() > 21)
        {
            Log.log("SGIPUserReport.isValid : invalid sp_number !", 0x1600000000000L);
            return false;
        }
        if(user_number != null && user_number.length() > 21)
        {
            Log.log("SGIPUserReport.isValid : invalid user_number !", 0x1600000000000L);
            return false;
        }
        if(user_condition < 0 || user_condition > 2)
        {
            Log.log("SGIPUserReport.isValid : invalid user_condition !", 0x1600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tsp_number = \"" + sp_number + "\"", 0x1000000000000L | lMethod);
        Log.log("\tuser_number = \"" + user_number + "\"", 0x1000000000000L | lMethod);
        Log.log("\tuser_condition = " + user_condition + " (" + SGIPUserCondition.toString(user_condition) + ")", 0x1000000000000L | lMethod);
        Log.log("\treserve = \"" + reserve + "\"", 0x1000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SGIPUserReport.wrap : wrap elements !", 0x1800000000000L);
        dump(0x800000000000L);
        addString(sp_number, 21);
        addString(user_number, 21);
        addByte(user_condition);
        addString(reserve, 8);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SGIPUserReport.unwrap : unwrap elements !", 0x1800000000000L);
        sp_number = getString(21);
        user_number = getString(21);
        user_condition = getByte();
        reserve = getString(8);
        dump(0x800000000000L);
    }


}