package com.leadtone.mas.bizplug.tunnel.bean;

public class MbnMerchantTunnelRelation {
	private Long id;
	private Long merchantPin;
	private Long tunnelId;
	private String accessNumber;
	private Float price;
	private Integer priorityLevel;
	private Integer state;
	private String tunnelExtCode;
	
	public MbnMerchantTunnelRelation(){
		
	}
	public MbnMerchantTunnelRelation(Long id, Long merchantPin, Long tunnelId, String accessNumber,
			Float price, Integer priorityLevel, Integer state, String tunnelExtCode){
		super();
		this.id = id;
		this.merchantPin= merchantPin;
		this.tunnelId = tunnelId;
		this.accessNumber = accessNumber;
		this.price = price;
		this.priorityLevel = priorityLevel;
		this.state = state;
		this.tunnelExtCode = tunnelExtCode;
	}
	public Integer getState() {
		return state;
	}
	
	public String getTunnelExtCode() {
		return tunnelExtCode;
	}

	public void setTunnelExtCode(String tunnelExtCode) {
		this.tunnelExtCode = tunnelExtCode;
	}

	public void setState(Integer state) {
		this.state = state;
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
	public Long getTunnelId() {
		return tunnelId;
	}
	public void setTunnelId(Long tunnelId) {
		this.tunnelId = tunnelId;
	}

	public String getAccessNumber() {
		return accessNumber;
	}
	public void setAccessNumber(String accessNumber) {
		this.accessNumber = accessNumber;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	
}
