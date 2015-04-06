package LeadTone.CMPPDatabase;

import LeadTone.Database.DatabasePool;
import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import java.io.PrintStream;

/**
 * 启动输入和输出进程，并监控线程状况
 */
public class CMPPDeliverDatabase extends Engine
{
    public static int m_nCount = 0;
    public static boolean m_input_switch = true;
    public static boolean m_output_switch = true;
    int m_nID;
    CMPPPacketQueue m_input_queue;
    CMPPPacketQueue m_output_queue;
    CMPPDeliverDatabaseOutput m_output;
    CMPPDeliverDatabaseInput m_input;
    

    public CMPPDeliverDatabase(int nID, CMPPPacketQueue input_queue, CMPPPacketQueue output_queue, DatabasePool pool)
    {
        super("CMPPDeliverDatabase(" + nID + ")");
        m_input_queue = null;
        m_output_queue = null;
        m_output = null;
        m_input = null;
        m_nID = nID;
        m_input_queue = input_queue;
        m_output_queue = output_queue;
        m_output = new CMPPDeliverDatabaseOutput(m_nID, m_output_queue, pool);
        m_input = new CMPPDeliverDatabaseInput(m_nID, m_input_queue, pool);
    }

    public void dump(PrintStream ps)
    {
        ps.print("\tdeliverdb(" + m_nID + "," + (isRunning() ? "+" : "-") + ") : " + "input(" + m_input.m_nCount + ")," + "output(" + m_output.m_nCount + ")\r\n");
    }

    public void empty()
    {
        m_input_queue = null;
        m_output_queue = null;
        m_output = null;
        m_input = null;
    }

    /**
     * 线程运行中不断检查输入进程和输出进程的工作状态
     */
    public void run()
    {
        try
        {
        	if (m_input_switch){
            Log.log("CMPPDeliverDatabase(" + m_nID + ").run : deliver database input begin startup !", 0x2000000000L);
            m_input.startup();
            Engine.wait(m_input);
        	}
        	
        	if(m_output_switch){
            Log.log("CMPPDeliverDatabase(" + m_nID + ").run : deliver database output begin startup !", 0x2000000000L);
            m_output.startup();
            Engine.wait(m_output);
        	}
            
            Log.log("CMPPDeliverDatabase(" + m_nID + ").run : deliver database startup !", 0x2000000000L);
            m_nStatus = 1;
            for(; isRunning(); Engine.sleep())
            {
            	
            	if (m_input_switch){
                if(!m_input.isRunning())
                {
                    Log.log("CMPPDeliverDatabase(" + m_nID + ").run : database input unexpected stopped !", 0x2000002000000000L);
                    break;
                }
            	}
            	
            	if(m_output_switch){
                if(!m_output.isRunning())
                {
                    Log.log("CMPPDeliverDatabase(" + m_nID + ").run : database output unexpected stopped !", 0x2000002000000000L);
                    break;
                }
            	}
            	
            }

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPDeliverDatabase(" + m_nID + ").run : unexpected exit !", 0x2000002000000000L);
        }
        Log.log("CMPPDeliverDatabase(" + m_nID + ").run : deliver database input begin shutdown !", 0x2000000000L);
        m_input.shutdown();
        Engine.wait(m_input);
        Log.log("CMPPDeliverDatabase(" + m_nID + ").run : deliver database output being shutdown !", 0x2000000000L);
        m_output.shutdown();
        Engine.wait(m_output);
        empty();
        m_nStatus = 3;
        Log.log("CMPPDeliverDatabase(" + m_nID + ").run : thread stopped !", 0x2000000000L);
    }



}