package LeadTone.Packet.SGIPPacket;

import LeadTone.*;



public class SGIPSequenceID
{
    public int sequence_id[];

    public SGIPSequenceID()
    {
        sequence_id = new int[3];
        sequence_id[0] = 0;
        sequence_id[1] = 0;
        sequence_id[2] = 0;
    }

    public SGIPSequenceID(int sequence_id)
    {
        this.sequence_id = new int[3];
        this.sequence_id[0] = 0;
        this.sequence_id[1] = 0;
        this.sequence_id[2] = sequence_id;
    }

    public SGIPSequenceID(SGIPSequenceID sid)
    {
        sequence_id = new int[3];
        sequence_id = sid.sequence_id;
    }

    public boolean increase(int nIndex)
    {
        if((sequence_id[nIndex] & 0x80000000) != 0)
        {
            int temp = sequence_id[nIndex] & 0x7fffffff;
            if((++temp & 0x80000000) != 0)
            {
                sequence_id[nIndex] = temp & 0x7fffffff;
                return true;
            }
            sequence_id[nIndex] = temp;
        } else
        {
            sequence_id[nIndex]++;
        }
        return false;
    }

    public void increase()
    {
        boolean bIncrease = true;
        for(int i = 3; i > 0; i--)
            if(bIncrease)
                bIncrease = increase(i - 1);

    }

    public void dump(long lMethod)
    {
        Log.log("\tsequence_id = 0x" + Utility.toHexString(sequence_id[0]) + Utility.toHexString(sequence_id[1]) + Utility.toHexString(sequence_id[2]), 0x200000000000000L | lMethod);
    }

    public void wrap(SGIPPacket packet)
        throws BufferException
    {
        Log.log("SGIPSequenceID.wrap : wrap elements !", 0x200800000000000L);
        dump(0x800000000000L);
        for(int i = 3; i > 0; i--)
            packet.insertInteger(sequence_id[i - 1]);

    }

    public void unwrap(SGIPPacket packet)
        throws BufferException
    {
        Log.log("SGIPSequenceID.unwrap : unwrap elements !", 0x200800000000000L);
        for(int i = 0; i < 3; i++)
            sequence_id[i] = packet.getInteger();

        dump(0x800000000000L);
    }


}