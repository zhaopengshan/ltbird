package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;


public class SMPPQuerySM extends SMPPCommandSM
{

    public SMPPQuerySM()
    {
    }

    public SMPPQuerySM(int sequence_id)
    {
        super(3, sequence_id);
    }

    public SMPPQuerySM(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 3)
        {
            Log.log("SMPPQuerySM.isValid : not a QUERY_SM command !", 0x18600000000000L);
            return false;
        }
        return super.isValid();
    }

    public void dump(long lMethod)
    {
        Log.log("\tmessage_id = \"" + message_id + "\"", 0x18000000000000L | lMethod);
        source.dump(lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPQuerySM.wrap : wrap elements !", 0x18800000000000L);
        dump(0x800000000000L);
        addCString(message_id);
        source.wrap(this);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPQuerySM.unwrap : unwrap elements !", 0x18800000000000L);
        message_id = getCString();
        source.unwrap(this);
        dump(0x800000000000L);
    }
}