package LeadTone.Packet.CMPPPacket;


/**
 * 参见CMPP协议2.1，定义建立连接结果的描述信息
 */
public class CMPPStatus
{

    public CMPPStatus()
    {
    }

    public static String toString(int status)
    {
        switch(status)
        {
        case 0:
            return "正确";

        case 1:
            return "无效的消息体";

        case 2:
            return "无效的SP_ID";

        case 3:
            return "SP认证错";

        case 4:
            return "版本太高";
        }
        return "其他错误";
    }
}