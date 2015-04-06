package com.leadtone.mas.bizplug.common.ex.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkBook implements Serializable{

	private static final long serialVersionUID = -111108734281590115L;

	private Sheet sheet;

	private List<Sheet> sheetList;

	public WorkBook(){
		sheetList = new ArrayList<Sheet>();
	}
	
	public List<Sheet> getSheetList() {
		return sheetList;
	}

	public void setSheetList(List<Sheet> sheetList) {
		this.sheetList = sheetList;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	
	public void addSheet(Sheet sheet){
		this.getSheetList().add(sheet);
	}
}
