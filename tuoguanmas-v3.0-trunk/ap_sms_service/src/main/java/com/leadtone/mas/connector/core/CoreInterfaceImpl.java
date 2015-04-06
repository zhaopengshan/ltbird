package com.leadtone.mas.connector.core;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.crypto.Cipher;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.j2eebestpractice.ssiframework.util.DateUtil;

import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.service.IRegionService;
import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.BizUtils;
import com.leadtone.mas.bizplug.util.SmsNumberArithmetic;
import com.leadtone.mas.connector.dao.PortalUserDao;
import com.leadtone.mas.connector.dao.SmsGetReportDao;
import com.leadtone.mas.connector.dao.SmsReceiveDao;
import com.leadtone.mas.connector.dao.SmsSendDao;
import com.leadtone.mas.connector.domain.PortalUser;
import com.leadtone.mas.connector.domain.SmsGetReport;
import com.leadtone.mas.connector.domain.SmsReceive;
import com.leadtone.mas.connector.domain.SmsSend;
import com.leadtone.mas.connector.utils.EncryptUtils;
import com.leadtone.mas.connector.utils.Nodelet;
import com.leadtone.mas.connector.utils.PinGen;
import com.leadtone.mas.connector.utils.StringUtil;
import com.leadtone.mas.connector.utils.XmlUtils;

/**
 * 
 * @author hejiyong
 * date:2013-1-23
 * 
 */
@Component("coreInterfaceImpl")
public class CoreInterfaceImpl implements CoreInterface{
	private static final Logger logger = LoggerFactory.getLogger(CoreInterface.class);
	private static final byte[] defaultIV = { 49, 50, 51, 52, 53, 54, 55, 56 };//{ 1, 2, 3, 4, 5, 6, 7, 8 };//modify sunyadong 20130529
	private static final byte[] defaultIVOld = { 1, 2, 3, 4, 5, 6, 7, 8 };
	private static final String DES_ALGORITHM = "DESede/CBC/PKCS5Padding";
	
	private static final String ACCOUNT_ID = "AccountId";
	private static final String ACCOUNT_PWD = "AccountPwd";
	private static final String MERCHANT_PIN = "merchant_pin";
	private static final String TUNNEL_ID = "TunnelId";
	private static final String PRIORITY_LEVEL = "PriorityLevel";
	private static final String MOBILES = "Mobiles";
	private static final String PORTAILUSER = "PORTAL_USER";
	private static final String CONTENT = "Content";
	private static final String DESDE_KEY = "DesDe_Key";
	private static final String ID = "Id";
	
	@Autowired
	private MbnSevenHCodeService mbnSevenHCodeService;
	 @Resource
	 private PortalUserDao portalUserDao;
	 @Resource
	 private SmsGetReportDao smsGetReportDao;
	 @Resource
	 private SmsReceiveDao smsReceiveDao;
	 @Resource
	 private SmsSendDao smsSendDao;
	 @Autowired
	 private IRegionService regionService;
	 
	 @Autowired
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	 @Autowired
	private SmsMbnTunnelService smsMbnTunnelService;
	 @Autowired
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	 @Autowired
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	 @Autowired
	private MbnConfigMerchantIService mbnConfigMerchantIService;
	 
