/**
 * 
 */
package com.leadtone.mas.bizplug.config.bean;

import java.io.Serializable;

/**
 * @author PAN-Z-G
 * 
 */
public class MbnConfigProvinceItemValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;// '唯一标识号 20位数字(格式见数据库设计定义)',
	private Long itemId;// '父配置项CODE',
	private String province;// '省份',
	private String itemValue;// '配置项值',
	private Long sortNum;// '排序号',
	private String description;// '配置说明',

	public MbnConfigProvinceItemValue() {
		super();
	}

	public MbnConfigProvinceItemValue(Long id) {
		super();
		this.id = id;
	}

	public MbnConfigProvinceItemValue(Long id, Long itemId, String province,
			String itemValue, Long sortNum, String description) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.province = province;
		this.itemValue = itemValue;
		this.sortNum = sortNum;
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public Long getitemId() {
		return itemId;
	}

	public String getProvince() {
		return province;
	}

	public String getitemValue() {
		return itemValue;
	}

	public Long getsortNum() {
		return sortNum;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setitemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setitemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public void setsortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
