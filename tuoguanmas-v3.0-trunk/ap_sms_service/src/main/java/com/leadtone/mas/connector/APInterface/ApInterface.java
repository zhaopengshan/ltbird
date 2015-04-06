package com.leadtone.mas.connector.APInterface;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leadtone.mas.connector.core.CoreInterfaceImpl;
import com.leadtone.mas.connector.utils.SpringUtils;

/**
 * 
 * @author hejiyong
 * date:2013-1-24
 * 
 */
public class ApInterface {
	private static final Logger logger = LoggerFactory.getLogger(ApInterface.class);
	
	
	public String smsSend(String message){
		if(logger.isDebugEnabled()){
			logger.debug("接收到SmsSend请求:{}",message);
		}
		
		CoreInterfaceImpl coreInterfaceImpl = (CoreInterfaceImpl) SpringUtils.getBean("coreInterfaceImpl");
		Map<String,String> xmlHead = coreInterfaceImpl.parseXmlHead(message);
		return coreInterfaceImpl.processAPRequest("smssend", message,xmlHead);
	}
	
	
	public String smsGetReport(String message){
		if(logger.isDebugEnabled()){
			logger.debug("接收到SmsGetReport请求:{}",message);
		}
		CoreInterfaceImpl coreInterfaceImpl = (CoreInterfaceImpl) SpringUtils.getBean("coreInterfaceImpl");
		Map<String,String> xmlHead = coreInterfaceImpl.parseXmlHead(message);
		return coreInterfaceImpl.processAPRequest("smsgetreport", message,xmlHead);
	}
	
	
	public String smsReceive(String message){
		if(logger.isDebugEnabled()){
			logger.debug("接收到SmsReceive请求:{}",message);
		}
		CoreInterfaceImpl coreInterfaceImpl = (CoreInterfaceImpl) SpringUtils.getBean("coreInterfaceImpl");
		Map<String,String> xmlHead = coreInterfaceImpl.parseXmlHead(message);
		return coreInterfaceImpl.processAPRequest("smsreceive", message,xmlHead);
	}
}
