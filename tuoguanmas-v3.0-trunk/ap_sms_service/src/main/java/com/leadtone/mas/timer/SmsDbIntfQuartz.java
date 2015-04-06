package com.leadtone.mas.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.j2eebestpractice.ssiframework.util.DateUtil;

import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.service.IRegionService;
import com.leadtone.mas.bizplug.common.service.WebUtils;
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
import com.leadtone.mas.connector.core.SmsDbIntfService;
import com.leadtone.mas.connector.dao.PortalUserDao;
import com.leadtone.mas.connector.dao.SmsGetReportDao;
import com.leadtone.mas.connector.dao.SmsReceiveDao;
import com.leadtone.mas.connector.dao.SmsSendDao;
import com.leadtone.mas.connector.domain.PortalUser;
import com.leadtone.mas.connector.domain.SmsGetReport;
import com.leadtone.mas.connector.domain.SmsInboxDbIntf;
import com.leadtone.mas.connector.domain.SmsOutboxDbIntf;
import com.leadtone.mas.connector.domain.SmsReceive;
import com.leadtone.mas.connector.domain.SmsSend;
import com.leadtone.mas.connector.domain.SmsSentDbIntf;
import com.leadtone.mas.connector.utils.PinGen;
import com.leadtone.mas.connector.utils.StringUtil;

@Component
public class SmsDbIntfQuartz {
	private static final Logger logger = LoggerFactory.getLogger(SmsDbIntfQuartz.class);
	@Autowired
	private SmsDbIntfService smsDbIntfService;
	@Resource
	private PortalUserDao portalUserDao;
	@Resource
	private SmsSendDao smsSendDao;
	@Autowired
	private IRegionService regionService;
	@Resource
	private SmsReceiveDao smsReceiveDao;
	@Resource
	private SmsGetReportDao smsGetReportDao;
	@Resource
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Resource
	private SmsMbnTunnelService smsMbnTunnelService;
	@Autowired
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	@Autowired
	private MbnConfigMerchantIService mbnConfigMerchantIService;
	@Autowired
	private MbnSevenHCodeService mbnSevenHCodeService;
	@Autowired
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	@Scheduled(fixedDelay=10000)
	//@Scheduled(cron="0,10,20,30,40,50 * * * * ?")
	public void doSmsDbTask(){
		if( !WebUtils.getDoSmsDbTask()){
			return;
		}
		boolean result = false;
		result = procSmsOutbox();
		logger.info("procSmsOutbox result={}", result);
//		result = procSmsSent();
//		logger.info("procSmsSent result={}", result);
//		result = procSmsInbox();
//		logger.info("procSmsInbox result={}", result);
	}
	@Scheduled(fixedDelay=10000)
	//@Scheduled(cron="0,10,20,30,40,50 * * * * ?")
	public void doSmsDbTaskSent(){
		if( !WebUtils.getDoSmsDbTask()){
			return;
		}
		boolean result = false;
//		result = procSmsOutbox();
//		logger.info("procSmsOutbox result={}", result);
		result = procSmsSent();
		logger.info("procSmsSent result={}", result);
//		result = procSmsInbox();
//		logger.info("procSmsInbox result={}", result);
	}
	@Scheduled(fixedDelay=10000)
	//@Scheduled(cron="0,10,20,30,40,50 * * * * ?")
	public void doSmsDbTaskInbox(){
		if( !WebUtils.getDoSmsDbTask()){
			return;
		}
		boolean result = false;
//		result = procSmsOutbox();
//		logger.info("procSmsOutbox result={}", result);
//		result = procSmsSent();
//		logger.info("procSmsSent result={}", result);
		result = procSmsInbox();
		logger.info("procSmsInbox result={}", result);
	}
	
	@Scheduled(cron="0 0 1 * * ?")
	public void doCleanTask(){
		if( !WebUtils.getDoSmsDbTask()){
			return;
		}
		boolean result = false;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		Date date = cal.getTime();
		result = procDataClean(date);
		logger.info("procDataClean result={}", result);
	}
	
