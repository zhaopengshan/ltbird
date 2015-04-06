/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.merchant.service;

import com.leadtone.delegatemas.merchant.bean.PointsCityStatistic;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import java.util.List;
import java.util.Map;

/**
 *
 * @author blueskybluesea
 */
public interface IMasMerchantService {
    /**
     * 根据城市来分页显示商户信息
     * @param paraMap
     * @return 
     */
    public Page paginateMasMerchants(Map<String,Object> paraMap);
    /**
     * 以某省的城市为分组基础,分组统计城市商户信息
     * @return 
     */
    public List<PointsCityStatistic> merchantCountByCity(Long provinceId);
    public void newMerchantAndMerchantConfig(MbnMerchantVip merchant,List<MbnConfigMerchant> merchantConfigs);
    public void updateMerchantAndMerchantConfig(MbnMerchantVip merchant,List<MbnConfigMerchant> merchantConfigs);
    public void removeMerchantAndConfigs(Long merchantPin);
    /**
     * 用于 远程业务节点开户
     * @param merchant
     * @param merchantConfigs
     */
    public void createMerchantAndMerchantConfig(MbnMerchantVip merchant,List<MbnConfigMerchant> merchantConfigs);
}
