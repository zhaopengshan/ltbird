package com.leadtone.mas.connector.dao;

import java.util.Date;
import java.util.List;

import com.leadtone.mas.connector.domain.SmsSentDbIntf;

public interface SmsSentDao {
	/**
	 * 插入发送记录
	 * @param smsSend
	 */
	public void insert(SmsSentDbIntf smsSent);
	/**
	 * 更新发送状态
	 * @param smsSend
	 */
	public void updateStatus(SmsSentDbIntf smsSent);

	/**
	 * 删除过期数据
	 * @param date
	 */
	public void deleteByTime(Date date);
	/**
	 * 查询等待状态报告列表
	 * @return
	 */
	public List<SmsSentDbIntf> getWaitRptList() ;
}
