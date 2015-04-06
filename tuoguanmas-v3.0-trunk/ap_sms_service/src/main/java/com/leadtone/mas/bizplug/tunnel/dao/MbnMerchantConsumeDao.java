package com.leadtone.mas.bizplug.tunnel.dao;

import java.util.List;

import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.dao.base.TunnelBaseIDao;

public interface MbnMerchantConsumeDao extends
		TunnelBaseIDao<MbnMerchantConsume, java.lang.Long> {
	public List<MbnMerchantConsume> findByPin(Long merchantPin);

	public MbnMerchantConsume findByTunnelId(Long merchantPin, Long tunnelId);
        public void deleteConsumesByMerchantPin(Long merchantPin);
}
