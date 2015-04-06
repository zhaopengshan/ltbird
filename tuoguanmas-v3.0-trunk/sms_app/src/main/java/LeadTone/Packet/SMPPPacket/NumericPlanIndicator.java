package LeadTone.Packet.SMPPPacket;


public class NumericPlanIndicator
{

    public NumericPlanIndicator()
    {
    }

    public static String toString(int NPI)
    {
        switch(NPI)
        {
        case 0:
            return "unknown";

        case 1:
            return "ISDN";

        case 3:
            return "data";

        case 4:
            return "telex";

        case 6:
            return "land mobile";

        case 8:
            return "national";

        case 9:
            return "private";

        case 10:
            return "ERMES";

        case 14:
            return "internet";

        case 18:
            return "wap client id";

        case 2:
        case 5:
        case 7:
        case 11:
        case 12:
        case 13:
        case 15:
        case 16:
        case 17: 
        default:
            return "reserve";
        }
    }

    public static final int UNKNOWN = 0;
    public static final int ISDN = 1;
    public static final int DATA = 3;
    public static final int TELEX = 4;
    public static final int LAND_MOBILE = 6;
    public static final int NATIONAL = 8;
    public static final int PRIVATE = 9;
    public static final int ERMES = 10;
    public static final int INTERNET = 14;
    public static final int WAP_CLIENT_ID = 18;

}