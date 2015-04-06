package com.leadtone.hostmas.smsapp.service;

import java.util.List;

import com.leadtone.hostmas.smsapp.domain.ConfigData;
import com.leadtone.hostmas.smsapp.exception.BizException;

public interface SmsService {

	void updateAppStatus(ConfigData configData) throws BizException;

	List queryAppStatus(ConfigData configData) throws BizException;
	
		
	ConfigData querySmsConfigDataByProvinceName(String provinceName) throws BizException;
}
