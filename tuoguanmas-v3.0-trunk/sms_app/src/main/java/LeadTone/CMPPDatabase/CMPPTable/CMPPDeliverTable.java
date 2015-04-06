package LeadTone.CMPPDatabase.CMPPTable;

import LeadTone.CMPPDatabase.CMPPDeliverDatabase;
import LeadTone.Database.*;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Utility;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * 继承自Table拥有所有JDBC的功能，完成对表CMPPDeliver的操作
 */
public class CMPPDeliverTable extends Table
{

    public CMPPDeliverTable()
    {
        //super("cmpp_deliver");
        super("smw_cmpp_deliver");
    }

    /**
     * 从CMPPDeliver表中读取Deliver消息直接生成DeliverResponse消息
     * @param queue
     * @return 返回队列是否为空
     * @throws SQLException
     */
    public boolean select(CMPPPacketQueue queue)
        throws SQLException
    {
        boolean bHasData;
        for(bHasData = false; !queue.isFull() && next(); bHasData = true)
        {
            CMPPDeliverResponse response = new CMPPDeliverResponse(0);
            select(response);
            queue.push(response);
        }

        return bHasData;
    }

    /**
     * 从CMPPDeliver表读取刚刚收到的Deliver消息记录，并组装成DeliverResponse消息
     * @param response 组装的DeliverResponse消息
     * @throws SQLException 抛出SQLException
     */
    public void select(CMPPDeliverResponse response)
        throws SQLException
    {
        response.guid = getLong("id");
        response.gateway_name = getString("ih_gateway");
        response.session_id = getInteger("ih_session");
        response.sequence_id = getInteger("ih_sequence");
        String msg_id = getString("msg_id");
        if(msg_id != null)
            response.msg_id = Utility.toHexLong(msg_id);
        else
            response.msg_id = 0L;
        String service_id = getString("service_id");
        if(service_id == null || service_id.length() <= 0)
            response.result = 0;
        else
        //SMPP协议中的特有部分
        if(service_id.equals("REPORT"))
            response.result = 1;
        else
        if(service_id.equals("USERRPT"))
            response.result = 2;
        else
            response.result = 0;
    }

    /**
     * 更新CMPPDeliver表 处理状况(process字段)
     * @param id
     * @param process
     * @throws SQLException
     */
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

    /**
     * 从Deliver消息中获取参数名称，与获取参数值配合完成向CMPPDeliver表Insert新Deliver记录的操作
     * @param deliver
     * @return 返回参数列组织的SQL语句部分
     */
    public String getParameters(CMPPDeliver deliver)
    {
        String strParameters = "";
        strParameters = strParameters + "msg_id,";
        if(deliver.destination_id != null && deliver.destination_id.length() > 0)
            strParameters = strParameters + "destination_id,";
        if(deliver.service_id != null && deliver.service_id.length() > 0)
            strParameters = strParameters + "service_id,";
        strParameters = strParameters + "tp_pid,tp_udhi,msg_fmt,";
        if(deliver.src_terminal_id != null && deliver.src_terminal_id.length() > 0)
            strParameters = strParameters + "src_terminal_id,";
        strParameters = strParameters + "registered_delivery,msg_length,";
        if(deliver.msg_content != null && deliver.msg_content.length > 0)
            strParameters = strParameters + "msg_content,";
        strParameters = strParameters + "msg_mr,";
        if(deliver.status_report.status != null && deliver.status_report.status.length() > 0)
            strParameters = strParameters + "status,";
        if(deliver.status_report.submit_time != null && deliver.status_report.submit_time.length() > 0)
            strParameters = strParameters + "submit_time,";
        if(deliver.status_report.done_time != null && deliver.status_report.done_time.length() > 0)
            strParameters = strParameters + "done_time,";
        if(deliver.status_report.dest_terminal_id != null && deliver.status_report.dest_terminal_id.length() > 0)
            strParameters = strParameters + "dest_terminal_id,";
        strParameters = strParameters + "smsc_sequence,ih_gateway,ih_session,ih_sequence";
        
        strParameters = strParameters + ",ih_process";
        
        return new String(strParameters);
    }

