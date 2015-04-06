package com.leadtone.mas.bizplug.mms.bean;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MbnMmsAttachment {
	private Long id;
	private Long frameId;
	private Integer typeId;
	private String storePath;
	private String attachmentName;
	private Double attachmentSize;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getStorePath() {
		return storePath;
	}
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public Double getAttachmentSize() {
		return attachmentSize;
	}
	public void setAttachmentSize(Double attachmentSize) {
		this.attachmentSize = attachmentSize;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getFrameId() {
		return frameId;
	}
	public void setFrameId(Long frameId) {
		this.frameId = frameId;
	}
}
