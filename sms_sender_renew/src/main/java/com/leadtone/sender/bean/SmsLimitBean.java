package com.leadtone.sender.bean;

import java.util.Date;

/**
 * @author limh 
 * table : mbn_merchant_sms_mms_limit
 */
public class SmsLimitBean {
    private long merchantPin;
    private Integer smsGatewayDaily;
    private Integer smsGatewayMonth;
    private Integer smsTdDaily;
    private Integer smsTdHour;
    private Integer smsGatewayIntraday;
    private Integer smsGatewayCurrentMonth;
    private Integer smsTdCurrentHour;
    private Integer smsTdCurrentDaily;
    private Date modifyTime;
    private Date gatewayModifyTime;
    
	public long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(long merchantPin) {
		this.merchantPin = merchantPin;
	}
	public Integer getSmsGatewayDaily() {
		return smsGatewayDaily;
	}
	public void setSmsGatewayDaily(Integer smsGatewayDaily) {
		this.smsGatewayDaily = smsGatewayDaily;
	}
	public Integer getSmsGatewayMonth() {
		return smsGatewayMonth;
	}
	public void setSmsGatewayMonth(Integer smsGatewayMonth) {
		this.smsGatewayMonth = smsGatewayMonth;
	}
	public Integer getSmsTdDaily() {
		return smsTdDaily;
	}
	public void setSmsTdDaily(Integer smsTdDaily) {
		this.smsTdDaily = smsTdDaily;
	}
	public Integer getSmsTdHour() {
		return smsTdHour;
	}
	public void setSmsTdHour(Integer smsTdHour) {
		this.smsTdHour = smsTdHour;
	}
	public Integer getSmsGatewayIntraday() {
		return smsGatewayIntraday;
	}
	public void setSmsGatewayIntraday(Integer smsGatewayIntraday) {
		this.smsGatewayIntraday = smsGatewayIntraday;
	}
	public Integer getSmsGatewayCurrentMonth() {
		return smsGatewayCurrentMonth;
	}
	public void setSmsGatewayCurrentMonth(Integer smsGatewayCurrentMonth) {
		this.smsGatewayCurrentMonth = smsGatewayCurrentMonth;
	}
	public Integer getSmsTdCurrentHour() {
		return smsTdCurrentHour;
	}
	public void setSmsTdCurrentHour(Integer smsTdCurrentHour) {
		this.smsTdCurrentHour = smsTdCurrentHour;
	}
	public Integer getSmsTdCurrentDaily() {
		return smsTdCurrentDaily;
	}
	public void setSmsTdCurrentDaily(Integer smsTdCurrentDaily) {
		this.smsTdCurrentDaily = smsTdCurrentDaily;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getGatewayModifyTime() {
		return gatewayModifyTime;
	}
	public void setGatewayModifyTime(Date gatewayModifyTime) {
		this.gatewayModifyTime = gatewayModifyTime;
	}
    
}
