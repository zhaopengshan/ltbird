package LeadTone.Packet.SGIPPacket;


public class SGIPCommandID
{
    public static final int SGIP_RESPONSE_MASK = 0x80000000;
    public static final int SGIP_BIND = 1;
    public static final int SGIP_BIND_RESPONSE = 0x80000001;
    public static final int SGIP_UNBIND = 2;
    public static final int SGIP_UNBIND_RESPONSE = 0x80000002;
    public static final int SGIP_SUBMIT = 3;
    public static final int SGIP_SUBMIT_RESPONSE = 0x80000003;
    public static final int SGIP_DELIVER = 4;
    public static final int SGIP_DELIVER_RESPONSE = 0x80000004;
    public static final int SGIP_REPORT = 5;
    public static final int SGIP_REPORT_RESPONSE = 0x80000005;
    public static final int SGIP_ADDSP = 6;
    public static final int SGIP_ADDSP_RESPONSE = 0x80000006;
    public static final int SGIP_MODIFYSP = 7;
    public static final int SGIP_MODIFYSP_RESPONSE = 0x80000007;
    public static final int SGIP_DELETESP = 8;
    public static final int SGIP_DELETESP_RESPONSE = 0x80000008;
    public static final int SGIP_QUERYROUTE = 9;
    public static final int SGIP_QUERYROUTE_RESPONSE = 0x80000009;
    public static final int SGIP_ADDTELESEG = 10;
    public static final int SGIP_ADDTELESEG_RESPONSE = 0x8000000a;
    public static final int SGIP_MODIFYTELESEG = 11;
    public static final int SGIP_MODIFYTELESEG_RESPONSE = 0x8000000b;
    public static final int SGIP_DELETETELESEG = 12;
    public static final int SGIP_DELETETELESEG_RESPONSE = 0x8000000c;
    public static final int SGIP_ADDSMG = 13;
    public static final int SGIP_ADDSMG_RESPONSE = 0x8000000d;
    public static final int SGIP_MODIFYSMG = 14;
    public static final int SGIP_MODIFYSMG_RESPONSE = 0x8000000e;
    public static final int SGIP_DELETESMG = 15;
    public static final int SGIP_DELETESMG_RESPONSE = 0x8000000f;
    public static final int SGIP_CHECKUSER = 16;
    public static final int SGIP_CHECKUSRE_RESPONSE = 0x80000010;
    public static final int SGIP_USERRPT = 17;
    public static final int SGIP_USERRPT_RESPONSE = 0x80000011;
    public static final int SGIP_TRACE = 4096;
    public static final int SGIP_TRACE_RESPONSE = 0x80001000;

    public SGIPCommandID()
    {
    }

    public static boolean isRequest(int command_id)
    {
        switch(command_id)
        {
        case 1:
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
        case 16:
        case 17:
        case 4096:
            return true;
        }
        return false;
    }

    public static boolean isResponse(int command_id)
    {
        switch(command_id)
        {
        case -2147483647: 
        case -2147483646: 
        case -2147483645: 
        case -2147483644: 
        case -2147483643: 
        case -2147483642: 
        case -2147483641: 
        case -2147483640: 
        case -2147483639: 
        case -2147483638: 
        case -2147483637: 
        case -2147483636: 
        case -2147483635: 
        case -2147483634: 
        case -2147483633: 
        case -2147483632: 
        case -2147483631: 
        case -2147479552: 
            return true;
        }
        return false;
    }

    public static boolean isValid(int command_id)
    {
        return isRequest(command_id) || isResponse(command_id);
    }

    public static boolean isMessage(int command_id)
    {
        switch(command_id)
        {
        case -2147483645: 
        case -2147483644: 
        case -2147483643: 
        case -2147483631: 
        case -2147479552: 
        case 3:
        case 4:
        case 5:
        case 17:
        case 4096: 
            return true;
        }
        return false;
    }

    public static boolean isTransmitterOutput(int command_id)
    {
        switch(command_id)
        {
        case -2147483646: 
        case 2:
        case 3:
        case 4096: 
            return true;
        }
        return false;
    }

    public static boolean isTransmitterInput(int command_id)
    {
        switch(command_id)
        {
        case -2147483646: 
        case -2147483645: 
        case -2147479552: 
        case 2:
            return true;
        }
        return false;
    }

    public static boolean isReceiverOutput(int command_id)
    {
        switch(command_id)
        {
        case -2147483646: 
        case -2147483644: 
        case -2147483643: 
        case -2147483631: 
        case 2:
            return true;
        }
        return false;
    }

    public static boolean isReceiverInput(int command_id)
    {
        switch(command_id)
        {
        case -2147483646: 
        case 2:
        case 4:
        case 5:
        case 17:
            return true;
        }
        return false;
    }

    public static String toString(int command_id)
    {
        switch(command_id)
        {
        case 1:
            return "sgip_bind";

        case 2:
            return "sgip_unbind";

        case 3:
            return "sgip_submit";

        case 4:
            return "sgip_deliver";

        case 5:
            return "sgip_report";

        case 6:
            return "sgip_addsp";

        case 7:
            return "sgip_modifysp";

        case 8:
            return "sgip_deletesp";

        case 9:
            return "sgip_queryroute";

        case 10:
            return "sgip_addteleseg";

        case 11:
            return "sgip_modifyteleseg";

        case 12:
            return "sgip_deleteteleseg";

        case 13:
            return "sgip_addsmg";

        case 14:
            return "sgip_modifysmg";

        case 15:
            return "sgip_deletesmg";

        case 16:
            return "sgip_checkuser";

        case 17:
            return "sgip_userrpt";

        case 4096: 
            return "sgip_trace";

        case -2147483647: 
            return "sgip_bind_response";

        case -2147483646: 
            return "sgip_unbind_response";

        case -2147483645: 
            return "sgip_submit_response";

        case -2147483644: 
            return "sgip_deliver_response";

        case -2147483643: 
            return "sgip_report_response";

        case -2147483642: 
            return "sgip_addsp_response";

        case -2147483641: 
            return "sgip_modifysp_response";

        case -2147483640: 
            return "sgip_deletesp_response";

        case -2147483639: 
            return "sgip_queryroute_response";

        case -2147483638: 
            return "sgip_addteleseg_response";

        case -2147483637: 
            return "sgip_modifyteleseg_response";

        case -2147483636: 
            return "sgip_deleteteleseg_response";

        case -2147483635: 
            return "sgip_addsmg_response";

        case -2147483634: 
            return "sgip_modifysmg_response";

        case -2147483633: 
            return "sgip_deletesmg_response";

        case -2147483632: 
            return "sgip_checkuser_response";

        case -2147483631: 
            return "sgip_userrpt_response";

        case -2147479552: 
            return "sgip_trace_response";
        }
        return "reserved";
    }



}