package LeadTone.Packet.CMPPPacket;


/**
 * �μ�CMPPЭ��2.1������ƷѶ����������Ϣ
 */
public class CMPPFeeUserType
{

    public CMPPFeeUserType()
    {
    }

    public static String toString(byte fee_usertype)
    {
        switch(fee_usertype)
        {
        case 0:
            return "��Ŀ���ն�MSISDN�Ʒ�";

        case 1:
            return "��Դ�ն�MSISDN�Ʒ�";

        case 2:
            return "��SP�Ʒ�";

        case 3:
            return "���ֶ���Ч";
        }
        return "����";
    }
}