package LeadTone.Session;

import LeadTone.Packet.CMPPPacket.CMPPQueryResponse;
import LeadTone.TimeConfig;
import java.net.InetAddress;


/**
 * �Ự���ͼ��������Լ��ṩ���������ѯ��ȡϵͳ�������Բ����ķ���
 */
public class SessionConfig extends BindType
{
    /**
     * ��Ϣ���е�Ĭ���������Ĭ��ֵ
     */
    public static final int DEFAULT_PACKET_QUEUE_SIZE = 128;
    /**
     * �Ự����������Ĭ��ֵ
     */
    public static final int DEFAULT_MAX_ERROR_COUNT = 3;
    /**
     * �Ự������ֵĬ��ֵ
     */
    public static final int DEFAULT_FLUX_CONTROL = 65535;
    /**
     * �Ự����������
     */
    public int m_nCount;
    /**
     * �����Ự���ӵ�IP��ַ
     */
    public InetAddress m_address;
    /**
     * �����Ự���ӵĶ˿ں�
     */
    public int m_nPort;
    /**
     * �Ự���ͣ��ο�����BindType
     */
    public int m_nType;
    /**
     * ��Ϣ����ʱʱ��
     */
    public long m_lPacketTimeout;
    /**
     * �·����ų�ʱʱ��
     */
    public long m_lMessageTimeout;
    /**
     * ����������Ϣ��ʱʱ��
     */
    public long m_lActiveTestTimeout;
    /**
     * �Ự�������������
     */
    public int m_nMaxErrorCount;
    /**
     * ��Ϣ���е�����
     */
    public int m_nQueueSize;
    /**
     * �Ự�����������ֵ
     */
    public int m_nMaxFlux;
    /**
     * �Ự�������ķ�ֵ
     */
    public int m_nPeakFlux;
    /**
     * �Ự�������ĵ�ǰֵ
     */
    public int m_nCurrFlux;

    /**
     * ���췽����ʼ�������
     */
    public SessionConfig()
    {
        m_nCount = 1;
        m_address = null;
        m_nPort = 7890;
        m_nType = 1;
        m_lPacketTimeout = TimeConfig.DEFAULT_PACKET_TIMEOUT;
        m_lMessageTimeout = TimeConfig.DEFAULT_MESSAGE_TIMEOUT;
        m_lActiveTestTimeout = TimeConfig.DEFAULT_ACTIVETEST_TIMEOUT;
        m_nMaxErrorCount = 3;
        m_nQueueSize = 128;
        m_nMaxFlux = 65535;
        m_nPeakFlux = 0;
        m_nCurrFlux = 0;
    }

    /**
     * ���췽����ʼ�������
     * @param nType �Ự���ʹ���
     * @param address �����Ự���ӵ�IP��ַ
     * @param nPort �����Ự���ӵĶ˿ں�
     */
    public SessionConfig(int nType, InetAddress address, int nPort)
    {
        m_nCount = 1;
        m_address = null;
        m_nPort = 7890;
        m_nType = 1;
        m_lPacketTimeout = TimeConfig.DEFAULT_PACKET_TIMEOUT;
        m_lMessageTimeout = TimeConfig.DEFAULT_MESSAGE_TIMEOUT;
        m_lActiveTestTimeout = TimeConfig.DEFAULT_ACTIVETEST_TIMEOUT;
        m_nMaxErrorCount = 3;
        m_nQueueSize = 128;
        m_nMaxFlux = 65535;
        m_nPeakFlux = 0;
        m_nCurrFlux = 0;
        m_nType = nType;
        m_address = address;
        m_nPort = nPort;
    }

    /**
     * �ο�����ͬ������
     */
    public boolean isTransmitter()
    {
        return (m_nType & 3) == 1;
    }

    /**
     * �ο�����ͬ������
     */
    public boolean isReceiver()
    {
        return (m_nType & 3) == 2;
    }

    /**
     * �ο�����ͬ������
     */
    public boolean isTransceiver()
    {
        return (m_nType & 3) == 3;
    }

    /**
     * �ο�����ͬ������
     */
    public boolean isActivater()
    {
        return (m_nType & 3) == 0;
    }

