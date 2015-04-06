package LeadTone.Packet.SGIPPacket;


public class SGIPMessageType
{

    public SGIPMessageType()
    {
    }

    public static String toString(byte message_type)
    {
        switch(message_type)
        {
        case 0: 
            return "\u77ED\u6D88\u606F";
        }
        return "\u4FDD\u7559";
    }
}