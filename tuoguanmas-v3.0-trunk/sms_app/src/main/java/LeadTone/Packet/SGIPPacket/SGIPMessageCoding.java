package LeadTone.Packet.SGIPPacket;


public class SGIPMessageCoding
{

    public SGIPMessageCoding()
    {
    }

    public static String toString(byte message_coding)
    {
        switch(message_coding)
        {
        case 0:
            return "ASCII\u5B57\u7B26\u4E32";

        case 3:
            return "\u5199\u5361\u64CD\u4F5C";

        case 4:
            return "\u4E8C\u8FDB\u5236\u7F16\u7801";

        case 8:
            return "UCS2\u7F16\u7801";

        case 15: 
            return "GBK\u7F16\u7801";
        }
        return "\u4FDD\u7559";
    }
}