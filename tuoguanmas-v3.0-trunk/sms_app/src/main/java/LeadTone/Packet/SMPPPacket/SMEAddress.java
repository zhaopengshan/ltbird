package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class SMEAddress extends SMPPAddress
{

    public SMEAddress()
    {
    }

    public SMEAddress(String address)
    {
        super(address);
    }

    public SMEAddress(int TON, int NPI, String address)
    {
        super(TON, NPI, address);
    }

    public boolean isValid()
    {
        if(address != null && address.length() > 20)
        {
            Log.log("SMEAddress.isValid : invalid address !", 0x100600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}