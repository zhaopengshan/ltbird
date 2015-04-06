package LeadTone.Packet.SGIPPacket;


public class SGIPUserCondition
{

    public SGIPUserCondition()
    {
    }

    public static String toString(byte user_condition)
    {
        switch(user_condition)
        {
        case 0:
            return "\u6CE8\u9500";

        case 1:
            return "\u6B20\u8D39\u505C\u673A";

        case 2: 
            return "\u6062\u590D\u6B63\u5E38";
        }
        return "\u4FDD\u7559";
    }
}