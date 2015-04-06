package LeadTone.Packet.SMPPPacket;


public class TypeOfNumber
{

    public static final int UNKNOWN = 0;
    public static final int INTERNATIONAL = 1;
    public static final int NATIONAL = 2;
    public static final int NETWORK_SPECIFIC = 3;
    public static final int SUBSCRIBER_NUMBER = 4;
    public static final int ALPHANUMBERIC = 5;
    public static final int ABBREVIATED = 6;

    public TypeOfNumber()
    {
    }

    public static String toString(int TON)
    {
        switch(TON)
        {
        case 0:
            return "unknown";

        case 1:
            return "international";

        case 2:
            return "national";

        case 3:
            return "network specific";

        case 4:
            return "subscriber number";

        case 5:
            return "alphanumberic";

        case 6:
            return "abbreviated";
        }
        return "reserved";
    }



}