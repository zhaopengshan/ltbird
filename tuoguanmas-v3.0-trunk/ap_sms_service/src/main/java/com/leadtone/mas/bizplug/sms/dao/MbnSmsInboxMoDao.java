package com.leadtone.mas.bizplug.sms.dao;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;

public interface MbnSmsInboxMoDao {
	/**
	 * 插入记录
	 *
	 * @param bean：上行记录实体类对象
	 */
	public void insert(MbnSmsInbox bean);
}
