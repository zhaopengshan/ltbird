package LeadTone.Packet.CMPPPacket;

/**
 * �μ�CMPPЭ��2.1�������ѯ��ʽ��������Ϣ
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
            return "������ѯ";

        case 1:
            return "��ҵ������ѯ";
        }
        return "����";
    }
}