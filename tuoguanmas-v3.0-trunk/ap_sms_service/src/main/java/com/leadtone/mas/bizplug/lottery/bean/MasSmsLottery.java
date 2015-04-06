package com.leadtone.mas.bizplug.lottery.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MasSmsLottery {
	private long id;
	private String title;
	private String tos;
	private Date commitTime;
	private Date readySendTime;
	private Date beginTime;
	private Date endTime;
	private String content;
	private long createBy;
	
	private int state;
	private Date begin_time;
	private Date end_time;
	private String reply_code;
	private String valid_tos;
	private String replyCode;
	private String validTos;
	
	private String loginAccount;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTos() {
		return tos;
	}
	public void setTos(String tos) {
		this.tos = tos;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReadySendTime() {
		return readySendTime;
	}
	public void setReadySendTime(Date readySendTime) {
		this.readySendTime = readySendTime;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getReplyCode() {
		return replyCode;
	}
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}
	public String getValidTos() {
		return validTos;
	}
	public void setValidTos(String validTos) {
		this.validTos = validTos;
	}
	
	public String getReply_code() {
		return reply_code;
	}
	public void setReply_code(String reply_code) {
		this.reply_code = reply_code;
	}
	public String getValid_tos() {
		return valid_tos;
	}
	public void setValid_tos(String valid_tos) {
		this.valid_tos = valid_tos;
	}
	public MasSmsLottery() {
		super();
	}
	public MasSmsLottery(long id, String title, String tos, Date commitTime,
			Date readySendTime, Date beginTime, Date endTime, String content,
			long createBy, int state, String replyCode, String validTos,
			String loginAccount) {
		super();
		this.id = id;
		this.title = title;
		this.tos = tos;
		this.commitTime = commitTime;
		this.readySendTime = readySendTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.content = content;
		this.createBy = createBy;
		this.state = state;
		this.replyCode = replyCode;
		this.validTos = validTos;
		this.loginAccount = loginAccount;
	}

	
	
}
