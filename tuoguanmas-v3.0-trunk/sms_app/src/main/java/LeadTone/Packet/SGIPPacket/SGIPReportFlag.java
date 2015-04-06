package LeadTone.Packet.SGIPPacket;


public class SGIPReportFlag
{

    public SGIPReportFlag()
    {
    }

    public static String toString(byte report_flag)
    {
        switch(report_flag)
        {
        case 0:
            return "\u8FD4\u56DE\u9519\u8BEF\u72B6\u6001";

        case 1:
            return "\u8FD4\u56DE\u72B6\u6001";

        case 2: 
            return "\u65E0\u9700\u72B6\u6001\u786E\u8BA4";
        }
        return "\u4FDD\u7559";
    }
}