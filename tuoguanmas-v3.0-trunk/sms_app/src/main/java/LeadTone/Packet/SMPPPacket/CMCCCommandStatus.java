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
            return "�ɹ�";

        case 1:
            return "��������";

        case 16:
            return "��Ϣ���ȴ���";

        case 17:
            return "����ȴ���";

        case 18:
            return "��ϢID��Ч";

        case 19:
            return "û��ִ�������Ȩ��";

        case 32:
            return "��Ч��system_id";

        case 33:
            return "��Ч������";

        case 34:
            return "��Ч��system_type";

        case 64:
            return "��ַ����";

        case 65:
            return "��������ύ��";

        case 66:
            return "��������·���";

        case 67:
            return "��Ч���û�";

        case 68:
            return "��Ч�����ݸ�ʽ";

        case 69:
            return "������Ϣʧ��";

        case 70:
            return "��Ч�Ķ���ϢID";

        case 71:
            return "���ݿ�ʧ��";

        case 72:
            return "ȡ������Ϣʧ��";

        case 73:
            return "����Ϣ״̬ʧ��";

        case 74:
            return "�滻��Ϣʧ��";

        case 75:
            return "�滻��ϢԴ��ַʧ��";

        case 96:
            return "��Ч��Դ��ַTON";

        case 97:
            return "��Ч��Դ��ַNPI";

        case 98:
            return "Դ��ַ����";

        case 99:
            return "��Ч��Ŀ�ĵ�ַTON";

        case 100:
            return "��Ч��Ŀ�ĵ�ַNPI";

        case 101:
            return "Ŀ�ĵ�ַ����";

        case 102:
            return "��Ч�Ķ�ʱʱ��";

        case 103:
            return "��Ч�ĳ�ʱʱ��";

        case 104:
            return "��Ч��esm_class";

        case 105:
            return "��Ч��UDLEN";

        case 106:
            return "��Ч��PRI";

        case 107:
            return "��Ч��registered_delivery_flag";

        case 108:
            return "��Ч��replace_if_present_flag";

        case 128:
            return "ָ�����û��Ѿ�����";

        case 129:
            return "�����û�ʧ��";

        case 130:
            return "�û�ID����";

        case 131:
            return "ָ���û�������";

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
            return "����";
        }
    }



}