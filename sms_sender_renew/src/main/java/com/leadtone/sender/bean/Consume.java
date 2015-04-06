package com.leadtone.sender.bean;

import java.util.Date;

public class Consume {
	private Long id;
	private Long merchantPin;
	private Long tunnelId;
	private Integer remainNumber;
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
	public Integer getRemainNumber() {
		return remainNumber;
	}
	public void setRemainNumber(Integer remainNumber) {
		this.remainNumber = remainNumber;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
