package LeadTone.Packet.SMPPPacket;

import LeadTone.*;


public class SMPPQuerySMResponse extends SMPPCommandSMResponse
{
    public String message_id;
    public String final_date;
    public byte message_state;
    public byte error_code;

    public SMPPQuerySMResponse()
    {
        message_id = null;
        final_date = null;
        message_state = 0;
        error_code = 0;
    }

    public SMPPQuerySMResponse(int sequence_id)
    {
        super(0x80000003, sequence_id);
        message_id = null;
        final_date = null;
        message_state = 0;
        error_code = 0;
    }

    public SMPPQuerySMResponse(SMPPPacket packet)
    {
        super(packet);
        message_id = null;
        final_date = null;
        message_state = 0;
        error_code = 0;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000003)
        {
            Log.log("SMPPQuerySMResponse.isValid : not a SMPP_QUERY_SM_RESPONSE command !", 0x18600000000000L);
            return false;
        }
        if(message_id != null && message_id.length() > 64)
        {
            Log.log("SMPPQuerySMResponse.isValid : invalid message_id !", 0x18600000000000L);
            return false;
        }
        if(final_date != null && final_date.length() != 16)
        {
            Log.log("SMPPQuerySMResponse.isValid : invalid final_date !", 0x18600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tmessage_id = \"" + message_id + "\"", 0x18000000000000L | lMethod);
        Log.log("\tfinal_date = \"" + final_date + "\"", 0x18000000000000L | lMethod);
        Log.log("\tmessage_state = 0x" + Utility.toHexString(message_state) + " (" + MessageState.toString(message_state) + ")", 0x18000000000000L | lMethod);
        Log.log("\terror_code = 0x" + Utility.toHexString(error_code), 0x18000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPQuerySM.wrap : wrap elements !", 0x18800000000000L);
        dump(0x800000000000L);
        addCString(message_id);
        addCString(final_date);
        addByte(message_state);
        addByte(error_code);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPQuerySM.unwrap : unwrap elements !", 0x18800000000000L);
        message_id = getCString();
        final_date = getCString();
        message_state = getByte();
        error_code = getByte();
        dump(0x800000000000L);
    }


}