package com.leadtone.mas.bizplug.dati.pojo;

import java.io.Serializable;

public class MasSmsDatiTiKuInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 题目序号
	 * */
	private int serialId;
	/**
	 * 题库编号id
	 * */
	private long tikuId;

	public int getSerialId() {
		return serialId;
	}

	public void setSerialId(int serialId) {
		this.serialId = serialId;
	}

	public long getTikuId() {
		return tikuId;
	}

	public void setTikuId(long tikuId) {
		this.tikuId = tikuId;
	}

}
