package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class Address
{

    public Address()
    {
        address = null;
    }

    public Address(String address)
    {
        this.address = null;
        this.address = address;
    }

    public boolean isValid()
    {
        return true;
    }

    public String toString()
    {
        return address;
    }

    public void dump(long lMethod)
    {
        Log.log("\taddress = \"" + address + "\"", 0x100000000000000L | lMethod);
    }

    public void wrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("Address.wrap : wrap address !", 0x100800000000000L);
        dump(0x800000000000L);
        packet.addCString(address);
    }

    public void unwrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("Address.unwrap : unwrap address !", 0x100800000000000L);
        address = packet.getCString();
        dump(0x800000000000L);
    }

    public String address;
}