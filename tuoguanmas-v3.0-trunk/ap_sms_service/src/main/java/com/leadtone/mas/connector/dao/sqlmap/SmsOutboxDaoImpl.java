package com.leadtone.mas.connector.dao.sqlmap;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.connector.dao.SmsOutboxDao;
import com.leadtone.mas.connector.domain.SmsInboxDbIntf;
import com.leadtone.mas.connector.domain.SmsOutboxDbIntf;

@Repository("smsOutboxDao")
public class SmsOutboxDaoImpl extends SqlMapBaseDao<SmsOutboxDbIntf, String>
		implements SmsOutboxDao {

	@Override
	public void delete(String masSmsId) {
		getSqlMapClientTemplate().delete(namespace + ".deleteByPk", masSmsId);
	}

	@Override
	public List<SmsOutboxDbIntf> getAll() {
		return getSqlMapClientTemplate().queryForList(namespace + ".loadAll");
	}

}
