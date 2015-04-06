package com.leadtone.sender.dao.local.impl;

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

import com.leadtone.sender.bean.GatewaySmsBean;
import com.leadtone.sender.bean.MbnSmsInbox;
import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.bean.SmsLimitBean;
import com.leadtone.sender.bean.UTcomGatewaySmsBean;
import com.leadtone.sender.bean.User;
import com.leadtone.sender.bean.ZxtReceiveBean;
import com.leadtone.sender.dao.local.ISmsDao;
import com.leadtone.util.ErrorCodeUtils;

/**
 * @author limh 
 */

public class SmsDaoImpl implements ISmsDao {
	
	private JdbcTemplate jt;
	private SimpleJdbcTemplate st;


	@Override
	public void saveHttpSmsRsp(String taskId, Long smsId, Long userId, String status) {
    	String sql = "INSERT INTO sms_http_rsp(task_id,sms_id,user_id,status,final_status) values("+taskId+","+smsId+","+userId+",'"+status+"','wait')";
    	jt.update(sql.toString());	
    }
	
	@Override
	public void savePukerSmsRsp(String taskId, Long smsId) {
    	String sql = "INSERT INTO mbn_sms_ready_puke_rel(ready_id,puke_id) values("+smsId+","+taskId+")";
    	jt.update(sql.toString());	
    }
	@Override
	public List getsmsHttpRsp(Long taskId) {
		return jt.queryForList("SELECT sms_id FROM sms_http_rsp where task_id = ? ",new Object[]{taskId});
	}
	
    @Override
    public void updateSmsHttp(Long taskId, String final_status) {
    	String sql = "UPDATE sms_http_rsp SET final_status = ? where task_id = ?;";
    	jt.update(sql.toString(), new Object[]{final_status,taskId});
    }
	
	@Override
	public List getGatewaySmsResult(int limit) {
		return jt.queryForList("SELECT mas_sms_id as id,IF (ih_process='cmpp_submit_success' AND(final_result='DELIVRD' OR final_result='UNRETUN'),2,3) as send_result, final_result FROM smw_cmpp_submit_result where final_result <>'GETTED' limit ?",new Object[]{limit});
	}
	@Override
	public List getEmppSmsResult(int limit) {
		return jt.queryForList("SELECT mas_sms_id as id,IF (ih_process='cmpp_submit_success' AND(final_result='DELIVRD' OR final_result='UNRETUN'),2,3) as send_result, final_result FROM smw_empp_submit_result where final_result <>'GETTED' limit ?",new Object[]{limit});
	}
	@Override
	public List getLtdxGatewaySmsResult(int limit) {
		return this.jt.queryForList("SELECT mas_sms_id as id,IF (ih_process='submit_success' AND(final_result='DELIVRD' OR final_result='UNRETUN'),2,3) as send_result, final_result FROM smw_sms_submit_result where final_result <>'GETTED' limit ?", new Object[] { Integer.valueOf(limit) });
	}
	
	@Override
	public List getHttpSmsResult(int limit) {
		return jt.queryForList("SELECT user_id FROM sms_http_rsp where final_status ='wait' group by user_id limit ?",new Object[]{limit});
	}
	
	@Override
	public List getProvinceByTunnelType(int tunnelType, int sendResult, String readSendTime) {
		//tunnel_type:0托管猫，2网关
		return jt.queryForList("SELECT province FROM mbn_sms_ready_send WHERE tunnel_type = ? and send_result = ? and ready_send_time < ? GROUP BY province",new Object[]{tunnelType,sendResult,readSendTime});
	}
	
	@Override
	public List getMerchantPinList(int tunnelType,int sendResult, String province, String readSendTime) {
		return jt.queryForList("SELECT merchant_pin FROM mbn_sms_ready_send WHERE tunnel_type = ? and send_result = ? and province =? and ready_send_time < ?  GROUP BY merchant_pin",new Object[]{tunnelType,sendResult,province,readSendTime});
	}
	
	@Override
	public List getMerchantPinList(int sendResult, String readSendTime) {
		return jt.queryForList("SELECT merchant_pin, province FROM mbn_sms_ready_send WHERE send_result = ? and ready_send_time < ?  GROUP BY merchant_pin",new Object[]{sendResult,readSendTime});
	}
	
