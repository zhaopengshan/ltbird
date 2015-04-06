package com.leadtone.mas.bizplug.tunnel.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PAN-Z-G
 *	通道帐户类
 */
public class SmsTunnelAccount implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private Long id;				// '唯一标识' 
	private Long tunnelId;			//'通道标识' 
	private Long balanceNumber;		// '剩余条数' 
	private Float balanceAmount;	//'剩余钱' 
	private Date modifyTime;		//'修改时间' 
	public SmsTunnelAccount() {
		super();
		 
	}
	public SmsTunnelAccount(Long id) {
		super();
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public Long getTunnelId() {
		return tunnelId;
	}
	public Long getBalanceNumber() {
		return balanceNumber;
	}
	public Float getBalanceAmount() {
		return balanceAmount;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTunnelId(Long tunnelId) {
		this.tunnelId = tunnelId;
	}
	public void setBalanceNumber(Long balanceNumber) {
		this.balanceNumber = balanceNumber;
	}
	public void setBalanceAmount(Float balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	} 
}
