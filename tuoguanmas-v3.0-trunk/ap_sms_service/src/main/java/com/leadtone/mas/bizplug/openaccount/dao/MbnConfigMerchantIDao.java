package com.leadtone.mas.bizplug.openaccount.dao;

import java.util.List;

import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;

public interface MbnConfigMerchantIDao {
	public boolean insert(MbnConfigMerchant mbnConfigMerchant);
	public boolean update(MbnConfigMerchant mbnConfigMerchant);
	public boolean batchSave(List<MbnConfigMerchant> list);
	public boolean batchUpdate(List<MbnConfigMerchant> list);
	public List<MbnConfigMerchant> list(MbnConfigMerchant mbnConfigMerhcant);
	public MbnConfigMerchant load(long MerchantPin,String name);
}
