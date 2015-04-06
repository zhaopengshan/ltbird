package LeadTone.Packet.CMPPPacket;

/**
 * 参见CMPP协议2.1，定义转发消息类型的描述信息
 */
public class CMPPForwardType
{

    public CMPPForwardType()
    {
    }

    public static String toString(byte msg_fwd_type)
    {
        switch(msg_fwd_type)
        {
        case 0:
            return "MT前转";

        case 2:
            return "状态报告";
        }
        return "保留";
    }
}