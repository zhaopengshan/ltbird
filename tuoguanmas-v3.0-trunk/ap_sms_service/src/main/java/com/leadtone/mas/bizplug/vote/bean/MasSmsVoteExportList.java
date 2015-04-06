package com.leadtone.mas.bizplug.vote.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class MasSmsVoteExportList {
	private String title;
	private Date begin_time;
	private Date end_time;
	private String create_by;
	private int result_count;
	private String tos;
	private String content;
	private String voteoptions;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public int getResult_count() {
		return result_count;
	}
	public void setResult_count(int result_count) {
		this.result_count = result_count;
	}
	public String getTos() {
		return tos;
	}
	public void setTos(String tos) {
		this.tos = tos;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVoteoptions() {
		return voteoptions;
	}
	public void setVoteoptions(String voteoptions) {
		this.voteoptions = voteoptions;
	}
	
}
