package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class SMPPBindResponse extends SMPPPacket
{
    public String system_id;

    public SMPPBindResponse()
    {
        system_id = null;
    }

    public SMPPBindResponse(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
        system_id = null;
    }

    public SMPPBindResponse(SMPPPacket packet)
    {
        super(packet);
        system_id = null;
    }

    public boolean isValid()
    {
        if(system_id != null && system_id.length() > 15)
        {
            Log.log("SMPPBindResponse.isValid : invalid system_id !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tsystem_id = \"" + system_id + "\"", 0x80000000000000L | lMethod);
        m_ops.dump(0x80000000000000L);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPBindResponse.wrap : wrap elements !", 0x80800000000000L);
        dump(0x800000000000L);
        addCString(system_id);
        m_ops.wrap(this);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPBindResponse.wrap : wrap elements !", 0x80800000000000L);
        system_id = getCString();
        dump(0x800000000000L);
        m_ops.unwrap(this);
    }


}