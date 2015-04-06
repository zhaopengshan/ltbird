package LeadTone.Gateway;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.Packet;
import LeadTone.Session.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


/**
 * CMPP�����߳��࣬�̳���GatewayEngine�����CMPP��Ϣ�Ľ����·���
 * ���ӻỰ���������������Ϣ���У�
 * ������������Ϣ�����������ӱ��ֵȸ�������Ϣ����
 * ���ڷ�����ģʽʱ��������˿�
 */
public class CMPPGatewayEngine extends GatewayEngine
{
    /**
     * SP��Ϣ
     */
    public ServiceProvider m_sp;
    /**
     * �����Ϣ����
     */
    public CMPPPacketQueue m_output;
    /**
     * ������Ϣ����
     */
    public CMPPPacketQueue m_input;

    /**
     * ���췽����ʼ�������
     * @param strName
     * @param nType
     * @param sp
     */
    public CMPPGatewayEngine(String strName, int nType, ServiceProvider sp)
    {
        super(strName, nType);
        m_sp = null;
        m_output = new CMPPPacketQueue(0);
        m_input = new CMPPPacketQueue(0);
        m_sp = sp;
    }

    /**
     * ������Ϣ�����������
     * @param nCapacity
     */
    public void setCapacity(int nCapacity)
    {
        m_output.setCapacity(nCapacity);
        m_input.setCapacity(nCapacity);
    }

    /**
     * ���ϵͳʹ�õ�������Դ
     */
    public void empty()
    {
        m_sp = null;
        m_output.empty();
        m_output = null;
        m_input.empty();
        m_input = null;
        super.empty();
    }

    /**
     * ���������к�������еĳ�ʱ���
     */
    public void checkAll()
    {
        m_output.checkTimeout();
        m_input.checkTimeout();
    }

    /**
     * ����socket���ӣ������Ự�߳�
     */
    public void accept()
    {
        try
        {
            Socket socket = m_socket.accept();
            Log.log("CMPPGatewayEngine(" + m_strName + ").accept : " + socket.toString(), 0x80000000000L);
            CMPPSessionEngine session = new CMPPSessionEngine(m_sc, socket, this);
            Log.log("CMPPGatewayEngine(" + m_strName + ").accept : begin startup " + session.m_nID + "th session engine !", 0x80000000000L);
            session.startup();
            Engine.wait(session);
            m_sessions.addElement(session);
        }
        catch(Exception e) { }
    }

    /**
     * �ο����෽�������ݲ�ͬ�ĻỰ��������װ�ػỰ�߳�
     * @param strHostAddress
     */
    public boolean loadAllSessions(String strHostAddress)
    {
        return false;
    }

    /**
     * װ�ػỰ�̣߳���������ʶΪ����ʱ��װ��ͬʱ�����Ự�߳�
     * @param sc �Ự�߳�����
     * @param bNeedStartup װ��ͬʱ�Ƿ������ı�ʶ
     * @return  �����Ƿ�װ�سɹ��Ĳ���ֵ
     */
    public boolean loadSession(SessionConfig sc, boolean bNeedStartup)
    {
        try
        {
            //���Ϊ��������Ự�̣߳����ü������˿ڣ���GatewayEngine�̵߳�run��������
            if(sc.m_nType == 0)
            {
                m_sc = sc;
                if(setListen(sc.m_nPort))
                    return true;
            } else
            //���Ϊ�Ƿ�������Ự�߳���װ�غ����Ự�̼߳�����
            if(sc.m_nType == 1 || sc.m_nType == 2 || sc.m_nType == 3)
            {
                CMPPSessionEngine session = new CMPPSessionEngine(sc, this);
                m_sessions.addElement(session);
                //���װ��ͬʱ�����ı�ʶ�������������������߳�
                if(bNeedStartup)
                    session.startup();
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000080000000000L);
            Log.log(e);
            Log.log("CMPPGatewayEngine(" + m_strName + ").loadSession : unexpected exit !", 0x2000080000000000L);
        }
        return false;
    }


