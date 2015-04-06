package LeadTone.CMPPDatabase;

import LeadTone.CMPPDatabase.CMPPTable.CMPPQueryTable;
import LeadTone.Center.CMPPCenter;
import LeadTone.Center.Center;
import LeadTone.Database.DatabaseConfig;
import LeadTone.Database.DatabasePool;
import LeadTone.Engine;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.TimeConfig;
import java.sql.SQLException;
import java.util.Vector;


/**
 * 输出处理线程，从数据库CMPPQuery表中读取所有未处理消息，完成查询或Response回复
 */
public class CMPPQueryDatabaseOutput extends Engine
{
    static final int DEFAULT_BUFFER_SIZE = 16;
    int m_nID;
    String m_strWhere;
    String m_strOrder;
    CMPPQueryTable m_query;
    CMPPPacketQueue m_queue;
    CMPPPacketQueue m_buffer;
    public int m_nCount;

    public CMPPQueryDatabaseOutput(int nID, CMPPPacketQueue queue, DatabasePool pool)
    {
        super("CMPPQueryDatabaseOutput(" + nID + ")");
        m_strWhere = null;
        m_strOrder = null;
        m_query = null;
        m_queue = null;
        m_buffer = new CMPPPacketQueue(16);
        m_nCount = 0;
        m_nID = nID;
        m_queue = queue;
        m_query = new CMPPQueryTable();
        m_query.m_pool = pool;
    }

    public void empty()
    {
        m_strWhere = null;
        m_strOrder = null;
        m_query.empty();
        m_query = null;
        m_buffer.empty();
        m_buffer = null;
        m_queue = null;
    }

    public void generateWhere()
    {
        if(m_query.m_pool.m_dc.isMySQL())
            m_strWhere = "ih_process = 'insert_cmpp_query' and (" + CMPPCenter.generateWhere() + ")" + " and " + " MOD(id," + CMPPQueryDatabase.m_nCount + ") = " + (m_nID - 1);
        else
        if(m_query.m_pool.m_dc.isMSSQLServer())
            m_strWhere = "ih_process = 'insert_cmpp_query' and (" + CMPPCenter.generateWhere() + ")" + " and " + " (id % " + CMPPQueryDatabase.m_nCount + ") = " + (m_nID - 1);
        else
        if(m_query.m_pool.m_dc.isOracle())
            m_strWhere = "ih_process = 'insert_cmpp_query' and (" + Center.generateWhere() + ")" + " and " + " MOD(id," + CMPPQueryDatabase.m_nCount + ") = " + (m_nID - 1);
        else
        if(m_query.m_pool.m_dc.isDB2())
            m_strWhere = "ih_process = 'insert_cmpp_query' and (" + Center.generateWhere() + ")" + " and " + " MOD(id," + CMPPQueryDatabase.m_nCount + ") = " + (m_nID - 1);
        else
        if(m_query.m_pool.m_dc.isODBC())
            m_strWhere = "ih_process = 'insert_cmpp_query' and (" + Center.generateWhere() + ")";
        else
            m_strWhere = "ih_process = 'insert_cmpp_query' and (" + Center.generateWhere() + ")";
        
        Log.log(" ----- generateWhere:"+m_strWhere,  0x1000000000L);
    }
    
    public void generateOrder(){
    	m_strOrder = " ";
    }

    public void updateLog()
        throws SQLException
    {
        m_query.open();
        boolean bFound = false;
        m_query.select("query_type = 5 and ih_process = 'cmpp_set_log'", " ");
        while(m_query.next()) 
        {
            CMPPQueryResponse response = new CMPPQueryResponse(0);
            m_query.select(response);
            if(response.query_code != null && response.query_code.length() > 0)
            {
                Log.setLog(response.query_code);
                bFound = true;
            }
        }
        if(bFound)
            m_query.update("query_code = ''", "query_type = 5");
        m_query.close();
    }

    public void updateGatewayStatus()
        throws SQLException
    {
        m_query.open();
        for(int i = 0; i < Center.m_gateways.size(); i++)
        {
            GatewayEngine gateway = (GatewayEngine)Center.m_gateways.elementAt(i);
            CMPPQueryResponse response = gateway.generateStatusReport();
            if(response != null)
                m_query.update(response);
        }

        m_query.close();
    }

    public void run()
    {
        try
        {
            Log.log("CMPPQueryDatabaseOutput(" + m_nID + ").run : thread startup !", 0x4000000000L);
            generateWhere();
            Log.log("\t" + m_strWhere, 0x4000000000L);
            m_nStatus = 1;
            while(isRunning()) 
            {
                m_query.open();
                generateWhere();
                generateOrder();
                m_query.select(m_strWhere, m_strOrder);
                boolean bHasData = m_query.select(m_buffer);
                m_query.close();
                if(!bHasData)
                {
                    updateGatewayStatus();
                    Thread.sleep(TimeConfig.DEFAULT_SURVIVE_TIME);
                } else
                {
                    while(!m_buffer.isEmpty() && isRunning()) 
                    {
                        CMPPQuery query = (CMPPQuery)m_buffer.pop();
                        if(!query.isValid())
                        {
                            m_query.open();
                            m_query.update(query.guid, "invalid_query_packet");
                            m_query.close();
                        } else
                        {
                            m_query.open();
                            m_query.update(query.guid, "wait_for_response");
                            m_query.close();
                            for(; !m_queue.push(query) && isRunning(); Engine.nap());
                            m_nCount++;
                            query.generate_time = System.currentTimeMillis();
                        }
                    }
                    Engine.nap(TimeConfig.DEFAULT_NAP_QUERY_TIME);
                }
            }
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("CMPPQueryDatabaseOutput(" + m_nID + ").run : unexpected exit !", 0x2000004000000000L);
        }
        empty();
        m_nStatus = 3;
        Log.log("CMPPQueryDatabaseOutput(" + m_nID + ").run : thread stopped !", 0x4000000000L);
    }



}