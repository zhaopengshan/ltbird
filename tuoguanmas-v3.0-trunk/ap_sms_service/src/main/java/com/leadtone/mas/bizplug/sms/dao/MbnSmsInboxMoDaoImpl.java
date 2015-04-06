package com.leadtone.mas.bizplug.sms.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;

@Component("mbnSmsInboxMoDao")
public class MbnSmsInboxMoDaoImpl extends SqlMapClientDaoSupport implements MbnSmsInboxMoDao {

	@Override
	public void insert(MbnSmsInbox bean) {
		getSqlMapClientTemplate().insert("MbnSmsInbox.insert", bean);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
