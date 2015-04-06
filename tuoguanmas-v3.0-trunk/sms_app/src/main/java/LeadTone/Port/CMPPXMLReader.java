package LeadTone.Port;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.TimeConfig;
import LeadTone.Utility;
import LeadTone.XML.XMLParser;
import LeadTone.XML.XMLTag;
import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;

/**
 * 用于系统与外部接口的扩展
 * 此接口为系统与外部除了数据库接口外的另一基于XML的接口
 * 用于基于XML格式的数据交换
 * 整个Port包下的类实现此接口功能
 */
public class CMPPXMLReader extends Engine
{
    int m_nID;
    CMPPPacketQueue m_queue;
    long m_lTimeout;
    long m_lLastPacket;
    InputStreamReader m_isr;
    public BufferedReader m_br;
    public int m_nCount;

    public CMPPXMLReader()
    {
        super("test");
        m_queue = null;
        m_lTimeout = TimeConfig.DEFAULT_PACKET_TIMEOUT;
        m_lLastPacket = System.currentTimeMillis();
        m_isr = null;
        m_br = null;
        m_nCount = 0;
    }

    public CMPPXMLReader(int nID, Socket socket, CMPPPacketQueue queue)
        throws IOException
    {
        super("CMPPXMLReader(" + nID + ")");
        m_queue = null;
        m_lTimeout = TimeConfig.DEFAULT_PACKET_TIMEOUT;
        m_lLastPacket = System.currentTimeMillis();
        m_isr = null;
        m_br = null;
        m_nCount = 0;
        m_nID = nID;
        m_isr = new InputStreamReader(socket.getInputStream());
        m_br = new BufferedReader(m_isr);
        m_queue = queue;
    }

    public void empty()
    {
        m_queue = null;
        m_br = null;
        m_isr = null;
    }

    public void close()
    {
        try
        {
            m_br.close();
            m_isr.close();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPXMLReader(" + m_nID + ").close : unexpected exit !", 0x2000000100000000L);
        }
    }

    public boolean isTimeout()
    {
        return m_lTimeout > 0L && System.currentTimeMillis() - m_lLastPacket > m_lTimeout;
    }

    public CMPPTerminate getTerminate(XMLTag tag)
    {
        CMPPTerminate terminate = new CMPPTerminate(0);
        terminate.sequence_id = XMLParser.getInteger(tag, ".sequence_id", 1);
        terminate.guid = -(long)m_nID;
        return terminate;
    }

    public CMPPActiveTest getActiveTest(XMLTag tag)
    {
        CMPPActiveTest activetest = new CMPPActiveTest(0);
        activetest.gateway_name = XMLParser.getString(tag, "\\gateway", null);
        activetest.sequence_id = XMLParser.getInteger(tag, ".sequence_id", 1);
        activetest.guid = -(long)m_nID;
        return activetest;
    }

    public CMPPSubmit getSubmit(XMLTag tag)
    {
        CMPPSubmit submit = new CMPPSubmit(0);
        submit.gateway_name = XMLParser.getString(tag, "\\gateway", null);
        submit.sequence_id = XMLParser.getInteger(tag, ".sequence_id", 1);
        submit.guid = -(long)m_nID;
        submit.registered_delivery = XMLParser.getByte(tag, "\\registered_delivery", (byte)0);
        submit.msg_level = XMLParser.getByte(tag, "\\msg_level", (byte)0);
        submit.service_id = XMLParser.getString(tag, "\\service_id", null);
        submit.fee_user_type = XMLParser.getByte(tag, "\\fee_user_type", (byte)0);
        submit.fee_terminal_id = XMLParser.getString(tag, "\\fee_terminal_id", null);
        submit.tp_pid = XMLParser.getByte(tag, "\\tp_pid", (byte)0);
        submit.tp_udhi = XMLParser.getByte(tag, "\\tp_udhi", (byte)0);
        submit.msg_fmt = 15;
        submit.msg_src = XMLParser.getString(tag, "\\msg_src", null);
        submit.fee_type = XMLParser.getString(tag, "\\fee_type", null);
        submit.fee_code = XMLParser.getString(tag, "\\fee_code", null);
        submit.valid_time = XMLParser.getString(tag, "\\valid_time", null);
        submit.at_time = XMLParser.getString(tag, "\\at_time", null);
        submit.src_terminal_id = XMLParser.getString(tag, "\\src_terminal_id", null);
        submit.dest_terminal_id = Utility.parseTerminalID(XMLParser.getString(tag, "\\dest_terminal_id", null));
        submit.dest_usr_tl = (byte)((submit.dest_terminal_id != null ? submit.dest_terminal_id.length : 0) & 0xff);
        String noticeStr = "短信平台" + submit.gateway_name + "网关测试(" + submit.src_terminal_id + ") " + (new Timestamp(System.currentTimeMillis())).toString(); //UTF-8表示 \u77ED\u4FE1\u5E73\u53F0    \u7F51\u5173\u6D4B\u8BD5
        submit.msg_content = Utility.toBytesValue(noticeStr);
        submit.msg_length = submit.msg_content != null ? submit.msg_content.length : 0;
        return submit;
    }

    public void run()
    {
        try
        {
            Log.log("CMPPXMLReader(" + m_nID + ").run : thread startup !", 0x100000000L);
            m_nStatus = 1;
            while(isRunning()) 
            {
                if(!m_br.ready())
                {
                    nap();
                    continue;
                }
                String strLine = m_br.readLine();
                if(strLine == null || strLine.length() <= 0)
                    continue;
                Log.log("CMPPXMLReader(" + m_nID + ").read : " + strLine, 0x100000000L);
                XMLTag tag = XMLParser.parse(strLine);
                if(tag == null || tag.m_strName == null)
                    break;
                LeadTone.Packet.CMPPPacket.CMPPPacket packet = null;
                if(tag.m_strName.equals("cmpp_submit"))
                    packet = getSubmit(tag);
                else
                if(tag.m_strName.equals("cmpp_activetest"))
                    packet = getActiveTest(tag);
                else
                if(tag.m_strName.equals("cmpp_terminate"))
                    packet = getTerminate(tag);
                if(packet == null)
                {
                    tag.empty();
                    break;
                }
                for(; !m_queue.push(packet); nap());
                m_nCount++;
                m_lLastPacket = System.currentTimeMillis();
                tag.empty();
                tag = null;
                nap();
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPXMLReader(" + m_nID + ").run : unexpected exit !", 0x2000000100000000L);
        }
        close();
        m_nStatus = 3;
        Log.log("CMPPXMLReader.run(" + m_nID + ") : thread stopped !", 0x100000000L);
        empty();
    }

    public static void main(String args[])
    {
        CMPPXMLReader xmlr = new CMPPXMLReader();
        String str = "<cmpp_submit sequnce_id=\"333\"><gateway>shcmcc</gateway><service_id>ajwl</service_id><src_terminal_id>01898</src_terminal_id><dest_terminal_id>13918938504</dest_terminal_id><msg_fmt>0</msg_fmt><msg_content>31323334</msg_content></cmpp_submit>";
        LeadTone.Packet.CMPPPacket.CMPPPacket packet = null;
        XMLTag tag = null;
        try
        {
            tag = XMLParser.parse(str);
            System.out.println(str);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(tag != null)
            packet = xmlr.getSubmit(tag);
    }


}
