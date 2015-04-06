package com.leadtone.mas.bizplug.tunnel.dao;

import java.util.List;

import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.dao.base.TunnelBaseIDao;

public interface MbnMerchantTunnelRelationDao extends
		TunnelBaseIDao<MbnMerchantTunnelRelation, java.lang.Long> {
	public List<MbnMerchantTunnelRelation> findByTunnelType(Long merchantPin,
			Long type);
	public List<MbnMerchantTunnelRelation> findByClassify(Long merchantPin,
			Integer type);
	/**
	 * 查询通道
	 * @param merchantPin
	 * @param classify
	 * @param type
	 * @return
	 */
	public List<MbnMerchantTunnelRelation> findByClassifyAndType(Long merchantPin,
			Integer classify, Integer type);
	
	public List<MbnMerchantTunnelRelation> findByPin(Long merchantPin);
	
	public MbnMerchantTunnelRelation findByPinAndTunnelId(Long merchantPin, Long tunnelId);
	
	public List<MbnMerchantTunnelRelation> findByAccessNumberAndType(Long merchantPin, String accessNumber, Integer type);
	public MbnMerchantTunnelRelation getExpireLastUsed(Long tunnelId);
	public MbnMerchantTunnelRelation getMaxUsed(Long tunnelId);
        public void delete(Long merchantPin);
}
