package com.leadtone.mas.bizplug.sms.dao;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseIDao;

public interface MbnSmsOperationClassDao extends
		SmsBaseIDao<MbnSmsOperationClass, Long> {
	public MbnSmsOperationClass queryByCoding(String coding);
}
