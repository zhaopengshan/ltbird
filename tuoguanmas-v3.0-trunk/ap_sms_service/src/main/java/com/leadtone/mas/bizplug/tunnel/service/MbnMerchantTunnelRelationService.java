package com.leadtone.mas.bizplug.tunnel.service;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsumeFlow;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;

public interface MbnMerchantTunnelRelationService {
	public List<MbnMerchantTunnelRelation> findByTunnelType(Long merchantPin,
			Long type);
	
	public List<MbnMerchantTunnelRelation> findByClassify(Long merchantPin,
			Integer classify);
	/**
	 * 查询通道
	 * @param merchantPin
	 * @param classify
	 * @param type 1 短信 2彩信
	 * @return
	 */
	public List<MbnMerchantTunnelRelation> findByClassifyAndType(Long merchantPin,
			Integer classify, Integer type);
			
	public List<MbnMerchantTunnelRelation> findByPin(Long merchantPin);
	
	public MbnMerchantTunnelRelation findByPinAndTunnelId(Long merchantPin, Long tunnelId);
	
	public boolean add(MbnMerchantTunnelRelation mmtr);
	public boolean update(MbnMerchantTunnelRelation mmtr);
	
	public String getAccessNumber(Long merchantPin, SmsMbnTunnel smt) ;
	/**
	 * 为企业通道充值
	 * @param mbnMerchantTunnelRelation
	 * @param mbnMerchantConsumeFlow
	 * @param entityMap
	 * @param chargePrice 
	 */
	public void corpCharge(MbnMerchantTunnelRelation mbnMerchantTunnelRelation,
			MbnMerchantConsumeFlow mbnMerchantConsumeFlow,
			Map<String, Object> entityMap,Float chargePrice, Users user);
	
	/**
	 * 获取通道配置
	 * @param merchantPin
	 * @param accessNumber
	 * @param type
	 * @return
	 */
	public List<MbnMerchantTunnelRelation> findByAccessNumberAndType(Long merchantPin,
			String accessNumber, Integer type);
}
