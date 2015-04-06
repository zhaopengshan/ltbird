package com.leadtone.mas.bizplug.common.ex.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sheet implements Serializable {
	private static final long serialVersionUID = 676436551426224361L;
	private String sheetId;
	private List<Table> tableList;
	private Table table;
	
	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	public List<Table> getTableList() {
		return tableList;
	}

	public void setTableList(List<Table> tableList) {
		this.tableList = tableList;
	}

	public void addTable(Table table) {
		if (tableList == null) {
			tableList = new ArrayList<Table>();
		}
		tableList.add(table);
	}
}
