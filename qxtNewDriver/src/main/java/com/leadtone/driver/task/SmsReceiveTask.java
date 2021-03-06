package com.leadtone.driver.task;

import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

import com.leadtone.driver.handler.ISmsSendHandler;
import com.leadtone.util.ProperUtil;
import com.leadtone.util.SpringUtils;
import com.litong.utils.StringUtil;

public class SmsReceiveTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(SmsReceiveTask.class);
	
	private Long merchantPin;
	private String accountStr;
	private String pwdStr;
	private String uid;
	private String receIpStr;
	private String rptIpStr;
	private ISmsSendHandler smsSendHandler;
	
	public SmsReceiveTask(Long merchant_pin, String account, String pwd, String id, String moIp, String rptIp){
		merchantPin = merchant_pin;
		accountStr = account;
		pwdStr = pwd;
		uid = id;
		receIpStr = moIp;
		rptIpStr = rptIp;
	}
	
	@Override
	public Integer call(){
		try{
//			logger.info(merchantPin+"企业,uid:"+uid+",QXT短信发送任务启动!");
			String handlerName = ProperUtil.readValue("sms.sendReceive.handler."+ merchantPin +".impl");
			if(StringUtil.isEmpty(handlerName)){
				handlerName = ProperUtil.readValue("sms.sendReceive.handler.common.impl");
			}
			String perAccount = ProperUtil.readValue("sms.one.user.one.account");
			smsSendHandler = (ISmsSendHandler) SpringUtils.getBean(handlerName);
			smsSendHandler.processer(merchantPin, accountStr, pwdStr, uid, receIpStr, rptIpStr, Boolean.valueOf(perAccount));
//			logger.info(merchantPin+"企业,QXT短信发送任务完成!");
		}catch(Exception e){
			logger.error(merchantPin+"企业,uid:"+uid+",QXT短信处理异常",e);
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
