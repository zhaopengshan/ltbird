package com.leadtone.mas.bizplug.sms.dao;

import java.util.List;

import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBeanVO;


public interface SmsMoLogDao {
	/**
	 * 依据状态查询上行数据日志记录，获取未处理的记录
	 *
	 * @param status：处理状态
	 * @return 上行数据日志实体类对象列表
	 */
	public List<SmsMoLogBean> queryByStatus(int status, String type);
	public List<SmsMoLogBeanVO> queryByStatusVo(int status, String type);

	/**
	 * 更新上行日志表中记录状态
	 *
	 * @param bean 上行数据日志实体类对象
	 */
	public void updateStatus(SmsMoLogBean bean);

	/**
	 * 插入一条记录
	 *
	 * @param bean：上行数据日志实体类对象
	 */
	public void insert(SmsMoLogBean bean);
	public void insertVo(SmsMoLogBeanVO bean);
}
