package com.leadtone.driver.bean;

public class ReceiveResult {
	
	private Long merchantPin;
	private String phone;
	private String content;
	private String datetime;
	private Integer procStatus;
	
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public Integer getProcStatus() {
		return procStatus;
	}
	public void setProcStatus(Integer procStatus) {
		this.procStatus = procStatus;
	}
}
