package LeadTone.Packet.SMPPPacket;


public class CMCCCommandStatus
{
    public static final int E_SUCCESS = 0;
    public static final int E_OTHERERR = 1;
    public static final int E_MSGLENERR = 16;
    public static final int E_CMDLENERR = 17;
    public static final int E_INVLDCMDID = 18;
    public static final int E_NORIGHT = 19;
    public static final int E_INVLDSYSTEMID = 32;
    public static final int E_INVLDSYSPASSWORD = 33;
    public static final int E_INVLDSYSTEMTYPE = 34;
    public static final int E_ADDRERR = 64;
    public static final int E_MOEXCEED = 65;
    public static final int E_MTEXCEED = 66;
    public static final int E_INVLDUSER = 67;
    public static final int E_INVLDDATAFMT = 68;
    public static final int E_CREATEMSGFAILURE = 69;
    public static final int E_INVLDMSGID = 70;
    public static final int E_DATABASEFAILURE = 71;
    public static final int E_CANCELMSGFAILURE = 72;
    public static final int E_MSGSTATEERR = 73;
    public static final int E_REPLACEMSGFAILURE = 74;
    public static final int E_INVLDRPLADDR = 75;
    public static final int E_INVLDORGTON = 96;
    public static final int E_INVLDORGNPI = 97;
    public static final int E_ORGADDRERR = 98;
    public static final int E_INVLDDESTTON = 99;
    public static final int E_INVLDDESTNPI = 100;
    public static final int E_DESTADDRERR = 101;
    public static final int E_INVLDSCHEDULE = 102;
    public static final int E_INVLDEXPIRE = 103;
    public static final int E_INVLDESM = 104;
    public static final int E_INVLDUDLEN = 105;
    public static final int E_INVLDPRI = 106;
    public static final int E_INVLDRDF = 107;
    public static final int E_INVLDRPF = 108;
    public static final int E_USERRALREADYEXIST = 128;
    public static final int E_CREATEUSERERR = 129;
    public static final int E_USERIDERR = 130;
    public static final int E_USERNOTEXIST = 131;

    public CMCCCommandStatus()
    {
    }

    public static String toString(int command_status)
    {
        switch(command_status)
        {
        case 0:
            return "成功";

        case 1:
            return "其他错误";

        case 16:
            return "消息长度错误";

        case 17:
            return "命令长度错误";

        case 18:
            return "消息ID无效";

        case 19:
            return "没有执行命令的权限";

        case 32:
            return "无效的system_id";

        case 33:
            return "无效的密码";

        case 34:
            return "无效的system_type";

        case 64:
            return "地址错误";

        case 65:
            return "超过最大提交数";

        case 66:
            return "超过最大下发数";

        case 67:
            return "无效的用户";

        case 68:
            return "无效的数据格式";

        case 69:
            return "创建消息失败";

        case 70:
            return "无效的短消息ID";

        case 71:
            return "数据库失败";

        case 72:
            return "取消短消息失败";

        case 73:
            return "短消息状态失败";

        case 74:
            return "替换消息失败";

        case 75:
            return "替换消息源地址失败";

        case 96:
            return "无效的源地址TON";

        case 97:
            return "无效的源地址NPI";

        case 98:
            return "源地址错误";

        case 99:
            return "无效的目的地址TON";

        case 100:
            return "无效的目的地址NPI";

        case 101:
            return "目的地址错误";

        case 102:
            return "无效的定时时间";

        case 103:
            return "无效的超时时间";

        case 104:
            return "无效的esm_class";

        case 105:
            return "无效的UDLEN";

        case 106:
            return "无效的PRI";

        case 107:
            return "无效的registered_delivery_flag";

        case 108:
            return "无效的replace_if_present_flag";

        case 128:
            return "指定的用户已经存在";

        case 129:
            return "创建用户失败";

        case 130:
            return "用户ID错误";

        case 131:
            return "指定用户不存在";

        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
        case 29:
        case 30:
        case 31:
        case 35:
        case 36:
        case 37:
        case 38:
        case 39:
        case 40:
        case 41:
        case 42:
        case 43:
        case 44:
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 56:
        case 57:
        case 58:
        case 59:
        case 60:
        case 61:
        case 62:
        case 63:
        case 76:
        case 77:
        case 78:
        case 79:
        case 80:
        case 81:
        case 82:
        case 83:
        case 84:
        case 85:
        case 86:
        case 87:
        case 88:
        case 89:
        case 90:
        case 91:
        case 92:
        case 93:
        case 94:
        case 95:
        case 109:
        case 110:
        case 111:
        case 112:
        case 113:
        case 114:
        case 115:
        case 116:
        case 117:
        case 118:
        case 119:
        case 120:
        case 121:
        case 122:
        case 123:
        case 124:
        case 125:
        case 126:
        case 127:
        default:
            return "保留";
        }
    }



}