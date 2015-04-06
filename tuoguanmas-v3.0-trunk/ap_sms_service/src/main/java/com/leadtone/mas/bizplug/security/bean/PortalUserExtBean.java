package com.leadtone.mas.bizplug.security.bean;

import java.util.Date;

/**
 * 用户扩展信息表
 * @author admin
 *
 */
public class PortalUserExtBean {
	// 用户ID
	private Long id;
	// 是否启用发送限制 0无限制 1限制
	private Integer smsLimit = 0;
	// 限制统计周期 0日 1月
	private Integer smsLimitPeriod = 0;
	// 周期内可发送数量
	private Integer smsLimitCount = 0;
	// 用户发送短信优先级
	private Integer smsPriority = 3;
	// 周期内已发送数量
	private Integer smsSendCount = 0;
	// 统计时间
	private Date countTime;
	// 备注
	private String memo;
	private String smsMobile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSmsLimit() {
		return smsLimit;
	}

	public void setSmsLimit(Integer smsLimit) {
		this.smsLimit = smsLimit;
	}
	public void setSmsLimit(String smsLimit) {
		if( smsLimit != null && smsLimit.equalsIgnoreCase("on")){
			this.smsLimit = 1;
		}else{
			this.smsLimit = 0;
		}
		
	}

	public Integer getSmsLimitPeriod() {
		return smsLimitPeriod;
	}

	public void setSmsLimitPeriod(Integer smsLimitPeriod) {
		this.smsLimitPeriod = smsLimitPeriod;
	}

	public Integer getSmsLimitCount() {
		return smsLimitCount;
	}

	public void setSmsLimitCount(Integer smsLimitCount) {
		this.smsLimitCount = smsLimitCount;
	}

	public Integer getSmsPriority() {
		return smsPriority;
	}

	public void setSmsPriority(Integer smsPriority) {
		this.smsPriority = smsPriority;
	}

	public Integer getSmsSendCount() {
		return smsSendCount;
	}

	public void setSmsSendCount(Integer smsSendCount) {
		this.smsSendCount = smsSendCount;
	}

	public Date getCountTime() {
		return countTime;
	}

	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "PortalUserExtBean [id=" + id + ", smsMobile="+smsMobile+", memo=" + memo + ", smsLimit="
				+ smsLimit + ", smsLimitCount=" + smsLimitCount
				+ ", smsLimitPeriod=" + smsLimitPeriod + ", smsPriority="
				+ smsPriority + ", smsSendCount=" + smsSendCount + "]";
	}

	public String getSmsMobile() {
		return smsMobile;
	}

	public void setSmsMobile(String smsMobile) {
		this.smsMobile = smsMobile;
	}

}
