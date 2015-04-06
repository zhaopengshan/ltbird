package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;

/**
 * �μ�CMPPЭ��2.1�Խ�����Ϣ�Ķ��壬��Ϊ׿����CMPP��ʵ�֣���ο�׿����CMPPЭ��ʵ���е�������
 */
public class MISCDeliver extends CMPPDeliver
{

    public MISCDeliver(int sequence_id)
    {
        super(sequence_id);
    }

    public MISCDeliver(CMPPPacket packet)
    {
        super(packet);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("MISCDeliver.unwrap : unwrap elements !", 0x4800000000000L);
        msg_id = getLong();
        destination_id = getString(21);
        service_id = getString(10);
        tp_pid = getByte();
        tp_udhi = getByte();
        msg_fmt = getByte();
        src_terminal_id = getString(21);
        registered_delivery = getByte();
        msg_length = getByte() & 0xff;
        msg_content = getBytes(msg_length);
        if(registered_delivery == 1)
        {
            setOffset(false, -msg_length);
            status_report.unwrapMISC(this);
        }
        reserve = getString(8);
        dump(0x4800000000000L);
    }
}