package LeadTone.Packet.CMPPPacket;


/**
 * 参见CMPP协议2.1对接收消息的定义，此为亚信对CMPP的实现，请参考亚信对CMPP协议实现中的特殊性
 */
public class ASIAINFODeliver extends CMPPDeliver
{

    public ASIAINFODeliver(int sequence_id)
    {
        super(sequence_id);
    }

    public ASIAINFODeliver(CMPPPacket packet)
    {
        super(packet);
    }
}
