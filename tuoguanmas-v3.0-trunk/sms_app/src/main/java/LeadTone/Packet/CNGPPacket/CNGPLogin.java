package LeadTone.Packet.CNGPPacket;

import LeadTone.*;


public class CNGPLogin extends CNGPPacket
{
    public String client_id;
    public byte authenticator_sp[];
    public byte login_type;
    public int timestamp;
    public byte version;

    public CNGPLogin()
    {
        super(1, 0);
        client_id = "";
        authenticator_sp = null;
        login_type = 2;
        timestamp = 0;
        version = 16;
    }

    public CNGPLogin(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
        client_id = "";
        authenticator_sp = null;
        login_type = 2;
        timestamp = 0;
        version = 16;
    }

    public CNGPLogin(CNGPPacket packet)
    {
        super(packet);
        client_id = "";
        authenticator_sp = null;
        login_type = 2;
        timestamp = 0;
        version = 16;
    }

    public boolean isValid()
    {
        if(authenticator_sp == null || authenticator_sp.length != 16)
        {
            Log.log("CMPPConnect.isValid : invalid authenticator_sp length !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tclient_id = \"" + client_id + "\"", 0x500000000000000L | lMethod);
        Log.log("\tauth_code = \"" + Utility.toHexString(authenticator_sp) + "\"", 0x500000000000000L | lMethod);
        Log.log("\tlogin_type = " + login_type + " (" + LoginType.toString(login_type) + ")", 0x500000000000000L | lMethod);
        Log.log("\ttimestamp = \"" + timestamp + "\"", 0x500000000000000L | lMethod);
        Log.log("\tversion = \"" + version + "\"", 0x500000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CNGPLogin.wrap : wrap elements !", 0x500800000000000L);
        dump(0x500000000000000L);
        addString(client_id, 10);
        addBytes(authenticator_sp);
        addByte(login_type);
        addInteger(timestamp);
        addByte(version);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CNGPLogin.unwrap : unwrap elements !", 0x500800000000000L);
        client_id = getString(10);
        authenticator_sp = getBytes(16);
        login_type = getByte();
        timestamp = getInteger();
        version = getByte();
        dump(0x800000000000L);
    }


}