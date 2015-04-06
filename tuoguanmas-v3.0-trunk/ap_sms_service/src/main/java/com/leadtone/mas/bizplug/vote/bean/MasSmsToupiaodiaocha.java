package com.leadtone.mas.bizplug.vote.bean;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MasSmsToupiaodiaocha {
	private Long id;
	private String title;//投票标题
	private String tos;//投票人手机号码
	private Date commit_time;//提交时间
	private Date ready_send_time;//发送投票信息时间
	private Date begin_time;//投票活动开始时间
	private Date end_time;//投票活动结束时间
	private String content;//投票内容
	private Long create_by;//投票活动创建人
	private int multi_selected_number;//每次投票最多可以选择的投票选项，以“，”为分割符号
	private int support_repeat;//多选，每次投票是否允许有重复项，即每项是否可以多投几次)
	private int effective_mode;//1 每次投票都有效；2仅第一次有效；3最后一次有效
	private int need_successful_remind;//是否回复投票成功短信,1表示是；0表示否
	private String need_successful_content;//回复投票成功短信内容
	private int need_not_permmit_remind;//是否回复不允许投票短信,1表示是；0表示否
	private String need_not_permmit_content;//不允许投票短信回复内容
	private int need_content_error_remind;//是否回复投票错误的提醒短信,1表示是；0表示否
	private String need_content_error_content;//回复投票错误的提醒短信内容
	private int deleted;//是否删除。1表示是；0表示否；
	private String taskNumber;
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Date getCommit_time() {
		return commit_time;
	}
	public void setCommit_time(Date commit_time) {
		this.commit_time = commit_time;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReady_send_time() {
		return ready_send_time;
	}
	public void setReady_send_time(Date ready_send_time) {
		this.ready_send_time = ready_send_time;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getCreate_by() {
		return create_by;
	}
	public void setCreate_by(Long create_by) {
		this.create_by = create_by;
	}
	public int getMulti_selected_number() {
		return multi_selected_number;
	}
	public void setMulti_selected_number(int multi_selected_number) {
		this.multi_selected_number = multi_selected_number;
	}
	public int getSupport_repeat() {
		return support_repeat;
	}
	public void setSupport_repeat(int support_repeat) {
		this.support_repeat = support_repeat;
	}
	public int getEffective_mode() {
		return effective_mode;
	}
	public void setEffective_mode(int effective_mode) {
		this.effective_mode = effective_mode;
	}
	public int getNeed_successful_remind() {
		return need_successful_remind;
	}
	public void setNeed_successful_remind(int need_successful_remind) {
		this.need_successful_remind = need_successful_remind;
	}
	public String getNeed_successful_content() {
		return need_successful_content;
	}
	public void setNeed_successful_content(String need_successful_content) {
		this.need_successful_content = need_successful_content;
	}
	public int getNeed_not_permmit_remind() {
		return need_not_permmit_remind;
	}
	public void setNeed_not_permmit_remind(int need_not_permmit_remind) {
		this.need_not_permmit_remind = need_not_permmit_remind;
	}
	public String getNeed_not_permmit_content() {
		return need_not_permmit_content;
	}
	public void setNeed_not_permmit_content(String need_not_permmit_content) {
		this.need_not_permmit_content = need_not_permmit_content;
	}
	public int getNeed_content_error_remind() {
		return need_content_error_remind;
	}
	public void setNeed_content_error_remind(int need_content_error_remind) {
		this.need_content_error_remind = need_content_error_remind;
	}
	public String getNeed_content_error_content() {
		return need_content_error_content;
	}
	public void setNeed_content_error_content(String need_content_error_content) {
		this.need_content_error_content = need_content_error_content;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public MasSmsToupiaodiaocha(){
		super();
	}
	public MasSmsToupiaodiaocha(Long id){
		super();
		this.id = id;
	}
	//public MbnSmsToupiaodiaocha
	public String getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
}
