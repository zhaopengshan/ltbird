package com.leadtone.mas.connector.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信已处理表(数据库接口表)
 * 
 * @author admin
 * 
 */
public class SmsSentDbIntf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8257660512377926570L;

	private String masSmsId;
	private String gwSmsId;
	private String siSmsId;
	private String extCode;
	private String destAddr;
	private Date requestTime;
	private Date sentTime;
	private Integer sentResult;
	private String smsStatus;
	private Date statusTime;
	private String applicationId;
	private Long merchantPin;
	private Long readySendId;

	public String getMasSmsId() {
		return masSmsId;
	}

	public void setMasSmsId(String masSmsId) {
		this.masSmsId = masSmsId;
	}

	public String getGwSmsId() {
		return gwSmsId;
	}

	public void setGwSmsId(String gwSmsId) {
		this.gwSmsId = gwSmsId;
	}

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

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getSentTime() {
		return sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	public Integer getSentResult() {
		return sentResult;
	}

	public void setSentResult(Integer sentResult) {
		this.sentResult = sentResult;
	}

	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
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

	public Long getReadySendId() {
		return readySendId;
	}

	public void setReadySendId(Long readySendId) {
		this.readySendId = readySendId;
	}

	@Override
	public String toString() {
		return "SmsSentDbIntf [applicationId=" + applicationId + ", destAddr="
				+ destAddr + ", extCode=" + extCode + ", gwSmsId=" + gwSmsId
				+ ", masSmsId=" + masSmsId + ", merchantPin=" + merchantPin
				+ ", readySendId=" + readySendId + ", requestTime="
				+ requestTime + ", sentResult=" + sentResult + ", sentTime="
				+ sentTime + ", siSmsId=" + siSmsId + ", smsStatus="
				+ smsStatus + ", statusTime=" + statusTime + "]";
	}

}
