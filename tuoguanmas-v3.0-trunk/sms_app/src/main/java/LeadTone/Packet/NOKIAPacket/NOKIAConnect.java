package LeadTone.Packet.NOKIAPacket;

import LeadTone.*;


public class NOKIAConnect extends NOKIAPacket
{
    public static final byte BIND_SEND = 0;
    public static final byte BIND_RECEIVE = 1;
    public String source_addr;
    public byte authenticator_sp[];
    public byte bind_type;
    public byte version;
    public int time_stamp;

    public NOKIAConnect(int sequence_id)
    {
        super(1, sequence_id);
        source_addr = null;
        authenticator_sp = null;
        bind_type = 0;
        version = 18;
        time_stamp = 0;
    }

    public NOKIAConnect(NOKIAPacket packet)
    {
        super(packet);
        source_addr = null;
        authenticator_sp = null;
        bind_type = 0;
        version = 18;
        time_stamp = 0;
    }

    public boolean isValid()
    {
        if(command_id != 1)
        {
            Log.log("NOKIAConnect.isValid : not a CMPP_CONNECT command !", 0x80600000000000L);
            return false;
        }
        if(source_addr != null && source_addr.length() > 6)
        {
            Log.log("NOKIAConnect.isValid : invalid source_addr length !", 0x80600000000000L);
            return false;
        }
        if(authenticator_sp == null || authenticator_sp.length != 16)
        {
            Log.log("NOKIAConnect.isValid : invalid authenticator_sp length !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public String toString(byte bind_type)
    {
        switch(bind_type)
        {
        case 0:
            return "\u53D1\u9001";

        case 1:
            return "\u63A5\u6536";
        }
        return "\u4FDD\u7559";
    }

    public void dump(long lMethod)
    {
        Log.log("\tsource_addr = \"" + source_addr + "\"", 0x80000000000000L | lMethod);
        Log.log("\tauthenticator_sp = 0x" + Utility.toHexString(authenticator_sp), 0x80000000000000L | lMethod);
        Log.log("\tbind_type = " + bind_type + " (" + toString(bind_type) + ")", 0x80000000000000L | lMethod);
        Log.log("\tversion = 0x" + Utility.toHexString(version), 0x80000000000000L | lMethod);
        Log.log("\ttime_stamp = 0x" + Utility.toHexString(time_stamp), 0x80000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("NOKIAConnect.wrap : wrap elements !", 0x80800000000000L);
        dump(0x800000000000L);
        addCString(source_addr);
        addBytes(authenticator_sp);
        addByte(bind_type);
        addByte(version);
        addInteger(time_stamp);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("NOKIAConnect.unwrap : unwrap elements !", 0x80800000000000L);
        source_addr = getCString();
        authenticator_sp = getBytes(16);
        bind_type = getByte();
        version = getByte();
        time_stamp = getInteger();
        dump(0x800000000000L);
    }



}