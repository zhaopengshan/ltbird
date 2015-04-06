package LeadTone.Packet.SGIPPacket;


public class SGIPReportState
{

    public SGIPReportState()
    {
    }

    public static String toString(byte state)
    {
        switch(state)
        {
        case 0:
            return "\u53D1\u9001\u6210\u529F | DELIVERED";

        case 1:
            return "\u7B49\u5F85\u5931\u8D25 | ENROUTE,ACCEPTED";

        case 2: 
            return "\u53D1\u9001\u5931\u8D25 | EXPIRED,DELETED,UNDELIVERABLE,UNKNOWN,REJECTED";
        }
        return "\u4FDD\u7559";
    }
}