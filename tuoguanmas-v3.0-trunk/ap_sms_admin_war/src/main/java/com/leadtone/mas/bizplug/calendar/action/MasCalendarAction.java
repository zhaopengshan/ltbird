package com.leadtone.mas.bizplug.calendar.action;
import static com.leadtone.mas.bizplug.common.MasCommonFunction.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

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
import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTask;
import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTaskSms;
import com.leadtone.mas.bizplug.calendar.bean.MbnSmsDuanxinrili;
import com.leadtone.mas.bizplug.calendar.service.MasSmsCalendarService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.BaseAction;
import com.leadtone.mas.bizplug.common.DateLocUtils;
import com.leadtone.mas.bizplug.common.DateUtil;
import com.leadtone.mas.bizplug.common.MasCommonFunction;
import com.leadtone.mas.bizplug.common.MasSmsBean;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDati;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInboxVO;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.service.MbnSmsHadSendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsInboxService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsSelectedService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.smssend.util.ContactsUtil;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ConvertUtil;
import com.leadtone.mas.bizplug.util.ExportUtil;
import com.leadtone.mas.bizplug.util.WebUtils;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteList;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/calendar")
public class MasCalendarAction extends BaseAction {
	
	private static Log LOG = LogFactory.getLog(MasCalendarAction.class);
	
	private static final String SEND_TYPE_NOW="NOW";
	private static final String SEND_TYPE_CYCLE="CYCLE";
	private static final String SEND_TYPE_TIMER="TIMER";
	private static final String PARA_RTN_MAP="entityMap";
	private static final String PARA_CYCLE_SELECT_MONTH="每月";
	private static final String PARA_CYCLE_SELECT_WEEK="每周";
	private static final String PARA_CYCLE_SELECT_DAY="每日";
	
	private static final String PARA_DATA_FORMAT="yyyy-MM-dd HH:mm";
	private static final String PARA_TUNNEL_LIST="tunnelList"; 
	private static final String PARA_LIST_TYPE_SEND="send";
	private static final String PARA_LIST_TYPE_NOT_SEND="notSend";
	
	private static final String ERROR_TIME_FORMAT_ERR="定时发送时间错误!";
	private static final String ERROR_PARA_CHECK_ERR="参数检查失败!";
	private static final String ERROR_INVALID_RECEIVER_ERR="无有效接收人!";
	private static final String ERROR_INSER_CALENDR_DB_ERR="其他错误!";
	
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private MbnSmsReadySendService mbnSmsReadySendService;
	@Autowired
	private MbnSmsHadSendService mbnSmsHadSendService;
	@Autowired
	private MbnThreeHCodeService mbnThreeHCodeService;
	@Autowired
	private MbnSevenHCodeService mbnSevenHCodeService;
	@Autowired
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Autowired
	private SmsMbnTunnelService smsMbnTunnelService;
	@Autowired
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	@Autowired
	private MbnSmsOperationClassService mbnSmsOperationClassService;
	@Autowired
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	@Resource
	private MbnSmsSelectedService mbnSmsSelectedService;
	@Resource
	private MasSmsCalendarService masSmsCalendarService;
	@Resource
	private MbnSmsInboxService mbnSmsInboxService;
	@Autowired
	private PortalUserExtService portalUserExtService;
	
