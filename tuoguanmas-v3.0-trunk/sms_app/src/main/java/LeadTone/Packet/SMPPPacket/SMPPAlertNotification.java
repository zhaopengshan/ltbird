package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;


public class SMPPAlertNotification extends SMPPPacket
{

    public SMPPAlertNotification()
    {
    }

    public SMPPAlertNotification(int sequence_id)
    {
        super(0x80000102, sequence_id);
    }

    public SMPPAlertNotification(SMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(!super.isValid())
            return false;
        if(command_id != 0x80000102)
        {
            Log.log("SMPPAlertNotification.isValid : not a SMPP_ALERT_NOTIFICATION command !", 0x1600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}