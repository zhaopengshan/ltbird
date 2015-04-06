package LeadTone.Packet.CMPPPacket;

/**
 * 参见CMPP协议2.1，定义短消息编码类型的描述信息
 */
public class CMPPMessageFormat
{

    public CMPPMessageFormat()
    {
    }

    public static String toString(byte msg_fmt)
    {
        switch(msg_fmt)
        {
        case 0:
            return "ASCII串";

        case 3:
            return "短信息写卡操作";

        case 4:
            return "二进制信息";

        case 8:
            return "UCS2编码";

        case 15:
            return "含GB汉字";
        }
        return "保留";
    }
}