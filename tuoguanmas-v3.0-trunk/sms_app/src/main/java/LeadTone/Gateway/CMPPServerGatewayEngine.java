package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.Packet;
import LeadTone.Session.*;



/**
 * CMPP网关服务器模式线程类，继承自CMPPGatewayEngine
 */
public class CMPPServerGatewayEngine extends CMPPGatewayEngine
{
    /**
     * 构造方法初始化类变量
     * @param strName
     * @param nType
     * @param sp
     */
    public CMPPServerGatewayEngine(String strName, int nType, ServiceProvider sp)
    {
        super(strName, nType, sp);
    }

    /**
     * 从输出队列提取消息包，如果会话类型为发送并且消息包属于发送类的消息，或者会话类型为接收并且消息包属于接收类的消息，
     * 则将消息从输出队列取出，并从消息队列中移除
     * @param nType 会话类型
     * @param nID 会话线程编号
     * @return 返回提取到的消息包
     */
    public Packet get(int nType, int nID)
    {
        if(m_output == null)
            return null;
        CMPPPacket cmpp = (CMPPPacket)m_output.peer();
        if(cmpp == null)
            return null;
        if(cmpp.session_id > 0 && cmpp.session_id != nID)
            return null;
        if(BindType.forTransmitter(nType) && CMPPCommandID.isTransmitterInput(cmpp.command_id) || BindType.forReceiver(nType) && CMPPCommandID.isReceiverInput(cmpp.command_id))
        {
            m_output.pop();
            return cmpp;
        } else
        {
            return null;
        }
    }

    /**
     * 输入会话线程从端口捕获消息包后，交给网关做过滤，
     * 如果为连接保持断开连接等类消息就直接处理，如果为上下行类消息则放入输入消息队列
     * @param nType 会话类型
     * @param packet 待处理的消息包
     * @return  返回是否操作成功的布尔值
     */
    public boolean put(int nType, Packet packet)
    {
        CMPPPacket cmpp = (CMPPPacket)packet;
        if(cmpp == null)
            return true;
        if(handleActiveTest(cmpp) || handleActiveTestResponse(cmpp) || handleTerminate(cmpp))
        {
            cmpp.empty();
            cmpp = null;
            return true;
        }
        if(BindType.forTransmitter(nType) && CMPPCommandID.isTransmitterOutput(cmpp.command_id) || BindType.forReceiver(nType) && CMPPCommandID.isReceiverOutput(cmpp.command_id))
            return m_input.push(cmpp);
        else
            return true;
    }

    /**
     * 接收建立会话连接操作消息包，发送建立连接回复消息包
     * @param session
     * @return 返回是否建立会话连接成功
     */
    public boolean login(SessionEngine session)
    {
        try
        {
            CMPPConnect connect_packet = m_sp.getCMPPConnect(m_nType, session.m_sc.m_nType);
            if(connect_packet == null)
            {
                session.m_bAuthenticated = true;
                return true;
            }
            CMPPPacket packet = ((CMPPSessionEngine)session).readCMPPPacket();
            if(packet == null || packet.command_id != 1)
                return false;
            CMPPConnect connect = new CMPPConnect(packet);
            connect.unwrap();
            CMPPConnectResponse response = new CMPPConnectResponse(connect.sequence_id);
            response.gateway_name = connect.gateway_name;
            response.session_id = connect.session_id;
            response.status = 0;
            response.authenticator_ismg = new byte[16];
            if(connect.source_addr != null && connect_packet.source_addr != null && !connect.source_addr.equals(connect_packet.source_addr))
            {
                response.status = 2;
            } else
            {
                connect_packet = m_sp.getCMPPConnect(m_nType, session.m_sc.m_nType, connect.time_stamp);
                if(connect_packet == null)
                {
                    response.status = 3;
                } else
                {
                    for(int i = 0; i < 16; i++)
                    {
                        if(connect_packet.authenticator_sp[i] == connect.authenticator_sp[i])
                            continue;
                        response.status = 3;
                        break;
                    }

                }
            }
            response.wrap();
            ((CMPPSessionEngine)session).writeCMPPPacket(response);
            if(response.status != 0)
            {
                session.m_bAuthenticated = false;
                return false;
            } else
            {
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CMPPServerGatewayEngine(" + m_strName + ").login : unexpected exit !", 0x2000080000000000L);
        session.m_bAuthenticated = false;
        return false;
    }
}
