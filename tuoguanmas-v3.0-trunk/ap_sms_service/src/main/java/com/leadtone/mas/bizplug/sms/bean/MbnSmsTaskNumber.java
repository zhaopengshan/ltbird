package com.leadtone.mas.bizplug.sms.bean;

import java.util.Date;

public class MbnSmsTaskNumber {
	private Long id;
	private Long merchantPin; // 商户PIN码,-1为系统级',
	private String operationCoding; // 业务编码
	private Long batchId; // '批次ID',
	private String taskNumber;// 短信任务号或题号三位数字的字符串
	private Date beginTime;// 开始时间
	private Date endTime;// 结束时间
	private Integer state;// 状态0过期；1生效
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
	public String getOperationCoding() {
		return operationCoding;
	}
	public void setOperationCoding(String operationCoding) {
		this.operationCoding = operationCoding;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
