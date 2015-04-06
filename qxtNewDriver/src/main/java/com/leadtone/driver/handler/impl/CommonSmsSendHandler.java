package com.leadtone.driver.handler.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

import com.leadtone.driver.bean.SendResultBean;
import com.leadtone.driver.bean.SmsBean;
import com.leadtone.driver.handler.ISmsSendHandler;
import com.leadtone.driver.service.ISmsService;
import com.leadtone.util.DateUtil;
import com.leadtone.util.Nodelet;
import com.leadtone.util.XmlUtils;
import com.litong.sms.impl.SmsClientImpl;

public class CommonSmsSendHandler implements ISmsSendHandler {

	Logger logger = Logger.getLogger(CommonSmsSendHandler.class);
	private ISmsService smsService;
//	private IMerchantService merchantService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	public void processer(Long merchantPin, String account, String password, String uid, String ip, String rptIp, boolean perAccount) {
		int limit = 200;
		try{
//			logger.error("QXT短信处理["+merchantPin+":"+merchantPin+"]，processer：");
			List<SmsBean> smsList = smsService.getReadySendSms(merchantPin, 14, 0, DateUtil.getNowFormatTime("yyyy-MM-dd HH:mm:ss"), limit, null, uid, perAccount);
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for(int j = 0; smsList != null && j < smsList.size();j++){
        		SmsBean bean = smsList.get(j);
        		String taskID = "";
        		Map<String, Object> resultMap = new HashMap<String, Object>();
        		try{
        			String rsp = SmsClientImpl.sendMsg(ip, account, password,
        					bean.getTos(), bean.getContent(),null, null);
        			
//        			logger.info("merchantPin--userid:"+merchantPin+"--"+uid+"QXT上行xml:"+rsp);
        			
        			SendResultBean tempBean = parseXmlBody(rsp, merchantPin, uid);
        			taskID = tempBean.getTaskId();
        	        if("Success".equalsIgnoreCase(tempBean.getReturnStatus())){
        	        	resultMap.put("send_result", 1);
        	        	resultMap.put("fail_reason", "UNRETUN");
        	        } else{
        	        	resultMap.put("send_result", 3);
        	        	resultMap.put("fail_reason", tempBean.getMessage());
        	        }
        		}catch(Exception e){
        			logger.error(e);
        			e.printStackTrace();
        			resultMap.put("send_result", 3);
    	        	resultMap.put("fail_reason", "UNRETUN");
        		}
    			resultMap.put("id", bean.getId());
    			resultMap.put("cpoid", taskID);
    			resultList.add(resultMap);
        	}
			if( resultList!=null && resultList.size() > 0 ){
				//提交成功
				smsService.updateSmsSendCpoid(resultList);
			}
		}catch(Exception e){
			logger.error("QXT短信处理失败["+merchantPin+":"+merchantPin+"],uid:"+uid+"，原因：", e);
		    e.printStackTrace();
		}
	}
	
	private SendResultBean parseXmlBody(String xmlDoc, Long merchantPin, String uid){
		//0,11120908560001
		logger.info("merchantPin--userid:"+ merchantPin +"--" + uid + "QXT发送回执:"+xmlDoc);
		SendResultBean tempBean = new SendResultBean();
		if( xmlDoc!=null && !xmlDoc.isEmpty() ){
			String[] resultArray = xmlDoc.split(",");
			switch(resultArray[0]){
				case "0": 
					tempBean.setReturnStatus("Success"); 
					break;
				case "-1": 
					tempBean.setReturnStatus("Error"); 
					tempBean.setMessage("帐号或密码为空");
					break;
				case "-2": 
					tempBean.setReturnStatus("Error"); 
					tempBean.setMessage("下发号码为空");
					break;
				case "-3": 
					tempBean.setReturnStatus("Error"); 
					tempBean.setMessage("下发内容为空");
					break;
				case "-4": 
					tempBean.setReturnStatus("Error"); 
					tempBean.setMessage("内容超长");
					break;
				case "-5": 
					tempBean.setReturnStatus("Error"); 
					tempBean.setMessage("下发号码超长");
					break;
				case "-99": 
					tempBean.setReturnStatus("Error"); 
					tempBean.setMessage("系统异常");
					break;
			}
			if(resultArray[1]!=null&&resultArray[1].length()>16){
				tempBean.setTaskId(resultArray[1].substring(0, resultArray[1].length()-1));
			}else{
				tempBean.setTaskId(resultArray[1]);
			}
//			tempBean.setTaskId(resultArray[1]);//+"0"
		}else{
			
		}
		 
		return tempBean;
	}

	public ISmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(ISmsService smsService) {
		this.smsService = smsService;
	}

//	public static void main(String[] args) {
//		CommonSmsSendHandler sender = new CommonSmsSendHandler();
//		sender.processer(1L,"","","","");
//	}
}
