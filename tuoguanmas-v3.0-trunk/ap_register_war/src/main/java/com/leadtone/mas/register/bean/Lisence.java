package com.leadtone.mas.register.bean;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
@XmlRootElement(name = "Root")
public class Lisence {
	@XmlElement(name = "lisence_id")
	private Long lisenceId;
	@XmlElement(name = "lisence_value")
	private String lisenceValue;
	@XmlElement(name = "user_name")
	private String userName;
	@XmlElement(name = "user_pwd")
	private String userPwd;
	@XmlElement(name = "user_key")
	private String userKey;
	@XmlElement(name = "create_time")
	private Date createTime;
	@XmlElement(name = "update_time")
	private Date updateTime;
	
	@XmlTransient
	public Long getLisenceId() {
		return lisenceId;
	}
	public void setLisenceId(Long lisenceId) {
		this.lisenceId = lisenceId;
	}
	@XmlTransient
	public String getLisenceValue() {
		return lisenceValue;
	}
	public void setLisenceValue(String lisenceValue) {
		this.lisenceValue = lisenceValue;
	}
	@XmlTransient
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@XmlTransient
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	@XmlTransient
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	@XmlTransient
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@XmlTransient
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
