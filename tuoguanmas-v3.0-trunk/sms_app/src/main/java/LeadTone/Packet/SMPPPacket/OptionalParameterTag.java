package LeadTone.Packet.SMPPPacket;


public class OptionalParameterTag
{
    public static final int DEST_ADDR_SUBUNIT = 5;
    public static final int DEST_NETWORK_TYPE = 6;
    public static final int DEST_BEARER_TYPE = 7;
    public static final int DEST_TELEMATICS_ID = 8;
    public static final int SOURCE_ADDR_SUBUNIT = 13;
    public static final int SOURCE_NETWORK_TYPE = 14;
    public static final int SOURCE_BEARER_TYPE = 15;
    public static final int SOURCE_TELEMATICS_ID = 16;
    public static final int QOS_TIME_TO_LIVE = 23;
    public static final int PAYLOAD_TYPE = 25;
    public static final int ADDITIONAL_STATUS_INFO_TEXT = 29;
    public static final int RECEIPTED_MESSAGE_ID = 30;
    public static final int MS_MSG_WAIT_FACILITIES = 48;
    public static final int PRIVACY_INDICATOR = 513;
    public static final int SOURCE_SUBADDRESS = 514;
    public static final int DEST_SUBADDRESS = 515;
    public static final int USER_MESSAGE_REFERENCE = 516;
    public static final int RESPONSE_CODE = 517;
    public static final int SOURCE_PORT = 522;
    public static final int DESTINATION_PORT = 523;
    public static final int SAR_MSG_REF_NUM = 524;
    public static final int LANGUAGE_INDICATOR = 525;
    public static final int SAR_TOTAL_SEGMENTS = 526;
    public static final int SAR_SEGMENT_SEQNUM = 527;
    public static final int SC_INTERFACE_VERSION = 528;
    public static final int CALLBACK_NUM_PRES_IND = 770;
    public static final int CALLBACK_NUM_ATAG = 771;
    public static final int NUMBER_OF_MESSAGES = 772;
    public static final int CALLBACK_NUM = 897;
    public static final int DPF_RESULT = 1056;
    public static final int SET_DPF = 1057;
    public static final int MS_AVAILABILITY_STATUS = 1058;
    public static final int NETWORK_ERROR_CODE = 1059;
    public static final int MESSAGE_PAYLOAD = 1060;
    public static final int DELIVERY_FAILURE_REASON = 1061;
    public static final int MORE_MESSAGES_TO_SEND = 1062;
    public static final int MESSAGE_STATE = 1063;
    public static final int USSD_SERVICE_OP = 1281;
    public static final int DISPLAY_TIME = 4609;
    public static final int SMS_SINGNAL = 4611;
    public static final int MS_VALIDITY = 4612;
    public static final int ALIER_ON_MESSAGE_DELIVERY = 4876;
    public static final int ITS_REPLY_TYPE = 4992;
    public static final int ITS_SESSION_INFO = 4993;

    public OptionalParameterTag()
    {
    }

