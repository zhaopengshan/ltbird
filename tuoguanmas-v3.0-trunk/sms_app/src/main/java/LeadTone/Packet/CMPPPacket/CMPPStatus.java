package LeadTone.Packet.CMPPPacket;


/**
 * �μ�CMPPЭ��2.1�����彨�����ӽ����������Ϣ
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
            return "��ȷ";

        case 1:
            return "��Ч����Ϣ��";

        case 2:
            return "��Ч��SP_ID";

        case 3:
            return "SP��֤��";

        case 4:
            return "�汾̫��";
        }
        return "��������";
    }
}