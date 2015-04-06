package LeadTone.Packet.CNGPPacket;

import LeadTone.Log;


public class CNGPActiveTest extends CNGPPacket
{

    public CNGPActiveTest(int sequence_id)
    {
        super(4, sequence_id);
    }

    public CNGPActiveTest(CNGPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 4)
        {
            Log.log("CNGPActiveTest.isValid : not a CNGP_ACTIVETEST command !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}