package com.leadtone.mas.bizplug.dati.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts2.json.annotations.JSON;

public class MasSmsDatiShiJuan implements Serializable {
	//试卷id
	private Long id;
	//短信答题id
	private Long dxdtId;
	//顺序号
	private int orderNumber;
	//问题	
	private String question;
	//答案
	private String answer;
	//分数
	private int score;
	//创建时间
	private Date createTime;
	//修改时间
	private Date modifyTime;
	//创建者id
	private long createBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDxdtId() {
		return dxdtId;
	}

	public void setDxdtId(Long dxdtId) {
		this.dxdtId = dxdtId;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
		.append("dxdtId",dxdtId)
		.append("orderNumber",orderNumber)
		.append("question",question)
		.append("answer",answer)
		.append("createTime",createTime)
		.append("modifyTime",modifyTime)
		.append("createBy",createBy).toString();
		
	}
	
	
	public int hashCode(){
		return new HashCodeBuilder(17, 37)
	       .append(id)
	       .append(dxdtId)
	       .append(orderNumber)
	       .append(question)
	       .append(answer)
	       .append(createTime)
	       .append(modifyTime)
	       .append(createBy)
	       .toHashCode();
	}

}
