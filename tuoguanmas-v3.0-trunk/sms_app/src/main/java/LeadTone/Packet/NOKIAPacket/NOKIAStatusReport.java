package LeadTone.Packet.NOKIAPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class NOKIAStatusReport
{
    static final byte END_BYTE = 9;
    public String message_id;
    public String status;
    public String submit_time;
    public String done_time;

    public NOKIAStatusReport()
    {
        message_id = null;
        status = null;
        submit_time = null;
        done_time = null;
    }

    public boolean isValid()
    {
        if(message_id != null && message_id.length() > 64)
            Log.log("CMPPStatusReport.isValid : invalid message_id !", 0x4600000000000L);
        if(status != null && status.length() > 7)
        {
            Log.log("CMPPStatusReport.isValid : invalid status !", 0x4600000000000L);
            return false;
        }
        if(submit_time != null && submit_time.length() > 12)
        {
            Log.log("CMPPStatusReport.isValid : invalid submit_time !", 0x4600000000000L);
            return false;
        }
        if(done_time != null && done_time.length() > 12)
        {
            Log.log("CMPPStatusReport.isValid : invalid done_time !", 0x4600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tmessage_id = 0x" + message_id, 0x4000000000000L | lMethod);
        Log.log("\tstatus = \"" + status + "\"", 0x4000000000000L | lMethod);
        Log.log("\tsubmit_time = \"" + submit_time + "\"", 0x4000000000000L | lMethod);
        Log.log("\tdone_time = \"" + done_time + "\"", 0x4000000000000L | lMethod);
    }

    public void wrap(NOKIAPacket packet)
        throws BufferException
    {
        Log.log("CMPPStatusReport.wrap : wrap elements !", 0x4800000000000L);
        dump(0x800000000000L);
        packet.addCString(message_id);
        packet.addCString(status);
        packet.addCString(submit_time);
        packet.addCString(done_time);
    }

    public void unwrap(NOKIAPacket packet)
        throws BufferException
    {
        Log.log("CMPPStatusReport.unwrap : unwrap elements !", 0x4800000000000L);
        message_id = packet.getString((byte)9);
        status = packet.getString((byte)9);
        submit_time = packet.getString((byte)9);
        done_time = packet.getCString();
        dump(0x800000000000L);
    }



}