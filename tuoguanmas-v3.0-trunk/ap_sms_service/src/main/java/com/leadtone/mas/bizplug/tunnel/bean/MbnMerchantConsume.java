package com.leadtone.mas.bizplug.tunnel.bean;

import java.util.Date;

public class MbnMerchantConsume {
	private Long id;
	private Long merchantPin;
	private Long tunnelId;
	private Long remainNumber;
	private Date modifyTime;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	public Long getTunnelId() {
		return tunnelId;
	}
	public void setTunnelId(Long tunnelId) {
		this.tunnelId = tunnelId;
	}
	public Long getRemainNumber() {
		return remainNumber;
	}
	public void setRemainNumber(Long remainNumber) {
		this.remainNumber = remainNumber;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public MbnMerchantConsume() {
		super();
	}
	public MbnMerchantConsume(Long id, Long merchantPin, Long tunnelId,
			Long remainNumber, Date modifyTime) {
		super();
		this.id = id;
		this.merchantPin = merchantPin;
		this.tunnelId = tunnelId;
		this.remainNumber = remainNumber;
		this.modifyTime = modifyTime;
	}	
}
