package LeadTone.Packet.SMPPPacket;


public class SMPPCommandID
{
    public static final int SMPP_RESPONSE_MASK = 0x80000000;
    public static final int SMPP_GENERIC_NACK_RESPONSE = 0x80000000;
    public static final int SMPP_BIND_RECEIVER = 1;
    public static final int SMPP_BIND_RECEIVER_RESPONSE = 0x80000001;
    public static final int SMPP_BIND_TRANSMITTER = 2;
    public static final int SMPP_BIND_TRANSMITTER_RESPONSE = 0x80000002;
    public static final int SMPP_QUERY_SM = 3;
    public static final int SMPP_QUERY_SM_RESPONSE = 0x80000003;
    public static final int SMPP_SUBMIT_SM = 4;
    public static final int SMPP_SUBMIT_SM_RESPONSE = 0x80000004;
    public static final int SMPP_DELIVER_SM = 5;
    public static final int SMPP_DELIVER_SM_RESPONSE = 0x80000005;
    public static final int SMPP_UNBIND = 6;
    public static final int SMPP_UNBIND_RESPONSE = 0x80000006;
    public static final int SMPP_REPLACE_SM = 7;
    public static final int SMPP_REPLACE_SM_RESPONSE = 0x80000007;
    public static final int SMPP_CANCEL_SM = 8;
    public static final int SMPP_CANCEL_SM_RESPONSE = 0x80000008;
    public static final int SMPP_BIND_TRANSCEIVER = 9;
    public static final int SMPP_BIND_TRANSCEIVER_RESPONSE = 0x80000009;
    public static final int SMPP_OUTBIND = 11;
    public static final int SMPP_ENQUIRE_LINK = 21;
    public static final int SMPP_ENQUIRE_LINK_RESPONSE = 0x80000015;
    public static final int SMPP_SUBMIT_MULTI = 33;
    public static final int SMPP_SUBMIT_MULTI_RESPONSE = 0x80000021;
    public static final int SMPP_ALERT_NOTIFICATION = 0x80000102;
    public static final int SMPP_DATA_SM = 259;
    public static final int SMPP_DATA_SM_RESPONSE = 0x80000103;

    public SMPPCommandID()
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
        case 11:
        case 21:
        case 33:
        case 259: 
            return true;
        }
        return false;
    }

    public static boolean isResponse(int command_id)
    {
        switch(command_id)
        {
        case -2147483648: 
        case -2147483647: 
        case -2147483646: 
        case -2147483645: 
        case -2147483644: 
        case -2147483643: 
        case -2147483642: 
        case -2147483641: 
        case -2147483640: 
        case -2147483639: 
        case -2147483627: 
        case -2147483615: 
        case -2147483390: 
        case -2147483389: 
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
        case -2147483644: 
        case -2147483643: 
        case -2147483615: 
        case -2147483389: 
        case 4:
        case 5:
        case 33:
        case 259: 
            return true;
        }
        return false;
    }

    public static boolean isTransmitterOutput(int command_id)
    {
        switch(command_id)
        {
        case -2147483643: 
        case -2147483627: 
        case 3:
        case 4:
        case 7:
        case 8:
        case 21:
        case 33:
        case 259: 
            return true;
        }
        return false;
    }

    public static boolean isTransmitterInput(int command_id)
    {
        switch(command_id)
        {
        case -2147483645: 
        case -2147483644: 
        case -2147483641: 
        case -2147483640: 
        case -2147483627: 
        case -2147483615: 
        case -2147483389: 
        case 5:
        case 21:
            return true;
        }
        return false;
    }

    public static boolean isReceiverOutput(int command_id)
    {
        switch(command_id)
        {
        case -2147483643: 
        case -2147483627: 
        case -2147483389: 
        case 21:
            return true;
        }
        return false;
    }

    public static boolean isReceiverInput(int command_id)
    {
        switch(command_id)
        {
        case -2147483627: 
        case 5:
        case 21:
        case 259: 
            return true;
        }
        return false;
    }

    public static boolean isTransceiverOutput(int command_id)
    {
        switch(command_id)
        {
        case -2147483643: 
        case -2147483627: 
        case -2147483389: 
        case 3:
        case 4:
        case 7:
        case 8:
        case 21:
        case 259: 
            return true;
        }
        return false;
    }

    public static boolean isTransceiverInput(int command_id)
    {
        switch(command_id)
        {
        case -2147483645: 
        case -2147483644: 
        case -2147483641: 
        case -2147483640: 
        case -2147483627: 
        case -2147483389: 
        case 5:
        case 21:
        case 259: 
            return true;
        }
        return false;
    }

    public static String toString(int command_id)
    {
        switch(command_id)
        {
        case 1:
            return "bind_receiver";

        case 2:
            return "bind_transmitter";

        case 3:
            return "query_sm";

        case 4:
            return "submit_sm";

        case 5:
            return "deliver_sm";

        case 6:
            return "unbind";

        case 7:
            return "replace_sm";

        case 8:
            return "cancel_sm";

        case 9:
            return "bind_transceiver";

        case 11:
            return "out_bind";

        case 21:
            return "enquire_link";

        case 33:
            return "submit_multi";

        case 259: 
            return "data_sm";

        case -2147483648: 
            return "generic_nack";

        case -2147483647: 
            return "bind_reciever_response";

        case -2147483646: 
            return "bind_transmitter_response";

        case -2147483645: 
            return "query_sm_response";

        case -2147483644: 
            return "submit_sm_response";

        case -2147483643: 
            return "deliver_sm_response";

        case -2147483642: 
            return "unbind_response";

        case -2147483641: 
            return "replace_sm_response";

        case -2147483640: 
            return "cancel_sm_response";

        case -2147483639: 
            return "bind_transceiver_response";

        case -2147483627: 
            return "enquire_link_response";

        case -2147483615: 
            return "submit_multi_response";

        case -2147483390: 
            return "alert_notifaction";

        case -2147483389: 
            return "data_sm_response";
        }
        return "reserved";
    }



}