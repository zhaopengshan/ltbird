package com.leadtone.mas.connector.dao;

import java.util.Date;

import com.leadtone.mas.connector.domain.SmsInboxDbIntf;

public interface SmsInboxDao {
	/**
	 * 插入上行信息
	 * @param smsInbox
	 */
	public void insert(SmsInboxDbIntf smsInbox);
	
	/**
	 * 删除过期数据
	 * @param date
	 */
	public void deleteByTime(Date date);
}