	@Override
	public List getMerchantPriorityList(Long merchantPin, int tunnelType,int sendResult, String province, String readSendTime) {
		return jt.queryForList("SELECT priority_level FROM mbn_sms_ready_send WHERE merchant_pin=? and tunnel_type = ? and send_result = ? and province =? and ready_send_time < ?  GROUP BY priority_level ORDER BY priority_level DESC",new Object[]{merchantPin, tunnelType,sendResult,province,readSendTime});
	}
	
	@Override
	public List getMerchantPriorityBatchList(Long merchantPin,
			int tunnelType, int sendResult, String readSendTime,
			int priority) {
    	try{
    		
    		return jt.queryForList("select batch_id from mbn_sms_ready_send where merchant_pin=? and priority_level = ? and tunnel_type =? and send_result = ? and ready_send_time < ? group by batch_id",
    				new Object[]{merchantPin,priority,tunnelType,sendResult,readSendTime});
    		
//			StringBuffer sql= new StringBuffer("select batch_id from mbn_sms_ready_send where merchant_pin=? and priority_level = ? and tunnel_type =? and send_result = ? and ready_send_time < ? group by batch_id");
//			List<Long> list = (List<Long>) st.getJdbcOperations().query(sql.toString(), new Object[]{merchantPin,priority,tunnelType,sendResult,readSendTime}, ParameterizedBeanPropertyRowMapper.newInstance(Long.class));
//			return list;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {

		}
	}
	
