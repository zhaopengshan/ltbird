package LeadTone.Packet.SGIPPacket;


public class SGIPFeeType
{

    public SGIPFeeType()
    {
    }

    public static String toString(int nFeeType)
    {
        switch(nFeeType)
        {
        case 0:
            return "\u6838\u68C0SP\u4FE1\u9053\u8D39\u7528";

        case 1:
            return "\u514D\u8D39";

        case 2:
            return "\u6309\u6761";

        case 3:
            return "\u5305\u6708";

        case 4: 
            return "SP\u6536\u8D39";
        }
        return "\u4FDD\u7559";
    }
}