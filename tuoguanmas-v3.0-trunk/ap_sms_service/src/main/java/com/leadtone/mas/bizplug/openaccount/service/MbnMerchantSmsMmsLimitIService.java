package com.leadtone.mas.bizplug.openaccount.service;

import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantSmsMmsLimit;

/**
 *
 * @author wangyu
 */
public interface MbnMerchantSmsMmsLimitIService {
    public MbnMerchantSmsMmsLimit loadByMerchantPin(Long merchantPin);
}
