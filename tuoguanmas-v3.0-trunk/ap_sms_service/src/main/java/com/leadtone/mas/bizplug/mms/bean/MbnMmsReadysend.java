package com.leadtone.mas.bizplug.mms.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MbnMmsReadysend {
    private Long id;
    private Long operationId;
    private String selfMobile;
    private String tos;
    private Long mmsId;
    private Date readySendTime;
    private Date expireTime;
    private Long merchantPin;
    private String serviceId;
    private Integer tunnelType;
    private Integer priorityLevel;
    private Date commitTime;
    private Integer sendResult;
    private String description;
    private String frameXml;
    
    @JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JSONFieldBridge(impl = StringBridge.class)
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
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getMmsId() {
		return mmsId;
	}
	public void setMmsId(Long mmsId) {
		this.mmsId = mmsId;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	@JSONFieldBridge(impl = StringBridge.class)
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
	public Integer getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSendResult() {
		return sendResult;
	}
	public void setSendResult(Integer sendResult) {
		this.sendResult = sendResult;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReadySendTime() {
		return readySendTime;
	}
	public void setReadySendTime(Date readySendTime) {
		this.readySendTime = readySendTime;
	}
	public String getFrameXml() {
		return frameXml;
	}
	public void setFrameXml(String frameXml) {
		this.frameXml = frameXml;
	}
}
