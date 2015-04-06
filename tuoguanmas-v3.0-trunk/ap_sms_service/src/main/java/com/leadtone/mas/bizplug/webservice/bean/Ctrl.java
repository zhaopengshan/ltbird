package com.leadtone.mas.bizplug.webservice.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Ctrl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -516394851631791376L;
	@XmlTransient
	private String action;
	@XmlTransient
	private String cmd;
	@XmlTransient
	private String reason;

	@XmlElement(name = "action")
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@XmlElement(name = "cmd")
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@XmlElement(name = "reason")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Ctrl [action=" + action + ", reason=" + reason + "]";
	}

}
