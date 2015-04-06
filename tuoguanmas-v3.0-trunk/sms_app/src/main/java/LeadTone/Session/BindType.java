package LeadTone.Session;


public class BindType
{
    /**
     * ģ������Ĭ��Ϊ�ȷ���Ҳ����
     */
    public static final int BIND_MASK = 3;
    /**
     * ����������ģʽ
     */
    public static final int BIND_ACTIVATER = 0;
    /**
     * ֻ����
     */
    public static final int BIND_TRANSMITTER = 1;
    /**
     * ֻ����
     */
    public static final int BIND_RECEIVER = 2;
    /**
     * �ȷ���Ҳ����
     */
    public static final int BIND_TRANSCEIVER = 3;

    public BindType()
    {
    }

    /**
     * �ж��Ƿ�Ϊֻ���ͣ���3�������������4 & 3���Ϊ0��Ŀ���ǹ����������ͣ�����������չ
     * @param nType �Ự���ʹ���
     * @return �����Ƿ�Ϊֻ�������͵Ĳ���ֵ
     */
    public static boolean isTransmitter(int nType)
    {
        return (nType & 3) == 1;
    }

    public static boolean isReceiver(int nType)
    {
        return (nType & 3) == 2;
    }

    public static boolean isTransceiver(int nType)
    {
        return (nType & 3) == 3;
    }

    public static boolean isActivater(int nType)
    {
        return (nType & 3) == 0;
    }

    public static boolean forTransmitter(int nType)
    {
        return isTransmitter(nType) || isTransceiver(nType);
    }

    public static boolean forReceiver(int nType)
    {
        return isReceiver(nType) || isTransceiver(nType) || isActivater(nType);
    }

    /**
     * �õ���Ӧ�����ع�����������
     * @param nType ���ʹ���
     * @return �������ع�����������
     */
    public static String toString(int nType)
    {
        switch(nType)
        {
        case 0:
            return "activater";

        case 1:
            return "transmitter";

        case 2:
            return "receiver";

        case 3: 
            return "transceiver";
        }
        return "unknown";
    }


    /**
     * �õ���Ӧ�����ع������ʹ���
     * @param strType  ��������
     * @return �������ع������ʹ���
     */
    public static int toType(String strType)
    {
        if(strType.equals("activater"))
            return 0;
        if(strType.equals("transmitter"))
            return 1;
        if(strType.equals("receiver"))
            return 2;
        return !strType.equals("transceiver") ? 3 : 3;
    }



}
