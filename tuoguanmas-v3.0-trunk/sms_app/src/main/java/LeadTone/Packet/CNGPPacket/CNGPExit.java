package LeadTone.Packet.CNGPPacket;

import LeadTone.Log;



public class CNGPExit extends CNGPPacket
{

    public CNGPExit(int sequence_id)
    {
        super(6, sequence_id);
    }

    public CNGPExit(CNGPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 6)
        {
            Log.log("CNGPExit.isValid : not a CNGP_EXIT command !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}