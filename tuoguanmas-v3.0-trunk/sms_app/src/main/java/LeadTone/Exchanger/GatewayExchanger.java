package LeadTone.Exchanger;

import LeadTone.Engine;
import LeadTone.Gateway.GatewayEngine;

/**
 * ʵ�����ؼ䲻ͬЭ���ʽת���Ĺ���
 */
public class GatewayExchanger extends Engine
{
    /**
     * ���m_nID����̺߳�Ψһ�Ա�ʶ
     */
    static int m_nUniqueID = 0;
    /**
     * ��Ϣת���̵߳��̺߳ţ����ڶ��߳�ʱ
     */
    int m_nID;
    /**
     * Դ���ض���
     */
    GatewayEngine m_source;
    /**
     * Ŀ�����ض���
     */
    GatewayEngine m_destination;

    /**
     * ���췽����ʼ�������
     * @param source Դ���ض���
     * @param destination Ŀ�����ض���
     */
    public GatewayExchanger(GatewayEngine source, GatewayEngine destination)
    {
        super("GatewayExchanger");
        m_source = null;
        m_destination = null;
        m_nID = ++m_nUniqueID;
        m_source = source;
        m_destination = destination;
    }



}
