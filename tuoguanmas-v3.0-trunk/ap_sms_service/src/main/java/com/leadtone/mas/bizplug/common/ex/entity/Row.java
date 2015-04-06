package com.leadtone.mas.bizplug.common.ex.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Row implements Serializable {
	private static final long serialVersionUID = -5581508627111167220L;
	private long rowId;
	private Map<String, String> rowDatas = new HashMap<String, String>();
	private Map<String, Table> rowTable = new HashMap<String, Table>();

	public long getRowId() {
		return rowId;
	}

	public void setRowId(long rowId) {
		this.rowId = rowId;
	}

	public Map<String, String> getRowDatas() {
		return rowDatas;
	}

	public void setRowDatas(Map<String, String> rowDatas) {
		this.rowDatas = rowDatas;
	}

	public Map<String, Table> getRowTable() {
		return rowTable;
	}

	public void setRowTable(Map<String, Table> rowTable) {
		this.rowTable = rowTable;
	}
	
	public void addRowTable(String name,Table table){
		this.rowTable.put(name, table);
	}
}
