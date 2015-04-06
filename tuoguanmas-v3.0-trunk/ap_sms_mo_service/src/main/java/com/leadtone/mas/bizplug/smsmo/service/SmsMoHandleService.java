package com.leadtone.mas.bizplug.smsmo.service;

import java.util.List;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBeanVO;

public interface SmsMoHandleService {

	/**
	 * 从上行日志表中获取需要处理的数据
	 *
	 * @return 上行日志实体类对象列表
	 */
	public List<SmsMoLogBean> getSmsMoLogs(int status, String type);
	public List<SmsMoLogBeanVO> queryByStatusVo(int status, String type);

	/**
	 * 对上行数据内容进行解析，分析出业务类型和编号
	 *
	 * @param bean 上行日志实体类对象
	 * @return 上行数据缓存表实体类对象
	 */
	public MbnSmsInbox parseContent(SmsMoLogBean smsMoLogBean);
	public MbnSmsInbox parseContent(SmsMoLogBeanVO smsMoLogBean);

	/**
	 * 插入上行数据缓存表中
	 * @param bean 上行数据缓存表实体类对象
	 * @return true：成功，false：失败
	 */
	public void addSmsInbox(MbnSmsInbox bean);

	/**
	 * 更新上行日志表中记录状态
	 *
	 * @param bean 上行日志实体类对象
	 */
	public void updateSmsMoLogStatus(SmsMoLogBean bean);
}
