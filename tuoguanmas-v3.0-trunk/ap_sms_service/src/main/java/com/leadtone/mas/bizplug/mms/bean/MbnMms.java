package com.leadtone.mas.bizplug.mms.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MbnMms {

	private Long id;
	private String title;
	private Long createBy; 
	private Long updateBy;
	private Date createTime;
    private Date updateTime;
    private Double attachmentSize;
    
    @JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
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
	public Double getAttachmentSize() {
		return attachmentSize;
	}
	public void setAttachmentSize(Double attachmentSize) {
		this.attachmentSize = attachmentSize;
	}
	
}
