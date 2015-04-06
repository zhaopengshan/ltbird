package com.leadtone.mas.bizplug.common.ex.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {

	private static final long serialVersionUID = -3484018205802608462L;

	private String tableId;
	private List<Row> rows;
	private List<Column> columns;
	
	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
	public void addRow(Row row){
		if (this.rows==null) {
			this.rows = new ArrayList<Row>();
		}
		rows.add(row);
	}
	
	public void addRowList(List<Row> rowList){
		if (this.rows==null) {
			this.rows = new ArrayList<Row>();
		}
		rows.addAll(rowList);
	}

}
