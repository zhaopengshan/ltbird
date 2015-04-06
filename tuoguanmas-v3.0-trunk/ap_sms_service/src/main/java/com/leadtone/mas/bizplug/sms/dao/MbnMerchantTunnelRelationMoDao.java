package com.leadtone.mas.bizplug.sms.dao;

import java.util.List;

import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;

public interface MbnMerchantTunnelRelationMoDao {

	/**
	 * 依据接入号（带扩展码）获取商户PIN码
	 * @param accessNumber
	 * @return
	 */
	public List<MbnMerchantTunnelRelation> findByAccessNumber(String accessNumber);

}
