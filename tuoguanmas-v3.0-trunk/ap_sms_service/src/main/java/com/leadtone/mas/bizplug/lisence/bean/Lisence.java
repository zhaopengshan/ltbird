package com.leadtone.mas.bizplug.lisence.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "Root")
public class Lisence {
	@XmlElement(name = "lisence_id")
	private Long lisenceId;
	@XmlElement(name = "lisence_value")
	private String lisenceValue;
	
	@XmlTransient
	public Long getLisenceId() {
		return lisenceId;
	}
	public void setLisenceId(Long lisenceId) {
		this.lisenceId = lisenceId;
	}
	@XmlTransient
	public String getLisenceValue() {
		return lisenceValue;
	}
	public void setLisenceValue(String lisenceValue) {
		this.lisenceValue = lisenceValue;
	}
}
