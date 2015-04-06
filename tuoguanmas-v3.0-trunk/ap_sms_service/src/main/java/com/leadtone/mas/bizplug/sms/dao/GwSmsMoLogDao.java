package com.leadtone.mas.bizplug.sms.dao;

import java.util.List;

import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBeanVO;

public interface GwSmsMoLogDao {
	/**
	 * 依据状态查询上行数据日志记录，获取未处理的记录
	 *
	 * @param status：处理状态
	 * @return 上行数据日志实体类对象列表
	 */
	public List<SmsMoLogBean> queryByStatus(int status);
	
	public List<SmsMoLogBean> queryLtdxByStatus(int status);
	
	public List<SmsMoLogBeanVO> queryByQxtStatusVo(int status);
	public List<SmsMoLogBeanVO> queryByQxtNewStatusVo(int status);
	
	public List<SmsMoLogBean> queryByEmppStatus(int status);
	/**
	 * 更新上行日志表中记录状态
	 *
	 * @param bean 上行数据日志实体类对象
	 */
	public void updateStatus(SmsMoLogBean bean);
	public void updateEmppStatus(SmsMoLogBean bean);
	public void updateLtdxStatus(SmsMoLogBean bean);
	void updateQxtStatus(SmsMoLogBeanVO bean);
	void updateQxtNewStatus(SmsMoLogBeanVO bean);
}
