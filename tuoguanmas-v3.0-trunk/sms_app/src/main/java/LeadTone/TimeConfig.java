package LeadTone;

/**
 * 时间参数配置类
 */
public class TimeConfig
{

    public TimeConfig()
    {
    }

    /**
     *  一秒钟
     */
    public static final long TIME_ONE_SECOND = 1000L;
    /**
     *  一分钟
     */
    public static final long TIME_ONE_MINUTE = 60000L;
    /**
     *  十五分钟
     */
    public static final long TIME_FIFTEEN_MINUTES = 900000L;
    /**
     *  一小时
     */
    public static final long TIME_ONE_HOUR = 3600000L;
    /**
     *  半小时
     */
    public static final long TIME_HALF_HOUR = 1800000L;
    /**
     * 一天
     */
    public static final long TIME_ONE_DAY = 86400000L;
    /**
     * 半天
     */
    public static final long TIME_HALF_DAY = 43200000L;
    /**
     *  一星期
     */
    public static final long TIME_ONE_WEEK = 604800000L;
    /**
     *  一个月(30天)
     */
    public static final long TIME_ONE_MONTH = 2592000000L;
    /**
     * 消息包存活周期 默认值30秒钟
     */
    public static long DEFAULT_SURVIVE_TIME = 30000L;
    /**
     *  等待线程启动时间 默认值10秒钟
     */
    public static long DEFAULT_WAIT_TIME = 10000L;
    /**
     * 线程睡眠时间 默认值1秒钟
     */
    public static long DEFAULT_SLEEP_TIME = 1000L;
    /**
     * 线程小睡时间 默认值5毫秒
     */
    public static long DEFAULT_NAP_TIME = 5L;
    /**
     * 线程小睡时间用于CMPP_SUBMIT表轮循间隔 默认值5毫秒
     */
    public static long DEFAULT_NAP_SUBMIT_TIME = 500L;
    /**
     * 线程小睡时间用于CMPP_DELIVER表轮循间隔 默认值5毫秒
     */
    public static long DEFAULT_NAP_DELIVER_TIME = 500L;
    /**
     * 线程小睡时间用于CMPP_QUERY表轮循间隔 默认值5毫秒
     */
    public static long DEFAULT_NAP_QUERY_TIME = 500L;
    
    
    /**
     *  监听端口超时时间 默认值1秒钟
     */
    public static long DEFAULT_LISTEN_TIMEOUT = 1000L;
    /**
     *  socket端口超时时间 默认值15秒钟
     */
    public static long DEFAULT_SOCKET_TIMEOUT = 15000L;
    /**
     *  消息包发送超时时间 默认值60秒钟
     */
    public static long DEFAULT_PACKET_TIMEOUT = 60000L;
    /**
     *  下发短信超时时间 默认值15分钟
     */
    public static long DEFAULT_MESSAGE_TIMEOUT = 900000L;
    /**
     *  异常超时时间 默认值30分钟
     */
    public static long DEFAULT_EXCEPTION_TIMEOUT = 1800000L;
    /**
     * 发送连接保持包的时间间隔 默认值30秒钟
     */
    public static long DEFAULT_ACTIVETEST_TIMEOUT = 30000L;
    /**
     *  数据库连接超时时间 默认值2分钟
     */
    public static long DEFAULT_DATABASE_TIMEOUT = 120000L;
    /**
     *  重发超时时间 默认值1分钟
     */
    public static long DEFAULT_RETRY_TIMEOUT = 60000L;
    /**
     *  清除CMPPSubmit表，转移数据到BackUp表的周期 默认值1小时
     */
    public static long DEFAULT_SUBMIT_CLEAN_TIMEOUT = TIME_ONE_HOUR;

}
