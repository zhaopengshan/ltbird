package com.leadtone.sender.dao.mandatory.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.leadtone.sender.dao.mandatory.ICorpDao;

public class CorpDaoImpl implements ICorpDao {
	private JdbcTemplate jt;
	private SimpleJdbcTemplate st;
	
	@Override
	public List getCorpPara(String name, String corpId) {
		return jt.queryForList("SELECT value FROM corp_para WHERE name = ? AND corp_id = ?",new Object[]{name,corpId});
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
