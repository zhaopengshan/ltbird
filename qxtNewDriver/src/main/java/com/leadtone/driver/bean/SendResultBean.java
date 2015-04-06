package com.leadtone.driver.bean;

public class SendResultBean {
	private String returnStatus;
	private String message;
	private String remainPoint;
	private String taskId;
	private String successCounts;
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRemainPoint() {
		return remainPoint;
	}
	public void setRemainPoint(String remainPoint) {
		this.remainPoint = remainPoint;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getSuccessCounts() {
		return successCounts;
	}
	public void setSuccessCounts(String successCounts) {
		this.successCounts = successCounts;
	}
}
