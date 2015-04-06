package com.leadtone.mas.bizplug.addr.bean;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

/**
 * 通迅录组
 * @author dongch
 *
 */
public class Group {

	private Long id ;
	private Long pid;
	private Long bookId;
	private String groupName;
	private String description;
	private Long createBy;
	private Integer shareType;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", pid=" + pid + ", bookId=" + bookId
				+ ", groupName=" + groupName + "]";
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Integer getShareType() {
		return shareType;
	}
	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}
	
}
