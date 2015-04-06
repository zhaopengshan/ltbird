package com.leadtone.mas.bizplug.tunnel.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantConsumeDao;

@Service(value = "mbnMerchantConsumeService")
@Transactional
public class MbnMerchantConsumeServiceImpl implements MbnMerchantConsumeService {
	@Resource
	private MbnMerchantConsumeDao mbnMerchantConsumeDao;

	@Override
	public List<MbnMerchantConsume> findByPin(Long merchantPin) {
		return mbnMerchantConsumeDao.findByPin(merchantPin);
	}

	@Override
	public MbnMerchantConsume findByTunnelId(Long merchantPin,
			Long tunnelId) {
		return mbnMerchantConsumeDao.findByTunnelId(merchantPin, tunnelId);
	}

	@Override
	public boolean add(MbnMerchantConsume mc) {
		this.mbnMerchantConsumeDao.insert(mc);
		return true;
	}

	@Override
	public boolean update(MbnMerchantConsume mc) {
		this.mbnMerchantConsumeDao.update(mc);
		return true;
	}
	
}