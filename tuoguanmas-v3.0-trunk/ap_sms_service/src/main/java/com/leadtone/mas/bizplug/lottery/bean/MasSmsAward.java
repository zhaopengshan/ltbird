package com.leadtone.mas.bizplug.lottery.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MasSmsAward {
	private long id;
	private long dxcjId;
	private String gradeLevelName;
	private String awardContent;
	private int orderNumber;
	private int quotaOfPeople;
	private Date createTime;
	private Date modifyTime;
	private long createBy;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public long getDxcjId() {
		return dxcjId;
	}
	public void setDxcjId(long dxcjId) {
		this.dxcjId = dxcjId;
	}
	public String getGradeLevelName() {
		return gradeLevelName;
	}
	public void setGradeLevelName(String gradeLevelName) {
		this.gradeLevelName = gradeLevelName;
	}
	public String getAwardContent() {
		return awardContent;
	}
	public void setAwardContent(String awardContent) {
		this.awardContent = awardContent;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public int getQuotaOfPeople() {
		return quotaOfPeople;
	}
	public void setQuotaOfPeople(int quotaOfPeople) {
		this.quotaOfPeople = quotaOfPeople;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}
	public MasSmsAward(long id, long dxcjId, String gradeLevelName,
			String awardContent, int orderNumber, int quotaOfPeople,
			Date createTime, Date modifyTime, long createBy) {
		super();
		this.id = id;
		this.dxcjId = dxcjId;
		this.gradeLevelName = gradeLevelName;
		this.awardContent = awardContent;
		this.orderNumber = orderNumber;
		this.quotaOfPeople = quotaOfPeople;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.createBy = createBy;
	}
	public MasSmsAward() {
		super();
	}

	
}
