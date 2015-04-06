package LeadTone.CMPPDatabase;

import java.sql.SQLException;

import LeadTone.Engine;
import LeadTone.LeadToneDate;
import LeadTone.Log;
import LeadTone.TimeConfig;
import LeadTone.CMPPDatabase.CMPPTable.CMPPSubmitBackupTable;
import LeadTone.CMPPDatabase.CMPPTable.CMPPSubmitTable;
import LeadTone.Center.Center;
import LeadTone.Database.DatabasePool;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.LeadToneLogic.BackupTableConfig;
import LeadTone.Packet.Packet;
import LeadTone.Packet.CMPPPacket.CMPPPacketQueue;
import LeadTone.Packet.CMPPPacket.CMPPSubmit;


/**
 * 输出处理线程，从数据库CMPPSubmit表中读取所有未处理消息，完成下发或Response回复
 */
public class CMPPSubmitDatabaseOutput extends Engine
{
    static final int DEFAULT_BUFFER_SIZE = 64;
    int m_nID;
    String m_strWhere;
    String m_strOrder;
    CMPPSubmitTable m_submit;
    CMPPSubmitBackupTable m_submit_backup;
    CMPPPacketQueue m_queue;
    CMPPPacketQueue m_buffer;
    public int m_nCount;

    public CMPPSubmitDatabaseOutput(int nID, CMPPPacketQueue queue, DatabasePool pool)
    {
        super("CMPPSubmitDatabaseOutput(" + nID + ")");
        m_strWhere = null;
        m_strOrder = null;
        m_submit = null;
        m_queue = null;
        m_buffer = new CMPPPacketQueue(64);
        m_nCount = 0;
        m_nID = nID;
        m_queue = queue;
        m_submit = new CMPPSubmitTable();
        m_submit.m_pool = pool;
        m_submit_backup = new CMPPSubmitBackupTable();
        m_submit_backup.m_pool = pool;
    }

    public void empty()
    {
    	m_strWhere = null;
    	m_strOrder = null;
        m_queue = null;
        m_buffer.empty();
        m_buffer = null;
        m_submit.empty();
        m_submit = null;
        m_submit_backup.empty();
        m_submit_backup = null;
    }

    public void generateWhere(String gateway_name)
    {
    	if (m_submit.m_pool.m_dc.isMySQL())
			/*** 优化查询，调整SQL语句 ***/
			/**
			 * m_strWhere =
			 * "(ih_process = 'insert_cmpp_submit' or (ih_process = 'wait_for_response' and (ih_retry > 0) and ih_timestamp < date_sub(now(),interval "
			 * + TimeConfig.DEFAULT_RETRY_TIMEOUT / 1000L + " second))" + ")" +
			 * " and " + "(ih_gateway = '" + gateway_name + "')" + " and " +
			 * " MOD(id," + CMPPSubmitDatabase.m_nCount + ") = " + (m_nID - 1);
			 **/
    		//由于mas发送短信由企业付费，所以，此处不再查询wait状态的记录进行待发 yuqian 20130128.
			/*m_strWhere = "(ih_gateway = '"
					+ gateway_name
					+ "') and "
					+ "(ih_process = 'insert_cmpp_submit' or (ih_process = 'wait_for_response' and (ih_retry > 0) and ih_timestamp < date_sub(now(),interval "
					+ TimeConfig.DEFAULT_RETRY_TIMEOUT / 1000L + " second))"
					+ ")" + " and " + " MOD(id," + CMPPSubmitDatabase.m_nCount
					+ ") = " + (m_nID - 1);else*/
    		m_strWhere = "(ih_gateway = '"
					+ gateway_name
					+ "') and "
					+ "(ih_process = 'insert_cmpp_submit'"
					+ ")" + " and " + " MOD(id," + CMPPSubmitDatabase.m_nCount
					+ ") = " + (m_nID - 1);else
        if(m_submit.m_pool.m_dc.isMSSQLServer())
            m_strWhere = "(ih_process = 'insert_cmpp_submit' or (ih_process = 'wait_for_response' and (ih_retry > 0) and ih_timestamp < DATEADD(millisecond,-" + TimeConfig.DEFAULT_RETRY_TIMEOUT + ",GetDate())))" + " and " + "(ih_gateway = '" + gateway_name + "')" + " and " + "(id % " + CMPPSubmitDatabase.m_nCount + ") = " + (m_nID - 1) ;
        else
        if(m_submit.m_pool.m_dc.isOracle())
            m_strWhere = "(ih_process = 'insert_cmpp_submit' or (ih_process = 'wait_for_response' and (ih_retry > 0) and ih_timestamp < (SYSDATE - " + TimeConfig.DEFAULT_RETRY_TIMEOUT / 1000L + " / (24 * 60 * 60))))" + " and " + "(ih_gateway = '" + gateway_name + "')" + " and " + " MOD(id," + CMPPSubmitDatabase.m_nCount + ") = " + (m_nID - 1) ;
        else
        if(m_submit.m_pool.m_dc.isDB2())
        {
            LeadToneDate date = new LeadToneDate(System.currentTimeMillis() - TimeConfig.DEFAULT_RETRY_TIMEOUT);
            m_strWhere = "(ih_process = 'insert_cmpp_submit' or (ih_process = 'wait_for_response' and (ih_retry > 0) and ih_timestamp < TIMESTAMP('" + date.toString() + "')))" + " and " + "(ih_gateway = '" + gateway_name + "')" + " and " + " MOD(id," + CMPPSubmitDatabase.m_nCount + ") = " + (m_nID - 1) ;
        } else
        if(m_submit.m_pool.m_dc.isODBC())
            m_strWhere = "ih_process = 'insert_cmpp_submit' and (ih_gateway = '" + gateway_name + "')" ;
        else
            m_strWhere = "ih_process = 'insert_cmpp_submit' and (ih_gateway = '" + gateway_name + "')" ;
        //System.out.println(" -----cmpp2.submit : "+m_strWhere);
    }
    
