/**
 * 
 */
package com.leadtone.mas.bizplug.common.bean;

import java.io.Serializable;

/**
 * @author PAN-Z-G
 *
 */
public class MbnProvinceDictionary implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private Integer id ;//
	private String provinceCoding;//'省份编码',
	private String provinceName;// '省份名称',
	public MbnProvinceDictionary() {
		super();
		//  
	}
	public MbnProvinceDictionary(Integer id) {
		super();
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public String getProvinceCoding() {
		return provinceCoding;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setProvinceCoding(String provinceCoding) {
		this.provinceCoding = provinceCoding;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
}
