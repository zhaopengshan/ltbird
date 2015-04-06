package com.leadtone.mas.bizplug.dati.bean;

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;
/**
 * TODO:短信答题题库
 * @author chenxuezheng
 * CreateDate:2013-01-23
 * */
public class MasSmsDatiTiKu {
	//题库id
	private Long id;
	//题库序号
	private Integer questionNumber;
	//题库问题
	private String question;
	//题库答案
	private String answer;
	//题库问题分数
	private Integer score;
	//创建时间
	private Date createTime;
	//修改时间
	private Date modifyTime;
	//创建人
	private long createBy;
	//题库状态 0：正常  -1：删除
	private int status;

	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
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
	
	
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString(){
		return new ToStringBuilder(this)
		.append("id",id)
		.append("questionNumber",questionNumber)
		.append("question",question)
		.append("answer",answer)
		.append("score",score)
		.append("createTime",createTime)
		.append("createBy",createBy).toString();
		
	}
	
	
	public int hashCode(){
		return new HashCodeBuilder(17, 37)
	       .append(id)
	       .append(questionNumber)
	       .append(question)
	       .append(answer)
	       .append(score)
	       .append(createTime)
	       .append(modifyTime)
	       .append(createBy)
	       .toHashCode();
	}

}
