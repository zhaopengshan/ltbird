package LeadTone.Packet.CNGPPacket;


public class CNGPFeeUserType
{

    public CNGPFeeUserType()
    {
    }

    public static String toString(String fee_user_type)
    {
        if(fee_user_type.equals("0"))
            return "\u5BF9\u76EE\u7684\u7EC8\u7AEF\u6536\u8D39";
        if(fee_user_type.equals("1"))
            return "\u5BF9\u6E90\u7EC8\u7AEF\u8BA1\u8D39";
        if(fee_user_type.equals("2"))
            return "\u5BF9SP\u8BA1\u8D39";
        if(fee_user_type.equals("3"))
            return "\u5BF9\u8BA1\u8D39\u7528\u6237\u53F7\u7801\u8BA1\u8D39";
        else
            return "reverse";
    }

    public static String toString(byte fee_user_type)
    {
        if(fee_user_type == 0)
            return "\u5BF9\u76EE\u7684\u7EC8\u7AEF\u6536\u8D39";
        if(fee_user_type == 1)
            return "\u5BF9\u6E90\u7EC8\u7AEF\u8BA1\u8D39";
        if(fee_user_type == 2)
            return "\u5BF9SP\u8BA1\u8D39";
        if(fee_user_type == 3)
            return "\u5BF9\u8BA1\u8D39\u7528\u6237\u53F7\u7801\u8BA1\u8D39";
        else
            return "reverse";
    }

    public static void main(String args[])
    {
        CNGPFeeUserType CNGPFeeUserType1 = new CNGPFeeUserType();
    }
}