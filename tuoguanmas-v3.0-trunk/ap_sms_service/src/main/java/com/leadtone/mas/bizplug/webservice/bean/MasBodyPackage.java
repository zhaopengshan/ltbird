package com.leadtone.mas.bizplug.webservice.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "body")
public class MasBodyPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3673003038484569763L;
	@XmlTransient
	private Ctrl ctrl;
	@XmlTransient
	private List<EntInfo> entInfoList;
	@XmlTransient
	private List<ConfigInfo> configInfoList;
	@XmlTransient
	private Register register;
	@XmlTransient
	private List<AdminInfo> adminInfoList;

	@XmlElement(name = "ctrl")
	public Ctrl getCtrl() {
		return ctrl;
	}

	public void setCtrl(Ctrl ctrl) {
		this.ctrl = ctrl;
	}

	@XmlElement(name = "entinfo")
	public List<EntInfo> getEntInfoList() {
		return entInfoList;
	}

	public void setEntInfoList(List<EntInfo> entInfoList) {
		this.entInfoList = entInfoList;
	}

	@XmlElement(name = "configinfo")
	public List<ConfigInfo> getConfigInfoList() {
		return configInfoList;
	}

	public void setConfigInfoList(List<ConfigInfo> configInfoList) {
		this.configInfoList = configInfoList;
	}

	@XmlElement(name = "register")
	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}
	@XmlElement(name = "admininfo")
	public List<AdminInfo> getAdminInfoList() {
		return adminInfoList;
	}

	public void setAdminInfoList(List<AdminInfo> adminInfoList) {
		this.adminInfoList = adminInfoList;
	}

}
