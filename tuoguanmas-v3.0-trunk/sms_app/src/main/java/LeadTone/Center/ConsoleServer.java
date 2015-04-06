package LeadTone.Center;

import LeadTone.Engine;
import LeadTone.Log;
import java.net.ServerSocket;

public class ConsoleServer extends Engine
{

    Center center;

    public ConsoleServer(Center center)
    {
        super("ConsoleServer");
        this.center = center;
    }

    public void run()
    {
        m_nStatus = 1;
        Log.log("Console server is starting ...", 1L);
        for(; isRunning(); sleep())
            if(center.m_socket != null && !center.m_socket.isClosed())
                center.accept();

        Log.log("Console server is shuting ...", 1L);
        m_nStatus = 3;
    }
}
