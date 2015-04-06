package com.leadtone.mas.connector.dao.sqlmap;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.connector.dao.SmsSentDao;
import com.leadtone.mas.connector.domain.SmsSentDbIntf;

@Repository("smsSentDao")
public class SmsSendDaoImpl extends SqlMapBaseDao<SmsSentDbIntf, String>
		implements SmsSentDao {

	@Override
	public void deleteByTime(Date date) {
		getSqlMapClientTemplate().delete(namespace + ".deleteByTime", date);

	}

	@Override
	public void insert(SmsSentDbIntf smsSent) {
		getSqlMapClientTemplate().insert(namespace + ".insert", smsSent);
	}

	@Override
	public void updateStatus(SmsSentDbIntf smsSent) {
		getSqlMapClientTemplate().update(namespace + ".updateStatus", smsSent);
	}

	@Override
	public List<SmsSentDbIntf> getWaitRptList() {
		return getSqlMapClientTemplate().queryForList(namespace + ".getWaitRptList");
	}
	
}
