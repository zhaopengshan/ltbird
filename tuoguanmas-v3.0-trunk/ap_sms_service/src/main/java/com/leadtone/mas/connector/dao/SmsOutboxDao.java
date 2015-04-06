package com.leadtone.mas.connector.dao;

import java.util.List;

import com.leadtone.mas.connector.domain.SmsOutboxDbIntf;

public interface SmsOutboxDao {
	/**
	 * 查询待发送列表
	 * @return
	 */
	public List<SmsOutboxDbIntf> getAll();
	/**
	 * 删除待发送记录
	 * @param masSmsId
	 */
	public void delete(String masSmsId);
}
