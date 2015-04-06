package com.leadtone.sender.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leadtone.sender.bean.SmsLimitBean;

public interface ISmsLimitService {
	
	public Map<String,Object> gatewayLimitCalculator(String province, Long merchantPin);
	
	public Map<String,Object> serverModemLimitCalculator(String province, Long merchantPin);
	    
    public List getProvinceLimit();
    
    public List getCorpLimit(Long merchantPin);
    
    public SmsLimitBean getMerchantLimit(Long merchantPin);
    
    public void updateConsume(Long consumeId,int count);
    
    public Integer getTDLimitCount(Long merchantPin);
    
    public Integer getGateWayLimitCount(Long merchantPin);
    
}
