package com.leadtone.mas.bizplug.tunnel.service;

import java.util.List;

import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;

public interface MbnMerchantConsumeService {
	public MbnMerchantConsume findByTunnelId(Long merchantPin,
			Long tunnelId);

	public List<MbnMerchantConsume> findByPin(Long merchantPin);
	public boolean add(MbnMerchantConsume mc);
	public boolean update(MbnMerchantConsume mc);
}
