
package LeadTone.Packet.CMPPPacket;


public class CMPPTerminalType
{

    public CMPPTerminalType()
    {
    }

    public static String toStringDesc(int iType)
    {
        if(iType == 0)
            return "��ʵ����";
        if(iType == 1)
            return "α����";
        else
            return "δ֪����";
    }
}
