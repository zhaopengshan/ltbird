package com.leadtone.mas.bizplug.mms.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MbnMmsFrame {
    private Long id;
    private String frameName;
    private String frameText;
    private Long mmsId;
    private Integer persistTime;
    
    @JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFrameName() {
		return frameName;
	}
	public void setFrameName(String frameName) {
		this.frameName = frameName;
	}
	public String getFrameText() {
		return frameText;
	}
	public void setFrameText(String frameText) {
		this.frameText = frameText;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getMmsId() {
		return mmsId;
	}
	public void setMmsId(Long mmsId) {
		this.mmsId = mmsId;
	}
	public Integer getPersistTime() {
		return persistTime;
	}
	public void setPersistTime(Integer persistTime) {
		this.persistTime = persistTime;
	}
}
