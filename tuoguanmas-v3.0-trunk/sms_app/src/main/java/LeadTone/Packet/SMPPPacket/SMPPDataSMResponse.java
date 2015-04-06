package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class SMPPDataSMResponse extends SMPPPacket
{
     public String message_id;

    public SMPPDataSMResponse()
    {
        message_id = null;
    }

    public SMPPDataSMResponse(int sequence_id)
    {
        super(0x80000103, sequence_id);
        message_id = null;
    }

    public SMPPDataSMResponse(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
        message_id = null;
    }

    public SMPPDataSMResponse(SMPPPacket packet)
    {
        super(packet);
        message_id = null;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000103)
        {
            Log.log("SMPPDataSMResponse.isValid : not a SMPP_DATA_SM_RESPONSE command !", 0x6600000000000L);
            return false;
        }
        if(message_id != null && message_id.length() > 64)
        {
            Log.log("SMPPDataSMResponse.isValid : invalid message_id !", 0x6600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tmessage_id = \"" + message_id + "\"", 0x6000000000000L | lMethod);
        m_ops.dump(lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPDataSMResponse.wrap : wrap elements !", 0x6800000000000L);
        dump(0x800000000000L);
        addCString(message_id);
        m_ops.wrap(this);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPDataSMResponse.unwrap : unwrap elements !", 0x6800000000000L);
        message_id = getCString();
        m_ops.unwrap(this);
        dump(0x800000000000L);
    }


}