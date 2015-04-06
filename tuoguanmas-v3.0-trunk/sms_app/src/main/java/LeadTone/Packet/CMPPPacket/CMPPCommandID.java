package LeadTone.Packet.CMPPPacket;


/**
 * 参见CMPP协议2.1，此为所有CMPP协议中定义的消息指令的定义，实现消息分类功能
 */
public class CMPPCommandID
{
    /**
     * 自定义字段，用于模糊处理所有回复消息
     */
    public static final int CMPP_RESPONSE_MASK = 0x80000000;
    /**
     * 自定义字段，用于模糊处理所有回复消息
     */
    public static final int CMPP_NACK_RESPONSE = 0x80000000;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_CONNECT = 0x00000001;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_CONNECT_RESPONSE = 0x80000001;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_TERMINATE = 0x00000002;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_TERMINATE_RESPONSE = 0x80000002;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_SUBMIT = 0x00000004;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_SUBMIT_RESPONSE = 0x80000004;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_DELIVER = 0x00000005;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_DELIVER_RESPONSE = 0x80000005;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_QUERY = 0x00000006;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_QUERY_RESPONSE = 0x80000006;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_CANCEL = 0x00000007;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_CANCEL_RESPONSE = 0x80000007;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_ACTIVETEST = 0x00000008;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_ACTIVETEST_RESPONSE = 0x80000008;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_FORWARD = 0x00000009;
    /**
     * 参考CMPP协议2.1
     */
    public static final int CMPP_FORWARD_RESPONSE = 0x80000009;

    /**
     * 自定义保留字段，在CMPP协议中未定义
     */
    public static final int CMPP_RESERVE = 0x00000003;
    /**
     * 自定义保留字段，在CMPP协议中未定义
     */
    public static final int CMPP_RESERVE_RESPONSE = 0x80000003;


    public CMPPCommandID()
    {
    }

    /**
     * 判断是否属于回复类消息
     * @param command_id 消息命令
     * @return 返回是否为回复类消息的布尔值
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
        //保留字段，CMPP协议中未定义
        case CMPP_RESERVE_RESPONSE:
        default:
            return false;
        }
    }

    /**
     * 判断是否属于请求类消息
     * @param command_id 消息命令
     * @return 返回是否为请求类消息的布尔值
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
        //保留字段，CMPP协议中未定义
        case CMPP_RESERVE:
        default:
            return false;
        }
    }

    /**
     * 判断消息指令是否合法，判断属于请求类型或者属于回复类型则合法
     * @param command_id 消息指令
     * @return 返回消息指令是否合法的布尔值
     */
    public static boolean isValid(int command_id)
    {
        return isRequest(command_id) || isResponse(command_id);
    }

    /**
     * 判断消息指令是否是短消息类的消息包，MO或MT（MO上行短信，MT下行短信）
     * @param command_id
     * @return 返回是否属于短消息类的消息包的布尔值
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
     * 判断消息指令是否属于单发送模式下输出的类型
     * @param command_id  消息指令
     * @return 返回消息指令是否属于单发送模式下输出的类型的布尔值
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
     * 判断消息指令是否属于单发送模式下输入的类型
     * @param command_id 消息指令
     * @return 返回消息指令是否属于单发送模式下输入的类型的布尔值
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
     * 判断消息指令是否属于单接收模式下输出的类型
     * @param command_id 消息指令
     * @return  返回消息指令是否属于单接收模式下输出的类型的布尔值
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
     * 判断消息指令是否属于单接收模式下输入的类型
     * @param command_id 消息指令
     * @return  返回消息指令是否属于单接收模式下输入的类型的布尔值
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
     * 消息指令到类型描述的转换函数
     * @param command_id  消息指令
     * @return 与消息指令对应的类型描述
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