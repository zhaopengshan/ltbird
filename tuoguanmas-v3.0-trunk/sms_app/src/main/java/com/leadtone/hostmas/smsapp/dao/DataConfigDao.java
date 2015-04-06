package com.leadtone.hostmas.smsapp.dao;

import java.util.List;

import com.leadtone.hostmas.smsapp.domain.ConfigData;
import com.leadtone.hostmas.smsapp.exception.BizException;


public interface DataConfigDao extends GenericDao<ConfigData>{
	
	void updateTunnelIdAndListenPort(ConfigData configData) throws BizException;
	
	List<ConfigData> listAllSmsConfigDatas() throws BizException ;
	
	void updateAppStatus(ConfigData configData) throws BizException;
	
	public ConfigData querySmsConfigDataByProvinceName(ConfigData configData) throws BizException;
	
	//public List<ConfigData> queryAppStauts() throws BizException ;
}
