/**
 * 
 */
package com.leadtone.mas.bizplug.config.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PAN-Z-G
 *
 */
public class MbnConfigSysDictionary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private Long id;//'唯一标识号 20位数字(格式见数据库设计定义)',
	 private Long pid;//'唯一标识号 20位数字(格式见数据库设计定义)',
	 private Long type;//'1省份,2性别,3大区,4市,5区县,6商圈,7通道类型',
	 private String coding;//'编码（非零）',
	 private String name;//'名称',
	 private Date lastModifyTime;//  最后一次修改时间',
	 private Date createTime;//'创建时间',
	 private String attrInfo;//'附加属性(备用)',
	 private String description;//'描述',
	 
	 
	 
	public MbnConfigSysDictionary(Long id) {
		super();
		this.id = id;
	}



	public MbnConfigSysDictionary() {
		super();
	}



	public MbnConfigSysDictionary(Long id, Long pid, Long type, String coding,
			String name, Date lastModifyTime, Date createTime,
			String attrInfo, String description) {
		super();
		this.id = id;
		this.pid = pid;
		this.type = type;
		this.coding = coding;
		this.name = name;
		this.lastModifyTime = lastModifyTime;
		this.createTime = createTime;
		this.attrInfo = attrInfo;
		this.description = description;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Long getId() {
		return id;
	}



	public Long getPid() {
		return pid;
	}



	public Long getType() {
		return type;
	}



	public String getCoding() {
		return coding;
	}



	public String getName() {
		return name;
	}



	public Date getLastModifyTime() {
		return lastModifyTime;
	}



	public Date getcreateTime() {
		return createTime;
	}



	public String getAttrInfo() {
		return attrInfo;
	}



	public String getDescription() {
		return description;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public void setPid(Long pid) {
		this.pid = pid;
	}



	public void setType(Long type) {
		this.type = type;
	}



	public void setCoding(String coding) {
		this.coding = coding;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}



	public void setcreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public void setAttrInfo(String attrInfo) {
		this.attrInfo = attrInfo;
	}



	public void setDescription(String description) {
		this.description = description;
	}

 
	 
	 
}
