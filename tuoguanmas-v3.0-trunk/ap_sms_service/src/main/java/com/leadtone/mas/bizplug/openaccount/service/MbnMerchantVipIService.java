package com.leadtone.mas.bizplug.openaccount.service;

import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;

/**
 *
 * @author wangyu
 */
public interface MbnMerchantVipIService {
    public MbnMerchantVip loadByMerchantPin(Long merchantPin);
    public MbnMerchantVip loadByName(String name);
	/**
	 * 查询省份虚拟企业
	 * @param privinceCode
	 * @param merchantType
	 * @return
	 */
	public MbnMerchantVip loadVirtualProvinceMerchant(String privinceCode, String merchantType);
	/**
	 * 查询地市虚拟企业
	 * @param cityCode
	 * @param merchantType
	 * @return
	 */
	public MbnMerchantVip loadVirtualCityMerchant(String cityCode, String merchantType);
	
	/**
	 * 查询省市企业
	 * @param privinceCode
	 * @param cityCode
	 * @return
	 */
	public List<MbnMerchantVip> loadByProvinceAndCity(String privinceCode, String cityCode);
	
    public Page page(PageUtil pageUtil);
    /**
     * 插入企业 
     * @param merchant
     * @return
     */
    public boolean insertMerchant(MbnMerchantVip merchant);
    
    public boolean checkZxtUserIdInUse(String zxtUserId);
    public int getCorpZXTId(Long merchantPin);
}
