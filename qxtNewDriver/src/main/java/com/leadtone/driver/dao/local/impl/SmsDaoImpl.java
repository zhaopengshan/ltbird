package com.leadtone.driver.dao.local.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.leadtone.driver.bean.ReceiveResult;
import com.leadtone.driver.bean.ReportResult;
import com.leadtone.driver.bean.SmsBean;
import com.leadtone.driver.dao.local.ISmsDao;
import com.leadtone.util.ErrorCodeUtils;

/**
 * @author limh 
 */

public class SmsDaoImpl implements ISmsDao {
	
	private JdbcTemplate jt;
	private SimpleJdbcTemplate st;

	@Override
	public List<Map<String, Object>> saveZxtDriverSms(final List<SmsBean> list) {
		StringBuffer sql = new StringBuffer("REPLACE INTO smw_qxt_new_submit_result(id , merchant_pin, operation_id, task_number, batch_id,"
				+"province, self_mobile, tos, tos_name, content, cut_apart_number, commit_time,"
				+"ready_send_time, complete_time, sms_access_number, tunnel_type, priority_level,"
				+"send_result, fail_reason, description, create_by,title, webservice, cpoid, del_status )"
				+" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		final List<Map<String, Object>> resultList = new ArrayList();
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
			    ps.setLong(1, list.get(i).getId());
			    ps.setLong(2, list.get(i).getMerchantPin());
			    ps.setLong(3, list.get(i).getOperationId());
			    ps.setString(4, list.get(i).getTaskNumber());
			    ps.setLong(5, list.get(i).getBatchId());
			    //
			    ps.setString(6, list.get(i).getProvince());
			    ps.setString(7, list.get(i).getSelfMobile());
			    ps.setString(8, list.get(i).getTos());
			    ps.setString(9, list.get(i).getTosName());
			    ps.setString(10, list.get(i).getContent());
			    ps.setInt(11, list.get(i).getCutApartNumber());
			    ps.setTimestamp(12, list.get(i).getCommitTime() != null ?(Timestamp)list.get(i).getCommitTime():null );
			    //
			    ps.setTimestamp(13, list.get(i).getReadySendTime() != null ?(Timestamp) list.get(i).getReadySendTime():null);
//			    ps.setInt(14, null);
			    ps.setTimestamp(14, list.get(i).getCompleteTime() != null ? (Timestamp) list.get(i).getCompleteTime():null);
//			    ps.setTimestamp(15, (Timestamp) list.get(i).getCompleteTime());
			    ps.setString(15, list.get(i).getSmsAccessNumber());
			    ps.setInt(16, list.get(i).getTunnelType());
			    ps.setInt(17, list.get(i).getPriorityLevel());
			    //
			    ps.setInt(18, list.get(i).getSendResult());
			    ps.setString(19, list.get(i).getFailReason());
			    ps.setString(20, list.get(i).getDescription());
			    ps.setLong(21, list.get(i).getCreateBy());
			    ps.setString(22, list.get(i).getTitle());
			    ps.setInt(23, list.get(i).getWebservice());
			    ps.setString(24, list.get(i).getCpoid());
			    ps.setInt(25, list.get(i).getDelStatus());
//		    	final HashMap<String, Object> map = new HashMap<String, Object>();
//		    	map.put("id", list.get(i).getId());
//			    map.put("send_result", 1);
//			    resultList.add(map);
			}
        };
        jt.batchUpdate(sql.toString(), setter);
        
        StringBuffer sqlDel = new StringBuffer("DELETE FROM smw_qxt_new_submit where id=?");
    	BatchPreparedStatementSetter setterDel= new BatchPreparedStatementSetter(){
            private int j =0;
            public int getBatchSize(){return list.size();}
			@Override
			public void setValues(PreparedStatement ps, int j)throws SQLException {
			    ps.setLong(1, list.get(j).getId());
			}
        };
        jt.batchUpdate(sqlDel.toString(), setterDel);
        
