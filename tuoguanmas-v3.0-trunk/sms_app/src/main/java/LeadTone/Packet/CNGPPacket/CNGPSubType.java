package LeadTone.Packet.CNGPPacket;


public class CNGPSubType
{

    public CNGPSubType()
    {
    }

    public static String toString(byte subType)
    {
        switch(subType)
        {
        case 0:
            return "\u53D6\u6D88\u8BA2\u9605";

        case 1:
            return "\u8BA2\u9605\u6216\u70B9\u64AD\u8BF7\u6C42";

        case 2:
            return "\u70B9\u64AD\u4E0B\u53D1";

        case 3: 
            return "\u8BA2\u9605\u4E0B\u53D1";
        }
        return "\u4FDD\u7559";
    }

    public static void main(String args[])
    {
        CNGPSubType CNGPSubType1 = new CNGPSubType();
    }
}