package LeadTone.Packet.CMPPPacket;


/**
 * �μ�CMPPЭ��2.1����Ϊ����CMPPЭ���ж������Ϣָ��Ķ��壬ʵ����Ϣ���๦��
 */
public class CMPPCommandID
{
    /**
     * �Զ����ֶΣ�����ģ���������лظ���Ϣ
     */
    public static final int CMPP_RESPONSE_MASK = 0x80000000;
    /**
     * �Զ����ֶΣ�����ģ���������лظ���Ϣ
     */
    public static final int CMPP_NACK_RESPONSE = 0x80000000;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_CONNECT = 0x00000001;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_CONNECT_RESPONSE = 0x80000001;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_TERMINATE = 0x00000002;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_TERMINATE_RESPONSE = 0x80000002;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_SUBMIT = 0x00000004;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_SUBMIT_RESPONSE = 0x80000004;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_DELIVER = 0x00000005;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_DELIVER_RESPONSE = 0x80000005;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_QUERY = 0x00000006;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_QUERY_RESPONSE = 0x80000006;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_CANCEL = 0x00000007;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_CANCEL_RESPONSE = 0x80000007;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_ACTIVETEST = 0x00000008;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_ACTIVETEST_RESPONSE = 0x80000008;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_FORWARD = 0x00000009;
    /**
     * �ο�CMPPЭ��2.1
     */
    public static final int CMPP_FORWARD_RESPONSE = 0x80000009;

    /**
     * �Զ��屣���ֶΣ���CMPPЭ����δ����
     */
    public static final int CMPP_RESERVE = 0x00000003;
    /**
     * �Զ��屣���ֶΣ���CMPPЭ����δ����
     */
    public static final int CMPP_RESERVE_RESPONSE = 0x80000003;


    public CMPPCommandID()
    {
    }

    /**
     * �ж��Ƿ����ڻظ�����Ϣ
     * @param command_id ��Ϣ����
     * @return �����Ƿ�Ϊ�ظ�����Ϣ�Ĳ���ֵ
     */
    public static boolean isResponse(int command_id)
    {
        switch(command_id)
        {
        case CMPP_RESPONSE_MASK:
        case CMPP_CONNECT_RESPONSE:
        case CMPP_TERMINATE_RESPONSE:
        case CMPP_SUBMIT_RESPONSE:
        case CMPP_DELIVER_RESPONSE:
        case CMPP_QUERY_RESPONSE:
        case CMPP_CANCEL_RESPONSE:
        case CMPP_ACTIVETEST_RESPONSE:
            return true;
        //�����ֶΣ�CMPPЭ����δ����
        case CMPP_RESERVE_RESPONSE:
        default:
            return false;
        }
    }

    /**
     * �ж��Ƿ�������������Ϣ
     * @param command_id ��Ϣ����
     * @return �����Ƿ�Ϊ��������Ϣ�Ĳ���ֵ
     */
    public static boolean isRequest(int command_id)
    {
        switch(command_id)
        {
        case CMPP_CONNECT:
        case CMPP_TERMINATE:
        case CMPP_SUBMIT:
        case CMPP_DELIVER:
        case CMPP_QUERY:
        case CMPP_CANCEL:
        case CMPP_ACTIVETEST:
            return true;
        //�����ֶΣ�CMPPЭ����δ����
        case CMPP_RESERVE:
        default:
            return false;
        }
    }

    /**
     * �ж���Ϣָ���Ƿ�Ϸ����ж������������ͻ������ڻظ�������Ϸ�
     * @param command_id ��Ϣָ��
     * @return ������Ϣָ���Ƿ�Ϸ��Ĳ���ֵ
     */
    public static boolean isValid(int command_id)
    {
        return isRequest(command_id) || isResponse(command_id);
    }

    /**
     * �ж���Ϣָ���Ƿ��Ƕ���Ϣ�����Ϣ����MO��MT��MO���ж��ţ�MT���ж��ţ�
     * @param command_id
     * @return �����Ƿ����ڶ���Ϣ�����Ϣ���Ĳ���ֵ
     */
    public static boolean isMessage(int command_id)
    {
        switch(command_id)
        {
        case CMPP_SUBMIT_RESPONSE:
        case CMPP_DELIVER_RESPONSE:
        case CMPP_SUBMIT:
        case CMPP_DELIVER:
            return true;
        }
        return false;
    }

