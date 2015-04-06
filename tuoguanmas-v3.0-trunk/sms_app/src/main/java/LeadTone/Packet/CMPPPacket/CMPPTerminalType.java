
package LeadTone.Packet.CMPPPacket;


public class CMPPTerminalType
{

    public CMPPTerminalType()
    {
    }

    public static String toStringDesc(int iType)
    {
        if(iType == 0)
            return "真实号码";
        if(iType == 1)
            return "伪号码";
        else
            return "未知类型";
    }
}
