package LeadTone.Packet.NOKIAPacket;

import LeadTone.*;


public class NOKIACancel extends NOKIAPacket
{
     public long msg_id;

    public NOKIACancel(int sequence_id)
    {
        super(7, sequence_id);
        msg_id = 1L;
    }

    public NOKIACancel(NOKIAPacket packet)
    {
        super(packet);
        msg_id = 1L;
    }

    public boolean isValid()
    {
        if(command_id != 7)
        {
            Log.log("NOKIACancel.isValid : not a CMPP_CANCEL command !", 0x10600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tmsg_id = 0x" + Utility.toHexString(msg_id), 0x10000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("NOKIACancel.wrap : wrap elements !", 0x10800000000000L);
        dump(0x800000000000L);
        addLong(msg_id);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("NOKIACancel.unwrap : unwrap elements !", 0x10800000000000L);
        msg_id = getLong();
        dump(0x800000000000L);
    }


}