    /**
     * 从Deliver消息中获取参数值
     * @param deliver
     * @return 返回参数赋值的SQL语句部分
     * @throws SQLException 
     */
    public String getValues(CMPPDeliver deliver) throws SQLException
    {
        String strValues = "";
        strValues = strValues + "'" + Utility.toHexString(deliver.msg_id) + "',";
        if(deliver.destination_id != null && deliver.destination_id.length() > 0)
            strValues = strValues + "'" + deliver.destination_id + "',";
        if(deliver.service_id != null && deliver.service_id.length() > 0)
            strValues = strValues + "'" + deliver.service_id + "',";
        strValues = strValues + deliver.tp_pid + "," + deliver.tp_udhi + "," + deliver.msg_fmt + ",";
        if(deliver.src_terminal_id != null && deliver.src_terminal_id.length() > 0)
            strValues = strValues + "'" + deliver.src_terminal_id + "',";
        strValues = strValues + deliver.registered_delivery + "," + deliver.msg_length + ",";
        if(deliver.msg_content != null && deliver.msg_content.length > 0)
            if(m_pool.m_dc.isMySQL() || m_pool.m_dc.isMSSQLServer())
            {//此处不再转为16进制。modified by chenguoliang
                //String strHex = Utility.toHexString(deliver.msg_content);
            	//remove follow
//            	String strHex = new String(deliver.msg_content);
//            	strValues = strValues + "'" + strHex + "',";
            	//add
            	//remove by sunyadong 20141221 follow 2 lines TODO
               String strHex="";
         	   try {
         		   strHex = new String(deliver.msg_content,"GBK");
     				if (deliver.msg_fmt == 8){
     					strHex = new String(deliver.msg_content,"UnicodeBigUnmarked");
     				}
     			} catch (UnsupportedEncodingException e) {
     				// TODO Auto-generated catch block
     				throw new SQLException();
     			}
         	    strValues = strValues + "'" + strHex + "',";
            } else
            if(m_pool.m_dc.isOracle())
                strValues = strValues + "HEXTORAW('" + Utility.toHexString(deliver.msg_content) + "'),";
            else
            if(m_pool.m_dc.isODBC())
            {
                String strHex = Utility.toHexString(deliver.msg_content);
                strValues = strValues + "0x" + strHex + ",";
            } else
            if(m_pool.m_dc.isDB2())
            {
                String strHex = Utility.toHexString(deliver.msg_content);
                strValues = strValues + "X'" + strHex + "',";
            } else
            {
                strValues = strValues + "'" + deliver.msg_content + "',";
            }
        strValues = strValues + "'" + Utility.toHexString(deliver.status_report.msg_id) + "',";
        if(deliver.status_report.status != null && deliver.status_report.status.length() > 0)
            strValues = strValues + "'" + deliver.status_report.status + "',";
        if(deliver.status_report.submit_time != null && deliver.status_report.submit_time.length() > 0)
            strValues = strValues + "'" + deliver.status_report.submit_time + "',";
        if(deliver.status_report.done_time != null && deliver.status_report.done_time.length() > 0)
            strValues = strValues + "'" + deliver.status_report.done_time + "',";
        if(deliver.status_report.dest_terminal_id != null && deliver.status_report.dest_terminal_id.length() > 0)
            strValues = strValues + "'" + deliver.status_report.dest_terminal_id + "',";
        strValues = strValues + deliver.status_report.smsc_sequence + "," + "'" + deliver.gateway_name + "'," + deliver.session_id + "," + deliver.sequence_id; 
                
        if (CMPPDeliverDatabase.m_output_switch){
            strValues = strValues + ",'insert_cmpp_deliver'";
        }
        else
        {
        	strValues = strValues + ",'cmpp_deliver_responsed'";	
        }

          return new String(strValues);
    }

    /**
     * 向CMPPDeliver表插入新收到的Deliver消息
     * @param deliver
     * @throws SQLException
     */
    public void insert(CMPPDeliver deliver)
        throws SQLException
    {
        String strParameters = getParameters(deliver);
        String strValues = getValues(deliver);
        insert(strParameters, strValues);
    }

    /**
     * 当组织完DeliverResponse之后，备份CMPPDeliver表数据到CMPPDeliverBackup表，最后删除CMPPDeliver表数据
     * @param response 通过DeliverResponse消息体获取对表操作的主键参数
     * @return 返回更新的记录条数
     * @throws SQLException
     */
    public int deletenow(CMPPDeliverResponse response)
        throws SQLException
    {
       String strCondition = String.valueOf((new StringBuffer("id = ")).append(response.guid));
       return delete(strCondition);
    }
    
    
    /**
     * 当收到deliver消息之后，备份CMPPDeliver表数据到CMPPDeliverBackup表，最后删除CMPPDeliver表数据
     * @param deliver 通过deliver消息体获取对表操作的主键参数
     * @return 返回更新的记录条数
     * @throws SQLException
     */
    public int deletenow(CMPPDeliver deliver)
        throws SQLException
    {
       String strCondition = String.valueOf((new StringBuffer("id = ")).append(deliver.guid));
       return delete(strCondition);
    }
    
    
    
    
    
}