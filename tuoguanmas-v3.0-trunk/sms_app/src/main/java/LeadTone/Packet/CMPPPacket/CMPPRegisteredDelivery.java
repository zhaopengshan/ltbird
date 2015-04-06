package LeadTone.Packet.CMPPPacket;


/**
 * 参见CMPP协议2.1，定义收条状态的描述信息
 */
public class CMPPRegisteredDelivery
{

    public CMPPRegisteredDelivery()
    {
    }

    public static String toString(byte registered_delivery)
    {
        switch(registered_delivery)
        {
        case 0:
            return "不需要";

        case 1:
            return "需要";

        case 2:
            return "产生SMC话单";
        }
        return "保留";
    }
}