	@Action(value = "calendarSend", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String createCalendar(){
		Users loginUser = (Users)super.getSessionUsers();
		Set<Contacts> userSet = null;
		List<String> colsList = null;
		MasCommonFunction masCommonFunction=null;
		
		/**   1,对参数进行基本检查    **/
		final String checkVal=checkParameters();
		if(!StringUtils.isBlank(checkVal)){
			getRequest().setAttribute(PARA_RTN_MAP, responseMapByMsg(checkVal));
			return SUCCESS;
		}
		Long createBy = loginUser.getId();
		if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
				|| loginUser.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
			createBy = null;
		}
		colsList=new ArrayList<String>();
		if(null == flag){
			userSet = ContactsUtil.getContactsSet(contactsService, getReceiver(), loginUser.getMerchantPin(), createBy);
		}
		if (getAddrUpload() != null) {
			userSet=getUploadContacts(colsList,userSet);
		}
		/** 2,对参数的有效性进行二次检查 **/
		if (userSet.size() == 0) {
	        this.getRequest().setAttribute(PARA_RTN_MAP, responseMapByMsg(ERROR_INVALID_RECEIVER_ERR));
			return SUCCESS;
		}
		Date readySendTime = new Date();
		final String sendType = getSendType();
		SimpleDateFormat sdf = new SimpleDateFormat(PARA_DATA_FORMAT);
		if (SEND_TYPE_TIMER.equalsIgnoreCase(sendType)) {
				try {
					readySendTime = sdf.parse(getSendTime());
				} catch (ParseException e) {
			        getRequest().setAttribute(PARA_RTN_MAP, responseMapByMsg(ERROR_TIME_FORMAT_ERR));
					return SUCCESS;
				}
		}
		try {
			long batchId=insertCalendarData(loginUser,readySendTime);
			if(batchId>0){
				if(SEND_TYPE_CYCLE.equalsIgnoreCase(sendType)){
					readySendTime=sdf.parse(getSendTime());
				}
				MasSmsBean masSmsBean=new MasSmsBean();
				initMasSmsBean(masSmsBean,loginUser,userSet);
				masSmsBean.setBatchId(batchId);
				masCommonFunction=new MasCommonFunction(masSmsBean);
				//生成短信记录，并返回消息
				entityMap = masCommonFunction.makeMbnSmsSendTask(loginUser,readySendTime,colsList);
				this.getRequest().setAttribute(PARA_RTN_MAP, entityMap);
				List<SmsMbnTunnelVO>  tunnelList = masCommonFunction.getTunnelList(loginUser.getMerchantPin());
				this.getRequest().setAttribute(PARA_TUNNEL_LIST, tunnelList);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			this.getRequest().setAttribute(PARA_RTN_MAP, responseMapByMsg(ERROR_INSER_CALENDR_DB_ERR));
			return SUCCESS;
		}
		//entityMap = new HashMap<String, Object>();
		//entityMap.put("resultcode", "success" );
       // entityMap.put("message", "提交成功");
		return SUCCESS;
	}
	
	private long insertCalendarData(Users loginUser,Date readySendTime) throws SQLException{
		String content=this.getSmsText() + (this.getReplyText()==null?"":this.getReplyText()) + (this.getEntSign()==null?"":this.getEntSign());
		MbnSmsDuanxinrili mbnSmsDuanxinrili=new MbnSmsDuanxinrili();
		long batchId=PinGen.getSerialPin();
		mbnSmsDuanxinrili.setId(batchId);
		mbnSmsDuanxinrili.setTitle(this.getTitle());
		mbnSmsDuanxinrili.setContent(content);
		mbnSmsDuanxinrili.setCreateBy(loginUser.getId());
		mbnSmsDuanxinrili.setCreateTime(new Date());
		
		mbnSmsDuanxinrili.setMerchantPin(loginUser.getMerchantPin());
		int number=0;
		int type=0;
		int cycleType=0;//0为日，1为周，2为月
		int remindWay=0;
		int selectCycleHour=0;
		int selectCycleMinutes=0;
		String sendTime="";
		MbnPeriodcTaskSms mbnPeriodcTaskSms=null;
		MbnPeriodcTask mbnPeriodcTask=null;
		if(SEND_TYPE_CYCLE.equalsIgnoreCase(sendType)){
			if(PARA_CYCLE_SELECT_MONTH.equals(this.selectCycle)){
				number=Integer.parseInt(this.getSelectCycleMonth());
				cycleType=3;
			}else if(PARA_CYCLE_SELECT_WEEK.equals(this.selectCycle)){
				number=Integer.parseInt(this.getSelectCycleWeek());
				cycleType=2;
			}else{
				cycleType=1;
			}
			selectCycleHour=Integer.parseInt(this.getSelectCycleHour());
			selectCycleMinutes=Integer.parseInt(this.getSelectCycleMinutes());
			remindWay=2;
//			mbnPeriodcTaskSms=new MbnPeriodcTaskSms();
//			mbnPeriodcTaskSms.setId(mbnSmsDuanxinrili.getId());
//			mbnPeriodcTaskSms.setContent(mbnSmsDuanxinrili.getContent());
//			mbnPeriodcTaskSms.setTaskId(mbnSmsDuanxinrili.getId());
//			mbnPeriodcTaskSms.setTitle(mbnSmsDuanxinrili.getTitle());
//			mbnPeriodcTaskSms.setTos(this.getReceiver());
			
			mbnPeriodcTask=new MbnPeriodcTask();
			mbnPeriodcTask.setId(mbnSmsDuanxinrili.getId());
			mbnPeriodcTask.setMerchantPin(mbnSmsDuanxinrili.getMerchantPin());
			mbnPeriodcTask.setOperationId(ApSmsConstants.OPERATION_CODING_TYPE_TX);
			mbnPeriodcTask.setAwokeMode(cycleType);
			mbnPeriodcTask.setNumber(number);
			Calendar   cal   =   Calendar.getInstance();  
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.DATE, cal.get(Calendar.DATE));
			cal.set(Calendar.HOUR_OF_DAY, selectCycleHour);
			cal.set(Calendar.MINUTE, selectCycleMinutes);
			mbnPeriodcTask.setAwakeTime(cal.getTime());
			
			mbnPeriodcTask.setStatus(2);
			mbnPeriodcTask.setCalendarType(0);
			mbnPeriodcTask.setLastTime(cal.getTime());
			mbnPeriodcTask.setTaskType(0);
			mbnPeriodcTask.setCreater(mbnSmsDuanxinrili.getCreateBy());
			// 设置用户定义执行次数，默认为10000次
			if( getRemindCounts() == null || getRemindCounts() == 0){
				mbnPeriodcTask.setTimes(10000);
			}else{
				mbnPeriodcTask.setTimes(getRemindCounts());
			}
			// 设置默认执行1次
			mbnPeriodcTask.setSendTimes(1);
			//如果周期任务等于当天，则执行短信周期发送
			Date awakeTime=calendarPeriodicService(mbnPeriodcTask);
			this.setSendTime(new SimpleDateFormat(PARA_DATA_FORMAT).format(awakeTime.getTime()));
//			if(DateLocUtils.isEqualsDate(awakeTime, cal.getTime())){
//				this.setSendTime(new SimpleDateFormat(PARA_DATA_FORMAT).format(awakeTime.getTime()));
//			}else{
//				batchId=0;
//			}
		}else if(SEND_TYPE_TIMER.equalsIgnoreCase(sendType)){
			sendTime=this.getSendTime();
			remindWay=1;
			type=1;
		}
		mbnSmsDuanxinrili.setRemindtime(readySendTime);
		mbnSmsDuanxinrili.setRemindhour(selectCycleHour);
		mbnSmsDuanxinrili.setRemindminutes(selectCycleMinutes);
		mbnSmsDuanxinrili.setCycletype(cycleType);
		mbnSmsDuanxinrili.setNumber(number);
		mbnSmsDuanxinrili.setRemindway(remindWay);//提醒方式
		mbnSmsDuanxinrili.setTos(this.getReceiver());
		mbnSmsDuanxinrili.setType(type);
		try {
			masSmsCalendarService.createRiliInfo(mbnSmsDuanxinrili, mbnPeriodcTaskSms, mbnPeriodcTask);
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return batchId;
	}
	/**
	 * 获取周期任务第一次执行时间
	 * @param mbnPeriodcTask
	 * @return
	 */
	private Date calendarPeriodicService(MbnPeriodcTask mbnPeriodcTask){
		int awokeMode=mbnPeriodcTask.getAwokeMode();//唤醒模式  1天，2周，3月
		int number=mbnPeriodcTask.getNumber();//周几或几号
		Date awakeDateTime=mbnPeriodcTask.getAwakeTime();//唤醒时间 201212231223
		//1,获取周期执行时间
		DateLocUtils dateUtil=null;
		Calendar   cal   =null;
		dateUtil=new DateLocUtils();
		if(awokeMode==ApSmsConstants.PERIODIC_AWOKE_MODE_DAY){
			cal   =   Calendar.getInstance();  
		}else if(awokeMode==ApSmsConstants.PERIODIC_AWOKE_MODE_WEEK){
			Date date=dateUtil.getCurrentWeekday(number);
			cal   =   Calendar.getInstance(); 
			cal.setTime(date);
		}else{//月
			cal=dateUtil.getNumDayOfMonth(number);
		}
		Calendar   calOri   =Calendar.getInstance();  
		calOri.setTime(awakeDateTime);
		//calOri.set(cal.get(Calendar.YEAR),cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DATE), calOri.get(Calendar.HOUR), calOri.get(Calendar.MINUTE));
		calOri.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		calOri.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		calOri.set(Calendar.DATE, cal.get(Calendar.DATE));
		
		//获取生成的时间是否小于当天的时间，如果小于则重新生成
		mbnPeriodcTask.setAwakeTime(calOri.getTime());
		getNextTaskCalendar(mbnPeriodcTask);
		return mbnPeriodcTask.getAwakeTime();
	}
	private void getNextTaskCalendar(MbnPeriodcTask mbnPeriodcTask){
		Calendar   calCurr   =null;
		Calendar calOri=Calendar.getInstance();
		calOri.setTime(mbnPeriodcTask.getAwakeTime());
		calCurr=Calendar.getInstance();
		if(calCurr.getTimeInMillis()>calOri.getTimeInMillis()){
			//判断时间是否相同,如当前时间大于规则执行时间，则重新生成规则时间
			int awokeMode=mbnPeriodcTask.getAwokeMode();//唤醒模式  1天，2周，3月
			int number=mbnPeriodcTask.getNumber();//周几或几号
			Date awakeDateTime=mbnPeriodcTask.getAwakeTime();//唤醒时间 201212231223
			//1,获取周期执行时间
			if(awokeMode==ApSmsConstants.PERIODIC_AWOKE_MODE_DAY){
				calOri.add(Calendar.DAY_OF_MONTH, 1);
			}else if(awokeMode==ApSmsConstants.PERIODIC_AWOKE_MODE_WEEK){
				calOri.add(Calendar.WEEK_OF_MONTH, 1);
			}else{//月
				calOri.add(Calendar.MONTH, 1);
			}
			mbnPeriodcTask.setLastTime(mbnPeriodcTask.getAwakeTime());
			mbnPeriodcTask.setAwakeTime(calOri.getTime());
		}
	}
	
	/**
	 * 创建通知初始化
	 * @return
	 */
	@Action(value = "writesms", results = {
			@Result(name = SUCCESS, location = "/sms/calendar/jsp/calendar_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String writeSms() {
        // 获取商户通道列表
		Users loginUser = (Users)super.getSessionUsers();
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
	@Action(value = "calendarResultDelete", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String delResultCalendar(){
		String[] smsIdsArray = smsIds.split(",");
		Long[] smsIds = ConvertUtil.arrStringToLong(smsIdsArray);
		try{
			mbnSmsReadySendService.batchDeleteByPks(smsIds);
			entityMap =responseMapByMsg("success","删除成功");
		}catch( Exception e ){
			LOG.error(e.getMessage());
			entityMap =responseMapByMsg("删除失败");
		}
		return SUCCESS;
	}
	
	@Action(value = "calendarDelete", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String delCalendar(){
		Users loginUser = (Users)super.getSessionUsers();
		List<Long> ids=new ArrayList<Long>();
		String smsIds=this.getSmsIds();
		if(!StringUtil.isEmpty(smsIds)){
			String[] smsSplitIds=smsIds.split(",");
			for(String id:smsSplitIds){
				ids.add(new Long(id));
			}
			try {
				masSmsCalendarService.deleteCalendarById(ids);
				entityMap =responseMapByMsg("success","删除成功");
			} catch (SQLException e) {
				LOG.error(e.getMessage());
				entityMap =responseMapByMsg("删除失败");
			}
		}else{
			entityMap =responseMapByMsg("删除失败");
		}
		return SUCCESS;
	}
	/**
	 * 通知列表查询
	 * @return
	 */
	@Action(value = "calendarList", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String listCalendar(){
		Users loginUser = (Users)super.getSessionUsers();
		String listType=this.getRequest().getParameter("listType");
		String searchTitle=this.getRequest().getParameter("searchTitle");
		String searchRemindWay=this.getRequest().getParameter("searchRemindWay");//通知类型
		String searchDateFrom=this.getRequest().getParameter("searchDateFrom");
		String searchDateTo=this.getRequest().getParameter("searchDateTo");
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("startPage", (page - 1) * 20);
        paraMap.put("pageSize", rows);
        
        if(!StringUtils.isBlank(searchTitle))
			try {
				paraMap.put("title", URLDecoder.decode(searchTitle,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
			}
        if(!StringUtils.isBlank(searchDateFrom))
        	paraMap.put("startDate", searchDateFrom);
        if(!StringUtils.isBlank(searchDateTo))
        	paraMap.put("endDate",searchDateTo+" 23:59:59");
        
        if(!StringUtils.isBlank(searchRemindWay)){
        	//0立即，1定时，2周期
        	if(!searchRemindWay.equals("3"))
        		paraMap.put("remindWay",searchRemindWay);
        }
        paraMap.put("createBy", loginUser.getId());
//        if(listType.equals(PARA_LIST_TYPE_SEND)){//listType ,,,待发箱notSend ，已发箱send
//        	paraMap.put("sendResult",0);//sendResult==0已发
//        }else if(listType.equals(PARA_LIST_TYPE_NOT_SEND)){
//        	paraMap.put("type",0);//sendResult<>0待发
//        }
        if(listType.equals(PARA_LIST_TYPE_SEND)){//listType ,,,待发箱notSend ，已发箱send
        	paraMap.put("sendResult","1");//已发
        }else if(listType.equals(PARA_LIST_TYPE_NOT_SEND)){
        	paraMap.put("sendResult","0");//待发
        }
        entityMap = new HashMap<String, Object>();
        if(LOG.isDebugEnabled()){
        	LOG.debug("用户的登录信息："+loginUser.getMerchantPin());
        }
        List<MbnSmsDuanxinrili> listMbnSmsDuanxinrili=null;
        String sendResult=(String)paraMap.get("sendResult");
        int totalCount=masSmsCalendarService.getAllCalendarCount(paraMap);
        if(totalCount>0){
        	paraMap.put("sendResult", sendResult);
        	listMbnSmsDuanxinrili=masSmsCalendarService.getAllCalendarList(paraMap);
        }
        else{
        	listMbnSmsDuanxinrili=new ArrayList<MbnSmsDuanxinrili>();
        }
        entityMap.put("rows", listMbnSmsDuanxinrili);
        entityMap.put("totalrecords", totalCount);
        entityMap.put("currpage", page);
        return SUCCESS;
	}
	/**
	 * 导出日程提醒列表
	 * @return
	 */
	@Action(value = "exportCalendarList", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String exportCalendarList(){
		Users loginUser = (Users)super.getSessionUsers();
		String listType=this.getRequest().getParameter("listType");
		String searchTitle=this.getRequest().getParameter("searchTitle");
		String searchRemindWay=this.getRequest().getParameter("searchRemindWay");//通知类型
		String searchDateFrom=this.getRequest().getParameter("searchDateFrom");
		String searchDateTo=this.getRequest().getParameter("searchDateTo");
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
//        paraMap.put("startPage", (page - 1) * 20);
//        paraMap.put("pageSize", rows);
        
        if(!StringUtils.isBlank(searchTitle))
			try {
				paraMap.put("title", URLDecoder.decode(searchTitle,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
			}
        if(!StringUtils.isBlank(searchDateFrom))
        	paraMap.put("startDate", searchDateFrom);
        if(!StringUtils.isBlank(searchDateTo))
        	paraMap.put("endDate",searchDateTo+" 23:59:59");
        
        if(!StringUtils.isBlank(searchRemindWay)){
        	//0立即，1定时，2周期
        	if(!searchRemindWay.equals("3"))
        		paraMap.put("remindWay",searchRemindWay);
        }
        paraMap.put("createBy", loginUser.getId());
//        if(listType.equals(PARA_LIST_TYPE_SEND)){
//        	paraMap.put("sendResult",0);
//        }else if(listType.equals(PARA_LIST_TYPE_NOT_SEND)){
//        	paraMap.put("type",0);
//        }
        if(listType.equals(PARA_LIST_TYPE_SEND)){//listType ,,,待发箱notSend ，已发箱send
        	paraMap.put("sendResult","1");//已发
        }else if(listType.equals(PARA_LIST_TYPE_NOT_SEND)){
        	paraMap.put("sendResult","0");//待发
        }
        entityMap = new HashMap<String, Object>();
        if(LOG.isDebugEnabled()){
        	LOG.debug("用户的登录信息："+loginUser.getMerchantPin());
        }
        try{
        List<MbnSmsDuanxinrili> listMbnSmsDuanxinrili=null;
        listMbnSmsDuanxinrili=masSmsCalendarService.getAllCalendarList(paraMap);
        if(listMbnSmsDuanxinrili==null){
        	listMbnSmsDuanxinrili=new ArrayList<MbnSmsDuanxinrili>();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int size=listMbnSmsDuanxinrili.size();
        String[][] billsInArr = new String[size][5];
        for(int i=0;i<size;i++){
        	MbnSmsDuanxinrili mbnSmsDuanxinrili=listMbnSmsDuanxinrili.get(i);
        	if("send".equals(listType))
        		billsInArr[i][0]="已发送";
        	if("notSend".equals(listType))
        		billsInArr[i][0]="待发送";
        	billsInArr[i][1]=mbnSmsDuanxinrili.getTitle()==null?"无":mbnSmsDuanxinrili.getTitle();
        	int remindWay=mbnSmsDuanxinrili.getRemindway();
        	billsInArr[i][2]="立即";
        	if(remindWay==1)billsInArr[i][2]="定时";
        	if(remindWay==2)billsInArr[i][2]="周期";
        	billsInArr[i][3]=mbnSmsDuanxinrili.getContent();
        	billsInArr[i][4]=sdf.format(mbnSmsDuanxinrili.getRemindtime());
        }
        String[] cols = {"状态", "任务名称","提醒类别","提醒内容","最新提醒时间"};
        String downLoadPath = ExportUtil.exportToExcel(getRequest(), listType.toUpperCase(), cols, billsInArr);
        entityMap.put("fileName", downLoadPath);
        entityMap.put("message", "导出"+size+"条日程提醒成功！");
		entityMap.put("resultcode", SUCCESS);
        }catch(Exception e){
        	entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", ERROR);
			entityMap.put("message", "导出日程提醒出错，请稍后再试");
            e.printStackTrace();
            return ERROR;
        }
		return SUCCESS;
	}
	
	/**
	 * 导出日程提醒结果列表
	 * @return
	 */
	@Action(value = "exportCalendarResultList", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String exportCalendarResultList(){
		String resultListType=this.getRequest().getParameter("resultListType");
		final String listType=this.getRequest().getParameter("listType");//获取发件箱类型
		List<MbnSmsReadySend> mbnSmsReadySendList=null;
		if(!StringUtil.isEmpty(batchId)){
			PageUtil pageUtil = new PageUtil();
//			pageUtil.setStart(page);
//			pageUtil.setPageSize(rows);
			pageUtil.setBatchId(Long.valueOf(batchId));
			 entityMap = new HashMap<String, Object>();
			try{
				Users u = (Users)super.getSessionUsers();                          
				pageUtil.setMerchantPin(u.getMerchantPin());
				
				if(!StringUtil.isEmpty(searchBycontacts)){
					pageUtil.setReceiverName(URLDecoder.decode(searchBycontacts,"UTF-8"));
				}
				if(!StringUtil.isEmpty(sendResult)&&!sendResult.equals("4")){
					pageUtil.setSendResult(Integer.valueOf(sendResult));
				}
				if(!StringUtil.isEmpty(searchByMobile)){
					pageUtil.setReceiveMoble(searchByMobile);
				}
				//手机号及失败原因
				if(!StringUtil.isEmpty(searchByFailReason)){
					pageUtil.setFailReason(URLDecoder.decode(searchByFailReason,"UTF-8"));
				}
				Page page =null;
				if(listType.equals("notSend")){
					page = mbnSmsReadySendService.batchPage(pageUtil);
				}else{
					page = mbnSmsHadSendService.batchPage(pageUtil);
				}
				
				if( page != null ){
					@SuppressWarnings("unchecked")
					
					List datas = (List) page.getData();
					   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				      int size=datas.size();
				      String[][] billsInArr = new String[size][7];
				      for(int i=0;i<size;i++){
				    	  MbnSmsHadSend mbnSmsHadSend=null;
				    	  MbnSmsReadySend mbnSmsReadySend=null;
				    	  Object dataObj=datas.get(i);
				    	  if(dataObj instanceof MbnSmsReadySend){
				    		  mbnSmsReadySend=(MbnSmsReadySend)dataObj;
				    	  }else{
				    		  mbnSmsHadSend=(MbnSmsHadSend)dataObj;
				    	  }
				    	  resultListType="1";//导出列表一致
				    	  if(resultListType.equals("1")){
				    		  int sendResult=0;
				    		  if(mbnSmsReadySend!=null){
				    			  billsInArr[i][0]=mbnSmsReadySend.getTos();
						    	  billsInArr[i][1]=mbnSmsReadySend.getTosName()==null?"(未知)":mbnSmsReadySend.getTosName();
						    	  sendResult=mbnSmsReadySend.getSendResult();
						    	  billsInArr[i][2]=mbnSmsReadySend.getTitle();
						    	  billsInArr[i][3]=mbnSmsReadySend.getContent();
						    	  billsInArr[i][4]=(null == mbnSmsReadySend.getReadySendTime()  ? "" : sdf.format(mbnSmsReadySend.getReadySendTime()));
				    		  }else if(mbnSmsHadSend!=null){
				    			  billsInArr[i][0]=mbnSmsHadSend.getTos();
						    	  billsInArr[i][1]=mbnSmsHadSend.getTosName()==null?"(未知)":mbnSmsHadSend.getTosName();
						    	  sendResult=mbnSmsHadSend.getSendResult();
						    	  billsInArr[i][2]=mbnSmsHadSend.getTitle();
						    	  billsInArr[i][3]=mbnSmsHadSend.getContent();
						    	  billsInArr[i][4]=(null == mbnSmsHadSend.getReadySendTime()  ? "" : sdf.format(mbnSmsHadSend.getReadySendTime()));
				    		  }
				    		  String resultType="已取消";
					    	  switch (sendResult){
					    	  case 0:resultType="等待发送";
					    		  break;
					    	  case 1:resultType="已提交网关";
				    		  break;
					    	  case 2:resultType="成功";
				    		  break;
					    	  case 3:resultType="失败";
				    		  break;
					    	  }
					    	  billsInArr[i][5]=resultType;
					    	  if(mbnSmsReadySend!=null)
					    		  billsInArr[i][6]=mbnSmsReadySend.getFailReason()==null?"(无)":mbnSmsReadySend.getFailReason();
					    	  else if(mbnSmsHadSend!=null)
					    		  billsInArr[i][6]=mbnSmsHadSend.getFailReason()==null?"(无)":mbnSmsHadSend.getFailReason();
				    	  }else{
				    		  if(mbnSmsReadySend!=null){
				    			  billsInArr[i][0]=mbnSmsReadySend.getTos();
					    		  billsInArr[i][1]=mbnSmsReadySend.getTosName()==null?"(未知)":mbnSmsReadySend.getTosName();
					    		  billsInArr[i][2]=mbnSmsReadySend.getContent();
					    		  billsInArr[i][3]=sdf.format(mbnSmsReadySend.getReadySendTime());
				    		  }else if(mbnSmsHadSend!=null){
				    			  billsInArr[i][0]=mbnSmsHadSend.getTos();
					    		  billsInArr[i][1]=mbnSmsHadSend.getTosName()==null?"(未知)":mbnSmsHadSend.getTosName();
					    		  billsInArr[i][2]=mbnSmsHadSend.getContent();
					    		  billsInArr[i][3]=sdf.format(mbnSmsHadSend.getReadySendTime());
				    		  }
				    	  }
				      }
				      String[] cols =null; 
				      if(resultListType.equals("0")){
				    	  cols=new String[]{"接收人姓名","提醒内容","发送时间"};
				      }else{
				    	  cols=new String[]{"手机号码","接收人姓名","短信标题","短信内容","发送时间","发送结果","失败原因"};
				      }
				      String downLoadPath = ExportUtil.exportToExcel(getRequest(), "calendarResult", cols, billsInArr);
				        entityMap.put("fileName", downLoadPath);
				        entityMap.put("message", "导出"+size+"条日程提醒结果成功！");
						entityMap.put("resultcode", SUCCESS);
				}
	        }catch(Exception e){
	        	entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", ERROR);
				entityMap.put("message", "导出日程提醒结果出错，请稍后再试");
				LOG.error(e.getMessage());
	            return ERROR;
	        }
		}
		return SUCCESS;
	}
	
	@Action(value = "getReplyInfo",
			results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String replyInfo(){ 
		String result=SUCCESS;
		try{ 
			PageUtil pageUtil=new PageUtil();
	 		pageUtil.setStart(page);
	 		pageUtil.setBatchId(Long.parseLong(batchId));
	 		pageUtil.setPageSize(rows);
	 		if(!StringUtil.isEmpty(replyName)){
				pageUtil.setReplyName(URLDecoder.decode(replyName,"UTF-8"));
			}
	 		if(!StringUtil.isEmpty(replyMobile)){
				pageUtil.setReplyMobile(replyMobile);
			}
			if(!StringUtil.isEmpty(searchAct)){
				Timestamp endDate = new Timestamp(System.currentTimeMillis());
				switch(Integer.valueOf(searchAct)){
					case 1: //all
					break;
					case 2: //一天内
						pageUtil.setEndDate(endDate);
						Date startDateDay = DateUtil.oneDayAgo(endDate);
						pageUtil.setStartDate(startDateDay);
					break;
					case 3: //一周内 7天
						pageUtil.setEndDate(endDate);
						Date startDateWeek = DateUtil.oneWeekAgo(endDate);
						pageUtil.setStartDate(startDateWeek);
						break;
					case 4: //一月内 30天
						pageUtil.setEndDate(endDate);
						Date startDateMonth = DateUtil.oneMonthAgo(endDate);
						pageUtil.setStartDate(startDateMonth);
						break;
					case 5: //时间区间dateFrom
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						if(!StringUtil.isEmpty(dateTo)){
							pageUtil.setEndDate(format.parse(dateTo));
						}
						if(!StringUtil.isEmpty(dateFrom)){
							pageUtil.setStartDate(format.parse(dateFrom));
						}
						break;
				}
			}
	 		Page m=mbnSmsInboxService.replyPage(pageUtil); 
	 		if(m!=null){
	 			@SuppressWarnings("unchecked")
				List<MbnSmsInboxVO> mshs=(List<MbnSmsInboxVO>) m.getData();
	 			entityMap = new HashMap<String, Object>();
	            entityMap.put("total", m.getRecords());
	            if( mshs == null ){
	            	mshs = new ArrayList<MbnSmsInboxVO>();
	            }
	            entityMap.put("rows", mshs); 
	            entityMap.put("totalrecords", m.getTotal());
	            entityMap.put("currpage", m.getStart());
	 		}
		}catch (Exception e) {
			e.printStackTrace();
			result=ERROR;
		}
		return result;
	}
	
	
	@Action(value = "calendarResultList", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String getBatchSms(){
		List<MbnSmsReadySend> mbnSmsReadySendList=null;
		final String listType=this.getRequest().getParameter("listType");//获取发件箱类型
		if(!StringUtil.isEmpty(batchId)){
			PageUtil pageUtil = new PageUtil();
			pageUtil.setStart(page);
			pageUtil.setPageSize(rows);
			pageUtil.setBatchId(Long.valueOf(batchId));                        
			try{
				Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);                                
				pageUtil.setMerchantPin(u.getMerchantPin());
				
				if(!StringUtil.isEmpty(searchBycontacts)){//接收人姓名
					pageUtil.setReceiverName(URLDecoder.decode(searchBycontacts,"UTF-8"));
				}
				if(!StringUtil.isEmpty(sendResult)&&!sendResult.equals("4")){
					pageUtil.setSendResult(Integer.valueOf(sendResult));
				}
				if(!StringUtil.isEmpty(searchByMobile)){
					pageUtil.setReceiveMoble(searchByMobile);
				}
				//手机号及失败原因
				if(!StringUtil.isEmpty(searchByFailReason)){
					pageUtil.setFailReason(URLDecoder.decode(searchByFailReason,"UTF-8"));
				}
				Page page =null;
				entityMap = new HashMap<String, Object>();
				//根据不同的上传条件查询不同的短信箱
				if(listType.equals("notSend")){
					page = mbnSmsReadySendService.batchPage(pageUtil);
					@SuppressWarnings("unchecked")
					List<MbnSmsHadSend> datas = (List<MbnSmsHadSend>) page.getData();
					entityMap.put("rows", datas);
				}
				else
				{
					page = mbnSmsHadSendService.batchPage(pageUtil);
					@SuppressWarnings("unchecked")
					List<MbnSmsReadySend> datas = (List<MbnSmsReadySend>) page.getData();
					entityMap.put("rows", datas);
				}
				if( page != null ){
		            entityMap.put("total", page.getRecords());
		            entityMap.put("totalrecords", page.getTotal());
		            entityMap.put("currpage", page.getStart());
				}
	        }catch(Exception e){
	            logger.error(e.getMessage());
	            return ERROR;
	        }
		}
		return SUCCESS;
	}
	
	@Action(value = "calendarResultCountList", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String getBatchSmsCount(){
		List<MbnSmsReadySend> mbnSmsReadySendList=null;
		List<MbnSmsHadSend> mbnSmsHadSendList=null;
		final String listType=this.getRequest().getParameter("listType");//获取发件箱类型
		if( !StringUtil.isEmpty(batchId)){
			try{
				Users loginUser = (Users)super.getSessionUsers();
				//Long batchId, Long mPin, Long createBy
				//设定只查询自己发送的信息
                 boolean isQuerySelf = false;
                 Long createBy = null;
                 if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                     isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                     if(isQuerySelf && loginUser.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                    	 createBy = loginUser.getId();
                     }
                 }
				 entityMap = new HashMap<String, Object>();
				 if(listType.equals("notSend")){
					 mbnSmsReadySendList = mbnSmsReadySendService.getByBatchId(Long.valueOf(batchId), loginUser.getMerchantPin(), createBy);
				 }else{
					 mbnSmsHadSendList = mbnSmsHadSendService.getByBatchId(Long.valueOf(batchId), loginUser.getMerchantPin(), createBy);
				 }
				smsSendResult = new HashMap<String,Integer>();
				smsSendResult.put("success", 0);
				smsSendResult.put("failure", 0);
				smsSendResult.put("sending", 0);
				smsSendResult.put("cancel", 0);
				smsSendResult.put("waiting", 0);
				if(mbnSmsReadySendList!=null&& !mbnSmsReadySendList.isEmpty() ){
					smsSendResult.put("totails", mbnSmsReadySendList.size());
					for(int i = 0; i < mbnSmsReadySendList.size(); i++){
						MbnSmsReadySend temp = mbnSmsReadySendList.get(i);
						switch(temp.getSendResult()){
						case -1: smsSendResult.put("cancel", smsSendResult.get("cancel")+1); break;
						case 0: smsSendResult.put("waiting", smsSendResult.get("waiting")+1); break;
						case 1: smsSendResult.put("sending", smsSendResult.get("sending")+1); break;
						case 2: smsSendResult.put("success", smsSendResult.get("success")+1); break;
						case 3: smsSendResult.put("failure", smsSendResult.get("failure")+1); break;
						}
					}
				}
				if(mbnSmsHadSendList!=null&& !mbnSmsHadSendList.isEmpty() ){
					smsSendResult.put("totails", mbnSmsHadSendList.size());
					for(int i = 0; i < mbnSmsHadSendList.size(); i++){
						MbnSmsHadSend temp = mbnSmsHadSendList.get(i);
						switch(temp.getSendResult()){
						case -1: smsSendResult.put("cancel", smsSendResult.get("cancel")+1); break;
						case 0: smsSendResult.put("waiting", smsSendResult.get("waiting")+1); break;
						case 1: smsSendResult.put("sending", smsSendResult.get("sending")+1); break;
						case 2: smsSendResult.put("success", smsSendResult.get("success")+1); break;
						case 3: smsSendResult.put("failure", smsSendResult.get("failure")+1); break;
						}
					}
				}
			}catch(Exception e){
				LOG.error(e.getMessage());
			}
		}
		entityMap.put("result", smsSendResult);
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询日程提醒详细信息
	 * @return
	 */
	@Action(value="editByResult",	
			results = { @Result(name = SUCCESS, location = "/sms/calendar/jsp/calendar_write.jsp"),
					@Result(name = ERROR, location = "/error.jsp")})
	public String modifyCalendarByResult(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{ 
				MbnSmsReadySend mbnSmsReadySend=mbnSmsReadySendService.mbnSmsReadySendByPk(Long.parseLong(selectedId));
				
				this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(mbnSmsReadySend.getContent()));
				this.getRequest().setAttribute("title", mbnSmsReadySend.getTitle());
				this.getRequest().setAttribute("receiver", mbnSmsReadySend.getTos());
//				this.getRequest().setAttribute("sendType", ""+mbnSmsDuanxinrili.getRemindway());
//				this.getRequest().setAttribute("selectCycle", ""+mbnSmsDuanxinrili.getCycletype());
//				this.getRequest().setAttribute("selectCycleHour", ""+mbnSmsDuanxinrili.getRemindhour());
//				this.getRequest().setAttribute("selectCycleMinutes", ""+mbnSmsDuanxinrili.getRemindminutes());
				
				Users loginUser = (Users) super.getSession().getAttribute(
						ApSmsConstants.SESSION_USER_INFO);
				MasCommonFunction masCommonFunction=null;
				MasSmsBean masSmsBean=new MasSmsBean();
				initMasSmsBean(masSmsBean,loginUser,null);
				masCommonFunction=new MasCommonFunction(masSmsBean);
				
				List<SmsMbnTunnelVO>  tunnelList = masCommonFunction.getTunnelList(loginUser.getMerchantPin());
				this.getRequest().setAttribute("tunnelList", tunnelList);
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		return result;
	}
	
	
	/**
	 * 根据ID查询日程提醒详细信息
	 * @return
	 */
	@Action(value="edit",	
			results = { @Result(name = SUCCESS, location = "/sms/calendar/jsp/calendar_write.jsp"),
					@Result(name = ERROR, location = "/error.jsp")})
	public String modifyCalendar(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{ 
				MbnSmsDuanxinrili mbnSmsDuanxinrili=masSmsCalendarService.getMbnSmsDuanxinriliById(Long.parseLong(selectedId));
				this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(mbnSmsDuanxinrili.getContent()));
				this.getRequest().setAttribute("title", mbnSmsDuanxinrili.getTitle());
				this.getRequest().setAttribute("receiver", mbnSmsDuanxinrili.getTos());
				this.getRequest().setAttribute("sendType", ""+mbnSmsDuanxinrili.getRemindway());
				this.getRequest().setAttribute("selectCycle", ""+mbnSmsDuanxinrili.getCycletype());
				this.getRequest().setAttribute("selectCycleHour", ""+mbnSmsDuanxinrili.getRemindhour());
				this.getRequest().setAttribute("selectCycleMinutes", ""+mbnSmsDuanxinrili.getRemindminutes());
				//selectCycleHour
				
				//this.getRequest().setAttribute("sendType", arg1);
				Users loginUser = (Users) super.getSession().getAttribute(
						ApSmsConstants.SESSION_USER_INFO);
				MasCommonFunction masCommonFunction=null;
				MasSmsBean masSmsBean=new MasSmsBean();
				initMasSmsBean(masSmsBean,loginUser,null);
				masCommonFunction=new MasCommonFunction(masSmsBean);
				
				List<SmsMbnTunnelVO>  tunnelList = masCommonFunction.getTunnelList(loginUser.getMerchantPin());
				this.getRequest().setAttribute("tunnelList", tunnelList);
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		return result;
	}
	
	/**
	 * 初始化实例对象
	 * @param masSmsBean
	 * @param loginUser
	 */
	private void initMasSmsBean(MasSmsBean masSmsBean,Users loginUser,Set<Contacts> userSet){
		masSmsBean.setTitle(title);
		masSmsBean.setSmsText(smsText);
		masSmsBean.setYdTunnel(ydTunnel);
		masSmsBean.setTdTunnel(tdTunnel);
		masSmsBean.setEntSign(entSign);
		masSmsBean.setReplyText(replyText);
		masSmsBean.setFlag(flag);
		masSmsBean.setReplyCode(replyCode);
		masSmsBean.setOperationType(ApSmsConstants.OPERATION_CODING_TYPE_TX);
		masSmsBean.setCodeType(ApSmsConstants.OPERATION_CODING_TX);//提醒
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
	private String title;
	private String receiver;
	private String smsText;
	private String sendType;
	private String flag; 
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
	private String smsIds;
	private HashMap<String,Integer> smsSendResult;
	public HashMap<String, Integer> getSmsSendResult() {
		return smsSendResult;
	}

	public void setSmsSendResult(HashMap<String, Integer> smsSendResult) {
		this.smsSendResult = smsSendResult;
	}

	public String getSmsIds() {
		return smsIds;
	}

	public void setSmsIds(String smsIds) {
		this.smsIds = smsIds;
	}
	//周期提醒参数
	private String selectCycle;
	private String selectCycleMonth;
	private String selectCycleWeek;
	private String selectCycleHour;
	private String selectCycleMinutes;
	
	
	//回执查询
	private String replyMobile;
	private String replyName;
	
	private Integer remindCounts;
	
	
	public String getReplyMobile() {
		return replyMobile;
	}

	public void setReplyMobile(String replyMobile) {
		this.replyMobile = replyMobile;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	private static List<String> weekList=new ArrayList<String>();
	static{
		weekList.add("周一");
		weekList.add("周二");
		weekList.add("周三");
		weekList.add("周四");
		weekList.add("周五");
		weekList.add("周六");
		weekList.add("周日");
		
	}
	public String getSelectCycle() {
		return selectCycle;
	}

	public void setSelectCycle(String selectCycle) {
		this.selectCycle = selectCycle;
	}

	public String getSelectCycleMonth() {
		selectCycleMonth=selectCycleMonth.substring(0,selectCycleMonth.indexOf("号"));
		return selectCycleMonth;
	}

	public void setSelectCycleMonth(String selectCycleMonth) {
		this.selectCycleMonth = selectCycleMonth;
	}

	public String getSelectCycleWeek() {
		for(int i=0;i<weekList.size();i++){
			if(weekList.get(i).equals(selectCycleWeek)){
				selectCycleWeek=""+(i+1);
			}
		}
		return selectCycleWeek;
	}

	public void setSelectCycleWeek(String selectCycleWeek) {
		this.selectCycleWeek = selectCycleWeek;
	}

	public String getSelectCycleHour() {
		return selectCycleHour;
	}

	public void setSelectCycleHour(String selectCycleHour) {
		this.selectCycleHour = selectCycleHour;
	}

	public String getSelectCycleMinutes() {
		return selectCycleMinutes;
	}

	public void setSelectCycleMinutes(String selectCycleMinutes) {
		this.selectCycleMinutes = selectCycleMinutes;
	}
	private List list;
	
	
	
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

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
	public Long getLtTunnel() {
		return ltTunnel;
	}
	public void setLtTunnel(Long ltTunnel) {
		this.ltTunnel = ltTunnel;
	}
	public Long getDxTunnel() {
		return dxTunnel;
	}
	public void setDxTunnel(Long dxTunnel) {
		this.dxTunnel = dxTunnel;
	}
	public Long getTdTunnel() {
		return tdTunnel;
	}
	public void setTdTunnel(Long tdTunnel) {
		this.tdTunnel = tdTunnel;
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
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	public Integer getRemindCounts() {
		return remindCounts;
	}

	public void setRemindCounts(Integer remindCounts) {
		this.remindCounts = remindCounts;
	}

	/**
	 * 检查传入的参数是否合法
	 * @return
	 */
	private String checkParameters(){
		String checkParameters=null;
		if(StringUtils.isBlank(title)){
			checkParameters=ERROR_PARA_CHECK_ERR;
		}else if(StringUtils.isBlank(smsText)){
			checkParameters=ERROR_PARA_CHECK_ERR;
		}
		return checkParameters;
	}
	
	/**
	 * 获取号码文件中的信息
	 * @return
	 */
	private Set<Contacts> getUploadContacts(List<String> colsList,Set<Contacts> userSet){
		Set<Contacts> userTmpSet =null;
		String filename = getAddrUploadFileName();
		String storepath = this.getServletContext().getRealPath("/");
		String[][] fileContent = ContactsUtil.getContactsArrayFromFile(getAddrUpload(), filename, storepath);
		int nameIndex=-1;
		userTmpSet=new HashSet<Contacts>();
		if( fileContent != null){
			for(int i=0; i< fileContent.length; i++){
				if(i == 0 ){
					// 取列头
					for(int k=0; k< fileContent[0].length; k++){
						colsList.add(fileContent[0][k]);
						if( -1 != fileContent[0][k].indexOf("姓名")){
							// 找客户姓名列
							nameIndex=k;
						}
					}
					continue;
				}
				Contacts c = new Contacts();
				// 从第二行遍历数据，因为第一行是列头
				for(int j=0; j<fileContent[i].length; j++){
					c.setMobile(fileContent[i][0]);
					if(-1 != nameIndex){
						c.setName(fileContent[i][nameIndex]);	// 客户姓名
					}
					c.put(fileContent[0][j], fileContent[i][j]);
					userTmpSet.add(c);
				}
			}
		}
		if(userSet!=null){
			for(Contacts contacts:userSet){
				userTmpSet.add(contacts);
			}
		}
		return userTmpSet;
	}
	
	

}
