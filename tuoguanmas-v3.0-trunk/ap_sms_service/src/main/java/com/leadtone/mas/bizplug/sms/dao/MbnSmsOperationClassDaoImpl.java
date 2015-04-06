package com.leadtone.mas.bizplug.sms.dao;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseDaoImpl;

@Repository
public class MbnSmsOperationClassDaoImpl extends
		SmsBaseDaoImpl<MbnSmsOperationClass, Long> implements
		MbnSmsOperationClassDao {
	protected static final String QUERYBYCODING = ".queryByCoding";

	@Override
	public MbnSmsOperationClass queryByCoding(String coding) {
		return (MbnSmsOperationClass) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + QUERYBYCODING, coding);
	}

}
