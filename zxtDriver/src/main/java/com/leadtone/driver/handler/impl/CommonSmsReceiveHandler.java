package com.leadtone.driver.handler.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

import com.leadtone.driver.bean.ReceiveResult;
import com.leadtone.driver.bean.ReportResult;
import com.leadtone.driver.bean.SmsBean;
import com.leadtone.driver.handler.ISmsSendHandler;
import com.leadtone.driver.service.ISmsService;
import com.leadtone.util.Nodelet;
import com.leadtone.util.XmlUtils;
import com.litong.sms.impl.SmsClientImpl;

public class CommonSmsReceiveHandler implements ISmsSendHandler {

	Logger logger = Logger.getLogger(CommonSmsReceiveHandler.class);
	private ISmsService smsService;
//	private IMerchantService merchantService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	public void processer(Long merchantPin, String account, String password, String receIp, String rptIp) {
		String rptXml = "";
		try {
		//状态报告获取yes
			rptXml = SmsClientImpl.smsRpt(account,password,"key",rptIp);
			logger.info("merchantPin:"+merchantPin+";rptXml"+rptXml);
//		rptXml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><smsResult>"
//        +"<result><spnumber>68537740</spnumber><phone>18600812629</phone><status>DELIVRD</status><rptcode>KB:001</rptcode><sendtime>20101102000000</sendtime></result>"
//        +"<result><spnumber>68537741</spnumber><phone>手机号码2</phone><status>DELIVRD</status><rptcode>KB:001</rptcode><sendtime>20101102000000</sendtime></result>"
//        +"</smsResult>";
			List<ReportResult> resultMap = parseXmlBody(rptXml);
//		if(resultMap!=null&&resultMap.size()>0){
			
			List<SmsBean> reportSms = smsService.getReportSms(merchantPin, resultMap);
			smsService.saveZxtDriverSms(reportSms);
		} catch (Exception e) {
			logger.error("ZXT短信状态报告处理失败["+ rptXml +"]，原因：", e);
			e.printStackTrace();
		}
//		}
		
		//获取上行yes
		String receiveXml = "";
		try {
			receiveXml = SmsClientImpl.smsMo(account,password,"key",receIp);
			logger.info("merchantPin:"+merchantPin+";receiveXml"+receiveXml);
	//				rptXml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><moResult>"
	//		        +"<result><phone>18600812629</phone><content>DELIVRD</content><datetime>20101102000000</datetime></result>"
	//		        +"<result><phone>手机号码2</phone><content>DELIVRD</content><datetime>20101102000000</datetime></result>"
	//		        +"</moResult>";
			List<ReceiveResult> receiveMap = parseXmlReceive(receiveXml, merchantPin);
			if(receiveMap!=null&&receiveMap.size()>0){
				smsService.updateSmsSendReceive(receiveMap);
			}
		} catch (Exception e) {
			logger.error("ZXT短信上行处理失败["+ rptXml +"]，原因：", e);
			e.printStackTrace();
		}
	}
	private List<ReceiveResult> parseXmlReceive(String xmlDoc, final Long merchantPin){
		final List<ReceiveResult> parameters=new ArrayList<ReceiveResult>();
		Document xmlBody = XmlUtils.parseText(xmlDoc);
		XmlUtils.selectNodes(xmlBody.getRootElement(), "result", new Nodelet<Object>() {
			@Override
			public Object processNode(Node node) {
				ReceiveResult tempBean = new ReceiveResult();
				tempBean.setPhone(XmlUtils.getText(node, "phone"));
				tempBean.setContent(XmlUtils.getText(node, "content"));
				tempBean.setDatetime(XmlUtils.getText(node, "datetime"));
				tempBean.setMerchantPin(merchantPin);
				parameters.add(tempBean);
				return null;
			}
		});
		
		return parameters;
	}
	private List<ReportResult> parseXmlBody(String xmlDoc){
		final List<ReportResult> parameters=new ArrayList<ReportResult>();
		Document xmlBody = XmlUtils.parseText(xmlDoc);
		XmlUtils.selectNodes(xmlBody.getRootElement(), "result", new Nodelet<Object>() {
			@Override
			public Object processNode(Node node) {
				ReportResult tempBean = new ReportResult();
				tempBean.setPhone(XmlUtils.getText(node, "phone"));
				tempBean.setRptcode(XmlUtils.getText(node, "rptcode"));
				tempBean.setSendtime(XmlUtils.getText(node, "sendtime"));
				tempBean.setSpnumber(XmlUtils.getText(node, "spnumber"));
				tempBean.setStatus(XmlUtils.getText(node, "status"));
				parameters.add(tempBean);
				return null;
			}
		});
		
		return parameters;
	}
	public ISmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(ISmsService smsService) {
		this.smsService = smsService;
	}
	
//	public static void main(String[] args) {
//		CommonSmsReceiveHandler sender = new CommonSmsReceiveHandler();
//		sender.processer(1L,"","","","");
//	}
}
