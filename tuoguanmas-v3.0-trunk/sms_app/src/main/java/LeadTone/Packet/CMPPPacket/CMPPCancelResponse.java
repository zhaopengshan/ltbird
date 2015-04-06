
package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;


public class CMPPCancelResponse extends CMPPPacket
{

    int success_id;

    public CMPPCancelResponse(int sequence_id)
    {
        super(0x80000007, sequence_id);
        success_id = -1;
    }

    public CMPPCancelResponse(CMPPPacket packet)
    {
        super(packet);
        success_id = -1;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000007)
        {
            Log.log("CMPPCancelResponse.isValid : not a CMPP_CANCEL_RESPONSE command !", 0x10600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tsuccesss_id = " + success_id, 0x10000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPCancelResponse.wrap : wrap elements !", 0x10800000000000L);
        dump(0x800000000000L);
        addInteger(success_id);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPCancelResponse.unwrap : unwrap elements !", 0x10800000000000L);
        success_id = getInteger();
        dump(0x800000000000L);
    }
}
