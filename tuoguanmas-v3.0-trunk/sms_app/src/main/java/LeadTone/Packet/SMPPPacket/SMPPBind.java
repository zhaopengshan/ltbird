package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;


public class SMPPBind extends SMPPPacket
{
    public String system_id;
    public String password;
    public String system_type;
    public byte interface_version;
    public ESMEAddress esme;

    public SMPPBind()
    {
        system_id = null;
        password = null;
        system_type = null;
        interface_version = 52;
        esme = new ESMEAddress();
    }

    public SMPPBind(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
        system_id = null;
        password = null;
        system_type = null;
        interface_version = 52;
        esme = new ESMEAddress();
    }

    public SMPPBind(SMPPPacket packet)
    {
        super(packet);
        system_id = null;
        password = null;
        system_type = null;
        interface_version = 52;
        esme = new ESMEAddress();
    }

    public boolean isValid()
    {
        if(system_id != null && system_id.length() > 15)
        {
            Log.log("SMPPBind.isValid : invalid system_id !", 0x80600000000000L);
            return false;
        }
        if(password != null && password.length() > 8)
        {
            Log.log("SMPPBind.isValid : invalid password !", 0x80600000000000L);
            return false;
        }
        if(system_type != null && system_type.length() > 12)
        {
            Log.log("SMPPBind.isValid : invalid system_type !", 0x80600000000000L);
            return false;
        }
        if(!esme.isValid())
        {
            Log.log("SMPPBind.isValid : invalid ESME address !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tsystem_id = \"" + system_id + "\"", 0x80000000000000L | lMethod);
        Log.log("\tpassword = \"" + password + "\"", 0x80000000000000L | lMethod);
        Log.log("\tsystem_type = \"" + system_type + "\"", 0x80000000000000L | lMethod);
        esme.dump(lMethod);
        m_ops.dump(lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPBind.wrap : wrap elements !", 0x80800000000000L);
        dump(0x800000000000L);
        addCString(system_id);
        addCString(password);
        addCString(system_type);
        addByte(interface_version);
        esme.wrap(this);
        m_ops.wrap(this);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPBind.unwrap : unwrap elements !", 0x80800000000000L);
        system_id = getCString();
        password = getCString();
        system_type = getCString();
        interface_version = getByte();
        esme.unwrap(this);
        m_ops.unwrap(this);
        dump(0x800000000000L);
    }


}