package com.leadtone.mas.bizplug.smsmo.handler;

public interface ISmsSendHandler {
	//处理
	public void processer(Long merchant_pin, String account, String pwd, String id, String ip, String rptIp);
}
