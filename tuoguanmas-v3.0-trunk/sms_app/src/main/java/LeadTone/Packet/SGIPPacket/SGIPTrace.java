package LeadTone.Packet.SGIPPacket;

import LeadTone.*;


public class SGIPTrace extends SGIPPacket
{

    public byte submit_sequence_number[];
    public String user_number;
    public String reserve;

    public SGIPTrace(int sequence_id)
    {
        super(4096, sequence_id);
        submit_sequence_number = null;
        user_number = null;
        reserve = null;
    }

    public SGIPTrace(SGIPPacket packet)
    {
        super(packet);
        submit_sequence_number = null;
        user_number = null;
        reserve = null;
    }

    public boolean isValid()
    {
        if(command_id != 4096)
        {
            Log.log("SGIPTrace.isValid : not a SGIP_TRACE command !", 0x8600000000000L);
            return false;
        }
        if(submit_sequence_number != null && submit_sequence_number.length != 16)
        {
            Log.log("SGIPTrace.isValid : invalid submit_sequence_number !", 0x8600000000000L);
            return false;
        }
        if(user_number != null && user_number.length() > 21)
        {
            Log.log("SGIPTrace.isValid : invalid user_number !", 0x8600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tsubmit_sequence_number = 0x" + Utility.toHexString(submit_sequence_number), 0x8000000000000L | lMethod);
        Log.log("\tuser_number = \"" + user_number + "\"", 0x8000000000000L | lMethod);
        Log.log("\treserve = \"" + reserve + "\"", 0x8000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SGIPTrace.wrap : wrap elements !", 0x8800000000000L);
        dump(0x800000000000L);
        addBytes(submit_sequence_number);
        addString(user_number, 21);
        addString(reserve, 8);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SGIPTrace.unwrap : unwrap elements !", 0x8800000000000L);
        submit_sequence_number = getBytes(16);
        user_number = getString(21);
        reserve = getString(8);
        dump(0x800000000000L);
    }


}