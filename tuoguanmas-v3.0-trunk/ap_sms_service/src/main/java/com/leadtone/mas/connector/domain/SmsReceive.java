package com.leadtone.mas.connector.domain;

import java.io.Serializable;

/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */
public class SmsReceive  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6473021033465338886L;
	private long id;
	private String sender_mobile;
	private String content;
	private String receiver_access_number;
	private String create_time;
	private int status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSender_mobile() {
		return sender_mobile;
	}
	public void setSender_mobile(String sender_mobile) {
		this.sender_mobile = sender_mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver_access_number() {
		return receiver_access_number;
	}
	public void setReceiver_access_number(String receiver_access_number) {
		this.receiver_access_number = receiver_access_number;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
