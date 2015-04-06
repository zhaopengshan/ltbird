package com.leadtone.sender.dao.modem.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.leadtone.sender.bean.GatewaySmsBean;
import com.leadtone.sender.bean.ModemSmsBean;
import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.bean.SmsLimitBean;
import com.leadtone.sender.dao.local.ISmsDao;
import com.leadtone.sender.dao.modem.IModemSmsDao;

/**
 * @author limh 
 */

public class SmsDaoImpl implements IModemSmsDao {
	
	private JdbcTemplate jt;
	private SimpleJdbcTemplate st;

	@Override
	public void updateQxtMoRestlt(final List<SmsBean> list){
		StringBuffer sql = new StringBuffer("UPDATE smw_qxt_submit_result SET process_status = ? where id = ?;");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
    	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				SmsBean tempBean = list.get(i);
			    ps.setInt(1, 1);
			    ps.setLong(2, tempBean.getId());
			}
        };
        jt.batchUpdate(sql.toString(), setter);
	}
	@Override
	public List<SmsBean> getQxtSmsResult(int limit){
		try{
			StringBuffer sql= new StringBuffer("select * from smw_qxt_submit_result where process_status=? limit ?");
			List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{0, limit}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
			return list;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {
		}
	}
	@Override
    public void deleteSendedSms(final List<Map<String, Object>> list){
    	StringBuffer sql = new StringBuffer("DELETE FROM mbn_sms_had_send WHERE id = ?;");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
			    ps.setLong(1, (Long) list.get(i).get("id"));
			}
        };
        jt.batchUpdate(sql.toString(), setter);
	}

	@Override
	public List getModemSmsResult(int limit) {
		return jt.queryForList("SELECT id,send_result FROM mbn_sms_had_send limit ?",new Object[]{limit});
	}
	
	@Override
	public List getProvince() {
		return jt.queryForList("SELECT province FROM mbn_sms_ready_send WHERE tunnel_type = 0 and send_result = 0 GROUP BY province");
	}
	
	@Override
	public List getMerchantPinByProvince(String province) {
		return jt.queryForList("SELECT merchant_pin FROM mbn_sms_ready_send WHERE tunnel_type = 0 and send_result = 0 and province =? GROUP BY merchant_pin",new Object[]{province});
	}
	
	@Override
	public List<Map<String, Object>> saveQxtDriverSms(final List<SmsBean> list) {
		StringBuffer sql = new StringBuffer("INSERT INTO smw_qxt_submit(id , merchant_pin, operation_id, task_number, batch_id,"
				+"province, self_mobile, tos, tos_name, content, cut_apart_number, commit_time,"
				+"ready_send_time, complete_time, sms_access_number, tunnel_type, priority_level,"
				+"send_result, fail_reason, description, create_by,title, webservice, del_status )"
				+" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			    ps.setInt(24, list.get(i).getDelStatus());
		    	final HashMap<String, Object> map = new HashMap<String, Object>();
		    	map.put("id", list.get(i).getId());
			    map.put("send_result", 1);
			    resultList.add(map);
			}
        };
        jt.batchUpdate(sql.toString(), setter);
        return resultList;
	}
	
    public SmsLimitBean getLimitByMerchantPin(Long merchantPin){
		return(SmsLimitBean) jt.queryForObject("SELECT * FROM mbn_merchant_sms_mms_limit WHERE merchant_pin=?",new Object[]{merchantPin},new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				SmsLimitBean bean = new SmsLimitBean();
				bean.setMerchantPin(rs.getLong("merchant_pin"));
				bean.setModifyTime(rs.getDate("modify_time"));
				bean.setSmsGatewayCurrentMonth(rs.getInt("sms_gateway_current_month"));
				bean.setSmsGatewayDaily(rs.getInt("sms_gateway_daily"));
				bean.setSmsGatewayIntraday(rs.getInt("sms_gateway_intraday"));
				bean.setSmsGatewayMonth(rs.getInt("sms_gateway_month"));
				bean.setSmsTdCurrentDaily(rs.getInt("sms_td_current_daily"));
				bean.setSmsTdCurrentHour(rs.getInt("sms_td_current_hour"));
				bean.setSmsTdDaily(rs.getInt("sms_td_daily"));
				bean.setSmsTdHour(rs.getInt("sms_td_hour"));
				return bean;
			}});
    }
    
	@Override
    public List<Map<String, Object>> saveModemSms(final List<ModemSmsBean> list){
    	StringBuffer sql = new StringBuffer("INSERT INTO mbn_sms_ready_send(id,self_mobile,tos,content,description,commit_time,merchant_pin,province,operation_id,batch_id,ready_send_time,tunnel_type,priority_level,send_result) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
    	final List<Map<String, Object>> resultList = new ArrayList();
    	
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
			    ps.setLong(1, list.get(i).getId());
			    ps.setString(2, list.get(i).getSelfMobile());
			    ps.setString(3, list.get(i).getTos());
			    ps.setString(4, list.get(i).getContent());
			    ps.setString(5, list.get(i).getDescription());
				String commitTime = df.format(list.get(i).getCommitTime());
			    ps.setString(6, commitTime);
			    ps.setLong(7, list.get(i).getMerchantPin());
			    ps.setString(8,list.get(i).getProvince());
			    ps.setLong(9, list.get(i).getOperationId());
			    ps.setLong(10, list.get(i).getBatchId());
			    String readySendTime = df.format(list.get(i).getReadySendTime());
			    ps.setString(11, readySendTime);
			    ps.setInt(12, list.get(i).getTunnelType());
			    ps.setInt(13, list.get(i).getPriorityLevel());
			    ps.setInt(14, list.get(i).getSendResult());
			    final Map<String, Object> map = new HashMap<String, Object>();
			    map.put("id", list.get(i).getId());
			    map.put("send_result", 1);
			    resultList.add(map);
			}
        };
        jt.batchUpdate(sql.toString(), setter);
        return resultList;
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
