package LeadTone.Packet.CNGPPacket;


public class CNGPCommandID
{
    public static final int CNGP_LOGIN = 1;
    public static final int CNGP_LOGIN_RESPONSE = 0x80000001;
    public static final int CNGP_SUBMIT = 2;
    public static final int CNGP_SUBMIT_RESPONSE = 0x80000002;
    public static final int CNGP_DELIVER = 3;
    public static final int CNGP_DELIVER_RESPONSE = 0x80000003;
    public static final int CNGP_ACTIVETEST = 4;
    public static final int CNGP_ACTIVETEST_RESPONSE = 0x80000004;
    public static final int CNGP_LOGOUT = 6;
    public static final int CNGP_LOGOUT_RESPONSE = 0x80000006;

    public CNGPCommandID()
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
        case 6:
            return true;

        case 5:
        default:
            return false;
        }
    }

    public static boolean isResponse(int command_id)
    {
        switch(command_id)
        {
        case -2147483647: 
        case -2147483646: 
        case -2147483645: 
        case -2147483644: 
        case -2147483642: 
            return true;

        case -2147483643: 
        default:
            return false;
        }
    }

    public static boolean isValid(int command_id)
    {
        return isRequest(command_id) || isResponse(command_id);
    }

    public static boolean isMessage(int command_id)
    {
        switch(command_id)
        {
        case -2147483646: 
        case -2147483645: 
        case 2:
        case 3:
            return true;
        }
        return false;
    }

    public static boolean isTransmitterOutput(int command_id)
    {
        switch(command_id)
        {
        case -2147483644: 
        case 2:
        case 4:
            return true;
        }
        return false;
    }

    public static boolean isTransmitterInput(int command_id)
    {
        switch(command_id)
        {
        case -2147483646: 
        case -2147483644: 
        case 4:
            return true;
        }
        return false;
    }

    public static boolean isReceiverOutput(int command_id)
    {
        switch(command_id)
        {
        case -2147483645: 
        case -2147483644: 
        case 4:
            return true;
        }
        return false;
    }

    public static boolean isReceiverInput(int command_id)
    {
        switch(command_id)
        {
        case -2147483644: 
        case 3:
        case 4:
            return true;
        }
        return false;
    }

    public static boolean isTransceiverOutput(int command_id)
    {
        switch(command_id)
        {
        case -2147483645: 
        case -2147483644: 
        case 2:
        case 4:
            return true;
        }
        return false;
    }

    public static boolean isTransceiverInput(int command_id)
    {
        switch(command_id)
        {
        case -2147483646: 
        case -2147483644: 
        case 3:
        case 4:
            return true;
        }
        return false;
    }

    public static String toString(int command_id)
    {
        switch(command_id)
        {
        case 1:
            return "cngp_login";

        case -2147483647: 
            return "cngp_login_resp";

        case 2:
            return "cngp_submit";

        case -2147483646: 
            return "cngp_submit_resp";

        case 3:
            return "cngp_deliver";

        case -2147483645: 
            return "cngp_deliver_resp";

        case 4:
            return "cngp_activetest";

        case -2147483644: 
            return "cngp_activetest_resp";

        case 6:
            return "cngp_logout";

        case -2147483642: 
            return "cngp_logout_resp";
        }
        return "reserved";
    }



}