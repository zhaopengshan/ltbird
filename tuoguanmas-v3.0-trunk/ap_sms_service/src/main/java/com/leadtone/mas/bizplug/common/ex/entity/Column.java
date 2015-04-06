package com.leadtone.mas.bizplug.common.ex.entity;

import java.io.Serializable;

public class Column implements Serializable {
	private static final long serialVersionUID = -9218563200983045975L;
	private String columnName;
	private String dataName;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
}
