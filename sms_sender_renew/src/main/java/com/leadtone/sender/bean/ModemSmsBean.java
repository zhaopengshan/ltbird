package com.leadtone.sender.bean;

import java.util.Date;

public class ModemSmsBean {
	private static final long serialVersionUID = 4335340677184641441L;
	private Long id;
	private Long merchantPin;
	private Long operationId;
	private Long batchId;
	private String province;
	private java.lang.String selfMobile;
	private java.lang.String tos;
	private java.lang.String tosName;
	private java.lang.String content;
	private int cutApartNumber;
	private Date commitTime;
	private java.util.Date readySendTime;
	private java.util.Date expireTime;
	private Date completeTime;
	private String serviceId;
	private java.lang.Integer tunnelType;
	private java.lang.Integer priorityLevel;
	private Integer sendResult;
	private String failReason;
	private java.lang.String description;

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public java.lang.String getSelfMobile() {
		return selfMobile;
	}

	public void setSelfMobile(java.lang.String selfMobile) {
		this.selfMobile = selfMobile;
	}

	public java.lang.String getTos() {
		return tos;
	}

	public void setTos(java.lang.String tos) {
		this.tos = tos;
	}


	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public java.lang.String getTosName() {
		return tosName;
	}

	public void setTosName(java.lang.String tosName) {
		this.tosName = tosName;
	}

	public int getCutApartNumber() {
		return cutApartNumber;
	}

	public void setCutApartNumber(int cutApartNumber) {
		this.cutApartNumber = cutApartNumber;
	}

	public java.util.Date getReadySendTime() {
		return readySendTime;
	}

	public void setReadySendTime(java.util.Date readySendTime) {
		this.readySendTime = readySendTime;
	}

	public java.util.Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(java.util.Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public java.lang.Integer getTunnelType() {
		return tunnelType;
	}

	public void setTunnelType(java.lang.Integer tunnelType) {
		this.tunnelType = tunnelType;
	}

	public java.lang.Integer getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(java.lang.Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public Integer getSendResult() {
		return sendResult;
	}

	public void setSendResult(Integer sendResult) {
		this.sendResult = sendResult;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

}
