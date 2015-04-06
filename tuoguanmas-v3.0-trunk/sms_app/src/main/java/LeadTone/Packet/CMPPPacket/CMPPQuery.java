package LeadTone.Packet.CMPPPacket;

import LeadTone.*;

/**
 * 参见CMPP协议2.1对查询消息的定义
 */
public class CMPPQuery extends CMPPPacket
{
    public String query_time;
    public byte query_type;
    public String query_code;
    private String reserve;

    public CMPPQuery(int sequence_id)
    {
        super(6, sequence_id);
        query_time = null;
        query_type = 0;
        query_code = null;
        reserve = "19751204";
    }

    public CMPPQuery(CMPPPacket packet)
    {
        super(packet);
        query_time = null;
        query_type = 0;
        query_code = null;
        reserve = "19751204";
    }

    public void setTime()
    {
        String strDate = Utility.toTimeString(new LeadToneDate());
        query_time = strDate.substring(0, 8);
    }

    public boolean isValid()
    {
        if(command_id != 6)
        {
            Log.log("CMPPQuery.isValid : not a CMPP_QUERY command !", 0x8600000000000L);
            return false;
        }
        if(query_time == null || query_time.length() != 8)
        {
            Log.log("CMPPQuery.isValid : invalid query_time !", 0x8600000000000L);
            return false;
        }
        if(query_type != 0 && query_type != 1)
        {
            Log.log("CMPPQuery.isValid : invalid query_type !", 0x8600000000000L);
            return false;
        }
        if(query_code != null && query_code.length() > 10)
        {
            Log.log("CMPPQuery.isValid : invalid query_code !", 0x8600000000000L);
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
        Log.log("\treserved = \"" + reserve + "\"", 0x8000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPQuery.wrap : wrap elements !", 0x8800000000000L);
        dump(0x800000000000L);
        addString(query_time);
        addByte(query_type);
        addString(query_code, 10);
        addString(reserve);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPQuery.unwrap : unwrap elements !", 0x8800000000000L);
        query_time = getString(8);
        query_type = getByte();
        query_code = getString(10);
        reserve = getString(8);
        dump(0x800000000000L);
    }


}