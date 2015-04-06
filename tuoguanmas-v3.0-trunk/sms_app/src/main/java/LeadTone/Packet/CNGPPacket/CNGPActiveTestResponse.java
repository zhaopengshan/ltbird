package LeadTone.Packet.CNGPPacket;

import LeadTone.Log;



public class CNGPActiveTestResponse extends CNGPPacket
{

    public CNGPActiveTestResponse(int sequence_id)
    {
        super(0x80000004, sequence_id);
    }

    public CNGPActiveTestResponse(CNGPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000004)
        {
            Log.log("CNGPActiveTestResponse.isValid : not a CNGP_ACTIVETEST_RESPONSE command !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}