package com.leadtone.delegatemas.operation.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class PortalOperation {

	private Long id;
	private String name;
	private Integer operation;
	private Integer type;
	private Date createTime;
	private Date updateTime;
	private Long createBy;
	private Long updateBy;
	private String description;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOperation() {
		return operation;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
