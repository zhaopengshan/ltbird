package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMPPCommandSM extends SMPPPacket
{
    public String message_id;
    public SMEAddress source;

    public SMPPCommandSM()
    {
        message_id = null;
        source = new SMEAddress();
    }

    public SMPPCommandSM(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
        message_id = null;
        source = new SMEAddress();
    }

    public SMPPCommandSM(SMPPPacket packet)
    {
        super(packet);
        message_id = null;
        source = new SMEAddress();
    }

    public boolean isValid()
    {
        if(message_id != null && message_id.length() > 64)
        {
            Log.log("SMPPCommandSM.isValid : invalid message_id !", 0x18600000000000L);
            return false;
        }
        if(!source.isValid())
        {
            Log.log("SMPPCommandSM.isValid : invalid source !", 0x18600000000000L);
            return false;
        } else
        {
            return true;
        }
    }


}