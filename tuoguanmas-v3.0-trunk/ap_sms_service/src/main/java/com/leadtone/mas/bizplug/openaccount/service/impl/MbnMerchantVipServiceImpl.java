package com.leadtone.mas.bizplug.openaccount.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;

/**
 *
 * @author wangyu
 */
@Service("MbnMerchantVipIService")
public class MbnMerchantVipServiceImpl implements MbnMerchantVipIService{
    @Resource
    private MbnMerchantVipIDao mbnMerchantVipIDao;

    public MbnMerchantVipIDao getMbnMerchantVipIDao() {
        return mbnMerchantVipIDao;
    }

    @Override
    public MbnMerchantVip loadByMerchantPin(Long merchantPin) {
        return this.mbnMerchantVipIDao.load(merchantPin);
    }

	@Override
	public MbnMerchantVip loadByName(String name) {
		return this.mbnMerchantVipIDao.loadByName(name);
	}

	@Override
	public Page page(PageUtil pageUtil) {
			return mbnMerchantVipIDao.page(pageUtil);
	}

	@Override
	public MbnMerchantVip loadVirtualCityMerchant(String cityCode,
			String merchantType) {
		return this.mbnMerchantVipIDao.loadVirtualCityMerchant(cityCode, merchantType);
	}
	
	
	@Override
	public List<MbnMerchantVip> loadByProvinceAndCity(String privinceCode,
			String cityCode) {
		return this.mbnMerchantVipIDao.loadByProvinceAndCity(privinceCode, cityCode);
	}

	@Override
	public MbnMerchantVip loadVirtualProvinceMerchant(String privinceCode,
			String merchantType) {
		return this.mbnMerchantVipIDao.loadVirtualProvinceMerchant(privinceCode, merchantType);
	}

	@Override
	public boolean insertMerchant(MbnMerchantVip merchantVip) {
		return this.mbnMerchantVipIDao.insert(merchantVip);
	}

	@Override
	public boolean checkZxtUserIdInUse(String zxtUserId) {
		
		Integer count = this.mbnMerchantVipIDao.checkZxtUserIdInUse(zxtUserId);
    	if(count != null && count > 0){
    		return true;
    	}
    	return false;
	}

	@Override
	public int getCorpZXTId(Long merchantPin) {
		return this.mbnMerchantVipIDao.getCorpZXTId(merchantPin);
	}
    
}
