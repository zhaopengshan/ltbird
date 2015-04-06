package com.leadtone.mas.connector.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author hejiyong
 * date:2013-1-22
 * 
 */
public class PortalUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9194351265239352196L;
	private long id;
	private long merchant_pin;
	private String login_account;
	private String login_pwd;
	private String user_ext_code;
	private int login_type;
	private String name;
	private String mobile;
	private String email;
	private String department_name;
	private String duty;
	private int gender;
	private int user_type;
	private int lock_flag;
	private int limit_try_count;
	private int active_flag;
	private Date login_time;
	private Date create_time;
	private Date update_time;
	private long create_by;
	private long update_by;
	private int ip_limit_flag;
	private String ip_address;
	private String province;
	private String city;
	private String region;
	private String corp_access_number;
	private String zxt_user_id;
	private int webservice;
	private int zxt_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMerchant_pin() {
		return merchant_pin;
	}
	public void setMerchant_pin(long merchant_pin) {
		this.merchant_pin = merchant_pin;
	}
	public String getLogin_account() {
		return login_account;
	}
	public void setLogin_account(String login_account) {
		this.login_account = login_account;
	}
	public String getLogin_pwd() {
		return login_pwd;
	}
	public void setLogin_pwd(String login_pwd) {
		this.login_pwd = login_pwd;
	}
	public String getUser_ext_code() {
		return user_ext_code;
	}
	public void setUser_ext_code(String user_ext_code) {
		this.user_ext_code = user_ext_code;
	}
	public int getLogin_type() {
		return login_type;
	}
	public void setLogin_type(int login_type) {
		this.login_type = login_type;
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
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getUser_type() {
		return user_type;
	}
	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}
	public int getLock_flag() {
		return lock_flag;
	}
	public void setLock_flag(int lock_flag) {
		this.lock_flag = lock_flag;
	}
	public int getLimit_try_count() {
		return limit_try_count;
	}
	public void setLimit_try_count(int limit_try_count) {
		this.limit_try_count = limit_try_count;
	}
	public int getActive_flag() {
		return active_flag;
	}
	public void setActive_flag(int active_flag) {
		this.active_flag = active_flag;
	}
	public Date getLogin_time() {
		return login_time;
	}
	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public long getCreate_by() {
		return create_by;
	}
	public void setCreate_by(long create_by) {
		this.create_by = create_by;
	}
	public long getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(long update_by) {
		this.update_by = update_by;
	}
	public int getIp_limit_flag() {
		return ip_limit_flag;
	}
	public void setIp_limit_flag(int ip_limit_flag) {
		this.ip_limit_flag = ip_limit_flag;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
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
	public String getCorp_access_number() {
		return corp_access_number;
	}
	public void setCorp_access_number(String corp_access_number) {
		this.corp_access_number = corp_access_number;
	}
	public int getWebservice() {
		return webservice;
	}
	public void setWebservice(int webservice) {
		this.webservice = webservice;
	}
	public String getZxt_user_id() {
		return zxt_user_id;
	}
	public void setZxt_user_id(String zxt_user_id) {
		this.zxt_user_id = zxt_user_id;
	}
	public int getZxt_id() {
		return zxt_id;
	}
	public void setZxt_id(int zxt_id) {
		this.zxt_id = zxt_id;
	}
}
