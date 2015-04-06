package empp2.dao;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import empp2.util.ByteUtil;
import com.wondertek.esmp.esms.empp.EMPPDeliver;
import com.wondertek.esmp.esms.empp.EMPPDeliverReport;
import com.wondertek.esmp.esms.empp.EMPPShortMsg;
import com.wondertek.esmp.esms.empp.EMPPSubmitSMResp;

import empp2.Log;


public class SmsEmppSubmitDao extends BaseDao {

	private PlatformTransactionManager transactionManager;
	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
   
	public List<HashMap<String, Object>> querySubmitSms() {
		final List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	
		String sql = "select id, msg_id, pk_total, pk_number, registered_delivery, msg_level, service_id, fee_user_type, fee_terminal_id,"
				+ " tp_pid, tp_udhi, msg_fmt, msg_src, fee_type, fee_code, valid_time, at_time, src_terminal_id, dest_usr_tl, dest_terminal_id,"
				+ " msg_length, msg_content, ih_process, ih_result, ih_retry, ih_gateway, ih_session, ih_timestamp, agent_flag, mo_relate_to_mt_flag,"
				+ " given_value, sms_type, sms_cid, final_result, done_time, cef_group_id, " 
				+ " mas_sms_id, linkID, fee_terminal_type, dest_terminal_type from smw_empp_submit where "
				+ " ih_process = 'insert_cmpp_submit' and final_result = 'UNRETUN' "
				+ " order by msg_level DESC,id ASC limit 1";
	
		_jt.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				HashMap<String, Object> row = new HashMap<String, Object>();
				row.put("id", rs.getString("id"));										row.put("msg_id", rs.getString("msg_id"));
				row.put("pk_total", rs.getString("pk_total"));							row.put("pk_number", rs.getString("pk_number"));
				row.put("registered_delivery", rs.getString("registered_delivery"));	row.put("msg_level", rs.getString("msg_level"));
				row.put("service_id", rs.getString("service_id"));						row.put("fee_user_type", rs.getString("fee_user_type"));
				row.put("fee_terminal_id", rs.getString("fee_terminal_id"));			row.put("tp_pid", rs.getString("tp_pid"));
				row.put("tp_udhi", rs.getString("tp_udhi"));							row.put("msg_fmt", rs.getString("msg_fmt"));
				row.put("msg_src", rs.getString("msg_src"));							row.put("fee_type", rs.getString("fee_type"));
				row.put("fee_code", rs.getString("fee_code"));							row.put("valid_time", rs.getString("valid_time"));
				row.put("at_time", rs.getString("at_time"));							row.put("src_terminal_id", rs.getString("src_terminal_id"));
				row.put("dest_usr_tl", rs.getString("dest_usr_tl"));					row.put("dest_terminal_id", rs.getString("dest_terminal_id"));
				row.put("msg_length", rs.getString("msg_length"));						row.put("msg_content", rs.getString("msg_content"));
				row.put("ih_process", rs.getString("ih_process"));						row.put("ih_result", rs.getString("ih_result"));
				row.put("ih_retry", rs.getString("ih_retry"));							row.put("ih_gateway", rs.getString("ih_gateway"));
				row.put("ih_session", rs.getString("ih_session"));						row.put("ih_timestamp", rs.getString("ih_timestamp"));
				row.put("agent_flag", rs.getString("agent_flag"));						row.put("mo_relate_to_mt_flag", rs.getString("mo_relate_to_mt_flag"));
				row.put("given_value", rs.getString("given_value"));					row.put("sms_type", rs.getString("sms_type"));
				row.put("sms_cid", rs.getString("sms_cid"));							row.put("final_result", rs.getString("final_result"));
//				row.put("final_timestamp", rs.getString("final_timestamp"));
				row.put("done_time", rs.getString("done_time"));
				row.put("cef_group_id", rs.getString("cef_group_id"));					
				row.put("mas_sms_id", rs.getString("mas_sms_id"));						row.put("linkID", rs.getString("linkID"));
				row.put("fee_terminal_type", rs.getString("fee_terminal_type"));		row.put("dest_terminal_type", rs.getString("dest_terminal_type"));
				
				list.add(row);
//				Object[] object = new Object[3];
//				int i = 1;
//				object[0] = rs.getString("id");
//				object[1] = rs.getString("dest_terminal_id");
//				object[2] = rs.getString("msg_content");
//				
			}
		});
		return list;
	}
	
	public void updateSubmitFailure(final HashMap<String, Object> object) {
	   try{
		   String sql = "update smw_empp_submit set ih_process = '"+ object.get("ih_process").toString() +"' where id = " + object.get("id").toString();
			_jt.execute(sql);
	   }catch(DataAccessException ex){
		   Log.log(ex.getMessage(), 0x2000000000000000L);
           Log.log(ex);
           Log.log("DataAccessException: unepected!", 0x2000000000000000L);
	   }
	}
	
	public void updateSubmitSmsReply(final HashMap<String, Object> object) {
		//transactionDefinition是表示事务的一些属性 如隔离级别等
	   DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	   //首先获得当前的事务，需要一个事务定义类（规定隔离级别等属性的一个类）
	   //根据指定的属性创造一个新事务实例.
	   TransactionStatus status = transactionManager.getTransaction(def);    
	   try{
		   String sql = "update smw_empp_submit set ih_process = '"+ object.get("ih_process").toString() +"' where id = " + object.get("id").toString();
			_jt.execute(sql);
			
			String replySql = "insert into smw_empp_submit_init(id, msg_id, pk_total, pk_number, registered_delivery, msg_level, service_id, fee_user_type, fee_terminal_id,"
					+ " tp_pid, tp_udhi, msg_fmt, msg_src, fee_type, fee_code, valid_time, at_time, src_terminal_id, dest_usr_tl, dest_terminal_id,"
					+ " msg_length, msg_content, ih_process, ih_result, ih_retry, ih_gateway, ih_session, ih_timestamp, agent_flag, mo_relate_to_mt_flag,"
					+ " given_value, sms_type, sms_cid, final_result, final_timestamp, done_time, cef_group_id, " 
					+ " mas_sms_id, linkID, fee_terminal_type, dest_terminal_type, sequence_number)"
			+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			_jt.update(replySql, new PreparedStatementSetter() {
				public void setValues(PreparedStatement ps) throws SQLException {
					int i = 1;
					ps.setLong(i++, Long.parseLong( object.get("id").toString()) ); ps.setString(i++, object.get("msg_id").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("pk_total").toString() ));ps.setInt(i++, Integer.parseInt( object.get("pk_number").toString() ));
					ps.setInt(i++, Integer.parseInt( object.get("registered_delivery").toString() ));ps.setInt(i++, Integer.parseInt( object.get("msg_level").toString() ));
					ps.setString(i++, object.get("service_id").toString() );ps.setInt(i++, Integer.parseInt( object.get("fee_user_type").toString() ));
					ps.setString(i++, object.get("fee_terminal_id").toString() );ps.setInt(i++, Integer.parseInt( object.get("tp_pid").toString() ));
					ps.setInt(i++, Integer.parseInt( object.get("tp_udhi").toString() ));ps.setInt(i++, Integer.parseInt( object.get("msg_fmt").toString() ));
					ps.setString(i++, object.get("msg_src").toString() ); ps.setString(i++, object.get("fee_type").toString() );
					ps.setString(i++, object.get("fee_code").toString() );ps.setString(i++, object.get("valid_time")==null?null:object.get("valid_time").toString() );
					ps.setString(i++, object.get("at_time") == null?null:object.get("at_time").toString() );ps.setString(i++, object.get("src_terminal_id").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("dest_usr_tl").toString() ));ps.setString(i++, object.get("dest_terminal_id").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("msg_length").toString() ));ps.setString(i++, object.get("msg_content").toString() );
					ps.setString(i++, object.get("ih_process").toString() );ps.setInt(i++, Integer.parseInt( object.get("ih_result").toString() ));
					ps.setInt(i++, Integer.parseInt( object.get("ih_retry").toString() ));ps.setString(i++, object.get("ih_gateway").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("ih_session").toString() ));
//					ps.setObject(i++, object.get("ih_timestamp"));
					ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
					ps.setInt(i++, Integer.parseInt( object.get("agent_flag").toString() ));ps.setInt(i++, Integer.parseInt( object.get("mo_relate_to_mt_flag").toString() ));
					ps.setString(i++, object.get("given_value").toString() );ps.setString(i++, object.get("sms_type").toString() );
					ps.setString(i++, object.get("sms_cid").toString() );ps.setString(i++, object.get("final_result").toString() );
//					ps.setObject(i++, object.get("final_timestamp"));
					ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
					ps.setString(i++, object.get("done_time") ==null?null:object.get("done_time").toString() );ps.setString(i++, object.get("cef_group_id").toString() );
					ps.setLong(i++, Long.parseLong( object.get("mas_sms_id").toString()) );ps.setString(i++, object.get("linkID")==null?null:object.get("linkID").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("fee_terminal_type").toString() ));ps.setInt(i++, Integer.parseInt( object.get("dest_terminal_type").toString() ));
					ps.setLong(i++, Long.parseLong( object.get("sequence_number").toString()) );
				}
			});
			transactionManager.commit(status);
	   	}catch(DataAccessException ex)
	   	{
	   		transactionManager.rollback(status);
	   		Log.log(ex.getMessage(), 0x2000000000000000L);
	        Log.log(ex);
	        Log.log("DataAccessException: unepected!", 0x2000000000000000L);
	   	}
	}
	
	public void updateSubmitReply(final EMPPSubmitSMResp resp, final BigInteger msgId){//int sequenceNumber, final BigInteger msgId, final int result) {
//		//transactionDefinition是表示事务的一些属性 如隔离级别等
//		   DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		   //首先获得当前的事务，需要一个事务定义类（规定隔离级别等属性的一个类）
//		   //根据指定的属性创造一个新事务实例.
//		   TransactionStatus status = transactionManager.getTransaction(def);
		   final int result = resp.getResult();
		   final int sequenceNumber = resp.getSequenceNumber();
		   
		   try{
			   String replySql = "insert into smw_empp_submit_reply(msg_id, ih_process, ih_result, final_result, sequence_number)"
				+ " values(?,?,?,?,?)";
				_jt.update(replySql, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						int i = 1;
						ps.setString(i++, msgId.toString() );
						if(result == 0){
							ps.setString(i++, "cmpp_submit_success");
						}else{
							ps.setString(i++, "cmpp_submit_failure");
						}
						ps.setInt(i++, result);
						ps.setString(i++, "'UNRETUN'");
						ps.setLong(i++, sequenceNumber );
					}
				});
