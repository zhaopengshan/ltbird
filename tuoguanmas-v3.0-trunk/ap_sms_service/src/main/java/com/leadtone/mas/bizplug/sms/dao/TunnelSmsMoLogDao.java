package com.leadtone.mas.bizplug.sms.dao;

import java.util.List;

import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;

public interface TunnelSmsMoLogDao {
	/**
	 * 查询上行数据日志记录
	 *
	 * @return 上行数据日志实体类对象列表
	 */
	public List<SmsMoLogBean> queryAll();

	/**
	 * 删除记录
	 *
	 * @param bean：上行数据日志实体类对象
	 */
	public void delete(SmsMoLogBean bean);
}
