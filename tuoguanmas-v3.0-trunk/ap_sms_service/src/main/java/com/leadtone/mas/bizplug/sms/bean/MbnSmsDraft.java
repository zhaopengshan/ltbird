package com.leadtone.mas.bizplug.sms.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MbnSmsDraft {

	/**
	 * 
	 */
	private Long id;
	private Long merchantPin; // 商户PIN码,-1为系统级',
	private String content; // 短信内容
	private Long createBy; // 创建人
	private Long updateBy; // 更新人
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private String title;

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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public MbnSmsDraft(){
		super();
	}
	public MbnSmsDraft(Long id){
		super();
		this.id = id;
	}
	public MbnSmsDraft(Long id, Long merchantPin, String content,
			Long createBy, Long updateBy, Date createTime, Date updateTime, String title) {
		super();
		this.id = id;
		this.merchantPin = merchantPin;
		this.content = content;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.title = title;
	}
}
