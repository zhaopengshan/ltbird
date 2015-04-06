package LeadTone.Packet.CNGPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Utility;



public class CNGPPacket extends Packet
{

    public int command_id;
    public int command_status;
    public int sequence_id;
    
    public CNGPPacket()
    {
        command_status = 0;
        sequence_id = 1;
    }

    public CNGPPacket(Packet packet)
    {
        super(packet);
        command_status = 0;
        sequence_id = 1;
    }

    public CNGPPacket(int command_id, int sequence_id)
    {
        command_status = 0;
        this.sequence_id = 1;
        this.command_id = command_id;
        command_status = 0;
        this.sequence_id = sequence_id;
    }

    public CNGPPacket(CNGPPacket packet)
    {
        super(packet);
        command_status = 0;
        sequence_id = 1;
        command_id = packet.command_id;
        command_status = packet.command_status;
        sequence_id = packet.sequence_id;
    }

    public boolean isValid()
    {
        if(!CNGPCommandID.isValid(command_id))
        {
            Log.log("CNGPPacket.isValid : not a valid command !", 0x500600000000000L);
            return false;
        }
        if(sequence_id <= 0)
        {
            Log.log("CNGPPacket.isValid : invalid sequence_id !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dumpCNGPPacket(long lMethod)
    {
        if(Log.isRequested(lMethod))
        {
            Log.log("\tcommand_id = 0x" + Utility.toHexString(command_id) + " (" + CNGPCommandID.toString(command_id) + ")", 0x500000000000000L | lMethod);
            Log.log("\tcommand_status = 0x" + Utility.toHexString(command_status) + " (" + CNGPCommandStatus.toString(command_status) + ")", 0x500000000000000L | lMethod);
            Log.log("\tsequence_id = " + sequence_id, 0x500000000000000L | lMethod);
        }
    }

    public void wrapCNGPPacket()
        throws BufferException
    {
        Log.log("CNGPPacket.wrap : wrap elements !", 0x500800000000000L);
        dumpCNGPPacket(0x800000000000L);
        insertInteger(sequence_id);
        insertInteger(command_status);
        insertInteger(command_id);
    }

    public void unwrapCNGPPacket()
        throws BufferException
    {
        Log.log("CNGPPacket.unwrap : unwrap elements !", 0x500800000000000L);
        command_id = getInteger();
        command_status = getInteger();
        sequence_id = getInteger();
        dumpCNGPPacket(0x800000000000L);
    }


}