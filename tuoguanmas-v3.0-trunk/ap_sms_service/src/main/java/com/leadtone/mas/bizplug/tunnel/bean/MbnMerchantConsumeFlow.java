package com.leadtone.mas.bizplug.tunnel.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class MbnMerchantConsumeFlow {
	private Long id;
	private Long merchantPin;
	private Long tunnelId;
	private Integer operationType;
	private Long number;
	private Date modifyTime;
	private Long remainNumber;
	
	

	public Long getRemainNumber() {
		return remainNumber;
	}

	public void setRemainNumber(Long remainNumber) {
		this.remainNumber = remainNumber;
	}

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

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public MbnMerchantConsumeFlow() {
		super();
	}

	public MbnMerchantConsumeFlow(Long id, Long merchantPin, Long tunnelId,
			Integer operationType, Long number, Date modifyTime) {
		super();
		this.id = id;
		this.merchantPin = merchantPin;
		this.tunnelId = tunnelId;
		this.operationType = operationType;
		this.number = number;
		this.modifyTime = modifyTime;
	}
}
