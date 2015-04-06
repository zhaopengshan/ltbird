package com.leadtone.mas.bizplug.mms.bean;

import java.util.Date;

public class MbnMmsHadsend {
    private Long id;
    private Long operationId;
    private String selfMobile;
    private String tos;
    private Long mmsId;
    private Date completeTime;
    private Long merchantPin;
    private String serviceId;
    private Integer tunnelType;
    private Date commitTime;
    private Date readySendTime;
    private Integer sendResult;
    private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOperationId() {
		return operationId;
	}
	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
	public String getSelfMobile() {
		return selfMobile;
	}
	public void setSelfMobile(String selfMobile) {
		this.selfMobile = selfMobile;
	}
	public String getTos() {
		return tos;
	}
	public void setTos(String tos) {
		this.tos = tos;
	}
	public Long getMmsId() {
		return mmsId;
	}
	public void setMmsId(Long mmsId) {
		this.mmsId = mmsId;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getTunnelType() {
		return tunnelType;
	}
	public void setTunnelType(Integer tunnelType) {
		this.tunnelType = tunnelType;
	}
	public Date getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}
	public Date getReadysendTime() {
		return readySendTime;
	}
	public void setReadysendTime(Date readySendTime) {
		this.readySendTime = readySendTime;
	}
	public Integer getSendResult() {
		return sendResult;
	}
	public void setSendResult(Integer sendResult) {
		this.sendResult = sendResult;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
