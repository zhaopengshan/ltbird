package LeadTone.Packet.CMPPPacket;

import LeadTone.*;

/**
 * �μ�CMPPЭ��2.1�Խ���������Ϣ�Ķ���
 */
public class CMPPConnect extends CMPPPacket
{
    /**
     * �μ�CMPPЭ��2.1��������Ϣ�Ķ���
     */
    public String source_addr;
    /**
     * �μ�CMPPЭ��2.1��������Ϣ�Ķ���
     */
    public byte authenticator_sp[];
    /**
     * �μ�CMPPЭ��2.1��������Ϣ�Ķ���
     */
    public byte version;
    /**
     * �μ�CMPPЭ��2.1��������Ϣ�Ķ���
     */
    public int time_stamp;

    /**
     * ���췽����ʼ�������
     * @param sequence_id
     */
    public CMPPConnect(int sequence_id)
    {
        super(1, sequence_id);
        source_addr = null;
        authenticator_sp = null;
        version = 32;
        time_stamp = 0;
    }

    /**
     * ���췽����ʼ�������
     * @param packet
     */
    public CMPPConnect(CMPPPacket packet)
    {
        super(packet);
        source_addr = null;
        authenticator_sp = null;
        version = 32;
        time_stamp = 0;
    }

    /**
     * ����������Ϣ����ʽ�Ƿ�Ϸ�
     * @return ���ظ�ʽ�Ƿ�Ϸ�
     */
    public boolean isValid()
    {
        if(command_id != 1)
        {
            Log.log("CMPPConnect.isValid : not a CMPP_CONNECT command !", 0x80600000000000L);
            return false;
        }
        if(source_addr != null && source_addr.length() > 6)
        {
            Log.log("CMPPConnect.isValid : invalid source_addr length !", 0x80600000000000L);
            return false;
        }
        if(authenticator_sp == null || authenticator_sp.length != 16)
        {
            Log.log("CMPPConnect.isValid : invalid authenticator_sp length !", 0x80600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * ���������Ϣ������
     * @param lMethod
     */
    public void dump(long lMethod)
    {
        Log.log("\tsource_addr = \"" + source_addr + "\"", 0x80000000000000L | lMethod);
        Log.log("\tauthenticator_sp = 0x" + Utility.toHexString(authenticator_sp), 0x80000000000000L | lMethod);
        Log.log("\tversion = 0x" + Utility.toHexString(version), 0x80000000000000L | lMethod);
        Log.log("\ttime_stamp = 0x" + Utility.toHexString(time_stamp), 0x80000000000000L | lMethod);
    }

    /**
     * ��װ������Ϣ��
     * @throws BufferException
     */
    public void wrap()
        throws BufferException
    {
        Log.log("CMPPConnect.wrap : wrap elements !", 0x80800000000000L);
        dump(0x800000000000L);
        addString(source_addr, 6);
        addBytes(authenticator_sp);
        addByte(version);
        addInteger(time_stamp);
    }

    /**
     * ����������Ϣ��
     * @throws BufferException
     */
    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPConnect.unwrap : unwrap elements !", 0x80800000000000L);
        source_addr = getString(6);
        authenticator_sp = getBytes(16);
        version = getByte();
        time_stamp = getInteger();
        dump(0x800000000000L);
    }


}