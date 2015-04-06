package com.leadtone.driver.bean;

public class ReportResult {
	private String mobile;
	private String taskId;
	private String status;
	private String receiveTime;
	private String errorCode;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public String toString() {
		return "ReportResult [mobile=" + mobile + ", taskId=" + taskId
				+ ", status=" + status + ", receiveTime=" + receiveTime
				+ ", errorCode=" + errorCode + "]";
	}
}
