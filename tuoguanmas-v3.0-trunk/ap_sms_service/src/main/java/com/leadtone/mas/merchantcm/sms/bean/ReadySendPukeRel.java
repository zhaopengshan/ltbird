package com.leadtone.mas.merchantcm.sms.bean;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class ReadySendPukeRel implements Serializable {

	private static final long serialVersionUID = 5009755079689419912L;
	private Long id;
	private Long readyId;
	private Long pukeId;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getReadyId() {
		return readyId;
	}
	public void setReadyId(Long readyId) {
		this.readyId = readyId;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getPukeId() {
		return pukeId;
	}
	public void setPukeId(Long pukeId) {
		this.pukeId = pukeId;
	}
	
}
