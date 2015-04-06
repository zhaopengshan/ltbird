package LeadTone.CMPPDatabase;

import LeadTone.Database.DatabasePool;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;


/**
 * Submit,Deliver,Query�������͵�������̾��̳���CMPPDatabaseInput�����е��������ͳһ����
 * �������У����ݲ�ͬ����Ϣ�����ٷַ�����CMPPDatabaseInput�а����������봦��
 * ���߼�
 */
public class CMPPQueryDatabaseInput extends CMPPDatabaseInput
{

    public CMPPQueryDatabaseInput(int nID, CMPPPacketQueue queue, DatabasePool pool)
    {
        super(nID, queue, pool);
    }
}
