package com.leadtone.mas.bizplug.lottery.action;

import static com.leadtone.mas.bizplug.common.MasCommonFunction.responseMapByMsg;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.leadtone.mas.bizplug.addr.bean.Contact;
import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.MasCommonFunction;
import com.leadtone.mas.bizplug.common.MasSmsBean;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsAward;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsLotteryUpshot;
import com.leadtone.mas.bizplug.lottery.service.AwardService;
import com.leadtone.mas.bizplug.lottery.service.LotteryService;
import com.leadtone.mas.bizplug.lottery.service.LotteryUpshotService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.smssend.util.ContactsUtil;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.WebUtils;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaodiaocha;
import com.leadtone.mas.bizplug.common.bean.MasCommonBean;
import com.leadtone.mas.bizplug.common.service.MasCommonService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.dao.MbnMerchantVipIDao;
import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.dao.IRegionDAO;

@SuppressWarnings("serial")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/lottery") 
public class LotteryAction extends BaseAction {
	
	private MasSmsLottery form;
	
	@Resource
	private LotteryService lotteryService;
	
	@Resource
	private AwardService awardService;
	
	@Resource
	private LotteryUpshotService lotteryUpshotService;
	
	@Resource
    private MbnMerchantVipIDao mbnMerchantVipIDao;
	 @Resource(name="regionDAOImpl")
	private IRegionDAO regionDAO;
	
	@Autowired
    private ContactsService contactsService;
		
	@Autowired
	private MasCommonService masCommonServiceImp;

	@Autowired
	private PortalUserExtService portalUserExtService;
	
