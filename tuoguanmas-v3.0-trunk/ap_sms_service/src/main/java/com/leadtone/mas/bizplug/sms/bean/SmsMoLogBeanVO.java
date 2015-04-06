package com.leadtone.mas.bizplug.sms.bean;

public class SmsMoLogBeanVO extends SmsMoLogBean {
	private Long userId;
	private Long merchantPin;
	private String cpoid;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMerchantPin() {
		return merchantPin;
	}

	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	
	@Override
	public String toString(){
		return super.toString() + ", userId=" + userId + ", merchantPin=" + merchantPin+ ", cpoid=" + cpoid +"]";
		
	}

	public String getCpoid() {
		return cpoid;
	}

	public void setCpoid(String cpoid) {
		this.cpoid = cpoid;
	}
}
