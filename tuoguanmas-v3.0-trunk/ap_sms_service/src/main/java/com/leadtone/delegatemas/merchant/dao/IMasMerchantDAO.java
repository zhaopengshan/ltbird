/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.merchant.dao;

import com.leadtone.delegatemas.merchant.bean.PointsCityStatistic;
import com.leadtone.mas.bizplug.common.Page;
import java.util.List;
import java.util.Map;

/**
 *
 * @author blueskybluesea
 */
public interface IMasMerchantDAO {
    public Page page(Map<String,Object> paraMap);
    public List<PointsCityStatistic> merchantCountByRegion(Long regionId);
    public void deleteMerchantVip(Long merchantPin);
}
