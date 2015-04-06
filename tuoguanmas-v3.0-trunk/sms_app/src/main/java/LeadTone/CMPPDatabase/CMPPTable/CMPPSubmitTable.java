package LeadTone.CMPPDatabase.CMPPTable;

import LeadTone.Database.*;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Utility;
import LeadTone.Log;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.leadtone.gegw.util.DateUtils;

/**
 * 继承自Table拥有所有JDBC的功能，完成对表CMPPSubmit的操作
 * 具体方法含义参考CMPPDeliverTable类
 */
public class CMPPSubmitTable extends Table
{

    public CMPPSubmitTable()
    {
        //super("cmpp_submit");
        super("smw_cmpp_submit");
    }

    public boolean select(CMPPPacketQueue queue)
        throws SQLException
    {
        boolean bHasData;
        for(bHasData = false; !queue.isFull() && next(); bHasData = true)
        {
            CMPPSubmit submit = new CMPPSubmit(0);
            select(submit);
            queue.push(submit);
        }

        return bHasData;
    }

    public String getParameters(CMPPSubmit submit)
    {
        String strParameters = "";
        strParameters = strParameters + "msg_id,pk_total,pk_number,registered_delivery,msg_level,";
        if(submit.service_id != null && submit.service_id.length() > 0)
            strParameters = strParameters + "service_id,";
        strParameters = strParameters + "fee_user_type,";
        if(submit.fee_terminal_id != null && submit.fee_terminal_id.length() > 0)
            strParameters = strParameters + "fee_terminal_id,";
        strParameters = strParameters + "tp_pid,tp_udhi,msg_fmt,";
        if(submit.msg_src != null && submit.msg_src.length() > 0)
            strParameters = strParameters + "msg_src,";
        if(submit.fee_type != null && submit.fee_type.length() > 0)
            strParameters = strParameters + "fee_type,";
        if(submit.fee_code != null && submit.fee_code.length() > 0)
            strParameters = strParameters + "fee_code,";
        if(submit.valid_time != null && submit.valid_time.length() > 0)
            strParameters = strParameters + "valid_time,";
        if(submit.at_time != null && submit.at_time.length() > 0)
            strParameters = strParameters + "at_time,";
        if(submit.src_terminal_id != null && submit.src_terminal_id.length() > 0)
            strParameters = strParameters + "src_terminal_id,";
        strParameters = strParameters + "dest_usr_tl,";
        if(submit.dest_terminal_id != null && submit.dest_terminal_id.length > 0)
            strParameters = strParameters + "dest_terminal_id,";
        strParameters = strParameters + "msg_length";
        if(submit.msg_content != null && submit.msg_content.length > 0)
            strParameters = strParameters + ",msg_content";
        // 常军添加新对象
        if(submit.apid != null && submit.apid.length() > 0)
        	strParameters = strParameters + ",apid";
        if(submit.srcapid != null && submit.srcapid.length() > 0)
        	strParameters = strParameters + ",srcapid";
        if(submit.masmsgid != null && submit.masmsgid.length() > 0)
        	strParameters = strParameters + ",masmsgid";
        return new String(strParameters);
    }

