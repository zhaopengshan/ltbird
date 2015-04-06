package com.leadtone.mas.bizplug.config.bean;

import java.io.Serializable;

/**
 * 三位H码表 运营商级
 * 
 * @author admin
 * 
 */
public class MbnSevenHCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9089607937983675731L;
	private Long id;
	private String mobilePrefix;
	private String corp;
	private String provinceCoding;
	private String city;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobilePrefix() {
		return mobilePrefix;
	}

	public void setMobilePrefix(String mobilePrefix) {
		this.mobilePrefix = mobilePrefix;
	}

	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getProvinceCoding() {
		return provinceCoding;
	}

	public void setProvinceCoding(String provinceCoding) {
		this.provinceCoding = provinceCoding;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public MbnSevenHCode() {

	}

	public MbnSevenHCode(Long id, String mobilePrefix, String corp,
			String provinceCoding, String city) {
		super();
		this.id = id;
		this.mobilePrefix = mobilePrefix;
		this.corp = corp;
		this.provinceCoding = provinceCoding;
		this.city = city;
	}

}
