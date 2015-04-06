package com.leadtone.mas.bizplug.meeting.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.DateUtil;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSendVO;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.service.MbnSmsHadSendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsSelectedService;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ConvertUtil;
import com.leadtone.mas.bizplug.util.ExportUtil;
import com.leadtone.mas.bizplug.util.WebUtils;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/meetSmsHadSendAction")
public class MeetSmsHadSendAction extends BaseAction {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MbnSmsHadSendService mbnSmsHadSendService;
	@Resource
	private MbnSmsSelectedService mbnSmsSelectedService;
	@Resource
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Resource	
	private SmsMbnTunnelService smsMbnTunnelService;
	@Resource
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	@Resource
	private MbnSmsOperationClassService mbnSmsOperationClassService;
	@Resource
	private UserService userService;
	
	private MbnSmsHadSend mbnSmsHadSend;
	private MbnSmsHadSendVO mbnSmsHadSendVO; 
	private List<MbnSmsHadSend> smsHadSendList;
	private String createBy;
	/**
	 * 批次短信已发箱 结果统计
	 */
	private HashMap<String,Integer> smsSendResult;
	
	/**
	 * @param page,rows,...
	 * @description 提供查询分页/模糊查询分页
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "listBatchSendResult", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String listBatchSendResult(){
		if(!StringUtil.isEmpty(batchId)){
			PageUtil pageUtil = new PageUtil();
			pageUtil.setStart(page);
			pageUtil.setPageSize(rows);
			pageUtil.setBatchId(Long.valueOf(batchId));
			try{
				Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
				pageUtil.setMerchantPin(u.getMerchantPin());
				if(!StringUtil.isEmpty(receiverName)){
					pageUtil.setReceiverName(URLDecoder.decode(receiverName,"UTF-8"));
				}
				if(!StringUtil.isEmpty(receiveMoble)){
					pageUtil.setReceiveMoble(URLDecoder.decode(receiveMoble,"UTF-8"));
				}
				if(!StringUtil.isEmpty(failReason)){
					pageUtil.setFailReason(URLDecoder.decode(failReason,"UTF-8"));
				}
				if(!StringUtil.isEmpty(sendResult) && !sendResult.equals("4")){
					pageUtil.setSendResult(Integer.valueOf(sendResult));
				}
				pageUtil.setOperationId(mbnSmsOperationClassService.findByCoding(ApSmsConstants.OPERATION_CODING_HY).getId());
				Page page = mbnSmsHadSendService.batchPage(pageUtil);
				if( page != null ){
					@SuppressWarnings("unchecked")
					List<MbnSmsHadSend> datas = (List<MbnSmsHadSend>) page.getData();
					entityMap = new HashMap<String, Object>();
		            entityMap.put("total", page.getRecords());
		            if( datas == null ){
		            	datas = new ArrayList<MbnSmsHadSend>();
		            }
		            entityMap.put("rows", datas);
		            entityMap.put("totalrecords", page.getTotal());
		            entityMap.put("currpage", page.getStart());
				}
	        }catch(Exception e){
	            e.printStackTrace();
	            return ERROR;
	        }
		}
		return SUCCESS;
	}
	@Action(value = "getBatchSms",
			results = { @Result(name = SUCCESS, location = "/sms/meeting/jsp/hadsend_result_details.jsp"),
						@Result(name = ERROR, location = "/error.jsp")})
	public String getBatchSms(){
		if( !StringUtil.isEmpty(batchId)){
			try{
				Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
				//Long batchId, Long mPin, Long createBy
				//设定只查询自己发送的信息
                 boolean isQuerySelf = false;
                 Long createById = null;
                 if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                     isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                     if(isQuerySelf && u.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                    	 createById = u.getId();
                     }
                 }
				smsHadSendList = mbnSmsHadSendService.getByBatchId(Long.valueOf(batchId), u.getMerchantPin(), createById);
				if( !smsHadSendList.isEmpty() ){
					smsSendResult = new HashMap<String,Integer>();
					smsSendResult.put("success", 0);
					smsSendResult.put("failure", 0);
					smsSendResult.put("sending", 0);
					smsSendResult.put("cancel", 0);
					smsSendResult.put("waiting", 0);
					smsSendResult.put("totails", smsHadSendList.size());
					for(int i = 0; i < smsHadSendList.size(); i++){
						MbnSmsHadSend temp = smsHadSendList.get(i);
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
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询下一条，上一条短信详细
	 * @return
	 */
	@Action(value = "followPage",
	results = { @Result(name = SUCCESS, location = "/sms/meeting/jsp/hadsend_content.jsp"),
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
				MbnSmsOperationClass smsOperationClass = mbnSmsOperationClassService.findByCoding(operationId);
				followPage.put("operationId", smsOperationClass.getId());
				if(!StringUtil.isEmpty(batchId)){
					followPage.put("batchId", Long.valueOf(batchId));
				}
				Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
				followPage.put("merchantPin", u.getMerchantPin());
				List<MbnSmsHadSendVO> smsList = mbnSmsHadSendService.followPage(followPage);
				if( !smsList.isEmpty() ){
					mbnSmsHadSendVO = smsList.get(0);
				}else{
					mbnSmsHadSendVO = mbnSmsHadSendService.queryByPk(Long.valueOf(selectedId));
					hasFollow = false;
				}
				//Long batchId, Long mPin, Long createBy
				//设定只查询自己发送的信息
                 boolean isQuerySelf = false;
                 Long createById = null;
                 if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                     isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                     if(isQuerySelf && u.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                    	 createById = u.getId();
                     }
                 }
				smsHadSendList =  mbnSmsHadSendService.getByBatchId(mbnSmsHadSendVO.getBatchId(), u.getMerchantPin(), createById);
				UserVO uv=new UserVO();
				uv.setId(mbnSmsHadSendVO.getCreateBy());
				Users us = this.userService.validateUser(uv);
				if(us != null){
					createBy=us.getName();
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
	results = { @Result(name = SUCCESS, location = "/sms/meeting/jsp/hadsend_content.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String getSmsDetails(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{ 
				mbnSmsHadSendVO = mbnSmsHadSendService.queryByPk(Long.parseLong(selectedId));
				Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
				//Long batchId, Long mPin, Long createBy
				//设定只查询自己发送的信息
                 boolean isQuerySelf = false;
                 Long createById = null;
                 if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                     isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                     if(isQuerySelf && u.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                    	 createById = u.getId();
                     }
                 }
				smsHadSendList =  mbnSmsHadSendService.getByBatchId(mbnSmsHadSendVO.getBatchId(), u.getMerchantPin(), createById);
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		UserVO uv=new UserVO();
		uv.setId(mbnSmsHadSendVO.getCreateBy());
		Users us = this.userService.validateUser(uv);
		if(us != null){
			createBy=us.getName();
		}
		return result;
	}
	
	
	
	/**
	 * 收藏收件箱短信，置标志 为 2 收藏
	 * @return
	 */
	@Action(value="collectByIds", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String collectByIds(){
		Users loginUser = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		entityMap = new HashMap<String, Object>();
		if( !StringUtil.isEmpty(smsIds) && loginUser != null){
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			try{
				String[] stringArr = smsIds.split(",");
				Long[] ids = ConvertUtil.arrStringToLong(stringArr);
				List<MbnSmsHadSend> smsInList = mbnSmsHadSendService.getByPks(ids);
				List<MbnSmsSelected> smsSelectedList = new ArrayList<MbnSmsSelected>();
				ListIterator<MbnSmsHadSend> smsInIterator = smsInList.listIterator();
				while(smsInIterator.hasNext()){
					MbnSmsHadSend tempInSms = smsInIterator.next();
					MbnSmsSelected smsSelected = new MbnSmsSelected();
					smsSelected.setId(PinGen.getSerialPin());
					smsSelected.setContent(tempInSms.getContent());
					smsSelected.setCreateBy(loginUser.getId());
					smsSelected.setCreateTime(currentTime);
					smsSelected.setMerchantPin(loginUser.getMerchantPin());
					smsSelectedList.add(smsSelected);
				}
				mbnSmsSelectedService.batchSaveByList(smsSelectedList);
			}catch( Exception e ){
				e.printStackTrace();
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "收藏失败！");
			}
			entityMap.put("resultcode", "success" );
	        entityMap.put("message", "收藏成功！");
		}else{
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", "收藏失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * "删除"收件箱短信 = 设置隐藏
	 * @return
	 */
	@Action(value="updateByIds", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String updateByIds(){
		entityMap = new HashMap<String, Object>();
		if(!StringUtil.isEmpty(smsIds)){
			try{
				String[] stringArr = smsIds.split(",");
				Long[] ids = ConvertUtil.arrStringToLong(stringArr);
				Set<Long> batchIdSet = new HashSet<Long>();
				List<MbnSmsHadSend> sendList = mbnSmsHadSendService.getByPks(ids);
				if( sendList != null){
					for( MbnSmsHadSend smsSend: sendList){
						batchIdSet.add(smsSend.getBatchId());
					}
				}
				Long[] batchIds = batchIdSet.toArray(new Long[1]);
				Integer result = mbnSmsHadSendService.updateDelByBatchIds(batchIds);
				//mbnSmsHadSendService.updateDel(smsIds);
			}catch( Exception e ){
				e.printStackTrace();
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "删除失败！");
			}
			entityMap.put("resultcode", "success" );
	        entityMap.put("message", "删除成功！");
		}else{
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", "删除失败！");
		}
		return SUCCESS;
	}
	/**
	 * @param page,rows,...
	 * @description 提供查询分页/模糊查询分页
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "listHadSendSms", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String listHadSendSms(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setStart(page);
		pageUtil.setPageSize(rows);
		try{
			MbnSmsOperationClass smsOperationClass = mbnSmsOperationClassService.findByCoding(operationId);
			pageUtil.setOperationId(smsOperationClass.getId());
			Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
			pageUtil.setMerchantPin(u.getMerchantPin());
			if(!StringUtil.isEmpty(searchBycontacts)){
				pageUtil.setCreateByName(URLDecoder.decode(searchBycontacts,"UTF-8"));
			}
			if(!StringUtil.isEmpty(searchBySmsTitle)){
				pageUtil.setSmsTitle(URLDecoder.decode(searchBySmsTitle,"UTF-8"));
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
			Page page = mbnSmsHadSendService.pageVO(pageUtil);
			//List<GroupUtil> groupBy = smsStatusService.groupPage(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MbnSmsHadSendVO> datas = (List<MbnSmsHadSendVO>) page.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", page.getRecords());
	            if( datas == null ){
	            	datas = new ArrayList<MbnSmsHadSendVO>();
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
	 		if(!StringUtil.isEmpty(replyName)){
				pageUtil.setReplyName(replyName);
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
	 		Page m=mbnSmsHadSendService.replyPage(pageUtil); 
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
	@Action(value = "retry",
			results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String retry(){
		String result = SUCCESS;
		entityMap = new HashMap<String, Object>();
		if(!StringUtil.isEmpty(smsIds)){
			try{
				String[] stringArr = smsIds.split(",");
				Long[] ids = ConvertUtil.arrStringToLong(stringArr);
				List<MbnSmsHadSend> smsList = mbnSmsHadSendService.getByPks(ids);
				if(smsList!=null){
					ListIterator<MbnSmsHadSend> smsIterator =  smsList.listIterator();
					while(smsIterator.hasNext()){
						MbnSmsHadSend tempSms = smsIterator.next();
						tempSms.setReadySendTime(new Date());
						tempSms.setCommitTime(new Date());
						tempSms.setSendResult(ApSmsConstants.SMS_READY_STATE);
						tempSms.setFailReason("");
						tempSms.setExpireTime(null);
					}
					mbnSmsHadSendService.batchUpdateByList(smsList);
					entityMap.put("resultcode", "success" );
			        entityMap.put("message", "短信失败重发命令提交成功！请勿短时间内重复点击“重发失败项”按钮。");
				}else{
					entityMap.put("resultcode", "error" );
			        entityMap.put("message", "未找到相关短信信息！");
				}
			}catch(Exception e){
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "短信失败重发命令提交失败！请重试");
				result = ERROR;
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 根据ID查询短信详细
	 * @return
	 */
	@Action(value = "edit",
	results = { @Result(name = SUCCESS, location = "/sms/meeting/jsp/sms_write.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String edit(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{ 
				mbnSmsHadSendVO = mbnSmsHadSendService.queryByPk(Long.parseLong(selectedId));
				this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(mbnSmsHadSendVO.getContent()));
				this.getRequest().setAttribute("title", mbnSmsHadSendVO.getTitle());
				Users loginUser = (Users) super.getSession().getAttribute(
						ApSmsConstants.SESSION_USER_INFO);
				List<SmsMbnTunnelVO>  tunnelList = getTunnelList(loginUser.getMerchantPin());
				this.getRequest().setAttribute("tunnelList", tunnelList);
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		return result;
	}
	
	
	/**
	 * 导出列表
	 * @return
	 */
	@Action(value = "export", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String export(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setColumn3("export");	// 用于判断，sql是否分页
		try{
			MbnSmsOperationClass smsOperationClass = mbnSmsOperationClassService.findByCoding(operationId);
			pageUtil.setOperationId(smsOperationClass.getId());
			Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
			pageUtil.setMerchantPin(u.getMerchantPin());
			if(!StringUtil.isEmpty(searchBycontacts)){
				pageUtil.setCreateByName(URLDecoder.decode(searchBycontacts,"UTF-8"));
			}
			if(!StringUtil.isEmpty(searchBySmsTitle)){
				pageUtil.setSmsTitle(URLDecoder.decode(searchBySmsTitle,"UTF-8"));
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
			Page page = mbnSmsHadSendService.pageVO(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MbnSmsHadSendVO> datas = (List<MbnSmsHadSendVO>) page.getData();
				entityMap = new HashMap<String, Object>();
	            if( datas == null ){
	            	datas = new ArrayList<MbnSmsHadSendVO>();
	            }
	            int size = datas.size();
	            String[][] billsInArr = new String[size][6];
	            for (int j = 0; j < size; j++) {
	            	MbnSmsHadSendVO mbnSmsHadSendVO = datas.get(j);
	            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            	/*int status = mbnSmsHadSendVO.getSendResult();
	            	String smsStatus = "";
	            	switch (status) {
					case -1:
						smsStatus = "取消发送";
						break;
					case 0:
						smsStatus = "未发送";
						break;
					case 1:
						smsStatus = "已提交网关";
						break;
					case 2:
						smsStatus = "成功";
						break;
					case 3:
						smsStatus = "失败";
						break;
					default:
						smsStatus = "无";
						break;
					}*/
	                billsInArr[j][0] = "已发送";		// 已发箱里的状态 就一个    已发送，与页面保持一致
	                billsInArr[j][1] = mbnSmsHadSendVO.getTitle();		// 短信标题
	                billsInArr[j][2] = mbnSmsHadSendVO.getContent();		//  短信内容 
	                billsInArr[j][3] = ""+mbnSmsHadSendVO.getContent().length();	//短信长度
	                billsInArr[j][4] = (null == mbnSmsHadSendVO.getCompleteTime()  ? "" : sdf.format(mbnSmsHadSendVO.getCompleteTime()));	// 发送时间
	                billsInArr[j][5] = ""+mbnSmsHadSendVO.getReplyCount();		//  回复
	            }
	            String[] cols = {"状态", "短信标题", "短信内容", "短信长度", "发送时间", "回复"};
	            String downLoadPath = ExportUtil.exportToExcel(getRequest(), "SMS_HADSEND", cols, billsInArr);
	            entityMap.put("fileName", downLoadPath);
	            entityMap.put("message", "导出"+size+"条已发箱记录成功！");
				entityMap.put("resultcode", SUCCESS);
			}
        }catch(Exception e){
        	entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", ERROR);
			entityMap.put("message", "导出已发箱出错，请稍后再试");
            e.printStackTrace();
            return ERROR;
        }
		return SUCCESS;
	}
	
	/**
	 * 导出已发箱结果
	 * @return
	 */
	@Action(value = "exportResult", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String exportResult(){
		try{ 
			PageUtil pageUtil=new PageUtil();
			pageUtil.setColumn3("export");	// 用于判断，sql是否分页
			pageUtil.setBatchId(Long.valueOf(batchId));
			Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
			pageUtil.setMerchantPin(u.getMerchantPin());
			if(!StringUtil.isEmpty(searchBycontacts)){
				pageUtil.setCreateByName(URLDecoder.decode(searchBycontacts,"UTF-8"));
			}
			if(!StringUtil.isEmpty(sendResult) && !sendResult.equals("4")){
				pageUtil.setSendResult(Integer.valueOf(sendResult));
			}
			Page page = mbnSmsHadSendService.batchPage(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MbnSmsHadSend> datas = (List<MbnSmsHadSend>) page.getData();
				entityMap = new HashMap<String, Object>();
	            if( datas == null ){
	            	datas = new ArrayList<MbnSmsHadSend>();
	            }
	            int size = datas.size();
	            String[][] billsInArr = new String[size][4];
	            for (int j = 0; j < size; j++) {
	            	MbnSmsHadSend mbnSmsHadSend = datas.get(j);
	            	int status = mbnSmsHadSend.getSendResult();
	            	String smsStatus = "";
	            	switch (status) {
					case -1:
						smsStatus = "取消发送";
						break;
					case 0:
						smsStatus = "未发送";
						break;
					case 1:
						smsStatus = "已提交网关";
						break;
					case 2:
						smsStatus = "成功";
						break;
					case 3:
						smsStatus = "失败";
						break;
					default:
						smsStatus = "无";
						break;
					}
	            	billsInArr[j][0] = mbnSmsHadSend.getTos();		// 手机号码
	            	billsInArr[j][1] = 
	            			(mbnSmsHadSend.getTosName() == null || mbnSmsHadSend.getTosName() == ""? 
	            					"(未知)" : mbnSmsHadSend.getTosName());		// 接收人姓名
	                billsInArr[j][2] = smsStatus;	// 状态 -1取消发送,0未发送,1已提交网关,2成功,3失败 
	                billsInArr[j][3] = (mbnSmsHadSend.getFailReason() == null || mbnSmsHadSend.getFailReason() == ""? 
	    					"(无)" : mbnSmsHadSend.getFailReason());	//  失败原因
	            }
	            String[] cols = {"手机号码", "接收人姓名", "发送结果", "失败原因"};
	            String downLoadPath = ExportUtil.exportToExcel(getRequest(), "SMS_HADSEND_RESULT", cols, billsInArr);
	            entityMap.put("fileName", downLoadPath);
	            entityMap.put("message", "导出"+size+"条已发箱发送结果记录成功！");
				entityMap.put("resultcode", SUCCESS);
			}
        }catch(Exception e){
        	entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", ERROR);
			entityMap.put("message", "导出已发箱发送结果出错，请稍后再试");
            e.printStackTrace();
            return ERROR;
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
	
	public MbnSmsHadSend getMbnSmsHadSend() {
		return mbnSmsHadSend;
	}
	public void setMbnSmsHadSend(MbnSmsHadSend mbnSmsHadSend) {
		this.mbnSmsHadSend = mbnSmsHadSend;
	}
	public List<MbnSmsHadSend> getSmsHadSendList() {
		return smsHadSendList;
	}
	public void setSmsHadSendList(List<MbnSmsHadSend> smsHadSendList) {
		this.smsHadSendList = smsHadSendList;
	}

	public HashMap<String, Integer> getSmsSendResult() {
		return smsSendResult;
	}

	public void setSmsSendResult(HashMap<String, Integer> smsSendResult) {
		this.smsSendResult = smsSendResult;
	}
	public MbnSmsHadSendVO getMbnSmsHadSendVO() {
		return mbnSmsHadSendVO;
	}
	public void setMbnSmsHadSendVO(MbnSmsHadSendVO mbnSmsHadSendVO) {
		this.mbnSmsHadSendVO = mbnSmsHadSendVO;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
}