    public static String toString(int tag)
    {
        switch(tag)
        {
        case 5:
            return "dest_addr_subunit";

        case 6:
            return "dest_network_type";

        case 7:
            return "dest_bearer_type";

        case 8:
            return "dest_telematics_id";

        case 13:
            return "source_addr_subunit";

        case 14:
            return "source_network_type";

        case 15:
            return "source_bearer_type";

        case 16:
            return "source_telematics_id";

        case 23:
            return "qos_time_to_live";

        case 25:
            return "payload_type";

        case 29:
            return "additional_status_info_text";

        case 30:
            return "receipted_message_id";

        case 48:
            return "ms_msg_wait_facilities";

        case 513: 
            return "privacy_indicator";

        case 514: 
            return "source_subaddress";

        case 515: 
            return "dest_subaddress";

        case 516: 
            return "user_message_reference";

        case 517: 
            return "response_code";

        case 522: 
            return "source_port";

        case 523: 
            return "destination_port";

        case 524: 
            return "sar_msg_ref_num";

        case 525: 
            return "language_indicator";

        case 526: 
            return "sar_total_segments";

        case 527: 
            return "sar_segment_seqnum";

        case 528: 
            return "sc_interface_version";

        case 770: 
            return "callback_num_pres_ind";

        case 771: 
            return "callback_num_atag";

        case 772: 
            return "number_of_messages";

        case 897: 
            return "callback_num";

        case 1056: 
            return "dpf_result";

        case 1057: 
            return "set_dpf";

        case 1058: 
            return "ms_availability_status";

        case 1059: 
            return "network_error_code";

        case 1060: 
            return "message_payload";

        case 1061: 
            return "delivery_failure_reason";

        case 1062: 
            return "more_messages_to_send";

        case 1063: 
            return "message_state";

        case 1281: 
            return "ussd_service_op";

        case 4609: 
            return "display_time";

        case 4611: 
            return "sms_singnal";

        case 4612: 
            return "ms_validity";

        case 4876: 
            return "alier_on_message_delivery";

        case 4992: 
            return "its_reply_type";

        case 4993: 
            return "its_session_info";
        }
        return "reserved";
    }

    public static int toTag(String strName)
    {
        if(strName != null && strName.length() > 0)
        {
            if(strName.equals("dest_addr_subunit"))
                return 5;
            if(strName.equals("dest_network_type"))
                return 6;
            if(strName.equals("dest_bearer_type"))
                return 7;
            if(strName.equals("dest_telematics_id"))
                return 8;
            if(strName.equals("source_addr_subunit"))
                return 13;
            if(strName.equals("source_network_type"))
                return 14;
            if(strName.equals("source_bearer_type"))
                return 15;
            if(strName.equals("source_telematics_id"))
                return 16;
            if(strName.equals("qos_time_to_live"))
                return 23;
            if(strName.equals("payload_type"))
                return 25;
            if(strName.equals("additional_status_info_text"))
                return 29;
            if(strName.equals("receipted_message_id"))
                return 30;
            if(strName.equals("ms_msg_wait_facilities"))
                return 48;
            if(strName.equals("privacy_indicator"))
                return 513;
            if(strName.equals("source_subaddress"))
                return 514;
            if(strName.equals("dest_subaddress"))
                return 515;
            if(strName.equals("user_message_reference"))
                return 516;
            if(strName.equals("response_code"))
                return 517;
            if(strName.equals("source_port"))
                return 522;
            if(strName.equals("destination_port"))
                return 523;
            if(strName.equals("sar_msg_ref_num"))
                return 524;
            if(strName.equals("language_indicator"))
                return 525;
            if(strName.equals("sar_total_segments"))
                return 526;
            if(strName.equals("sar_segment_seqnum"))
                return 527;
            if(strName.equals("sc_interface_version"))
                return 528;
            if(strName.equals("callback_num_pres_ind"))
                return 770;
            if(strName.equals("callback_num_atag"))
                return 771;
            if(strName.equals("number_of_messages"))
                return 772;
            if(strName.equals("callback_num"))
                return 897;
            if(strName.equals("dpf_result"))
                return 1056;
            if(strName.equals("set_dpf"))
                return 1057;
            if(strName.equals("ms_availability_status"))
                return 1058;
            if(strName.equals("network_error_code"))
                return 1059;
            if(strName.equals("message_payload"))
                return 1060;
            if(strName.equals("delivery_failure_reason"))
                return 1061;
            if(strName.equals("more_messages_to_send"))
                return 1062;
            if(strName.equals("message_state"))
                return 1063;
            if(strName.equals("ussd_service_op"))
                return 1281;
            if(strName.equals("display_time"))
                return 4609;
            if(strName.equals("sms_singnal"))
                return 4611;
            if(strName.equals("ms_validity"))
                return 4612;
            if(strName.equals("alier_on_message_delivery"))
                return 4876;
            if(strName.equals("its_reply_type"))
                return 4992;
            if(strName.equals("its_session_info"))
                return 4993;
        }
        return -1;
    }



}