    public String getValues(CMPPSubmit submit)
    {
        String strValues = "";
        strValues = strValues + "'" + Utility.toHexString(submit.msg_id) + "'," + submit.pk_total + "," + submit.pk_number + "," + submit.registered_delivery + "," + submit.msg_level + ",";
        if(submit.service_id != null && submit.service_id.length() > 0)
            strValues = strValues + "'" + submit.service_id + "',";
        strValues = strValues + submit.fee_user_type + ",";
        if(submit.fee_terminal_id != null && submit.fee_terminal_id.length() > 0)
            strValues = strValues + "'" + submit.fee_terminal_id + "',";
        strValues = strValues + submit.tp_pid + "," + submit.tp_udhi + "," + submit.msg_fmt + ",";
        if(submit.msg_src != null && submit.msg_src.length() > 0)
            strValues = strValues + "'" + submit.msg_src + "',";
        if(submit.fee_type != null && submit.fee_type.length() > 0)
            strValues = strValues + "'" + submit.fee_type + "',";
        if(submit.fee_code != null && submit.fee_code.length() > 0)
            strValues = strValues + "'" + submit.fee_code + "',";
        if(submit.valid_time != null && submit.valid_time.length() > 0)
            strValues = strValues + "'" + submit.valid_time + "',";
        if(submit.at_time != null && submit.at_time.length() > 0)
            strValues = strValues + "'" + submit.at_time + "',";
        if(submit.src_terminal_id != null && submit.src_terminal_id.length() > 0)
            strValues = strValues + "'" + submit.src_terminal_id + "',";
        strValues = strValues + submit.dest_usr_tl + ",";
        if(submit.dest_terminal_id != null && submit.dest_terminal_id.length > 0)
        {
            int length = submit.dest_terminal_id.length;
            strValues = strValues + "'";
            for(int i = 0; i < length; i++)
                strValues = strValues + submit.dest_terminal_id[i] + (i >= length - 1 ? "" : ";");

            strValues = strValues + "',";
        }
        strValues = strValues + submit.msg_length;
        if(submit.msg_content != null && submit.msg_content.length > 0)
            if(m_pool.m_dc.isMSSQLServer() || m_pool.m_dc.isMySQL())
                strValues = strValues + ",0x" + Utility.toHexString(submit.msg_content);
            else
            if(m_pool.m_dc.isOracle())
                strValues = strValues + ",HEXTORAW('" + Utility.toHexString(submit.msg_content) + "')";
            else
                strValues = strValues + ",0x" + Utility.toHexString(submit.msg_content);
        // 常军添加新对象
        if(submit.apid != null && submit.apid.length() > 0)
        	strValues = strValues + "'" + submit.apid + "',";
        if(submit.srcapid != null && submit.srcapid.length() > 0)
        	strValues = strValues + "'" + submit.srcapid + "',";
        if(submit.masmsgid != null && submit.masmsgid.length() > 0)
        	strValues = strValues + "'" + submit.masmsgid + "',";
        return new String(strValues);
    }

    public void insert(CMPPSubmit submit)
        throws SQLException
    {
        String strParameters = getParameters(submit);
        String strValues = getValues(submit);
        if(submit.gateway_name != null && submit.gateway_name.length() > 0)
        {
            strParameters = strParameters + ",ih_gateway";
            strValues = strValues + ",'" + submit.gateway_name + "'";
        }
        insert(strParameters, strValues);
    }

   public int selectIh_retry(CMPPSubmitResponse response) throws SQLException{
       int ih_retry=0;
	   String sqlWhere="";  //select ih_retry from smw_cmpp_submit
	   long id=response.guid;
	   if(m_pool.m_dc.isMySQL() || m_pool.m_dc.isMSSQLServer2000() || m_pool.m_dc.isOracle() || m_pool.m_dc.isDB2())
		   sqlWhere = "id = " + id;
	    else
	    if(m_pool.m_dc.isMSSQLServer65() || m_pool.m_dc.isMSSQLServer70())
	    	sqlWhere = "id = " + (int)(id & -1L);
	    else
	    if(m_pool.m_dc.isODBC())
	    	sqlWhere = "id = " + id;
	    else
	    	sqlWhere = "id = " + id;
		this.select(sqlWhere, "order by id");
		while(this.m_rsCurrent.next()){
			ih_retry=m_rsCurrent.getInt("ih_retry");//getInteger("")
		}
	   return ih_retry;
   }

   public List<CMPPSubmit> select(String sqlWhere) throws SQLException {
	   List<CMPPSubmit> submits=new ArrayList<CMPPSubmit>();
	   select(sqlWhere, " ");
	   while(this.m_rsCurrent.next()){
		   CMPPSubmit submit=new CMPPSubmit(0);
		   select(submit);
		   submits.add(submit);
	   }
	   return submits;
   }

	/**
	 * 查找符合移表备份条件的数据
	 * @param sqlWhere
	 * @return
	 * @throws SQLException
	 */
   public List<CMPPSubmit> select2BackData(String ih_gateway) throws SQLException{
	   String date = null;

	   Long beforeOneHoursDate = System.currentTimeMillis()- 60 * 60 * 1000L;//指定查找一个小时之前的未备份成功的记录
		date = DateUtils.format(new Date(beforeOneHoursDate),"yyyy-MM-dd HH:mm:ss");//ih_timestamp <= ' "+date+" '
	   String sqlWhere="ih_gateway='"+ih_gateway+"' and (ih_process='cmpp_submit_success' or ih_process='cmpp_submit_failure') and ih_timestamp <= ' "+date+" '";
	   List<CMPPSubmit> submits=select(sqlWhere);
	   return submits;
   }
	
