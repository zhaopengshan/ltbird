package LeadTone.Packet.SGIPPacket;



public class SGIPNodeStatusResult
{

    public SGIPNodeStatusResult()
    {
    }

    public static String toString(byte result)
    {
        switch(result)
        {
        case 0:
            return "\u63A5\u6536\u6210\u529F";

        case 1: 
            return "\u7B49\u5F85\u5904\u7406";
        }
        return SGIPResult.toString(result);
    }
}