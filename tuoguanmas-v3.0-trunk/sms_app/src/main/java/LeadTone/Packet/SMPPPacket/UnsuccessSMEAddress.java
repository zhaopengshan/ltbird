package LeadTone.Packet.SMPPPacket;

import LeadTone.*;


public class UnsuccessSMEAddress extends SMEAddress
{
    public int error_status_code;

    public UnsuccessSMEAddress()
    {
        error_status_code = 0;
    }

    public UnsuccessSMEAddress(String address)
    {
        super(address);
        error_status_code = 0;
    }

    public UnsuccessSMEAddress(int TON, int NPI, String address)
    {
        super(TON, NPI, address);
        error_status_code = 0;
    }

    public UnsuccessSMEAddress(int TON, int NPI, String address, int error_status_code)
    {
        super(TON, NPI, address);
        this.error_status_code = 0;
        this.error_status_code = error_status_code;
    }

    public String toString()
    {
        return new String(super.toString() + "[" + SMPPCommandStatus.toString(error_status_code) + "]");
    }

    public void dump(long lMethod)
    {
        Log.log("\tton = 0x" + Utility.toHexString(TON) + " (" + TypeOfNumber.toString(TON) + ")", 0x100000000000000L | lMethod);
        Log.log("\tnpi = 0x" + Utility.toHexString(NPI) + " (" + NumericPlanIndicator.toString(NPI) + ")", 0x100000000000000L | lMethod);
        Log.log("\taddress = \"" + address + "\"", 0x100000000000000L | lMethod);
        Log.log("\terror_status_code = 0x" + Utility.toHexString(error_status_code) + " (" + SMPPCommandStatus.toString(error_status_code) + ")", 0x100000000000000L | lMethod);
    }

    public void wrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("SMPPAddress.wrap : wrap SMPP address !", 0x100800000000000L);
        dump(0x800000000000L);
        packet.addByte((byte)(TON & 0xff));
        packet.addByte((byte)(NPI & 0xff));
        packet.addCString(address);
        packet.addInteger(error_status_code);
    }

    public void unwrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("SMPPAddress.unwrap : unwrap SMPP address !", 0x100800000000000L);
        TON = packet.getByte();
        NPI = packet.getByte();
        address = packet.getCString();
        error_status_code = packet.getInteger();
        dump(0x800000000000L);
    }


}