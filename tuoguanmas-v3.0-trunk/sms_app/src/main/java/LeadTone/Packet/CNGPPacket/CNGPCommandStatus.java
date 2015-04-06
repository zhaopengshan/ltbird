package LeadTone.Packet.CNGPPacket;


public class CNGPCommandStatus
{

    public static final int CNGP_OK = 0;
    public static final int CNGP_SYSTEM_ERROR = 1;
    public static final int CNGP_OVER_MAX_FLUX = 2;
    public static final int CNGP_ERROR_MSG_STRUCTURE = 10;
    public static final int CNGP_COMMAND_ERROR = 11;
    public static final int CNGP_REPEAT_SEQID = 12;
    public static final int CNGP_ERROR_IP = 20;
    public static final int CNGP_ERROR_AUTHED = 21;
    public static final int CNGP_UNSUPPORT_VER = 22;
    public static final int CNGP_ERROR_FEEUSERTYPE = 23;
    public static final int CNGP_ERROR_SUBTYPE = 24;
    public static final int CNGP_ERROR_NODE_COUNT = 25;
    public static final int CNGP_ILLEGAL_MSGID = 32;
    public static final int CNGP_ILLEGAL_SMTYPE = 30;
    public static final int CNGP_ILLEGAL_PRIORTY = 31;
    public static final int CNGP_ILLEGAL_FEETYPE = 32;
    public static final int CNGP_ILLEGAL_FEE_CODE = 33;
    public static final int CNGP_ILLEGAL_MSGFMT = 34;
    public static final int CNGP_INVALID_TIME_FORMAT = 35;
    public static final int CNGP_INVALID_MSG_LENGTH = 36;
    public static final int CNGP_INVALID_VALIDTIME = 37;
    public static final int CNGP_ILLEGAL_QUERYTYPE = 38;
    public static final int CNGP_ERROR_ROUTE = 39;
    
    public CNGPCommandStatus()
    {
    }

    public static String toString(int command_status)
    {
        switch(command_status)
        {
        case 0:
            return "\u6B63\u786E";

        case 1:
            return "\u7CFB\u7EDF\u5FD9";

        case 2:
            return "\u8D85\u8FC7\u6700\u5927\u8FDE\u63A5\u6570";

        case 10:
            return "\u6D88\u606F\u7ED3\u679C\u9519";

        case 11:
            return "\u547D\u4EE4\u5B57\u9519";

        case 12:
            return "\u5E8F\u5217\u53F7\u91CD\u590D";

        case 20:
            return "IP\u5730\u5740\u9519";

        case 21:
            return "\u8BA4\u8BC1\u9519";

        case 22:
            return "\u7248\u672C\u592A\u9AD8";

        case 23:
            return "\u975E\u6CD5FeeUserType";

        case 24:
            return "\u975E\u6CD5SubType";

        case 25:
            return "NodesCount\u8D85\u8FC7\u9600\u503C";

        case 26:
            return "\u975E\u6CD5MsgID";

        case 30:
            return "\u975E\u6CD5\u6D88\u606F\u7C7B\u578B(SMType)";

        case 31:
            return "\u975E\u6CD5\u4F18\u5148\u7EA7(Priorty)";

        case 32:
            return "\u975E\u6CD5\u8D44\u8D39\u7C7B\u578B(FeeType)";

        case 33:
            return "\u975E\u6CD5\u8D44\u8D39\u4EE3\u7801(FeeCode)";

        case 34:
            return "\u975E\u6CD5\u77ED\u6D88\u606F\u683C\u5F0F(MsgFormat)";

        case 35:
            return "\u975E\u6CD5\u65F6\u95F4\u683C\u5F0F";

        case 36:
            return "\u975E\u6CD5\u77ED\u6D88\u606F\u957F\u5EA6(MsgLength)";

        case 37:
            return "\u6709\u6548\u671F\u5DF2\u8FC7";

        case 38:
            return "\u975E\u6CD5\u67E5\u8BE2\u7C7B\u522B(QueryType)";

        case 39:
            return "\u7CFB\u7EDF\u9519\u8BEF";

        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 27:
        case 28:
        case 29:
        default:
            return "reserved";
        }
    }



}