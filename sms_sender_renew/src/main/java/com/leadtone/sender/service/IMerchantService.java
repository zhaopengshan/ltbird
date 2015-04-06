package com.leadtone.sender.service;

import java.util.List;

import com.leadtone.sender.bean.ConfigParam;
import com.leadtone.sender.bean.Consume;

public interface IMerchantService {
    public List getMerchantVip(Long merchantPin);
    
    public ConfigParam getConfigParam(Long merchantPin, String name);
    
	public List getTunnelInfo(Long merchantPin,Integer type, Integer classify);
    
	public List getTunnelInfo(Long merchantPin, Integer type);
}