    public void generateOrder(){
    	m_strOrder = " ORDER BY priority_level DESC";
    }

    public boolean selectData()
        throws SQLException
    {
        m_submit.open();
        m_buffer.setCapacity(Center.m_nMaxFlux);
        boolean bHasData = false;
        for(int i = 0; i < Center.m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)Center.m_gateways.elementAt(i);
            if(gateway.m_bTransmittable)
            {
                generateWhere(gateway.m_strName);
                generateOrder();

                if(m_submit.m_pool.m_dc.isOracle())
                    m_submit.selectRowNum(m_strWhere, gateway.m_nMaxFlux, m_strOrder);
                else  if(m_submit.m_pool.m_dc.isMSSQLServer())
                    m_submit.selectTop(gateway.m_nMaxFlux, m_strWhere, m_strOrder);
                else  if(m_submit.m_pool.m_dc.isMySQL())
                    m_submit.selectLimit(m_strWhere, gateway.m_nMaxFlux, m_strOrder);
                else
                    m_submit.select(m_strWhere, m_strOrder);
                
                if(m_submit.select(m_buffer))
                    bHasData = true;
                
            }
        }

        m_submit.close();
        return bHasData;
    }

    public void run()
    {
        try
        {
            Log.log("CMPPSubmitDatabaseOutput(" + m_nID + ").run : thread startup !", 0x1000000000L);
            //屏蔽获取条件为unavailable的查询
            //generateWhere("unavailable");
            //Log.log("\t" + m_strWhere, 0x1000000000L);
            m_nStatus = 1;
            while(isRunning()) 
                if(!selectData())
                {
                    Engine.sleep();
                } else
                {
                    while(!m_buffer.isEmpty() && isRunning()) 
                    {
                        CMPPSubmit submit = (CMPPSubmit)m_buffer.pop();
                        if(submit != null)
                            if(!submit.isValid()) {
                               //如果检查Submit格式有问题则更新数据库Submit状态为 invalid_submit_packet
                                Log.log("CMPPDatabaseOutput.run : update submit record !", 0x4000000000000L);
                                m_submit.open();
                                m_submit.update(submit.guid, 0, "invalid_submit_packet");
                                m_submit.close();

                                //如果动态备份机制开启则执行备份并删除
                                if (BackupTableConfig.DYNAMICBACKUPTABLE.equalsIgnoreCase("true")) {
                                Log.log("CMPPDatabaseOutput.run : backup submit record !", 0x4000000000000L);
                                m_submit_backup.open();
                                m_submit_backup.backup(submit);
                                m_submit_backup.close();
                                Log.log("CMPPDatabaseOutput.run : delete submit record !", 0x4000000000000L);
                                m_submit.open();
                                m_submit.deletenow(submit);
                                m_submit.close();
                                }

                            } else {
                                m_submit.open();
                                //在Submit没被Session处理之前sequence_id用来代替数据库中ih_retry(重发次数)的作用
                                m_submit.update(((Packet) (submit)).guid, submit.sequence_id - 1, "wait_for_response");
                                m_submit.close();
                                //当线程在运行，直到将Submit成功插入队列才推出循环
                                for(; !m_queue.push(submit) && isRunning(); Engine.nap());
                                submit.generate_time = System.currentTimeMillis();
                                m_nCount++;
                            }
                    }
                    Engine.nap(TimeConfig.DEFAULT_NAP_SUBMIT_TIME);
                }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPSubmitDatabaseOutput(" + m_nID + ").run : unexpected exit !", 0x2000001000000000L);
        }
        empty();
        m_nStatus = 3;
        Log.log("CMPPSubmitDatabaseOutput(" + m_nID + ").run : thread stopped !", 0x1000000000L);
    }



}