package LeadTone.Packet.CMPPPacket;


/**
 * 参见CMPP协议2.1，定义计费方式的描述信息
 */
public class CMPPFeeType
{

    public CMPPFeeType()
    {
    }

    public static String toString(byte fee_type)
    {
        switch(fee_type)
        {
        case 0:
            return "核对信道费";

        case 1:
            return "免费";

        case 2:
            return "按条";

        case 3:
            return "包月";

        case 4:
            return "封顶";

        case 5:
            return "SP收费";
        }
        return "保留";
    }

    public static String toString(String fee_type)
    {
        if(fee_type == null || fee_type.length() <= 0)
            return "缺省";
        if(fee_type.equals("00"))
            return "核对信道费";
        if(fee_type.equals("01"))
            return "免费";
        if(fee_type.equals("02"))
            return "按条计费";
        if(fee_type.equals("03"))
            return "包月";
        if(fee_type.equals("04"))
            return "封顶";
        if(fee_type.equals("05"))
            return "由SP收费";
        else
            return "未知";
    }
}