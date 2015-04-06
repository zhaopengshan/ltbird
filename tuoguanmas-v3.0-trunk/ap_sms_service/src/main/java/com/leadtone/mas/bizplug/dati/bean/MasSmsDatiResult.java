package com.leadtone.mas.bizplug.dati.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts2.json.annotations.JSON;
/**
 * @author chenxuezheng
 * TODO:短信答题结果bean
 * CreateDate:2013-01-23 18:40
 * */
public class MasSmsDatiResult implements Serializable {
	//短信答题结果id
	private Long id;
	//短信答题id
	private Long dxdtId;
	//手机号码
	private String mobile;
	//答题人姓名
	private String name;
	//所答问题内容
	private String question;
	//答案
	private String answer;
	//本题分数
	private int score;
	//试卷中的题号
	private int orderNumber;
	//创建时间
	private Date createTime;
	//修改时间
	private Date modifyTime;
	//创建者
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
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
