package LeadTone.CMPPDatabase;

import LeadTone.CMPPDatabase.CMPPTable.CMPPDeliverTable;
import LeadTone.CMPPDatabase.CMPPTable.CMPPDeliverBackupTable;
import LeadTone.Center.Center;
import LeadTone.Database.DatabaseConfig;
import LeadTone.Database.DatabasePool;
import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.TimeConfig;
import LeadTone.LeadToneLogic.UpdateFinalResultConfig;
import LeadTone.LeadToneLogic.BackupTableConfig;
import LeadTone.Packet.CMPPPacket.CMPPDeliverResponse;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import LeadTone.Packet.Packet;


/**
 * ��������̣߳������ݿ�CMPPDeliver���ж�ȡ����δ������Ϣ������·���Response�ظ�
 */
public class CMPPDeliverDatabaseOutput extends Engine
{
    static final int DEFAULT_BUFFER_SIZE = 64;
    int m_nID;
    String m_strWhere;
    String m_strOrder;
    CMPPDeliverTable m_deliver;
    CMPPDeliverBackupTable m_deliver_backup;
    CMPPPacketQueue m_queue;
    //m_bufferΪ�����ݿ��ж�ȡ����ʱ��Ļ������
    CMPPPacketQueue m_buffer;
    public int m_nCount;

    public CMPPDeliverDatabaseOutput(int nID, CMPPPacketQueue queue, DatabasePool pool)
    {
        super("CMPPDeliverDatabaseOutput(" + nID + ")");
        m_strWhere = null;
        m_strOrder = null;
        m_deliver = null;
        m_queue = null;
        m_buffer = new CMPPPacketQueue(64);
        m_nCount = 0;
        m_nID = nID;
        m_queue = queue;
        m_deliver = new CMPPDeliverTable();
        m_deliver.m_pool = pool;
        m_deliver_backup = new CMPPDeliverBackupTable();
        m_deliver_backup.m_pool = pool;
    }

    public void empty()
    {
        m_strWhere = null;
        m_strOrder = null;
        m_deliver.empty();
        m_deliver = null;
        m_deliver_backup.empty();
        m_deliver_backup = null;
        m_buffer.empty();
        m_buffer = null;
        m_queue = null;
    }

    /**
     *  ��ѯCMPPDeliver���иող����Deliver��Ϣ��¼��׼������DeliverResponse��MOD��Ŀ����Ϊ�˽������·���¼�ַ�����ͬ���̴߳���
     */
    public void generateWhere()
    {
        if(m_deliver.m_pool.m_dc.isMySQL())
            m_strWhere = "ih_process = 'insert_cmpp_deliver' and (" + Center.generateWhere() + ")" + " and " + " MOD(id," + CMPPDeliverDatabase.m_nCount + ") = " + (m_nID - 1);
        else
        if(m_deliver.m_pool.m_dc.isMSSQLServer())
            m_strWhere = "ih_process = 'insert_cmpp_deliver' and (" + Center.generateWhere() + ")" + " and " + " (id % " + CMPPDeliverDatabase.m_nCount + ") = " + (m_nID - 1);
        else
        if(m_deliver.m_pool.m_dc.isOracle())
            m_strWhere = "ih_process = 'insert_cmpp_deliver' and (" + Center.generateWhere() + ")" + " and " + " MOD(id," + CMPPDeliverDatabase.m_nCount + ") = " + (m_nID - 1);
        else
        if(m_deliver.m_pool.m_dc.isDB2())
            m_strWhere = "ih_process = 'insert_cmpp_deliver' and (" + Center.generateWhere() + ")" + " and " + " MOD(id," + CMPPDeliverDatabase.m_nCount + ") = " + (m_nID - 1);
        else
        if(m_deliver.m_pool.m_dc.isODBC())
            m_strWhere = "ih_process = 'insert_cmpp_deliver' and (" + Center.generateWhere() + ")";
        else
            m_strWhere = "ih_process = 'insert_cmpp_deliver' and (" + Center.generateWhere() + ")";
    }
    
