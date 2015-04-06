
package LeadTone.Packet;

import LeadTone.*;
import java.io.*;



public class Packet extends Buffer
{
    /**
     * ��Ϣ���ʱ��
     */
    static long m_lSurviveTime;
    /**
     * ��Ϣ������ʱ��
     */
    public long generate_time;
    /**
     * ��Ϣ������������
     */
    public String gateway_name;
    /**
     * ��Ϣ��������ĻỰ�̱߳��
     */
    public int session_id;
    /**
     * ��Ӧ�������
     */
    public long guid;

    public String origin_gw_id;

    /**
     * ��Ϣ�ܳ���
     */
    public int total_length;

    static
    {
        m_lSurviveTime = TimeConfig.DEFAULT_SURVIVE_TIME;
    }

    /**
     * ���췽����ʼ�������
     */
    public Packet()
    {
        generate_time = System.currentTimeMillis();
        gateway_name = null;
        session_id = 0;
        guid = 0L;
        origin_gw_id = "";
        total_length = 0;
    }

    /**
     * ���췽����ʼ�������
     * @param packet
     */
    public Packet(Packet packet)
    {
        super(packet);
        generate_time = System.currentTimeMillis();
        gateway_name = null;
        session_id = 0;
        guid = 0L;
        origin_gw_id = "";
        total_length = 0;
        gateway_name = packet.gateway_name;
        session_id = packet.session_id;
        guid = packet.guid;
        total_length = packet.total_length;
    }

    /**
     * �ж���Ϣ���Ƿ��Ѿ���ʱ
     * @return  ������Ϣ���Ƿ��Ѿ���ʱ�Ĳ���ֵ
     */
    public boolean isTimeout()
    {
        return m_lSurviveTime > 0L && System.currentTimeMillis() - generate_time > m_lSurviveTime;
    }

    /**
     * �����Ϣ������
     * @param lMethod ��������log4j����־����Ŀ��Ʋ���
     */
    public void dumpPacket(long lMethod)
    {
        LeadToneDate date = new LeadToneDate(generate_time);
        Log.log("\tgenerate_time = \"" + date.toLocaleString() + "\"" + " (" + (isTimeout() ? "timeout" : "alive") + ")", 0x400000000000000L | lMethod);
        Log.log("\tgateway_name = \"" + gateway_name + "\"", 0x400000000000000L | lMethod);
        Log.log("\tsession_id = " + session_id, 0x400000000000000L | lMethod);
        Log.log("\tguid = " + guid, 0x400000000000000L | lMethod);
        Log.log("\ttotal_length = " + total_length, 0x400000000000000L | lMethod);
    }

    /**
     * �������������ж�ȡ��Ϣ��
     * @param dis ����������
     * @throws IOException
     * @throws BufferException
     * @throws PacketException
     */
    public void inputPacket(DataInputStream dis)
        throws IOException, BufferException, PacketException
    {
        total_length = dis.readInt();
        if(total_length < 4 || total_length > 2048)
            throw new PacketException("Packet.inputPacket : invalid total_length !");
        int body_length = total_length - 4;
        if(body_length > 0)
            inputBuffer(dis, body_length);
        Log.log("Packet.inputPacket : input packet !", 0x400100000000000L);
        dumpPacket(0x100000000000L);
    }

    /**
     * �����������д����Ϣ��
     * @param dos ���������
     * @throws IOException
     * @throws BufferException
     * @throws PacketException
     */
    public void outputPacket(DataOutputStream dos)
        throws IOException, BufferException, PacketException
    {
        Log.log("Packet.outPacket : output packet !", 0x400100000000000L);
        total_length = m_nLength + 4;
        insertInteger(total_length);
        dumpPacket(0x100000000000L);
        outputBuffer(dos);
    }

    /**
     * ������Ϣ���Ĵ��ʱ��
     * @param lSurviveTime
     */
    public static void setSurviveTime(long lSurviveTime)
    {
        m_lSurviveTime = lSurviveTime;
    }


}
