package LeadTone.Packet.NOKIAPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class NOKIAFailedUser
{

    public int error_user_index;
    public byte error_status;
    
    public NOKIAFailedUser()
    {
        error_user_index = 0;
        error_status = 0;
    }

    public void dump(long lMethod)
    {
        Log.log("\terror_user_index = " + error_user_index, 0x2000000000000L | lMethod);
        Log.log("\terror_status = " + error_status, 0x2000000000000L | lMethod);
    }

    public void wrap(NOKIAPacket packet)
        throws BufferException
    {
        Log.log("NOKIAFailedUser.wrap : wrap elements !", 0x2800000000000L);
        dump(0x800000000000L);
        packet.addByte((byte)error_user_index);
        packet.addByte(error_status);
    }

    public void unwrap(NOKIAPacket packet)
        throws BufferException
    {
        Log.log("NOKIAFailedUser.unwrap : unwrap elements !", 0x2800000000000L);
        error_user_index = packet.getByte() & 0xff;
        error_status = packet.getByte();
        dump(0x800000000000L);
    }


}