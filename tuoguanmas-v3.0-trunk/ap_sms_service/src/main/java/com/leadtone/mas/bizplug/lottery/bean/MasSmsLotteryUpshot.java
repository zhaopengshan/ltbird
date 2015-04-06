package com.leadtone.mas.bizplug.lottery.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MasSmsLotteryUpshot {
	
	private long id;
	private long dxcjId;
	private String gradeLevelName;
	private String awardContent;
	private String name;
	private String mobile;
	private int state;
	private Date createTime;
	private long createBy;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}
	public MasSmsLotteryUpshot(long id, long dxcjId, String gradeLevelName,
			String awardContent, String name, String mobile, int state,
			Date createtime, long createBy) {
		super();
		this.id = id;
		this.dxcjId = dxcjId;
		this.gradeLevelName = gradeLevelName;
		this.awardContent = awardContent;
		this.name = name;
		this.mobile = mobile;
		this.state = state;
		this.createTime = createtime;
		this.createBy = createBy;
	}
	public MasSmsLotteryUpshot() {
		super();
	}
	
}
