package LeadTone.Port;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPDeliver;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import LeadTone.TimeConfig;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


/**
 * 用于系统与外部接口的扩展
 * 此接口为系统与外部除了数据库接口外的另一基于XML的接口
 * 用于基于XML格式的数据交换
 * 整个Port包下的类实现此接口功能
 */
public class CMPPXMLExchanger extends Engine
{
    public CMPPPacketQueue m_input;
    public CMPPPacketQueue m_output;
    ServerSocket m_socket;
    Vector m_porters;

    public CMPPXMLExchanger(int nPort)
        throws IOException
    {
        super("CMPPXMLExchanger");
        m_input = new CMPPPacketQueue();
        m_output = new CMPPPacketQueue();
        m_socket = null;
        m_porters = new Vector();
        m_socket = new ServerSocket(nPort);
        m_socket.setSoTimeout((int)TimeConfig.DEFAULT_LISTEN_TIMEOUT);
    }

    public void close()
    {
        try
        {
            if(m_socket != null)
                m_socket.close();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPXMLExchanger.close : unexpected exit !", 0x2000000800000000L);
        }
    }

    public void dump(PrintStream ps)
    {
        ps.print("\txmlexchanger(" + (isRunning() ? "running" : "stopped") + ") : " + "count(" + m_porters.size() + ")," + "iqs(" + m_input.getSize() + ")," + "oqs(" + m_output.getSize() + ")\r\n");
        for(int i = 0; i < m_porters.size(); i++)
        {
            CMPPXMLPorter porter = (CMPPXMLPorter)m_porters.elementAt(i);
            porter.dump(ps);
        }

    }

    public void empty()
    {
        m_socket = null;
        m_input.empty();
        m_input = null;
        m_output.empty();
        m_output = null;
    }

    public CMPPXMLPorter getPorter(int nID)
    {
        for(int i = 0; i < m_porters.size(); i++)
        {
            CMPPXMLPorter porter = (CMPPXMLPorter)m_porters.elementAt(i);
            if(porter.m_nID == nID)
                return porter;
        }

        return null;
    }

    public synchronized int getUniquePorterID()
    {
        for(int i = 1; i <= m_porters.size(); i++)
            if(getPorter(i) == null)
                return i;

        return m_porters.size() + 1;
    }

    public void distribute()
    {
        CMPPDeliver deliver = (CMPPDeliver)m_output.pop();
        if(deliver != null)
        {
            for(int i = 0; i < m_porters.size(); i++)
            {
                CMPPXMLPorter porter = (CMPPXMLPorter)m_porters.elementAt(i);
                porter.m_output.push(deliver);
            }

            deliver.empty();
            deliver = null;
        }
    }

    public void shutdownAllPorters()
    {
        for(int i = 0; i < m_porters.size(); i++)
        {
            CMPPXMLPorter porter = (CMPPXMLPorter)m_porters.elementAt(i);
            porter.shutdown();
            if(i == m_porters.size() - 1)
                Engine.wait(porter);
        }

    }

    public void checkAllPorters()
    {
        for(int i = 0; i < m_porters.size(); i++)
        {
            CMPPXMLPorter porter = (CMPPXMLPorter)m_porters.elementAt(i);
            if(!porter.isRunning())
                m_porters.removeElement(porter);
            else
                porter.checkTimeout();
        }

    }

    public void accept()
    {
        if(m_socket == null)
            return;
        try
        {
            Socket socket = m_socket.accept();
            Log.log("CMPPXMLExchanger.accept : client(" + socket.toString() + ") connected !", 0x2000000800000000L);
            int nID = getUniquePorterID();
            CMPPXMLPorter porter = new CMPPXMLPorter(nID, socket, this);
            porter.startup();
            Engine.wait(porter);
            m_porters.addElement(porter);
        }
        catch(Exception e) { }
    }

    public void checkTimeout()
    {
        m_input.checkTimeout();
        m_output.checkTimeout();
    }

    public void run()
    {
        try
        {
            Log.log("CMPPXMLExchanger.run : xml exchanger startup !", 0x800000000L);
            m_nStatus = 1;
            while(isRunning()) 
            {
                checkTimeout();
                checkAllPorters();
                if(m_socket != null)
                    accept();
                else
                    sleep();
            }
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000800000000L);
            Log.log(e);
            Log.log("CMPPXMLExchanger.run : unexpected exit !", 0x2000000800000000L);
        }
        shutdownAllPorters();
        close();
        empty();
        m_nStatus = 3;
        Log.log("CMPPXMLExchanger.run : thread stopped !", 0x800000000L);
    }


}
