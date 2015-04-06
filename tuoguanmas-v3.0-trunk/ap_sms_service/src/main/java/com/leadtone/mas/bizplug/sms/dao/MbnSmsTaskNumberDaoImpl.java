package com.leadtone.mas.bizplug.sms.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseDaoImpl;

@Repository
public class MbnSmsTaskNumberDaoImpl extends
		SmsBaseDaoImpl<MbnSmsTaskNumber, Long> implements MbnSmsTaskNumberDao {
	protected static final String QUERYMAXINUSE = ".queryMaxInUse";
	protected static final String QUERYEXPIRELASTUSED = ".queryExpireLastUsed";
	protected static final String QUERYLASTUSED = ".queryLastUsed";

	@Override
	public MbnSmsTaskNumber getExpireLastUsed(Long merchantPin, String coding, Date endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantPin", merchantPin);
		map.put("coding", coding);
		map.put("endTime", endTime);
		return (MbnSmsTaskNumber) this.getSqlMapClientTemplate()
				.queryForObject(this.sqlMapNamespace + QUERYEXPIRELASTUSED, map);
	}

	@Override
	public MbnSmsTaskNumber getMaxUsed(Long merchantPin, String coding) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantPin", merchantPin);
		map.put("coding", coding);
		return (MbnSmsTaskNumber) this.getSqlMapClientTemplate()
				.queryForObject(this.sqlMapNamespace + QUERYMAXINUSE, map);
	}

	@Override
	public MbnSmsTaskNumber getLastUsed(Long merchantPin, String coding) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantPin", merchantPin);
		map.put("coding", coding);
		return (MbnSmsTaskNumber) this.getSqlMapClientTemplate()
				.queryForObject(this.sqlMapNamespace + QUERYLASTUSED, map);
	}

}