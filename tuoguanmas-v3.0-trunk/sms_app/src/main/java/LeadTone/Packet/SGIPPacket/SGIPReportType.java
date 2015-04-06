package LeadTone.Packet.SGIPPacket;


public class SGIPReportType
{

    public SGIPReportType()
    {
    }

    public static String toString(byte report_type)
    {
        switch(report_type)
        {
        case 0:
            return "submit\u547D\u4EE4\u7684\u72B6\u6001\u62A5\u544A";

        case 1: 
            return "\u524D\u8F6Cdeliver\u547D\u4EE4\u7684\u72B6\u6001\u62A5\u544A";
        }
        return "\u4FDD\u7559";
    }
}