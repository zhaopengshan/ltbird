package LeadTone.Packet.SMPPPacket;

import LeadTone.*;


public class SMPPAddress extends Address
{
    public int TON;
    public int NPI;

    public SMPPAddress()
    {
        TON = 0;
        NPI = 0;
    }

    public SMPPAddress(String address)
    {
        super(address);
        TON = 0;
        NPI = 0;
    }

    public SMPPAddress(int TON, int NPI, String address)
    {
        super(address);
        this.TON = 0;
        this.NPI = 0;
        this.TON = TON;
        this.NPI = NPI;
    }

    public String toString()
    {
        return new String(super.toString() + "(" + TypeOfNumber.toString(TON) + "," + NumericPlanIndicator.toString(NPI) + ")");
    }

    public void dump(long lMethod)
    {
        Log.log("\tTON = 0x" + Utility.toHexString(TON) + " (" + TypeOfNumber.toString(TON) + ")", 0x100000000000000L | lMethod);
        Log.log("\tNPI = 0x" + Utility.toHexString(NPI) + " (" + NumericPlanIndicator.toString(NPI) + ")", 0x100000000000000L | lMethod);
        Log.log("\taddress = \"" + address + "\"", 0x100000000000000L | lMethod);
    }

    public void wrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("SMPPAddress.wrap : wrap SMPP address !", 0x100800000000000L);
        dump(0x800000000000L);
        packet.addByte((byte)(TON & 0xff));
        packet.addByte((byte)(NPI & 0xff));
        packet.addCString(address);
    }

    public void unwrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("SMPPAddress.unwrap : unwrap SMPP address !", 0x100800000000000L);
        TON = packet.getByte();
        NPI = packet.getByte();
        address = packet.getCString();
        dump(0x800000000000L);
    }


}