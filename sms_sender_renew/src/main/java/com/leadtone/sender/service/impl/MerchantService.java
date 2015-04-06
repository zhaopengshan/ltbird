package com.leadtone.sender.service.impl;

import java.util.List;

import com.leadtone.sender.bean.ConfigParam;
import com.leadtone.sender.bean.Consume;
import com.leadtone.sender.dao.local.IMerchantDao;
import com.leadtone.sender.service.IMerchantService;

public class MerchantService implements IMerchantService {

	private IMerchantDao merchantDao;
	@Override
	public List getMerchantVip(Long merchantPin) {
		return merchantDao.getMerchantVip(merchantPin);
	}
	
	@Override
	public List getTunnelInfo(Long merchantPin,Integer type, Integer classify){
		return merchantDao.getTunnelInfo(merchantPin, type, classify);
	}
	@Override
	public List getTunnelInfo(Long merchantPin, Integer type) {
		return merchantDao.getTunnelInfo(merchantPin, type);
	}
	
	@Override
	public ConfigParam getConfigParam(Long merchantPin, String name) {
		return merchantDao.getConfigParam(merchantPin, name);
	}
	
	public IMerchantDao getMerchantDao() {
		return merchantDao;
	}
	public void setMerchantDao(IMerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}

}
