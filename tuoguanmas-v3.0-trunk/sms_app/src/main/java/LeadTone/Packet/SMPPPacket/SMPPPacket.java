package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Utility;


public class SMPPPacket extends Packet
{
    public int command_id;
    public int command_status;
    public int sequence_id;
    public OptionalParameterList m_ops;

    public SMPPPacket()
    {
        command_status = 0;
        sequence_id = 1;
        m_ops = new OptionalParameterList();
    }

    public SMPPPacket(int command_id, int sequence_id)
    {
        command_status = 0;
        this.sequence_id = 1;
        m_ops = new OptionalParameterList();
        this.command_id = command_id;
        command_status = 0;
        this.sequence_id = sequence_id;
    }

    public SMPPPacket(Packet packet)
    {
        super(packet);
        command_status = 0;
        sequence_id = 1;
        m_ops = new OptionalParameterList();
    }

    public SMPPPacket(SMPPPacket packet)
    {
        super(packet);
        command_status = 0;
        sequence_id = 1;
        m_ops = new OptionalParameterList();
        command_id = packet.command_id;
        command_status = packet.command_status;
        sequence_id = packet.sequence_id;
    }

    public boolean isValid()
    {
        if(!SMPPCommandID.isValid(command_id))
        {
            Log.log("SMPPPacket.isValid : not a valid command !", 0x200600000000000L);
            return false;
        }
        if(sequence_id <= 0)
        {
            Log.log("SMPPPacket.isValid : invalid sequence_id !", 0x200600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dumpSMPPPacket(long lMethod)
    {
        if(Log.isRequested(lMethod))
        {
            Log.log("\tcommand_id = 0x" + Utility.toHexString(command_id) + " (" + SMPPCommandID.toString(command_id) + ")", 0x200000000000000L | lMethod);
            Log.log("\tcommand_status = 0x" + Utility.toHexString(command_status) + " (" + SMPPCommandStatus.toString(command_status) + " | " + CMCCCommandStatus.toString(command_status) + ")", 0x200000000000000L | lMethod);
            Log.log("\tsequence_id = " + sequence_id, 0x200000000000000L | lMethod);
        }
    }

    public void wrapSMPPPacket()
        throws BufferException
    {
        Log.log("SMPPPacket.wrap : wrap elements !", 0x200800000000000L);
        dumpSMPPPacket(0x800000000000L);
        insertInteger(sequence_id);
        insertInteger(command_status);
        insertInteger(command_id);
    }

    public void unwrapSMPPPacket()
        throws BufferException
    {
        Log.log("SMPPPacket.unwrap : unwrap elements !", 0x200800000000000L);
        command_id = getInteger();
        command_status = getInteger();
        sequence_id = getInteger();
        dumpSMPPPacket(0x800000000000L);
    }


}