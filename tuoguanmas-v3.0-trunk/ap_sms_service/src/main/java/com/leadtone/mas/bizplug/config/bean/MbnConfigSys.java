/**
 * 
 */
package com.leadtone.mas.bizplug.config.bean;

import java.io.Serializable;

/**
 * @author PAN-Z-G
 *
 */
public class MbnConfigSys implements Serializable {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//'唯一标识号 20位数字(格式见数据库设计定义)',
	  private Long type;//'类别',
	  private String name;//'配置项编码',
	  private String itemValue;//'配置项值',
	  private String description;//配置项说明',

	  
	public MbnConfigSys() {
		super();
		// 
	}
	
	public MbnConfigSys(Long id) {
		super();
		this.id = id;
	}

	public MbnConfigSys(Long id, Long type, String name, String itemValue,
			String description) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.itemValue = itemValue;
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public Long getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getItemValue() {
		return itemValue;
	}
	public String getDescription() {
		return description;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	  
	  
}
