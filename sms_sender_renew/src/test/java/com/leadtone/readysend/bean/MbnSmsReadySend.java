/**
 * 
 */
package com.leadtone.readysend.bean;

import java.util.Date;

/**
 * @author R
 *  待发短信息类
 */
public class MbnSmsReadySend {

	public MbnSmsReadySend() {
		super();
	}
	
	public MbnSmsReadySend(Long id) {
		super();
		this.id = id;
	}
 
	 private Long id;
	 private Long merchantPin;		//商户PIN码，-1为系统级',
	 private Long operationId;	//业务类型ID',
	 private Long batchId;		//'批次ID',
	 private String province;		//省份代码',
	 private String selfMobile;		//本机号码',
	 private String tos;			//对方号码 不支持群发,
	 private String tosName;		//接收人姓名,
	 private String content;		//短信内容',
	 private Integer cutApartNumber;//发送时切分短信条数,
	 private Date commitTime;		//用户提交时间',
	 private Date readySendTime;	//'准备发送时间',
	 private Date expireTime;		//过期时间',
	 private Date completeTime;		//'发送完成时间',
	 private String smsAccessNumber;//特服号码,
	 private Integer tunnelType;	//1话机 2网关',
	 private Integer priorityLevel;	//1-50，越大优先级越高',
	 private Integer sendResult;		//'-1取消发送,0未发送,1已提交网关,2成功,3失败',
	 private String failReason;		//失败原因',
	 private String description;	//备注',
	 private Long createBy;		// 创建人
	 private String title;//短信标题
	 private String taskNumber;
	 private Integer webService;
	 
	public Long getId() {
		return id;
	}
	public Long getMerchantPin() {
		return merchantPin;
	}

	public Long getOperationId() {
		return operationId;
	}
	public Long getBatchId() {
		return batchId;
	}

	public String getProvince() {
		return province;
	}

	public String getSelfMobile() {
		return selfMobile;
	}

	public String getTos() {
		return tos;
	}

	public String getTosName() {
		return tosName;
	}

	public String getContent() {
		return content;
	}

	public Integer getCutApartNumber() {
		return cutApartNumber;
	}
	public Date getCommitTime() {
		return commitTime;
	}
	public Date getReadySendTime() {
		return readySendTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}

	public String getSmsAccessNumber() {
		return smsAccessNumber;
	}

	public Integer getTunnelType() {
		return tunnelType;
	}

	public Integer getPriorityLevel() {
		return priorityLevel;
	}

	public Integer getSendResult() {
		return sendResult;
	}

	public String getFailReason() {
		return failReason;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setSelfMobile(String selfMobile) {
		this.selfMobile = selfMobile;
	}

	public void setTos(String tos) {
		this.tos = tos;
	}

	public void setTosName(String tosName) {
		this.tosName = tosName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCutApartNumber(Integer cutApartNumber) {
		this.cutApartNumber = cutApartNumber;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public void setReadySendTime(Date readySendTime) {
		this.readySendTime = readySendTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public void setSmsAccessNumber(String smsAccessNumber) {
		this.smsAccessNumber = smsAccessNumber;
	}

	public void setTunnelType(Integer tunnelType) {
		this.tunnelType = tunnelType;
	}

	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public void setSendResult(Integer sendResult) {
		this.sendResult = sendResult;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public Integer getWebService() {
		return webService;
	}

	public void setWebService(Integer webService) {
		this.webService = webService;
	}
 
}
