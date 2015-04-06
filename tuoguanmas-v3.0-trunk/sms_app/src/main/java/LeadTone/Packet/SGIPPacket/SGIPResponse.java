package LeadTone.Packet.SGIPPacket;

import LeadTone.*;


public class SGIPResponse extends SGIPPacket
{

    public SGIPResponse(SGIPPacket packet)
    {
        super(packet);
        result = 0;
        reserve = null;
    }

    public SGIPResponse(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
        result = 0;
        reserve = null;
    }

    public boolean isValid()
    {
        return true;
    }

    public void dump(long lMethod)
    {
        Log.log("\tresult = 0x" + Utility.toHexString(result) + " (" + SGIPResult.toString(result) + ")", 0x200000000000000L | lMethod);
        Log.log("\treserve = \"" + reserve + "\"", 0x200000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SGIPResponse.wrap : wrap elements !", 0x200800000000000L);
        dump(0x800000000000L);
        addByte(result);
        addString(reserve, 8);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SGIPResponse.unwrap : unwrap elements !", 0x200800000000000L);
        result = getByte();
        reserve = getString(8);
        dump(0x800000000000L);
    }

    public byte result;
    public String reserve;
}