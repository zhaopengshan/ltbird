package com.leadtone.mas.bizplug.sms.service;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;

public interface MbnSmsTaskNumberService {
	/**
	 * 获取3位任务号
	 * @param merchantPin
	 * @param coding
	 * @return
	 */
	public String getTaskNumber(Long merchantPin, String coding);
	/**
	 * 获取2位任务号
	 * @param merchantPin
	 * @param userExtCode
	 * @return
	 */
	public String getTaskNumber2(Long merchantPin, String userExtCode);
	public Integer addTaskNumber(MbnSmsTaskNumber num);
}