	/**
	 * 转换发送短信至mbn_sms_ready_send
	 * @return
	 */
	private boolean procSmsOutbox(){
		List<SmsOutboxDbIntf> outboxList = smsDbIntfService.getSmsOutbox();
		if( outboxList == null || outboxList.isEmpty()){
			return true;
		}
		try {
			for(SmsOutboxDbIntf outbox: outboxList){
				logger.info("Process id={},mobile={}", outbox.getSiSmsId(), outbox.getDestAddr());
				boolean sendResult = true;
				Long merchantPin = outbox.getMerchantPin();
				PortalUser user = portalUserDao.loadbyUserName(outbox.getAccountId(), merchantPin);
				if( user == null || user.getId() == 0){
					// 用户不存在， 置为发送错误
					logger.info("user not exist, default sendresult fail. Process id={},mobile={},", outbox.getSiSmsId(), outbox.getDestAddr());
					sendResult = false;
				}
				
				// 查询企业签名
				String smsSign = "";
				HashMap<Integer, SmsMbnTunnelVO>  tunnelList = null;
				String mobiles = outbox.getDestAddr();
				String[] user_mobile = mobiles.split(",");
				Long batchId = PinGen.getSerialPin();
				// 根据用户来生成2位扩展码
				String taskNumber = "";
				String provCode ="";
				if(sendResult){
					tunnelList = getRangeTunnelList(user.getMerchant_pin());
					MbnConfigMerchant confMerchant = mbnConfigMerchantIService.loadByMerchantPin(user.getMerchant_pin(), "sms_sign_content");
					if( confMerchant != null && confMerchant.getItemValue() != null){
						smsSign = confMerchant.getItemValue();
					}
					Region region = regionService.findByProvinceId(Long.parseLong(user.getProvince()));
					provCode = region.getCode();
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
				}
				for(String mobile:user_mobile){
					logger.info("Process id={},mobile={}", outbox.getSiSmsId(), mobile);
					Long msgId = PinGen.getSerialPin();
					if( sendResult){
						SmsSend msg = new SmsSend();
						msg.setId( msgId);
						msg.setMerchant_pin(user.getMerchant_pin());
						msg.setBatch_id(batchId);
						msg.setProvince(provCode);
						msg.setSelf_mobile(user.getMobile());
						msg.setTos(mobile);
						msg.setTos_name(user.getName());
						msg.setContent(outbox.getMessageContent());
						String time = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
						msg.setCommit_time(time);
						String sendTime = DateUtil.format(outbox.getRequestTime(), "yyyy-MM-dd HH:mm:ss");
						msg.setReady_send_time(sendTime);
						msg.setExpire_time(1500);
						// 计算通道ID
//						String accessNumber = outbox.getTunnelId();
//						SmsMbnTunnelVO vo = getTunnelInfo(merchantPin, accessNumber, mobile);
//						if( vo == null){
//							logger.info("{} send to {} fail.",outbox.getSiSmsId(), mobile);
//							sendResult = false;
//						}
//						msg.setSms_access_number(vo.getAccessNumber());
//						msg.setTunnel_type(vo.getClassify());
						validSendTunnel(outbox,  mobile, tunnelList, smsSign, provCode, user, batchId, msg, taskNumber);
						
						msg.setPriority_level(3);
						msg.setSend_result(0);
						msg.setFail_reason("");
						msg.setDescription("");
						msg.setCreate_by(user.getId());
						msg.setTitle("");
						msg.setWebservice(3);
						if( sendResult){
							smsSendDao.save(msg);
						}
					}
					// 插入Sms_sent
					String masSmsId = UUID.randomUUID().toString();
					SmsSentDbIntf smsSent = new SmsSentDbIntf();
					smsSent.setMasSmsId(masSmsId);
					smsSent.setSiSmsId(outbox.getSiSmsId());
					if(outbox.getExtCode()!=null){
						smsSent.setExtCode(outbox.getExtCode());
					}else if(user!=null && user.getUser_ext_code()!=null){
						smsSent.setExtCode(user.getUser_ext_code());
					}else{
						smsSent.setExtCode("01");
					}
					smsSent.setDestAddr(mobile);
					smsSent.setRequestTime(outbox.getRequestTime());
					smsSent.setSentTime(new Date());
					if( sendResult){
						smsSent.setSentResult(0);
						smsSent.setReadySendId(msgId);
					}else{
						// 用户不存在，直接置为失败
						smsSent.setSentResult(3);
						smsSent.setReadySendId(0L);
					}
					smsSent.setApplicationId(outbox.getApplicationId());
					smsSent.setMerchantPin(merchantPin);
					smsDbIntfService.insertSmsSent(smsSent);
					// 删除sms_outbox信息
					smsDbIntfService.deleteSmsOutbox(outbox.getSiSmsId());
				}
			}
		}catch(Exception e){
			logger.error("do procSmsOutbox exception", e.getMessage());
		}
		return true;
	}
	private void validSendTunnel(SmsOutboxDbIntf outbox, String mobile,HashMap<Integer, SmsMbnTunnelVO>  tunnelList, String smsSign, 
			String provCode, PortalUser user, Long batchId, SmsSend msg, String taskNumber){
		try{
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
							makeSureClassify(outbox, provCode, user, mobile,
									tunnelList.get(tunnrlRange), batchId, smsSign, msg, taskNumber);
							return;
						}
					}else{
						if( (tunnrlRange&1) == 1 ){
							makeSureClassify(outbox, provCode, user, mobile,
									tunnelList.get(tunnrlRange), batchId, smsSign, msg, taskNumber);
							return;
						}
					}
				}
				if( mobileType.equalsIgnoreCase("LT")&&(tunnrlRange&4) == 4){
					makeSureClassify(outbox, provCode, user, mobile,
							tunnelList.get(tunnrlRange), batchId, smsSign, msg, taskNumber);
					return;
				}
				if( mobileType.equalsIgnoreCase("DX")&&(tunnrlRange&8) == 8){
					makeSureClassify(outbox, provCode, user, mobile,
							tunnelList.get(tunnrlRange), batchId, smsSign, msg, taskNumber);
					return;
				}
			}
			logger.info("通道不支持发送号码："+mobile);
		}catch(Exception e){
			logger.error("set send tunnel_type and classify exception:", e);
		}
	}
	private void makeSureClassify(SmsOutboxDbIntf outbox, String provCode, PortalUser user, String mobile,
			SmsMbnTunnelVO tunnel, Long batchId, String smsSign, SmsSend msg, String taskNumber){
		int tunnel_type=0;//默认
		if(tunnel.getClassify() == 6||tunnel.getClassify() == 5||tunnel.getClassify() == 4||tunnel.getClassify() == 3
				||tunnel.getClassify() == 2||tunnel.getClassify() == 1||tunnel.getClassify() == 13||tunnel.getClassify() == 15){
				if( tunnel.getClassify() == 1 ){
					MbnSevenHCode code = mbnSevenHCodeService.queryByBobilePrefix(StringUtil.getLongPrefix(mobile));
					if( code == null || !provCode.equalsIgnoreCase(code.getProvinceCoding())){//非本省号码走资信通
//						tunnel_type =0;
						logger.error("tunnel Classify=1; MbnSevenHCode == null || provCode != MbnSevenHCode.getProvinceCoding()");
					} else{
						tunnel_type =1;
						msg.setTunnel_type(tunnel_type);
						msg.setSms_access_number(ProcessAccessNum(outbox, mobile, tunnel, user.getUser_ext_code(), taskNumber));
						return;
					}
				}else if(tunnel.getClassify() == 2){
					tunnel_type =2;
					msg.setTunnel_type(tunnel_type);
					msg.setSms_access_number(ProcessAccessNum(outbox, mobile, tunnel, user.getUser_ext_code(), taskNumber));
					return;
				}
				if(tunnel.getClassify() == 3){
					tunnel_type = 3;
					msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ?  smsSign: "" ));
					msg.setTunnel_type(tunnel_type);
					msg.setSms_access_number(tunnel.getAccessNumber());//ProcessAccessNum(outbox, mobile, tunnel, user.getUser_ext_code(), taskNumber)
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
					msg.setSms_access_number(ProcessAccessNum(outbox, mobile, tunnel, user.getUser_ext_code(), taskNumber));
					tunnel_type = 15;
					msg.setTunnel_type(tunnel_type);
					return;
				}else if( tunnel.getClassify() == 13 ){
					//移动 三网
					msg.setContent(msg.getContent() + ( smsSign != null && !smsSign.equals("") ? smsSign: "" ));
					msg.setSms_access_number( ProcessAccessNum(outbox, mobile, tunnel, user.getUser_ext_code(), taskNumber) );
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
	private String ProcessAccessNum(SmsOutboxDbIntf outbox, String mobile,
			SmsMbnTunnelVO tunnel, String userExtCode, String taskNumber){
		String tunnelId = outbox.getTunnelId();
		String extCode = outbox.getExtCode();
		if(StringUtils.isBlank(tunnelId)){
			if(StringUtils.isNotBlank(tunnel.getAccessNumber())){
				String setExtCode = "";
				if(StringUtils.isBlank(extCode)){
					setExtCode = userExtCode;
				}else{
					setExtCode = extCode;
				}
				String accessNumber = BizUtils.buildAccessNumber(tunnel.getAccessNumber(), setExtCode, taskNumber);
				return accessNumber;
			}else{
				logger.info("通道ACCESSNUMBER不存在", mobile);
			}
		}else{
			String setExtCode = "";
			if(StringUtils.isBlank(extCode)){
				setExtCode = userExtCode;
			}else{
				setExtCode = extCode;
			}
			String accessNumber = BizUtils.buildAccessNumber(tunnel.getAccessNumber(), setExtCode, taskNumber);
			return accessNumber;
//			return tunnelId;
		}
		return null;
	}
	private HashMap<Integer, SmsMbnTunnelVO> getRangeTunnelList(Long merchantPin){
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
	private List<SmsMbnTunnelVO> getTunnelList(Long merchantPin){
        // 获取商户通道列表
		List<MbnMerchantTunnelRelation> relList = mbnMerchantTunnelRelationService.findByPin(merchantPin);
		List<SmsMbnTunnelVO>  tunnelList = new ArrayList<SmsMbnTunnelVO>();
		for( MbnMerchantTunnelRelation rel : relList){
			SmsMbnTunnelVO tvo = null;
			try {
				tvo = smsMbnTunnelService.queryByPk(rel.getTunnelId());
			} catch (Exception e) {
				// ignore
			}
			if( tvo != null){
				tunnelList.add(tvo);
			}
		}
		return tunnelList;
	}
	/**
	 * 更新发送状态
	 * @return
	 */
	private boolean procSmsSent(){
		// 获取等待状态报告记录
		List<SmsSentDbIntf> waitRptList = smsDbIntfService.getWaitRptList();
		if( waitRptList == null || waitRptList.isEmpty()){
			return true;
		}
		// 更新状态
		for(SmsSentDbIntf smsSent: waitRptList){
			Long readySendId = smsSent.getReadySendId();
			SmsGetReport rpt = smsGetReportDao.loadById(readySendId);
			if( rpt != null){
				smsSent.setSentResult(rpt.getSend_result());
				smsSent.setStatusTime(rpt.getComplete_time());
				smsSent.setSmsStatus(rpt.getFail_reason());
				smsDbIntfService.updateSmsSent(smsSent);
			}else{
				rpt = smsGetReportDao.loadByIdReady(readySendId);
				if(rpt != null){
					smsSent.setSentResult(1);
					smsDbIntfService.updateSmsSent(smsSent);
				}
			}
		}
		return true;
	}
	/**
	 * 处理短信上行信息
	 * @return
	 */
	private boolean procSmsInbox(){
		List<Long> merchantList = portalUserDao.loadAllMerchantPin();
		if( merchantList ==null || merchantList.isEmpty()){
			return true;
		}
		for( Long merchantPin: merchantList){
			//根据企业通道 处理企业DB人员的上行短信
			List<PortalUser> dbUserList = portalUserDao.loadAllMerchantPinDBUser(merchantPin);
			List<SmsMbnTunnelVO> tunnelList = getTunnelList(merchantPin);
			for( SmsMbnTunnelVO tunnelVo : tunnelList ){
				switch(tunnelVo.getClassify()){
					case 1: 
					case 2: fetchByAccessNumber("CMCC", merchantPin, tunnelVo.getAccessNumber(), dbUserList); break;//移动
					case 3: 
					case 4:  break;//联通
					case 5: 
					case 6:  break;//电信
					case 7:  break;//全网
					case 8:  break;//短信猫
					case 9:  break;//TD话机
					case 10: break;//资信通
					case 11: break;//企信通//
					case 12: break;//铺客
					case 13: break;//移动异网
					case 14: fetchByQxtNew("QXTNEW", merchantPin, dbUserList); break;//新企信通//
					case 15: break;//empp接入方式
					default: break;
				}
			}
		}
		return true;
	}
	
	private void fetchByQxtNew(String type, Long merchantPin, List<PortalUser> dbUserList ){
		for( PortalUser user: dbUserList ){
			List<SmsReceive> smsList = smsReceiveDao.loadDbSyncSmsByUid(user.getId(), type);
			if( smsList == null || smsList.isEmpty()){
				return;
			}
			for(SmsReceive smsMo: smsList){
				SmsInboxDbIntf smsInbox = new SmsInboxDbIntf();
				smsInbox.setMasSmsId(UUID.randomUUID().toString());
				smsInbox.setExtCode(smsMo.getReceiver_access_number());
				smsInbox.setSourceAddr(smsMo.getSender_mobile());
				Date recvTime = DateUtil.parse(smsMo.getCreate_time(),"yyyy-MM-dd HH:mm:ss");
				smsInbox.setReceiveTime(recvTime);
				smsInbox.setMessageContent(smsMo.getContent());
				smsInbox.setMsgFmt(8);// UCS2
				smsInbox.setRequestTime(recvTime);
				smsInbox.setApplicationId("");
				smsInbox.setMerchantPin(merchantPin);
				smsDbIntfService.insertSmsInbox(smsInbox);
				smsReceiveDao.updateDbSyncSmsStatus(smsMo.getId());
			}
		}
	}
	
	private void fetchByAccessNumber( String type, Long merchantPin, String accessNumber, List<PortalUser> dbUserList ){
		for( PortalUser user: dbUserList ){
			List<SmsReceive> smsList = smsReceiveDao.loadDbSyncSmsByAccessNumber(accessNumber + user.getUser_ext_code(), type);
			if( smsList == null || smsList.isEmpty()){
				return;
			}
			for(SmsReceive smsMo: smsList){
				SmsInboxDbIntf smsInbox = new SmsInboxDbIntf();
				smsInbox.setMasSmsId(UUID.randomUUID().toString());
				smsInbox.setExtCode(smsMo.getReceiver_access_number());
				smsInbox.setSourceAddr(smsMo.getSender_mobile());
				Date recvTime = DateUtil.parse(smsMo.getCreate_time(),"yyyy-MM-dd HH:mm:ss");
				smsInbox.setReceiveTime(recvTime);
				smsInbox.setMessageContent(smsMo.getContent());
				smsInbox.setMsgFmt(8);// UCS2
				smsInbox.setRequestTime(recvTime);
				smsInbox.setApplicationId("");
				smsInbox.setMerchantPin(merchantPin);
				smsDbIntfService.insertSmsInbox(smsInbox);
				smsReceiveDao.updateDbSyncSmsStatus(smsMo.getId());
			}
		}
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	private boolean procDataClean(Date date){
		smsDbIntfService.deleteSmsInboxByTime(date);
		smsDbIntfService.deleteSmsSentByTime(date);
		return true;
	}
	
	/**
	 * 获取通道信息
	 * @param merchantPin
	 * @param accessNumber
	 * @param mobile
	 * @return
	 */
	private SmsMbnTunnelVO getTunnelInfo(Long merchantPin, String accessNumber,String mobile){
		SmsMbnTunnelVO svo = null;
		if(StringUtils.isBlank(accessNumber)){
			String mobileType = smsSendDao.loadMobileTypeByH3(mobile.substring(0,3));
			
			if(StringUtils.isNotBlank(mobileType)){
				if(mobileType.equalsIgnoreCase("YD")){
					// 获取移动通道
					svo = BizUtils.getChinaMobileTunnel(mbnMerchantTunnelRelationService, smsMbnTunnelService, merchantPin);
				}
				else{
					Long tdTunnel = BizUtils.getTdTunnelId(mbnMerchantTunnelRelationService, merchantPin);
					if( tdTunnel != null && tdTunnel > 0){
						try {
							svo =  smsMbnTunnelService.queryByPk(tdTunnel);
						} catch (Exception e) {
							logger.error("Fail to get {} tunnel", tdTunnel, e);
						}
					}
				}
			}
			else{
				logger.info("未能找到手机{}的类型",mobile);
			}
		}
		else{
			List<MbnMerchantTunnelRelation> list = mbnMerchantTunnelRelationService.findByAccessNumberAndType(merchantPin, accessNumber, 1);
			if( list != null){
				try {
					svo = smsMbnTunnelService.queryByPk(list.get(0).getTunnelId());
				} catch (Exception e) {
					logger.error("Fail to get {}.", list.get(0).getTunnelId(), e);
				}
			}
		}
		
		return svo;
	}
	

}
