package com.leadtone.mas.bizplug.common.bean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.security.bean.Users;

/**
 * 
 * @author xiazhy
 *通用方法BEAN
 */
public class MasCommonBean implements Serializable{
	
	private String title;//标题 
	private String receiver;
	private String prov_code;
	private Date readySendTime;
	private String smsText;
	private String sendType;
	private String flag; 
	private String sendTime;
	private Long ydTunnel=0L;
	private Long ltTunnel=0L;
	private Long dxTunnel=0L;
	private Long tdTunnel=0L;
	private String replyText;
	private String codeType;
	private Long merchantPin;
	private int operationType;
	private String entSign;
	private String replyCode;
	private List<String> colsList;
	public List<String> getColsList() {
		return colsList;
	}
	public void setColsList(List<String> colsList) {
		this.colsList = colsList;
	}
	public Set<Contacts> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<Contacts> userSet) {
		this.userSet = userSet;
	}
	public Users getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(Users loginUser) {
		this.loginUser = loginUser;
	}
	private Set<Contacts> userSet;
	private Users loginUser;
	
	public Date getReadySendTime() {
		return readySendTime;
	}
	public void setReadySendTime(Date readySendTime) {
		this.readySendTime = readySendTime;
	}
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	private long batchId;
	public String getProv_code() {
		return prov_code;
	}
	public void setProv_code(String provCode) {
		prov_code = provCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSmsText() {
		return smsText;
	}
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public Long getYdTunnel() {
		return ydTunnel;
	}
	public void setYdTunnel(Long ydTunnel) {
		this.ydTunnel = ydTunnel;
	}
	public Long getLtTunnel() {
		return ltTunnel;
	}
	public void setLtTunnel(Long ltTunnel) {
		this.ltTunnel = ltTunnel;
	}
	public Long getDxTunnel() {
		return dxTunnel;
	}
	public void setDxTunnel(Long dxTunnel) {
		this.dxTunnel = dxTunnel;
	}
	public Long getTdTunnel() {
		return tdTunnel;
	}
	public void setTdTunnel(Long tdTunnel) {
		this.tdTunnel = tdTunnel;
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	public String getEntSign() {
		return entSign;
	}
	public void setEntSign(String entSign) {
		this.entSign = entSign;
	}
	public String getReplyCode() {
		return replyCode;
	}
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}
	

}
