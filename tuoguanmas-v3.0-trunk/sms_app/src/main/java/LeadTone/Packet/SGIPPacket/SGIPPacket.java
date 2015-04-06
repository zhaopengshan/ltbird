package LeadTone.Packet.SGIPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Utility;



public class SGIPPacket extends Packet
{
    public int command_id;
    public int node_id;
    public int time_stamp;
    public int sequence_id;

    public SGIPPacket()
    {
        node_id = 0x493e0;
    }

    public SGIPPacket(Packet packet)
    {
        super(packet);
        node_id = 0x493e0;
    }

    public SGIPPacket(int command_id, int sequence_id)
    {
        node_id = 0x493e0;
        this.command_id = command_id;
        this.sequence_id = sequence_id;
    }

    public SGIPPacket(SGIPPacket packet)
    {
        super(packet);
        node_id = 0x493e0;
        command_id = packet.command_id;
        node_id = packet.node_id;
        time_stamp = packet.time_stamp;
        sequence_id = packet.sequence_id;
    }

    public boolean isValid()
    {
        if(!SGIPCommandID.isValid(command_id))
        {
            Log.log("SGIPPacket.isValid : not a valid command !", 0x200600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dumpSGIPPacket(long lMethod)
    {
        if(Log.isRequested(lMethod))
        {
            Log.log("\tcommand_id = 0x" + Utility.toHexString(command_id) + " (" + SGIPCommandID.toString(command_id) + ")", 0x200000000000000L | lMethod);
            Log.log("\tnode_id = " + node_id, 0x200000000000000L | lMethod);
            Log.log("\ttime_stamp = " + time_stamp, 0x200000000000000L | lMethod);
            Log.log("\tsequence_id = " + sequence_id, 0x200000000000000L | lMethod);
        }
    }

    public void wrapSGIPPacket()
        throws BufferException
    {
        Log.log("SGIPPacket.wrap : wrap elements !", 0x200800000000000L);
        dumpSGIPPacket(0x800000000000L);
        insertInteger(sequence_id);
        insertInteger(time_stamp);
        insertInteger(node_id);
        insertInteger(command_id);
    }

    public void unwrapSGIPPacket()
        throws BufferException
    {
        Log.log("SGIPPacket.unwrap : unwrap elements !", 0x200800000000000L);
        command_id = getInteger();
        node_id = getInteger();
        time_stamp = getInteger();
        sequence_id = getInteger();
        dumpSGIPPacket(0x800000000000L);
    }


}