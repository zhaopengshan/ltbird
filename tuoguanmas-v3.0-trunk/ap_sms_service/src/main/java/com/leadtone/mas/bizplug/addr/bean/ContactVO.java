package com.leadtone.mas.bizplug.addr.bean;

public class ContactVO extends Contact{

	private String groupName;	// 用于页面展示
	
	private Integer counts;		// 组下有多少个联系人

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	
}
