package LeadTone.Packet.CMPPPacket;

/**
 * �μ�CMPPЭ��2.1���������Ϣ�������͵�������Ϣ
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
            return "ASCII��";

        case 3:
            return "����Ϣд������";

        case 4:
            return "��������Ϣ";

        case 8:
            return "UCS2����";

        case 15:
            return "��GB����";
        }
        return "����";
    }
}