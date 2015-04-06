package com.leadtone.mas.bizplug.webservice.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "entinfo")
public class EntInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -251984701317765928L;
	@XmlTransient
	private Long entId;
	@XmlTransient
	private String name;
	@XmlTransient
	private String province;
	@XmlTransient
	private String city;
	@XmlTransient
	private String region;
	@XmlTransient
	private String platform;
	@XmlTransient
	private Integer corpId;
	@XmlTransient
	private String corpExt;
	@XmlTransient
	private List<ConfigInfo> configInfoList;
	@XmlTransient
	private List<TunnelInfo> tunnelInfoList;

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

	@XmlElement(name = "province")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@XmlElement(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement(name = "region")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@XmlElement(name = "platform")
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	@XmlElement(name = "corpid")
	public Integer getCorpId() {
		return corpId;
	}

	public void setCorpId(Integer corpId) {
		this.corpId = corpId;
	}

	@XmlElement(name = "corpext")
	public String getCorpExt() {
		return corpExt;
	}

	public void setCorpExt(String corpExt) {
		this.corpExt = corpExt;
	}

	@XmlElement(name = "configinfo")
	public List<ConfigInfo> getConfigInfoList() {
		return configInfoList;
	}

	public void setConfigInfoList(List<ConfigInfo> configInfoList) {
		this.configInfoList = configInfoList;
	}

	@XmlElement(name = "tunnelinfo")
	public List<TunnelInfo> getTunnelInfoList() {
		return tunnelInfoList;
	}

	public void setTunnelInfoList(List<TunnelInfo> tunnelInfoList) {
		this.tunnelInfoList = tunnelInfoList;
	}

	@Override
	public String toString() {
		return "EntInfo [city=" + city + ", configInfoList=" + configInfoList
				+ ", corpExt=" + corpExt + ", corpId=" + corpId + ", entId="
				+ entId + ", name=" + name + ", platform=" + platform
				+ ", province=" + province + ", region=" + region
				+ ", tunnelInfoList=" + tunnelInfoList + "]";
	}

}
