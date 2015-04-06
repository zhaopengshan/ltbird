package com.leadtone.driver.handler;

public interface ISmsSendHandler {
	//处理
	public void processer(Long merchant_pin, String account, String pwd, String ip, String rptIp);
}
