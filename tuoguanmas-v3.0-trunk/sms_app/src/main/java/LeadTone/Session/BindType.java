package LeadTone.Session;


public class BindType
{
    /**
     * 模糊处理，默认为既发送也接收
     */
    public static final int BIND_MASK = 3;
    /**
     * 服务器监听模式
     */
    public static final int BIND_ACTIVATER = 0;
    /**
     * 只发送
     */
    public static final int BIND_TRANSMITTER = 1;
    /**
     * 只接收
     */
    public static final int BIND_RECEIVER = 2;
    /**
     * 既发送也接收
     */
    public static final int BIND_TRANSCEIVER = 3;

    public BindType()
    {
    }

    /**
     * 判断是否为只发送，和3做与操作，例如4 & 3结果为0，目的是归类四种类型，便于类型扩展
     * @param nType 会话类型代码
     * @return 返回是否为只发送类型的布尔值
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
     * 得到对应的网关工作类型描述
     * @param nType 类型代码
     * @return 返回网关工作类型描述
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
     * 得到对应的网关工作类型代码
     * @param strType  类型描述
     * @return 返回网关工作类型代码
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
