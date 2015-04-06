/**
 * 
 */
package com.leadtone.mas.bizplug.tunnel.bean;

/**
 * @author PAN-Z-G
 *	通道扩展类
 */
public class SmsMbnTunnelConsumerVO extends SmsMbnTunnelVO {

	 
	private static final long serialVersionUID = 1L;

	
	/**
	 * 商户剩余条数
	 */
	private Long remainNumber;
	
	public Long getRemainNumber() {
		return remainNumber;
	}
	public void setRemainNumber(Long remainNumber) {
		this.remainNumber = remainNumber;
	}
	
	
}
					