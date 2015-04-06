package com.leadtone.mas.bizplug.openaccount.service;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;

/**
 *
 * @author wangyu
 */
public interface MbnConfigMerchantIService {
    public MbnConfigMerchant loadByMerchantPin(Long merchantPin,String name);
    public boolean insert(MbnConfigMerchant mbnConfigMerchant);
    public boolean update(MbnConfigMerchant mbnConfigMerchant);
    public boolean insertBatch(List<MbnConfigMerchant> list);
    public boolean updateBatch(List<MbnConfigMerchant> list);
    public Map<String,MbnConfigMerchant> loadByList(Long merchantPin,List<String> list);
    public List<MbnConfigMerchant> queryMerchantConfigList(Long merchantPin);
}
