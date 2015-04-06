package LeadTone.Packet.SMPPPacket;

import LeadTone.*;


public class DestinationAddress
{
    public static final int UNKNOWN = 0;
    public static final int SME_ADDRESS = 1;
    public static final int DISTRIBUTION_LIST_NAME = 2;
    public int dest_flag;
    public SMEAddress sme;
    public String dl_name;

    public DestinationAddress()
    {
        dest_flag = 0;
        sme = null;
        dl_name = null;
    }

    public DestinationAddress(String dl_name)
    {
        dest_flag = 0;
        sme = null;
        this.dl_name = null;
        dest_flag = 2;
        this.dl_name = dl_name;
    }

    public DestinationAddress(int TON, int NPI, String address)
    {
        dest_flag = 0;
        sme = null;
        dl_name = null;
        dest_flag = 1;
        sme = new SMEAddress(TON, NPI, address);
    }

    public DestinationAddress(SMEAddress sme)
    {
        dest_flag = 0;
        this.sme = null;
        dl_name = null;
        dest_flag = 1;
        this.sme = sme;
    }

    public static String toString(int dest_flag)
    {
        switch(dest_flag)
        {
        case 1:
            return "sme address";

        case 2:
            return "distribution list name";
        }
        return "reserved";
    }

    public boolean isValid()
    {
        if(dest_flag == 1)
        {
            if(sme == null || !sme.isValid())
                return false;
        } else
        if(dest_flag == 2 && dl_name != null && dl_name.length() > 20)
        {
            Log.log("DestinationAddress.isValid : invalid distribute list name !", 0x100600000000000L);
            return false;
        }
        return true;
    }

    public void dump(long lMethod)
    {
        Log.log("\tdest_flag = 0x" + Utility.toHexString(dest_flag) + " (" + toString(dest_flag) + ")", 0x100600000000000L);
        if(dest_flag == 1)
            sme.dump(lMethod);
        else
        if(dest_flag == 2)
            Log.log("\tdl_name = \"" + dl_name + "\"", 0x100600000000000L);
    }

    public void wrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("DestinationAddress.wrap : wrap elements !", 0x100800000000000L);
        dump(0x800000000000L);
        packet.addByte((byte)(dest_flag & 0xff));
        if(dest_flag == 1)
            sme.wrap(packet);
        else
        if(dest_flag == 2)
            packet.addCString(dl_name);
    }

    public void unwrap(SMPPPacket packet)
        throws BufferException
    {
        Log.log("DestinationAddress.unwrap : unwrap elements !", 0x100800000000000L);
        dest_flag = packet.getByte();
        if(dest_flag == 1)
        {
            sme = new SMEAddress();
            sme.unwrap(packet);
        } else
        if(dest_flag == 2)
            dl_name = packet.getCString();
    }



}