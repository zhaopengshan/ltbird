package LeadTone.Packet.CMPPPacket;


/**
 * 参见CMPP协议2.1，定义短消息发送状态的描述信息
 */
public class CMPPSuccessID
{

    public CMPPSuccessID()
    {
    }

    public static String toString(int success_id)
    {
        switch(success_id)
        {
        case 0:
            return "成功";

        case 1:
            return "失败";
        }
        return "其他";
    }
}