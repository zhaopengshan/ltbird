/**
 * 
 */
package com.leadtone.mas.bizplug.sms.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author PAN-Z-G
 * 发送箱扩展类
 */
public class MbnSmsReadySendVO extends MbnSmsReadySend {
	//回复条数
			private Integer replyCount;
			private Date receiveTime;
			public Integer getReplyCount() {
				return replyCount;
			}

			public void setReplyCount(Integer replyCount) {
				this.replyCount = replyCount;
			}
			@JSON(format="yyyy-MM-dd HH:mm:ss")
			public Date getReceiveTime() {
				return receiveTime;
			}

			public void setReceiveTime(Date receiveTime) {
				this.receiveTime = receiveTime;
			} 
}
