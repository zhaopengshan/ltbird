package com.leadtone.sender.handler.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leadtone.sender.bean.ConfigParam;
import com.leadtone.sender.bean.GatewaySmsBean;
import com.leadtone.sender.bean.ModemSmsBean;
import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.bean.UTcomGatewaySmsBean;
import com.leadtone.sender.handler.ISmsSendHandler;
import com.leadtone.sender.service.ICorpService;
import com.leadtone.sender.service.IMerchantService;
import com.leadtone.sender.service.ISmsLimitService;
import com.leadtone.sender.service.ISmsPacketService;
import com.leadtone.sender.service.ISmsService;
import com.leadtone.util.DateUtil;

public class CommonSmsSendHandler implements ISmsSendHandler {

	Logger logger = Logger.getLogger(CommonSmsSendHandler.class);
	private ISmsService smsService;
	private ISmsLimitService smsLimitService;
	private ISmsPacketService smsPacketService;
	private IMerchantService merchantService;
	private ICorpService corpService;
//	private long timeStart = System.currentTimeMillis();
	@Override
	public void processer(Long merchantPinTemp, String provinceStr) {
//		logger.info("start time test1--" + (System.currentTimeMillis() - timeStart) );
		ConfigParam configParam = merchantService.getConfigParam(merchantPinTemp, "sms_send_time_interval");
		if(this.senderTimeInterval(configParam==null?"08-22":configParam.getItemValue())){
			try{
				int limit = 400;
				int nLenth = 0;//企业签名长度
				List<SmsBean> smsList = smsService.getReadySendSms(merchantPinTemp, 0, DateUtil.getNowFormatTime("yyyy-MM-dd HH:mm:ss"), limit);
				List<SmsBean> smsListYd= new ArrayList<SmsBean>();
				List<SmsBean> smsListLt= new ArrayList<SmsBean>();
				List<SmsBean> smsListDx= new ArrayList<SmsBean>();
				List<SmsBean> smsListQw= new ArrayList<SmsBean>();
				List<SmsBean> smsListMao= new ArrayList<SmsBean>();
				List<SmsBean> smsListTd= new ArrayList<SmsBean>();
				List<SmsBean> smsListZxt= new ArrayList<SmsBean>();
				List<SmsBean> smsListQxt= new ArrayList<SmsBean>();
				List<SmsBean> smsListPk= new ArrayList<SmsBean>();
				List<SmsBean> smsListYdSw= new ArrayList<SmsBean>();
				List<SmsBean> smsListQxtNew= new ArrayList<SmsBean>();
				List<SmsBean> smsListEmpp= new ArrayList<SmsBean>();
				List<SmsBean> smsCancel = new ArrayList<SmsBean>();
				ConfigParam signParam = merchantService.getConfigParam(merchantPinTemp, "sms_sign_content");
				if ((signParam != null) && (signParam.getItemValue() != null)) {
					nLenth = signParam.getItemValue().length();
				}
//				logger.info("start time test2--" + (System.currentTimeMillis() - timeStart) );
				for(int i=0; smsList!=null && i < smsList.size(); i++){
					SmsBean smsBean = smsList.get(i);
					switch(smsBean.getTunnelType()){
						case 1: 
						case 2: smsListYd.add(smsBean); break;//移动
						case 3: 
						case 4: smsListLt.add(smsBean); break;//联通
						case 5: 
						case 6: smsListDx.add(smsBean); break;//电信
						case 7: smsListQw.add(smsBean); break;//全网
						case 8: smsListMao.add(smsBean); break;//短信猫
						case 9: smsListTd.add(smsBean); break;//TD话机
						case 10: smsListZxt.add(smsBean); break;//资信通
						case 11: smsListQxt.add(smsBean); break;//企信通//
						case 12: smsListPk.add(smsBean); break;//铺客
						case 13: smsListYdSw.add(smsBean); break;//移动异网
						case 14: smsListQxtNew.add(smsBean); break;//新企信通//
						case 15: smsListEmpp.add(smsBean); break;//empp接入方式
						default: break;
					}
				}
//				logger.info("start time test3--" + (System.currentTimeMillis() - timeStart) );
				List tunnelList = merchantService.getTunnelInfo(merchantPinTemp, 1);
				for( int j=0; tunnelList!=null && j < tunnelList.size(); j++ ){
					Map tunnelInfo = (Map) tunnelList.get(j);
					Integer classify = (Integer) tunnelInfo.get("classify");
					switch(classify){
						case 1: 
						case 2: gatewaySmsYd(signParam, tunnelInfo, nLenth, smsListYd, merchantPinTemp, 1);
								smsListYd = null;
								break;//移动
						case 3: 
						case 4: gatewaySmsLtDx(signParam, tunnelInfo, nLenth, smsListLt, merchantPinTemp); 
								smsListLt = null;
								break;
						case 5: 
						case 6: gatewaySmsLtDx(signParam, tunnelInfo, nLenth, smsListDx, merchantPinTemp); 
								smsListDx = null;
								break;//联通+电信
						case 7: 
								//smsListQw = null; 
								break;//全网无处理
						case 8: maoSms(signParam, tunnelInfo, nLenth, smsListMao, merchantPinTemp); 
								smsListMao = null;
								break;//短信猫
						case 9: 
								//smsListTd = null; 
								break;//TD话机
//						case 10: zxtSms(signParam, nLenth, smsListZxt); 
//								break;//资信通
//						case 11: 
//								smsListQxt = null; 
//								break;//企信通
						case 12: 
								//smsListPk = null; 
								break;//铺客
						case 13: 
								gatewaySmsYd(signParam, tunnelInfo, nLenth, smsListYdSw, merchantPinTemp, 13);
								smsListYdSw = null;
								break;//移动 三网
						case 14: 
								break;
						case 15: //empp通道
								emppSmsNew(signParam, tunnelInfo, nLenth, smsListEmpp, merchantPinTemp);
								smsListEmpp = null;
								break;
						default: break;
					}
				}
				if( smsListZxt != null && smsListZxt.size() > 0 ){
					zxtSms(signParam, nLenth, smsListZxt, merchantPinTemp);
					smsListZxt = null;
				}
				if( smsListQxt != null && smsListQxt.size() > 0 ){
					qxtSms(signParam, nLenth, smsListQxt, merchantPinTemp);
					smsListQxt = null;
				}
				if( smsListQxtNew != null && smsListQxtNew.size() > 0 ){
					qxtSmsNew(signParam, nLenth, smsListQxtNew, merchantPinTemp);
					smsListQxtNew = null;
				}
				
				//发送取消
				if( smsListEmpp != null ){
					smsCancel.addAll(smsListEmpp);
				}
				if(smsListQxtNew!=null){
					smsCancel.addAll(smsListQxtNew);
				}
				if(smsListQxt!=null){
					smsCancel.addAll(smsListQxt);
				}
				if(smsListZxt!=null){
					smsCancel.addAll(smsListZxt);
				}
				if(smsListYd!=null){
					smsCancel.addAll(smsListYd);
				}
				if(smsListYdSw!=null){
					smsCancel.addAll(smsListYdSw);
				}
				if(smsListLt!=null){
					smsCancel.addAll(smsListLt);
				}
				if(smsListDx!=null){
					smsCancel.addAll(smsListDx);
				}
				if(smsListQw!=null){
					smsCancel.addAll(smsListQw);
				}
				if(smsListMao!=null){
					smsCancel.addAll(smsListMao);
				}
				if(smsListTd!=null){
					smsCancel.addAll(smsListTd);
				}
				if(smsListQxt!=null){
					smsCancel.addAll(smsListQxt);
				}
				if(smsListPk!=null){
					smsCancel.addAll(smsListPk);
				}
				if( smsCancel!=null&&smsCancel.size()>0 ){
					smsService.updateSmsSendCancel(smsCancel);
				}
//				logger.info("start time test4--" + (System.currentTimeMillis() - timeStart) );
			}catch(Exception e){
				logger.error("短信处理失败["+provinceStr+":"+merchantPinTemp+"]，原因："+e);
			    e.printStackTrace();
			}
		}else{
			logger.info("商户["+merchantPinTemp+"]，当前时间时间段不允许发送短信！");
		}
	}
	private void qxtSms(ConfigParam signParam, int nLenth, List<SmsBean> smsListQxt, Long merchantPin){
		if( smsListQxt != null && smsListQxt.size() > 0 ){
			List<Map<String, Object>> resultList = smsService.saveQxtDriverSms(smsListQxt);
			logMessage(resultList, merchantPin);
			smsService.updateSmsSendRestlt(resultList);
		}
	}
	private void qxtSmsNew(ConfigParam signParam, int nLenth, List<SmsBean> smsListQxtNew, Long merchantPin){
		if( smsListQxtNew != null && smsListQxtNew.size() > 0 ){
			List<Map<String, Object>> resultList = smsService.saveQxtNewDriverSms( smsListQxtNew );
			logMessage(resultList, merchantPin);
			smsService.updateSmsSendRestlt(resultList);
		}
	}
	private void emppSmsNew(ConfigParam signParam, Map tunnelInfo, int nLenth, List<SmsBean> smsListEmpp, Long merchantPin){
		if(smsListEmpp!=null&&smsListEmpp.size()>0){
			List gatewaySmsList = null;
			List<Map<String, Object>> resultList = null;
	      	gatewaySmsList = assembleGatewaySmsEmpp(smsListEmpp, tunnelInfo, nLenth);
	      	resultList = smsService.saveEmppSms(gatewaySmsList);
	      	logMessage(resultList, merchantPin);
	      	smsService.updateSmsSendRestlt(resultList);
		}
	}
	private void zxtSms(ConfigParam signParam, int nLenth, List<SmsBean> smsListZxt, Long merchantPin){
		if(smsListZxt!=null&&smsListZxt.size()>0){
			List<Map<String, Object>> resultList = smsService.saveZxtDriverSms(smsListZxt);
			logMessage(resultList, merchantPin);
			smsService.updateSmsSendRestlt(resultList);
		}
	}
	private void maoSms(ConfigParam signParam, Map tunnelInfo, int nLenth, List<SmsBean> smsListMao, Long merchantPin){
		if(smsListMao!=null&&smsListMao.size()>0){
			List<ModemSmsBean> modemSmsList = assembleModemSms(smsListMao);
		    List<Map<String, Object>> resultList  = smsService.saveModemSms(modemSmsList);
		    logMessage(resultList, merchantPin);
		    smsService.updateSmsSendRestlt(resultList);
		}
	}
	private void gatewaySmsYd(ConfigParam signParam, Map tunnelInfo, int nLenth, List<SmsBean> smsListYd, Long merchantPin, int type){
		if(smsListYd!=null&&smsListYd.size()>0){
			List gatewaySmsList = null;
			List<Map<String, Object>> resultList = null;
	      	gatewaySmsList = assembleGatewaySms(smsListYd, tunnelInfo, nLenth, type);
	      	resultList = smsService.saveGatewaySms(gatewaySmsList);
	      	logMessage(resultList, merchantPin);
	      	smsService.updateSmsSendRestlt(resultList);
		}
	}
	private void gatewaySmsLtDx(ConfigParam signParam, Map tunnelInfo, int nLenth, List<SmsBean> smsListYd, Long merchantPin){
		if(smsListYd!=null&&smsListYd.size()>0){
			List gatewaySmsList = null;
			List<Map<String, Object>> resultList = null;
	      	gatewaySmsList = assembleUTcomGatewaySms(smsListYd, tunnelInfo, nLenth);
	      	resultList = smsService.saveUTcomGatewaySms(gatewaySmsList);
	      	logMessage(resultList, merchantPin);
	      	smsService.updateSmsSendRestlt(resultList);
		}
	}
	private List<ModemSmsBean> assembleModemSms(List<SmsBean> smsListMao){
		List<ModemSmsBean> modemSmsList = new ArrayList<ModemSmsBean>();
		for(int i=0;i<smsListMao.size();i++){
			SmsBean sms = smsListMao.get(i);
			ModemSmsBean modemSms = new ModemSmsBean();
			modemSms.setId(sms.getId());
			modemSms.setBatchId(sms.getBatchId());
			modemSms.setReadySendTime(sms.getReadySendTime());
			modemSms.setTunnelType(sms.getTunnelType());
			modemSms.setPriorityLevel(sms.getPriorityLevel());
			modemSms.setOperationId(sms.getOperationId());
			modemSms.setMerchantPin(sms.getMerchantPin());
			modemSms.setProvince(sms.getProvince());
			modemSms.setSelfMobile(sms.getSelfMobile());
			modemSms.setSendResult(sms.getSendResult());
//			String selfMobile = sms.getSelfMobile();
//			if("86".equals(selfMobile.substring(0, 1))){
//				modemSms.setSelfMobile(selfMobile);
//			}else{
//				modemSms.setSelfMobile("86"+sms.getSelfMobile());
//			}
			modemSms.setTos(sms.getTos());
			modemSms.setContent(sms.getContent());
			modemSms.setCommitTime(sms.getReadySendTime());
			modemSms.setDescription(sms.getDescription());
			modemSmsList.add(modemSms);
		}
		return modemSmsList;
	}
	private List<GatewaySmsBean> assembleGatewaySmsEmpp(List<SmsBean> smsList, Map<String,String> corpInfo, int signLen){
		List<GatewaySmsBean> gatewaySmsList = new ArrayList<GatewaySmsBean>();
		for(int i=0;i<smsList.size();i++){
			SmsBean sms = smsList.get(i);
			String[] contentArray = smsPacketService.emppPacket(sms.getContent());
//			if(gatewaySmsList.size()+contentArray.length>limit){
//				break;
//			}
			for(int j=0;j<contentArray.length;j++){
				GatewaySmsBean gatewaySms = new GatewaySmsBean();
				gatewaySms.setServiceId(corpInfo.get("service_id"));
//				gatewaySms.setServiceId(corpInfo.get("EMPP"));
				gatewaySms.setMsgSrc(corpInfo.get("sms_corp_ident"));
//				gatewaySms.setMsgSrc("EMPP");
				gatewaySms.setFeeTerminalId(sms.getSmsAccessNumber());
				gatewaySms.setMsgFmt(8);
				gatewaySms.setFeeType("01");
				gatewaySms.setSrcTerminalId(sms.getSmsAccessNumber());
				gatewaySms.setDestUsrTl(1);
				gatewaySms.setDestTerminalId(sms.getTos());
				gatewaySms.setIhResult(-1);
				String ihGateway = "mas_"+sms.getProvince()+"_"+gatewaySms.getMsgSrc();
				gatewaySms.setIhGateway(ihGateway);
				gatewaySms.setIhSession(0);
				gatewaySms.setIhTimestamp(new Date());
				gatewaySms.setMasSmsId(sms.getId());
				//0：短短信；1：长短信
				gatewaySms.setTpUdhi(contentArray.length>1?1:0);
				gatewaySms.setPkTotal(contentArray.length);
				gatewaySms.setPkNumber(j+1);
				gatewaySms.setMsgLength(contentArray[j].length());
				gatewaySms.setMsgContent(contentArray[j]);
				gatewaySms.setFeeUserType(0);
				//是否需要状态报告，0：不需要；1：需要；
				gatewaySms.setRegisteredDelivery(1);
				gatewaySmsList.add(gatewaySms);
			}
		}
		return gatewaySmsList;
	}
	private List<GatewaySmsBean> assembleGatewaySms(List<SmsBean> smsList, Map<String,String> corpInfo, int signLen, int type){
		List<GatewaySmsBean> gatewaySmsList = new ArrayList<GatewaySmsBean>();
		for(int i=0;i<smsList.size();i++){
			SmsBean sms = smsList.get(i);
			String[] contentArray = smsPacketService.packet(sms.getContent(),signLen);
//			if(gatewaySmsList.size()+contentArray.length>limit){
//				break;
//			}
			for(int j=0;j<contentArray.length;j++){
				GatewaySmsBean gatewaySms = new GatewaySmsBean();
				gatewaySms.setServiceId(corpInfo.get("service_id"));
				gatewaySms.setMsgSrc(corpInfo.get("sms_corp_ident"));
				gatewaySms.setFeeTerminalId(sms.getSmsAccessNumber());
				gatewaySms.setMsgFmt(8);
				gatewaySms.setFeeType("01");
				gatewaySms.setSrcTerminalId(sms.getSmsAccessNumber());
				gatewaySms.setDestUsrTl(1);
				gatewaySms.setMsgLevel(sms.getPriorityLevel());
				gatewaySms.setDestTerminalId(sms.getTos());
				gatewaySms.setIhResult(-1);
				String ihGateway = "";
				if(type == 13){
					ihGateway = "mas_"+sms.getProvince()+"_yw_"+gatewaySms.getMsgSrc();
				}else{
					ihGateway = "mas_"+sms.getProvince()+"_"+gatewaySms.getMsgSrc();
				}
				gatewaySms.setIhGateway(ihGateway);
				gatewaySms.setIhSession(0);
				gatewaySms.setIhTimestamp(new Date());
				gatewaySms.setMasSmsId(sms.getId());
				//0：短短信；1：长短信
				gatewaySms.setTpUdhi(contentArray.length>1?1:0);
				gatewaySms.setPkTotal(contentArray.length);
				gatewaySms.setPkNumber(j+1);
				gatewaySms.setMsgLength(contentArray[j].length());
				gatewaySms.setMsgContent(contentArray[j]);
				gatewaySms.setFeeUserType(0);
				//是否需要状态报告，0：不需要；1：需要；
				gatewaySms.setRegisteredDelivery(1);
				gatewaySmsList.add(gatewaySms);
			}
		}
		return gatewaySmsList;
	}
	private List<UTcomGatewaySmsBean> assembleUTcomGatewaySms(List<SmsBean> smsList, Map<String,String> corpInfo, int signLen){
		List<UTcomGatewaySmsBean> gatewaySmsList = new ArrayList<UTcomGatewaySmsBean>();
		for(int i=0;i<smsList.size();i++){
			SmsBean sms = smsList.get(i);
			String[] contentArray = smsPacketService.packet(sms.getContent(),signLen);
//			if(gatewaySmsList.size()+contentArray.length>limit){
//				break;
//			}
			for(int j=0;j<contentArray.length;j++){
				UTcomGatewaySmsBean gatewaySms = new UTcomGatewaySmsBean();
				gatewaySms.setMasSmsId(sms.getId());
				gatewaySms.setPkTotal(contentArray.length);
				gatewaySms.setPkNumber(j+1);
				gatewaySms.setServiceId(corpInfo.get("service_id"));
				gatewaySms.setTpUdhi(contentArray.length>1?1:0);
//				gatewaySms.setSrcTerminalId(corpInfo.get("access_number"));
				gatewaySms.setSrcTerminalId(sms.getSmsAccessNumber());
				gatewaySms.setDestTerminalId(sms.getTos());
				gatewaySms.setMsgContent(contentArray[j]);
				//是否需要状态报告，0：不需要；1：需要；
				gatewaySmsList.add(gatewaySms);
			}
		}
		return gatewaySmsList;
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
	private void logMessage(List<Map<String, Object>> resultList, Long merchantPin){
		StringBuffer logStr = new StringBuffer();
		logStr.append("merchantPin:"+merchantPin);
		for( int i =0; resultList != null && i < resultList.size(); i++ ){
			Map<String, Object> map =  resultList.get(i);
			logStr.append(",id:");
			logStr.append(map.get("id"));
			logStr.append(",send_result:");
			logStr.append(map.get("send_result"));
			logStr.append(";");
		}
		logStr.append("end");
		logger.info(logStr.toString());
//		return logStr.toString();
	}
	public ISmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(ISmsService smsService) {
		this.smsService = smsService;
	}

	public ISmsLimitService getSmsLimitService() {
		return smsLimitService;
	}

	public void setSmsLimitService(ISmsLimitService smsLimitService) {
		this.smsLimitService = smsLimitService;
	}

	public ISmsPacketService getSmsPacketService() {
		return smsPacketService;
	}

	public void setSmsPacketService(ISmsPacketService smsPacketService) {
		this.smsPacketService = smsPacketService;
	}

	public IMerchantService getMerchantService() {
		return merchantService;
	}

	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}

	public ICorpService getCorpService() {
		return corpService;
	}

	public void setCorpService(ICorpService corpService) {
		this.corpService = corpService;
	}
}