    /**
     * �����������ȡ��Ϣ��������Ự����Ϊ���Ͳ�����Ϣ�����ڷ��������Ϣ�����߻Ự����Ϊ���ղ�����Ϣ�����ڽ��������Ϣ��
     * ����Ϣ���������ȡ����������Ϣ�������Ƴ�
     * @param nType �Ự����
     * @param nID �Ự�̱߳��
     * @return ������ȡ������Ϣ��
     */
    public synchronized Packet get(int nType, int nID)
    {
        if(m_output == null)
            return null;
        CMPPPacket cmpp = (CMPPPacket)m_output.peer();
        if(cmpp == null)
            return null;
        if(cmpp.session_id > 0 && cmpp.session_id != nID)
            return null;
        if(BindType.forTransmitter(nType) && CMPPCommandID.isTransmitterOutput(cmpp.command_id) || BindType.forReceiver(nType) && CMPPCommandID.isReceiverOutput(cmpp.command_id))
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
     * ������synchronized���δʣ���֤�̰߳�ȫ
     * @param nType �Ự����
     * @param packet ���������Ϣ��
     * @return  �����Ƿ�����ɹ��Ĳ���ֵ
     */
    public synchronized boolean put(int nType, Packet packet)
    {
        CMPPPacket cmpp = (CMPPPacket)packet;
        if(cmpp == null)
            return true;
        //�����Ϣ��Ϊ����������Ϣ�����򱣳����ӻظ���Ϣ������Ͽ����Ӱ����������Ϣ�����سɹ�
        if(handleActiveTest(cmpp) || handleActiveTestResponse(cmpp) || handleTerminate(cmpp))
        {
            cmpp.empty();
            cmpp = null;
            return true;
        }
        //����Ự����Ϊ���Ͳ�����Ϣ�����ڷ��������Ϣ�����߻Ự����Ϊ���ղ�����Ϣ�����ڽ��������Ϣ��
        //����Ϣ����������Ϣ������
        if((BindType.forTransmitter(nType) && CMPPCommandID.isTransmitterInput(cmpp.command_id) || BindType.forReceiver(nType) && CMPPCommandID.isReceiverInput(cmpp.command_id)) && m_input != null)
            return m_input.push(cmpp);
        else
            return true;
    }


     /**
     * �������ӱ��ֻظ���Ϣ����ֱ����Ựд�������ӻظ���Ϣ
     * ���ƻỰ�����������յ�һ�����ӱ�����Ϣ�ظ���ʱ�Ự����������һ
     * @param packet
     * @return �����Ƿ���ɹ��Ĳ���ֵ
     */
    public boolean handleActiveTest(CMPPPacket packet)
    {
        if(packet.command_id == 8)
        {
            CMPPSessionEngine session = (CMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
                try
                {
                    CMPPActiveTestResponse response = new CMPPActiveTestResponse(packet.sequence_id);
                    response.gateway_name = packet.gateway_name;
                    response.session_id = packet.session_id;
                    response.wrap();
                    //���������ӱ�����Ϣ��ʧ�ܵ�ʱ�򣬻Ự����������һ
                    if(!m_output.push(response))
                        session.m_nErrorCount++;
                    session.delayActiveTest();
                }
                catch(Exception e)
                {
                    Log.log(e);
                    Log.log("CMPPGatewayEngine.handleEnquireLink : unexpected exit !", 0x2000040000000000L);
                }
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * �������ӱ��ֻظ���Ϣ��
     * ���ƻỰ�����������յ�һ�����ӱ�����Ϣ�ظ���ʱ�Ự����������һ
     * @param packet
     * @return �����Ƿ���ɹ��Ĳ���ֵ
     */
    public boolean handleActiveTestResponse(CMPPPacket packet)
    {
        if(packet.command_id == 0x80000008)
        {
            CMPPSessionEngine session = (CMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
            {
                //���ڿ��ƻỰ�����������յ�һ�����ӱ�����Ϣ�ظ���ʱ��������һ
                session.m_nErrorCount--;
                session.delayActiveTest();
                if(session.m_nErrorCount < 0)
                    session.m_nErrorCount = 0;
            }
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * ����Ͽ�������Ϣ��ֱ����Ựд�ضϿ����ӻظ���Ϣ
     * @param packet
     * @return �����Ƿ���ɹ��Ĳ���ֵ
     */
    public boolean handleTerminate(CMPPPacket packet)
    {
        if(packet.command_id == 2)
        {
            CMPPSessionEngine session = (CMPPSessionEngine)getSession(packet.session_id);
            if(session != null)
            {
                try
                {
                    //����Ͽ�������Ϣ��ֱ��������д�ضϿ����ӻظ���Ϣ
                    CMPPTerminateResponse response = new CMPPTerminateResponse(packet.sequence_id);
                    session.writeCMPPPacket(response);
                    session.m_bNeedTerminate = true;
                    return true;
                }
                catch(Exception e)
                {
                    Log.log(e);
                }
                Log.log("CMPPGatewayEngine.handleUnbind : unexpected exit !", 0x2000040000000000L);
            }
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * �������Ӳ���
     * @param session
     */
    public void enquireLink(SessionEngine session)
    {
        try
        {
            CMPPActiveTest request = new CMPPActiveTest(0x7fffffff);
            request.gateway_name = m_strName;
            request.session_id = session.m_nID;
            m_output.push(request);
            session.m_nErrorCount++;
            session.delayActiveTest();
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPGatewayEngine.enquireLink : unexpected exit !", 0x2000080000000000L);
        }
    }

    /**
     * �������Ự���Ӳ���
     * @param session
     * @return �����Ƿ����Ự���ӳɹ�
     */
    public boolean login(SessionEngine session)
    {
        try
        {
            //��֯�������Ӱ�
            CMPPConnect connect = m_sp.getCMPPConnect(m_nType, session.m_sc.m_nType);
            //����޷����ɽ������Ӱ�Ϊ�գ�����Ϊ�����Ȩ����
            if(connect == null)
            {
                session.m_bAuthenticated = true;
                return true;
            }
            connect.gateway_name = m_strName;
            connect.session_id = session.m_nID;
            connect.sequence_id = session.getSequenceID();
            connect.wrap();
            //д�뽨�����Ӱ����õ��������ӻظ���
            CMPPPacket packet = ((CMPPSessionEngine)session).request(connect);
            if(packet == null)
                return false;
            CMPPConnectResponse response = new CMPPConnectResponse(packet);
            response.unwrap();
            if(response.status == 0)
            {   //���ûỰ��Ȩ״̬Ϊ����Ȩ
                session.m_bAuthenticated = true;
                return true;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPGatewayEngine(" + m_strName + ").login : unexpected exit !", 0x2000080000000000L);
        }
        session.m_bAuthenticated = false;
        return false;
    }

    /**
     * ����Ͽ��Ự���Ӳ���
     * @param session
     */
    public void logout(SessionEngine session)
    {
        try
        {
            CMPPTerminate terminate = new CMPPTerminate(session.getSequenceID());
            terminate.gateway_name = m_strName;
            terminate.session_id = session.m_nID;
            //ֱ��������д��Ͽ����Ӱ�
            CMPPPacket packet = ((CMPPSessionEngine)session).request(terminate);
            if(packet == null)
                return;
            CMPPTerminateResponse response = new CMPPTerminateResponse(packet);
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPGatewayEngine(" + m_strName + ").logout : unexpected exit !", 0x2000080000000000L);
        }
        session.m_bAuthenticated = false;
    }

    /**
     * ������Ϣ������������Ϣ��������ȡ��Ϣ��
     * @return ��ȡ����Ϣ��
     */
    public Packet receive()
    {
        return m_input.pop();
    }

    /**
     * ������Ϣ��������Ϣ�����������Ϣ����
     * @param packet
     * @return �����Ƿ��ͳɹ�
     */
    public boolean send(Packet packet)
    {
        return m_output.push(packet);
    }


}
