package com.leadtone.mas.bizplug.sms.dao;

import java.util.Date;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseIDao;

public interface MbnSmsTaskNumberDao extends SmsBaseIDao<MbnSmsTaskNumber, Long> {
	public MbnSmsTaskNumber getMaxUsed(Long merchantPin, String coding);
	public MbnSmsTaskNumber getExpireLastUsed(Long merchantPin, String coding, Date endTime);
	public MbnSmsTaskNumber getLastUsed(Long merchantPin, String coding);
}
