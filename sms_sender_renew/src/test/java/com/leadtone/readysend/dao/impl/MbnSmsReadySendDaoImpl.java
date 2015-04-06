package com.leadtone.readysend.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.leadtone.readysend.bean.MbnSmsReadySend;
import com.leadtone.readysend.dao.MbnSmsReadySendDao;

public class MbnSmsReadySendDaoImpl implements MbnSmsReadySendDao {
	
	private JdbcTemplate jt;
	private SimpleJdbcTemplate st;
	
	@Override
	public void save(final MbnSmsReadySend mbnSmsReadySend) {
		StringBuffer sql = new StringBuffer("INSERT INTO mbn_sms_ready_send(id , merchant_pin, operation_id,batch_id,"
				+"province, self_mobile, tos, tos_name, content, cut_apart_number, commit_time,"
				+"ready_send_time, expire_time,complete_time, sms_access_number, tunnel_type, priority_level,"
				+"send_result, fail_reason, description, create_by,title,task_number,webservice )"
				+" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		
		Object[] params=new Object[]{
			mbnSmsReadySend.getId(),
			mbnSmsReadySend.getMerchantPin(),
			mbnSmsReadySend.getOperationId(),
			mbnSmsReadySend.getBatchId(),
			mbnSmsReadySend.getProvince(),
			mbnSmsReadySend.getSelfMobile(),
			mbnSmsReadySend.getTos(),
			mbnSmsReadySend.getTosName(),
			mbnSmsReadySend.getContent(),
			mbnSmsReadySend.getCutApartNumber(),
			mbnSmsReadySend.getCommitTime(),
			mbnSmsReadySend.getReadySendTime(),
			mbnSmsReadySend.getExpireTime(),
			mbnSmsReadySend.getCompleteTime(),
			mbnSmsReadySend.getSmsAccessNumber(),
			mbnSmsReadySend.getTunnelType(),
			mbnSmsReadySend.getPriorityLevel(),
			mbnSmsReadySend.getSendResult(),
			mbnSmsReadySend.getFailReason(),
			mbnSmsReadySend.getDescription(),
			mbnSmsReadySend.getCreateBy(),
			mbnSmsReadySend.getTitle(),
			mbnSmsReadySend.getTaskNumber(),
			mbnSmsReadySend.getWebService()
	    };
		int[] types=new int[]{
			Types.BIGINT, Types.BIGINT, Types.BIGINT, Types.BIGINT,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.INTEGER, Types.DATE, Types.DATE, Types.INTEGER, Types.DATE,
			Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR,
			Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.INTEGER
		};
		jt.update(sql.toString(), params, types);
//		PreparedStatementSetter preparedStatementSetter = new PreparedStatementSetter(){
//			@Override
//			public void setValues(PreparedStatement ps) throws SQLException {
//		           ps.setLong(1, mbnSmsReadySend.getId());
//		           ps.setInt(2, mbnSmsReadySend.getPriorityLevel());
//		    }
//		};
//		jt.update(sql.toString(), preparedStatementSetter);
		
//		PreparedStatementCreator PreparedStatementCreator = new PreparedStatementCreator(){
//			@Override
//			public PreparedStatement createPreparedStatement(Connection arg0)
//					throws SQLException {
//				String sql="insert into user (name,age) values(?,?)";
//			    PreparedStatement ps = arg0.prepareStatement(sql);
//			    ps.setString(1, mbnSmsReadySend.getTosName());
//			    ps.setInt(2, mbnSmsReadySend.getPriorityLevel());
//			    return ps;
//			}
//		};
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
