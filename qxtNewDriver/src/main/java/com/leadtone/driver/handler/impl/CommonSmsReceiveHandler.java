package com.leadtone.driver.handler.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	public void processer(Long merchantPin, String account, String password, String uid, String receIp, String rptIp, boolean perAccount) {
		//状态报告获取yes
		String rptXml = SmsClientImpl.smsRpt( account, password, rptIp );
		List<ReportResult> resultMap = parseXmlBody(rptXml, merchantPin, uid);
//		if(resultMap!=null&&resultMap.size()>0){
			try {
				List<SmsBean> reportSms = smsService.getReportSms(merchantPin, resultMap, uid, perAccount);
				smsService.saveZxtDriverSms(reportSms);
			} catch (Exception e) {
				logger.error("QXT短信状态报告处理失败["+ rptXml +"]，原因：", e);
				e.printStackTrace();
			}
//		}
		
		//获取上行yes
		String receiveXml = SmsClientImpl.smsMo(account,password,receIp);
		List<ReceiveResult> receiveMap = parseXmlReceive(receiveXml==null?"":receiveXml.trim(), merchantPin, uid);
		if(receiveMap!=null&&receiveMap.size()>0){
			try {
				smsService.updateSmsSendReceive(receiveMap);
			} catch (Exception e) {
				logger.error("QXT短信上行处理失败["+ rptXml +"]，原因：", e);
				e.printStackTrace();
			}
		}
	}
	private List<ReceiveResult> parseXmlReceive(String xmlDoc, final Long merchantPin, final String uid){
		logger.info("merchantPin--userid:"+ merchantPin +"--"+uid+"QXT上行xml:"+xmlDoc);
//		Mobile|msg 取出来的格式(每次1号码，1个内容，号码和内容用符合|隔开)
//		例如：13900000001|你好
		List<ReceiveResult> parameters=new ArrayList<ReceiveResult>();
		if(xmlDoc.contains("|")){
			String[] recArray = xmlDoc.split("\\|");
			ReceiveResult tempBean = new ReceiveResult();
			tempBean.setContent(recArray[1]);
			tempBean.setMobile(recArray[0]);
			tempBean.setReceivetime(sdf.format( new Date() ));
			tempBean.setTaskid("-1");
			tempBean.setMerchantPin(merchantPin);
			tempBean.setUserId(uid);
			parameters.add(tempBean);
		}else{
			//logger.info("merchantPin--userid:"+ merchantPin +"--"+uid+"QXT上行xml:"+xmlDoc);
		}
		return parameters;
	}
	private List<ReportResult> parseXmlBody(String xmlDoc, final Long merchantPin, final String uid){
		logger.info("merchantPin--userid:"+ merchantPin +"--" + uid + "QXT状态报告xml:"+xmlDoc);
//		msgid,mobile,stat|msgid,mobile,stat|msgid,mobile,stat
//		例如：05201617404137,13900000001,DELIVRD|05220128211118,13900000002,DELIVRD
		List<ReportResult> parameters = new ArrayList<ReportResult>();
		if(xmlDoc.contains("|")){
			String[] rptArray = xmlDoc.split("\\|");
			for( int i = 0; i < rptArray.length; i++ ){
				String rptPerOne = rptArray[i];
				String[] perResult = rptPerOne.split(",");
				ReportResult tempBean = new ReportResult();
				tempBean.setMobile(perResult[1]);
				tempBean.setReceiveTime( sdf.format( new Date() ) );
				tempBean.setStatus(perResult[2].equalsIgnoreCase("DELIVRD")?"10":"20");
				tempBean.setErrorCode(perResult[2]);
				if(perResult[0]!=null&&perResult[0].length()>16){
					tempBean.setTaskId(perResult[0].substring(0, perResult[0].length()-1));
				}else{
					tempBean.setTaskId(perResult[0]);
				}
				
				parameters.add(tempBean);
			}
		}else{
			String[] perResult = xmlDoc.split(",");
			if(perResult!=null&&perResult.length>2){
				ReportResult tempBean = new ReportResult();
				tempBean.setMobile(perResult[1]);
				tempBean.setReceiveTime( sdf.format( new Date() ) );
				tempBean.setStatus(perResult[2].equalsIgnoreCase("DELIVRD")?"10":"20");
				tempBean.setErrorCode(perResult[2]);
				if(perResult[0]!=null&&perResult[0].length()>16){
					tempBean.setTaskId(perResult[0].substring(0, perResult[0].length()-1));
				}else{
					tempBean.setTaskId(perResult[0]);
				}
				
				parameters.add(tempBean);
			}
		}
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
