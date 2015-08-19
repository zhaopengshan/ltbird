package com.changyuan.misc.demo.domain.user;

public class User {
	private Integer userId;
	private String userName;
	private int age;
	
	public User(){}
	
	public User(Integer userId, String userName, int age) {
		this.userId = userId;
		this.userName = userName;
		this.age = age;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
