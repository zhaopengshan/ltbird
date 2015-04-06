/**
 * 
 */
package com.leadtone.mas.bizplug.sms.bean;

import java.io.Serializable;

/**
 * @author PAN-Z-G
 * 
 */
public class MbnSmsOperationClass implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;			// '短信业务类别id',
	private String coding;		// '业务类别编码，两位字母数标识(如，短信互动为hd)',
	private String name;		// ' 短信业务类别名字，如短信答题、会议通知等',
	private Long expireTime;	// '从ready_send_time起的分钟数,默认25小时(1500分钟)',
	private Long priorityLevel;	// '1,2,3,4,5级，数字越大优先级越高，默认3'

	public MbnSmsOperationClass() {
		super();
	}

	/**
	 * 有参构造（id,一般用于根据 id查询对象）
	 * 
	 * @param id
	 */
	public MbnSmsOperationClass(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getCoding() {
		return coding;
	}

	public String getName() {
		return name;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public Long getPriorityLevel() {
		return priorityLevel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCoding(String coding) {
		this.coding = coding;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public void setPriorityLevel(Long priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
}
