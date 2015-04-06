package com.leadtone.mas.bizplug.config.bean;

import java.io.Serializable;

/**
 * 三位H码表 运营商级
 * 
 * @author admin
 * 
 */
public class MbnThreeHCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9089607937983675731L;
	private Long id;
	private String mobilePrefix;
	private String corp;

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

	public MbnThreeHCode() {

	}

	public MbnThreeHCode(Long id, String mobilePrefix, String corp) {
		super();
		this.id = id;
		this.mobilePrefix = mobilePrefix;
		this.corp = corp;
	}

}
