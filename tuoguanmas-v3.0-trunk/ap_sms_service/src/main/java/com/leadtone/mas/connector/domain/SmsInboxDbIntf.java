package com.leadtone.mas.connector.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 上行短信表(数据接口表)
 * 
 * @author admin
 * 
 */
public class SmsInboxDbIntf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6002524099868144999L;
	// Field,Type,Null,Key,Default,Extra
	// MASSMSID,varchar(50),NO,PRI,NULL,
	// EXTCODE,varchar(21),YES,,NULL,
	// SOURCEADDR,varchar(21),NO,MUL,NULL,
	// RECEIVETIME,datetime,NO,,NULL,
	// MESSAGECONTENT,varchar(400),YES,,NULL,
	// MSGFMT,int(4),NO,,0,
	// REQUESTTIME,timestamp,NO,,CURRENT_TIMESTAMP,
	// APPLICATIONID,varchar(16),NO,,NULL,
	// MERCHANT_PIN,bigint(20),NO,,NULL,

	private String masSmsId;
	private String extCode;
	private String sourceAddr;
	private Date receiveTime;
	private String messageContent;
	private Integer msgFmt;
	private Date requestTime;
	private String applicationId;
	private Long merchantPin;

	public String getMasSmsId() {
		return masSmsId;
	}

	public void setMasSmsId(String masSmsId) {
		this.masSmsId = masSmsId;
	}

	public String getExtCode() {
		return extCode;
	}

	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}

	public String getSourceAddr() {
		return sourceAddr;
	}

	public void setSourceAddr(String sourceAddr) {
		this.sourceAddr = sourceAddr;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getMsgFmt() {
		return msgFmt;
	}

	public void setMsgFmt(Integer msgFmt) {
		this.msgFmt = msgFmt;
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

	@Override
	public String toString() {
		return "SmsInboxDbIntf [applicationId=" + applicationId + ", extCode="
				+ extCode + ", masSmsId=" + masSmsId + ", merchantPin="
				+ merchantPin + ", messageContent=" + messageContent
				+ ", msgFmt=" + msgFmt + ", receiveTime=" + receiveTime
				+ ", requestTime=" + requestTime + ", sourceAddr=" + sourceAddr
				+ "]";
	}

}
