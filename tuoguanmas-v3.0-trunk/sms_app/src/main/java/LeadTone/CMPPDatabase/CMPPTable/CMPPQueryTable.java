package LeadTone.CMPPDatabase.CMPPTable;

import LeadTone.Database.*;
import LeadTone.Packet.CMPPPacket.*;
import java.sql.SQLException;

/**
 * 继承自Table拥有所有JDBC的功能，完成对表CMPPQuery的操作
 * 具体方法含义参考CMPPDeliverTable类
 */
public class CMPPQueryTable extends Table
{

    public CMPPQueryTable()
    {
    	//super("cmpp_query");
        super("smw_cmpp_query");
    }

    public boolean select(CMPPPacketQueue queue)
        throws SQLException
    {
        boolean bHasData;
        for(bHasData = false; !queue.isFull() && next(); bHasData = true)
        {
            CMPPQuery query = new CMPPQuery(0);
            select(query);
            queue.push(query);
        }

        return bHasData;
    }

    public void select(CMPPQuery query)
        throws SQLException
    {
        query.guid = getLong("id");
        query.query_time = getString("query_time");
        if(query.query_time == null || query.query_time.length() <= 0)
            query.setTime();
        query.query_type = (byte)(getInteger("query_type") & 0xff);
        query.query_code = getString("query_code");
        query.gateway_name = getString("ih_gateway");
        query.session_id = getInteger("ih_session");
    }

    public void select(CMPPQueryResponse response)
        throws SQLException
    {
        response.guid = getLong("id");
        response.query_time = getString("query_time");
        response.query_type = (byte)(getInteger("query_type") & 0xff);
        response.query_code = getString("query_code");
        response.MT_TLMsg = getInteger("MT_TLMsg");
        response.MT_TLUsr = getInteger("MT_TLUsr");
        response.MT_Scs = getInteger("MT_Scs");
        response.MT_WT = getInteger("MT_WT");
        response.MT_FL = getInteger("MT_FL");
        response.MO_Scs = getInteger("MO_Scs");
        response.MO_WT = getInteger("MO_WT");
        response.MO_FL = getInteger("MO_FL");
        response.gateway_name = getString("ih_gateway");
        response.session_id = getInteger("ih_session");
    }

    public void update(long id, String process)
        throws SQLException
    {
        String strWhere = null;
        if(m_pool.m_dc.isMySQL() || m_pool.m_dc.isMSSQLServer2000() || m_pool.m_dc.isOracle() || m_pool.m_dc.isDB2())
            strWhere = "id = " + id;
        else
        if(m_pool.m_dc.isMSSQLServer65() || m_pool.m_dc.isMSSQLServer70())
            strWhere = "id = " + (int)(id & -1L);
        else
        if(m_pool.m_dc.isODBC())
            strWhere = "id = " + id;
        else
            strWhere = "id = " + id;
        update("ih_process = '" + process + "'", strWhere);
    }

    public void insert(CMPPQuery query)
        throws SQLException
    {
        insert((query.query_time != null ? "query_time," : "") + "query_type," + (query.query_code != null ? "query_code," : "") + "ih_session", (query.query_time != null ? "'" + query.query_time + "'" : "") + query.query_type + "," + (query.query_code != null ? "'" + query.query_code + "'" : "") + query.session_id);
    }

    public void insert(CMPPQueryResponse response)
        throws SQLException
    {
        insert((response.query_time != null ? "query_time," : "") + "query_type," + (response.query_code != null ? "query_code," : "") + "MT_TLMsg,MT_TLUsr,MT_Scs,MT_WT,MT_FL,MO_Scs,MO_WT,MO_FL,ih_gateway,ih_session,ih_process", (response.query_time != null ? "'" + response.query_time + "'," : "") + response.query_type + "," + (response.query_code != null ? "'" + response.query_code + "'," : "") + response.MT_TLMsg + "," + response.MT_TLUsr + "," + response.MT_Scs + "," + response.MT_WT + "," + response.MT_FL + "," + response.MO_Scs + "," + response.MO_WT + "," + response.MO_FL + "," + "'" + response.gateway_name + "'," + response.session_id + "," + "'cmpp_query_success'");
    }

    public void update(CMPPQueryResponse response)
        throws SQLException
    {
        String strWhere = null;
        if(response.query_type >= 3)
            strWhere = "query_type = " + response.query_type + " and " + " ih_gateway = '" + response.gateway_name + "'";
        else
        if(m_pool.m_dc.isMySQL() || m_pool.m_dc.isMSSQLServer2000() || m_pool.m_dc.isOracle() || m_pool.m_dc.isDB2())
            strWhere = "id = " + response.guid;
        else
        if(m_pool.m_dc.isODBC())
            strWhere = "id = " + (response.guid & -1L);
        else
            strWhere = "id = " + response.guid;
        int nResult = update((response.query_time != null ? "query_time = '" + response.query_time + "'," : "") + "query_type = " + response.query_type + "," + (response.query_code != null ? "query_code = '" + response.query_code + "'," : "") + "MT_TLMsg = " + response.MT_TLMsg + "," + "MT_TLUsr = " + response.MT_TLUsr + "," + "MT_Scs = " + response.MT_Scs + "," + "MT_WT = " + response.MT_WT + "," + "MT_FL = " + response.MT_FL + "," + "MO_Scs = " + response.MO_Scs + "," + "MO_WT = " + response.MO_WT + "," + "MO_FL = " + response.MO_FL + "," + "ih_gateway = '" + response.gateway_name + "'," + "ih_process = 'cmpp_query_success'", strWhere);
        if(response.query_type >= 3 && nResult <= 0)
            insert(response);
    }
}