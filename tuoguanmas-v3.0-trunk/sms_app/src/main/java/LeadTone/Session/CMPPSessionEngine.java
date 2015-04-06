package LeadTone.Session;

import LeadTone.BufferException;
import LeadTone.Gateway.CMPPGatewayEngine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.Packet;
import java.net.Socket;


/**
 * CMPP中国移动的短消息协议实现
 */
public class CMPPSessionEngine extends SessionEngine
{
    /**
     * 消息队列的映射
     */
    CMPPPacketQueue m_queue;
    /**
     * 输入消息包对象
     */
    CMPPPacket cmpp_input;
    /**
     * 输出消息包对象
     */
    CMPPPacket cmpp_output;

    /**
     * 构造方法初始化类变量，参考父类方法
     * @param sc
     * @param socket
     * @param gateway
     */
    public CMPPSessionEngine(SessionConfig sc, Socket socket, CMPPGatewayEngine gateway)
    {
        super(sc, socket, gateway);
        m_queue = new CMPPPacketQueue();
        cmpp_input = null;
        cmpp_output = null;
    }

    /**
     * 构造方法初始化类变量，参考父类方法
     * @param sc
     * @param gateway
     */
    public CMPPSessionEngine(SessionConfig sc, CMPPGatewayEngine gateway)
    {
        super(sc, gateway);
        m_queue = new CMPPPacketQueue();
        cmpp_input = null;
        cmpp_output = null;
    }

    /**
     * 继承自父类方法，清除使用的资源
     */
    public void empty()
    {
        m_queue.empty();
        m_queue = null;
        super.empty();
    }

    /**
     * 继承自父类方法，完成发送消息从网关管理线程到输出管理线程InputEngine的过程
     * @return 返回是否放入成功
     * @throws BufferException
     */
    public boolean put()
        throws BufferException
    {
        if(cmpp_output == null)
        {
            //从网关对象中获取待发送消息包
            cmpp_output = (CMPPPacket)m_gateway.get(m_sc.m_nType, m_nID);
            if(cmpp_output == null)
                return false;
            if(CMPPCommandID.isRequest(cmpp_output.command_id))
            {
                cmpp_output.session_id = m_nID;
                cmpp_output.sequence_id = getSequenceID();
                cmpp_output.generate_time = System.currentTimeMillis();
                m_queue.push(cmpp_output);
            }
            if(CMPPCommandID.isMessage(cmpp_output.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            Log.log("CMPPSessionEngine[" + BindType.toString(m_sc.m_nType) + "].sendPakcet", 0x200800000000000L);
            cmpp_output.wrapCMPPPacket();
        }
        if(m_output.put(cmpp_output))
        {
            cmpp_output = null;
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 继承自父类方法，完成接收消息从输入管理线程InputEngine到网关管理线程的过程
     * @return 返回是否取出成功
     * @throws BufferException
     */
    public boolean get()
        throws BufferException
    {
        if(cmpp_input == null)
        {
            //从输入线程InputEngine管理的消息队列中读取消息包
            Packet packet = m_input.get();
            if(packet == null)
                return false;
            cmpp_input = new CMPPPacket(packet);
            Log.log("CMPPSessionEngine[" + BindType.toString(m_sc.m_nType) + "].recievePakcet", 0x200800000000000L);
            cmpp_input.unwrapCMPPPacket();
            //读取到输入流之后，如果为回复消息则到消息队列中寻找与之对应的请求消息，通过序列号建立对应关系，并更新其guid主键
            if(CMPPCommandID.isResponse(cmpp_input.command_id) && !m_queue.recover(cmpp_input))
                cmpp_input.guid = 0L;//如果找不到对应的请求消息，则更新主键为零
            if(CMPPCommandID.isMessage(cmpp_input.command_id))
            {
                delayMessageTimeout();
                delayActiveTest();
            }
            packet.empty();
        }
        //把获取的消息包放入送入网关管理线程
        if(m_gateway.put(m_sc.m_nType, cmpp_input))
        {
            cmpp_input = null;
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 检查消息队列的状态，当消息队列的容量超过限制的最大流量时关闭线程，
     * 检验队列中超时消息的个数，并累计到会话总错误数中
     */
    public void checkPacketQueue()
    {
        m_nQueueSize = m_queue.getSize();
        if(m_sc.m_nQueueSize > 0 && m_nQueueSize > m_sc.m_nQueueSize)
            m_bNeedTerminate = true;
        m_nErrorCount += m_queue.checkTimeout();
    }

    /**
     * 可参考自父类方法 ，从输入数据流中读取CMPPP消息包
     * @return 返回从输入流中读取的CMPP消息包
     */
    public CMPPPacket readCMPPPacket()
    {
        try
        {
            CMPPPacket packet = new CMPPPacket();
            //标识消息包隶属的网关属性
            packet.gateway_name = m_gateway.m_strName;
            //处理此消息包的会话编号
            packet.session_id = m_nID;
            //从流中读取消息包
            packet.inputPacket(m_dis);
            //解析消息包
            packet.unwrapCMPPPacket();
            return packet;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPSessionEngine.readPacket : unexpected exit !", 0x2000040000000000L);
        return null;
    }

    /**
     * 可参考自父类方法，向输出数据流中写入消息包
     * @param packet 待写入的消息包
     * @return 返回是否写入成功
     */
    public boolean writeCMPPPacket(CMPPPacket packet)
    {
        try
        {
            //组织消息包，插入消息头
            packet.wrapCMPPPacket();
            //将消息包写入输出数据流
            packet.outputPacket(m_dos);
            return true;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPSessionEngine.writePacket : unexpected exit !", 0x2000040000000000L);
        return false;
    }

    /**
     * 将消息包成功写入输出数据流后即从输入流读取消息包并返回，具体实现与协议相关，请参考相应继承类
     * 如写入失败则返回NULL
     * @param packet 待写入的消息包
     * @return 从输入数据流中读取的消息包
     */
    public CMPPPacket request(CMPPPacket packet)
    {
        if(writeCMPPPacket(packet))
            return readCMPPPacket();
        else
            return null;
    }


}
