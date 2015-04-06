package com.leadtone.mas.connector.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信待发送表(数据库接口表)
 * @author admin
 *
 */
public class SmsOutboxDbIntf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7661100444383780873L;

	private String siSmsId;
	private String extCode;
	private String destAddr;
	private String messageContent;
	private Integer reqDeliveryReport;
	private Integer msgFmt;
	private Integer sendMethod;
	private Date requestTime;
	private String applicationId;
	private Long merchantPin;
	private String accountId;
	private String tunnelId;

	public String getSiSmsId() {
		return siSmsId;
	}

	public void setSiSmsId(String siSmsId) {
		this.siSmsId = siSmsId;
	}

	public String getExtCode() {
		return extCode;
	}

	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}

	public String getDestAddr() {
		return destAddr;
	}

	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getReqDeliveryReport() {
		return reqDeliveryReport;
	}

	public void setReqDeliveryReport(Integer reqDeliveryReport) {
		this.reqDeliveryReport = reqDeliveryReport;
	}

	public Integer getMsgFmt() {
		return msgFmt;
	}

	public void setMsgFmt(Integer msgFmt) {
		this.msgFmt = msgFmt;
	}

	public Integer getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(Integer sendMethod) {
		this.sendMethod = sendMethod;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public Long getMerchantPin() {
		return merchantPin;
	}

	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTunnelId() {
		return tunnelId;
	}

	public void setTunnelId(String tunnelId) {
		this.tunnelId = tunnelId;
	}

	@Override
	public String toString() {
		return "SmsOutboxDbIntf [accountId=" + accountId + ", applicationId="
				+ applicationId + ", destAddr=" + destAddr + ", extCode="
				+ extCode + ", merchantPin=" + merchantPin
				+ ", messageContent=" + messageContent + ", msgFmt=" + msgFmt
				+ ", reqDeliveryReport=" + reqDeliveryReport + ", requestTime="
				+ requestTime + ", sendMethod=" + sendMethod + ", siSmsId="
				+ siSmsId + ", tunnelId=" + tunnelId + "]";
	}

}
