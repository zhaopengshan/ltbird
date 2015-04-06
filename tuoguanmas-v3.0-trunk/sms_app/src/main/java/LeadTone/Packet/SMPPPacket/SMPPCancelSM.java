package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class SMPPCancelSM extends SMPPCommandSM
{
    public String service_type;
    public SMEAddress destination;

    public SMPPCancelSM()
    {
        service_type = null;
        destination = new SMEAddress();
    }

    public SMPPCancelSM(int sequence_id)
    {
        super(8, sequence_id);
        service_type = null;
        destination = new SMEAddress();
    }

    public SMPPCancelSM(SMPPPacket packet)
    {
        super(packet);
        service_type = null;
        destination = new SMEAddress();
    }

    public boolean isValid()
    {
        if(command_id != 8)
        {
            Log.log("SMPPCancelSM.isValid : not a SMPP_CANCEL_SM command !", 0x18600000000000L);
            return false;
        }
        if(!super.isValid())
            return false;
        if(!destination.isValid())
        {
            Log.log("SMPPCancelSM.isValid : invalid destination !", 0x18600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tservice_type = \"" + service_type + "\"", 0x18000000000000L | lMethod);
        Log.log("\tmessage_id = \"" + message_id + "\"", 0x18000000000000L | lMethod);
        source.dump(lMethod);
        destination.dump(lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPCancelSM.wrap : wrap elements !", 0x18800000000000L);
        dump(0x800000000000L);
        addCString(service_type);
        addCString(message_id);
        source.wrap(this);
        destination.wrap(this);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPCancelSM.unwrap : unwrap elements !", 0x18800000000000L);
        service_type = getCString();
        message_id = getCString();
        source.unwrap(this);
        destination.unwrap(this);
        dump(0x800000000000L);
    }


}