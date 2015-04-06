package LeadTone.Packet.NOKIAPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class NOKIASubmitResponse extends NOKIAPacket
{
    public String message_id;
    public int failed_users_count;
    public NOKIAFailedUser failed_user[];

    public NOKIASubmitResponse(int sequence_id)
    {
        super(0x80000004, sequence_id);
        message_id = null;
        failed_users_count = 0;
        failed_user = null;
    }

    public NOKIASubmitResponse(NOKIAPacket packet)
    {
        super(packet);
        message_id = null;
        failed_users_count = 0;
        failed_user = null;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000004)
        {
            Log.log("NOKIASubmitResponse.isValid : not a CMPP_SUBMIT_RESPONSE command !", 0x2600000000000L);
            return false;
        }
        if(message_id != null && message_id.length() > 64)
        {
            Log.log("NOKIASubmitResponse.isValid : invalid message_id !", 0x2600000000000L);
            return false;
        }
        if(failed_user == null && failed_users_count != 0 || failed_user != null && failed_users_count != failed_user.length)
        {
            Log.log("NOKIASubmitResponse.isValid : failed_users_count is not according with failed_user length !", 0x2600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tmessage_id = \"" + message_id + "\"", 0x2000000000000L | lMethod);
        Log.log("\tfailed_users_count = " + failed_users_count, 0x2000000000000L | lMethod);
        for(int i = 0; i < failed_users_count; i++)
            Log.log("\tfailed_users[" + failed_user[i].error_user_index + "] = " + failed_user[i].error_status, 0x2000000000000L | lMethod);

    }

    public void wrap()
        throws BufferException
    {
        Log.log("NOKIASubmitResponse.wrap : wrap elements !", 0x2800000000000L);
        dump(0x800000000000L);
        addCString(message_id);
        addByte((byte)(failed_users_count & 0xff));
        for(int i = 0; i < failed_users_count; i++)
            failed_user[i].wrap(this);

    }

    public void unwrap()
        throws BufferException
    {
        Log.log("NOKIASubmitResponse.unwrap : unwrap elements !", 0x2800000000000L);
        message_id = getCString();
        failed_users_count = getByte() & 0xff;
        if(failed_users_count > 0)
        {
            failed_user = new NOKIAFailedUser[failed_users_count];
            for(int i = 0; i < failed_users_count; i++)
            {
                failed_user[i] = new NOKIAFailedUser();
                failed_user[i].unwrap(this);
            }

        }
        dump(0x800000000000L);
    }


}