package com.leadtone.driver.handler.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leadtone.driver.bean.SmsBean;
import com.leadtone.driver.handler.ISmsSendHandler;
import com.leadtone.driver.service.ISmsService;
import com.leadtone.util.DateUtil;
import com.leadtone.util.PinGen;
import com.litong.sms.impl.SmsClientImpl;

public class CommonSmsSendHandler implements ISmsSendHandler {

	Logger logger = Logger.getLogger(CommonSmsSendHandler.class);
	private ISmsService smsService;
//	private IMerchantService merchantService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	public void processer(Long merchantPin, String account, String password, String ip, String rptIp) {
		int limit = 200;
		try{
//			logger.error("ZXT短信处理["+merchantPin+":"+merchantPin+"]，processer：");
			String userId = "";
			List<SmsBean> smsList = smsService.getReadySendSms(merchantPin, 10, 0, DateUtil.getNowFormatTime("yyyy-MM-dd HH:mm:ss"), limit, null);
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for(int j = 0; smsList != null && j < smsList.size();j++){
        		SmsBean bean = smsList.get(j);
        		String taskID = "";
        		Map<String, Object> resultMap = new HashMap<String, Object>();
        		try{
        			userId = bean.getSmsAccessNumber();
        			taskID = bean.getId().toString().substring(bean.getId().toString().length()-8, bean.getId().toString().length());
        			String rsp = SmsClientImpl.sendMsg(account,password,"key",ip,
        					bean.getTos(),URLEncoder.encode(bean.getContent(), "utf-8"),
        					sdf.format(new Date()), taskID, userId);
        			logger.info("merchantPin:"+merchantPin+",tos:"+bean.getTos()+";rspXml:"+ rsp);
        	        if("success".equalsIgnoreCase(rsp)){
        	        	resultMap.put("send_result", 1);
        	        	resultMap.put("fail_reason", "UNRETUN");
        	        } else{
        	        	String tempStr = String.valueOf(PinGen.getSerialPin());
        	        	taskID = tempStr.substring(tempStr.length()-8, tempStr.length());
        	        	resultMap.put("send_result", 3);
        	        	resultMap.put("fail_reason", rsp);
        	        }
        		}catch(Exception e){
        			logger.error(e);
        			e.printStackTrace();
        			resultMap.put("send_result", 3);
    	        	resultMap.put("fail_reason", "UNRETUN");
        		}catch(Throwable e){
        			logger.error(e);
//        			e.printStackTrace();
        			resultMap.put("send_result", 3);
    	        	resultMap.put("fail_reason", "UNRETUN");
        		}
    			resultMap.put("id", bean.getId());
    			resultMap.put("cpoid", taskID);
    			resultList.add(resultMap);
        	}
			if(resultList!=null&&resultList.size()>0){
				//提交成功
				smsService.updateSmsSendCpoid(resultList);
			}
		}catch(Exception e){
			logger.error("ZXT短信处理失败["+merchantPin+":"+merchantPin+"]，原因：", e);
		    e.printStackTrace();
		}catch(Throwable e){
			logger.error("ZXT短信处理失败["+merchantPin+":"+merchantPin+"]，原因：", e);
//			e.printStackTrace();
		}
		
//		//状态报告获取yes
//		String rptXml = SmsClientImpl.smsRpt(account,password,"key",ip);
////		rptXml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><smsResult>"
////        +"<result><spnumber>68537740</spnumber><phone>18600812629</phone><status>DELIVRD</status><rptcode>KB:001</rptcode><sendtime>20101102000000</sendtime></result>"
////        +"<result><spnumber>68537741</spnumber><phone>手机号码2</phone><status>DELIVRD</status><rptcode>KB:001</rptcode><sendtime>20101102000000</sendtime></result>"
////        +"</smsResult>";
//		List<ReportResult> resultMap = parseXmlBody(rptXml);
//		if(resultMap!=null&&resultMap.size()>0){
//			try {
//				smsService.updateSmsSendReport(resultMap);
//			} catch (Exception e) {
//				logger.error("ZXT短信状态报告处理失败["+ rptXml +"]，原因：", e);
//				e.printStackTrace();
//			}
//		}
	}
	
	//判断当前时间是否在可发时间内，默认是早上8点-晚上22点
	private boolean senderTimeInterval(String timeInterval) {
		int m = 8;
		int n = 22;
		if (timeInterval != null && !"".equals(timeInterval) && timeInterval.length() >= 5){
			String mStr = timeInterval.substring(0, 2);
			String nStr = timeInterval.substring(3, 5);
			m = Integer.parseInt(mStr);
			n = Integer.parseInt(nStr);
		}
		return DateUtil.inTimeInterval(Calendar.getInstance().getTime(), m, n);
	}

	public ISmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(ISmsService smsService) {
		this.smsService = smsService;
	}
//	private List<ReportResult> parseXmlBody(String xmlDoc){
//		final List<ReportResult> parameters=new ArrayList<ReportResult>();
////		Document xmlBodyDoc = XmlUtils.parseText(xmlDoc);
////		String body = XmlUtils.getText(xmlBodyDoc, "//smsResult");
//		Document xmlBody = XmlUtils.parseText(xmlDoc);
//		XmlUtils.selectNodes(xmlBody.getRootElement(), "result", new Nodelet<Object>() {
//			@Override
//			public Object processNode(Node node) {
//				ReportResult tempBean = new ReportResult();
//				tempBean.setPhone(XmlUtils.getText(node, "phone"));
//				tempBean.setRptcode(XmlUtils.getText(node, "rptcode"));
//				tempBean.setSendtime(XmlUtils.getText(node, "sendtime"));
//				tempBean.setSpnumber(XmlUtils.getText(node, "spnumber"));
//				tempBean.setStatus(XmlUtils.getText(node, "status"));
//				parameters.add(tempBean);
//				return null;
//			}
//		});
//		
//		return parameters;
//	}
//	public static void main(String[] args) {
//		CommonSmsSendHandler sender = new CommonSmsSendHandler();
//		sender.processer(1L,"","","","");
//	}
}
