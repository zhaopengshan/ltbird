package LeadTone.CMPPDatabase;

import LeadTone.Database.DatabasePool;
import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import java.io.PrintStream;


/**
 * 启动输入和输出进程，并监控线程状况
 */
public class CMPPQueryDatabase extends Engine
{
    public static int m_nCount = 0;
    public static boolean m_input_switch = true;
    public static boolean m_output_switch = true;
    int m_nID;
    CMPPPacketQueue m_input_queue;
    CMPPPacketQueue m_output_queue;
    CMPPQueryDatabaseOutput m_output;
    CMPPQueryDatabaseInput m_input;
    

    public CMPPQueryDatabase(int nID, CMPPPacketQueue input_queue, CMPPPacketQueue output_queue, DatabasePool pool)
    {
        super("CMPPQueryDatabase(" + nID + ")");
        m_input_queue = null;
        m_output_queue = null;
        m_output = null;
        m_input = null;
        m_nID = nID;
        m_input_queue = input_queue;
        m_output_queue = output_queue;
        m_output = new CMPPQueryDatabaseOutput(m_nID, m_output_queue, pool);
        m_input = new CMPPQueryDatabaseInput(m_nID, m_input_queue, pool);
    }

    public void dump(PrintStream ps)
    {
        ps.print("\tquerydb(" + m_nID + "," + (isRunning() ? "+" : "-") + ") : " + "input(" + m_input.m_nCount + ")," + "output(" + m_output.m_nCount + ")\r\n");
    }

    public void empty()
    {
        m_input_queue = null;
        m_output_queue = null;
    }

    public void run()
    {
        try
        {
        	if (m_input_switch){
            Log.log("CMPPQueryDatabase(" + m_nID + ").run : query database input begin startup !", 0x4000000000L);
            m_input.startup();
            Engine.wait(m_input);
        	}
        	
        	if(m_output_switch){
            Log.log("CMPPQueryDatabase(" + m_nID + ").run : query database output begin startup !", 0x4000000000L);
            m_output.startup();
            Engine.wait(m_output);
        	}
        	
            Log.log("CMPPQueryDatabase(" + m_nID + ").run : query database startup !", 0x4000000000L);
            m_nStatus = 1;
            for(; isRunning(); Engine.sleep())
            {
            	if (m_input_switch){
                if(!m_input.isRunning())
                {
                    Log.log("CMPPQueryDatabase(" + m_nID + ").run : database input unexpected stopped !", 0x2000004000000000L);
                    break;
                }
            	}
            	
            	if(m_output_switch){
                if(!m_output.isRunning())
                {
                   Log.log("CMPPQueryDatabase(" + m_nID + ").run : database output unexpected stopped !", 0x2000004000000000L);
                   break;
                }
            	}
            }

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPQueryDatabase(" + m_nID + ").run : unexpected exit !", 0x2000004000000000L);
        }
        Log.log("CMPPQueryDatabase(" + m_nID + ").run : query database input being shutdown !", 0x4000000000L);
        m_input.shutdown();
        Engine.wait(m_input);
        Log.log("CMPPQueryDatabase(" + m_nID + ").run : query database output being shutdown !", 0x4000000000L);
        m_output.shutdown();
        Engine.wait(m_output);
        empty();
        m_nStatus = 3;
        Log.log("CMPPQueryDatabase(" + m_nID + ").run : thread stopped !", 0x4000000000L);
    }



}