package com.leadtone.mas.bizplug.addr.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class Contact {

	private Long id ; 
	private Long bookId;
	private Long bookGroupId;
	private String name;
	private Integer gender;
	private Date birthday;
	private String mobile;
	private String telephone;
	private String address;
	private String company;
	private String preferences;
	
//	private Integer smsTunnel;
//	private Integer mmsTunnel;
	private Integer customerAffirmFlag; 
	
	private Date lastModifyTime;
	private String identificationNumber;
	private String email;
	private String qqNumber;
	private String msn;
	private String microBlog;
	private String vpmn;
	private Date createTime;
	private String description;
	
	private Integer merchantBlackFlag;
	private Long createBy;
	
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getBookGroupId() {
		return bookGroupId;
	}
	public void setBookGroupId(Long bookGroupId) {
		this.bookGroupId = bookGroupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPreferences() {
		return preferences;
	}
	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
//	public Integer getSmsTunnel() {
//		return smsTunnel;
//	}
//	public void setSmsTunnel(Integer smsTunnel) {
//		this.smsTunnel = smsTunnel;
//	}
//	public Integer getMmsTunnel() {
//		return mmsTunnel;
//	}
//	public void setMmsTunnel(Integer mmsTunnel) {
//		this.mmsTunnel = mmsTunnel;
//	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getMicroBlog() {
		return microBlog;
	}
	public void setMicroBlog(String microBlog) {
		this.microBlog = microBlog;
	}
	public String getVpmn() {
		return vpmn;
	}
	public void setVpmn(String vpmn) {
		this.vpmn = vpmn;
	}
	public Integer getMerchantBlackFlag() {
		return merchantBlackFlag;
	}
	public void setMerchantBlackFlag(Integer merchantBlackFlag) {
		this.merchantBlackFlag = merchantBlackFlag;
	}
	public Integer getCustomerAffirmFlag() {
		return customerAffirmFlag;
	}
	public void setCustomerAffirmFlag(Integer customerAffirmFlag) {
		this.customerAffirmFlag = customerAffirmFlag;
	}
	@Override
	public String toString() {
		return "Contact [id=" + id + ", bookId=" + bookId + ", bookGroupId="
				+ bookGroupId + ", name=" + name + ", gender=" + gender
				+ ", birthday=" + birthday + ", mobile=" + mobile
				+ ", telephone=" + telephone + ", address=" + address
				+ ", company=" + company + ", preferences=" + preferences
	//			+ ", smsTunnel=" + smsTunnel + ", mmsTunnel=" + mmsTunnel
				+ ", customerAffirmFlag=" + customerAffirmFlag
				+ ", lastModifyTime=" + lastModifyTime
				+ ", identificationNumber=" + identificationNumber + ", email="
				+ email + ", qqNumber=" + qqNumber + ", msn=" + msn
				+ ", microBlog=" + microBlog + ", vpmn=" + vpmn
				+ ", createTime=" + createTime + ", description=" + description
				+ ", merchantBlackFlag=" + merchantBlackFlag + "]";
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	
	
	
}
