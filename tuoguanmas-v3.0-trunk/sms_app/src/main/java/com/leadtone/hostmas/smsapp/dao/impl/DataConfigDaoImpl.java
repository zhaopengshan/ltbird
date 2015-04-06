package com.leadtone.hostmas.smsapp.dao.impl;

import java.util.List;


import com.leadtone.hostmas.smsapp.dao.DataConfigDao;
import com.leadtone.hostmas.smsapp.dao.SqlMapGenericDaoImpl;
import com.leadtone.hostmas.smsapp.domain.ConfigData;
import com.leadtone.hostmas.smsapp.exception.BizException;

public class DataConfigDaoImpl  extends SqlMapGenericDaoImpl<ConfigData> implements DataConfigDao {
	


	/*@Override
	public void updateListenport() throws BizException {
		String statementName = sqlMapNamespace + ".update_listen_port";
		this.getSqlMapClientTemplate().update(statementName);
	}*/
	
	@Override
	public void updateTunnelIdAndListenPort(ConfigData configData) throws BizException {
		
		String statementName = sqlMapNamespace + ".update_tunnelid_listenport";
		
		this.getSqlMapClientTemplate().update(statementName,configData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigData> listAllSmsConfigDatas() throws BizException {
		String statementName = sqlMapNamespace + ".list";
		
		return getSqlMapClientTemplate().queryForList(statementName);
	}

	@Override
	public ConfigData querySmsConfigDataByProvinceName(ConfigData configData) throws BizException {
		String statementName = sqlMapNamespace + ".loadByProvinceName";
		
		return (ConfigData) getSqlMapClientTemplate().queryForObject(statementName,configData.getProvince_code());
	}

	@Override
	public void updateAppStatus(ConfigData configData) throws BizException {
		
		String statementName = sqlMapNamespace + ".update_app_status";
		
		this.getSqlMapClientTemplate().update(statementName,configData);
	}
	
	/*public List<ConfigData> queryAppStauts() throws BizException {
		
		String statementName = sqlMapNamespace + ".list";
		
		return getSqlMapClientTemplate().queryForList(statementName);
	}*/
}
