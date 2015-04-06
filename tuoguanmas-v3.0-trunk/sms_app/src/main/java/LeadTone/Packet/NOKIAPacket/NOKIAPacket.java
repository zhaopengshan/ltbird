package LeadTone.Packet.NOKIAPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPCommandID;
import LeadTone.Packet.Packet;
import LeadTone.Utility;



public class NOKIAPacket extends Packet
{

    public int command_id;
    public int command_status;
    public int sequence_id;

    public NOKIAPacket()
    {
        command_id = 0x80000000;
        command_status = 0;
        sequence_id = 1;
    }

    public NOKIAPacket(Packet packet)
    {
        super(packet);
        command_id = 0x80000000;
        command_status = 0;
        sequence_id = 1;
    }

    public NOKIAPacket(int command_id, int sequence_id)
    {
        this.command_id = 0x80000000;
        command_status = 0;
        this.sequence_id = 1;
        this.command_id = command_id;
        command_status = 0;
        this.sequence_id = sequence_id;
    }

    public NOKIAPacket(NOKIAPacket packet)
    {
        super(packet);
        command_id = 0x80000000;
        command_status = 0;
        sequence_id = 1;
        command_id = packet.command_id;
        command_status = packet.command_status;
        sequence_id = packet.sequence_id;
    }

    public boolean isValid()
    {
        if(!CMPPCommandID.isValid(command_id))
        {
            Log.log("NOKIAPacket.isValid : not a valid command !", 0x200600000000000L);
            return false;
        }
        if(sequence_id <= 0)
        {
            Log.log("NOKIAPacket.isValid : invalid sequence_id !", 0x200600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dumpNOKIAPacket(long lMethod)
    {
        if(Log.isRequested(lMethod))
        {
            Log.log("\tcommand_id = 0x" + Utility.toHexString(command_id) + " (" + CMPPCommandID.toString(command_id) + ")", 0x200000000000000L | lMethod);
            Log.log("\tcommand_status = 0x" + Utility.toHexString(command_status) + " (" + NOKIACommandStatus.toString(command_status) + ")", 0x200000000000000L | lMethod);
            Log.log("\tsequence_id = " + sequence_id, 0x200000000000000L | lMethod);
        }
    }

    public void wrapNOKIAPacket()
        throws BufferException
    {
        Log.log("NOKIAPacket.wrap : wrap elements !", 0x200800000000000L);
        dumpNOKIAPacket(0x800000000000L);
        insertInteger(sequence_id);
        insertInteger(command_status);
        insertInteger(command_id);
    }

    public void unwrapNOKIAPacket()
        throws BufferException
    {
        Log.log("NOKIAPacket.unwrap : unwrap elements !", 0x200800000000000L);
        command_id = getInteger();
        command_status = getInteger();
        sequence_id = getInteger();
        dumpNOKIAPacket(0x800000000000L);
    }


}