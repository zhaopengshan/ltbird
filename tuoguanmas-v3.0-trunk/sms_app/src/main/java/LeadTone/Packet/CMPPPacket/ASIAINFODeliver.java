package LeadTone.Packet.CMPPPacket;


/**
 * �μ�CMPPЭ��2.1�Խ�����Ϣ�Ķ��壬��Ϊ���Ŷ�CMPP��ʵ�֣���ο����Ŷ�CMPPЭ��ʵ���е�������
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
