package LeadTone.Packet.CMPPPacket;

import LeadTone.*;


/**
 * �μ�CMPPЭ��2.1�Խ���������Ϣ�ظ��Ķ���
 */
public class CMPPConnectResponse extends CMPPPacket
{
    /**
     * �μ�CMPPЭ��2.1�����ӻظ���Ϣ�Ķ���
     */
    public byte status;
    /**
     * �μ�CMPPЭ��2.1�����ӻظ���Ϣ�Ķ���
     */
    public byte authenticator_ismg[];
    /**
     * �μ�CMPPЭ��2.1�����ӻظ���Ϣ�Ķ���
     */
    public byte version;

    /**
     * ���췽����ʼ�������
     * @param sequence_id
     */
    public CMPPConnectResponse(int sequence_id)
    {
        super(0x80000001, sequence_id);
        status = 0;
        authenticator_ismg = null;
        version = 18;
    }

    /**
     * ���췽����ʼ�������
     * @param packet
     */
    public CMPPConnectResponse(CMPPPacket packet)
    {
        super(packet);
        status = 0;
        authenticator_ismg = null;
        version = 18;
    }

    /**
     * �������ӻظ���Ϣ����ʽ�Ƿ�Ϸ�
     * @return ���ظ�ʽ�Ƿ�Ϸ�
     */
    public boolean isValid()
    {
        if(command_id != 0x80000001)
        {
            Log.log("CMPPConnectResponse.isValid : not a CMPP_CONNECT_RESPONSE command !", 0x80600000000000L);
            return false;
        }
        if(authenticator_ismg == null || authenticator_ismg.length != 16)
        {
            Log.log("CMPPConnectResponse.isValid : invalid ismg_auth length!", 0x80600000000000L);
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
        Log.log("\tstatus = " + status + " (" + CMPPStatus.toString(status) + ") ", 0x80000000000000L | lMethod);
        Log.log("\tauthenticator_ismg = " + Utility.toHexString(authenticator_ismg), 0x80000000000000L | lMethod);
        Log.log("\tversion = 0x" + Utility.toHexString(version), 0x80000000000000L | lMethod);
    }

    /**
     * ��װ������Ϣ��
     * @throws BufferException
     */
    public void wrap()
        throws BufferException
    {
        Log.log("CMPPConnectResponse.wrap : wrap elements !", 0x80800000000000L);
        dump(0x800000000000L);
        addByte(status);
        addBytes(authenticator_ismg);
        addByte(version);
    }

    /**
     * ����������Ϣ��
     * @throws BufferException
     */
    public void unwrap()
        throws BufferException
    {
        Log.log("CMPPConnectResponse.unwrap : unwrap elements !", 0x80800000000000L);
        status = getByte();
        authenticator_ismg = getBytes(16);
        version = getByte();
        dump(0x800000000000L);
    }


}