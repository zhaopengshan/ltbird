package com.leadtone.mas.bizplug.smssend.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.DateUtil;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSendVO;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.service.MbnSmsInboxService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsKeywordsService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsSelectedService;
import com.leadtone.mas.bizplug.smssend.util.ContactsUtil;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ConvertUtil;

@Component("com.leadtone.mas.bizplug.smssend.action.MbnSmsInboxAction")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/mbnSmsInboxAction")
public class MbnSmsInboxAction extends BaseAction {
 
	private static final long serialVersionUID = 1L;
	@Resource
	private MbnSmsInboxService mbnSmsInboxService;
	@Resource
	private MbnSmsSelectedService mbnSmsSelectedService;
	@Resource
	private MbnSmsReadySendService mbnSmsReadySendService;
	@Resource
	private MbnSmsKeywordsService mbnSmsKeywordsService;
	@Resource
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Resource	
	private SmsMbnTunnelService smsMbnTunnelService;
	@Resource
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	private MbnSmsInbox mbnSmsInbox;
	private String smsContent;
	private String contactIds;

	/**
	 * 快速发送
	 * @return
	 */
	@Action(value="fastSend",	
	results = { @Result(name = SUCCESS, location = "/smssend/jsp/sms_write.jsp"),
			@Result(name = ERROR, location = "/error.jsp")})
	public String fastSend(){
		if( selectedId != null){
			this.getRequest().setAttribute("receiver", contactIds);
			try {
				if(smsContent!=null && smsContent.length()>0){
					this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(URLDecoder.decode(smsContent,"UTF-8")));
				}
			} catch (UnsupportedEncodingException e1) {
				// ignore
			}
			this.getRequest().setAttribute("title", "");
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
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询下一条，上一条短信详细
	 * @return
	 */
	@Action(value = "followPage",
	results = { @Result(name = SUCCESS, location = "/smssend/jsp/inbox_content.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String followPage(){
		if( !StringUtil.isEmpty(selectedId) && pageDirect != null ){
			entityMap = new HashMap<String, Object>();
			try {
				HashMap<String,Object> followPage = new HashMap<String, Object>();
				if( pageDirect == 0 ){
					followPage.put("frontPage", Long.valueOf(selectedId));
				}else{
					followPage.put("nextPage", Long.valueOf(selectedId));
				}
				Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
				followPage.put("merchantPin", u.getMerchantPin());
				List<MbnSmsInbox> smsList = mbnSmsInboxService.followPage(followPage);
				if( !smsList.isEmpty() ){
					mbnSmsInbox = smsList.get(0);
					if(mbnSmsInbox.getStatus() != ApSmsConstants.SMS_INBOX_READ ){
						mbnSmsInbox.setStatus(ApSmsConstants.SMS_INBOX_READ);
						mbnSmsInboxService.update(mbnSmsInbox);
					}
				}else{
					mbnSmsInbox = mbnSmsInboxService.queryByPk(Long.valueOf(selectedId));
					hasFollow = false;
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ERROR;
	}
	/**
	 * 根据ID查询短信详细
	 * @return
	 */
	@Action(value = "getSmsDetails",
	results = { @Result(name = SUCCESS, location = "/smssend/jsp/inbox_content.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String getSmsDetails(){
		String result = SUCCESS;
		if( selectedId != null ){
			try{
				mbnSmsInbox = mbnSmsInboxService.queryByPk(Long.parseLong(selectedId));
				mbnSmsInbox.setStatus(ApSmsConstants.SMS_INBOX_READ);
				mbnSmsInboxService.update(mbnSmsInbox);
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		return result;
	}
	/**
	 * 收藏收件箱短信，置标志 为 
	 * @return
	 */
	@Action(value="collectByIds", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String collectByIds(){
		Users loginUser = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		entityMap = new HashMap<String, Object>();
		if(smsIds != null && loginUser != null && !smsIds.equals("")){
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			try{
				String[] stringArr = smsIds.split(",");
				Long[] ids = ConvertUtil.arrStringToLong(stringArr);
				List<MbnSmsInbox> smsInList = mbnSmsInboxService.getByPks(ids);
				List<MbnSmsSelected> smsSelectedList = new ArrayList<MbnSmsSelected>();
				ListIterator<MbnSmsInbox> smsInIterator = smsInList.listIterator();
				while(smsInIterator.hasNext()){
					MbnSmsInbox tempInSms = smsInIterator.next();
					MbnSmsSelected smsSelected = new MbnSmsSelected();
					smsSelected.setId(PinGen.getSerialPin());
					smsSelected.setMerchantPin(loginUser.getMerchantPin());
					smsSelected.setContent(tempInSms.getContent());
					smsSelected.setCreateBy(loginUser.getId());
					smsSelected.setCreateTime(currentTime);
					smsSelectedList.add(smsSelected);
				}
				mbnSmsSelectedService.batchSaveByList(smsSelectedList);
			}catch( Exception e ){
				e.printStackTrace();
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "保存珍藏失败！");
			}
			entityMap.put("resultcode", "success" );
	        entityMap.put("message", "保存珍藏成功！");
		}else{
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", "保存珍藏失败！");
		}
		return SUCCESS;
	}
	/**
	 * 删除收件箱短信
	 * @return
	 */
	@Action(value="deleteByIds", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String deleteByIds(){
		String[] smsIdsArray = smsIds.split(",");
		Long[] smsIds = ConvertUtil.arrStringToLong(smsIdsArray);
		try{
			mbnSmsInboxService.batchDeleteByPks(smsIds);
		}catch( Exception e ){
			e.printStackTrace();
		}
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("message", "删除成功！");
		return SUCCESS;
	}
	
	/**
	 * @param page,rows,...
	 * @description 提供查询分页/模糊查询分页
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "listSmsInbox", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String listSmsInbox(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setStart(page);
		pageUtil.setPageSize(rows);
		try{
			Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
			pageUtil.setMerchantPin(u.getMerchantPin());
			if(!StringUtil.isEmpty(searchBycontacts)){
				pageUtil.setContactName(URLDecoder.decode(searchBycontacts,"UTF-8"));
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
			Page page = mbnSmsInboxService.pageVO(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MbnSmsInbox> datas = (List<MbnSmsInbox>) page.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", page.getRecords());
	            if( datas == null ){
	            	datas = new ArrayList<MbnSmsInbox>();
	            }
	            entityMap.put("rows", datas);
	            entityMap.put("totalrecords", page.getTotal());
	            entityMap.put("currpage", page.getStart());
			}
        }catch(Exception e){
            e.printStackTrace();
            return ERROR;
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
	 		if(!StringUtil.isEmpty(searchBycontacts)){
				pageUtil.setContactName(searchBycontacts);
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
				List<MbnSmsHadSendVO> mshs=(List<MbnSmsHadSendVO>) m.getData();
	 			entityMap = new HashMap<String, Object>();
	            entityMap.put("total", m.getRecords());
	            if( mshs == null ){
	            	mshs = new ArrayList<MbnSmsHadSendVO>();
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
	
	
	
	
	
	
	
	
	
 
	
	/**
	 * 根据ID查询短信详细
	 * @return
	 */
	@Action(value = "edit",
			results = { @Result(name = SUCCESS, location = "/smssend/jsp/sms_write.jsp"),
						@Result(name = ERROR, location = "/error.jsp")})
	public String edit(){
		String result = SUCCESS;
		if( selectedId != null ){
			try{
				mbnSmsInbox = mbnSmsInboxService.queryByPk(Long.parseLong(selectedId));
				this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(mbnSmsInbox.getContent()));
				this.getRequest().setAttribute("title", "");
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
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		return result;
	}
 
	/**
	 * 根据ID查询短信详细
	 * @return
	 */
	@Action(value = "reply",
			results = { @Result(name = SUCCESS, location = "/smssend/jsp/sms_write.jsp"),
						@Result(name = ERROR, location = "/error.jsp")})
	public String reply(){
		String result = SUCCESS;
		if( selectedId != null ){
			try{
				mbnSmsInbox = mbnSmsInboxService.queryByPk(Long.parseLong(selectedId));
				this.getRequest().setAttribute("smsText", "");
				this.getRequest().setAttribute("title", "");
				this.getRequest().setAttribute("receiver", mbnSmsInbox.getSenderMobile());
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		return result;
	}
	
	public MbnSmsInboxService getMbnSmsInboxService() {
		return mbnSmsInboxService;
	}
	public void setMbnSmsInboxService(MbnSmsInboxService mbnSmsInboxService) {
		this.mbnSmsInboxService = mbnSmsInboxService;
	}
	public MbnSmsInbox getMbnSmsInbox() {
		return mbnSmsInbox;
	}
	public void setMbnSmsInbox(MbnSmsInbox mbnSmsInbox) {
		this.mbnSmsInbox = mbnSmsInbox;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getContactIds() {
		return contactIds;
	}
	public void setContactIds(String contactIds) {
		this.contactIds = contactIds;
	}
}
