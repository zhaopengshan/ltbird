package LeadTone.Gateway;

import LeadTone.Engine;
import LeadTone.Log;

/**
 * �����ü����������ڼ������Ӷ˿�
 */
public class GatewayListener extends Engine
{
    /**
     * ���������������ض���
     */
     GatewayEngine m_gateway;

    /**
     * ���췽����ʼ�������
     * @param gateway
     */
    public GatewayListener(GatewayEngine gateway)
    {
        super("GatewayListener");
        m_gateway = null;
        m_gateway = gateway;
    }

    /**
     * �������̣߳�������������Ƿ����������������Ӷ˿ڵȴ���������
     */
    public void run()
    {
        try
        {
            Log.log("GatewayListener(" + m_gateway.m_strName + ").run : thread startup !", 0x80000000000L);
            m_nStatus = 1;

            for(; isRunning() && m_gateway.isBeginToRunning(); Engine.nap())
                if(m_gateway.m_socket != null)
                    m_gateway.accept();

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("GatewayListener.run(" + m_gateway.m_strName + ") : unexpected exit !", 0x2000080000000000L);
        }
        m_nStatus = 3;
        Log.log("GatewayListener(" + m_gateway.m_strName + ").run : thread stopped !", 0x80000000000L);
    }


}