    public void generateOrder(){
    	m_strOrder = " ";
    }

    /**
     * �����ݿ��е���Deliver��¼����m_buffer�����У�������Ϸ��ԣ�������Ϸ�����¼�¼״̬Ϊinvalid_response_packet��
     * ��������ɾ��������Ϸ������Ϊcmpp_deliver_responsed�����Ҳ���������У�ֱ���ɹ�����Ϊֹ
     */
    public void run()
    {
        try
        {
            Log.log("CMPPDeliverDatabaseOutput(" + m_nID + ").run : thread startup !", 0x2000000000L);
            generateWhere();
            Log.log("\t" + m_strWhere, 0x2000000000L);
            m_nStatus = 1;
            while(isRunning())
            {
                m_deliver.open();
                generateWhere();   
                generateOrder();
                m_deliver.select(m_strWhere, m_strOrder);
                boolean bHasData = m_deliver.select(m_buffer);
                m_deliver.close();
                if(!bHasData)
                {
                    Engine.sleep();
                } else
                {   
                    while(!m_buffer.isEmpty() && isRunning())
                    {
                        CMPPDeliverResponse response = (CMPPDeliverResponse)m_buffer.pop();
                        //���DeliverResponse��Ϣ���Ϸ�����ֱ�Ӹ�����Ϣ״̬Ϊ���Ϸ�������ֲ�����ݱ�
                        if(!response.isValid())
                        {
                            Log.log("CMPPDatabaseOutput.run : update deliver ih_process invalid_response_packet !", 0x4000000000000L);
                            m_deliver.open();
                            m_deliver.update(((Packet) (response)).guid, "invalid_response_packet");
                            m_deliver.close();

                            //�����̬���ݻ��ƿ�����ִ�б��ݲ�ɾ��
                            if (BackupTableConfig.DYNAMICBACKUPTABLE.equalsIgnoreCase("true")) {
                            Log.log("CMPPDatabaseOutput.run : backup deliver record !", 0x4000000000000L);
                            m_deliver_backup.open();
                            m_deliver_backup.backup(response);
                            m_deliver_backup.close();
                            Log.log("CMPPDatabaseOutput.run : delete deliver record !", 0x4000000000000L);
                            m_deliver.open();
                            m_deliver.deletenow(response);
                            m_deliver.close();
                            }

                        } else
                        {


                            for(; !m_queue.push(response) && isRunning(); Engine.nap());
                            response.generate_time = System.currentTimeMillis();
                            m_nCount++;

                            Log.log("CMPPDatabaseOutput.run : update deliver ih_process cmpp_deliver_responsed !", 0x4000000000000L);
                            m_deliver.open();
                            m_deliver.update(((Packet) (response)).guid, "cmpp_deliver_responsed");
                            m_deliver.close();

                            //�����̬���ݻ��ƿ�����ִ�б��ݲ�ɾ��
                            if (BackupTableConfig.DYNAMICBACKUPTABLE.equalsIgnoreCase("true")) {
                            Log.log("CMPPDatabaseOutput.run : backup deliver record !", 0x4000000000000L);
                            m_deliver_backup.open();
                            m_deliver_backup.backup(response);
                            m_deliver_backup.close();
                            Log.log("CMPPDatabaseOutput.run : delete deliver record !", 0x4000000000000L);
                            m_deliver.open();
                            m_deliver.deletenow(response);
                            m_deliver.close();
                            }
                        }
                    }
                    Engine.nap(TimeConfig.DEFAULT_NAP_DELIVER_TIME);
                }
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPDeliverDatabaseOutput(" + m_nID + ").run : unexpected exit !", 0x2000002000000000L);
        }
        empty();
        m_nStatus = 3;
        Log.log("CMPPDeliverDatabaseOutput(" + m_nID + ").run : thread stopped !", 0x2000000000L);
    }



}