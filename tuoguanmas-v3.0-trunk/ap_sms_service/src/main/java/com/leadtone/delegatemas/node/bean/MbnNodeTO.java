package com.leadtone.delegatemas.node.bean;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MbnNodeTO {
	private Long id;				// 主键
	private String name;			// 节点名称
	private boolean isSelected;		// 选中与否
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean getIsSelected(){
		return isSelected;
	}
}
