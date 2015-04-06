package com.leadtone.driver.task;

import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

import com.leadtone.driver.handler.ISmsSendHandler;
import com.leadtone.util.ProperUtil;
import com.leadtone.util.SpringUtils;
import com.litong.utils.StringUtil;

public class SmsSendTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(SmsSendTask.class);
	
	private Long merchantPin;
	private String accountStr;
	private String pwdStr;
	private String ipStr;
	private String rptIpStr;
	private ISmsSendHandler smsSendHandler;
	
	public SmsSendTask(Long merchant_pin, String account, String pwd, String ip, String rptIp){
		merchantPin = merchant_pin;
		accountStr = account;
		pwdStr = pwd;
		ipStr = ip;
		rptIpStr = rptIp;
	}
	
	@Override
	public Integer call(){
		try{
			String handlerName = ProperUtil.readValue("sms.sendTask.handler."+ merchantPin +".impl");
			if(StringUtil.isEmpty(handlerName)){
				handlerName = ProperUtil.readValue("sms.sendTask.handler.common.impl");
			}
			smsSendHandler = (ISmsSendHandler) SpringUtils.getBean(handlerName);
			smsSendHandler.processer(merchantPin, accountStr, pwdStr, ipStr, rptIpStr);
		}catch(Exception e){
			logger.error(merchantPin+"企业,ZXT短信处理异常",e);
		}catch(Throwable e){
			logger.error(merchantPin+"企业,ZXT短信处理异常",e);
		}
		
		return 1;
	}


	public Long getMerchantPin() {
		return merchantPin;
	}


	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
}