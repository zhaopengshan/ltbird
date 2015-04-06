package com.leadtone.mas.bizplug.sms.service;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;

public interface MbnSmsOperationClassService {
	public MbnSmsOperationClass findById(Long id);

	public MbnSmsOperationClass findByCoding(String coding);
}