	@Action(value = "writeSms", results = {
			@Result(name = SUCCESS, location = "/sms/lottery/jsp/lottery_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String writeSms() {
		MasCommonFunction masCommonFunction=null;
		MasSmsBean masSmsBean=new MasSmsBean();
		initMasSmsBean(masSmsBean,loginUser,null);
		masCommonFunction=new MasCommonFunction(masSmsBean);
		
		List<SmsMbnTunnelVO>  tunnelList = masCommonFunction.getTunnelList(loginUser.getMerchantPin());
		this.getRequest().setAttribute("tunnelList", tunnelList);
		if(null != flag && "dynamic".equals(flag)){
			return "dynamic";
		}
		return SUCCESS;
	}
	
	@Action(value = "editLottery", results = {
			@Result(name = SUCCESS, location = "/sms/lottery/jsp/lottery_write.jsp"),
			@Result(name = ERROR, location = "/login.jsp")})
	public String editLottery() {
		try{
			if(!StringUtil.isEmpty(selectedId)){
				MasSmsLottery  lottery=lotteryService.queryLotteryById(selectedId);
				this.getRequest().setAttribute("lotteryText", StringEscapeUtils.escapeJavaScript(lottery.getContent()));
				this.getRequest().setAttribute("lotteryTitle", StringEscapeUtils.escapeJavaScript(lottery.getTitle()));
				MasCommonFunction masCommonFunction=null;
				MasSmsBean masSmsBean=new MasSmsBean();
				initMasSmsBean(masSmsBean,loginUser,null);
				masCommonFunction=new MasCommonFunction(masSmsBean);
				List<SmsMbnTunnelVO>  tunnelList = masCommonFunction.getTunnelList(loginUser.getMerchantPin());
				this.getRequest().setAttribute("tunnelList", tunnelList);
			}
		}catch(Exception e){
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	@Action(value = "lotterySms", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String addlottery() {
		SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			form.setBeginTime(smt.parse(startTime));
			form.setEndTime(smt.parse(endTime));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		
		Set<Contacts> userSet =  new HashSet<Contacts>();
		List<String> colsList =  new ArrayList<String>();
		this.setTitle(form.getTitle());
		this.setSmsText(form.getContent());
		if(form.getTos() !=""){
			Long createBy = loginUser.getId();
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
					|| loginUser.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				createBy = null;
			}
			userSet = ContactsUtil.getContactsSet(contactsService, form.getTos(), loginUser.getMerchantPin(),createBy);
			if(addrUpload!=null){
				String filename = addrUpload.getName();
				String storepath = this.getServletContext().getRealPath("/");
				String[][] fileContent = ContactsUtil.getContactsArrayFromFile(getAddrUpload(), filename, storepath);
				int nameIndex=-1;
				if( fileContent != null){
					for(int i=0; i< fileContent.length; i++){
						if(i == 0 ){
							// 取列头
							for(int k=0; k< fileContent[0].length; k++){
								colsList.add(fileContent[0][k]);
								if( -1 != fileContent[0][k].indexOf("姓名")){
									nameIndex=k;
								}
							}
							continue;
						}
						Contacts c = new Contacts();
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
		}
		
		entityMap = new HashMap<String, Object>();
		
		long id=0L;
		try{
			id=PinGen.getSerialPin();
			
			form.setId(id);
			form.setCommitTime(new Date());
			form.setCreateBy(loginUser.getId());
			String tmpTos="";
			for(Object obj:userSet){
				Contacts contact=(Contacts)obj;
				tmpTos+=(contact.getName()==null?contact.getMobile():contact.getName())+"<"+contact.getMobile()+">,";
			}
			form.setTos(tmpTos);
			lotteryService.addLottery(form);//添加
			int count=awardNum==null?0:Integer.parseInt(awardNum);  
			for(int i=1;i<=count;i++){
				MasSmsAward award=new MasSmsAward();
				String AwardContent="".equals(getDecode("awardContent"+i))?"":getDecode("awardContent"+i);
				if("".equals(AwardContent)){
					continue;
				}
				award.setAwardContent("".equals(getDecode("awardContent"+i))?"":getDecode("awardContent"+i));
				award.setQuotaOfPeople("".equals(getDecode("quotaOfPeople"+i))?0:Integer.parseInt(getDecode("quotaOfPeople"+i)));
				award.setGradeLevelName("".equals(getDecode("gradeLevelName"+i))?"":getDecode("gradeLevelName"+i));
				award.setCreateTime(new Date());
				award.setId(PinGen.getSerialPin());
				award.setDxcjId(id);
				award.setOrderNumber(0);//排序字段未用到
				award.setCreateBy(loginUser.getId());
				awardService.addLottery(award);
			}

			MasCommonFunction masCommonFunction=null;
			MasSmsBean masSmsBean=new MasSmsBean();
			tp_batchId=id;
			masSmsBean.setReplyCode(form.getReplyCode());
			initMasSmsBean(masSmsBean,loginUser,userSet);
			masCommonFunction=new MasCommonFunction(masSmsBean);
			

			entityMap = masCommonFunction.makeMbnSmsSendTask(loginUser,form.getBeginTime(),colsList);
			this.getRequest().setAttribute("entityMap", entityMap);
			
		}catch (Exception e) {
			LOG.error(e.getMessage());
			entityMap.put("resultcode", "failure" );
	        entityMap.put("message", "添加失败");
			return SUCCESS;
		}
		
		
		//entityMap.put("resultcode", "success" );
        //entityMap.put("message", "添加成功");
		return SUCCESS;

	}
	
	private void initMasSmsBean(MasSmsBean masSmsBean,Users loginUser,Set<Contacts> userSet){
		
		masSmsBean.setTitle(title);
		masSmsBean.setSmsText(smsText);
		masSmsBean.setYdTunnel(ydTunnel);
		masSmsBean.setTdTunnel(tdTunnel);
		masSmsBean.setEntSign(entSign);
		masSmsBean.setReplyText(replyText);
		masSmsBean.setBatchId(tp_batchId);
		masSmsBean.setFlag(flag);
		//masSmsBean.setReplyCode(replyCode);
		masSmsBean.setOperationType(ApSmsConstants.OPERATION_CODING_TYPE_CJ);
		masSmsBean.setCodeType(ApSmsConstants.OPERATION_CODING_CJ);//抽奖
		masSmsBean.setMerchantPin(loginUser.getMerchantPin());
		masSmsBean.setUserSet(userSet);
		masSmsBean.setProv_code(super.getSession().getAttribute("prov_code").toString());
		masSmsBean.setMbnSmsReadySendService(mbnSmsReadySendService);
		masSmsBean.setMbnThreeHCodeService(mbnThreeHCodeService);
		masSmsBean.setMbnSevenHCodeService(mbnSevenHCodeService);
		masSmsBean.setMbnMerchantTunnelRelationService(mbnMerchantTunnelRelationService);
		masSmsBean.setSmsMbnTunnelService(smsMbnTunnelService);
		masSmsBean.setMbnMerchantConsumeService(mbnMerchantConsumeService);
		masSmsBean.setMbnSmsOperationClassService(mbnSmsOperationClassService);
		masSmsBean.setMbnSmsTaskNumberService(mbnSmsTaskNumberService);
		masSmsBean.setPortalUserExtService(portalUserExtService);
	}
	
	@Action(value = "pageDatiLotteryList", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String pageDatiLotteryList(){
		log.debug("pageDatiLotteryList action run");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("startPage", (page - 1) * 20);
        paraMap.put("pageSize", rows);
        paraMap.put("id", loginUser.getId());
        
        entityMap = new HashMap<String, Object>();
        String type=getDecode("sendType");
        int sendType= "".equals(type)? lotteryNoSend:Integer.parseInt(type);
        List<MasSmsLottery> masSmsLotteryList = lotteryService.queryLotteryById(paraMap,sendType);
        paraMap.put("startPage", null);
        paraMap.put("pageSize", null);
        int totalrecords=lotteryService.queryLotteryById(paraMap,sendType).size();
        entityMap.put("rows", masSmsLotteryList);
        entityMap.put("totalrecords", totalrecords);
        entityMap.put("currpage", page);
        return SUCCESS;
	}
	
	@Action(value = "deleteLottery", results = {
			@Result(type = "json", params = {
					"root", "entityMap", "contentType", "text/html"})})
	public String deleteLottery(){
		log.debug("deleteLottery action run");
		try{
			lotteryService.deleteLottery(smsIds);
			entityMap =responseMapByMsg("success","删除成功");
		}catch (Exception e) {
			log.error(e.getMessage());
			entityMap =responseMapByMsg("删除失败");
		}
		return SUCCESS;
	}
	
	
	@Action(value = "awardList", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String AwardList(){
		log.debug("awardListList action run");
		entityMap=new HashMap<String, Object>();
		String id=getDecode("lotteryId");
		MasSmsLottery lottery=lotteryService.queryLotteryById(id);
		if(lottery!=null){
			if(2==lottery.getState()){
				entityMap.put("message", "已经进行过摇奖");
				entityMap.put("content", null);
			}else{
		        List<MasSmsAward> masSmsAwardList = awardService.awardList(id);
		        entityMap.put("content", masSmsAwardList);
		        entityMap.put("message", "");
			}
		}
        return SUCCESS;
	}
	
	@Action(value = "addLotteryUpshot", results = {
			@Result(type = "json", params = {
					"root", "entityMap", "contentType", "text/html"})})
	public String addLotteryUpshot(){
		log.debug("addLotteryUpshot action run");
		entityMap = new HashMap<String, Object>();
		MasSmsLotteryUpshot lotteryUpshot=new MasSmsLotteryUpshot();
		lotteryUpshot.setId(PinGen.getSerialPin());
		lotteryUpshot.setAwardContent(getDecode("awardContent"));
		lotteryUpshot.setCreateBy(loginUser.getId());
		lotteryUpshot.setCreateTime(new Date());
		lotteryUpshot.setDxcjId(new Long(getDecode("dxcjId")));
		lotteryUpshot.setGradeLevelName(getDecode("gradeLevelName"));//
		lotteryUpshot.setMobile(getDecode("mobile"));
		lotteryUpshot.setName("");
		lotteryUpshot.setState(0);
		try{
			lotteryUpshotService.addLotteryUpshot(lotteryUpshot);
			//标记为已抽奖
			Map param=new HashMap<String, Object>();
			param.put("id", getDecode("dxcjId"));
			param.put("state", "2");
			lotteryService.updateLottery(param);
			String SmsText=("恭喜手机号码为："+getDecode("mobile")+"获得"+getDecode("awardContent"));
			List<String> colsList = new ArrayList();
			colsList.add(getDecode("mobile"));
//			Long baseAccessNumber = mbnSequenceService.getNextVal();
//			String extCode = ExtCodeUtil.getExtCode(baseAccessNumber, Integer.parseInt(SubsystemConfig.getInstances().getProperty("corpExtLen")));
			String extCode = mbnSmsTaskNumberService.getTaskNumber(loginUser.getMerchantPin(), ApSmsConstants.OPERATION_CODING_CJ);
			replyLotterySMS(PinGen.getSerialPin(),colsList,loginUser.getCreateBy(),loginUser.getMerchantPin(),getDecode("mobile"),SmsText,extCode);

			entityMap =responseMapByMsg("success","恭喜中奖");
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			entityMap =responseMapByMsg("操作失败");
		}
		return SUCCESS;
	}
	
	
	
	private void replyLotterySMS(Long batchID,List<String> colsList,Long create_by,Long merchantPin,String receiver,String smsText,String taskNumber){
		try{
			MasCommonBean masCommonBean=new MasCommonBean();
			masCommonBean.setBatchId(batchID);
			masCommonBean.setCodeType("CJ");
			masCommonBean.setColsList(colsList);
			masCommonBean.setDxTunnel(0L);
			masCommonBean.setEntSign("");
			masCommonBean.setFlag(null);
			masCommonBean.setLtTunnel(0L);
			masCommonBean.setLoginUser(loginUser);
			masCommonBean.setLtTunnel(0L);
			masCommonBean.setMerchantPin(merchantPin);
			masCommonBean.setOperationType(5);
			
			MbnMerchantVip merchant = mbnMerchantVipIDao.load(merchantPin);
			String prov = merchant.getProvince();
			Region region = regionDAO.queryByProvinceId(Long.parseLong(prov));
			String provCode = region.getCode();
			masCommonBean.setProv_code(provCode);
			
			masCommonBean.setReadySendTime(new Date());
			masCommonBean.setReceiver(receiver);
			masCommonBean.setReplyCode(taskNumber);
			masCommonBean.setReplyText("");
			masCommonBean.setSmsText(smsText);
			masCommonBean.setTdTunnel(0L);
			masCommonBean.setTitle("");
			Set<Contacts> userSet=getContactsSet(contactsService, receiver, loginUser.getMerchantPin(),create_by);
			masCommonBean.setUserSet(userSet);
			masCommonBean.setYdTunnel(0L);
			masCommonServiceImp.insertMbnSmsSendTask(masCommonBean);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static Set<Contacts> getContactsSet(ContactsService contactsService,
			String info, Long merchantPin, Long createBy) {
		Set<Contacts> set = new HashSet<Contacts>();
		if (info != null && info.trim().length() > 0) {
			StringTokenizer st = new StringTokenizer(info, ",");
			while (st.hasMoreTokens()) {
				String mobile = null;
				String name = null;
				String uInfo = st.nextToken();
				uInfo = uInfo.trim();
				// 判断用户还是组
				if (uInfo.endsWith("<用户组>")) {
					String gName = uInfo.substring(0, uInfo.indexOf("<"));
					List<Contacts> list = contactsService.getContactsByGroupName(merchantPin, 
							gName.trim(), createBy);
					if (list != null) {
						for (Contacts contact : list) {
							if( isMobile(contact.getMobile())){
								Contacts c = new Contacts(contact.getMobile(),
										contact.getName());
								set.add(c);
							}
						}
					}
				} else {
					if (uInfo.indexOf("<") >= 0) {
						mobile = uInfo.substring(0, uInfo.indexOf("<"));
						name = uInfo.substring(uInfo.indexOf("<") + 1, uInfo
								.length() - 1);
					} else {
						mobile = uInfo;
						List<Contact> tContact = contactsService.checkContactByMobile(
								mobile.trim(), merchantPin, createBy, null);
						if( tContact!=null && tContact.size()>0 ){
							name = tContact.get(0).getName();
						}
					}
					mobile = mobile.trim();
					if( isMobile(mobile)){
						Contacts c = new Contacts(mobile, name);
						set.add(c);
					}
				}
			}
		}
		return set;
	}
	
	private static boolean isMobile(String mobile){
		if (null == mobile || "".equals(mobile.trim())) {
			return false;
		}
		return Pattern.matches("^1\\d{10}$", mobile);
	}
	
	@Action(value = "lotteryUpshotList", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String lotteryUpshotList(){
		log.debug("lotteryUpshotList action run");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("startPage", (page - 1) * 20);
        paraMap.put("pageSize", rows);
        paraMap.put("id", getDecode("lotteryId"));
        paraMap.put("gradeLevelName", getDecode("gradeLevelName"));
        paraMap.put("mobile", getDecode("mobile"));
        paraMap.put("createTime", getDecode("createTime"));
        entityMap = new HashMap<String, Object>();
        List<MasSmsLotteryUpshot> lotteryUpshotList = lotteryUpshotService.queryLotteryUpshotById(paraMap);
        entityMap.put("rows", lotteryUpshotList);
        
        paraMap.put("startPage", null);
        paraMap.put("pageSize", null);
        paraMap.put("gradeLevelName", null);
        paraMap.put("mobile", null);
        paraMap.put("createTime", null);
        lotteryUpshotList = lotteryUpshotService.queryLotteryUpshotById(paraMap);
        entityMap.put("totalrecords", lotteryUpshotList.size());
        entityMap.put("currpage", page);
        return SUCCESS;
	}
	
	@Action(value = "deleteLotteryUpshot", results = {
			@Result(type = "json", params = {
					"root", "entityMap", "contentType", "text/html"})})
	public String deleteLotteryUpshot(){
		log.debug("deleteLottery action run");
		try{
			lotteryUpshotService.deleteLotteryUpshot(smsIds);
			entityMap =responseMapByMsg("success","删除成功");
		}catch (Exception e) {
			log.error(e.getMessage());
			entityMap =responseMapByMsg("删除失败");
		}
		return SUCCESS;
	}
	
	@Action(value = "queryLotteryNumber", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String queryLotteryNumber() {
		Users loginUser = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		String code = mbnSmsTaskNumberService.getTaskNumber(loginUser.getMerchantPin(), ApSmsConstants.OPERATION_CODING_CJ);
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("code", code);
		return SUCCESS;
	}
	
	@Action(value = "filterLottery", results = {
			@Result(type = "json", params = {
					"root", "entityMap", "contentType", "text/html"})})
	public String filterLottery(){
		log.debug("filterLottery action run");
		try{
			Map<String,Object> param=new HashMap<String, Object>();
			String type= getDecode("state");
			if("unsend".equals(type)){
				param.put("type", "0");
			}else if("send".equals(type)){
				param.put("type", "1");
			}else if("lottery".equals(type)){
				param.put("type", "2");
			}
			param.put("title", "".equals(getDecode("title"))?null:getDecode("title"));
			param.put("loginAccount", "".equals(getDecode("loginAccount"))?null:getDecode("loginAccount"));
			param.put("tos", "".equals(getDecode("tos"))?null:getDecode("tos"));
			
			param.put("startPage", (page - 1) * 20);
			param.put("pageSize", rows);
			
			entityMap=new HashMap<String, Object>();
			List<MasSmsLottery> masSmsLotteryList=lotteryService.filterLottery(param);
			entityMap.put("rows", masSmsLotteryList);
			param.put("startPage", null);
			param.put("pageSize", null);
			int totalrecords=lotteryService.filterLottery(param).size();
	        entityMap.put("totalrecords", totalrecords);
	        entityMap.put("currpage", page);
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			entityMap =responseMapByMsg("查询失败");
		}
		return SUCCESS;
	}
	
	
	
	
	String getDecode(String param){
		String str="";
		try {
			param=this.getRequest().getParameter(param);
			if(param!=null){
				str=java.net.URLDecoder.decode(param,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			log.info("url转码异常："+e.toString());
		}
		return str;
	}
	
	private String title;
	private String tos;
	private String content;
	private int optionCount;
	private int lotteryTime;
	private String begin_time;
	private String end_time;
	private int txi;
	private String ready_send_time;
	private int multi_selected_number;
	private int effective_mode;
	private int support_repeat;
	private int need_successful_remind;
	private String need_successful_content;
	private int need_not_permmit_remind;
	private String need_not_permmit_content;
	private int need_content_error_remind;
	private String need_content_error_content;
	private String addrUploadFileName;
	private Long id;
	private String replyCode;
	private String replyText;
	private String smsText;
	private String entSign;
	private String flag;
	private Long tdTunnel=0L;
	private Long ydTunnel=0L;

	public Users getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Users loginUser) {
		this.loginUser = loginUser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTos() {
		return tos;
	}

	public void setTos(String tos) {
		this.tos = tos;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getOptionCount() {
		return optionCount;
	}

	public void setOptionCount(int optionCount) {
		this.optionCount = optionCount;
	}

	public int getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(int lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getTxi() {
		return txi;
	}

	public void setTxi(int txi) {
		this.txi = txi;
	}

	public String getReady_send_time() {
		return ready_send_time;
	}

	public void setReady_send_time(String ready_send_time) {
		this.ready_send_time = ready_send_time;
	}

	public int getMulti_selected_number() {
		return multi_selected_number;
	}

	public void setMulti_selected_number(int multi_selected_number) {
		this.multi_selected_number = multi_selected_number;
	}

	public int getEffective_mode() {
		return effective_mode;
	}

	public void setEffective_mode(int effective_mode) {
		this.effective_mode = effective_mode;
	}

	public int getSupport_repeat() {
		return support_repeat;
	}

	public void setSupport_repeat(int support_repeat) {
		this.support_repeat = support_repeat;
	}

	public int getNeed_successful_remind() {
		return need_successful_remind;
	}

	public void setNeed_successful_remind(int need_successful_remind) {
		this.need_successful_remind = need_successful_remind;
	}

	public String getNeed_successful_content() {
		return need_successful_content;
	}

	public void setNeed_successful_content(String need_successful_content) {
		this.need_successful_content = need_successful_content;
	}

	public int getNeed_not_permmit_remind() {
		return need_not_permmit_remind;
	}

	public void setNeed_not_permmit_remind(int need_not_permmit_remind) {
		this.need_not_permmit_remind = need_not_permmit_remind;
	}

	public String getNeed_not_permmit_content() {
		return need_not_permmit_content;
	}

	public void setNeed_not_permmit_content(String need_not_permmit_content) {
		this.need_not_permmit_content = need_not_permmit_content;
	}

	public int getNeed_content_error_remind() {
		return need_content_error_remind;
	}

	public void setNeed_content_error_remind(int need_content_error_remind) {
		this.need_content_error_remind = need_content_error_remind;
	}

	public String getNeed_content_error_content() {
		return need_content_error_content;
	}

	public void setNeed_content_error_content(String need_content_error_content) {
		this.need_content_error_content = need_content_error_content;
	}

	public String getAddrUploadFileName() {
		return addrUploadFileName;
	}

	public void setAddrUploadFileName(String addrUploadFileName) {
		this.addrUploadFileName = addrUploadFileName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReplyCode() {
		return replyCode;
	}

	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public String getEntSign() {
		return entSign;
	}

	public void setEntSign(String entSign) {
		this.entSign = entSign;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getTdTunnel() {
		return tdTunnel;
	}

	public void setTdTunnel(Long tdTunnel) {
		this.tdTunnel = tdTunnel;
	}

	public Long getYdTunnel() {
		return ydTunnel;
	}

	public void setYdTunnel(Long ydTunnel) {
		this.ydTunnel = ydTunnel;
	}
	
	private MbnSmsReadySendService mbnSmsReadySendService;
	private MbnThreeHCodeService mbnThreeHCodeService;
	private MbnSevenHCodeService mbnSevenHCodeService;
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Resource(name="smsMbnTunnelService")
	private SmsMbnTunnelService smsMbnTunnelService;
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	private MbnSmsOperationClassService mbnSmsOperationClassService;

	public MbnSmsReadySendService getMbnSmsReadySendService() {
		return mbnSmsReadySendService;
	}

	public void setMbnSmsReadySendService(
			MbnSmsReadySendService mbnSmsReadySendService) {
		this.mbnSmsReadySendService = mbnSmsReadySendService;
	}

	public MbnThreeHCodeService getMbnThreeHCodeService() {
		return mbnThreeHCodeService;
	}

	public void setMbnThreeHCodeService(MbnThreeHCodeService mbnThreeHCodeService) {
		this.mbnThreeHCodeService = mbnThreeHCodeService;
	}

	public MbnSevenHCodeService getMbnSevenHCodeService() {
		return mbnSevenHCodeService;
	}

	public void setMbnSevenHCodeService(MbnSevenHCodeService mbnSevenHCodeService) {
		this.mbnSevenHCodeService = mbnSevenHCodeService;
	}

	public MbnMerchantTunnelRelationService getMbnMerchantTunnelRelationService() {
		return mbnMerchantTunnelRelationService;
	}

	public void setMbnMerchantTunnelRelationService(
			MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService) {
		this.mbnMerchantTunnelRelationService = mbnMerchantTunnelRelationService;
	}

	public SmsMbnTunnelService getSmsMbnTunnelService() {
		return smsMbnTunnelService;
	}

	public void setSmsMbnTunnelService(SmsMbnTunnelService smsMbnTunnelService) {
		this.smsMbnTunnelService = smsMbnTunnelService;
	}

	public MbnMerchantConsumeService getMbnMerchantConsumeService() {
		return mbnMerchantConsumeService;
	}

	public void setMbnMerchantConsumeService(
			MbnMerchantConsumeService mbnMerchantConsumeService) {
		this.mbnMerchantConsumeService = mbnMerchantConsumeService;
	}

	public MbnSmsOperationClassService getMbnSmsOperationClassService() {
		return mbnSmsOperationClassService;
	}

	public void setMbnSmsOperationClassService(
			MbnSmsOperationClassService mbnSmsOperationClassService) {
		this.mbnSmsOperationClassService = mbnSmsOperationClassService;
	}

	
	public static int lotteryNoSend =0;//未发送
	public static int lotterySend =1;//已发送
	public static int lotteryYES =2;//已抽奖
	private File addrUpload;
	
	
	private static Log log=LogFactory.getLog(LotteryAction.class);
	private Users loginUser = (Users) super.getSession().getAttribute(com.leadtone.mas.admin.common.ApSmsConstants.SESSION_USER_INFO);
	@Autowired
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	
	
	private Long tp_batchId=0L;
	
	public Long getTp_batchId() {
		return tp_batchId;
	}

	public void setTp_batchId(Long tp_batchId) {
		this.tp_batchId = tp_batchId;
	}

	public File getAddrUpload() {
		return addrUpload;
	}

	public void setAddrUpload(File addrUpload) {
		this.addrUpload = addrUpload;
	}

	public ContactsService getContactsService() {
		return contactsService;
	}

	public void setContactsService(ContactsService contactsService) {
		this.contactsService = contactsService;
	}

	private String startTime;
	private String endTime;
	

	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	private String awardNum;
	
	public String getAwardNum() {
		return awardNum;
	}

	public void setAwardNum(String awardNum) {
		this.awardNum = awardNum;
	}

	public MbnSmsTaskNumberService getMbnSmsTaskNumberService() {
		return mbnSmsTaskNumberService;
	}

	public void setMbnSmsTaskNumberService(
			MbnSmsTaskNumberService mbnSmsTaskNumberService) {
		this.mbnSmsTaskNumberService = mbnSmsTaskNumberService;
	}

	public LotteryService getLotteryService() {
		return lotteryService;
	}

	public void setLotteryService(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public MasSmsLottery getForm() {
		return form;
	}

	public void setForm(MasSmsLottery form) {
		this.form = form;
	}
	
	public LotteryUpshotService getLotteryUpshotService() {
		return lotteryUpshotService;
	}

	public void setLotteryUpshotService(LotteryUpshotService lotteryUpshotService) {
		this.lotteryUpshotService = lotteryUpshotService;
	}
	
}
