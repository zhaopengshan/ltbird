package com.leadtone.mas.bizplug.security.bean;
import java.util.Date;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;


public class Users {
	private Long id;
	private String account;
	private String password;
	//短信扩展码
	private String userExtCode;
	//用户企业端口号
	private String corpAccessNumber;
	private Long merchantPin;
	private String name;
	private String mobile;
	private Integer loginType;
	
	private String email;
	private String departmentName;
	private String duty;
	private Integer gender;
	
	private Integer userType;
	private Integer lockFlag;
	private Integer limitTryCount;
	private Integer activeFlag;
	
	private Date loginTime ;
	private Date createTime ;
	private Date updateTime ;
	
	private Long createBy; 
	private Long updateBy;
	
	private Integer ipLimitFlag;
	private String ipAddress;
	private String province;
	private String city;
	private String region;
	
	private Integer webService;
	// 资信通用户ID
	private String zxtUserId;
	// 企信通登陆帐户
	private String zxtLoginAcount;
	// 企信能登陆密码
	private String zxtPwd;
	// 企信通用户ID
	private Integer zxtId;
	
	//首次登录标识
	private Integer firstLoginFlag;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}
	public Integer getLimitTryCount() {
		return limitTryCount;
	}
	public void setLimitTryCount(Integer limitTryCount) {
		this.limitTryCount = limitTryCount;
	}
	public Integer getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(Integer activeFlag) {
		this.activeFlag = activeFlag;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Integer getIpLimitFlag() {
		return ipLimitFlag;
	}
	public void setIpLimitFlag(Integer ipLimitFlag) {
		this.ipLimitFlag = ipLimitFlag;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getLoginType() {
		return loginType;
	}
	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "Users [account=" + account + ", activeFlag=" + activeFlag
				+ ", city=" + city + ", createBy=" + createBy + ", createTime="
				+ createTime + ", departmentName=" + departmentName + ", duty="
				+ duty + ", email=" + email + ", gender=" + gender + ", id="
				+ id + ", ipAddress=" + ipAddress + ", ipLimitFlag="
				+ ipLimitFlag + ", limitTryCount=" + limitTryCount
				+ ", lockFlag=" + lockFlag + ", loginTime=" + loginTime
				+ ", loginType=" + loginType + ", merchantPin=" + merchantPin
				+ ", mobile=" + mobile + ", name=" + name + ", password="
				+ password + ", province=" + province + ", region=" + region
				+ ", updateBy=" + updateBy + ", updateTime=" + updateTime
				+ ", userType=" + userType + "]";
	}
	public String getUserExtCode() {
		return userExtCode;
	}
	public void setUserExtCode(String userExtCode) {
		this.userExtCode = userExtCode;
	}
	public String getCorpAccessNumber() {
		return corpAccessNumber;
	}
	public void setCorpAccessNumber(String corpAccessNumber) {
		this.corpAccessNumber = corpAccessNumber;
	}
	public Integer getWebService() {
		return webService;
	}
	public void setWebService(Integer webService) {
		this.webService = webService;
	}
	public String getZxtUserId() {
		return zxtUserId;
	}
	public void setZxtUserId(String zxtUserId) {
		this.zxtUserId = zxtUserId;
	}
	public Integer getFirstLoginFlag() {
		return firstLoginFlag;
	}
	public void setFirstLoginFlag(Integer firstLoginFlag) {
		this.firstLoginFlag = firstLoginFlag;
	}
	public String getZxtLoginAcount() {
		return zxtLoginAcount;
	}
	public void setZxtLoginAcount(String zxtLoginAcount) {
		this.zxtLoginAcount = zxtLoginAcount;
	}
	public String getZxtPwd() {
		return zxtPwd;
	}
	public void setZxtPwd(String zxtPwd) {
		this.zxtPwd = zxtPwd;
	}
	public Integer getZxtId() {
		return zxtId;
	}
	public void setZxtId(Integer zxtId) {
		this.zxtId = zxtId;
	}
	
}
