package LeadTone.Packet.SMPPPacket;


public class SMPPCommandStatus
{
    public static final int ESME_ROK = 0;
    public static final int ESME_RINVMSGLEN = 1;
    public static final int ESME_RINVCMDLEN = 2;
    public static final int ESME_RINVCMDID = 3;
    public static final int ESME_RINVBNDSTS = 4;
    public static final int ESME_RALYBND = 5;
    public static final int ESME_RINVPRTFLG = 6;
    public static final int ESME_RINVREGDLVFLG = 7;
    public static final int ESME_RSYSERR = 8;
    public static final int ESME_RINVSRCADR = 10;
    public static final int ESME_RINVDSTADR = 11;
    public static final int ESME_RINVMSGID = 12;
    public static final int ESME_RBINDFAIL = 13;
    public static final int ESME_RINVPASWD = 14;
    public static final int ESME_RINVSYSID = 15;
    public static final int ESME_RCANCELFAIL = 17;
    public static final int ESME_RREPLACEFAIL = 19;
    public static final int ESME_RMSGQFUL = 20;
    public static final int ESME_RINVSERTYP = 21;
    public static final int ESME_RINVNUMDESTS = 51;
    public static final int ESME_RINVDLNAME = 52;
    public static final int ESME_RINVDESTFLAG = 64;
    public static final int ESME_RINVSUBREP = 66;
    public static final int ESME_RINVESMCLASS = 67;
    public static final int ESME_RCNTSUBDL = 68;
    public static final int ESME_RSUBMITFAIL = 69;
    public static final int ESME_RINVSRCTON = 72;
    public static final int ESME_RINVSRCNPI = 73;
    public static final int ESME_RINVDSTTON = 80;
    public static final int ESME_RINVDSTNPI = 81;
    public static final int ESME_RINVSYSTYP = 83;
    public static final int ESME_RINVREPFLAG = 84;
    public static final int ESME_RINVNUMMSGS = 85;
    public static final int ESME_RTHROTTLED = 88;
    public static final int ESME_RINVSCHED = 97;
    public static final int ESME_RINVEXPIRY = 98;
    public static final int ESME_RINVDFTMSGID = 99;
    public static final int ESME_RX_T_APPN = 100;
    public static final int ESME_RX_P_APPN = 101;
    public static final int ESME_RX_R_APPN = 102;
    public static final int ESME_RQUERYFAIL = 103;
    public static final int ESME_RINVOPTPARSTREAM = 192;
    public static final int ESME_ROPTPARNOTALLWD = 193;
    public static final int ESME_RINVPARLEN = 194;
    public static final int ESME_RMISSINGOPTPARAM = 195;
    public static final int ESME_RINVOPTPARAMVAL = 196;
    public static final int ESME_RDELIVERYFAILURE = 254;
    public static final int ESME_RUNKNOWNERR = 255;

    public SMPPCommandStatus()
    {
    }

    public static String toString(int command_status)
    {
        switch(command_status)
        {
        case 0:
            return "no error";

        case 1:
            return "message length is invalid";

        case 2:
            return "command length is invalid";

        case 3:
            return "invalid command ID";

        case 4:
            return "incorrect BIND status for given com-mand";

        case 5:
            return "ESME already in bound state";

        case 6:
            return "invalid priority flag";

        case 7:
            return "invalid registered delivery flag";

        case 8:
            return "system error";

        case 10:
            return "invalid source address";

        case 11:
            return "invalid dest addr";

        case 12:
            return "message ID is invalid";

        case 13:
            return "bind failed";

        case 14:
            return "invalid password";

        case 15:
            return "invalid system ID";

        case 17:
            return "cancel SM failed";

        case 19:
            return "replace SM failed";

        case 20:
            return "message queue full";

        case 21:
            return "invalid service type";

        case 51:
            return "invalid number of destinations";

        case 52:
            return "invalid distribution list name";

        case 64:
            return "destination flag is invalid (submit_multi)";

        case 66:
            return "invalid ¡®submit with replace¡¯ request (i.e. submit_sm with replace_if_present_flag set)";

        case 67:
            return "invalid esm_class field data";

        case 68:
            return "cannot submit to distribution list";

        case 69:
            return "submit_sm or submit_multi failed";

        case 72:
            return "invalid source address TON";

        case 73:
            return "invalid source address NPI";

        case 80:
            return "invalid destination address TON";

        case 81:
            return "invalid destination address NPI";

        case 83:
            return "invalid system_type field";

        case 84:
            return "invalid replace_if_present flag";

        case 85:
            return "invalid number of messages";

        case 88:
            return "throttling error (ESME has exceeded allowed message limits)";

        case 97:
            return "invalid scheduled delivery time";

        case 98:
            return "invalid message validity period (expiry time)";

        case 99:
            return "predefined message invalid or not found";

        case 100:
            return "ESME receiver temporary app error code";

        case 101:
            return "ESME receiver permanent app error code";

        case 102:
            return "ESME receiver reject message error code";

        case 103:
            return "query_sm request failed";

        case 192: 
            return "error in the optional part of the PDU Body.";

        case 193: 
            return "qptional parameter not allowed";

        case 194: 
            return "invalid parameter length.";

        case 195: 
            return "expected optional parameter missing";

        case 196: 
            return "invalid optional parameter value";

        case 254: 
            return "delivery failure (used for data_sm_resp)";

        case 255: 
            return "unknown error";
        }
        return "reserved";
    }



}