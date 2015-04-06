package LeadTone.Port;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;


/**
 * 用于系统与外部接口的扩展
 * 此接口为系统与外部除了数据库接口外的另一基于XML的接口
 * 用于基于XML格式的数据交换
 * 整个Port包下的类实现此接口功能
 */
public class CMPPXMLPorter extends Engine
{
    static final int DEFAULT_TIMEOUT = 15000;
    static final int DEFAULT_PACKET_QUEUE_SIZE = 64;
    public int m_nID;
    boolean m_bNeedTerminate;
    Socket m_socket;
    public CMPPPacketQueue m_input;
    public CMPPPacketQueue m_output;
    CMPPXMLReader m_reader;
    CMPPXMLWriter m_writer;
    CMPPXMLExchanger m_exchanger;

    public CMPPXMLPorter(int nID, Socket socket, CMPPXMLExchanger exchanger)
        throws IOException
    {
        super("CMPPXMLPorter(" + nID + ")");
        m_bNeedTerminate = false;
        m_socket = null;
        m_input = new CMPPPacketQueue();
        m_output = new CMPPPacketQueue();
        m_reader = null;
        m_writer = null;
        m_exchanger = null;
        m_nID = nID;
        m_socket = socket;
        m_socket.setSoTimeout(15000);
        m_reader = new CMPPXMLReader(m_nID, m_socket, m_input);
        m_writer = new CMPPXMLWriter(m_nID, m_socket, m_output);
        m_exchanger = exchanger;
    }

    public void empty()
    {
        m_socket = null;
        m_input.empty();
        m_input = null;
        m_output.empty();
        m_output = null;
        m_reader = null;
        m_writer = null;
        m_exchanger = null;
    }

    public void dump(PrintStream ps)
    {
        ps.print("\txmlporter(" + m_nID + "," + (isRunning() ? "running" : "stopped") + ") : " + "read(" + m_reader.m_nCount + ")," + "write(" + m_writer.m_nCount + ")\r\n");
    }

    public void close()
    {
        try
        {
            m_socket.close();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPXMLPorter(" + m_nID + ").close : unexpected exit !", 0x2000000400000000L);
        }
    }

    public void checkTimeout()
    {
        m_input.checkTimeout();
        m_output.checkTimeout();
    }

    public void handleQuery(CMPPQuery query)
        throws SQLException
    {
        m_exchanger.m_input.push(query);
    }

    public void handleSubmit(CMPPSubmit submit)
        throws SQLException
    {
        m_exchanger.m_input.push(submit);
    }

    public void handleActiveTest(CMPPActiveTest activetest)
    {
        CMPPActiveTestResponse response = new CMPPActiveTestResponse(activetest.sequence_id);
        response.gateway_name = activetest.gateway_name;
        response.guid = activetest.guid;
        m_output.push(response);
    }

    public void handleTerminate(CMPPTerminate terminate)
    {
        Log.log("CMPPXMLPorter(" + m_nID + ").run : receive a terminate command !", 0x400000000L);
        m_bNeedTerminate = true;
    }

    public void run()
    {
        try
        {
            Log.log("CMPPXMLPorter(" + m_nID + ").run : xml reader begin startup !", 0x400000000L);
            m_reader.startup();
            Engine.wait(m_reader);
            Log.log("CMPPXMLPorter(" + m_nID + ").run : xml writer begin startup !", 0x400000000L);
            m_writer.startup();
            Engine.wait(m_writer);
            Log.log("CMPPXMLPorter(" + m_nID + ").run : xml porter startup !", 0x400000000L);
            m_nStatus = 1;
            while(isRunning()) 
            {
                if(!m_reader.isRunning())
                {
                    Log.log("CMPPXMLPorter(" + m_nID + ").run : xml reader unexpected stopped !", 0x2000000400000000L);
                    break;
                }
                if(m_reader.isTimeout())
                {
                    Log.log("CMPPXMLPorter(" + m_nID + ").run : xml reader no packets for a long time !", 0x2000000400000000L);
                    break;
                }
                if(!m_writer.isRunning())
                {
                    Log.log("CMPPXMLPorter(" + m_nID + ").run : xml writer unexpected stopped !", 0x2000000400000000L);
                    break;
                }
                if(m_writer.isTimeout())
                {
                    Log.log("CMPPXMLPorter(" + m_nID + ").run : xml writer no packets for a long time !", 0x2000000400000000L);
                    break;
                }
                if(m_bNeedTerminate)
                {
                    Log.log("CMPPXMLPorter(" + m_nID + ").run : xml porter terminated !", 0x400000000L);
                    break;
                }
                m_exchanger.distribute();
                CMPPPacket packet = (CMPPPacket)m_input.pop();
                if(packet == null)
                {
                    nap();
                } else
                {
                    if(packet.command_id == 4)
                        handleSubmit((CMPPSubmit)packet);
                    else
                    if(packet.command_id != 5)
                        if(packet.command_id == 6)
                            handleQuery((CMPPQuery)packet);
                        else
                        if(packet.command_id == 8)
                            handleActiveTest((CMPPActiveTest)packet);
                        else
                        if(packet.command_id == 2)
                            handleTerminate((CMPPTerminate)packet);
                    packet.empty();
                    packet = null;
                    nap();
                }
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPXMLPorter(" + m_nID + ").run : unexpected exit !", 0x2000000400000000L);
        }
        Log.log("CMPPXMLPorter(" + m_nID + ").run : xml reader begin shutdown !", 0x400000000L);
        m_reader.shutdown();
        Engine.wait(m_reader);
        Log.log("CMPPXMLPorter(" + m_nID + ").run : xml writer begin shutdown !", 0x400000000L);
        m_writer.shutdown();
        Engine.wait(m_writer);
        empty();
        m_nStatus = 3;
        Log.log("CMPPXMLPorter(" + m_nID + ").run : thread stopped !", 0x400000000L);
    }



}
