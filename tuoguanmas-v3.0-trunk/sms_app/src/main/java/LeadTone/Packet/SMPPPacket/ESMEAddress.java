package LeadTone.Packet.SMPPPacket;

import LeadTone.Log;



public class ESMEAddress extends SMPPAddress
{

    public ESMEAddress()
    {
    }

    public ESMEAddress(String address)
    {
        super(address);
    }

    public ESMEAddress(int TON, int NPI, String address)
    {
        super(TON, NPI, address);
    }

    public boolean isValid()
    {
        if(address != null && address.length() > 64)
        {
            Log.log("ESMEAddress.isValid : invalid address !", 0x100600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}