package LeadTone;

/**
 * ʱ�����������
 */
public class TimeConfig
{

    public TimeConfig()
    {
    }

    /**
     *  һ����
     */
    public static final long TIME_ONE_SECOND = 1000L;
    /**
     *  һ����
     */
    public static final long TIME_ONE_MINUTE = 60000L;
    /**
     *  ʮ�����
     */
    public static final long TIME_FIFTEEN_MINUTES = 900000L;
    /**
     *  һСʱ
     */
    public static final long TIME_ONE_HOUR = 3600000L;
    /**
     *  ��Сʱ
     */
    public static final long TIME_HALF_HOUR = 1800000L;
    /**
     * һ��
     */
    public static final long TIME_ONE_DAY = 86400000L;
    /**
     * ����
     */
    public static final long TIME_HALF_DAY = 43200000L;
    /**
     *  һ����
     */
    public static final long TIME_ONE_WEEK = 604800000L;
    /**
     *  һ����(30��)
     */
    public static final long TIME_ONE_MONTH = 2592000000L;
    /**
     * ��Ϣ��������� Ĭ��ֵ30����
     */
    public static long DEFAULT_SURVIVE_TIME = 30000L;
    /**
     *  �ȴ��߳�����ʱ�� Ĭ��ֵ10����
     */
    public static long DEFAULT_WAIT_TIME = 10000L;
    /**
     * �߳�˯��ʱ�� Ĭ��ֵ1����
     */
    public static long DEFAULT_SLEEP_TIME = 1000L;
    /**
     * �߳�С˯ʱ�� Ĭ��ֵ5����
     */
    public static long DEFAULT_NAP_TIME = 5L;
    /**
     * �߳�С˯ʱ������CMPP_SUBMIT����ѭ��� Ĭ��ֵ5����
     */
    public static long DEFAULT_NAP_SUBMIT_TIME = 500L;
    /**
     * �߳�С˯ʱ������CMPP_DELIVER����ѭ��� Ĭ��ֵ5����
     */
    public static long DEFAULT_NAP_DELIVER_TIME = 500L;
    /**
     * �߳�С˯ʱ������CMPP_QUERY����ѭ��� Ĭ��ֵ5����
     */
    public static long DEFAULT_NAP_QUERY_TIME = 500L;
    
    
    /**
     *  �����˿ڳ�ʱʱ�� Ĭ��ֵ1����
     */
    public static long DEFAULT_LISTEN_TIMEOUT = 1000L;
    /**
     *  socket�˿ڳ�ʱʱ�� Ĭ��ֵ15����
     */
    public static long DEFAULT_SOCKET_TIMEOUT = 15000L;
    /**
     *  ��Ϣ�����ͳ�ʱʱ�� Ĭ��ֵ60����
     */
    public static long DEFAULT_PACKET_TIMEOUT = 60000L;
    /**
     *  �·����ų�ʱʱ�� Ĭ��ֵ15����
     */
    public static long DEFAULT_MESSAGE_TIMEOUT = 900000L;
    /**
     *  �쳣��ʱʱ�� Ĭ��ֵ30����
     */
    public static long DEFAULT_EXCEPTION_TIMEOUT = 1800000L;
    /**
     * �������ӱ��ְ���ʱ���� Ĭ��ֵ30����
     */
    public static long DEFAULT_ACTIVETEST_TIMEOUT = 30000L;
    /**
     *  ���ݿ����ӳ�ʱʱ�� Ĭ��ֵ2����
     */
    public static long DEFAULT_DATABASE_TIMEOUT = 120000L;
    /**
     *  �ط���ʱʱ�� Ĭ��ֵ1����
     */
    public static long DEFAULT_RETRY_TIMEOUT = 60000L;
    /**
     *  ���CMPPSubmit��ת�����ݵ�BackUp������� Ĭ��ֵ1Сʱ
     */
    public static long DEFAULT_SUBMIT_CLEAN_TIMEOUT = TIME_ONE_HOUR;

}
