package LeadTone.Packet.SGIPPacket;


public class SGIPAgentFlag
{

    public SGIPAgentFlag()
    {
    }

    public static String toString(byte agent_flag)
    {
        switch(agent_flag)
        {
        case 0:
            return "\u5E94\u6536";

        case 1: 
            return "\u5B9E\u6536";
        }
        return "\u4FDD\u7559";
    }
}