	@Override
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
				bean.setGatewayModifyTime(rs.getDate("gateway_modify_time"));
				return bean;
			}});
    }
	@Override
	public List<ZxtReceiveBean> getZxtMoBean(int limit){
			try{
				int status = 0;
				StringBuffer sql= new StringBuffer("select * from smw_zxt_mo where proc_status = ? limit ?");
				List<ZxtReceiveBean> list = (List<ZxtReceiveBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{status, limit}, ParameterizedBeanPropertyRowMapper.newInstance(ZxtReceiveBean.class));
				return list;
			} catch (org.springframework.dao.DataAccessException e) {
				e.printStackTrace();
				return null;
			}finally {

			}
	}

	@Override
    public List<Map<String, Object>> saveGatewaySms(final List<GatewaySmsBean> list){
    	StringBuffer sqlv = new StringBuffer("INSERT INTO smw_cmpp_submit(pk_total,pk_number,service_id,fee_terminal_id,tp_udhi,msg_fmt,msg_src,fee_type,src_terminal_id,dest_usr_tl,dest_terminal_id,msg_length,msg_content,ih_result,ih_gateway,ih_session,ih_timestamp,mas_sms_id,fee_user_type,registered_delivery) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
    	StringBuffer sqlv5 = new StringBuffer("INSERT INTO smw_cmpp_submit(pk_total,pk_number,service_id,fee_terminal_id,tp_udhi,msg_fmt,msg_src,fee_type,src_terminal_id,dest_usr_tl,dest_terminal_id,msg_length,msg_content,ih_result,ih_gateway,ih_session,ih_timestamp,mas_sms_id,fee_user_type,registered_delivery,id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
    	final List<Map<String, Object>> resultList = new ArrayList();
    	final List<GatewaySmsBean> listv = new ArrayList();
    	final List<GatewaySmsBean> listv5 = new ArrayList();
    	if(list!=null){
    		for(GatewaySmsBean gwSmsBean:list){
    			if(gwSmsBean.getMsgLevel() == 5){
    				listv5.add(gwSmsBean);
    			}else{
    				listv.add(gwSmsBean);
    			}
    		}
    	}
    	BatchPreparedStatementSetter setterv= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return listv.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
			    ps.setInt(1, listv.get(i).getPkTotal());
			    ps.setInt(2, listv.get(i).getPkNumber());
			    ps.setString(3, listv.get(i).getServiceId());
			    ps.setString(4, listv.get(i).getFeeTerminalId());
			    ps.setInt(5, listv.get(i).getTpUdhi());
			    ps.setInt(6, listv.get(i).getMsgFmt());
			    ps.setString(7, listv.get(i).getMsgSrc());
			    ps.setString(8, listv.get(i).getFeeType());
			    ps.setString(9, listv.get(i).getSrcTerminalId());
			    ps.setInt(10, listv.get(i).getDestUsrTl());
			    ps.setString(11, listv.get(i).getDestTerminalId());
			    ps.setInt(12, listv.get(i).getMsgLength());
			    ps.setString(13, listv.get(i).getMsgContent());
			    ps.setInt(14, listv.get(i).getIhResult());
			    ps.setString(15, listv.get(i).getIhGateway());
			    ps.setInt(16, listv.get(i).getIhSession());
			    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String commitTime = df.format(listv.get(i).getIhTimestamp());
			    ps.setString(17,commitTime);
			    ps.setLong(18, listv.get(i).getMasSmsId());
			    ps.setInt(19, listv.get(i).getFeeUserType());
			    ps.setInt(20, listv.get(i).getRegisteredDelivery());
		    	final Map<String, Object> map = new HashMap<String, Object>();
		    	map.put("id", listv.get(i).getMasSmsId());
			    map.put("send_result", 1);
			    resultList.add(map);
			}
        };
        jt.batchUpdate(sqlv.toString(), setterv);
        BatchPreparedStatementSetter setterv5= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return listv5.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
			    ps.setInt(1, listv5.get(i).getPkTotal());
			    ps.setInt(2, listv5.get(i).getPkNumber());
			    ps.setString(3, listv5.get(i).getServiceId());
			    ps.setString(4, listv5.get(i).getFeeTerminalId());
			    ps.setInt(5, listv5.get(i).getTpUdhi());
			    ps.setInt(6, listv5.get(i).getMsgFmt());
			    ps.setString(7, listv5.get(i).getMsgSrc());
			    ps.setString(8, listv5.get(i).getFeeType());
			    ps.setString(9, listv5.get(i).getSrcTerminalId());
			    ps.setInt(10, listv5.get(i).getDestUsrTl());
			    ps.setString(11, listv5.get(i).getDestTerminalId());
			    ps.setInt(12, listv5.get(i).getMsgLength());
			    ps.setString(13, listv5.get(i).getMsgContent());
			    ps.setInt(14, listv5.get(i).getIhResult());
			    ps.setString(15, listv5.get(i).getIhGateway());
			    ps.setInt(16, listv5.get(i).getIhSession());
			    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String commitTime = df.format(listv5.get(i).getIhTimestamp());
			    ps.setString(17,commitTime);
			    ps.setLong(18, listv5.get(i).getMasSmsId());
			    ps.setInt(19, listv5.get(i).getFeeUserType());
			    ps.setInt(20, listv5.get(i).getRegisteredDelivery());
			    ps.setLong(21, 1L);
		    	final Map<String, Object> map = new HashMap<String, Object>();
		    	map.put("id", listv5.get(i).getMasSmsId());
			    map.put("send_result", 1);
			    resultList.add(map);
			}
        };
        jt.batchUpdate(sqlv5.toString(), setterv5);
        return resultList;
    }
	
	@Override
    public List<Map<String, Object>> saveEmppSms(final List<GatewaySmsBean> list){
    	StringBuffer sql = new StringBuffer("INSERT INTO smw_empp_submit(pk_total,pk_number,service_id,fee_terminal_id,tp_udhi,msg_fmt,msg_src,fee_type,src_terminal_id,dest_usr_tl,dest_terminal_id,msg_length,msg_content,ih_result,ih_gateway,ih_session,ih_timestamp,mas_sms_id,fee_user_type,registered_delivery) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
    	final List<Map<String, Object>> resultList = new ArrayList();
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
			    ps.setInt(1, list.get(i).getPkTotal());
			    ps.setInt(2, list.get(i).getPkNumber());
			    ps.setString(3, list.get(i).getServiceId());
			    ps.setString(4, list.get(i).getFeeTerminalId());
			    ps.setInt(5, list.get(i).getTpUdhi());
			    ps.setInt(6, list.get(i).getMsgFmt());
			    ps.setString(7, list.get(i).getMsgSrc());
			    ps.setString(8, list.get(i).getFeeType());
			    ps.setString(9, list.get(i).getSrcTerminalId());
			    ps.setInt(10, list.get(i).getDestUsrTl());
			    ps.setString(11, list.get(i).getDestTerminalId());
			    ps.setInt(12, list.get(i).getMsgLength());
			    ps.setString(13, list.get(i).getMsgContent());
			    ps.setInt(14, list.get(i).getIhResult());
			    ps.setString(15, list.get(i).getIhGateway());
			    ps.setInt(16, list.get(i).getIhSession());
			    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String commitTime = df.format(list.get(i).getIhTimestamp());
			    ps.setString(17,commitTime);
			    ps.setLong(18, list.get(i).getMasSmsId());
			    ps.setInt(19, list.get(i).getFeeUserType());
			    ps.setInt(20, list.get(i).getRegisteredDelivery());
		    	final Map<String, Object> map = new HashMap<String, Object>();
		    	map.put("id", list.get(i).getMasSmsId());
			    map.put("send_result", 1);
			    resultList.add(map);
			}
        };
        jt.batchUpdate(sql.toString(), setter);
        return resultList;
    }
	
	@Override
	public List<Map<String, Object>> saveUTcomGatewaySms(final List<UTcomGatewaySmsBean> list){
    	StringBuffer sql = new StringBuffer("INSERT INTO smw_sms_submit(mas_sms_id,pk_total,pk_number,service_id,tp_udhi,src_term_id,dest_term_id_count,dest_term_id,msg_content,msg_length) values(?,?,?,?,?,?,?,?,?,?);");
    	final List<Map<String, Object>> resultList = new ArrayList();
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
			    ps.setLong(1, list.get(i).getMasSmsId());
			    ps.setInt(2, list.get(i).getPkTotal());
			    ps.setInt(3, list.get(i).getPkNumber());
			    ps.setString(4, list.get(i).getServiceId());
			    ps.setInt(5, list.get(i).getTpUdhi());
			    ps.setString(6, list.get(i).getSrcTerminalId());
			    ps.setInt(7, 1);
			    ps.setString(8, list.get(i).getDestTerminalId());
			    ps.setString(9, list.get(i).getMsgContent());
			    ps.setInt(10, 0);
		    	final HashMap<String, Object> map = new HashMap<String, Object>();
		    	map.put("id", list.get(i).getMasSmsId());
			    map.put("send_result", 1);
			    resultList.add(map);
			}
        };
        jt.batchUpdate(sql.toString(), setter);
        return resultList;
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
	@Override
	public List<Map<String, Object>> saveQxtNewDriverSms(final List<SmsBean> list) {
		StringBuffer sql = new StringBuffer("INSERT INTO smw_qxt_new_submit(id , merchant_pin, operation_id, task_number, batch_id,"
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
	@Override
	public List<SmsBean> getQxtNewSmsResult(int limit){
		try{
			StringBuffer sql= new StringBuffer("select * from smw_qxt_new_submit_result where process_status=? limit ?");
			List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{0, limit}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
			return list;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {
		}
	}
	@Override
	public List<Map<String, Object>> saveZxtDriverSms(final List<SmsBean> list) {
		StringBuffer sql = new StringBuffer("INSERT INTO smw_zxt_submit(id , merchant_pin, operation_id, task_number, batch_id,"
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
	public void updateQxtNewProcRestlt(final List<SmsBean> list){
		StringBuffer sql = new StringBuffer("UPDATE smw_qxt_new_submit_result SET process_status = ? where id = ?;");
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
	public void updateSmsSendRestlt(final List<Map<String, Object>> list) {
    	StringBuffer sql = new StringBuffer("UPDATE mbn_sms_ready_send SET send_result = ?, fail_reason = ?, complete_time = ? where id = ?;");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
    	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				int sendResult = Integer.parseInt(list.get(i).get("send_result").toString());
			    ps.setInt(1, sendResult);
			    String final_result = null;
			    if( list.get(i).get("final_result") != null){
			    	final_result = list.get(i).get("final_result").toString();
			    }
			    if( sendResult == 3){
			    	ps.setString(2, ErrorCodeUtils.getErrorDesc(final_result));
			    }else{
			    	ps.setString(2, null);
			    }
			    ps.setString(3, df.format(new Date()));
			    ps.setLong(4, (Long) list.get(i).get("id"));
			}
        };
        jt.batchUpdate(sql.toString(), setter);
    }
	@Override
	public void updateSmsSendCancel(final List<SmsBean> list) {
    	StringBuffer sql = new StringBuffer("UPDATE mbn_sms_ready_send SET send_result = ?, fail_reason = ?, complete_time = ? where id = ?;");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
    	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				SmsBean tempBean = list.get(i);
			    ps.setInt(1, -1);
			    ps.setString(2, "取消发送");
			    ps.setString(3, df.format(new Date()));
			    ps.setLong(4, tempBean.getId());
			}
        };
        jt.batchUpdate(sql.toString(), setter);
    }
	
	@Override
	public void updateZxtSmsSendRestlt(final List<SmsBean> list) {
    	StringBuffer sql = new StringBuffer("UPDATE mbn_sms_ready_send SET send_result = ?, fail_reason = ?, complete_time = ? where id = ?;");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
    	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				SmsBean tempBean = list.get(i);
				int sendResult = tempBean.getFailReason().equalsIgnoreCase("DELIVRD")?2:3;
			    ps.setInt(1, sendResult);
			    if( sendResult == 3){
			    	ps.setString(2, ErrorCodeUtils.getErrorDesc(tempBean.getFailReason()));
			    }else{
			    	ps.setString(2, null);
			    }
			    ps.setString(3, tempBean.getCompleteTime()==null ? df.format(new Date()) : df.format(tempBean.getCompleteTime()));
			    ps.setLong(4, tempBean.getId());
			}
        };
        jt.batchUpdate(sql.toString(), setter);
    }
	@Override
	public void updateQxtSmsSendRestlt(final List<SmsBean> list) {
    	StringBuffer sql = new StringBuffer("UPDATE mbn_sms_ready_send SET send_result = ?, fail_reason = ?, complete_time = ?, cpoid=? where id = ?;");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
    	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				SmsBean tempBean = list.get(i);
				int sendResult = tempBean.getSendResult();
			    ps.setInt(1, sendResult);
			    if( sendResult == 3){
			    	ps.setString(2, tempBean.getFailReason());
			    }else{
			    	ps.setString(2, null);
			    }
			    ps.setString(3, df.format(new Date()));
			    ps.setString(4, tempBean.getCpoid());
			    ps.setLong(5, tempBean.getId());
			    
			}
        };
        jt.batchUpdate(sql.toString(), setter);
    }
	
	@Override
	public void updateQxtNewSmsSendRestlt(final List<SmsBean> list) {
    	StringBuffer sql = new StringBuffer("UPDATE mbn_sms_ready_send SET send_result = ?, fail_reason = ?, complete_time = ?, cpoid=? where id = ?;");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
    	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				SmsBean tempBean = list.get(i);
				int sendResult = tempBean.getSendResult();
			    ps.setInt(1, sendResult);
			    if( sendResult == 3){
			    	ps.setString(2, tempBean.getFailReason());
			    }else{
			    	ps.setString(2, null);
			    }
			    ps.setString(3, df.format(new Date()));
			    ps.setString(4, tempBean.getCpoid());
			    ps.setLong(5, tempBean.getId());
			    
			}
        };
        jt.batchUpdate(sql.toString(), setter);
    }
	
	@Override
	public void updateZxtMoRestlt(final List<SmsBean> list){
		StringBuffer sql = new StringBuffer("UPDATE smw_zxt_submit_result SET process_status = ? where id = ?;");
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
	
    @SuppressWarnings("unchecked")
	@Override
	public List<SmsBean> getReadySendSms(Long merchantPin, int tunnelType, int sendResult, String readSendTime,int limit, String orderFlag) {
		try{
			StringBuffer sql= new StringBuffer("select * from mbn_sms_ready_send where merchant_pin=? and tunnel_type =? and send_result = ? and ready_send_time < ? order by priority_level desc limit ?");
			List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{merchantPin,tunnelType,sendResult,readSendTime,limit}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
			return list;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {

		}
	}
    
	@Override
	public List<SmsBean> getReadySendSms(Long merchantPin, int sendResult,
			String readSendTime, int limit) {
		try{
			StringBuffer sql= new StringBuffer("select * from mbn_sms_ready_send where merchant_pin=? and send_result = ? and ready_send_time < ? order by priority_level desc limit ?");
			List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{merchantPin, sendResult, readSendTime, limit}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
			return list;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {

		}
	}
	
	@Override
	public List<SmsBean> getZxtSmsResult(int limit){
		try{
			StringBuffer sql= new StringBuffer("select * from smw_zxt_submit_result where process_status=? limit ?");
			List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{0, limit}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
			return list;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {

		}
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
	public List<SmsBean> getReadySendSmsByPriority(Long merchantPin,
			int tunnelType, int sendResult, String readSendTime, int limit,
			int priority) {
    	try{
			StringBuffer sql= new StringBuffer("select * from mbn_sms_ready_send where merchant_pin=? and priority_level = ? and tunnel_type =? and send_result = ? and ready_send_time < ? order by priority_level desc limit ?");
			List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{merchantPin,priority,tunnelType,sendResult,readSendTime,limit}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
			return list;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {

		}
	}
    
    @Override
	public List<SmsBean> getReadySendSmsByPriorityBatch(Long merchantPin,
			int tunnelType, int sendResult, String readSendTime, int limit,
			int priority, Long batchId) {
    	try{
			StringBuffer sql= new StringBuffer("select * from mbn_sms_ready_send where merchant_pin=? and priority_level = ? and batch_id = ? and tunnel_type =? and send_result = ? and ready_send_time < ? order by priority_level desc limit ?");
			List<SmsBean> list = (List<SmsBean>) st.getJdbcOperations().query(sql.toString(), new Object[]{merchantPin,priority, batchId, tunnelType,sendResult,readSendTime,limit}, ParameterizedBeanPropertyRowMapper.newInstance(SmsBean.class));
			return list;
		} catch (org.springframework.dao.DataAccessException e) {
			e.printStackTrace();
			return null;
		}finally {

		}
	}
        
    @Override
    public void updateMerchantGatewayLimit(Long merchantPin, int currentDay, int currentMonth) {
    	String sql = "UPDATE mbn_merchant_sms_mms_limit SET sms_gateway_intraday = ?,sms_gateway_current_month = ?,gateway_modify_time = ? where merchant_pin = ?;";
    	jt.update(sql.toString(), new Object[]{currentDay,currentMonth,new Date(),merchantPin});
    }
    
    @Override
    public void butchInsertGatewaySms(String values) {
    	String sql = "INSERT INTO smw_cmpp_submit(pk_total,pk_number,service_id,fee_terminal_id,tp_udhi,msg_fmt,msg_src,fee_type,src_terminal_id,dest_usr_tl,dest_terminal_id,msg_length,msg_content,ih_result,ih_gateway,ih_session,ih_timestamp,mas_sms_id,fee_user_type,registered_delivery) values";
    	sql = sql+values;
    	jt.update(sql.toString());
    }
    
    @Override
    public void updateMerchantModemLimit(Long merchantPin, int currenthour, int currentDay) {
    	String sql = "UPDATE mbn_merchant_sms_mms_limit SET sms_td_current_daily = ?,sms_td_current_hour = ?,modify_time = ? where merchant_pin = ?;";
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	jt.update(sql.toString(), new Object[]{currenthour,currentDay,new Date(),merchantPin});
    }
    
    @Override
	public void updateGatewaySms(final List<Map<String, Object>> list){
    	StringBuffer sql = new StringBuffer("UPDATE smw_cmpp_submit_result SET final_result = ? where mas_sms_id = ?;");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
			    ps.setString(1, "GETTED");
			    ps.setLong(2, (Long) list.get(i).get("id"));
			}
        };
        jt.batchUpdate(sql.toString(), setter);
	}
    @Override
	public void updateEmppSms(final List<Map<String, Object>> list){
    	StringBuffer sql = new StringBuffer("UPDATE smw_empp_submit_result SET final_result = ? where mas_sms_id = ?;");
    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
            private int i =0;
            public int getBatchSize(){return list.size();}
			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
			    ps.setString(1, "GETTED");
			    ps.setLong(2, (Long) list.get(i).get("id"));
			}
        };
        jt.batchUpdate(sql.toString(), setter);
	}

    @Override
   	public void updateLtdxGatewaySms(final List<Map<String, Object>> list){
       	StringBuffer sql = new StringBuffer("UPDATE smw_sms_submit_result SET final_result = ? where mas_sms_id = ?;");
       	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
               private int i =0;
               public int getBatchSize(){return list.size();}
   			@Override
   			public void setValues(PreparedStatement ps, int i)throws SQLException {
   			    ps.setString(1, "GETTED");
   			    ps.setLong(2, (Long) list.get(i).get("id"));
   			}
           };
           jt.batchUpdate(sql.toString(), setter);
   	}
    
    @Override
   	public void updateZxtMoBean(final List<ZxtReceiveBean> list){
       	StringBuffer sql = new StringBuffer("UPDATE smw_zxt_mo SET proc_status = ? where id = ?;");
       	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
               private int i =0;
               public int getBatchSize(){return list.size();}
   			@Override
   			public void setValues(PreparedStatement ps, int i)throws SQLException {
   			    ps.setInt(1, 1);
   			    ps.setInt(2, list.get(i).getId());
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

	@Override
	public User getUserByZXTId(String zxtUserId) {
		return(User) jt.queryForObject("SELECT * FROM portal_user WHERE zxt_id=?",new Object[]{zxtUserId},new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				User bean = new User();
				bean.setId(rs.getLong("id"));
				bean.setMerchant_pin(rs.getLong("merchant_pin"));
				bean.setLogin_account(rs.getString("login_account"));
				bean.setLogin_pwd(rs.getString("login_pwd"));
				bean.setZxt_login_acount(rs.getString("zxt_login_acount"));
				bean.setZxt_pwd(rs.getString("zxt_pwd"));
				bean.setZxt_id(rs.getInt("zxt_id"));
				return bean;
			}});
	}
	
	public List<User> getUsers() {
		return(List<User>) jt.query("SELECT * FROM portal_user ",new Object[]{},new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				User bean = new User();
				bean.setId(rs.getLong("id"));
				bean.setMerchant_pin(rs.getLong("merchant_pin"));
				bean.setLogin_account(rs.getString("login_account"));
				bean.setLogin_pwd(rs.getString("login_pwd"));
				bean.setZxt_login_acount(rs.getString("zxt_login_acount"));
				bean.setZxt_pwd(rs.getString("zxt_pwd"));
				bean.setZxt_id(rs.getInt("zxt_id"));
				return bean;
			}});
	}

	public Integer addUpSms(Long id,String sender,String receiver,String content,Timestamp createTime){
		
		String sql = "INSERT INTO mbn_sms_up_comm_log(id,sender_mobile,receiver_access_number,content,create_time,status,webstatus) values('"+id+"','"+sender+"','"+receiver+"','"+content+"','"+createTime+ "','"+"0','0'"+")";
    	return jt.update(sql.toString());
		
	}
	
	public void getBackLimit(String userId){
		String sql ="update portal_user_ext set  sms_send_count=sms_send_count-1 where sms_limit=1 and id=? and sms_send_count>0";
	   jt.update(sql.toString(), new Object[]{userId});
	}

	@Override
	public void saveZxtMoToInbox(final List<MbnSmsInbox> list){
		try{
	    	StringBuffer sql = new StringBuffer("INSERT INTO mbn_sms_inbox(id,status,merchant_pin,sender_mobile,"
			+"receiver_access_number,operation_coding,content,receive_time,sender_name,webservice,reply_batch_id) values(?,?,?,?,?,?,?,?,?,?,?)");
	    	BatchPreparedStatementSetter setter= new BatchPreparedStatementSetter(){
	            private int i =0;
	            public int getBatchSize(){return list.size();}
				@Override
				public void setValues(PreparedStatement ps, int i)throws SQLException {
					MbnSmsInbox temp = list.get(i);
				    ps.setLong(1, temp.getId());
				    ps.setInt(2, temp.getStatus());
				    ps.setLong(3, temp.getMerchantPin());
				    ps.setString(4, temp.getSenderMobile());
				    ps.setString(5, temp.getReceiverAccessNumber());
				    ps.setString(6, temp.getOperationId());
				    ps.setString(7, temp.getContent());
				    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    String commitTime = df.format(new Date());
				    System.out.print(temp.getReceiveTime() != null && temp.getReceiveTime().length() > 19 ? temp.getReceiveTime().substring(0, 19) :commitTime);
				    ps.setString(8, temp.getReceiveTime() != null && temp.getReceiveTime().length() > 19 ? temp.getReceiveTime().substring(0, 19) :commitTime);
				    ps.setString(9, temp.getSenderName());
				    ps.setInt(10, temp.getWebService());
				    ps.setLong(11, temp.getReplyBatchId());
				}
	        };
	        jt.batchUpdate(sql.toString(), setter);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