//				transactionManager.commit(status);
		   	}catch(DataAccessException ex)
		   	{
//		   		transactionManager.rollback(status);
		   		Log.log(ex.getMessage(), 0x2000000000000000L);
		        Log.log(ex);
		        Log.log("DataAccessException: unepected!", 0x2000000000000000L);
		   	}
		   
//		   try{
//			   String sql ="";
//			   if(result==0){
//				   sql = "update smw_empp_submit_reply set ih_process='cmpp_submit_success', msg_id = '"+msgId.toString()
//							+"' where msg_id = '0000000000000001' and ih_process = 'wait_for_response' and sequence_number = "+sequenceNumber ;
//					_jt.execute(sql);
//	        	}else{
//	        	   sql = "update smw_empp_submit_reply set ih_process='cmpp_submit_failure', ih_result="+result+", msg_id = '"+msgId.toString()
//							+"' where msg_id = '0000000000000001' and ih_process = 'wait_for_response' and sequence_number = "+sequenceNumber ;
//					_jt.execute(sql);
//	        	}
//			   
//				
//				//更新会submit 表 msgid
//				String getId = "select id from smw_empp_submit_reply where msg_id = '"+msgId.toString()+"'";
//				_jt.query(getId, new RowCallbackHandler() {
//					public void processRow(ResultSet rs) throws SQLException {
//						String report = "";
//						if(result==0){
//							report = "update smw_cmpp_submit set ih_process='cmpp_submit_success', msg_id = '" + msgId.toString()
//									+"'  where id = " + rs.getString("id");
//			        	}else{
//			        		report = "update smw_cmpp_submit set ih_process='cmpp_submit_failure', ih_result="+result+", msg_id = '" + msgId.toString()
//									+"'  where id = " + rs.getString("id");
//			        	}
//						_jt.execute(report);
//					}
//				});
////				transactionManager.commit(status);
//		   	}catch(DataAccessException ex)
//		   	{
////		   		transactionManager.rollback(status);
//		   		ex.printStackTrace();
//		   	}
	}
	
	public void updateSubmitReport(final BigInteger msgId, final String finalResult) {
		final HashMap<String, Object> row = new HashMap<String, Object>();
		try{
		    //report mark
			String sql = "update smw_empp_submit_reply set final_result='"+finalResult+"' where msg_id = '"+msgId.toString()+"'";
			_jt.execute(sql);
			
			String sqlSelect = "select init.id as id, reply.msg_id as msg_id, reply.ih_process as ih_process, reply.ih_result as ih_result" 
					+ " from smw_empp_submit_reply as reply, smw_empp_submit_init as init where "
					+ " reply.msg_id = '"+msgId.toString()+"' and reply.sequence_number = init.sequence_number";
			
			_jt.query(sqlSelect, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					row.put("id", rs.getString("id"));
					String report = "update smw_empp_submit set final_result='"+finalResult+"', msg_id = '" +msgId.toString()
							+ "', ih_process = '"+ rs.getString("ih_process") +"', ih_result = '"+ rs.getInt("ih_result") +"'" 
							+ " where id = '"+rs.getString("id").toString()+"'";
					_jt.execute(report);
				}
			});
	   	}catch(DataAccessException ex)
	   	{
	   		ex.printStackTrace();
	   	}
		if(row.get("id")!=null){
			HashMap<String, Object> object = this.querySubmitSms(row.get("id").toString());
			try{
				if(object !=null){
					insertSubmitSmsResult(object);
					removeSubmitSms(row.get("id").toString());
				}
		   	}catch(DataAccessException ex)
		   	{
		   		Log.log(ex.getMessage(), 0x2000000000000000L);
		        Log.log(ex);
		        Log.log("DataAccessException: unepected!", 0x2000000000000000L);
		   	}
		}
	}
	public void removeSubmitSms(String smsId){
		String sql = "delete from smw_empp_submit where id = " + smsId;
		_jt.execute(sql);
	}
	public void initStartUp(){
		String sqlInit = "delete from smw_empp_submit_init";
		_jt.execute(sqlInit);
	}
	public void insertSubmitSmsResult(final HashMap<String, Object> object) {
	   try{
			String replySql = "insert into smw_empp_submit_result(id, msg_id, pk_total, pk_number, registered_delivery, msg_level, service_id, fee_user_type, fee_terminal_id,"
					+ " tp_pid, tp_udhi, msg_fmt, msg_src, fee_type, fee_code, valid_time, at_time, src_terminal_id, dest_usr_tl, dest_terminal_id,"
					+ " msg_length, msg_content, ih_process, ih_result, ih_retry, ih_gateway, ih_session, ih_timestamp, agent_flag, mo_relate_to_mt_flag,"
					+ " given_value, sms_type, sms_cid, final_result, final_timestamp, done_time, cef_group_id, " 
					+ " mas_sms_id, linkID, fee_terminal_type, dest_terminal_type)"
			+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			_jt.update(replySql, new PreparedStatementSetter() {
				public void setValues(PreparedStatement ps) throws SQLException {
					int i = 1;
					ps.setLong(i++, Long.parseLong( object.get("id").toString()) ); ps.setString(i++, object.get("msg_id").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("pk_total").toString() ));ps.setInt(i++, Integer.parseInt( object.get("pk_number").toString() ));
					ps.setInt(i++, Integer.parseInt( object.get("registered_delivery").toString() ));ps.setInt(i++, Integer.parseInt( object.get("msg_level").toString() ));
					ps.setString(i++, object.get("service_id").toString() );ps.setInt(i++, Integer.parseInt( object.get("fee_user_type").toString() ));
					ps.setString(i++, object.get("fee_terminal_id").toString() );ps.setInt(i++, Integer.parseInt( object.get("tp_pid").toString() ));
					ps.setInt(i++, Integer.parseInt( object.get("tp_udhi").toString() ));ps.setInt(i++, Integer.parseInt( object.get("msg_fmt").toString() ));
					ps.setString(i++, object.get("msg_src").toString() ); ps.setString(i++, object.get("fee_type").toString() );
					ps.setString(i++, object.get("fee_code").toString() );ps.setString(i++, object.get("valid_time")==null?null:object.get("valid_time").toString() );
					ps.setString(i++, object.get("at_time") == null?null:object.get("at_time").toString() );ps.setString(i++, object.get("src_terminal_id").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("dest_usr_tl").toString() ));ps.setString(i++, object.get("dest_terminal_id").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("msg_length").toString() ));ps.setString(i++, object.get("msg_content").toString() );
					ps.setString(i++, object.get("ih_process").toString() );ps.setInt(i++, Integer.parseInt( object.get("ih_result").toString() ));
					ps.setInt(i++, Integer.parseInt( object.get("ih_retry").toString() ));ps.setString(i++, object.get("ih_gateway").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("ih_session").toString() ));
//					ps.setObject(i++, object.get("ih_timestamp"));
					ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
					ps.setInt(i++, Integer.parseInt( object.get("agent_flag").toString() ));ps.setInt(i++, Integer.parseInt( object.get("mo_relate_to_mt_flag").toString() ));
					ps.setString(i++, object.get("given_value").toString() );ps.setString(i++, object.get("sms_type").toString() );
					ps.setString(i++, object.get("sms_cid").toString() );ps.setString(i++, object.get("final_result").toString() );
//					ps.setObject(i++, object.get("final_timestamp"));
					ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
					ps.setString(i++, object.get("done_time") ==null?null:object.get("done_time").toString() );ps.setString(i++, object.get("cef_group_id").toString() );
					ps.setLong(i++, Long.parseLong( object.get("mas_sms_id").toString()) );ps.setString(i++, object.get("linkID")==null?null:object.get("linkID").toString() );
					ps.setInt(i++, Integer.parseInt( object.get("fee_terminal_type").toString() ));ps.setInt(i++, Integer.parseInt( object.get("dest_terminal_type").toString() ));
				}
			});
	   	}catch(DataAccessException ex)
	   	{
	   		Log.log(ex.getMessage(), 0x2000000000000000L);
	        Log.log(ex);
	        Log.log("DataAccessException: unepected!", 0x2000000000000000L);
	   	}
	}
	
	public HashMap<String, Object> querySubmitSms(String smsId) {
		try{
			final HashMap<String, Object> row = new HashMap<String, Object>();
			String sql = "select id, msg_id, pk_total, pk_number, registered_delivery, msg_level, service_id, fee_user_type, fee_terminal_id,"
					+ " tp_pid, tp_udhi, msg_fmt, msg_src, fee_type, fee_code, valid_time, at_time, src_terminal_id, dest_usr_tl, dest_terminal_id,"
					+ " msg_length, msg_content, ih_process, ih_result, ih_retry, ih_gateway, ih_session, ih_timestamp, agent_flag, mo_relate_to_mt_flag,"
					+ " given_value, sms_type, sms_cid, final_result, done_time, cef_group_id, " 
					+ " mas_sms_id, linkID, fee_terminal_type, dest_terminal_type from smw_empp_submit where  id='"+smsId+"'";
		
			_jt.query(sql, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					row.put("id", rs.getString("id"));										row.put("msg_id", rs.getString("msg_id"));
					row.put("pk_total", rs.getString("pk_total"));							row.put("pk_number", rs.getString("pk_number"));
					row.put("registered_delivery", rs.getString("registered_delivery"));	row.put("msg_level", rs.getString("msg_level"));
					row.put("service_id", rs.getString("service_id"));						row.put("fee_user_type", rs.getString("fee_user_type"));
					row.put("fee_terminal_id", rs.getString("fee_terminal_id"));			row.put("tp_pid", rs.getString("tp_pid"));
					row.put("tp_udhi", rs.getString("tp_udhi"));							row.put("msg_fmt", rs.getString("msg_fmt"));
					row.put("msg_src", rs.getString("msg_src"));							row.put("fee_type", rs.getString("fee_type"));
					row.put("fee_code", rs.getString("fee_code"));							row.put("valid_time", rs.getString("valid_time"));
					row.put("at_time", rs.getString("at_time"));							row.put("src_terminal_id", rs.getString("src_terminal_id"));
					row.put("dest_usr_tl", rs.getString("dest_usr_tl"));					row.put("dest_terminal_id", rs.getString("dest_terminal_id"));
					row.put("msg_length", rs.getString("msg_length"));						row.put("msg_content", rs.getString("msg_content"));
					row.put("ih_process", rs.getString("ih_process"));						row.put("ih_result", rs.getString("ih_result"));
					row.put("ih_retry", rs.getString("ih_retry"));							row.put("ih_gateway", rs.getString("ih_gateway"));
					row.put("ih_session", rs.getString("ih_session"));						row.put("ih_timestamp", rs.getString("ih_timestamp"));
					row.put("agent_flag", rs.getString("agent_flag"));						row.put("mo_relate_to_mt_flag", rs.getString("mo_relate_to_mt_flag"));
					row.put("given_value", rs.getString("given_value"));					row.put("sms_type", rs.getString("sms_type"));
					row.put("sms_cid", rs.getString("sms_cid"));							row.put("final_result", rs.getString("final_result"));
	//				row.put("final_timestamp", rs.getString("final_timestamp"));
					row.put("done_time", rs.getString("done_time"));
					row.put("cef_group_id", rs.getString("cef_group_id"));					
					row.put("mas_sms_id", rs.getString("mas_sms_id"));						row.put("linkID", rs.getString("linkID"));
					row.put("fee_terminal_type", rs.getString("fee_terminal_type"));		row.put("dest_terminal_type", rs.getString("dest_terminal_type"));
				}
			});
			return row;
		}catch(Exception e){
			Log.log(e.getMessage(), 0x2000000000000000L);
	        Log.log(e);
	        Log.log("DataAccessException: unepected!", 0x2000000000000000L);
			return null;
		}
	}
	
	public void deliverSave(final EMPPDeliver deliver) {
		try{
			String replySql = "insert into smw_empp_deliver(msg_id, destination_id, service_id, tp_pid, tp_udhi, msg_fmt, src_terminal_id, registered_delivery,"
					+ " msg_length, msg_content, smsc_sequence, "
					+ " notice_status, notice_count, linkID, fee_terminal_type, dest_terminal_type)"
			+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			_jt.update(replySql, new PreparedStatementSetter() {
				public void setValues(PreparedStatement ps) throws SQLException {
					int i = 1;
					ps.setString(i++,  new BigInteger( deliver.getMsgId() ).toString() ); 
					ps.setString(i++, deliver.getDstAddr() );
					ps.setString(i++, deliver.getServiceId());
					ps.setInt(i++, new Integer( deliver.getTpPid() ));
					ps.setInt(i++, new Integer( deliver.getTpUdhi() ));
					ps.setInt(i++, new Integer( deliver.getMsgFmt() ));
					ps.setString(i++, deliver.getSrcTermId() );
					ps.setInt(i++, new Integer( deliver.getRegister() ));
					ps.setInt(i++, deliver.getMsgLen() );
					EMPPShortMsg emppShortMsg = deliver.getMsgContent();
//					String contentStr = "";
//					try {
//						byte[] dataBytes = emppShortMsg.getMessage().getBytes("UnicodeBigUnmarked");
//						System.out.println("emppShortMsg.getMessage():"+emppShortMsg.getMessage());
//						contentStr = ByteUtil.byte2hex(dataBytes);
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//					}
					
					ps.setString(i++, emppShortMsg.getMessage() ); 
					//msg_mr
//					ps.setString(i++,  );
					//status
//					ps.setString(i++, deliver.getDeliverReport().getStat() );
					//submit_time
//					ps.setString(i++, deliver.getDeliverReport().getSubTime());
					//done_time
//					ps.setString(i++, deliver.getDeliverReport().getDoneTime() );
					//dest_terminal_id
//					ps.setString(i++, deliver.getEMPPResponse().getOriginalRequest().get);
					//smsc_sequence
					ps.setInt(i++, deliver.getSequenceNumber() );
					//ih_process
//					ps.setString(i++, deliver.getDeliverReport().getStat() );
					//ih_gateway
//					ps.setString(i++,  deliver.getEMPPResponse().getEmppHost());
					//ih_session
//					ps.setInt(i++, deliver.get);
					//ih_sequence
//					ps.setInt(i++, 0);
					//ih_timestamp
//					ps.setTimestamp(i++,  new Timestamp(System.currentTimeMillis()) );
					//notice_status
					ps.setInt(i++, 0);
					//notice_count
					ps.setInt(i++, 0);
					//linkID
					ps.setString(i++, deliver.getLinkId() );
					//fee_terminal_type
					ps.setInt(i++, 0);
					//dest_terminal_type
					ps.setInt(i++, 0);
				}
			});
			
			final HashMap<String, Object> row = new HashMap<String, Object>();
			String sql = "select id from smw_empp_deliver where  msg_id = '"+new BigInteger( deliver.getMsgId() ).toString()+"'";
			_jt.query(sql, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					row.put("id", rs.getString("id"));
				}
			});
			
			if( row !=null && !row.isEmpty()){
				String deliverResultSql = "insert into smw_empp_deliver_result(id, msg_id, destination_id, service_id, tp_pid, tp_udhi, msg_fmt, src_terminal_id, registered_delivery,"
						+ " msg_length, msg_content, smsc_sequence, "
						+ " notice_status, notice_count, linkID, fee_terminal_type, dest_terminal_type)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				_jt.update(deliverResultSql, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						int i = 1;
						ps.setLong(i++, Long.parseLong( row.get("id").toString() ));
						ps.setString(i++,  new BigInteger( deliver.getMsgId() ).toString() ); 
						ps.setString(i++, deliver.getDstAddr() );
						ps.setString(i++, deliver.getServiceId());
						ps.setInt(i++, new Integer( deliver.getTpPid() ));
						ps.setInt(i++, new Integer( deliver.getTpUdhi() ));
						ps.setInt(i++, new Integer( deliver.getMsgFmt() ));
						ps.setString(i++, deliver.getSrcTermId() );
						ps.setInt(i++, new Integer( deliver.getRegister() ));
						ps.setInt(i++, deliver.getMsgLen() );
						EMPPShortMsg emppShortMsg = deliver.getMsgContent();
//						String contentStr = "";
//						try {
//							byte[] dataBytes = emppShortMsg.getMessage().getBytes("UnicodeBigUnmarked");
//							System.out.println("emppShortMsg.getMessage():"+emppShortMsg.getMessage());
//							contentStr = ByteUtil.byte2hex(dataBytes);
//						} catch (UnsupportedEncodingException e) {
//							e.printStackTrace();
//						}
						ps.setString(i++, emppShortMsg.getMessage() ); 
						ps.setInt(i++, deliver.getSequenceNumber() );
						ps.setInt(i++, 0);
						//notice_count
						ps.setInt(i++, 0);
						//linkID
						ps.setString(i++, deliver.getLinkId() );
						//fee_terminal_type
						ps.setInt(i++, 0);
						//dest_terminal_type
						ps.setInt(i++, 0);
					}
				});
				
				String sqlDelete = "delete from smw_empp_deliver where id = " + row.get("id").toString();
				_jt.execute(sqlDelete);
			}
	   	}catch(DataAccessException ex)
	   	{
	   		Log.log(ex.getMessage(), 0x2000000000000000L);
	        Log.log(ex);
	        Log.log("DataAccessException: unepected!", 0x2000000000000000L);
	   	}
	}
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
//	public int updateStatus(String strUpdate, String strCondition)
//  throws SQLException
//{
//String sql = "insert into smw_cmpp_submit(pk_total,pk_number,registered_delivery,msg_level,service_id,fee_terminal_id,tp_pid,"
//		+ "tp_udhi,msg_fmt,msg_src,valid_time,src_terminal_id,dest_usr_tl,dest_terminal_id,msg_length,msg_content,"
//		+ "ih_gateway,apid,srcapid,masmsgid,transationid)"
//		+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//	return _jt.update(sql, new PreparedStatementSetter() {
//		public void setValues(PreparedStatement ps) throws SQLException {
//			int i = 1;
//		}
//	});
//}
//	public List<Object[]> queryMMSACK() {
//		final List<Object[]> list = new ArrayList<Object[]>();
//
//		//if (smsIf != null) {
//			// ,, 
//			String sql = "select ih_process, dest_addr,masmsgid,id,gwmsgid from mbn_mms_outbox_status where ack=0 and ( ih_process='SUBMITOK' or ih_process='SUBMITERROR')";
//			_jt.query(sql, new RowCallbackHandler() {
//				public void processRow(ResultSet rs) throws SQLException {
//					Object[] object = new Object[6];
//					int i = 1;
//					object[0] = rs.getString("ih_process");
//					object[1] = rs.getString("dest_addr");
//					object[2] = rs.getString("masmsgid");
//					object[3] = rs.getString("id");
//					object[4] = rs.getString("gwmsgid");
//
//					list.add(object);
//				}
//			});
//		//}
//
//		return list;
//	}
//	
//	public List<Object[]> queryMMSRPT() {
//		final List<Object[]> list = new ArrayList<Object[]>();
//
//		//if (smsIf != null) {
//			// ,, 
//			String sql = "select final_result,dest_addr,sender_addr,gwmsgid,id from mbn_mms_outbox_status where rpt=0 and ack = 1 and ( final_result='DELIVRD' or final_result='UNDELIV')";
//			_jt.query(sql, new RowCallbackHandler() {
//				public void processRow(ResultSet rs) throws SQLException {
//					Object[] object = new Object[7];
//					int i = 1;
//					object[0] = rs.getString("final_result");
//					object[1] = rs.getString("sender_addr");
//					object[2] = rs.getString("dest_addr");
//					object[3] = rs.getString("gwmsgid");
//					object[4] = rs.getString("id");
//
//					list.add(object);
//				}
//			});
//		//}
//
//		return list;
//	}
//	
//	public int countMsgTotal() {
//			String sql = "select count(*) from smw_cmpp_submit where ih_process = 'insert_cmpp_submit'";
//			return _jt.queryForInt(sql);
//	}
//	
//	public void updateMMSAck(int id) {
//		
//		String sql = "update mbn_mms_outbox_status set ack = 1 where id = " + id;
//
//		_jt.execute(sql);
//	}
//
//	public void updateMMSRPT(int id) {
//		
//		String sql = "update mbn_mms_outbox_status set rpt = 1 where id = " + id;
//
//		_jt.execute(sql);
//	}
}