   /**
    * 更新符合重置条件的短信记录为初始化状态
    * @throws SQLException
    */
   public void update2RestorData(String ih_gateway) throws SQLException{
	   String date = null;
	   Long beforeOneHoursDate = System.currentTimeMillis()- 60 * 60 * 1000L;//指定查找一个小时之前当前省的未备份成功的记录
	   date = DateUtils.format(new Date(beforeOneHoursDate),"yyyy-MM-dd HH:mm:ss");//
	   String sqlWhere="ih_gateway='"+ih_gateway+"' and ih_timestamp <= ' "+date+" '"+" and ih_process='wait_for_response'";
	   String strUpdate = "ih_process = 'insert_cmpp_submit',ih_retry = " + 3;
	   update(strUpdate, sqlWhere);
   }
    public void select(CMPPSubmit submit)
        throws SQLException
    {
        submit.guid = getLong("id");
        submit.gateway_name = getString("ih_gateway");
        if(submit.gateway_name != null)
            submit.gateway_name = submit.gateway_name.trim();
        submit.session_id = getInteger("ih_session");
        submit.sequence_id = getInteger("ih_retry");
        String msg_id = getString("msg_id");
        if(msg_id != null)
            msg_id = msg_id.trim();
        if(msg_id != null && msg_id.length() <= 0)
            submit.msg_id = Utility.toHexLong(msg_id);
        else
            submit.msg_id = 1L;
        submit.registered_delivery = (byte)(getInteger("registered_delivery") & 0xff);
        submit.msg_level = (byte)(getInteger("msg_level") & 0xff);
        submit.service_id = getString("service_id");
        if(submit.service_id != null)
            submit.service_id = submit.service_id.trim();
        submit.fee_user_type = (byte)(getInteger("fee_user_type") & 0xff);
        submit.fee_terminal_id = getString("fee_terminal_id");
        if(submit.fee_terminal_id != null)
            submit.fee_terminal_id = submit.fee_terminal_id.trim();
        submit.fee_type = getString("fee_type");
        if(submit.fee_type != null)
            submit.fee_type = submit.fee_type.trim();
        submit.fee_code = getString("fee_code");
        if(submit.fee_code != null)
            submit.fee_code = submit.fee_code.trim();
        submit.msg_src = getString("msg_src");
        if(submit.msg_src != null)
            submit.msg_src = submit.msg_src.trim();
        submit.valid_time = getString("valid_time");
        if(submit.valid_time != null)
            submit.valid_time = submit.valid_time.trim();
        submit.at_time = getString("at_time");
        if(submit.at_time != null)
            submit.at_time = submit.at_time.trim();
        submit.src_terminal_id = getString("src_terminal_id");
        if(submit.src_terminal_id != null)
            submit.src_terminal_id = submit.src_terminal_id.trim();
        String dest_terminal_id = getString("dest_terminal_id");
        if(dest_terminal_id != null)
            dest_terminal_id = dest_terminal_id.trim();
        if(dest_terminal_id != null && dest_terminal_id.length() > 0)
        {
            submit.dest_terminal_id = Utility.parseTerminalID(dest_terminal_id);
            submit.dest_usr_tl = (byte)((submit.dest_terminal_id != null ? submit.dest_terminal_id.length : 0) & 0xff);
        } else
        {
            submit.dest_terminal_id = null;
            submit.dest_usr_tl = 0;
        }
        submit.msg_fmt = (byte)(getInteger("msg_fmt") & 0xff);

        if(m_pool.m_dc.isMSSQLServer() || m_pool.m_dc.isMySQL())
            submit.msg_content = Utility.toBytesValue(getString("msg_content"));
        else
        if(m_pool.m_dc.isOracle())
            submit.msg_content = getBytes("msg_content");
        else
            submit.msg_content = Utility.toBytesValue(getString("msg_content"));

        submit.msg_length = submit.msg_content != null ? submit.msg_content.length : 0;
        submit.pk_total = (byte)(getInteger("pk_total") & 0xff);
        submit.pk_number = (byte)(getInteger("pk_number") & 0xff);
        submit.tp_pid = (byte)(getInteger("tp_pid") & 0xff);
        submit.tp_udhi = (byte)(getInteger("tp_udhi") & 0xff);
        submit.agent_flag = (byte)(getInteger("agent_flag") & 0xff);
        submit.mo_relate_to_mt_flag = (byte)(getInteger("mo_relate_to_mt_flag") & 0xff);
        submit.given_value = getString("given_value");
        if(submit.given_value != null)
            submit.given_value = submit.given_value.trim();

    }

