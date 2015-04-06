package LeadTone.Packet.SMPPPacket;


public class DataCoding
{

    public DataCoding()
    {
    }

    public static String toString(byte data_coding)
    {
        switch(data_coding)
        {
        case 0:
            return "SMSC Default Alphabet";

        case 1:
            return "IA5 (CCIT T.50)/ASCII (ANSI X3.4)";

        case 2:
        case 4:
            return "Octet unspecified (8-bit binary)";

        case 3:
            return "Latin 1 (ISO-8859-1)";

        case 5:
            return "JIS (X 0208-1990)";

        case 6:
            return "Cyrllic (ISO-8859-1)";

        case 7:
            return "Latin/Hebrew (ISO-8859-8)";

        case 8:
            return "UCS2 (ISO/IEC-10646)";

        case 9:
            return "Pictogram Encoding";

        case 10:
            return "ISO-20220-JP (Music Codes)";

        case 13:
            return "Extended Kanji JIS (X 0212-1900)";

        case 14:
            return "KS C 5601";

        case 11:
        case 12: 
        default:
            return "reserved";
        }
    }
}