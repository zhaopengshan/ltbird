package LeadTone.Packet.SMPPPacket;


public class RegisteredDelivery
{
    public static final byte RD_DELIVERY_RECEIPT_MASK = 3;
    public static final byte RD_REQUEST_SUCCESS_AND_FAILURE = 1;
    public static final byte RD_REQUEST_FAILURE = 2;
    public static final byte RD_ORIGINATED_ACKNOWLEDGEMENT_MASK = 12;
    public static final byte RD_REQUEST_DELIVERY_ACKNOWLEDGEMENT = 4;
    public static final byte RD_REQUEST_USER_ACKNOWLEDGEMENT = 8;
    public static final byte RD_REQUEST_INTERMEDIATE_NOTIFICATION = 16;

    public RegisteredDelivery()
    {
    }

    public static String toString(byte registered_delivery)
    {
        String strTemp = "";
        strTemp = strTemp + "delivery receipt : ";
        if((registered_delivery & 3) == 1)
            strTemp = strTemp + "on success or failure";
        else
        if((registered_delivery & 3) == 2)
            strTemp = strTemp + "on failure";
        else
        if((registered_delivery & 3) == 0)
            strTemp = strTemp + "none";
        else
            strTemp = strTemp + "reserved";
        strTemp = strTemp + ",";
        strTemp = strTemp + "originated acknowledgement : ";
        if((registered_delivery & 0xc) == 12)
            strTemp = strTemp + "both";
        else
        if((registered_delivery & 0xc) == 4)
            strTemp = strTemp + "delivery only";
        else
        if((registered_delivery & 0xc) == 8)
            strTemp = strTemp + "user only";
        else
            strTemp = strTemp + "none";
        strTemp = strTemp + ",";
        strTemp = strTemp + "intermediate notification : ";
        if((registered_delivery & 0x10) != 0)
            strTemp = strTemp + "requested";
        else
            strTemp = strTemp + "none";
        return strTemp;
    }



}