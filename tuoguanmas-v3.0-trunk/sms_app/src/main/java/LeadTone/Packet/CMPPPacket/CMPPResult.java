package LeadTone.Packet.CMPPPacket;

/**
 * �μ�CMPPЭ��2.1�����巢����Ϣ�ύ���ؽ����������Ϣ
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
            return "��ȷ";

        case 1:
            return "��Ϣ�����";

        case 2:
            return "�����ִ�";

        case 3:
            return "��Ϣ����ظ�";

        case 4:
            return "��Ϣ���ȴ�";

        case 5:
            return "�ʷѴ����";

        case 6:
            return "���������Ϣ��";

        case 7:
            return "ҵ������";

        case 8:
            return "�������ƴ�";
        }
        return "��������";
    }
}