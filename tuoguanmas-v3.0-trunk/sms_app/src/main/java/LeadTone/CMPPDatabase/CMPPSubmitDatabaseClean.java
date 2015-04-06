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

public class CMPPSubmitDatabaseClean extends Engine{

	    static final int DEFAULT_BUFFER_SIZE = 64;
	    int m_nID;
	    String m_strWhere;
	    String m_strOrder;
	    CMPPSubmitTable m_submit;
	    CMPPSubmitBackupTable m_submit_backup;
	    CMPPPacketQueue m_buffer;
	    public int m_nCount;

	    public CMPPSubmitDatabaseClean(int nID, DatabasePool pool)
	    {
	    	super("CMPPSubmitDatabaseClean(" + nID + ")");
	        m_strWhere = null;
	        m_strOrder = null;
	        m_submit = null;
	        m_buffer = new CMPPPacketQueue(64);
	        m_nCount = 0;
	        m_nID = nID;
	        m_submit = new CMPPSubmitTable();
	        m_submit.m_pool = pool;
	        m_submit_backup = new CMPPSubmitBackupTable();
	        m_submit_backup.m_pool = pool;
	    }

	    public void empty()
	    {
	    	m_strWhere = null;
	    	m_strOrder = null;
	        m_buffer.empty();
	        m_buffer = null;
	        m_submit.empty();
	        m_submit = null;
	        m_submit_backup.empty();
	        m_submit_backup = null;
	    }

	    public void generateWhere(String gateway_name)
	    {
	        if(m_submit.m_pool.m_dc.isMySQL())
	            m_strWhere = "((ih_process <> 'insert_cmpp_submit' and ih_process <> 'wait_for_response') or (ih_process = 'wait_for_response' and ih_retry <= 0)) and ih_timestamp < date_sub(now(),interval " + (2*TimeConfig.TIME_ONE_DAY) / 1000L + " second) " + " and " + "(ih_gateway = '" + gateway_name + "')" + " and " + " MOD(id," + CMPPSubmitDatabase.m_nCount + ") = " + (m_nID - 1) ;
            else
	        if(m_submit.m_pool.m_dc.isMSSQLServer())
	            m_strWhere = "((ih_process <> 'insert_cmpp_submit' and ih_process <> 'wait_for_response') or (ih_process = 'wait_for_response' and ih_retry <= 0)) and ih_timestamp < DATEADD(millisecond,-" + (2*TimeConfig.TIME_ONE_DAY) + ",GetDate())" + " and " + "(ih_gateway = '" + gateway_name + "')" + " and " + "(id % " + CMPPSubmitDatabase.m_nCount + ") = " + (m_nID - 1) ;
            else
	        if(m_submit.m_pool.m_dc.isOracle())
	        	   m_strWhere = "((ih_process <> 'insert_cmpp_submit' and ih_process <> 'wait_for_response') or (ih_process = 'wait_for_response' and ih_retry <= 0)) and ih_timestamp < (SYSDATE - " + (2*TimeConfig.TIME_ONE_DAY) / 1000L + " / (24 * 60 * 60))" + " and " + "(ih_gateway = '" + gateway_name + "')" + " and " + " MOD(id," + CMPPSubmitDatabase.m_nCount + ") = " + (m_nID - 1) ;
            else
	        if(m_submit.m_pool.m_dc.isDB2())
	        {
	            LeadToneDate date = new LeadToneDate(System.currentTimeMillis() - (2*TimeConfig.TIME_ONE_DAY));
	            m_strWhere = "((ih_process <> 'insert_cmpp_submit' and ih_process <> 'wait_for_response') or (ih_process = 'wait_for_response' and ih_retry <= 0)) and ih_timestamp < TIMESTAMP('" + date.toString() + "')" + " and " + "(ih_gateway = '" + gateway_name + "')" + " and " + " MOD(id," + CMPPSubmitDatabase.m_nCount + ") = " + (m_nID - 1) ;
            } else
	        if(m_submit.m_pool.m_dc.isODBC())
	        	m_strWhere = "ih_process <> 'insert_cmpp_submit' and (ih_gateway = '" + gateway_name + "')" ;
            else
            	  m_strWhere = "ih_process <> 'insert_cmpp_submit' and (ih_gateway = '" + gateway_name + "')" ;
            }

	    public void generateOrder(){
	    	m_strOrder = "  ORDER BY id ASC";
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
	                if(m_submit.m_pool.m_dc.isMSSQLServer())
	                    m_submit.selectTop(gateway.m_nMaxFlux, m_strWhere, m_strOrder);
	                else
	                if(m_submit.m_pool.m_dc.isMySQL())
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
	        	Log.log("CMPPSubmitDatabaseClean(" + m_nID + ").run : thread startup !", 0x1000000000L);
	            generateWhere("unavailable");
	            Log.log("\t" + m_strWhere, 0x1000000000L);
	            m_nStatus = 1;
	            while(isRunning()) 
	                if(!selectData())
	                {
	                	Engine.nap(TimeConfig.DEFAULT_SUBMIT_CLEAN_TIMEOUT);
	                } else
	                {
	                    while(!m_buffer.isEmpty() && isRunning()) 
	                    {
	                        CMPPSubmit submit = (CMPPSubmit)m_buffer.pop();
	                        if(submit != null){


	                                //如果动态备份机制开启则执行备份并删除
	                                if (BackupTableConfig.DYNAMICBACKUPTABLE.equalsIgnoreCase("true")) {
	                                Log.log("CMPPSubmitDatabaseClean.run : backup submit record !", 0x4000000000000L);
	                                m_submit_backup.open();
	                                m_submit_backup.backup(submit);
	                                m_submit_backup.close();
	                                Log.log("CMPPSubmitDatabaseClean.run : delete submit record !", 0x4000000000000L);
	                                m_submit.open();
	                                m_submit.deletenow(submit);
	                                m_submit.close();
	                                m_nCount++;
	                                }

	                                
	                            }
	                    }
	                    Engine.nap(TimeConfig.DEFAULT_SUBMIT_CLEAN_TIMEOUT);
	                }
	        }
	        catch(Exception e)
	        {
	            Log.log(e);
	            Log.log("CMPPSubmitDatabaseClean.run : unexpected exit !", 0x2000001000000000L);
	        }
	        empty();
	        m_nStatus = 3;
	        Log.log("CMPPSubmitDatabaseClean.run : thread stopped !", 0x1000000000L);
	    }


	
	
	
}