        return resultList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsBean> getReadySendSms(Long merchantPin, int tunnelType, int sendResult, String readSendTime,int limit, String orderFlag, String createBy, boolean perAccount) {
		try{
			StringBuffer sql = null; 
			if(perAccount){
				sql= new StringBuffer("select * from smw_qxt_new_submit where merchant_pin=? and tunnel_type =? and create_by=? and send_result = ? and ready_send_time < ? order by priority_level desc limit ?");
				List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{merchantPin,tunnelType, createBy, sendResult,readSendTime,limit}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
				return list;
			}else{
				sql= new StringBuffer("select * from smw_qxt_new_submit where merchant_pin=? and tunnel_type =? and send_result = ? and ready_send_time < ? order by priority_level desc limit ?");
				List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{merchantPin,tunnelType, sendResult,readSendTime,limit}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
				return list;
			}
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {
	
		}
	}
	
	@Override
	public void saveHttpSmsRsp(String taskId, Long smsId, Long userId, String status) {
    	String sql = "INSERT INTO sms_http_rsp(task_id,sms_id,user_id,status,final_status) values("+taskId+","+smsId+","+userId+",'"+status+"','wait')";
    	jt.update(sql.toString());	
    }
	
	@Override
	public void updateSmsSendCpoid(final List<Map<String, Object>> list) throws Exception {
		StringBuffer sql = new StringBuffer("UPDATE smw_qxt_new_submit SET send_result = ?, fail_reason = ?, cpoid = ? where id = ?");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
    		private int i =0;
            public int getBatchSize(){return list.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				int sendResult = Integer.parseInt(list.get(i).get("send_result").toString());
			    ps.setInt(1, sendResult);
			    ps.setString(2, list.get(i).get("fail_reason")==null?"":list.get(i).get("fail_reason").toString());
			    ps.setString(3, list.get(i).get("cpoid")==null? "" : list.get(i).get("cpoid").toString() );
			    ps.setLong(4, (Long) list.get(i).get("id"));
			}
        };
        jt.batchUpdate(sql.toString(), setter);
	}
	@Override
	public List<SmsBean> getReportSms(Long merchantPin, final List<ReportResult> resultMap, String uid, boolean perAccount){
		try{
			//report dodo
			if(resultMap!=null&&resultMap.size()>0){
				StringBuffer sql = new StringBuffer("UPDATE smw_qxt_new_submit SET send_result = ?, fail_reason = ?, complete_time= ? where cpoid = ? and tos = ?");
		    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
		    		private int i =0;
		            public int getBatchSize(){return resultMap.size();}
		            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					@Override
					public void setValues(PreparedStatement ps, int i)throws SQLException {
						ReportResult tempBean = resultMap.get(i);
					    ps.setInt(1, tempBean.getStatus().equalsIgnoreCase("10")?2:3);//10 ok; 20 failure
					    ps.setString(2, tempBean.getErrorCode());
					    ps.setString(3, df.format(new Date()));
					    ps.setString(4, tempBean.getTaskId());
					    ps.setString(5, tempBean.getMobile());
					}
		        };
		        jt.batchUpdate(sql.toString(), setter);
			}
			//TODO
			if(perAccount){
				StringBuffer sqlSelect= new StringBuffer("select * from smw_qxt_new_submit where merchant_pin = ? and sms_access_number = ? and send_result in ('2','3')");
				List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sqlSelect.toString(), new Object[]{merchantPin, uid}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
				return list;
			}else{
				StringBuffer sqlSelect= new StringBuffer("select * from smw_qxt_new_submit where merchant_pin = ? and send_result in ('2','3')");
				List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sqlSelect.toString(), new Object[]{merchantPin}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
				return list;
			}
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {
	
		}
	}
	@Override
	public void updateSmsSendReceive(final List<ReceiveResult> list) throws Exception {
		StringBuffer sql = new StringBuffer("INSERT INTO smw_qxt_new_mo(merchant_pin, phone, content, datetime, user_id, cpoid ) values(?,?,?,?,?,?)");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				ReceiveResult tempBean = list.get(i);
			    ps.setLong(1, tempBean.getMerchantPin());
			    ps.setString(2, tempBean.getMobile());
			    ps.setString(3, tempBean.getContent());
			    ps.setString(4, tempBean.getReceivetime());
			    ps.setLong(5, Long.parseLong(tempBean.getUserId()));
			    ps.setString(6, tempBean.getTaskid());
			}
        };
        jt.batchUpdate(sql.toString(), setter);
	}
	public JdbcTemplate getJt() {
		return jt;
	}

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	public SimpleJdbcTemplate getSt() {
		return st;
	}

	public void setSt(SimpleJdbcTemplate st) {
		this.st = st;
	}

}
