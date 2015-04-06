package LeadTone.Packet.CMPPPacket;


/**
 * �μ�CMPPЭ��2.1������Ʒѷ�ʽ��������Ϣ
 */
public class CMPPFeeType
{

    public CMPPFeeType()
    {
    }

    public static String toString(byte fee_type)
    {
        switch(fee_type)
        {
        case 0:
            return "�˶��ŵ���";

        case 1:
            return "���";

        case 2:
            return "����";

        case 3:
            return "����";

        case 4:
            return "�ⶥ";

        case 5:
            return "SP�շ�";
        }
        return "����";
    }

    public static String toString(String fee_type)
    {
        if(fee_type == null || fee_type.length() <= 0)
            return "ȱʡ";
        if(fee_type.equals("00"))
            return "�˶��ŵ���";
        if(fee_type.equals("01"))
            return "���";
        if(fee_type.equals("02"))
            return "�����Ʒ�";
        if(fee_type.equals("03"))
            return "����";
        if(fee_type.equals("04"))
            return "�ⶥ";
        if(fee_type.equals("05"))
            return "��SP�շ�";
        else
            return "δ֪";
    }
}