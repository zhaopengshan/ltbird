package LeadTone.Packet.CMPPPacket;

/**
 * �μ�CMPPЭ��2.1������ת����Ϣ���͵�������Ϣ
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
            return "MTǰת";

        case 2:
            return "״̬����";
        }
        return "����";
    }
}