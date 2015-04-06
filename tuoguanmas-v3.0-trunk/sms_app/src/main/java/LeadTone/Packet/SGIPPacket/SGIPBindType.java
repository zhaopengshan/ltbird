package LeadTone.Packet.SGIPPacket;


public class SGIPBindType
{

    public SGIPBindType()
    {
    }

    public static String toString(byte login_type)
    {
        switch(login_type)
        {
        case 1:
            return "\u53D1\u9001\u547D\u4EE4\uFF08SP->SMG\uFF09";

        case 2:
            return "\u53D1\u9001\u547D\u4EE4\uFF08SP<-SMG\uFF09";

        case 3:
            return "\u8F6C\u53D1\u547D\u4EE4\uFF08SMG<->SMG\uFF09";

        case 4:
            return "\u8DEF\u7531\u8868\u68C0\u7D22\u4E0E\u7EF4\u62A4\uFF08SMG->GNS\uFF09";

        case 5:
            return "\u8DEF\u7531\u8868\u66F4\u65B0\uFF08SMG<-GNS\uFF09";

        case 6:
            return "\u8DEF\u7531\u8868\u540C\u6B65\uFF08GNS<->GNS\uFF09";

        case 7: 
            return "\u8DDF\u8E2A\u6D4B\u8BD5\uFF08SP->SMG,SMG<->SMG\uFF09";
        }
        return "\u4FDD\u7559";
    }
}