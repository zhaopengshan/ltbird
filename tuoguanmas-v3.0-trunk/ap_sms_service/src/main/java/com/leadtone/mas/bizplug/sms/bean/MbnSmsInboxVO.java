/**
 * 
 */
package com.leadtone.mas.bizplug.sms.bean;

/**
 * @author PAN-Z-G
 * 收件箱扩展类
 */
public class MbnSmsInboxVO extends MbnSmsInbox {
   
	private String customerName;
	//回复条数
	private Integer replyCount;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}
	
	
}