    /**
     * �ж���Ϣָ���Ƿ����ڵ�����ģʽ�����������
     * @param command_id  ��Ϣָ��
     * @return ������Ϣָ���Ƿ����ڵ�����ģʽ����������͵Ĳ���ֵ
     */
    public static boolean isTransmitterOutput(int command_id)
    {
        switch(command_id)
        {
        case CMPP_TERMINATE_RESPONSE:
        case CMPP_ACTIVETEST_RESPONSE:
        case CMPP_TERMINATE:
        case CMPP_SUBMIT:
        case CMPP_QUERY:
        case CMPP_CANCEL:
        case CMPP_ACTIVETEST:
            return true;
        }
        return false;
    }

    /**
     * �ж���Ϣָ���Ƿ����ڵ�����ģʽ�����������
     * @param command_id ��Ϣָ��
     * @return ������Ϣָ���Ƿ����ڵ�����ģʽ����������͵Ĳ���ֵ
     */
    public static boolean isTransmitterInput(int command_id)
    {
        switch(command_id)
        {
        case CMPP_TERMINATE_RESPONSE:
        case CMPP_SUBMIT_RESPONSE:
        case CMPP_QUERY_RESPONSE:
        case CMPP_CANCEL_RESPONSE:
        case CMPP_ACTIVETEST_RESPONSE:
        case CMPP_TERMINATE:
        case CMPP_ACTIVETEST:
            return true;
        }
        return false;
    }

    /**
     * �ж���Ϣָ���Ƿ����ڵ�����ģʽ�����������
     * @param command_id ��Ϣָ��
     * @return  ������Ϣָ���Ƿ����ڵ�����ģʽ����������͵Ĳ���ֵ
     */
    public static boolean isReceiverOutput(int command_id)
    {
        switch(command_id)
        {
        case CMPP_TERMINATE_RESPONSE:
        case CMPP_DELIVER_RESPONSE:
        case CMPP_ACTIVETEST_RESPONSE:
        case CMPP_TERMINATE:
        case CMPP_ACTIVETEST:
            return true;
        }
        return false;
    }

    /**
     * �ж���Ϣָ���Ƿ����ڵ�����ģʽ�����������
     * @param command_id ��Ϣָ��
     * @return  ������Ϣָ���Ƿ����ڵ�����ģʽ����������͵Ĳ���ֵ
     */
    public static boolean isReceiverInput(int command_id)
    {
        switch(command_id)
        {
        case CMPP_TERMINATE_RESPONSE:
        case CMPP_ACTIVETEST_RESPONSE:
        case CMPP_TERMINATE:
        case CMPP_DELIVER:
        case CMPP_ACTIVETEST:
            return true;
        }
        return false;
    }

    /**
     * ��Ϣָ�����������ת������
     * @param command_id  ��Ϣָ��
     * @return ����Ϣָ���Ӧ����������
     */
    public static String toString(int command_id)
    {
        switch(command_id)
        {
        case CMPP_NACK_RESPONSE:
            return "cmpp_nack_response";

        case CMPP_CONNECT:
            return "cmpp_connect";

        case CMPP_CONNECT_RESPONSE:
            return "cmpp_connect_response";

        case CMPP_TERMINATE:
            return "cmpp_terminate";

        case CMPP_TERMINATE_RESPONSE:
            return "cmpp_terminate_response";

        case CMPP_SUBMIT:
            return "cmpp_submit";

        case CMPP_SUBMIT_RESPONSE:
            return "cmpp_submit_response";

        case CMPP_DELIVER:
            return "cmpp_deliver";

        case CMPP_DELIVER_RESPONSE:
            return "cmpp_deliver_response";

        case CMPP_QUERY:
            return "cmpp_query";

        case CMPP_QUERY_RESPONSE:
            return "cmpp_query_response";

        case CMPP_CANCEL:
            return "cmpp_cancel";

        case CMPP_CANCEL_RESPONSE:
            return "cmpp_cancel_response";

        case CMPP_ACTIVETEST:
            return "cmpp_activetest";

        case CMPP_ACTIVETEST_RESPONSE:
            return "cmpp_activetest_response";
        }
        return "reserved";
    }



}