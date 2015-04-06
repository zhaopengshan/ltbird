package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Utility;


/**
 * 
 */
public class CMPPStatusReport
{
    public static final int STATUS_REPORT_SIZE = 60;
    public long msg_id;
    public String status;
    public String submit_time;
    public String done_time;
    public String dest_terminal_id;
    public int smsc_sequence;
    
    public CMPPStatusReport()
    {
        msg_id = 1L;
        status = null;
        submit_time = null;
        done_time = null;
        dest_terminal_id = null;
        smsc_sequence = 0;
    }

    public boolean isValid()
    {
        if(status != null && status.length() > 7)
        {
            Log.log("CMPPStatusReport.isValid : invalid status !", 0x4600000000000L);
            return false;
        }
        if(submit_time != null && submit_time.length() > 10)
        {
            Log.log("CMPPStatusReport.isValid : invalid submit_time !", 0x4600000000000L);
            return false;
        }
        if(done_time != null && done_time.length() > 10)
        {
            Log.log("CMPPStatusReport.isValid : invalid done_time !", 0x4600000000000L);
            return false;
        }
        if(dest_terminal_id != null && dest_terminal_id.length() > 21)
        {
            Log.log("CMPPStatusReport.isValid : invalid dest_terminal_id !", 0x4600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
    	Log.log("消息类型＝CMPP_Report,状态报告报文为： ", 0x4000000000000L | lMethod);
        Log.log("\tmsg_id = 0x" + Utility.toHexString(msg_id), 0x4000000000000L | lMethod);
        Log.log("\tstatus = \"" + status + "\"", 0x4000000000000L | lMethod);
        Log.log("\tsubmit_time = \"" + submit_time + "\"", 0x4000000000000L | lMethod);
        Log.log("\tdone_time = \"" + done_time + "\"", 0x4000000000000L | lMethod);
        Log.log("\tdest_terminal_id = \"" + dest_terminal_id + "\"", 0x4000000000000L | lMethod);
        Log.log("\tsmsc_sequence = " + smsc_sequence, 0x4000000000000L | lMethod);
    }

    public void wrap(CMPPPacket packet)
        throws BufferException
    {
        Log.log("CMPPStatusReport.wrap : wrap elements !", 0x4800000000000L);
        dump(0x800000000000L);
        packet.addLong(msg_id);
        packet.addString(status, 7);
        packet.addString(submit_time, 10);
        packet.addString(done_time, 10);
        packet.addString(dest_terminal_id, 21);
        packet.addInteger(smsc_sequence);
    }

    public void unwrap(CMPPPacket packet)
        throws BufferException
    {
        Log.log("CMPPStatusReport.unwrap : unwrap elements !", 0x4800000000000L);
        msg_id = packet.getLong();
        status = packet.getString(7);
        submit_time = packet.getString(10);
        done_time = packet.getString(10);
        dest_terminal_id = packet.getString(21);
        smsc_sequence = packet.getInteger();
        dump(0x800000000000L);
    }

    public void unwrapMISC(CMPPPacket packet)
        throws BufferException
    {
        Log.log("CMPPStatusReport.unwrapMISC : unwrap elements !", 0x4800000000000L);
        msg_id = packet.getLong();
        submit_time = packet.getString(7);
        submit_time = null;
        done_time = packet.getString(15);
        done_time = done_time.substring(5);
        status = packet.getString(5);
        status.trim();
        dest_terminal_id = packet.getString(21);
        dest_terminal_id.trim();
        smsc_sequence = packet.getInteger();
        dump(0x800000000000L);
    }

    private String getValue(String strKeyWord, char cStart, char cEnd, String msg_content)
    {
        if(msg_content == null || msg_content.length() <= 0)
            return null;
        if(strKeyWord == null || strKeyWord.length() <= 0)
            return null;
        int nStart = -1;
        int nEnd = -1;
        nStart = msg_content.indexOf(strKeyWord);
        if(nStart < 0)
            return null;
        nStart = msg_content.indexOf(cStart, nStart);
        if(nStart < 0)
            nStart = 0;
        nEnd = msg_content.indexOf(cEnd, nStart);
        if(nEnd < 0)
            nEnd = msg_content.length();
        return msg_content.substring(nStart + 1, nEnd);
    }

    public void unwrapASIAINFO(String msg_content)
    {
        if(msg_content == null || msg_content.length() <= 0)
            return;
        String strID = getValue("Id", '[', ']', msg_content);
        if(strID != null)
        {
            int i;
            for(i = 0; i < strID.length(); i++)
                if(strID.charAt(i) != ' ')
                    break;

            msg_id = Long.parseLong(strID.substring(i, strID.length()));
        }
        status = getValue("Stat", '[', ']', msg_content);
        submit_time = getValue("SubmitDate", '[', ']', msg_content);
        done_time = getValue("DoneDate", '[', ']', msg_content);
    }

    public void unwrapSMSC(String msg_content)
    {
        if(msg_content == null || msg_content.length() <= 0)
            return;
        String strID = getValue("id", ':', ' ', msg_content);
        if(strID != null && strID.length() > 0)
            msg_id = Long.parseLong(strID);
        status = getValue("stat", ':', ' ', msg_content);
        submit_time = getValue("submit date", ':', ' ', msg_content);
        done_time = getValue("done date", ':', ' ', msg_content);
    }



}