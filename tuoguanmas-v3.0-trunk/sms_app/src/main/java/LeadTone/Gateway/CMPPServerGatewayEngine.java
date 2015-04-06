package LeadTone.Gateway;

import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.Packet;
import LeadTone.Session.*;



/**
 * CMPP���ط�����ģʽ�߳��࣬�̳���CMPPGatewayEngine
 */
public class CMPPServerGatewayEngine extends CMPPGatewayEngine
{
    /**
     * ���췽����ʼ�������
     * @param strName
     * @param nType
     * @param sp
     */
    public CMPPServerGatewayEngine(String strName, int nType, ServiceProvider sp)
    {
        super(strName, nType, sp);
    }

    /**
     * �����������ȡ��Ϣ��������Ự����Ϊ���Ͳ�����Ϣ�����ڷ��������Ϣ�����߻Ự����Ϊ���ղ�����Ϣ�����ڽ��������Ϣ��
     * ����Ϣ���������ȡ����������Ϣ�������Ƴ�
     * @param nType �Ự����
     * @param nID �Ự�̱߳��
     * @return ������ȡ������Ϣ��
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
     * ����Ự�̴߳Ӷ˿ڲ�����Ϣ���󣬽������������ˣ�
     * ���Ϊ���ӱ��ֶϿ����ӵ�����Ϣ��ֱ�Ӵ������Ϊ����������Ϣ�����������Ϣ����
     * @param nType �Ự����
     * @param packet ���������Ϣ��
     * @return  �����Ƿ�����ɹ��Ĳ���ֵ
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
     * ���ս����Ự���Ӳ�����Ϣ�������ͽ������ӻظ���Ϣ��
     * @param session
     * @return �����Ƿ����Ự���ӳɹ�
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
