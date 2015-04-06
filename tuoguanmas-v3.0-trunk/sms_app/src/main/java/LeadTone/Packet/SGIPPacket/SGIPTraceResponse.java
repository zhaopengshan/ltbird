package LeadTone.Packet.SGIPPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class SGIPTraceResponse extends SGIPPacket
{
    public int count;
    public SGIPNodeStatus node_statuses[];

    public SGIPTraceResponse(int sequence_id)
    {
        super(0x80001000, sequence_id);
        count = 0;
        node_statuses = null;
    }

    public SGIPTraceResponse(SGIPPacket packet)
    {
        super(packet);
        count = 0;
        node_statuses = null;
    }

    public boolean isValid()
    {
        if(command_id != 0x80001000)
        {
            Log.log("SGIPTraceResponse.isValid : not a SGIP_TRACE_RESPONSE command !", 0x8600000000000L);
            return false;
        }
        if(count < 0 || count > 100)
        {
            Log.log("SGIPTraceResponse.isValid : invalid count !", 0x8600000000000L);
            return false;
        }
        if(node_statuses != null && node_statuses.length != count)
        {
            Log.log("SGIPTraceResponse.isValid : count not according with node_statuses.length !", 0x8600000000000L);
            return false;
        }
        for(int i = 0; i < count; i++)
            if(!node_statuses[i].isValid())
            {
                Log.log("SGIPTraceResponse.isValid : invalid node_status[" + i + "] !", 0x8600000000000L);
                return false;
            }

        return true;
    }

    public void dump(long lMethod)
    {
        Log.log("\tcount = " + count, 0x8000000000000L | lMethod);
        for(int i = 0; i < count; i++)
            node_statuses[i].dump(0x8000000000000L | lMethod);

    }

    public void wrap()
        throws BufferException
    {
        Log.log("SGIPTraceResponse.wrap : wrap elements !", 0x8800000000000L);
        dump(0x800000000000L);
        addByte((byte)count);
        for(int i = 0; i < count; i++)
            node_statuses[i].wrap(this);

    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SGIPTraceResponse.unwrap : unwrap elements !", 0x8800000000000L);
        count = getByte() & 0xff;
        if(count > 0 && count < 100)
        {
            node_statuses = new SGIPNodeStatus[count];
            for(int i = 0; i < count; i++)
                node_statuses[i].unwrap(this);

        }
        dump(0x800000000000L);
    }


}