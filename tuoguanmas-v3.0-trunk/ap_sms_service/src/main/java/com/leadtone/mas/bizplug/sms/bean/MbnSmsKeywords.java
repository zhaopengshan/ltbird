package com.leadtone.mas.bizplug.sms.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MbnSmsKeywords {
	private Long id;
	private Long merchantPin; // 商户PIN码,-1为系统级',
	private String keywords; // 短信内容
	private Long createBy; // 创建人
	private Date createTime; // 创建时间
	private Long updateBy; // 创建人
	private Date updateTime; // 创建时间

	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSONFieldBridge(impl = StringBridge.class)
	public Long getMerchantPin() {
		return merchantPin;
	}

	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public MbnSmsKeywords() {
		super();
	}

	public MbnSmsKeywords(Long id) {
		super();
		this.id = id;
	}

	public MbnSmsKeywords(Long id, Long merchantPin, String keywords,
			Long createBy, Date createTime, Long updateBy, Date updateTime) {
		super();
		this.id = id;
		this.merchantPin = merchantPin;
		this.keywords = keywords;
		this.createBy = createBy;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
	}

}
