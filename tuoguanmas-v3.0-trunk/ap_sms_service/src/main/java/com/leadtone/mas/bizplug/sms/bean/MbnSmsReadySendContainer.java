package com.leadtone.mas.bizplug.sms.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author admin
 * 
 */
public class MbnSmsReadySendContainer {
	private Long merchantPin;
	private Map<Long, List<MbnSmsReadySend>> smsHashMap = new HashMap<Long, List<MbnSmsReadySend>>();

	public Long getMerchantPin() {
		return merchantPin;
	}

	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}

	public Map<Long, List<MbnSmsReadySend>> getSmsHashMap() {
		return smsHashMap;
	}

	public void setSmsHashMap(Map<Long, List<MbnSmsReadySend>> smsHashMap) {
		this.smsHashMap = smsHashMap;
	}
	
	public void addSmsMap(Long tunnelId, List<MbnSmsReadySend> smsList){
		List<MbnSmsReadySend> exitList = this.smsHashMap.get(tunnelId);
		if(exitList!=null&&exitList.size()>0){
			exitList.addAll(smsList);
			this.smsHashMap.put(tunnelId, exitList);
		}else{
			this.smsHashMap.put(tunnelId, smsList);
		}
	}
}
