package com.leadtone.sender.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author limh 
 * table : mbn_mms_ready_send
 */
public class MmsBean implements Serializable {

	private static final long serialVersionUID = 2792202074932963606L;
	private Long id;
	private Long merchantPin;
	private Long operationId;
	private Long mmsId;
	private java.lang.String tos;
	private java.util.Date expireTime;
	private String serviceId;
	private java.lang.Integer tunnelType;
	private java.lang.Integer priorityLevel;
	private Date commitTime;
	private java.lang.String selfMobile;
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

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
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

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public java.util.Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(java.util.Date expireTime) {
		this.expireTime = expireTime;
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

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public Long getMmsId() {
		return mmsId;
	}

	public void setMmsId(Long mmsId) {
		this.mmsId = mmsId;
	}

}