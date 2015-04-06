package LeadTone.Packet.SMPPPacket;


public class ESMClass
{
    public static final byte ESM_MESSAGING_MODE_MASK = 3;
    public static final byte ESM_DEFAULT_SMSC_MODE = 0;
    public static final byte ESM_DATAGRAM_MODE = 1;
    public static final byte ESM_FORWARD_MODE = 2;
    public static final byte ESM_STORE_AND_FORWARD_MODE = 3;
    public static final byte ESM_MESSAGE_TYPE_MASK = 60;
    public static final byte ESM_DEFAULT_MESSAGE_TYPE = 0;
    public static final byte ESM_DELIVERY_ACKNOWLEDGEMENT = 8;
    public static final byte ESM_USER_ACKNOWLEDGEMENT = 16;
    public static final byte ESM_DELIVER_RECEIPT = 4;
    public static final byte ESM_CONVERSATION_ABORT = 24;
    public static final byte ESM_INTERMEDIATE_DELIVERY_NOTIFICATION = 32;
    public static final byte ESM_NETWORK_SPECIFIC_FEATURES_MASK = -64;
    public static final byte ESM_UDHI_INDICATOR = 64;
    public static final byte ESM_REPLY_PATH = -128;

    public ESMClass()
    {
    }

    public static String toString(byte esm_class)
    {
        String strTemp = "";
        strTemp = strTemp + "message mode : ";
        switch(esm_class & 3)
        {
        case 0:
            strTemp = strTemp + "Default SMSC Mode";
            break;

        case 1:
            strTemp = strTemp + "Datagram mode";
            break;

        case 2:
            strTemp = strTemp + "Forward mode";
            break;

        case 3:
            strTemp = strTemp + "Store and Forward mode";
            break;

        default:
            strTemp = strTemp + "reserved";
            break;
        }
        strTemp = strTemp + ",";
        strTemp = strTemp + "message type : ";
        switch(esm_class & 0x3c)
        {
        case 0:
            strTemp = strTemp + "Default message Type";
            break;

        case 8:
            strTemp = strTemp + "Delivery Acknowledgement";
            break;

        case 16:
            strTemp = strTemp + "User Acknowledgement";
            break;

        case 4:
            strTemp = strTemp + "Delivery Receipt";
            break;

        case 24:
            strTemp = strTemp + "Conversation Abort";
            break;

        case 32:
            strTemp = strTemp + "Intermediate Delivery Notification";
            break;

        default:
            strTemp = strTemp + "reserve";
            break;
        }
        strTemp = strTemp + ",";
        strTemp = strTemp + "network specific feature : ";
        if((esm_class & 0xffffffc0) == -64)
            strTemp = strTemp + "UDHI Indicator & Reply Path";
        else
        if((esm_class & 0xffffffc0) == -128)
            strTemp = strTemp + "Reply Path";
        else
        if((esm_class & 0xffffffc0) == 64)
            strTemp = strTemp + "UDHI Indicator";
        else
            strTemp = strTemp + "none";
        return strTemp;
    }



}