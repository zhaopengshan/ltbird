package com.leadtone.mas.bizplug.sms.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

/**
 * 
 * @author PAN-Z-G 短信收件箱
 */
public class MbnSmsInbox {

	private Long id;						// 唯一标识号'
	private Integer status; 				//'态状图标，0:已读，1：未读,2:珍藏'
	private Long merchantPin;				// '商户PIN码'
	private String senderMobile;			// 客户手机号'
	private String receiverAccessNumber;	// 接受者特服号'
	private String serviceCode;				// '服务扩展码(编号)'
	private String operationId;				// '业务扩展码'
	private String content;					// '客户上行短信内容'
	private Date receiveTime;				// '短信接收时间'
	private String senderName ;				//发件人姓名
	private Integer webService;				// 用户使用的登录方式。1：页面；2：webservice。
	private Long replyBatchId;				// 上行短信对应的发送批次ID 
	private Integer classify;//上行通道类型
	
	public MbnSmsInbox() {
		super();
	}
	/**
	 * 带ID构造
	 * @param id
	 */
	public MbnSmsInbox(Long id) {
		super();
		this.id = id;
	}

	 

	
	public MbnSmsInbox(Long id, Integer status, Long merchantPin,
			String senderMobile, String receiverAccessNumber,
			String serviceCode, String operationId, String content,
			Date receiveTime) {
		super();
		this.id = id;
		this.status = status;
		this.merchantPin = merchantPin;
		this.senderMobile = senderMobile;
		this.receiverAccessNumber = receiverAccessNumber;
		this.serviceCode = serviceCode;
		this.operationId = operationId;
		this.content = content;
		this.receiveTime = receiveTime;
	}

	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getMerchantPin() {
		return merchantPin;
	}

	public String getSenderMobile() {
		return senderMobile;
	}
 
	public String getServiceCode() {
		return serviceCode;
	}

	public String getOperationId() {
		return operationId;
	}

	public String getContent() {
		return content;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}

	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}
 
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getReceiverAccessNumber() {
		return receiverAccessNumber;
	}
	public void setReceiverAccessNumber(String receiverAccessNumber) {
		this.receiverAccessNumber = receiverAccessNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public Integer getWebService() {
		return webService;
	}
	public void setWebService(Integer webService) {
		this.webService = webService;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getReplyBatchId() {
		return replyBatchId;
	}
	public void setReplyBatchId(Long replyBatchId) {
		this.replyBatchId = replyBatchId;
	}
	public Integer getClassify() {
		return classify;
	}
	public void setClassify(Integer classify) {
		this.classify = classify;
	}

}
