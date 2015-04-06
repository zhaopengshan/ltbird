package com.leadtone.mas.bizplug.vote.bean;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;
public class MasSmsToupiaoxuanxiang {
	private Long id;
	private Long tpdc_id;//对应的投票调查号码
	private String order_number;//案答选项号码
	private String answer_content;//选项内容
	private Date create_time;//创建时间
	private Date modify_time;//修改时间
	private Long create_by;//创建人
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getTpdc_id() {
		return tpdc_id;
	}
	public void setTpdc_id(Long tpdc_id) {
		this.tpdc_id = tpdc_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getAnswer_content() {
		return answer_content;
	}
	public void setAnswer_content(String answer_content) {
		this.answer_content = answer_content;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getCreate_by() {
		return create_by;
	}
	public void setCreate_by(Long create_by) {
		this.create_by = create_by;
	}
	public MasSmsToupiaoxuanxiang(){
		super();
	}
	public MasSmsToupiaoxuanxiang(Long id){
		super();
		this.id = id;
	}
	
}
