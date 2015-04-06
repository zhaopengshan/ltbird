package com.leadtone.mas.bizplug.webservice.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "configinfo")
public class ConfigInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7350306696909765086L;
	// entid 1 String 20 商户ID
	// name 1 String 255 配置项
	// itemvalue 1 String 64 值
	// desc ? String 64 描述
	// validflag 1 String 64 是否有效0时表示永久生效1生效 2无效
	@XmlTransient
	private Long entId;
	@XmlTransient
	private String name;
	@XmlTransient
	private String itemValue;
	@XmlTransient
	private String desc;
	@XmlTransient
	private Integer validFlag;
	
	@XmlElement(name = "entid")
	public Long getEntId() {
		return entId;
	}
	public void setEntId(Long entId) {
		this.entId = entId;
	}
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement(name = "itemvalue")
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	@XmlElement(name = "desc")
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@XmlElement(name = "validflag")
	public Integer getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}
	@Override
	public String toString() {
		return "ConfigInfo [desc=" + desc + ", entId=" + entId + ", itemValue="
				+ itemValue + ", name=" + name + ", validFlag=" + validFlag
				+ "]";
	}

}