    /**
     * ��ϵͳ��֧�ֵ��������ð�װΪ��ѯ�ظ�(QueryResponse)
     * ����ϵͳ�䵱������ʱ��ͻ��˷��ز�ѯ���
     * @param response  ��ѯ����ظ�
     */
    public void getConfig(CMPPQueryResponse response)
    {
        if(m_nType == 3)
            getTransceiverConfig(response);
        else
        if(m_nType == 1)
            getTransmitterConfig(response);
        else
        if(m_nType == 2)
            getReceiverConfig(response);
        else
        if(m_nType == 0)
            getActivaterConfig(response);
    }

    /**
     * ���ݲ�ѯ����ظ�����ϵͳ��������
     * ����ϵͳ�䵱�ͻ���ʱ���ݲ�ѯ��������Լ��Ĺ�������
     * @param response  ��ѯ����ظ�
     */
    public void setConfig(CMPPQueryResponse response)
    {
        if(m_nType == 3)
            setTransceiverConfig(response);
        else
        if(m_nType == 1)
            setTransmitterConfig(response);
        else
        if(m_nType == 2)
            setReceiverConfig(response);
        else
        if(m_nType == 0)
            setActivaterConfig(response);
    }

    /**
     * �ο�getConfig(CMPPQueryResponse response)����
     * @param response
     */
    public void getTransceiverConfig(CMPPQueryResponse response)
    {
        response.MT_TLUsr = 3;
        response.MT_TLMsg = 3;
        response.MT_Scs = m_nPort;
        response.MT_WT = m_nCount;
        response.MT_FL = m_nMaxFlux;
        response.MO_Scs = m_nPort;
        response.MO_WT = m_nCount;
        response.MO_FL = m_nMaxFlux;
    }

    /**
     * �ο�getConfig(CMPPQueryResponse response)����
     * @param response
     */
    public void setTransceiverConfig(CMPPQueryResponse response)
    {
        m_nType = 3;
        if(response.MT_TLUsr == 3)
        {
            m_nPort = response.MT_Scs;
            m_nCount = response.MT_WT;
            m_nMaxFlux = response.MT_FL;
        } else
        if(response.MT_TLMsg == 3)
        {
            m_nPort = response.MO_Scs;
            m_nCount = response.MO_WT;
            m_nMaxFlux = response.MO_FL;
        }
    }

    /**
     * �ο�getConfig(CMPPQueryResponse response)����
     * @param response
     */
    public void getTransmitterConfig(CMPPQueryResponse response)
    {
        response.MT_TLUsr = 1;
        response.MT_Scs = m_nPort;
        response.MT_WT = m_nCount;
        response.MT_FL = m_nMaxFlux;
    }

    /**
     * �ο�setConfig(CMPPQueryResponse response)����
     * @param response
     */
    public void setTransmitterConfig(CMPPQueryResponse response)
    {
        m_nType = 1;
        m_nPort = response.MT_Scs;
        m_nCount = response.MT_WT;
        m_nMaxFlux = response.MT_FL;
    }

    /**
     * �ο�setConfig(CMPPQueryResponse response)����
     * @param response
     */
    public void getReceiverConfig(CMPPQueryResponse response)
    {
        response.MT_TLMsg = 2;
        response.MO_Scs = m_nPort;
        response.MO_WT = m_nCount;
        response.MO_FL = m_nMaxFlux;
    }

    /**
     * �ο�setConfig(CMPPQueryResponse response)����
     * @param response
     */
    public void setReceiverConfig(CMPPQueryResponse response)
    {
        m_nType = 2;
        m_nPort = response.MO_Scs;
        m_nCount = response.MO_WT;
        m_nMaxFlux = response.MO_FL;
    }

    /**
     * �ο�getConfig(CMPPQueryResponse response)����
     * @param response
     */
    public void getActivaterConfig(CMPPQueryResponse response)
    {
        response.MT_TLMsg = 0;
        response.MO_Scs = m_nPort;
        response.MO_WT = m_nCount;
        response.MO_FL = m_nMaxFlux;
    }

    /**
     * �ο�setConfig(CMPPQueryResponse response)����
     * @param response
     */
    public void setActivaterConfig(CMPPQueryResponse response)
    {
        m_nType = 0;
        m_nPort = response.MO_Scs;
        m_nCount = response.MO_WT;
        m_nMaxFlux = response.MO_FL;
    }



}
