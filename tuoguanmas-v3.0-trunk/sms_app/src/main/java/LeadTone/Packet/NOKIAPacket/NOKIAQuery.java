package LeadTone.Packet.NOKIAPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPQueryType;
import java.util.Date;



public class NOKIAQuery extends NOKIAPacket
{

    public String query_time;
    public byte query_type;
    public String query_code;
    
    public NOKIAQuery(int sequence_id)
    {
        super(6, sequence_id);
        query_time = null;
        query_type = 0;
        query_code = null;
    }

    public NOKIAQuery(NOKIAPacket packet)
    {
        super(packet);
        query_time = null;
        query_type = 0;
        query_code = null;
    }

    public void setTime()
    {
        Date currDate = new Date(System.currentTimeMillis());
        query_time = currDate.toString().substring(0, 4) + currDate.toString().substring(5, 7) + currDate.toString().substring(8, 10);
    }

    public boolean isValid()
    {
        if(command_id != 6)
        {
            Log.log("NOKIAQuery.isValid : not a CMPP_QUERY command !", 0x8600000000000L);
            return false;
        }
        if(query_time == null || query_time.length() != 8)
        {
            Log.log("NOKIAQuery.isValid : invalid query_time !", 0x8600000000000L);
            return false;
        }
        if(query_type != 0 && query_type != 1)
        {
            Log.log("NOKIAQuery.isValid : invalid query_type !", 0x8600000000000L);
            return false;
        }
        if(query_code != null && query_code.length() > 10)
        {
            Log.log("NOKIAQuery.isValid : invalid query_code !", 0x8600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tquery_time = \"" + query_time + "\"", 0x8000000000000L | lMethod);
        Log.log("\tquery_type = " + query_type + " (" + CMPPQueryType.toString(query_type) + ")", 0x8000000000000L | lMethod);
        Log.log("\tquery_code = \"" + query_code + "\"", 0x8000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("NOKIAQuery.wrap : wrap elements !", 0x8800000000000L);
        dump(0x8800000000000L);
        addCString(query_time);
        addByte(query_type);
        addCString(query_code);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("NOKIAQuery.unwrap : unwrap elements !", 0x8800000000000L);
        query_time = getCString();
        query_type = getByte();
        query_code = getCString();
        dump(0x8800000000000L);
    }


}