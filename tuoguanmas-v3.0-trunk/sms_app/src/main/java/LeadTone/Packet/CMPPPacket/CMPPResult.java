package LeadTone.Packet.CMPPPacket;

/**
 * 参见CMPP协议2.1，定义发送消息提交网关结果的描述信息
 */
public class CMPPResult
{

    public CMPPResult()
    {
    }

    public static String toString(byte result)
    {
        switch(result)
        {
        case 0:
            return "正确";

        case 1:
            return "消息结果错";

        case 2:
            return "命令字错";

        case 3:
            return "消息序号重复";

        case 4:
            return "消息长度错";

        case 5:
            return "资费代码错";

        case 6:
            return "超过最带信息长";

        case 7:
            return "业务代码错";

        case 8:
            return "流量控制错";
        }
        return "其他错误";
    }
}