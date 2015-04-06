package LeadTone.Packet.CMPPPacket;


/**
 * 参见CMPP协议2.1，定义计费对象的描述信息
 */
public class CMPPFeeUserType
{

    public CMPPFeeUserType()
    {
    }

    public static String toString(byte fee_usertype)
    {
        switch(fee_usertype)
        {
        case 0:
            return "对目的终端MSISDN计费";

        case 1:
            return "对源终端MSISDN计费";

        case 2:
            return "对SP计费";

        case 3:
            return "本字段无效";
        }
        return "保留";
    }
}