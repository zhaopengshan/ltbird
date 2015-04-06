package com.leadtone.sender.handler;

public interface ISmsSendHandler {
	//处理
	public void processer(Long merchant_pin, String province_str);
}
