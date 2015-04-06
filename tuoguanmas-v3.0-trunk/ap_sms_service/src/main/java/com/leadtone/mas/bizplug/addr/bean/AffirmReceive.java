package com.leadtone.mas.bizplug.addr.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class AffirmReceive {

	private Long id;
	private Long merchantPin;
	private String province;
	private String customerMobile;
	private String receiverAccessNumber;
	private Integer operationId;
	private String content;
	private Date createTime; 
	private Date updateTime; 
	private Integer affirmTime;
	
	
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
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getReceiverAccessNumber() {
		return receiverAccessNumber;
	}
	public void setReceiverAccessNumber(String receiverAccessNumber) {
		this.receiverAccessNumber = receiverAccessNumber;
	}
	public Integer getOperationId() {
		return operationId;
	}
	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getAffirmTime() {
		return affirmTime;
	}
	public void setAffirmTime(Integer affirmTime) {
		this.affirmTime = affirmTime;
	}
}
