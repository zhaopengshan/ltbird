package com.leadtone.mas.bizplug.webservice.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class AdminInfo implements Serializable {
	private static final long serialVersionUID = -8799731284966466218L;
	@XmlTransient
	private Long id;
	@XmlTransient
	private String account;
	@XmlTransient
	private String password;
	// 短信扩展码
	@XmlTransient
	private String userExtCode;
	// 用户企业端口号
	@XmlTransient
	private String corpAccessNumber;
	@XmlTransient
	private Long merchantPin;
	@XmlTransient
	private String name;
	@XmlTransient
	private String mobile;
	@XmlTransient
	private Integer loginType;
	@XmlTransient
	private String email;
	@XmlTransient
	private String departmentName;
	@XmlTransient
	private String duty;
	@XmlTransient
	private Integer gender;
	@XmlTransient
	private Integer userType;
	@XmlTransient
	private Integer lockFlag;
	@XmlTransient
	private Integer limitTryCount;
	@XmlTransient
	private Integer activeFlag;
	@XmlTransient
	private Long createBy;
	@XmlTransient
	private Integer ipLimitFlag;
	@XmlTransient
	private String ipAddress;
	@XmlTransient
	private String province;
	@XmlTransient
	private String city;
	@XmlTransient
	private String region;
	@XmlTransient
	private Integer webService;
	// 资信通用户ID
	@XmlTransient
	private String zxtUserId;
	// 企信通登陆帐户
	@XmlTransient
	private String zxtLoginAcount;
	// 企信能登陆密码
	@XmlTransient
	private String zxtPwd;
	// 企信通用户ID
	@XmlTransient
	private Integer zxtId;

	// 首次登录标识
	@XmlTransient
	private Integer firstLoginFlag;
	
	// 是否启用发送限制 0无限制 1限制
	@XmlTransient
	private Integer smsLimit;
	// 限制统计周期 0日 1月
	@XmlTransient
	private Integer smsLimitPeriod;
	// 周期内可发送数量
	@XmlTransient
	private Integer smsLimitCount;
	// 用户发送短信优先级
	@XmlTransient
	private Integer smsPriority;
	
	@XmlTransient
	private String smsMobile;
	

	@JSONFieldBridge(impl = StringBridge.class)
	@XmlElement(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "loginaccount")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@XmlElement(name = "loginpwd")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name = "entid")
	public Long getMerchantPin() {
		return merchantPin;
	}

	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@XmlElement(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(name = "departmentname")
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@XmlElement(name = "duty")
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@XmlElement(name = "gender")
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@XmlElement(name = "usertype")
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@XmlElement(name = "lockflag")
	public Integer getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}

	@XmlElement(name = "limittrycount")
	public Integer getLimitTryCount() {
		return limitTryCount;
	}

	public void setLimitTryCount(Integer limitTryCount) {
		this.limitTryCount = limitTryCount;
	}

	@XmlElement(name = "activeflag")
	public Integer getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Integer activeFlag) {
		this.activeFlag = activeFlag;
	}

	@XmlElement(name = "createby")
	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	@XmlElement(name = "iplimitflag")
	public Integer getIpLimitFlag() {
		return ipLimitFlag;
	}

	public void setIpLimitFlag(Integer ipLimitFlag) {
		this.ipLimitFlag = ipLimitFlag;
	}

	@XmlElement(name = "ipaddress")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@XmlElement(name = "logintype")
	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	@XmlElement(name = "province")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@XmlElement(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement(name = "region")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@XmlElement(name = "uesrextcode")
	public String getUserExtCode() {
		return userExtCode;
	}

	public void setUserExtCode(String userExtCode) {
		this.userExtCode = userExtCode;
	}

	@XmlElement(name = "corpaccessnumber")
	public String getCorpAccessNumber() {
		return corpAccessNumber;
	}

	public void setCorpAccessNumber(String corpAccessNumber) {
		this.corpAccessNumber = corpAccessNumber;
	}

	@XmlElement(name = "webservice")
	public Integer getWebService() {
		return webService;
	}

	public void setWebService(Integer webService) {
		this.webService = webService;
	}

	@XmlElement(name = "zxtuserid")
	public String getZxtUserId() {
		return zxtUserId;
	}

	public void setZxtUserId(String zxtUserId) {
		this.zxtUserId = zxtUserId;
	}

	@XmlElement(name = "firstloginflag")
	public Integer getFirstLoginFlag() {
		return firstLoginFlag;
	}

	public void setFirstLoginFlag(Integer firstLoginFlag) {
		this.firstLoginFlag = firstLoginFlag;
	}

	@XmlElement(name = "zxtloginaccount")
	public String getZxtLoginAcount() {
		return zxtLoginAcount;
	}

	public void setZxtLoginAcount(String zxtLoginAcount) {
		this.zxtLoginAcount = zxtLoginAcount;
	}

	@XmlElement(name = "zxtpwd")
	public String getZxtPwd() {
		return zxtPwd;
	}

	public void setZxtPwd(String zxtPwd) {
		this.zxtPwd = zxtPwd;
	}

	@XmlElement(name = "zxtid")
	public Integer getZxtId() {
		return zxtId;
	}

	public void setZxtId(Integer zxtId) {
		this.zxtId = zxtId;
	}

	@XmlElement(name = "smslimit")
	public Integer getSmsLimit() {
		return smsLimit;
	}

	public void setSmsLimit(Integer smsLimit) {
		this.smsLimit = smsLimit;
	}
	
	@XmlElement(name = "smspimitperiod")
	public Integer getSmsLimitPeriod() {
		return smsLimitPeriod;
	}

	public void setSmsLimitPeriod(Integer smsLimitPeriod) {
		this.smsLimitPeriod = smsLimitPeriod;
	}
	@XmlElement(name = "smslimitcount")
	public Integer getSmsLimitCount() {
		return smsLimitCount;
	}

	public void setSmsLimitCount(Integer smsLimitCount) {
		this.smsLimitCount = smsLimitCount;
	}
	@XmlElement(name = "smspriority")
	public Integer getSmsPriority() {
		return smsPriority;
	}

	public void setSmsPriority(Integer smsPriority) {
		this.smsPriority = smsPriority;
	}

	@Override
	public String toString() {
		return "AdminInfo [id=" + id + ", account=" + account + ", password="
				+ password + ", userExtCode=" + userExtCode
				+ ", corpAccessNumber=" + corpAccessNumber + ", merchantPin="
				+ merchantPin + ", name=" + name + ", mobile=" + mobile
				+ ", loginType=" + loginType + ", email=" + email
				+ ", departmentName=" + departmentName + ", duty=" + duty
				+ ", gender=" + gender + ", userType=" + userType
				+ ", lockFlag=" + lockFlag + ", limitTryCount=" + limitTryCount
				+ ", activeFlag=" + activeFlag + ", createBy=" + createBy
				+ ", ipLimitFlag=" + ipLimitFlag + ", ipAddress=" + ipAddress
				+ ", province=" + province + ", city=" + city + ", region="
				+ region + ", webService=" + webService + ", zxtUserId="
				+ zxtUserId + ", zxtLoginAcount=" + zxtLoginAcount
				+ ", zxtPwd=" + zxtPwd + ", zxtId=" + zxtId
				+ ", firstLoginFlag=" + firstLoginFlag + ", smsLimit="
				+ smsLimit + ", smsLimitPeriod=" + smsLimitPeriod
				+ ", smsLimitCount=" + smsLimitCount + ", smsPriority="
				+ smsPriority + ", smsMobile=" + smsMobile + "]";
	}

	public String getSmsMobile() {
		return smsMobile;
	}

	public void setSmsMobile(String smsMobile) {
		this.smsMobile = smsMobile;
	}
}
