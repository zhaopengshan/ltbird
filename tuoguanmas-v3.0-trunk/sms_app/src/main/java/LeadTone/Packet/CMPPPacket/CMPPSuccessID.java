package LeadTone.Packet.CMPPPacket;


/**
 * �μ�CMPPЭ��2.1���������Ϣ����״̬��������Ϣ
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
            return "�ɹ�";

        case 1:
            return "ʧ��";
        }
        return "����";
    }
}