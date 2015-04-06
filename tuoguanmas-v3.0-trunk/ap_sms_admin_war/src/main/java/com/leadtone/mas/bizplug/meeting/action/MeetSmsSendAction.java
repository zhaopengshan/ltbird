package com.leadtone.mas.bizplug.meeting.action;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.config.bean.MbnConfigSysDictionary;
import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.service.MbnConfigSysDictionaryService;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
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
import com.leadtone.mas.bizplug.util.BizUtils;
import com.leadtone.mas.bizplug.util.SmsNumberArithmetic;
import com.leadtone.mas.bizplug.util.WebUtils;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/meeting")
public class MeetSmsSendAction extends BaseAction {
	private static Log LOG = LogFactory.getLog(MeetSmsSendAction.class);
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
	@Autowired
	private MbnConfigSysDictionaryService mbnConfigSysDictionaryService;
	@Autowired
	private PortalUserExtService portalUserExtService;
	/**
	 * 
	 * @return
	 */
	@Action(value = "send", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String send() {
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		
		if( smsText == null || smsText.length() == 0){
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "fail" );
	        entityMap.put("message", "发送内容为空！");
	        List<SmsMbnTunnelVO>  tunnelList = getTunnelList(loginUser.getMerchantPin());
	        this.getRequest().setAttribute("tunnelList", tunnelList);
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
		Set<Contacts> userSet = new HashSet<Contacts>();
		List<String> colsList = new ArrayList<String>();
		Long createBy = loginUser.getId();
		if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
				|| loginUser.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
			createBy = null;
		}
		if(null == flag){
			// 从短信发送页面过来，     获取发送列表
			userSet = ContactsUtil.getContactsSet(contactsService, getReceiver(), loginUser.getMerchantPin(), createBy);
		}
		// 号码文件处理
		if (getAddrUpload() != null) {
			String filename = getAddrUploadFileName();
			String storepath = this.getServletContext().getRealPath("/");
			String[][] fileContent = ContactsUtil.getContactsArrayFromFile(getAddrUpload(), filename, storepath);
			int nameIndex=-1;
			if( fileContent != null){
				for(int i=0; i< fileContent.length; i++){
					if(i == 0 ){
						// 取列头
						for(int k=0; k< fileContent[0].length; k++){
							//if(null == fileContent[0][k])
							//	continue;
							colsList.add(fileContent[0][k]);
							if( -1 != fileContent[0][k].indexOf("姓名")){
								// 找客户姓名列
								nameIndex=k;
							}
						}
						continue;
					}
					//System.out.println("姓名列： "+nameIndex);
					Contacts c = new Contacts();
					// 从第二行遍历数据，因为第一行是列头
					for(int j=0; j<fileContent[i].length; j++){
						c.setMobile(fileContent[i][0]);
						if(-1 != nameIndex){
							c.setName(fileContent[i][nameIndex]);	// 客户姓名
						}
						c.put(fileContent[0][j], fileContent[i][j]);
						userSet.add(c);
					}
				}
			}
		}
		if (userSet.size() == 0) {
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", "无有效接收人！");
	        this.getRequest().setAttribute("entityMap", entityMap);
			return SUCCESS;
		}
		
		//get count		
		PortalUserExtBean userExt = portalUserExtService.getByPk(loginUser.getId());
		if(userExt!=null &&userExt.getSmsLimit()==1){
			int limit = userExt.getSmsLimitCount();//
			int curr = userExt.getSmsSendCount();//
			//如果限制，且量已经达到上限。则不发。
			if(curr+userSet.size()>limit){
				entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "发送短信量限制！");
		        this.getRequest().setAttribute("entityMap", entityMap);
		        List<SmsMbnTunnelVO>  tunnelList = getTunnelList(loginUser.getMerchantPin());
		        this.getRequest().setAttribute("tunnelList", tunnelList);
		        logger.info("User sms priority :" + curr+" + " + userSet.size()+">" + limit);
				return SUCCESS;
			} else {
				userExt.setSmsSendCount(curr+userSet.size());
				userExt.setCountTime(new Date());
				portalUserExtService.update(userExt);
			}
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
			= mbnSmsOperationClassService.findByCoding(ApSmsConstants.OPERATION_CODING_HY);
		int priority = mbnSmsOperationClass.getPriorityLevel().intValue();
		if("true".endsWith(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.SMS_PRIORITY_FROM_USER))){
			try{
				priority = userExt.getSmsPriority();//get priory from userExt
			} catch(Exception e){
				logger.info("User sms priority :" + e.getMessage());
			}
		}
		
