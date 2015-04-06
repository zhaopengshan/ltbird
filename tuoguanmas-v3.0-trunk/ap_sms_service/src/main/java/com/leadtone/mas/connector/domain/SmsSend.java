package com.leadtone.mas.connector.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */
public class SmsSend  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1292687714957407127L;
	private long id;
	private long merchant_pin;
	private long operation_id;
	private String task_number;
	private long batch_id;
	private String province;
	private String self_mobile;
	private String tos;
	private String tos_name;
	private String content;
	private int cut_apart_number;
	private String commit_time;
	private String ready_send_time;
	private int expire_time;
	private String complete_time;
	private String sms_access_number;
	private int tunnel_type;
	private int priority_level;
	private int send_result;
	private String fail_reason;
	private String description;
	private long create_by;
	private String title;
	private int webservice;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMerchant_pin() {
		return merchant_pin;
	}
	public void setMerchant_pin(long merchant_pin) {
		this.merchant_pin = merchant_pin;
	}
	public long getOperation_id() {
		return operation_id;
	}
	public void setOperation_id(long operation_id) {
		this.operation_id = operation_id;
	}
	public String getTask_number() {
		return task_number;
	}
	public void setTask_number(String task_number) {
		this.task_number = task_number;
	}
	public long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getSelf_mobile() {
		return self_mobile;
	}
	public void setSelf_mobile(String self_mobile) {
		this.self_mobile = self_mobile;
	}
	public String getTos() {
		return tos;
	}
	public void setTos(String tos) {
		this.tos = tos;
	}
	public String getTos_name() {
		return tos_name;
	}
	public void setTos_name(String tos_name) {
		this.tos_name = tos_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCut_apart_number() {
		return cut_apart_number;
	}
	public void setCut_apart_number(int cut_apart_number) {
		this.cut_apart_number = cut_apart_number;
	}
	public String getCommit_time() {
		return commit_time;
	}
	public void setCommit_time(String commit_time) {
		this.commit_time = commit_time;
	}
	public String getReady_send_time() {
		return ready_send_time;
	}
	public void setReady_send_time(String ready_send_time) {
		this.ready_send_time = ready_send_time;
	}
	public int getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(int expire_time) {
		this.expire_time = expire_time;
	}
	public String getComplete_time() {
		return complete_time;
	}
	public void setComplete_time(String complete_time) {
		this.complete_time = complete_time;
	}
	public String getSms_access_number() {
		return sms_access_number;
	}
	public void setSms_access_number(String sms_access_number) {
		this.sms_access_number = sms_access_number;
	}
	public int getTunnel_type() {
		return tunnel_type;
	}
	public void setTunnel_type(int tunnel_type) {
		this.tunnel_type = tunnel_type;
	}
	public int getPriority_level() {
		return priority_level;
	}
	public void setPriority_level(int priority_level) {
		this.priority_level = priority_level;
	}
	public int getSend_result() {
		return send_result;
	}
	public void setSend_result(int send_result) {
		this.send_result = send_result;
	}
	public String getFail_reason() {
		return fail_reason;
	}
	public void setFail_reason(String fail_reason) {
		this.fail_reason = fail_reason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCreate_by() {
		return create_by;
	}
	public void setCreate_by(long create_by) {
		this.create_by = create_by;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWebservice() {
		return webservice;
	}
	public void setWebservice(int webservice) {
		this.webservice = webservice;
	}
	
	
}