	@Override
	public String toString() {
		return "CoreInterfaceImpl [portalUserDao=" + portalUserDao
				+ ", smsGetReportDao=" + smsGetReportDao + ", smsReceiveDao="
				+ smsReceiveDao + ", smsSendDao=" + smsSendDao
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public CoreInterfaceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String getTrueKey(String key){
		byte[] srcKey = key.getBytes();
		byte[] newKey = new byte[24];
		if(key.length() < 24){
			//用空格补齐
			for(int i = 0; i < key.length(); i++){ //将原始的数组放到新的数组中
				newKey[i] = srcKey[i];
		    }
			for(int j = key.length();j < 24; j++){
				newKey[j] = Byte.parseByte("32");
			}
		}
		else{
			//超过24位的话，取前边24位
			for(int i = 0; i < key.length(); i++){
				newKey[i] = srcKey[i];
			}
		}
		String reallKey = StringUtil.bytes2str(newKey, "UTF-8");
		return reallKey;
	} 
	private String getTrueKeyOld(String key){
		byte[] srcKey = key.getBytes();
		byte[] newKey = new byte[24];
		if(key.length() < 24){
			//用空格补齐
			for(int i = 0; i < key.length(); i++){ //将原始的数组放到新的数组中
				newKey[i] = srcKey[i];
		    }
			for(int j = key.length();j < 24; j++){
				newKey[j] = Byte.parseByte("20");
			}
		}
		else{
			//超过24位的话，取前边24位
			for(int i = 0; i < key.length(); i++){
				newKey[i] = srcKey[i];
			}
		}
		String reallKey = StringUtil.bytes2str(newKey, "UTF-8");
		return reallKey;
	} 
	
	private String Decode(String message,String srckey) throws Exception{
		String key = getTrueKey(srckey);
		byte[] desKey = key.getBytes();
		byte[] msgByte = EncryptUtils.decodeBase64(message);
		byte[] decodeByte = EncryptUtils.DESede(msgByte, DES_ALGORITHM, Cipher.DECRYPT_MODE, desKey, defaultIV);
		return StringUtil.bytes2str(decodeByte, "UTF-8");
	}
	
	private String Encode(String message,String srckey){
		String key = getTrueKey(srckey);
		byte[] desKey = key.getBytes();
		byte[] messagebody = StringUtil.getBytes(message,"UTF-8");
		byte[] encodeByte = EncryptUtils.DESede(messagebody, DES_ALGORITHM, Cipher.ENCRYPT_MODE, desKey, defaultIV);
		return EncryptUtils.base64Encode(encodeByte);
	}
	
	private String DecodeOld(String message,String srckey) throws UnsupportedEncodingException{
		String key = getTrueKeyOld(srckey);
		byte[] desKey = key.getBytes();
		byte[] msgByte = EncryptUtils.decodeBase64(message);
		byte[] decodeByte = EncryptUtils.DESede(msgByte, DES_ALGORITHM, Cipher.DECRYPT_MODE, desKey, defaultIVOld);
		return StringUtil.bytes2str(decodeByte, "UTF-8");
	}
	
	private String EncodeOld(String message,String srckey){
		String key = getTrueKeyOld(srckey);
		byte[] desKey = key.getBytes();
		byte[] messagebody = StringUtil.getBytes(message,"UTF-8");
		byte[] encodeByte = EncryptUtils.DESede(messagebody, DES_ALGORITHM, Cipher.ENCRYPT_MODE, desKey, defaultIVOld);
		return EncryptUtils.base64Encode(encodeByte);
	}
	
	@Override
	public Map<String,String> parseXmlHead(String xmlDoc){
		final Map<String,String> parameters = new HashMap<String,String>();
		Document xmlBodyDoc = XmlUtils.parseText(xmlDoc);
		String PINID = XmlUtils.getText(xmlBodyDoc, "//PINID");
		parameters.put(MERCHANT_PIN, PINID);
		if(logger.isDebugEnabled())
			logger.debug("the PINID is:{}",PINID);
		
		String AccountId = XmlUtils.getText(xmlBodyDoc, "//AccountId");
		parameters.put(ACCOUNT_ID, AccountId);
		
		String AccountPwd = XmlUtils.getText(xmlBodyDoc, "//AccountPwd");
		parameters.put(ACCOUNT_PWD, AccountPwd);
		
		return parameters;
	}
	
	private Map<String,Object> parseXmlBody(String xmlDoc,Map<String,String> xmHead){
		final Map<String,Object> parameters=new HashMap<String, Object>();
		Document xmlBodyDoc = XmlUtils.parseText(xmlDoc);
		String body = XmlUtils.getText(xmlBodyDoc, "//Body");
		
		if(logger.isDebugEnabled())
			logger.debug("the xml body is:{}",body);
		//通过pinID找到id,id做为加密的密钥
		//PortalUser portaluser = portalUserDao.loadbyUid(PinID);
		PortalUser portaluser = portalUserDao.loadbyUserNameAndPwd(
				xmHead.get(ACCOUNT_ID), 
				xmHead.get(ACCOUNT_PWD), 
				xmHead.get(MERCHANT_PIN));
		if(logger.isDebugEnabled())
			logger.debug("the DES KEY is:{}",portaluser.getId());
		
		//记录用户对象
		parameters.put(PORTAILUSER, portaluser);
		String decodedbody = null;
		try {
			decodedbody = Decode(body,portaluser.getId()+"");
			parameters.put("version", true);
		} catch (Exception e) {
			logger.error("decodedbody error will do decode old methord");
			try {
				decodedbody = DecodeOld(body,portaluser.getId()+"");
				parameters.put("version", false);
			} catch (Exception e1) {
				logger.error("decodedbody error:"+e1.toString());
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("the decoded xml body is:{}",decodedbody);
		//记录密钥
		parameters.put(DESDE_KEY, portaluser.getId()+"");

		// 带修正解析
		Document xmlBody = parseTextWithFix(decodedbody);//XmlUtils.parseText(decodedbody);
		
		XmlUtils.selectNodes(xmlBody.getRootElement(), "//BODY/*", new Nodelet<Object>() {
			@Override
			public Object processNode(Node node) {
				String _nodeName = node.getName();
				parameters.put(_nodeName, node.getText());
				if(logger.isDebugEnabled())
					logger.debug("key:{},value:{}",_nodeName,node.getText());
				return null;
			}
		});
		
		return parameters;
	}
	
	/*
	 * */
	@Override
	public String processAPRequest(String requestType,String message,Map<String,String> xmlHead){
		String PINID = xmlHead.get(MERCHANT_PIN);
		Map<String,Object> xmlBodyMap = parseXmlBody(message,xmlHead);
		if("smsgetreport".equalsIgnoreCase(requestType)){
			return processSmsGetReport(xmlBodyMap,PINID);
		}
		else if("smsreceive".equalsIgnoreCase(requestType)){
			return processSmsReceive(xmlBodyMap,PINID);
		}
		else if("smssend".equalsIgnoreCase(requestType)){
			return processSmsSend(xmlBodyMap,PINID);
		}
		else{
			logger.info("未知的请求类型{}",requestType);
		}
		//加密返回
		return "";
	}
	
	/**
	 * 
	 * @param merchantPin
	 * @return
	 */
	private HashMap<Integer, SmsMbnTunnelVO> getTunnelList(Long merchantPin){
        // 获取商户通道列表
		List<MbnMerchantTunnelRelation> relList = mbnMerchantTunnelRelationService.findByPin(merchantPin);
		HashMap<Integer, SmsMbnTunnelVO>  tunnelList = new HashMap<Integer, SmsMbnTunnelVO>();
		for( MbnMerchantTunnelRelation rel : relList){
			SmsMbnTunnelVO tvo = null;
			try {
				tvo = smsMbnTunnelService.queryByPk(rel.getTunnelId());
			} catch (Exception e) {
				// ignore
			}
			if( tvo != null){
				MbnMerchantConsume consume = mbnMerchantConsumeService.findByTunnelId(merchantPin, tvo.getId());
				if( consume!=null){
					tvo.setSmsNumber(consume.getRemainNumber());
				}else{
					tvo.setSmsNumber(0L);
				}
//				tunnelList.put(tvo.getClassify(), tvo);
				tunnelList.put(tvo.getTunnelRange(), tvo);
				//.add(tvo);
			}
		}
		return tunnelList;
	}
	
	private void makeSureClassify(Map<String,Object> xmlBodyMap, SmsSend msg, String provCode, String PINID, PortalUser user, String mobile,
			SmsMbnTunnelVO tunnel, Long batchId, String smsSign){
		String taskNumber = "";
		int tunnel_type=0;//默认
		if(tunnel.getClassify() == 6||tunnel.getClassify() == 5||tunnel.getClassify() == 4||tunnel.getClassify() == 3
				||tunnel.getClassify() == 2||tunnel.getClassify() == 1||tunnel.getClassify() == 13||tunnel.getClassify() == 15){
			// 根据用户来生成2位扩展码
            taskNumber = mbnSmsTaskNumberService.getTaskNumber2(user.getMerchant_pin(), user.getUser_ext_code());
			// 记录扩展码
			MbnSmsTaskNumber num = new MbnSmsTaskNumber();
			num.setId(PinGen.getSerialPin());
			num.setMerchantPin(user.getMerchant_pin());
			num.setOperationCoding(user.getUser_ext_code()); //使用用户的两位扩展
			num.setBatchId(batchId);
			num.setTaskNumber(taskNumber);
			num.setBeginTime(new Date());
			num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), 1500));
			num.setState(1);
			taskNumber = num.getTaskNumber();
			mbnSmsTaskNumberService.addTaskNumber(num);
				if( tunnel.getClassify() == 1 ){
					MbnSevenHCode code = mbnSevenHCodeService.queryByBobilePrefix(StringUtil.getLongPrefix(mobile));
					if( code == null || !provCode.equalsIgnoreCase(code.getProvinceCoding())){//非本省号码走资信通
//						tunnel_type =0;
					} else{
						tunnel_type =1;
						msg.setTunnel_type(tunnel_type);
						msg.setSms_access_number(ProcessAccessNum(xmlBodyMap,mobile,PINID, tunnel, user.getUser_ext_code(), taskNumber));
						return;
					}
				}else if(tunnel.getClassify() == 2){
					tunnel_type =2;
					msg.setTunnel_type(tunnel_type);
					msg.setSms_access_number(ProcessAccessNum(xmlBodyMap,mobile,PINID, tunnel, user.getUser_ext_code(), taskNumber));
					return;
				}
				if(tunnel.getClassify() == 3){
					tunnel_type = 3;
					msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ?  smsSign: "" ));
					msg.setTunnel_type(tunnel_type);
					msg.setSms_access_number(tunnel.getAccessNumber());
					return;
				}else if(tunnel.getClassify() == 4){
					tunnel_type = 4;
					msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ?  smsSign: "" ));
					msg.setTunnel_type(tunnel_type);
					msg.setSms_access_number(tunnel.getAccessNumber());
					return;
				}
				if( tunnel.getClassify() == 5){
					tunnel_type = 5;
					msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ?  smsSign: "" ));
					msg.setTunnel_type(tunnel_type);
					msg.setSms_access_number(tunnel.getAccessNumber());
					return;
				}else if(tunnel.getClassify() == 6){
					tunnel_type = 6;
					msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ? smsSign: "" ));
					msg.setTunnel_type(tunnel_type);
					msg.setSms_access_number(tunnel.getAccessNumber());
					return;
				}else if( tunnel.getClassify() == 15 ){
					//企信通 new
					msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ? smsSign: "" ));
					msg.setSms_access_number(ProcessAccessNum(xmlBodyMap,mobile,PINID, tunnel, user.getUser_ext_code(), taskNumber));
					tunnel_type = 15;
					msg.setTunnel_type(tunnel_type);
					return;
				}else if( tunnel.getClassify() == 13 ){
					//移动 三网
					msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ? smsSign: "" ));
					msg.setSms_access_number( ProcessAccessNum(xmlBodyMap,mobile,PINID, tunnel, user.getUser_ext_code(), taskNumber) );
					tunnel_type = 13;
					msg.setTunnel_type(tunnel_type);
					return;
				}
		}
		if( tunnel.getClassify() == 14 ){
			//企信通 new
			msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ? smsSign: "" ));
			msg.setSms_access_number( String.valueOf( user.getZxt_user_id() ) );
			tunnel_type = 14;
			msg.setTunnel_type(tunnel_type);
			return;
		}else if(tunnel.getClassify() == 11){
			// 企信通
			msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ? smsSign: "" ));
			msg.setSms_access_number( String.valueOf( user.getZxt_user_id() ) );
			tunnel_type = 11;
			msg.setTunnel_type(tunnel_type);
			return;
		}else if(tunnel.getClassify() == 10){
			// 资信通
			msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ? smsSign: "" ));
			msg.setSms_access_number(user.getZxt_user_id());
			tunnel_type = 10;
			msg.setTunnel_type(tunnel_type);
			return;
		}else if(tunnel.getClassify() == 9 ){
			//TD
			msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ?  smsSign: "" ));
			msg.setSms_access_number(user.getZxt_user_id());
			tunnel_type = 9;
			msg.setTunnel_type(tunnel_type);
			return;
		}else if(tunnel.getClassify() == 8){
			//MODEM
			msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ? smsSign: "" ));
			msg.setSms_access_number(user.getZxt_user_id());
			tunnel_type = 8;
			msg.setTunnel_type(tunnel_type);
			return;
		}
	}
	
	private void setSendClassify(Map<String,Object> xmlBodyMap, SmsSend msg, String provCode, String PINID, PortalUser user, String mobile,
			HashMap<Integer, SmsMbnTunnelVO>  tunnelList, Long batchId, String smsSign){
		try{
			//如果请求报文里边存在TunnelId，就用报文里边的值
			//如果报文里边没有该值,联合查询mbn_tunnel,mbn_merchant_tunnel_relation,mbn_h3得出接入号码
			//msg.setSms_access_number(user.getCorp_access_number());
			String threeNumber = mobile.substring(0,3);
			String mobileType = "";
			if(threeNumber.equals("170")){
				String fourthNumber = StringUtil.getShortPrefixFourth(mobile);
				switch(fourthNumber){
					case"0": mobileType = "DX"; break;
					case"5": mobileType = "YD"; break;
					case"9": mobileType = "LT"; break;
					default: ;
				}
			}else{
				mobileType = smsSendDao.loadMobileTypeByH3(threeNumber);
			}
			Iterator<Integer> tunnrlRangeIterator = tunnelList.keySet().iterator();
			while(tunnrlRangeIterator.hasNext()){
				Integer tunnrlRange = tunnrlRangeIterator.next();
				if(mobileType.equalsIgnoreCase("YD")){
					MbnSevenHCode code = mbnSevenHCodeService.queryByBobilePrefix(StringUtil.getLongPrefix(mobile));
					if( code == null || !provCode.equalsIgnoreCase(code.getProvinceCoding())){
						if( (tunnrlRange&2) == 2 ){
							makeSureClassify(xmlBodyMap, msg, provCode, PINID, user, mobile,
									tunnelList.get(tunnrlRange), batchId, smsSign);
							return;
						}
					}else{
						if( (tunnrlRange&1) == 1 ){
							makeSureClassify(xmlBodyMap, msg, provCode, PINID, user, mobile,
									tunnelList.get(tunnrlRange), batchId, smsSign);
							return;
						}
					}
				}
				if( mobileType.equalsIgnoreCase("LT")&&(tunnrlRange&4) == 4){
					makeSureClassify(xmlBodyMap, msg, provCode, PINID, user, mobile,
							tunnelList.get(tunnrlRange), batchId, smsSign);
					return;
				}
				if( mobileType.equalsIgnoreCase("DX")&&(tunnrlRange&8) == 8){
					makeSureClassify(xmlBodyMap, msg, provCode, PINID, user, mobile,
							tunnelList.get(tunnrlRange), batchId, smsSign);
					return;
				}
			}
			logger.info("通道不支持发送号码："+mobile);
		}catch(Exception e){
			logger.error("set send tunnel_type and classify exception:", e);
		}
		/*
		String mobileType = smsSendDao.loadMobileTypeByH3(mobile.substring(0,3));
		int tunnel_type=10;//默认资信通
		if(StringUtils.isNotBlank(mobileType)){
			if(mobileType.equalsIgnoreCase("YD")){
				String localPro = "1";
				String localTunnel = smsSendDao.loadAccessNumByMobile(PINID, localPro);
				if(localTunnel!=null){//本省通道。
					MbnSevenHCode code = mbnSevenHCodeService.queryByBobilePrefix(StringUtil.getLongPrefix(mobile));
					if( code == null || !provCode.equalsIgnoreCase(code.getProvinceCoding())){//非本省号码走资信通
						tunnel_type =10;
					} else{
						tunnel_type =1;
					}	
				} else {//移动全网
					tunnel_type =2;
				}						
			}
			else if(mobileType.equalsIgnoreCase("LT")){
				String ltType = "4";
				String localTunnel = smsSendDao.loadAccessNumByMobile(PINID, ltType);
				if(localTunnel!=null){
					tunnel_type =4;
				} else{
					tunnel_type =10;
				}
			}
			else if(mobileType.equalsIgnoreCase("DX")){
				String ltType = "6";
				String localTunnel = smsSendDao.loadAccessNumByMobile(PINID, ltType);
				if(localTunnel!=null){
					tunnel_type =6;
				} else{
					tunnel_type =10;
				}
			}						
		}
		if(tunnel_type==10){
			msg.setSms_access_number(user.getZxt_user_id());
		} else if(tunnel_type==2){
			msg.setSms_access_number(smsSendDao.loadAccessNumByMobile(PINID, "2"));
		}
		//msg.setTunnel_type(user.getCorp_access_number().substring(2).equalsIgnoreCase("10")?2:3);
		msg.setTunnel_type(tunnel_type);
		*/
	}
	
	private String processSmsSend(Map<String,Object> xmlBodyMap,String PINID){
		try{
			Document responseXmlDoc = DocumentHelper.createDocument();
			Element responseXmlRootElement = responseXmlDoc.addElement("Root");
			String mobiles = (String)xmlBodyMap.get(MOBILES);
			PortalUser user = (PortalUser)xmlBodyMap.get(PORTAILUSER);
			// 查询企业签名
			String smsSign = "";
			MbnConfigMerchant confMerchant = mbnConfigMerchantIService.loadByMerchantPin(user.getMerchant_pin(), "sms_sign_content");
			if( confMerchant != null && confMerchant.getItemValue() != null){
				smsSign = confMerchant.getItemValue();
			}
			if(user == null||user.getWebservice()!=2){
				responseXmlRootElement.addElement("Result").setText("0003");
			}else{
				responseXmlRootElement.addElement("Result").setText("0000");
				String[] user_mobile = mobiles.split(",");
				
				HashMap<Integer, SmsMbnTunnelVO>  tunnelList = getTunnelList(user.getMerchant_pin());
				Long batchId = PinGen.getSerialPin();
				for(String mobile:user_mobile){
					SmsSend msg = new SmsSend();
					msg.setId(PinGen.getSerialPin());
					msg.setMerchant_pin(user.getMerchant_pin());
					//msg.setOperation_id()
					//msg.setTask_number("");
					msg.setBatch_id(batchId);
					Region region = regionService.findByProvinceId(Long.parseLong(user.getProvince()));
					String provCode = region.getCode();
					msg.setProvince(provCode);
					msg.setSelf_mobile(user.getMobile());
					msg.setTos(mobile);
					msg.setTos_name(user.getName());
					msg.setContent((String)xmlBodyMap.get(CONTENT));
					//msg.setCut_apart_number(0);
					String time = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
					msg.setCommit_time(time);
					msg.setReady_send_time(time);
					//msg.setExpire_time(1500);
					//msg.setComplete_time(time);
					//
					this.setSendClassify(xmlBodyMap, msg, provCode, PINID, user, mobile, tunnelList, batchId, smsSign );
					
					
					String PriorityLevel = (String)xmlBodyMap.get(PRIORITY_LEVEL);
					Integer levelP = 3;
					if(StringUtils.isBlank(PriorityLevel)){
						try{
							levelP = Integer.parseInt(PriorityLevel);
						}catch(Exception e){
							levelP = 3;
						}
						msg.setPriority_level(levelP);
					}else{
						msg.setPriority_level(3);
					}
					msg.setSend_result(0);
					msg.setFail_reason("");
					msg.setDescription("");
					msg.setCreate_by(user.getId());
					msg.setTitle("");
					msg.setWebservice(2);
					smsSendDao.save(msg);
					Element msgElement = responseXmlRootElement.addElement("Msg");
					msgElement.addElement("Mobile").setText(mobile);
					msgElement.addElement("Id").setText(msg.getId()+"");
				}
			}
			//加密返回
			Boolean version = (boolean) xmlBodyMap.get("version");
			if(version==null||version){
				return Encode(responseXmlDoc.asXML(),(String)xmlBodyMap.get(DESDE_KEY));
			}else{
				return EncodeOld(responseXmlDoc.asXML(),(String)xmlBodyMap.get(DESDE_KEY));
			}
			
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return "";
	}
	
	private String ProcessAccessNum(Map<String,Object> xmlBodyMap,String mobile,String PINID, 
			SmsMbnTunnelVO tunnel, String userExtCode, String taskNumber){
		
		String tunnelId = (String)xmlBodyMap.get(TUNNEL_ID);
		if(StringUtils.isBlank(tunnelId)){
			if(StringUtils.isNotBlank(tunnel.getAccessNumber())){
				//查找接入号码
//				String accessNum = smsSendDao.loadAccessNumByMobile(PINID, mobileType);
				String accessNumber = BizUtils.buildAccessNumber(tunnel.getAccessNumber(), userExtCode, taskNumber);
				return accessNumber;
			}else{
				logger.info("通道ACCESSNUMBER不存在", mobile);
			}
//			if(logger.isDebugEnabled())
//				logger.debug("手机号{}对应的类型是{}",mobile,mobileType);
		}
		else{
			return tunnelId;
		}
		return null;
	}
	
	private String processSmsReceive(Map<String,Object> xmlBodyMap,String PINID){
		try{
			Document responseXmlDoc = DocumentHelper.createDocument();
			Element responseXmlRootElement = responseXmlDoc.addElement("Root");
			PortalUser user = (PortalUser)xmlBodyMap.get(PORTAILUSER);
			if(user == null||user.getWebservice()!=2){
				responseXmlRootElement.addElement("Result").setText("0003");
			}
			else{
				responseXmlRootElement.addElement("Result").setText("0000");
				List<MasTunnel> userTunnelList = smsMbnTunnelService.getMerchantPinTunnels(user.getMerchant_pin());
				for( MasTunnel tunnelBean : userTunnelList){
					if( tunnelBean.getClassify() == 11 ){
						String qxtUserId = user.getZxt_user_id();// smsReceiveDao.loadAccByUid(user.getMerchant_pin()+"");
						List<SmsReceive> smsReceive=smsReceiveDao.loadByQxtUserId( Integer.parseInt(qxtUserId) );
						for(SmsReceive sms:smsReceive){
							Element msgElement = responseXmlRootElement.addElement("Msg");
							msgElement.addElement("Mobile").setText(sms.getSender_mobile());
							msgElement.addElement("Content").setText(sms.getContent());
							msgElement.addElement("TunnelId").setText(sms.getReceiver_access_number());
//							String time = DateUtil.format(report.getCommit_time(), "yyyy-MM-dd HH:mm:ss");
							msgElement.addElement("SendTime").setText(sms.getCreate_time());
							//更新状态
							smsReceiveDao.updateSmsStatus(sms.getId()+"");
						}
					}else{
						String accessNumber = tunnelBean.getAccessNumber() + (user.getUser_ext_code()==null?"":user.getUser_ext_code());
//						accesses.add(user.getCorp_access_number()+(user.getUser_ext_code()==null?"":user.getUser_ext_code()));
//						accesses.add(user.getZxt_user_id());
						List<SmsReceive> smsReceive=smsReceiveDao.loadByPk( accessNumber );
						for(SmsReceive sms:smsReceive){
							Element msgElement = responseXmlRootElement.addElement("Msg");
							msgElement.addElement("Mobile").setText(sms.getSender_mobile());
							msgElement.addElement("Content").setText(sms.getContent());
							msgElement.addElement("TunnelId").setText(sms.getReceiver_access_number());
							msgElement.addElement("SendTime").setText(sms.getCreate_time());
							//更新状态
							smsReceiveDao.updateSmsStatus(sms.getId()+"");
						}
					}
				}
			}
			Boolean version = (boolean) xmlBodyMap.get("version");
			if(version==null||version){
				return Encode(responseXmlDoc.asXML(),(String)xmlBodyMap.get(DESDE_KEY));
			}else{
				return EncodeOld(responseXmlDoc.asXML(),(String)xmlBodyMap.get(DESDE_KEY));
			}
//			return Encode(responseXmlDoc.asXML(),(String)xmlBodyMap.get(DESDE_KEY));
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return "";
	}
	private String processSmsGetReport(Map<String,Object> xmlBodyMap,String PINID){
		try{
			Document responseXmlDoc = DocumentHelper.createDocument();
			Element responseXmlRootElement = responseXmlDoc.addElement("Root");
			PortalUser user = (PortalUser)xmlBodyMap.get(PORTAILUSER);
			if(user == null||user.getWebservice()!=2){
				responseXmlRootElement.addElement("Result").setText("0003");
			}
			else{
				responseXmlRootElement.addElement("Result").setText("0000");
				String uids = (String)xmlBodyMap.get(ID);
				String[] user_uids = uids.split(",");
				
				List<SmsGetReport> smsGetReport=smsGetReportDao.loadbyPk(Arrays.asList(user_uids));
				for(SmsGetReport report:smsGetReport){
					Element msgElement = responseXmlRootElement.addElement("Msg");
					msgElement.addElement("Id").setText(report.getId()+"");
					msgElement.addElement("Status").setText(report.getSend_result()+"");
					String time = DateUtil.format(report.getCommit_time(), "yyyy-MM-dd HH:mm:ss");
					msgElement.addElement("RecvTime").setText(time);
					String sendtime = DateUtil.format(report.getComplete_time(), "yyyy-MM-dd HH:mm:ss");
					msgElement.addElement("SendTime").setText(sendtime);
				}
			}
			Boolean version = (boolean) xmlBodyMap.get("version");
			if(version==null||version){
				return Encode(responseXmlDoc.asXML(),(String)xmlBodyMap.get(DESDE_KEY));
			}else{
				return EncodeOld(responseXmlDoc.asXML(),(String)xmlBodyMap.get(DESDE_KEY));
			}
//			return Encode(responseXmlDoc.asXML(),(String)xmlBodyMap.get(DESDE_KEY));
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return "";
	}
	
	/**
	 * 修正Content中非法字符，如&符
	 * @param xml
	 * @return
	 */
	private String fixUnvalidContent(String xml){
		String res = xml;
		String regx = "<Content>.*</Content>";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(xml);
		if( matcher.find()){
			String r = matcher.group(0);
			String t = r.substring(9,r.length()-10);
			String m = "<![CDATA[" + t + "]]>";
			res = StringUtils.replaceOnce(xml, r, "<Content>"+m+"</Content>");
		}
		return res;
	}
	/**
	 * 带修正解析
	 * @param decodeBodyXml
	 * @return
	 */
	private Document parseTextWithFix(String decodeBodyXml)
	{
		//拼接xml
		String strxmlBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><BODY>";
		strxmlBody += decodeBodyXml;
		strxmlBody += "</BODY></ROOT>";
		Document document = null;
		try {
			document = DocumentHelper.parseText(strxmlBody);
		} catch (DocumentException ex) {
			logger.error("Parse fail, xml will be fixed and parse again.");
			document = null;
		}
		if( document == null){
			decodeBodyXml = fixUnvalidContent(decodeBodyXml);
			strxmlBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><BODY>";
			strxmlBody += decodeBodyXml;
			strxmlBody += "</BODY></ROOT>";
			try {
				document = DocumentHelper.parseText(strxmlBody);
			} catch (DocumentException ex) {
				logger.error("", ex);
				document = null;
			}
		}
		return document;
	}
	
	public static void main(String[] args){
		CoreInterfaceImpl intf = new CoreInterfaceImpl();
		String key = intf.getTrueKey("sjzadmin");
		String x = intf.Encode("this is a & test.", key);
		System.out.println(x);
		
//		x = intf.Decode("jTUTaPSyDOLltiy8zbh0O6F6eH+IPTYe", key);
		System.out.println(x);
	}
}
