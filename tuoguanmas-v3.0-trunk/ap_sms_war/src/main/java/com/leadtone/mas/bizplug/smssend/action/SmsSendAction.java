package com.leadtone.mas.bizplug.smssend.action;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsDraft;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendContainer;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;
import com.leadtone.mas.bizplug.sms.service.MbnSmsDraftService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsKeywordsService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.smssend.util.ContactsContainer;
import com.leadtone.mas.bizplug.smssend.util.ContactsUtil;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.SmsNumberArithmetic;

@Component("com.leadtone.mas.bizplug.smssend.action.SmsSendAction")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/smssend")
public class SmsSendAction extends BaseAction {
	private static Log LOG = LogFactory.getLog(SmsSendAction.class);
	@Autowired
	private MbnSmsReadySendService mbnSmsReadySendService;
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private MbnSmsKeywordsService mbnSmsKeywordsService;
	@Autowired
	private MbnThreeHCodeService mbnThreeHCodeService;
	@Autowired
	private MbnSevenHCodeService mbnSevenHCodeService;
	@Autowired
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Autowired
	private SmsMbnTunnelService smsMbnTunnelService;
	@Autowired
	private MbnSmsDraftService mbnSmsDraftService;
	@Autowired
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	@Autowired
	private MbnSmsOperationClassService mbnSmsOperationClassService;
	@Autowired
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	/**
	 * 
	 * @return
	 */
	@Action(value = "send", results = {
			@Result(name = SUCCESS, location = "/smssend/jsp/sms_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String send() {
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		
		if( smsText == null || smsText.length() == 0){
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "fail" );
	        entityMap.put("message", "发送内容为空！");
	        this.getRequest().setAttribute("entityMap", entityMap);
			return SUCCESS;
		}
		if( !mbnSmsKeywordsService.checkSms(loginUser.getMerchantPin(), smsText) ){
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "fail" );
	        entityMap.put("message", "内容包含敏感词！");
	        this.getRequest().setAttribute("entityMap", entityMap);
			return SUCCESS;
		}
		// 获取发送列表
		Set<Contacts> userSet = ContactsUtil.getContactsSet(contactsService, getReceiver(), loginUser.getMerchantPin());
		// 号码文件处理
		if (getAddrUpload() != null) {
			String filename = getAddrUploadFileName();
			String storepath = this.getServletContext().getRealPath("/");
			Set<Contacts> fileUserSet = ContactsUtil.getContactsFromFile(getAddrUpload(), filename, storepath);
			if (fileUserSet != null) {
				userSet.addAll(fileUserSet);
			}
		}
		if (userSet.size() == 0) {
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", "无有效接收人！");
	        this.getRequest().setAttribute("entityMap", entityMap);
			return SUCCESS;
		}
		Date readySendTime = new Date();
		String sendType = getSendType();
		if (!"NOW".equalsIgnoreCase(sendType)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				readySendTime = sdf.parse(getSendTime());
			} catch (ParseException e) {
				entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "定时发送时间错误！");
		        this.getRequest().setAttribute("entityMap", entityMap);
				return SUCCESS;
			}
		}
		long batchId = PinGen.getSerialPin();
		// 获取业务优先级
		MbnSmsOperationClass mbnSmsOperationClass 
			= mbnSmsOperationClassService.findByCoding(ApSmsConstants.OPERATION_CODING_HD);
		int priority = mbnSmsOperationClass.getPriorityLevel().intValue();

		// 把用户按运营商分类
		ContactsContainer container = new ContactsContainer();
		for (Contacts info : userSet) {
			MbnThreeHCode hcode = mbnThreeHCodeService.queryByBobilePrefix(StringUtil.getShortPrefix(info.getMobile()));
			if( hcode != null){
				container.addContacts(info, hcode.getCorp());
			}
		}
		
		String[] result = checkSmsNumber(loginUser, container);
		if( Boolean.parseBoolean(result[0])){
			// 检查成功
			if( getReplyCode() != null && getReplyCode().length()>0){
				MbnSmsTaskNumber num = new MbnSmsTaskNumber();
				num.setId(PinGen.getSerialPin());
				num.setMerchantPin(loginUser.getMerchantPin());
				num.setOperationCoding(ApSmsConstants.OPERATION_CODING_HD);
				num.setBatchId(batchId);
				num.setTaskNumber(getReplyCode());
				num.setBeginTime(new Date());
				num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
				num.setState(1);
				mbnSmsTaskNumberService.addTaskNumber(num);
			}
			MbnSmsReadySendContainer smsContainer = buildContainer(loginUser, container, batchId, readySendTime, priority);
			mbnSmsReadySendService.batchSave(smsContainer);
			// 增加发送结果
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "success" );
	        entityMap.put("message", "发送成功！");
	        this.getRequest().setAttribute("entityMap", entityMap);
		}else{
			// 检查失败
			MbnSmsDraft mbnSmsDraft = new MbnSmsDraft();
			mbnSmsDraft.setId(PinGen.getSerialPin());
			mbnSmsDraft.setMerchantPin(loginUser.getMerchantPin());
			mbnSmsDraft.setTitle(title);
			mbnSmsDraft.setContent(smsText);
			mbnSmsDraft.setCreateTime(new Date());
			mbnSmsDraft.setCreateBy(loginUser.getId());
			mbnSmsDraftService.insert(mbnSmsDraft);
			
			StringBuilder builder = new StringBuilder();
			builder.append("发送失败：");
			builder.append(result[1]);
			builder.append(result[2]);
			builder.append(result[3]);
			builder.append(" 信息已保存至草稿箱");
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", builder.toString());
	        this.getRequest().setAttribute("entityMap", entityMap);
		}
		List<MbnMerchantTunnelRelation> relList = mbnMerchantTunnelRelationService.findByPin(loginUser.getMerchantPin());
		List<SmsMbnTunnelVO>  tunnelList = new ArrayList<SmsMbnTunnelVO>();
		for( MbnMerchantTunnelRelation rel : relList){
			SmsMbnTunnelVO tvo = null;
			try {
				tvo = smsMbnTunnelService.queryByPk(rel.getTunnelId());
			} catch (Exception e) {
				// ignore
			}
			if( tvo != null){
				MbnMerchantConsume consume = mbnMerchantConsumeService.findByTunnelId(loginUser.getMerchantPin(), tvo.getId());
				tvo.setSmsNumber(consume.getRemainNumber());
				tunnelList.add(tvo);
			}
		}
		this.getRequest().setAttribute("tunnelList", tunnelList);
		return SUCCESS;
	}
	@Action(value = "edit", results = {
			@Result(name = SUCCESS, location = "/smssend/jsp/sms_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String edit() {
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
        this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(getSmsText()));
		List<MbnMerchantTunnelRelation> relList = mbnMerchantTunnelRelationService.findByPin(loginUser.getMerchantPin());
		List<SmsMbnTunnelVO>  tunnelList = new ArrayList<SmsMbnTunnelVO>();
		for( MbnMerchantTunnelRelation rel : relList){
			SmsMbnTunnelVO tvo = null;
			try {
				tvo = smsMbnTunnelService.queryByPk(rel.getTunnelId());
			} catch (Exception e) {
				// ignore
			}
			if( tvo != null){
				MbnMerchantConsume consume = mbnMerchantConsumeService.findByTunnelId(loginUser.getMerchantPin(), tvo.getId());
				tvo.setSmsNumber(consume.getRemainNumber());
				tunnelList.add(tvo);
			}
		}
		this.getRequest().setAttribute("tunnelList", tunnelList);
		return SUCCESS;
	}
	
	@Action(value = "writesms", results = {
			@Result(name = SUCCESS, location = "/smssend/jsp/sms_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String writeSms() {
        // 获取商户通道列表
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		List<MbnMerchantTunnelRelation> relList = mbnMerchantTunnelRelationService.findByPin(loginUser.getMerchantPin());
		List<SmsMbnTunnelVO>  tunnelList = new ArrayList<SmsMbnTunnelVO>();
		for( MbnMerchantTunnelRelation rel : relList){
			SmsMbnTunnelVO tvo = null;
			try {
				tvo = smsMbnTunnelService.queryByPk(rel.getTunnelId());
			} catch (Exception e) {
				// ignore
			}
			if( tvo != null){
				MbnMerchantConsume consume = mbnMerchantConsumeService.findByTunnelId(loginUser.getMerchantPin(), tvo.getId());
				tvo.setSmsNumber(consume.getRemainNumber());
				tunnelList.add(tvo);
			}
		}
		this.getRequest().setAttribute("tunnelList", tunnelList);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	@Action(value = "queryTaskNumber", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String getTaskNumber() {
		Users loginUser = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		String code = mbnSmsTaskNumberService.getTaskNumber(loginUser.getMerchantPin(), ApSmsConstants.OPERATION_CODING_HD);
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("code", code);
		return "success";
	}
	/**
	 * 检查用户选择通道是否能发送
	 * @param container
	 * @return	String[0] true 可以发送 false 检查失败
	 * 			String[1-3] 分别为移动、联通、电信失败原因
	 */
	private String[] checkSmsNumber(Users loginUser, ContactsContainer container){
		// TODO: 两个以上使用同一全网通道时，容量计算
		String[] result = new String[4];
		boolean finalResult = true;

		MbnMerchantConsume mmc = null;
		SmsMbnTunnelVO svo = null;
		// 检查移动通道
		String ydTip = "";
		if( ydTunnel > 0){
			try {
				mmc = mbnMerchantConsumeService.findByTunnelId(loginUser.getMerchantPin(), ydTunnel);
				svo = smsMbnTunnelService.queryByPk(ydTunnel);
			} catch (Exception e) {
				svo = null;
			}
			if( svo == null){
				finalResult = false;
				ydTip = "移动帐户无可用余额";
			}else{
				if( mmc.getRemainNumber() < container.getYdList().size()){
					finalResult = false;
					ydTip = "移动帐户余额" + mmc.getRemainNumber()+"条 实际"+container.getYdList().size()+"条";
				}
				else{
					// 判断是否省分通道
					if( svo.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_SELF_YD){
						// 判断是否所有 短信来自省分
						if( !checkSmsProvince(container.getYdList(), svo.getProvince())){
							finalResult = false;
							ydTip = "移动通道为分省通道，不支持其它省号码";
						}
					}
				}
			}
		}
		result[1] = ydTip;
		// 检查联通通道
		String ltTip = ""; 
		if( ltTunnel > 0){
			try {
				mmc = mbnMerchantConsumeService.findByTunnelId(loginUser.getMerchantPin(), ltTunnel);
				svo = smsMbnTunnelService.queryByPk(ltTunnel);
			} catch (Exception e) {
				svo = null;
			}
			if( svo == null){
				finalResult = false;
				ltTip = "联通帐户无可用余额";
			}else{
				if( mmc.getRemainNumber() < container.getLtList().size()){
					finalResult = false;
					ltTip = "联通帐户余额" + mmc.getRemainNumber()+"条 实际"+container.getLtList().size()+"条";
				}
				else{
					// 判断是否省分通道
					if( svo.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_SELF_LT){
						// 判断是否所有 短信来自省分
						if( !checkSmsProvince(container.getLtList(), svo.getProvince())){
							finalResult = false;
							ltTip = "联通通道为分省通道，不支持其它省号码";
						}
					}
				}
			}
		}
		result[2] = ltTip;
		// 检查电信通道
		String dxTip = "";
		if( dxTunnel > 0){
			try {
				mmc = mbnMerchantConsumeService.findByTunnelId(loginUser.getMerchantPin(), dxTunnel);
				svo = smsMbnTunnelService.queryByPk(dxTunnel);
			} catch (Exception e) {
				svo = null;
			}
			if( svo == null){
				finalResult = false;
				dxTip = "电信帐户无可用余额";
			}else{
				if( mmc.getRemainNumber() < container.getDxList().size()){
					finalResult = false;
					dxTip = "电信帐户余额" + mmc.getRemainNumber()+"条 实际"+container.getLtList().size()+"条";
				}
				else{
					// 判断是否省分通道
					if( svo.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_SELF_DX){
						// 判断是否所有 短信来自省分
						if( !checkSmsProvince(container.getDxList(), svo.getProvince())){
							finalResult = false;
							dxTip = "电信通道为分省通道，不支持其它省号码";
						}
					}
				}
			}
		}
		result[3] = dxTip;
		result[0] = Boolean.toString(finalResult);
		return result;
	}
	/**
	 * 检查列表中的用户是否与省份代码一致
	 * @param list
	 * @param provinceCode
	 * @return true 一致 false 其它
	 */
	private boolean checkSmsProvince(List<Contacts> list, String provinceCode){
		if(list == null  || list.size() == 0){
			return true;
		}
		for( Contacts contacts: list){
			MbnSevenHCode code = mbnSevenHCodeService.queryByBobilePrefix(StringUtil.getLongPrefix(contacts.getMobile()));
			if( code == null || !provinceCode.equalsIgnoreCase(code.getProvinceCoding())){
				return false;
			}
		}
		return true;
	}

	/**
	 * 构建待发送操作
	 * @param loginUser
	 * @param container
	 * @param batchId
	 * @param readySendTime
	 * @param priority
	 * @return
	 */
	private MbnSmsReadySendContainer buildContainer(Users loginUser, ContactsContainer container, 
			Long batchId, Date readySendTime, Integer priority){
		MbnSmsReadySendContainer smsContainer = new MbnSmsReadySendContainer();
		smsContainer.setMerchantPin(loginUser.getMerchantPin());
		// 构建移动通道短信列表
		if( container.getYdList() != null &&  container.getYdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getYdList(), batchId, readySendTime, priority, ydTunnel);
			smsContainer.addSmsMap(ydTunnel, msrsList);
		}
		// 构建联通通道短信列表
		if( container.getLtList() != null &&  container.getLtList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getLtList(), batchId, readySendTime, priority, ltTunnel);
			smsContainer.addSmsMap(ltTunnel, msrsList);
		}
		// 构建电信通道短信列表
		if( container.getDxList() != null &&  container.getDxList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getDxList(), batchId, readySendTime, priority, dxTunnel);
			smsContainer.addSmsMap(dxTunnel, msrsList);
		}
		
		return smsContainer;
	}
	/**
	 * 构建待发送短信列表
	 * @param loginUser
	 * @param list
	 * @param batchId
	 * @param readySendTime
	 * @param priority
	 * @param tunnelId
	 * @return
	 */
	private List<MbnSmsReadySend> buildSmsList(Users loginUser, List<Contacts> list, 
			Long batchId, Date readySendTime, Integer priority, Long tunnelId){

		SmsMbnTunnelVO svo = null;
		MbnMerchantTunnelRelation mmtr = null;
		try {
			mmtr = mbnMerchantTunnelRelationService.findByPinAndTunnelId(loginUser.getMerchantPin(), tunnelId);
			svo = smsMbnTunnelService.queryByPk(tunnelId);
		} catch (Exception e) {
			LOG.error(e);
		}
		List<MbnSmsReadySend> msrsList = new ArrayList<MbnSmsReadySend>();
		for(Contacts info: list ){
			MbnSmsReadySend msrs = new MbnSmsReadySend();
			msrs.setId(PinGen.getSerialPin());
			msrs.setMerchantPin(loginUser.getMerchantPin());
			msrs.setOperationId(Long.valueOf(ApSmsConstants.SMS_SEND_OP_ID));
			msrs.setBatchId(batchId);
			msrs.setTitle(getTitle());
			msrs.setTos(info.getMobile());
			msrs.setTosName(info.getName());
			msrs.setContent(getSmsText() + (getReplyText()==null?"":getReplyText()) + (getEntSign()==null?"":getEntSign()));
			msrs.setCommitTime(new Date());
			msrs.setReadySendTime(readySendTime);
			msrs.setTunnelType(ApSmsConstants.TUNNEL_TYPE_GW);
			msrs.setPriorityLevel(priority);
			msrs.setSendResult(0);
			msrs.setCreateBy(loginUser.getId());
			msrs.setSmsAccessNumber(mmtr.getAccessNumber());
			msrsList.add(msrs);
		}
		return msrsList;
	}
	
	
	private String title;
	private String receiver;
	private String smsText;
	private String sendType;
	private String sendTime;
	private File addrUpload;
	private String addrUploadFileName;
	private Long ydTunnel;
	private Long ltTunnel;
	private Long dxTunnel;
	private String replyText;
	private String entSign;
	private String replyCode;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public File getAddrUpload() {
		return addrUpload;
	}

	public void setAddrUpload(File addrUpload) {
		this.addrUpload = addrUpload;
	}

	public String getAddrUploadFileName() {
		return addrUploadFileName;
	}

	public void setAddrUploadFileName(String addrUploadFileName) {
		this.addrUploadFileName = addrUploadFileName;
	}
	public Long getYdTunnel() {
		return ydTunnel;
	}
	public void setYdTunnel(Long ydTunnel) {
		this.ydTunnel = ydTunnel;
	}
	public void setYdTunnel(String ydTunnel) {
		if( !StringUtils.isBlank(ydTunnel) && StringUtils.isNumeric(ydTunnel)){
			this.ydTunnel = Long.parseLong(ydTunnel);
		}else{
			this.ydTunnel = 0L;
		}
	}
	public Long getLtTunnel() {
		return ltTunnel;
	}
	public void setLtTunnel(Long ltTunnel) {
		this.ltTunnel = ltTunnel;
	}
	public void setLtTunnel(String ltTunnel) {
		if( !StringUtils.isBlank(ltTunnel) && StringUtils.isNumeric(ltTunnel)){
			this.ltTunnel = Long.parseLong(ltTunnel);
		}else{
			this.ltTunnel = 0L;
		}
	}
	public Long getDxTunnel() {
		return dxTunnel;
	}
	public void setDxTunnel(Long dxTunnel) {
		this.dxTunnel = dxTunnel;
	}
	public void setDxTunnel(String dxTunnel) {
		if( !StringUtils.isBlank(dxTunnel) && StringUtils.isNumeric(dxTunnel)){
			this.dxTunnel = Long.parseLong(dxTunnel);
		}else{
			this.dxTunnel = 0L;
		}
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public String getEntSign() {
		return entSign;
	}
	public void setEntSign(String entSign) {
		this.entSign = entSign;
	}
	public String getReplyCode() {
		return replyCode;
	}
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}

}
