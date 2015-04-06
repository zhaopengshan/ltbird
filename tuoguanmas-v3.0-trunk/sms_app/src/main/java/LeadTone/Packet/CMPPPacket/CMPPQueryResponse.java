package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;


/**
 * 参见CMPP协议2.1对查询消息回复的定义
 */
public class CMPPQueryResponse extends CMPPPacket
{
    public String query_time;
    public byte query_type;
    public String query_code;
    public int MT_TLMsg;
    public int MT_TLUsr;
    public int MT_Scs;
    public int MT_WT;
    public int MT_FL;
    public int MO_Scs;
    public int MO_WT;
    public int MO_FL;

    public CMPPQueryResponse(int sequence_id)
    {
        super(0x80000006, sequence_id);
        query_time = null;
        query_type = 0;
        query_code = null;
        MT_TLMsg = 0;
        MT_TLUsr = 0;
        MT_Scs = 0;
        MT_WT = 0;
        MT_FL = 0;
        MO_Scs = 0;
        MO_WT = 0;
        MO_FL = 0;
    }

    public CMPPQueryResponse(CMPPPacket packet)
    {
        super(packet);
        query_time = null;
        query_type = 0;
        query_code = null;
        MT_TLMsg = 0;
        MT_TLUsr = 0;
        MT_Scs = 0;
        MT_WT = 0;
        MT_FL = 0;
        MO_Scs = 0;
        MO_WT = 0;
        MO_FL = 0;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000006)
        {
            Log.log("CMPPQueryResponse.isValid : not a CMPP_QUERY_RESPONSE command !", 0x8600000000000L);
            return false;
        }
        if(query_time == null || query_time.length() != 8)
        {
            Log.log("CMPPQueryResponse.isValid : invalid query_time !", 0x8600000000000L);
            return false;
        }
        if(query_type != 0 && query_type != 1)
        {
            Log.log("CMPPQueryResponse.isValid : invalid query_type !", 0x8600000000000L);
            return false;
        }
        if(query_code != null && query_code.length() > 10)
        {
            Log.log("CMPPQueryResponse.isValid : invalid query_code !", 0x8600000000000L);
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
        Log.log("\tMT_TLMsg = " + MT_TLMsg, 0x8000000000000L | lMethod);
        Log.log("\tMO_TLUsr = " + MT_TLUsr, 0x8000000000000L | lMethod);
        Log.log("\tMT_Scs = " + MT_Scs, 0x8000000000000L | lMethod);
        Log.log("\tMT_WT = " + MT_WT, 0x8000000000000L | lMethod);
        Log.log("\tMT_FL = " + MT_FL, 0x8000000000000L | lMethod);
        Log.log("\tMO_Scs = " + MO_Scs, 0x8000000000000L | lMethod);
        Log.log("\tMO_WT = " + MO_WT, 0x8000000000000L | lMethod);
        Log.log("\tMO_FL = " + MO_FL, 0x8000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPQueryResponse.wrap : wrap elements !", 0x8800000000000L);
        dump(0x800000000000L);
        addString(query_time);
        addByte(query_type);
        addString(query_code, 10);
        addInteger(MT_TLMsg);
        addInteger(MT_TLUsr);
        addInteger(MT_Scs);
        addInteger(MT_WT);
        addInteger(MT_FL);
        addInteger(MO_Scs);
        addInteger(MO_WT);
        addInteger(MO_FL);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPQueryResponse.unwrap : unwrap elements !", 0x8800000000000L);
        query_time = getString(8);
        query_type = getByte();
        query_code = getString(10);
        MT_TLMsg = getInteger();
        MT_TLUsr = getInteger();
        MT_Scs = getInteger();
        MT_WT = getInteger();
        MT_FL = getInteger();
        MO_Scs = getInteger();
        MO_WT = getInteger();
        MO_FL = getInteger();
        dump(0x800000000000L);
    }


}