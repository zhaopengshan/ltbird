package com.leadtone.mas.bizplug.sms.bean;
 
import java.util.Date; 
import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MbnSmsSelected {
	private Long id;
	private Long merchantPin; // 商户PIN码,-1为系统级',
	private String content; // 短信内容
	private Long createBy; // 创建人
	private Date createTime; // 创建时间
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public MbnSmsSelected(){
		super();
	}
	public MbnSmsSelected(Long id){
		super();
		this.id = id;
	}
	public MbnSmsSelected(Long id, Long merchantPin, String content,
			Long createBy, Date createTime) {
		super();
		this.id = id;
		this.merchantPin = merchantPin;
		this.content = content;
		this.createBy = createBy;
		this.createTime = createTime;
	}

}
