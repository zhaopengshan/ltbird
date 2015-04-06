package LeadTone.Packet.CNGPPacket;



public class LoginType extends CNGPPacket
{

    public LoginType()
    {
    }

    public static String toString(int loginType)
    {
        switch(loginType)
        {
        case 0:
            return "\u53D1\u9001\u578B";

        case 1:
            return "\u63A5\u6536\u578B";

        case 2: 
            return "\u6536\u53D1\u578B";
        }
        return "\u672A\u77E5\u7C7B\u578B";
    }
}