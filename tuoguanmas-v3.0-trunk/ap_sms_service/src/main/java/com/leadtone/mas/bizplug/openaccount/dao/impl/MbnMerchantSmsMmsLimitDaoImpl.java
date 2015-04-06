package com.leadtone.mas.bizplug.openaccount.dao.impl;

import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantSmsMmsLimit;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantSmsMmsLimitIDao;
@Component("mbnMerchantSmsMmsLimitIDao")
public class MbnMerchantSmsMmsLimitDaoImpl extends BaseDao implements
		MbnMerchantSmsMmsLimitIDao {

	@Override
	public boolean insert(MbnMerchantSmsMmsLimit mbnMerchantSmsMmsLimit) {
		this.getSqlMapClientTemplate().insert("MbnMerchantSmsMmsLimit.insert", mbnMerchantSmsMmsLimit);
		return true;
	}

	@Override
	public boolean update(MbnMerchantSmsMmsLimit mbnMerchantSmsMmsLimit) {
		int i=this.getSqlMapClientTemplate().update("MbnMerchantSmsMmsLimit.update", mbnMerchantSmsMmsLimit);
		if(i>0){
			return true;
		}
		return false;
	}

    @Override
    public MbnMerchantSmsMmsLimit loadByMerhcantPin(Long merchantPin) {
        return (MbnMerchantSmsMmsLimit)this.getSqlMapClientTemplate().queryForObject("MbnMerchantSmsMmsLimit.loadByMerhcantPin", merchantPin);
    }

}
