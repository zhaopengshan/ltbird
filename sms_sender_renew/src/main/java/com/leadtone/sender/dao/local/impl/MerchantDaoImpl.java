package com.leadtone.sender.dao.local.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.leadtone.sender.bean.ConfigParam;
import com.leadtone.sender.bean.Consume;
import com.leadtone.sender.bean.SmsLimitBean;
import com.leadtone.sender.dao.local.IMerchantDao;

public class MerchantDaoImpl implements IMerchantDao {
	private JdbcTemplate jt;
	private SimpleJdbcTemplate st;
	

	@Override
	public List getTunnelInfo(Long merchantPin, Integer type, Integer classify) {
		return jt.queryForList("SELECT t.access_number,t.service_id,t.sms_corp_ident FROM mbn_merchant_tunnel_relation tr LEFT JOIN mbn_tunnel t ON tr.tunnel_id = t.id WHERE tr.merchant_pin = ? AND t.classify = ? AND t.type =?",new Object[]{merchantPin,classify,type});
	}
	@Override
	public List getTunnelInfo(Long merchantPin, Integer type) {
		return jt.queryForList("SELECT t.access_number,t.service_id,t.sms_corp_ident, t.classify FROM mbn_merchant_tunnel_relation tr LEFT JOIN mbn_tunnel t ON tr.tunnel_id = t.id WHERE tr.merchant_pin = ? AND t.type =?",new Object[]{merchantPin, type});
	}
	@Override
	public Consume getConsume(Long merchantPin, Integer type, Integer classify) {
		try{
		return(Consume) jt.queryForObject("SELECT c.id,c.merchant_pin,c.tunnel_id,c.remain_number,c.modify_time FROM mbn_merchant_consume c LEFT JOIN mbn_merchant_tunnel_relation tr ON c.merchant_pin=tr.merchant_pin AND c.tunnel_id = tr.tunnel_id LEFT JOIN mbn_tunnel t ON tr.tunnel_id = t.id WHERE c.merchant_pin = ? AND t.classify = ? AND t.type =?",new Object[]{merchantPin,classify,type},new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Consume bean = new Consume();
				bean.setId(rs.getLong("id"));
				bean.setRemainNumber(rs.getInt("remain_number"));
				bean.setTunnelId(rs.getLong("tunnel_id"));
				bean.setMerchantPin(rs.getLong("merchant_pin"));
				bean.setModifyTime(rs.getDate("modify_time"));
				return bean;
			}});
		}catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void updateConsume(Long id, Integer remainNumber) {
    	String sql = "UPDATE mbn_merchant_consume SET remain_number = ?,modify_time = ? where id = ?;";
    	jt.update(sql.toString(), new Object[]{remainNumber,new Date(),id});		
	}
	
	@Override
	public ConfigParam getConfigParam(Long merchantPin, String name) {
		try{
		return(ConfigParam) jt.queryForObject("SELECT * from mbn_config_merchant where merchant_pin = ? and name = ?",new Object[]{merchantPin,name},new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ConfigParam bean = new ConfigParam();
				bean.setId(rs.getLong("id"));
				bean.setMerchantPin(rs.getLong("merchant_pin"));
				bean.setName(rs.getString("name"));
				bean.setItemValue(rs.getString("item_value"));
				bean.setDescription(rs.getString("description"));
				return bean;
			}});
		}catch (Exception e) {
            return null;
        }
	}
	
	@Override
	public List getMerchantVip(Long merchantPin) {
		return jt.queryForList("SELECT group_code,sms_access_number FROM mbn_merchant_vip WHERE merchant_pin = ?",new Object[]{merchantPin});
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
