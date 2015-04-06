package com.leadtone.mas.bizplug.dati.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MasSmsDatiBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//答题id
	private Long id;
	//答题主题
	private String title;
	//答题接收人
	private String tos;
	//提交时间
	private Date commitTime;
	//准备发送时间
	private Date readySendTime;
	//答题开始时间
	private Date beginTime;
	//答题结束时间
	private Date endTime;
	//答题内容
	private String content;
	//答题创建者
	private String createBy;
	//任务编号 DT001
	private String taskNumber;
	//总分数
	private int score;
	//题目总数量
	private int dtSum;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTos() {
		return tos;
	}
	public void setTos(String tos) {
		this.tos = tos;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReadySendTime() {
		return readySendTime;
	}
	public void setReadySendTime(Date readySendTime) {
		this.readySendTime = readySendTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getDtSum() {
		return dtSum;
	}
	public void setDtSum(int dtSum) {
		this.dtSum = dtSum;
	}
	
	
}
