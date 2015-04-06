package com.leadtone.mas.bizplug.tunnel.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;
/**
 * 
 * @author PAN-Z-G
 * 通道帐户流水类
 */
public class SmsMbnTunnelAccountFlow implements Serializable {

	private static final long serialVersionUID = 1L; 
	 private Long id; 
	 private Long tunnelId;//,
	 private Integer operationType;//'1向代理商购买；2代理商赠送；3代理商退钱；4商户购买',
	 private Long number;//'条数',
	 private Float amount ;//'相关金额',
	 private Date modifyTime;//操作时间 
	 private String modifyBy;//'操作者，可能是系统管理员或企业',
	 private Long modifyByPin;//'操作者pin码，可能是系统管理员pin码或企业pin码', 
	 private Long balanceNumber;//当前短信余量
	 /**
	  * id构造;一般用于对象根据id查询
	  * @param id
	  */
	 public SmsMbnTunnelAccountFlow(Long id) {
		super();
		this.id = id;
	}
	public SmsMbnTunnelAccountFlow() {
		super();
		//  
	}
	/**
	  * 访问器
	  * @return
	  */
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public Long getTunnelId() {
		return tunnelId;
	}
	public Integer getOperationType() {
		return operationType;
	}
	public Long getNumber() {
		return number;
	}
	public Float getAmount() {
		return amount;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyTime() {
		return modifyTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public Long getModifyByPin() {
		return modifyByPin;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTunnelId(Long tunnelId) {
		this.tunnelId = tunnelId;
	}
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public void setModifyByPin(Long modifyByPin) {
		this.modifyByPin = modifyByPin;
	}
	public Long getBalanceNumber() {
		return balanceNumber;
	}
	public void setBalanceNumber(Long balanceNumber) {
		this.balanceNumber = balanceNumber;
	}
 
	 
}
