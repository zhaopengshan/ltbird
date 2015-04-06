package LeadTone.Port;

import LeadTone.Center.CMPPCenter;
import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.TimeConfig;
import LeadTone.Utility;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 用于系统与外部接口的扩展
 * 此接口为系统与外部除了数据库接口外的另一基于XML的接口
 * 用于基于XML格式的数据交换
 * 整个Port包下的类实现此接口功能
 */
public class CMPPXMLWriter extends Engine
{
    int m_nID;
    CMPPPacketQueue m_queue;
    long m_lTimeout;
    long m_lLastPacket;
    public PrintStream m_ps;
    public int m_nCount;

    public CMPPXMLWriter(int nID, Socket socket, CMPPPacketQueue queue)
        throws IOException
    {
        super("CMPPXMLWriter(" + nID + ")");
        m_queue = null;
        m_lTimeout = TimeConfig.DEFAULT_PACKET_TIMEOUT;
        m_lLastPacket = System.currentTimeMillis();
        m_ps = null;
        m_nCount = 0;
        m_nID = nID;
        m_ps = new PrintStream(socket.getOutputStream());
        m_queue = queue;
    }

    public void empty()
    {
        m_queue = null;
        m_ps = null;
    }

    public void close()
    {
        try
        {
            m_ps.close();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPXMLWriter(" + m_nID + ").close : unexpected exit !", 0x2000000200000000L);
        }
    }

    public boolean isTimeout()
    {
        return m_lTimeout > 0L && System.currentTimeMillis() - m_lLastPacket > m_lTimeout;
    }

    public String getXML(CMPPActiveTestResponse response)
    {
        String strXML = "<cmpp_activetest_response sequence_id=\"" + response.sequence_id + "\">";
        strXML = strXML + CMPPCenter.getGwXMLStr();
        strXML = strXML + "</cmpp_activetest_response>";
        System.out.println(strXML);
        return new String(strXML);
    }

    public String getXML(CMPPDeliver deliver)
    {
        String strXML = "<cmpp_deliver>";
        if(deliver.destination_id != null && deliver.destination_id.length() > 0)
            strXML = strXML + "<destination_id>" + deliver.destination_id + "</destination_id>";
        if(deliver.service_id != null && deliver.service_id.length() > 0)
            strXML = strXML + "<service_id>" + deliver.service_id + "</service_id>";
        strXML = strXML + "<msg_fmt>" + deliver.msg_fmt + "</msg_fmt>";
        if(deliver.src_terminal_id != null && deliver.src_terminal_id.length() > 0)
            strXML = strXML + "<src_terminal_id>" + deliver.src_terminal_id + "</src_terminal_id>";
        if(deliver.registered_delivery == 0)
        {
            if(deliver.msg_content != null && deliver.msg_content.length > 0)
                strXML = strXML + "<msg_content>" + Utility.toHexString(deliver.msg_content) + "</msg_content>";
        } else
        if(deliver.registered_delivery == 1)
        {
            strXML = strXML + "<msg_mr>" + deliver.status_report.msg_id + "</msg_mr>";
            if(deliver.status_report.status != null && deliver.status_report.status.length() > 0)
                strXML = strXML + "<status>" + deliver.status_report.status + "</status>";
            if(deliver.status_report.submit_time != null && deliver.status_report.submit_time.length() > 0)
                strXML = strXML + "<submit_time>" + deliver.status_report.submit_time + "</submit_time>";
            if(deliver.status_report.done_time != null && deliver.status_report.done_time.length() > 0)
                strXML = strXML + "<done_time>" + deliver.status_report.done_time + "</done_time>";
            if(deliver.status_report.dest_terminal_id != null && deliver.status_report.dest_terminal_id.length() > 0)
                strXML = strXML + "<dest_terminal_id>" + deliver.status_report.dest_terminal_id + "</dest_terminal_id>";
        } else
        {
            strXML = strXML + "<msg_length>" + deliver.msg_length + "</msg_length>";
            strXML = strXML + "<msg_content>" + Utility.toHexString(deliver.msg_content) + "</msg_content>";
        }
        if(deliver.gateway_name != null && deliver.gateway_name.length() > 0)
            strXML = strXML + "<gateway>" + deliver.gateway_name + "</gateway>";
        strXML = strXML + "</cmpp_deliver>";
        return new String(strXML);
    }

    public void handleActiveTestResponse(CMPPActiveTestResponse response)
    {
        String strXML = getXML(response);
        m_ps.print(strXML + "\r\n");
        m_ps.flush();
        Log.log("CMPPXMLWriter(" + m_nID + ").write : " + strXML, 0x200000000L);
        m_nCount++;
        m_lLastPacket = System.currentTimeMillis();
    }

    public void handleDeliver(CMPPDeliver deliver)
    {
        if(deliver.registered_delivery == 0)
        {
            String strXML = getXML(deliver);
            m_ps.print(strXML + "\r\n");
            m_ps.flush();
            Log.log("CMPPXMLWriter(" + m_nID + ").write : " + strXML, 0x200000000L);
            m_nCount++;
            m_lLastPacket = System.currentTimeMillis();
        }
    }

    public void run()
    {
        try
        {
            Log.log("CMPPXMLWriter(" + m_nID + ").run : thread startup !", 0x200000000L);
            m_nStatus = 1;
            while(isRunning()) 
            {
                CMPPPacket packet = (CMPPPacket)m_queue.pop();
                if(packet == null)
                {
                    nap();
                } else
                {
                    if(packet.command_id == 5)
                        handleDeliver((CMPPDeliver)packet);
                    else
                    if(packet.command_id == 0x80000008)
                        handleActiveTestResponse((CMPPActiveTestResponse)packet);
                    packet.empty();
                    packet = null;
                    nap();
                }
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPXMLWriter(" + m_nID + ").run : unexpected exit !", 0x2000000200000000L);
        }
        close();
        m_nStatus = 3;
        Log.log("CMPPXMLWriter(" + m_nID + ").run : thread stopped !", 0x200000000L);
        empty();
    }


}