    public void update(long id, int retry, String process)
        throws SQLException
    {
        String strUpdate = "ih_process = '" + process + "',ih_retry = " + retry;
        if(m_pool.m_dc.isMySQL())
            strUpdate = strUpdate + ",ih_timestamp = Now()";
        else
        if(m_pool.m_dc.isMSSQLServer())
            strUpdate = strUpdate + ",ih_timestamp = GetDate()";
        else
        if(m_pool.m_dc.isOracle())
            strUpdate = strUpdate + ",ih_timestamp = SYSDATE";
        else
        if(m_pool.m_dc.isDB2())
            strUpdate = strUpdate + ",ih_timestamp = CURRENT TIMESTAMP";
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
        update(strUpdate, strWhere);
    }

    public void update_success(long id, long msg_id, String gateway_name, int session_id)
        throws SQLException
    {
        String strUpdate = "ih_process = 'cmpp_submit_success',ih_result = 0,msg_id = '" + Utility.toHexString(msg_id) + "'";
        if(m_pool.m_dc.isMySQL())
            strUpdate = strUpdate + ",ih_timestamp = Now()";
        else
        if(m_pool.m_dc.isMSSQLServer())
            strUpdate = strUpdate + ",ih_timestamp = GetDate()";
        else
        if(m_pool.m_dc.isOracle())
            strUpdate = strUpdate + ",ih_timestamp = SYSDATE";
        else
        if(m_pool.m_dc.isDB2())
            strUpdate = strUpdate + ",ih_timestamp = CURRENT TIMESTAMP";
        if(gateway_name != null)
            strUpdate = strUpdate + ",ih_gateway = '" + gateway_name + "',ih_session = " + session_id + "";
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
        update(strUpdate, strWhere);
    }

    public void update_failure(long id, int result, String gateway_name, int session_id)
        throws UnsupportedEncodingException, SQLException
    {
        String strUpdate = "ih_process = 'cmpp_submit_failure',ih_result = " + result;
        if(m_pool.m_dc.isMySQL())
            strUpdate = strUpdate + ",ih_timestamp = Now()";
        else
        if(m_pool.m_dc.isMSSQLServer())
            strUpdate = strUpdate + ",ih_timestamp = GetDate()";
        else
        if(m_pool.m_dc.isOracle())
            strUpdate = strUpdate + ",ih_timestamp = SYSDATE";
        else
        if(m_pool.m_dc.isDB2())
            strUpdate = strUpdate + ",ih_timestamp = CURRENT TIMESTAMP";
        if(gateway_name != null)
            strUpdate = strUpdate + ",ih_gateway = '" + gateway_name + "',ih_session = " + session_id;
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
        update(strUpdate, strWhere);
    }


    public void update_resend(long id, long msg_id, String gateway_name, int session_id)
    throws SQLException
{
    String strUpdate = "ih_process = 'insert_cmpp_submit',ih_result = -1,ih_retry=3,msg_id = '0000000000000001'";
    if(m_pool.m_dc.isMySQL())
        strUpdate = strUpdate + ",ih_timestamp = Now()";
    else
    if(m_pool.m_dc.isMSSQLServer())
        strUpdate = strUpdate + ",ih_timestamp = GetDate()";
    else
    if(m_pool.m_dc.isOracle())
        strUpdate = strUpdate + ",ih_timestamp = SYSDATE";
    else
    if(m_pool.m_dc.isDB2())
        strUpdate = strUpdate + ",ih_timestamp = CURRENT TIMESTAMP";
    if(gateway_name != null)
        strUpdate = strUpdate + ",ih_gateway = '" + gateway_name + "',ih_session = " + session_id + "";
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
    update(strUpdate, strWhere);
}

