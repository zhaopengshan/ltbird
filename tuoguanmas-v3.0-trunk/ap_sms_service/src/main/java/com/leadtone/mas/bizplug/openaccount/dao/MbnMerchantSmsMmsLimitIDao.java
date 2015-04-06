package com.leadtone.mas.bizplug.openaccount.dao;

import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantSmsMmsLimit;

public interface MbnMerchantSmsMmsLimitIDao {
	public boolean insert(MbnMerchantSmsMmsLimit mbnMerchantSmsMmsLimit);
	public boolean update(MbnMerchantSmsMmsLimit mbnMerchantSmsMmsLimit);
        public MbnMerchantSmsMmsLimit loadByMerhcantPin(Long merchantPin);
}
