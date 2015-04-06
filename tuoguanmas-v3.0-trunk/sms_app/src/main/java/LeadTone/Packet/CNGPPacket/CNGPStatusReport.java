package LeadTone.Packet.CNGPPacket;

import LeadTone.*;
import java.util.Enumeration;

public class CNGPStatusReport
{

    public String message_id;
    public String status;
    public String submit_time;
    public String done_time;
    public String sub;
    public String dlvrd;
    public String err;
    public String txt_length;
    public String txt_content;
    
    public CNGPStatusReport()
    {
        message_id = null;
        status = "unkown";
        submit_time = "";
        done_time = "";
        sub = "";
        dlvrd = "";
        err = "";
        txt_length = "";
        txt_content = "";
    }

    public boolean isValid()
    {
        if(message_id != null && message_id.length() > 64)
            Log.log("CNGPStatusReport.isValid : invalid message_id !", 0x500600000000000L);
        if(status != null && status.length() > 7)
        {
            Log.log("CNGPStatusReport.isValid : invalid status !", 0x500600000000000L);
            return false;
        }
        if(submit_time != null && submit_time.length() > 12)
        {
            Log.log("CNGPStatusReport.isValid : invalid submit_time !", 0x500600000000000L);
            return false;
        }
        if(done_time != null && done_time.length() > 12)
        {
            Log.log("CNGPStatusReport.isValid : invalid done_time !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tid = \"" + message_id + "\"", 0x500000000000000L | lMethod);
        Log.log("\tSub = \"" + sub + "\"", 0x500000000000000L | lMethod);
        Log.log("\tdlvrd = \"" + dlvrd + "\"", 0x500000000000000L | lMethod);
        Log.log("\tsubmit_time = \"" + submit_time + "\"", 0x500000000000000L | lMethod);
        Log.log("\tdone_time = \"" + done_time + "\"", 0x500000000000000L | lMethod);
        Log.log("\tstatus = \"" + status + "\"", 0x500000000000000L | lMethod);
        Log.log("\terr = \"" + err + "\"", 0x500000000000000L | lMethod);
        Log.log("\ttxt (len:" + txt_length + ")= \"" + txt_content + "\"", 0x500000000000000L | lMethod);
    }

    public void unwrap(String msg_content)
        throws BufferException
    {
        Log.log("CNGPStatusReport.unwrap : unwrap elements !", 0x500800000000000L);
        Enumeration Enum = Utility.splitSeparater(msg_content, " ");
        String temp = "";
        if(Enum.hasMoreElements())
        {
            temp = (String)Enum.nextElement();
            message_id = temp.substring(temp.indexOf(":") + 1);
            if(Enum.hasMoreElements())
            {
                temp = (String)Enum.nextElement();
                sub = temp.substring(temp.indexOf(":") + 1);
                if(Enum.hasMoreElements())
                {
                    temp = (String)Enum.nextElement();
                    dlvrd = temp.substring(temp.indexOf(":") + 1);
                    if(Enum.hasMoreElements())
                    {
                        temp = (String)Enum.nextElement();
                        submit_time = temp.substring(temp.indexOf(":") + 1);
                        if(Enum.hasMoreElements())
                        {
                            temp = (String)Enum.nextElement();
                            done_time = temp.substring(temp.indexOf(":") + 1);
                            if(Enum.hasMoreElements())
                            {
                                temp = (String)Enum.nextElement();
                                status = temp.substring(temp.indexOf(":") + 1);
                                if(Enum.hasMoreElements())
                                {
                                    temp = (String)Enum.nextElement();
                                    err = temp.substring(temp.indexOf(":") + 1);
                                    if(Enum.hasMoreElements())
                                    {
                                        temp = (String)Enum.nextElement();
                                        txt_length = temp.substring(0, 3);
                                        txt_content = temp.substring(3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String args[])
    {
        CNGPStatusReport rep = new CNGPStatusReport();
        try
        {
            rep.unwrap("id:1234567890 sub:123 dlvrd:001 submit_date:031027120000 done_date:031027130000 stat:DELIVRD err:092 002he");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}