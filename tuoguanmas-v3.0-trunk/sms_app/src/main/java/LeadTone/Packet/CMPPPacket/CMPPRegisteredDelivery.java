package LeadTone.Packet.CMPPPacket;


/**
 * �μ�CMPPЭ��2.1����������״̬��������Ϣ
 */
public class CMPPRegisteredDelivery
{

    public CMPPRegisteredDelivery()
    {
    }

    public static String toString(byte registered_delivery)
    {
        switch(registered_delivery)
        {
        case 0:
            return "����Ҫ";

        case 1:
            return "��Ҫ";

        case 2:
            return "����SMC����";
        }
        return "����";
    }
}