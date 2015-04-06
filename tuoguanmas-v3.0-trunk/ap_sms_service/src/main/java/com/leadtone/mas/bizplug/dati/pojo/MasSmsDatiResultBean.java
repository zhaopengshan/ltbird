package com.leadtone.mas.bizplug.dati.pojo;

import java.io.Serializable;

public class MasSmsDatiResultBean implements Serializable {
	private Long id;
	private String mobile;
	private String name;
	private int sumScore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getSumScore() {
		return sumScore;
	}

	public void setSumScore(int sumScore) {
		this.sumScore = sumScore;
	}

}
