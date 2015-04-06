/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.ford.merchant.service;

import com.leadtone.delegatemas.merchant.bean.PointsCityStatistic;
import com.leadtone.delegatemas.merchant.dao.IMasMerchantDAO;
import com.leadtone.delegatemas.merchant.service.IMasMerchantService;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author blueskybluesea
 */
@Service("fordMerchantServiceImpl")
public class FordMerchantServiceImpl implements IMasMerchantService {
    @Resource(name="masMerchantServiceImpl")
    private IMasMerchantService merchantService;
    @Resource(name="masMerchantDAOImpl")
    private IMasMerchantDAO masMerchantDAOImpl;
    @Override
    public Page paginateMasMerchants(Map<String, Object> paraMap) {
        return merchantService.paginateMasMerchants(paraMap);
    }

    @Override
    public List<PointsCityStatistic> merchantCountByCity(Long provinceId) {
        return merchantService.merchantCountByCity(provinceId);
    }

    @Override
    public void newMerchantAndMerchantConfig(MbnMerchantVip merchant, List<MbnConfigMerchant> merchantConfigs) {
        MbnMerchantVipIDao mbnMerchantVipIDao = (MbnMerchantVipIDao)masMerchantDAOImpl;
        merchant.setMerchantPin(PinGen.getMerchantPin());
        merchant.setCreateTime(Calendar.getInstance().getTime());
        mbnMerchantVipIDao.insert(merchant);        ;
    }

    @Override
    public void updateMerchantAndMerchantConfig(MbnMerchantVip merchant, List<MbnConfigMerchant> merchantConfigs) {
        MbnMerchantVipIDao mbnMerchantVipIDao = (MbnMerchantVipIDao)masMerchantDAOImpl;
        mbnMerchantVipIDao.update(merchant);
    }

    @Override
    public void removeMerchantAndConfigs(Long merchantPin) {
        merchantService.removeMerchantAndConfigs(merchantPin);
    }

	@Override
	public void createMerchantAndMerchantConfig(MbnMerchantVip merchant,
			List<MbnConfigMerchant> merchantConfigs) {
	}
    
}