     public void update_final_result(long msg_id, String final_result, String done_time)
        throws SQLException
    {
        String strUpdate = "final_result = '" + final_result + "'";
        if(m_pool.m_dc.isMySQL())
            strUpdate = strUpdate + ",done_time = '"+ done_time +"', final_timestamp = Now()";
        else
        if(m_pool.m_dc.isMSSQLServer())
            strUpdate = strUpdate + ",done_time = '"+ done_time +"', final_timestamp = GetDate()";
        else
        if(m_pool.m_dc.isOracle())
            strUpdate = strUpdate + ",done_time = '"+ done_time +"', final_timestamp = SYSDATE";
        else
        if(m_pool.m_dc.isDB2())
            strUpdate = strUpdate + ",done_time = '"+ done_time +"', final_timestamp = CURRENT TIMESTAMP";

        String strWhere = null;

            strWhere = "msg_id = '"+ Utility.toHexString(msg_id) +"' " ;
        try{
        int count = update(strUpdate, strWhere);
        Log.log("CMPPSubmitTable.update_final_result : success ! " +count+ " rows has been updated !", 0x4000000000000L);
        }catch(Exception e){
        Log.log("CMPPSubmitTable.update_final_result : fail !", 0x4000000000000L);
        Log.log("CMPPSubmitTable.update_final_result : " + e, 0x4000000000000L);
        }

    }

    public void update(CMPPSubmitResponse response)
        throws UnsupportedEncodingException, SQLException
    {
        if(response.result == 0)
            update_success(response.guid, response.msg_id, response.gateway_name, response.session_id);
        else
            update_failure(response.guid, response.result, response.gateway_name, response.session_id);
    }

    public void updateResend(CMPPSubmitResponse response)
        throws UnsupportedEncodingException, SQLException
    {
        update_resend(response.guid, response.msg_id, response.gateway_name, response.session_id);
    }
    
       public void update(CMPPDeliver deliver)
        throws UnsupportedEncodingException, SQLException
    {
            if (deliver.status_report.status != null ){
                update_final_result(deliver.status_report.msg_id, deliver.status_report.status, deliver.status_report.done_time);
            }

    }

     //回应完Deliver Response之后删除CMPP_SUBMIT表中数据
    public int deletenow(CMPPDeliver deliver)
       throws SQLException
    {
       String strCondition = String.valueOf((new StringBuffer(" msg_id = '")).append(Utility.toHexString(deliver.status_report.msg_id)).append("'"));
       return delete(strCondition);
    }

    //收到SubmitResponse之后删除CMPP_SUBMIT表中数据
    public int deletenow(CMPPSubmitResponse response)
       throws SQLException
    {
       String strCondition = String.valueOf((new StringBuffer(" id = ")).append(response.guid));
       return delete(strCondition);
    }
    
    public String selectAccess(CMPPSubmitResponse response){
    	//response.guid, response.result, response.gateway_name, response.session_id
    	setSQL("select src_terminal_id from " + "smw_cmpp_submit " + " where id='" +response.guid + "' ");
        try {
			m_rsCurrent = executeQuery();
			
			return getString("src_terminal_id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
    }
    
    public String selectUserId(String corpAccess,String userExt){
    	setSQL("select id from " + "portal_user" + " where corp_access_number='" +corpAccess + "' and user_ext_code='" + userExt+"'");
        try {
			m_rsCurrent = executeQuery();
			
			return getString("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
    }
    
    public void updateLimit(String userId){
    	m_rsCurrent = null;
        setSQL("update portal_user_ext set  sms_send_count=sms_send_count-1 where sms_limit=1 and id='"+ userId +"' and sms_send_count>0");
        try {
			executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //发送Submit中如果检验Submit格式错误，备份后删除CMPP_SUBMIT表中数据
    public int deletenow(CMPPSubmit submit)
       throws SQLException
    {
       String strCondition = String.valueOf((new StringBuffer(" id = ")).append(submit.guid));
       return delete(strCondition);
    }
}