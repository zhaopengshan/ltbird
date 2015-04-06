package com.leadtone.mas.bizplug.sms.bean;

import java.sql.Timestamp;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;


public class SmsMoLogBean {
	private Long id;
	private String sender;
	private String receiver;
	private String content;
	private Timestamp createTime;
	private Integer status;
	
	private String type;
	private Integer classify;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SmsMoLog [sender=" + sender + ", receiver=" + receiver + ", create_time=" + createTime + ", type=" + type + ", content=" + content + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

}
