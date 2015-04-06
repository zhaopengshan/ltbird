package LeadTone.Packet.CNGPPacket;

import LeadTone.Log;



public class CNGPExitResponse extends CNGPPacket
{

    public CNGPExitResponse(int sequence_id)
    {
        super(6, sequence_id);
    }

    public CNGPExitResponse(CNGPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000006)
        {
            Log.log("CNGPExitResponse.isValid : not a CNGP_EXIT_RESPONSE command !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}