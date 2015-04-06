package LeadTone.Packet.SGIPPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class SGIPBind extends SGIPPacket
{
    public static final byte BIND_SEND = 0;
    public static final byte BIND_RECEIVE = 1;
    public byte login_type;
    public String login_name;
    public String login_password;
    public String reserve;

    public SGIPBind(int sequence_id)
    {
        super(1, sequence_id);
        login_type = 0;
        login_name = null;
        login_password = null;
        reserve = null;
    }

    public SGIPBind(SGIPPacket packet)
    {
        super(packet);
        login_type = 0;
        login_name = null;
        login_password = null;
        reserve = null;
    }

    public boolean isValid()
    {
        if(command_id != 1)
        {
            Log.log("SGIPBind.isValid : not a SGIP_BIND command !", 0x80600000000000L);
            return false;
        }
        if(login_name != null && login_name.length() > 16)
        {
            Log.log("SGIPBind.isValid : invalid login_name length !", 0x80600000000000L);
            return false;
        }
        if(login_password == null || login_password.length() != 16)
        {
            Log.log("SGIPBind.isValid : invalid login_password length !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tlogin_type = " + login_type + " (" + SGIPBindType.toString(login_type) + ")", 0x80000000000000L | lMethod);
        Log.log("\tlogin_name = \"" + login_name + "\"", 0x80000000000000L | lMethod);
        Log.log("\tlogin_password = \"" + login_password + "\"", 0x80000000000000L | lMethod);
        Log.log("\treserve = \"" + reserve + "\"", 0x80000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SGIPBind.wrap : wrap elements !", 0x80800000000000L);
        dump(0x800000000000L);
        addByte(login_type);
        addString(login_name, 16);
        addString(login_password, 16);
        addString(reserve, 8);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SGIPBind.unwrap : unwrap elements !", 0x80800000000000L);
        login_type = getByte();
        login_name = getString(16);
        login_password = getString(16);
        reserve = getString(8);
        dump(0x800000000000L);
    }



}