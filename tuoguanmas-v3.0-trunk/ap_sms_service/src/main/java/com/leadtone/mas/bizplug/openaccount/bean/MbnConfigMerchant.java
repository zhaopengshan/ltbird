package com.leadtone.mas.bizplug.openaccount.bean;

public class MbnConfigMerchant {
	private Long id;
	private Long merchantPin;
	private String name;
	private String itemValue;
	private String description;
	private Integer validFlag;
	 
	public MbnConfigMerchant() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public MbnConfigMerchant(Long merchantPin, String name, String itemValue,
			Integer validFlag) {
		super();
		this.merchantPin = merchantPin;
		this.name = name;
		this.itemValue = itemValue;
		this.validFlag = validFlag;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getValidFlag() {
		return validFlag;
	}


	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}
	
}
