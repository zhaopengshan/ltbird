package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;

/**
 * 参见CMPP协议2.1对接收消息的定义，此为卓望对CMPP的实现，请参考卓望对CMPP协议实现中的特殊性
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