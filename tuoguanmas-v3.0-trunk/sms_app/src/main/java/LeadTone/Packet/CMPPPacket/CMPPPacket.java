package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.Packet;
import LeadTone.Utility;


/**
 * �μ�CMPPЭ��2.1��Ϣ��ʽ�Ķ��壬������Ϊ����������Ϣ������ʵ�ֹ���������Ϣͷ�Ĳ���
 */
public class CMPPPacket extends Packet
{
    /**
     * ��Ϣָ��
     */
    public int command_id;
    /**
     * ��Ϣ���к�
     */
    public int sequence_id;

    /**
     * ���췽����ʼ�������
     */
    public CMPPPacket()
    {
        command_id = 0x80000000;
        sequence_id = 1;
    }

    /**
     * ���췽����ʼ�������
     * @param command_id ��Ϣָ��
     * @param sequence_id ��Ϣ���к�
     */
    public CMPPPacket(int command_id, int sequence_id)
    {
        this.command_id = 0x80000000;
        this.sequence_id = 1;
        this.command_id = command_id;
        this.sequence_id = sequence_id;
    }

    /**
     * ���췽����ʼ�������
     * @param packet
     */
    public CMPPPacket(Packet packet)
    {
        super(packet);
        command_id = 0x80000000;
        sequence_id = 1;
    }

    /**
     * ���췽����ʼ�������
     * @param packet
     */
    public CMPPPacket(CMPPPacket packet)
    {
        super(packet);
        command_id = 0x80000000;
        sequence_id = 1;
        command_id = packet.command_id;
        sequence_id = packet.sequence_id;
    }

    /**
     * �����Ϣͷ��ʽ�Ƿ�Ϸ�
     * @return ������Ϣͷ�Ƿ�Ϸ��Ĳ���ֵ
     */
    public boolean isValid()
    {
        if(!CMPPCommandID.isValid(command_id))
        {
            Log.log("CMPPPacket.isValid : not a valid command !", 0x200600000000000L);
            return false;
        }
        if(sequence_id < 0)
        {
            Log.log("CMPPPacket.isValid : invalid sequence_id !", 0x200600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * �����Ϣͷ������
     * @param lMethod
     */
    public void dumpCMPPPacket(long lMethod)
    {
        if(Log.isRequested(lMethod))
        {
            Log.log("\tcommand_id = 0x" + Utility.toHexString(command_id) + " (" + CMPPCommandID.toString(command_id) + ")", 0x200000000000000L | lMethod);
            Log.log("\tsequence_id = " + sequence_id, 0x200000000000000L | lMethod);
        }
    }

    /**
     * ��װ��Ϣͷ
     * @throws BufferException
     */
    public void wrapCMPPPacket()
        throws BufferException
    {
        Log.log("CMPPPacket.wrapCMPPPacket : wrap elements !", 0x200800000000000L);
        dumpCMPPPacket(0x800000000000L);
        insertInteger(sequence_id);
        insertInteger(command_id);
    }

    /**
     * ������Ϣͷ
     * @throws BufferException
     */
    public void unwrapCMPPPacket()
        throws BufferException
    {
        Log.log("CMPPPacket.unwrapCMPPPacket : unwrap elements !", 0x200800000000000L);
        command_id = getInteger();
        sequence_id = getInteger();
        dumpCMPPPacket(0x800000000000L);
    }


}