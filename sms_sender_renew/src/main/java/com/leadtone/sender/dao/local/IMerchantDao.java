package com.leadtone.sender.dao.local;

import java.util.List;

import com.leadtone.sender.bean.ConfigParam;
import com.leadtone.sender.bean.Consume;

public interface IMerchantDao {
    public List getMerchantVip(Long merchantPin);
    
    public Consume getConsume(Long merchantPin, Integer type, Integer classify);
    
    public void updateConsume(Long id,Integer remainNumber);
    
    public ConfigParam getConfigParam(Long merchantPin, String name);
    
	public List getTunnelInfo(Long merchantPin,Integer type, Integer classify);
	public List getTunnelInfo(Long merchantPin, Integer type);
}
