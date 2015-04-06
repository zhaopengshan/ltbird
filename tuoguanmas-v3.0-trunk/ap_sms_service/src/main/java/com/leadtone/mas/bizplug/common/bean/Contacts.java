package com.leadtone.mas.bizplug.common.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class Contacts implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 955784730477679717L;
	
	private Long id;
	private String name;
	private String mobile;
	private Long parentId;
	private boolean isParent;
	private Integer counts;
	
	private Map<String, String> colMap ;	// 动态发送短信时，存放列头与列值
	public Contacts(){
		this.colMap = new HashMap<String, String>();
	}
	public Contacts(String mobile, String name){
		this.mobile = mobile;
		this.name = name;
		this.colMap = new HashMap<String, String>();
	}
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public String get(String key) {
		return colMap.get(key);
	}
	public void put(String key, String value) {
		this.colMap.put(key, value);
	}
	public void remove(String key){
		this.colMap.remove(key);
	}
}