		// 获取默认猫池通道ID
		//设定是否为托管mas处理方式
        if(WebUtils.isHostMas()) {
            // 获取第三方通道ID
        	tdTunnel = BizUtils.getTdTunnelId(mbnMerchantTunnelRelationService,loginUser.getMerchantPin());
            if( tdTunnel == null || tdTunnel <= 0){
                entityMap = new HashMap<String, Object>();
                entityMap.put("resultcode", "error" );
                entityMap.put("message", "短信通道未配置，请联系管理员！");
                this.getRequest().setAttribute("entityMap", entityMap);
                List<SmsMbnTunnelVO>  tunnelList = getTunnelList(loginUser.getMerchantPin());
                this.getRequest().setAttribute("tunnelList", tunnelList);
                return SUCCESS;
            }
        }

		// 把用户按运营商分类
		ContactsContainer container = new ContactsContainer();
		for (Contacts info : userSet) {
			MbnThreeHCode hcode = mbnThreeHCodeService.queryByBobilePrefix(StringUtil.getShortPrefix(info.getMobile()));
			if( hcode != null){
				container.addContacts(info, hcode.getCorp());
			}
		}
		
		//String[] result = checkSmsNumber(loginUser, container);
		String[] result = rebuildContainerForHostMas(loginUser, container);
		if( Boolean.parseBoolean(result[0])){
			// 检查成功
			String taskNumber = "";
        	if( WebUtils.getExtCodeStyle() == com.leadtone.mas.admin.common.ApSmsConstants.OPERATION_EXT_CODE_TYPE){
        		// 根据业务类型生成扩展码
                if( getReplyCode() != null && getReplyCode().length()>0){
                    MbnSmsTaskNumber num = new MbnSmsTaskNumber();
                    num.setId(PinGen.getSerialPin());
                    num.setMerchantPin(loginUser.getMerchantPin());
                    num.setOperationCoding(ApSmsConstants.OPERATION_CODING_HY);
                    num.setBatchId(batchId);
                    num.setTaskNumber(getReplyCode());
                    num.setBeginTime(new Date());
                    num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
                    num.setState(1);
                    taskNumber = num.getTaskNumber();
                    mbnSmsTaskNumberService.addTaskNumber(num);
                }
        	}else{
        		// 根据用户来生成扩展码
                taskNumber = mbnSmsTaskNumberService.getTaskNumber2(loginUser.getMerchantPin(), loginUser.getUserExtCode());
				// 记录扩展码
				MbnSmsTaskNumber num = new MbnSmsTaskNumber();
				num.setId(PinGen.getSerialPin());
				num.setMerchantPin(loginUser.getMerchantPin());
				num.setOperationCoding(loginUser.getUserExtCode()); //使用用户的两位扩展
				num.setBatchId(batchId);
				num.setTaskNumber(taskNumber);
				num.setBeginTime(new Date());
				num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
				num.setState(1);
				taskNumber = num.getTaskNumber();
				mbnSmsTaskNumberService.addTaskNumber(num);
        	}
			
			MbnSmsReadySendContainer smsContainer = buildContainerForHostMas(loginUser, container, batchId, readySendTime, priority, colsList, taskNumber);
			mbnSmsReadySendService.batchSave(smsContainer);
			// 增加发送结果
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "success" );
	        entityMap.put("message", "提交成功！");
	        this.getRequest().setAttribute("entityMap", entityMap);
		}else{
			// 检查失败
			/*MbnSmsDraft mbnSmsDraft = new MbnSmsDraft();
			mbnSmsDraft.setId(PinGen.getSerialPin());
			mbnSmsDraft.setMerchantPin(loginUser.getMerchantPin());
			mbnSmsDraft.setTitle(title);
			mbnSmsDraft.setContent(smsText);
			mbnSmsDraft.setCreateTime(new Date());
			mbnSmsDraft.setCreateBy(loginUser.getId());
			mbnSmsDraftService.insert(mbnSmsDraft);*/
			
			StringBuilder builder = new StringBuilder();
			builder.append("提交失败：");
			builder.append(result[1]);
			builder.append(result[2]);
			builder.append(result[3]);
			//builder.append(" 信息已保存至草稿箱");
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", builder.toString());
	        this.getRequest().setAttribute("entityMap", entityMap);
		}
		/*if(null != flag && "dynamic".equals(flag)){
			// 如果为动态发送短信，则不用再查以下的通道信息了，直接返回
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "success" );
	        entityMap.put("message", "发送成功");
			return SUCCESS;
		}*/
		List<SmsMbnTunnelVO>  tunnelList = getTunnelList(loginUser.getMerchantPin());
		this.getRequest().setAttribute("tunnelList", tunnelList);
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("message", "提交成功");
		return SUCCESS;
	}
	@Action(value = "edit", results = {
			@Result(name = SUCCESS, location = "/sms/meeting/jsp/sms_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String edit() {
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
        this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(getSmsText()));
		List<SmsMbnTunnelVO>  tunnelList = getTunnelList(loginUser.getMerchantPin());
		this.getRequest().setAttribute("tunnelList", tunnelList);
		return SUCCESS;
	}
	
	@Action(value = "writesms", results = {
			@Result(name = SUCCESS, location = "/sms/meeting/jsp/sms_write.jsp"),
			@Result(name = "dynamic", location = "/sms/meeting/jsp/dynamic_sms_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String writeSms() {
        // 获取商户通道列表
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		List<SmsMbnTunnelVO>  tunnelList = getTunnelList(loginUser.getMerchantPin());
		this.getRequest().setAttribute("tunnelList", tunnelList);
		if(null != flag && "dynamic".equals(flag)){
			return "dynamic";
		}
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * @param merchantPin
	 * @return
	 */
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
				MbnMerchantConsume consume = mbnMerchantConsumeService.findByTunnelId(merchantPin, tvo.getId());
				if( consume!=null){
					tvo.setSmsNumber(consume.getRemainNumber());
				}else{
					tvo.setSmsNumber(0L);
				}
				tunnelList.add(tvo);
			}
		}
		return tunnelList;
	}
	
	
	/**
	 * 
	 * @return
	 */
	@Action(value = "queryTaskNumber", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String getTaskNumber() {
		Users loginUser = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		String code = mbnSmsTaskNumberService.getTaskNumber(loginUser.getMerchantPin(), ApSmsConstants.OPERATION_CODING_HY);
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("code", code);
		return "success";
	}
	/**
	 * 拖管MAS 重建发送集，非本省、联通、电信移到td通道
	 * @param loginUser
	 * @param container
	 * @return
	 */
	private String[] rebuildContainerForHostMas(Users loginUser, ContactsContainer container){
		String[] result = new String[]{Boolean.TRUE.toString(), "OK","OK","OK"};
		String provinceCode = "";
		SmsMbnTunnelVO svo = null;
		try {
			svo = smsMbnTunnelService.queryByPk(ydTunnel);
			provinceCode = svo.getProvince();
		} catch (Exception e) {
			logger.error("Find " + ydTunnel + " FAIL", e);
		}
		
		if( container.getYdList().size() > 0){
			for( Contacts contacts: container.getYdList()){
				MbnSevenHCode code = mbnSevenHCodeService.queryByBobilePrefix(StringUtil.getLongPrefix(contacts.getMobile()));
				if( code == null || !provinceCode.equalsIgnoreCase(code.getProvinceCoding())){
					if(svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_YD 
							||svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_GLOBAL){
					//	container.addYdContacts(contacts);
					} else {
						container.addTdContacts(contacts);
					}
				}
			}
		}
		// 将猫池发送从YD列表中删除
		for( Contacts contacts: container.getTdList()){
			container.removeContacts(contacts, ApSmsConstants.YD_CODE);
		}
		// 判断企业是否有联通通道，如有配置，使用联通通道，否则转移动TD
		// 原注释：联通移至TD列表
		Long ltTunnelId = BizUtils.getLtTunnelId(mbnMerchantTunnelRelationService,
				loginUser.getMerchantPin());
		if( ltTunnelId <= 0){
			if( container.getLtList().size() > 0){
				for( Contacts contacts: container.getLtList()){
					if(svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_GLOBAL){
						container.addYdContacts(contacts);
					} else{
						container.addTdContacts(contacts);
					}
				}
			}
			container.setLtList(new ArrayList<Contacts>());
		}
		// 判断企业是否有联通通道，如有配置，使用联通通道，否则转移动TD
		// 原注释：电信移至TD列表
		Long dxTunnelId = BizUtils.getLtTunnelId(mbnMerchantTunnelRelationService,
				loginUser.getMerchantPin());
		if( dxTunnelId <= 0){
			if( container.getDxList().size() > 0){
				for( Contacts contacts: container.getDxList()){
					if(svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_GLOBAL){
						container.addYdContacts(contacts);
					} else{
						container.addTdContacts(contacts);
					}
				}
			}
			container.setDxList(new ArrayList<Contacts>());
		}
		return result;
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
		boolean ydResult = true;
		boolean ltResult = true;
		boolean dxResult = true;

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
		}else{
			ydResult = false;
			ydTip = "无可用移动通道";
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
		}else{
			ltResult = false;
			ltTip = "无可用联通通道";
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
		}else{
			dxResult = false;
			dxTip = "无可用电信通道";
		}
		result[3] = dxTip;
		result[0] = Boolean.toString(finalResult&&(dxResult||ltResult||ydResult));
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
	private MbnSmsReadySendContainer buildContainerForHostMas(Users loginUser, ContactsContainer container, 
			Long batchId, Date readySendTime, Integer priority, List<String> colsList, String taskNumber ){
		MbnSmsReadySendContainer smsContainer = new MbnSmsReadySendContainer();
		smsContainer.setMerchantPin(loginUser.getMerchantPin());
		// 构建移动通道短信列表
		if( container.getYdList() != null &&  container.getYdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getYdList(), batchId, readySendTime, priority, ydTunnel, colsList, taskNumber);
			smsContainer.addSmsMap(ydTunnel, msrsList);
		}
		// 构建联通通道短信列表
		Long ltTunnelId = BizUtils.getLtTunnelId(mbnMerchantTunnelRelationService, loginUser.getMerchantPin());
		if (ltTunnelId <= 0) {
			ltTunnelId = tdTunnel;
		}
		if( container.getLtList() != null &&  container.getLtList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getLtList(), batchId, readySendTime, priority, ltTunnelId, colsList, taskNumber);
			smsContainer.addSmsMap(ltTunnelId, msrsList);
		}
		// 构建电信通道短信列表
		Long dxTunnelId = BizUtils.getDxTunnelId(mbnMerchantTunnelRelationService, loginUser.getMerchantPin());
		if (dxTunnelId <= 0) {
			dxTunnelId = tdTunnel;
		}
		if( container.getDxList() != null &&  container.getDxList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getDxList(), batchId, readySendTime, priority, dxTunnelId, colsList, taskNumber);
			smsContainer.addSmsMap(dxTunnelId, msrsList);
		}
		// 构建TD通道短信列表
		if( container.getTdList() != null &&  container.getTdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getTdList(), batchId, readySendTime, priority, tdTunnel, colsList, taskNumber);
			smsContainer.addSmsMap(tdTunnel, msrsList);
		}
		
		return smsContainer;
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
			Long batchId, Date readySendTime, Integer priority, List<String> colsList, String taskNumber ){
		MbnSmsReadySendContainer smsContainer = new MbnSmsReadySendContainer();
		smsContainer.setMerchantPin(loginUser.getMerchantPin());
		// 构建移动通道短信列表
		if( container.getYdList() != null &&  container.getYdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getYdList(), batchId, readySendTime, priority, ydTunnel, colsList, taskNumber);
			smsContainer.addSmsMap(ydTunnel, msrsList);
		}
		// 构建联通通道短信列表
		if( container.getLtList() != null &&  container.getLtList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getLtList(), batchId, readySendTime, priority, ltTunnel, colsList, taskNumber);
			smsContainer.addSmsMap(ltTunnel, msrsList);
		}
		// 构建电信通道短信列表
		if( container.getDxList() != null &&  container.getDxList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getDxList(), batchId, readySendTime, priority, dxTunnel, colsList, taskNumber);
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
			Long batchId, Date readySendTime, Integer priority, Long tunnelId, List<String> colNameList, String taskNumber){

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
			
			//PAN-Z-G 修改webservice信息
			if(loginUser.getWebService()!=com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_NO)
				msrs.setWebService(com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_YES);
			else {
				msrs.setWebService(com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_NO);
			}
			
			msrs.setId(PinGen.getSerialPin());
			msrs.setMerchantPin(loginUser.getMerchantPin());
			//bean改好解开注释Integer=》long
			msrs.setOperationId(mbnSmsOperationClassService.findByCoding(ApSmsConstants.OPERATION_CODING_HY).getId());
			msrs.setBatchId(batchId);
			msrs.setTitle(getTitle());
			msrs.setProvince(super.getSession().getAttribute("prov_code").toString());
			msrs.setTos(info.getMobile());
			msrs.setTosName(info.getName());
			msrs.setTaskNumber(taskNumber);
			setSmsText(smsText.replaceAll("\r", ""));
			if(null != flag && "dynamic".equals(flag)){
				// 如果是发送动态短信，则替换短信内容里的变量
				String _smsText = smsText;
				for(String colName : colNameList){
					try{
						_smsText = _smsText.replace("%"+ colName+ "%", info.get(colName));
					}catch (Exception e) {
						System.out.println("没有["+ colName+"]列");
						continue;
					}
				}
				msrs.setContent(_smsText + (getReplyText()==null?"":getReplyText()) + (getEntSign()==null?"":getEntSign()));
			} else{
				msrs.setContent(getSmsText() + (getReplyText()==null?"":getReplyText()) + (getEntSign()==null?"":getEntSign()));
			}
			msrs.setCommitTime(new Date());
			msrs.setReadySendTime(readySendTime);
            msrs.setTunnelType(svo.getClassify());
		
			// 20130112 转换通道类型
            // 设置通道类型
            int tunnelType = svo.getClassify();
            
            if( tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_ZXT 
            		|| tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_TD 
            		|| tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_MODEM){
            	// 资信通, TD, MODEM
            	msrs.setSmsAccessNumber(loginUser.getZxtUserId());
            }
            else if( tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_QXT){
            	// 企信通
            	msrs.setSmsAccessNumber(String.valueOf(loginUser.getZxtId()));
            }
            else{
            	//20130904 还需要增加 taskNumber
            	String accessNumber = BizUtils.buildAccessNumber(mmtr.getAccessNumber(), 
            			loginUser.getUserExtCode(), msrs.getTaskNumber());
            	msrs.setSmsAccessNumber(accessNumber);
            }
			msrs.setPriorityLevel(priority);
			msrs.setSendResult(0);
			msrs.setCreateBy(loginUser.getId());
			msrs.setSelfMobile(mmtr.getAccessNumber());
			msrs.setCutApartNumber(1000);
			msrsList.add(msrs);
		}
		
		return msrsList;
	}
	/**
	 * 获取全短信发送失败的失败原因
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "searchFailReason", results = {
			@Result(type = "json", params = {"root", "list","contentType","text/html"})})
	public String searchFailReason() {
		PageUtil pu=new PageUtil();
		pu.setType("10");
		Page p=this.mbnConfigSysDictionaryService.page(pu);
		list=(List<MbnConfigSysDictionary>) p.getData();
		return SUCCESS;
	}


	private String title;
	private String receiver;
	private String smsText;
	private String sendType;
	private String sendTime;
	private File addrUpload;
	private String addrUploadFileName;
	private Long ydTunnel=0L;
	private Long ltTunnel=0L;
	private Long dxTunnel=0L;
	private Long tdTunnel=0L;
	private String replyText;
	private String entSign;
	private String replyCode;
	private List list;

	private String flag; 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReceiver() {
		return receiver;
	}

	public Long getTdTunnel() {
		return tdTunnel;
	}
	public void setTdTunnel(Long tdTunnel) {
		this.tdTunnel = tdTunnel;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}

}
