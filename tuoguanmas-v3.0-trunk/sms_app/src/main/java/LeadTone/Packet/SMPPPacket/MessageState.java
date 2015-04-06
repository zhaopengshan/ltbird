package LeadTone.Packet.SMPPPacket;


public class MessageState
{
    public static final int ENROUTE = 1;
    public static final int DELIVERED = 2;
    public static final int EXPIRED = 3;
    public static final int DELETED = 4;
    public static final int UNDELIVERABLE = 5;
    public static final int ACCEPTED = 6;
    public static final int UNKNOWN = 7;
    public static final int REJECTED = 8;

    public MessageState()
    {
    }

    public static String toString(int message_state)
    {
        switch(message_state)
        {
        case 1:
            return "enroute";

        case 2:
            return "delivered";

        case 3:
            return "expired";

        case 4:
            return "deleted";

        case 5:
            return "undeliverable";

        case 6:
            return "accepted";

        case 7:
            return "unknown";

        case 8:
            return "rejected";
        }
        return "reserved";
    }



}