package com.leadtone.mas.bizplug.dati.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;
/**
 * @author Chenxuezheng
 * TODO:答题bean
 * CreatedDate:2013-01-23 18:20
 * */
public class MasSmsDati implements Serializable {
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
	private long createBy;
	//任务编号 DT001
	private String taskNumber;
	//总分数
	private int score;
	//题目总数量
	private int dtSum;
	
	

	
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

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

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

	public long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}
	
	public String toString(){
		return new ToStringBuilder(this)
		.append("id",id)
		.append("title",title)
		.append("tos",tos)
		.append("commitTime",commitTime)
		.append("readySendTime",readySendTime)
		.append("beginTime",beginTime)
		.append("endTime",endTime)
		.append("content",content)
		.append("createBy",createBy).toString();
		
	}
	
	
	public int hashCode(){
		return new HashCodeBuilder(17, 37)
	       .append(id)
	       .append(title)
	       .append(tos)
	       .append(commitTime)
	       .append(readySendTime)
	       .append(beginTime)
	       .append(endTime)
	       .append(content)
	       .append(createBy)
	       .toHashCode();
	}

}
