package LeadTone.Packet.CNGPPacket;


public class CNGPFeeType
{

    public CNGPFeeType()
    {
    }

    public static String toString(String feeType)
    {
        if(feeType.equals("00"))
            return "\u514D\u8D39";
        if(feeType.equals("01"))
            return "\u6309\u6761\u6536\u8D39";
        if(feeType.equals("02"))
            return "\u5305\u6708";
        if(feeType.equals("03"))
            return "\u5C01\u9876";
        if(feeType.equals("04"))
            return "\u5305\u6708\u6263\u8D39\u8BF7\u6C42";
        if(feeType.equals("05"))
            return "CR\u8BDD\u5355";
        else
            return "reverse";
    }

    public static String toString(byte feeType)
    {
        switch(feeType)
        {
        case 0:
            return "\u514D\u8D39";

        case 1:
            return "\u6309\u6761\u6536\u8D39";

        case 2:
            return "\u5305\u6708";

        case 3:
            return "\u5C01\u9876";

        case 4:
            return "\u5305\u6708\u6263\u8D39\u8BF7\u6C42";

        case 5: 
            return "CR\u8BDD\u5355";
        }
        return "reverse";
    }

    public static void main(String args[])
    {
        CNGPFeeType CNGPFeeType1 = new CNGPFeeType();
    }
}