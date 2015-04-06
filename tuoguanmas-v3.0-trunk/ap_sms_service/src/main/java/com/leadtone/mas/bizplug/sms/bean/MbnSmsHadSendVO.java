package com.leadtone.mas.bizplug.sms.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class MbnSmsHadSendVO extends MbnSmsHadSend {
	//回复条数
		private Integer replyCount;
		private String name;
		private Date receiveTime;
		//创建人名称 
		private String createName;
		
		public Integer getReplyCount() {
			return replyCount;
		}

		public void setReplyCount(Integer replyCount) {
			this.replyCount = replyCount;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		@JSON(format="yyyy-MM-dd HH:mm:ss")
		public Date getReceiveTime() {
			return receiveTime;
		}

		public void setReceiveTime(Date receiveTime) {
			this.receiveTime = receiveTime;
		}

		public String getCreateName() {
			return createName;
		}

		public void setCreateName(String createName) {
			this.createName = createName;
		}
		 
}
