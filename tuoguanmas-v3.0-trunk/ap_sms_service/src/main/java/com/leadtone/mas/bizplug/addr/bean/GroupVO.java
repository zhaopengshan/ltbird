package com.leadtone.mas.bizplug.addr.bean;

public class GroupVO extends Group {
	private boolean isParent;
	private Integer counts;

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
		setParent(this.counts > 0);
	}

}
