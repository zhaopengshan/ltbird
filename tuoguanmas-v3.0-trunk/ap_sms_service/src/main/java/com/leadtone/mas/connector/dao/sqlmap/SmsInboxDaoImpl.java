package com.leadtone.mas.connector.dao.sqlmap;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.connector.dao.SmsInboxDao;
import com.leadtone.mas.connector.domain.SmsInboxDbIntf;

@Repository("smsInboxDao")
public class SmsInboxDaoImpl extends SqlMapBaseDao<SmsInboxDbIntf, String>
		implements SmsInboxDao {

	@Override
	public void deleteByTime(Date date) {
		getSqlMapClientTemplate().delete(namespace + ".deleteByTime", date);
	}

	@Override
	public void insert(SmsInboxDbIntf smsInbox) {
		getSqlMapClientTemplate().insert(namespace + ".insert", smsInbox);
	}

}
