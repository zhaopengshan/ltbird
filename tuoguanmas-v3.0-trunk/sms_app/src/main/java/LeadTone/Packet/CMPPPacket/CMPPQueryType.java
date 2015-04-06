package LeadTone.Packet.CMPPPacket;

/**
 * 参见CMPP协议2.1，定义查询方式的描述信息
 */
public class CMPPQueryType
{

    public CMPPQueryType()
    {
    }

    public static String toString(byte query_type)
    {
       switch(query_type)
        {
        case 0:
            return "总数查询";

        case 1:
            return "按业务代码查询";
        }
        return "保留";
    }
}