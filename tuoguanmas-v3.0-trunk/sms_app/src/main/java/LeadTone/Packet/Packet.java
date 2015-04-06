
package LeadTone.Packet;

import LeadTone.*;
import java.io.*;



public class Packet extends Buffer
{
    /**
     * 消息存活时限
     */
    static long m_lSurviveTime;
    /**
     * 消息包生成时刻
     */
    public long generate_time;
    /**
     * 消息包隶属的网关
     */
    public String gateway_name;
    /**
     * 消息包被处理的会话线程编号
     */
    public int session_id;
    /**
     * 对应表的主键
     */
    public long guid;

    public String origin_gw_id;

    /**
     * 消息总长度
     */
    public int total_length;

    static
    {
        m_lSurviveTime = TimeConfig.DEFAULT_SURVIVE_TIME;
    }

    /**
     * 构造方法初始化类变量
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
     * 构造方法初始化类变量
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
     * 判断消息包是否已经超时
     * @return  返回消息包是否已经超时的布尔值
     */
    public boolean isTimeout()
    {
        return m_lSurviveTime > 0L && System.currentTimeMillis() - generate_time > m_lSurviveTime;
    }

    /**
     * 输出消息包内容
     * @param lMethod 用于类似log4j的日志输出的控制参数
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
     * 从输入数据流中读取消息包
     * @param dis 输入数据流
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
     * 向输出数据流写入消息包
     * @param dos 输出数据流
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
     * 设置消息包的存活时限
     * @param lSurviveTime
     */
    public static void setSurviveTime(long lSurviveTime)
    {
        m_lSurviveTime = lSurviveTime;
    }


}
