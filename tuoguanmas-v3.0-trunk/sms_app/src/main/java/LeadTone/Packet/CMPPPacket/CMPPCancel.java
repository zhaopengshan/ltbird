package LeadTone.Packet.CMPPPacket;

import LeadTone.*;


public class CMPPCancel extends CMPPPacket
{

    public long msg_id;

    public CMPPCancel(int sequence_id)
    {
        super(7, sequence_id);
        msg_id = 1L;
    }

    public CMPPCancel(CMPPPacket packet)
    {
        super(packet);
        msg_id = 1L;
    }

    public boolean isValid()
    {
        return true;
    }

    public void dump(long lMethod)
    {
        Log.log("\tmsg_id = 0x" + Utility.toHexString(msg_id), 0x10000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CMPPCancel.wrap : wrap elements !", 0x10800000000000L);
        dump(0x10800000000000L);
        addLong(msg_id);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPCancel.unwrap : unwrap elements !", 0x10800000000000L);
        msg_id = getLong();
        dump(0x10800000000000L);
    }
}
