package LeadTone.Packet.CMPPPacket;

import LeadTone.Log;


/**
 * �μ�CMPPЭ��2.1�ԶϿ�������Ϣ�Ķ���
 */
public class CMPPTerminate extends CMPPPacket
{

    public CMPPTerminate(int sequence_id)
    {
        super(2, sequence_id);
    }

    public CMPPTerminate(CMPPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 2)
        {
            Log.log("CMPPTerminate.isValid : not a CMPP_TERMINATE command !", 0x40600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}