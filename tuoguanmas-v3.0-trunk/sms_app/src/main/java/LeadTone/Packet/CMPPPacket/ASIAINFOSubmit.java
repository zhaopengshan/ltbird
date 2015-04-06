package LeadTone.Packet.CMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;


/**
 * �μ�CMPPЭ��2.1�Է�����Ϣ�Ķ��壬��Ϊ���Ŷ�CMPP��ʵ�֣���ο����Ŷ�CMPPЭ��ʵ���е�������
 */
public class ASIAINFOSubmit extends CMPPSubmit
{

    public ASIAINFOSubmit(int sequence_id)
    {
        super(sequence_id);
    }

    public ASIAINFOSubmit(CMPPPacket packet)
    {
        super(packet);
    }

    public void wrap(String service_code)
        throws BufferException
    {
        Log.log("ASIAINFOSubmit.wrap : wrap elements !", 0x2800000000000L);
        //src_terminal_id = service_code + src_terminal_id; //for sms submit return ErrorCode 10 (src_terminal_id is wrong)
        dump(0x2800000000000L);
        addLong(msg_id);
        addByte(pk_total);
        addByte(pk_number);
        addByte(registered_delivery);
        addByte(msg_level);
        addString(service_id, 10);
        addByte(fee_user_type);
        addString(fee_terminal_id, 21);
        addByte(tp_pid);
        addByte(tp_udhi);
        addByte(msg_fmt);
        addString(msg_src, 6);
        addString(2, fee_type);
        addString(6, fee_code);
        addString(valid_time, 17);
        addString(at_time, 17);
        addString(src_terminal_id, 21); //for sms submit return ErrorCode 10 (src_terminal_id is wrong)
        addByte(dest_usr_tl);
        for(int i = dest_usr_tl; i > 0; i--)
            addString(dest_terminal_id[i - 1], 21);

        addByte((byte)(msg_length & 0xff));
        addBytes(msg_content);
        //CMPP��׼Э����ʵ���б����ֶ�reserve 8�ֽڣ����ŵ�ʵ����û��
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("ASIAINFOSubmit.unwrap : unwrap elements !", 0x2800000000000L);
        msg_id = getLong();
        pk_total = getByte();
        pk_number = getByte();
        registered_delivery = getByte();
        msg_level = getByte();
        service_id = getString(10);
        fee_user_type = getByte();
        fee_terminal_id = getString(21);
        tp_pid = getByte();
        tp_udhi = getByte();
        msg_fmt = getByte();
        msg_src = getString(6);
        fee_type = getString(2);
        fee_code = getString(6);
        valid_time = getString(17);
        at_time = getString(17);
        src_terminal_id = getString(21);
        dest_usr_tl = getByte();
        dest_terminal_id = new String[dest_usr_tl];
        for(int i = 0; i < dest_usr_tl; i++)
            dest_terminal_id[i] = getString(21);

        msg_length = getByte();
        msg_content = getBytes(msg_length);
        //CMPP��׼Э����ʵ���б����ֶ�reserve 8�ֽڣ����ŵ�ʵ����û��
        dump(0x2800000000000L);
    }
}