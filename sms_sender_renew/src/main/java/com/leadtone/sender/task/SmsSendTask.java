package com.leadtone.sender.task;

import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import com.leadtone.sender.handler.ISmsSendHandler;
import com.leadtone.util.ProperUtil;
import com.leadtone.util.SpringUtils;
import com.leadtone.util.StringUtil;

public class SmsSendTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(SmsSendTask.class);
	
	private Long merchantPin;
	private String provinceStr;
	private ISmsSendHandler smsSendHandler;
	
	public SmsSendTask(Long merchant_pin, String province_str){
		merchantPin = merchant_pin;
		provinceStr = province_str;
	}
	
	@Override
	public Integer call(){
		try{
//			logger.info(merchantPin+"企业,短信发送任务启动!");
			String handlerName = ProperUtil.readValue("sms.sendTask.handler."+ provinceStr +".impl");
			if(StringUtil.isEmpty(handlerName)){
				handlerName = ProperUtil.readValue("sms.sendTask.handler.common.impl");
			}
//			logger.info(merchantPin+"企业,短信处理："+handlerName);
			smsSendHandler = (ISmsSendHandler) SpringUtils.getBean(handlerName);
			smsSendHandler.processer(merchantPin, provinceStr);
//			logger.info(merchantPin+"企业,短信发送任务完成!");
		}catch(Exception e){
			logger.error(merchantPin+"企业,短信处理异常",e);
		}
		
		return 1;
	}


	public Long getMerchantPin() {
		return merchantPin;
	}


	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}


	public String getProvinceStr() {
		return provinceStr;
	}


	public void setProvinceStr(String provinceStr) {
		this.provinceStr = provinceStr;
	}
}
