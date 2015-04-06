/**
 * 
 */
package com.leadtone.mas.bizplug.tunnel.bean;

/**
 * @author PAN-Z-G
 *	通道扩展类
 */
public class SmsMbnTunnelVO extends SmsMbnTunnel {

	 
	private static final long serialVersionUID = 1L;
	/**
	 * 短信条数
	 */
	private Long smsNumber;
	/**
	 * 省份名称
	 */
	private String provinceName;
	
	
	
	/**
	 * 访问器
	 * @return
	 */
	public Long getSmsNumber() {
		return smsNumber;
	}
	public void setSmsNumber(Long smsNumber) {
		this.smsNumber = smsNumber;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	
}
					