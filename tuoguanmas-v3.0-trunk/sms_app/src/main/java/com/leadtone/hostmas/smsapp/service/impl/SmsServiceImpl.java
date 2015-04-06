package com.leadtone.hostmas.smsapp.service.impl;

import java.util.List;

import com.leadtone.hostmas.smsapp.dao.DataConfigDao;
import com.leadtone.hostmas.smsapp.domain.ConfigData;
import com.leadtone.hostmas.smsapp.exception.BizException;
import com.leadtone.hostmas.smsapp.service.SmsService;


public class SmsServiceImpl implements SmsService {
	
	private DataConfigDao dataConfigDao;
	
	public DataConfigDao getDataConfigDao() {
		return dataConfigDao;
	}

	public void setDataConfigDao(DataConfigDao dataConfigDao) {
		this.dataConfigDao = dataConfigDao;
	}



	@Override
	public void updateAppStatus(ConfigData configData) throws BizException {
		dataConfigDao.updateAppStatus(configData);
	}

	@Override
	public List<ConfigData> queryAppStatus(ConfigData smsConfigData) throws BizException {
		return dataConfigDao.listAllSmsConfigDatas();
		
	}

	
	public ConfigData querySmsConfigDataByProvinceName(String provinceName) throws BizException {
		ConfigData configData = new ConfigData();
		configData.setProvince_code(provinceName);
		return dataConfigDao.querySmsConfigDataByProvinceName(configData);
		
	}
	
}
