package com.leadtone.mas.bizplug.openaccount.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantSmsMmsLimit;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantSmsMmsLimitIDao;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantSmsMmsLimitIService;

/**
 *
 * @author wangyu
 */
@Service("MbnMerchantSmsMmsLimitIService")
public class MbnMerchantSmsMmsLimitServiceImpl implements MbnMerchantSmsMmsLimitIService{
     @Resource
    private MbnMerchantSmsMmsLimitIDao mbnMerchantSmsMmsLimitIDao;

    public MbnMerchantSmsMmsLimitIDao getMbnMerchantSmsMmsLimitIDao() {
        return mbnMerchantSmsMmsLimitIDao;
    }

    @Override
    public MbnMerchantSmsMmsLimit loadByMerchantPin(Long merchantPin) {
        return this.mbnMerchantSmsMmsLimitIDao.loadByMerhcantPin(merchantPin);
    }
}
