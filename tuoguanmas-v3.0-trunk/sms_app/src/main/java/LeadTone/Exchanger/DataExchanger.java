package LeadTone.Exchanger;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Engine;
import LeadTone.Packet.CMPPPacket.CMPPPacket;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import LeadTone.Port.CMPPXMLExchanger;

/**
 * ϵͳ����н����ݿ��ṹ���Ϊ����CMPP��׼Э��ʵ�֣�������֤��ṹ��ͳһ�ԣ�Ҳ�����ֶεĶ����ԣ�
 * ����ϵͳͬʱ֧�ֶ��ֶ���Э��ķ�������գ�����SMPP��SGIP��CNGP�Լ���ͬ���̵ķǱ�׼ʵ�ֲ��죩��
 * �����Ϣ�����ڶ����ش���ͱ��س־û�֮���Ҫ���б�Ҫ����Ϣ��ʽת����������Ҫ��ʵ����Ϣ�־û���
 * ������ݿ���ȡ���д���Ĺ��ܣ�������Ϣ��ʽת�������������࣬���ݸ��Ե�Э��������ת��
 */
public class DataExchanger extends Engine
{
    /**
     * ���m_nID����̺߳�Ψһ�Ա�ʶ
     */
    static int m_nUniqueID = 1;
    /**
     * ��Ϣת���̵߳��̺߳ţ����ڶ��߳�ʱ
     */
    public int m_nID;
    /**
     * ���ݿ��������
     */
    CMPPDatabase m_database;
    /**
     * ����ϵͳ���ⲿ�Ļ���XML�Ľӿڣ�ʵ����Ϣ����XML��XML����Ϣ�����ת��������һ���������Ϣת��
     */
    CMPPXMLExchanger m_exchanger;
    /**
     * ��Ϣ����ӳ�䣬������ʱ���
     */
    CMPPPacket m_packet;

    /**
     * ���췽����ʼ�������
     * @param database
     * @param exchanger
     */
    public DataExchanger(CMPPDatabase database, CMPPXMLExchanger exchanger)
    {
        super("DataExchanger");
        m_database = null;
        m_exchanger = null;
        m_packet = null;
        m_nID = m_nUniqueID;
        m_nUniqueID++;
        m_database = database;
        m_exchanger = exchanger;
    }

    /**
     * ���ӻ���XML���ⲿ�ӿڻ�ȡ����Ϣȡ�����������ݿ��������Ϣ���У��ȴ��־û���
     * ������ʵ�ֲ�ͬЭ�����Ϣת����Ϻ�������ݿ�־û��Ĺ���
     */
    public void toDatabase()
    {
        //���ӻ���XML���ⲿ�ӿڻ�ȡ����Ϣȡ��
        if(m_packet == null)
            m_packet = (CMPPPacket)m_exchanger.m_input.pop();
        else
        //�������ݿ��������Ϣ���У��ȴ��־û�
        if(m_database.m_input.push(m_packet))
            m_packet = null;
    }



}
