package com.leadtone.mas.bizplug.vote.bean;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;
public class MasSmsToupiaojieguo {
	private Long id;
	private Long tpdc_id;//票投任务id
	private String mobile;//投票人手机号码
	private String name;//票投人姓名
	private String order_number;//答案选项号码
	private String answer_content;//收到的答案
	private Date create_time;//创建时间
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getCreate_by() {
		return create_by;
	}
	public void setCreate_by(Long create_by) {
		this.create_by = create_by;
	}
	public MasSmsToupiaojieguo(){
		super();
	}
	public MasSmsToupiaojieguo(Long id){
		super();
		this.id = id;
	}
}
