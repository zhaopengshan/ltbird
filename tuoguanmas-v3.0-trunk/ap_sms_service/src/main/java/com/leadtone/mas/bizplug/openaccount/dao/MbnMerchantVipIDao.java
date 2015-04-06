package com.leadtone.mas.bizplug.openaccount.dao;

import java.util.List;

import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.security.dao.base.PageBaseIDao;

public interface MbnMerchantVipIDao extends PageBaseIDao {
	public boolean insert(MbnMerchantVip merchantVip);
	public boolean update(MbnMerchantVip merchantVip);
	public MbnMerchantVip load(long merchantPin);
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
	 * @param privinceCode
	 * @param merchantType
	 * @return
	 */
	public MbnMerchantVip loadVirtualCityMerchant(String privinceCode, String merchantType);
	/**
	 * 查询省市企业
	 * @param privinceCode
	 * @param cityCode
	 * @return
	 */
	public List<MbnMerchantVip> loadByProvinceAndCity(String privinceCode, String cityCode);
	
	public boolean updateBatch(List<Long> pins,String smsState);
	
	 public Integer checkZxtUserIdInUse(String zxtUserId);
	 public int getCorpZXTId(Long merchantPin);
	
}
