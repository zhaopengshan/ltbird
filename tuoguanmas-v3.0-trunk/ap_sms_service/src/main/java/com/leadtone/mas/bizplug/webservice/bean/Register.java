package com.leadtone.mas.bizplug.webservice.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "register")
public class Register implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4327670949065724535L;
	@XmlTransient
	private String webServiceUrl;
	@XmlTransient
	private String ip;

	@XmlElement(name = "webserviceurl")
	public String getWebServiceUrl() {
		return webServiceUrl;
	}

	public void setWebServiceUrl(String webServiceUrl) {
		this.webServiceUrl = webServiceUrl;
	}

	@XmlElement(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "Register [ip=" + ip + ", webServiceUrl=" + webServiceUrl + "]";
	}

}
