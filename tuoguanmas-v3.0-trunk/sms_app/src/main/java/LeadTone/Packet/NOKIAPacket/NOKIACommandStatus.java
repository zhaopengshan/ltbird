package LeadTone.Packet.NOKIAPacket;


public class NOKIACommandStatus
{

    public static final int CMPPE_RSP_SUCCESS = 0;
    public static final int CMPPE_RSP_OTHER_ERR = 1;
    public static final int CMPPE_RSP_INVAL_MSG_LEN = 2;
    public static final int CMPPE_RSP_UNKNOWN_CMD = 3;
    public static final int CMPPE_RSP_SYNC_ERR = 4;
    public static final int CMPPE_RSP_INVAL_STRUCT = 5;
    public static final int CMPPE_RSP_INVAL_ICP = 16;
    public static final int CMPPE_RSP_INVAL_AUTH = 17;
    public static final int CMPPE_RSP_INVAL_BIND_TYPE = 18;
    public static final int CMPPE_RSP_BINDED = 19;
    public static final int CMPPE_RSP_BIND_EXCEED = 20;
    public static final int CMPPE_RSP_NOT_BIND = 21;
    public static final int CMPPE_RSP_INVAL_MSG_MODE = 32;
    public static final int CMPPE_RSP_INVAL_DATA_CODING = 33;
    public static final int CMPPE_RSP_INVAL_SVC_TYPE = 34;
    public static final int CMPPE_RSP_INVAL_FEE_TYPE = 35;
    public static final int CMPPE_RSP_INVAL_DATETIME = 36;
    public static final int CMPPE_RSP_DSTS_EXCEED = 37;
    public static final int CMPPE_RSP_SMLEN_EXCEED = 38;
    public static final int CMPPE_RSP_INVAL_MSISDN = 38;
    public static final int CMPPE_RSP_INVAL_PARA = 39;
    public static final int CMPPE_RSP_PK_SEQ_REPEAT = 48;
    public static final int CMPPE_RSP_PK_SEQ_EXCEED = 49;
    public static final int CMPPE_RSP_MSG_NOT_FOUND = 50;
    public static final int CMPPE_RSP_LEN_BAD = 136;

    public NOKIACommandStatus()
    {
    }

    public static String toString(int command_status)
    {
        switch(command_status)
        {
        case 0:
            return "\u6B63\u786E";

        case 1:
            return "\u5176\u4ED6\u9519\u8BEF";

        case 2:
            return "\u65E0\u6548\u7684\u6D88\u606F\u957F\u5EA6";

        case 3:
            return "\u65E0\u6548\u7684\u547D\u4EE4";

        case 4:
            return "\u540C\u6B65\u9519";

        case 5:
            return "\u65E0\u6548\u7684\u7ED3\u6784";

        case 16:
            return "\u65E0\u6548\u7684ICP";

        case 17:
            return "\u65E0\u6548\u7684\u6548\u9A8C";

        case 18:
            return "\u65E0\u6548\u7684\u7ED1\u5B9A\u7C7B\u578B";

        case 19:
            return "\u5DF2\u7ECF\u5E2E\u5B9A";

        case 20:
            return "\u8D85\u51FA\u7ED1\u5B9A\u9650\u5236";

        case 21:
            return "\u6CA1\u6709\u7ED1\u5B9A";

        case 32:
            return "\u65E0\u6548\u7684\u6D88\u606F\u6A21\u5F0F";

        case 33:
            return "\u65E0\u6548\u7684\u7F16\u7801\u65B9\u5F0F";

        case 34:
            return "\u65E0\u6548\u7684\u670D\u52A1\u7C7B\u578B";

        case 35:
            return "\u65E0\u6548\u7684\u6536\u8D39\u7C7B\u578B";

        case 36:
            return "\u65E0\u6548\u7684\u65F6\u95F4";

        case 37:
            return "\u76EE\u7684\u5730\u5740\u8D85\u51FA";

        case 38:
            return "\u6D88\u606F\u8D85\u8FC7\u6700\u5927\u957F\u5EA6\u6216\u65E0\u6548\u7684\u53F7\u7801";

        case 39:
            return "\u65E0\u6548\u7684\u53C2\u6570";

        case 48:
            return "\u5E8F\u5217\u53F7\u91CD\u590D";

        case 49:
            return "\u5E8F\u5217\u53F7\u8D85\u51FA";

        case 50:
            return "\u6CA1\u6709\u627E\u5230\u6D88\u606F";

        case 136: 
            return "\u9519\u8BEF\u7684\u957F\u5EA6";
        }
        return "\u672A\u77E5\u9519\u8BEF";
    }



}