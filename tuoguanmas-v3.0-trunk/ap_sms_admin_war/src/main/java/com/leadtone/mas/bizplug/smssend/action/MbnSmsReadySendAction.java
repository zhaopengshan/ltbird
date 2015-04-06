package com.leadtone.mas.bizplug.smssend.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.DateUtil;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendVO;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
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
import org.apache.commons.lang3.StringUtils;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/mbnSmsReadySendAction")
public class MbnSmsReadySendAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private MbnSmsReadySendService mbnSmsReadySendService;
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
	
	private MbnSmsReadySend mbnSmsReadySend;
	private MbnSmsReadySendVO mbnSmsReadySendVO;
	private List<MbnSmsReadySend> smsReadySendList;
	private HashMap<String,Integer> smsSendResult;
	private String batchId;
	
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
				if(!StringUtil.isEmpty(searchBycontacts)){
					pageUtil.setContactName(URLDecoder.decode(searchBycontacts,"UTF-8"));
				}
				if(!StringUtil.isEmpty(sendResult)&&!sendResult.equals("4")){
					pageUtil.setSendResult(Integer.valueOf(sendResult));
				}
				Page page = mbnSmsReadySendService.batchPage(pageUtil);
				if( page != null ){
					@SuppressWarnings("unchecked")
					List<MbnSmsReadySend> datas = (List<MbnSmsReadySend>) page.getData();
					entityMap = new HashMap<String, Object>();
		            entityMap.put("total", page.getRecords());
		            if( datas == null ){
		            	datas = new ArrayList<MbnSmsReadySend>();
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
			results = { @Result(name = SUCCESS, location = "/sms/smssend/jsp/readysend_result_details.jsp"),
						@Result(name = ERROR, location = "/error.jsp")})
	public String getBatchSms(){
		if( !StringUtil.isEmpty(batchId)){
			try{
				Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
				//Long batchId, Long mPin, Long createBy
				//设定只查询自己发送的信息
                 boolean isQuerySelf = false;
                 Long createBy = null;
                 if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                     isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                     if(isQuerySelf && u.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                    	 createBy = u.getId();
                     }
                 }
				
				smsReadySendList = mbnSmsReadySendService.getByBatchId(Long.valueOf(batchId), u.getMerchantPin(), createBy);
				if( !smsReadySendList.isEmpty() ){
					smsSendResult = new HashMap<String,Integer>();
					smsSendResult.put("success", 0);
					smsSendResult.put("failure", 0);
					smsSendResult.put("sending", 0);
					smsSendResult.put("cancel", 0);
					smsSendResult.put("waiting", 0);
					smsSendResult.put("totails", smsReadySendList.size());
					for(int i = 0; i < smsReadySendList.size(); i++){
						MbnSmsReadySend temp = smsReadySendList.get(i);
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
	results = { @Result(name = SUCCESS, location = "/sms/smssend/jsp/readysend_content.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String followPage(){
		if( !StringUtil.isEmpty(selectedId) && pageDirect != null){
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
				List<MbnSmsReadySendVO> smsList = mbnSmsReadySendService.followPage(followPage);
				if( !smsList.isEmpty() ){
					mbnSmsReadySendVO = smsList.get(0);
				}else{
					mbnSmsReadySendVO = mbnSmsReadySendService.queryByPk(Long.valueOf(selectedId));
					hasFollow = false;
				}
				
				//Long batchId, Long mPin, Long createBy
				//设定只查询自己发送的信息
                 boolean isQuerySelf = false;
                 Long createBy = null;
                 if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                     isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                     if(isQuerySelf && u.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                    	 createBy = u.getId();
                     }
                 }
				smsReadySendList = mbnSmsReadySendService.getByBatchId(mbnSmsReadySendVO.getBatchId(), u.getMerchantPin(), createBy);
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
	results = { @Result(name = SUCCESS, location = "/sms/smssend/jsp/readysend_content.jsp"),
				@Result(name = "refresh", type="redirectAction", location = "../mbnSmsHadSendAction/getSmsDetails.action", 
						params={"selectedId","${selectedId}"}),
				@Result(name = ERROR, location = "/error.jsp")})
	public String getSmsDetails(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{
				mbnSmsReadySendVO = mbnSmsReadySendService.queryByPk(Long.parseLong(selectedId));
				if( mbnSmsReadySendVO == null ){
					return "refresh";
				}
				Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
				//Long batchId, Long mPin, Long createBy
				//设定只查询自己发送的信息
                 boolean isQuerySelf = false;
                 Long createBy = null;
                 if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                     isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                     if(isQuerySelf && u.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                    	 createBy = u.getId();
                     }
                 }
				smsReadySendList = mbnSmsReadySendService.getByBatchId(mbnSmsReadySendVO.getBatchId(), u.getMerchantPin(), createBy);
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		return result;
	}
	/**
	 * 收藏收件箱短信，置标志 为 2 收藏
	 * @return
	 */
	@Action(value="cancelSendByIds", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String cancelSendByIds(){
		Users loginUser = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		entityMap = new HashMap<String, Object>();
		if( !StringUtil.isEmpty(smsIds) && loginUser != null){
			String[] smsTemp = smsIds.split(",");
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			try{
				List<Long> smsBatchIdsList = mbnSmsReadySendService.getBatchIdsByPks(smsTemp);
				if( !smsBatchIdsList.isEmpty() ){
					HashMap<String,Object> smsPro = new HashMap<String,Object>();
					smsPro.put("sendResult", ApSmsConstants.SMS_READY_STATE);
					smsPro.put("batchIds", smsBatchIdsList);
					List<MbnSmsReadySend> smsCancelList = mbnSmsReadySendService.getByBatchIds(smsPro);
					if( !smsCancelList.isEmpty()){
						ListIterator<MbnSmsReadySend> smsCancelIterator = smsCancelList.listIterator();
						while(smsCancelIterator.hasNext()){
							MbnSmsReadySend smsCancel = smsCancelIterator.next();
							smsCancel.setSendResult(ApSmsConstants.SMS_CANCEL_STATE);
							smsCancel.setCompleteTime(currentTime);
							smsCancel.setFailReason("取消发送");
						}
						mbnSmsReadySendService.batchUpdateByList(smsCancelList);
						entityMap.put("resultcode", "success" );
				        entityMap.put("message", "取消发送成功！");
				        return SUCCESS;
					}
				}
			}catch( Exception e ){
				e.printStackTrace();
			}
		}
		entityMap.put("resultcode", "error" );
        entityMap.put("message", "取消发送失败！");
		return SUCCESS;
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
				List<MbnSmsReadySend> smsInList = mbnSmsReadySendService.getByPks(ids);
				List<MbnSmsSelected> smsSelectedList = new ArrayList<MbnSmsSelected>();
				ListIterator<MbnSmsReadySend> smsInIterator = smsInList.listIterator();
				while(smsInIterator.hasNext()){
					MbnSmsReadySend tempInSms = smsInIterator.next();
					MbnSmsSelected smsSelected = new MbnSmsSelected();
					smsSelected.setId(PinGen.getSerialPin());
					smsSelected.setContent(tempInSms.getContent());
					smsSelected.setCreateBy(loginUser.getId());
					smsSelected.setCreateTime(currentTime);
					smsSelected.setMerchantPin(loginUser.getMerchantPin());
					smsSelectedList.add(smsSelected);
				}
				mbnSmsSelectedService.batchSaveByList(smsSelectedList);
			}catch( Throwable e ){
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
	 * @param page,rows,...
	 * @description 提供查询分页/模糊查询分页
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "listSmsReadySend", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String listSmsReadySend(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setStart(page);
		pageUtil.setPageSize(rows);
		try{
			MbnSmsOperationClass smsOperationClass = mbnSmsOperationClassService.findByCoding(ApSmsConstants.SMS_OPERATION_CODE_HD);
			pageUtil.setOperationId(smsOperationClass.getId());
			Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
                        //设定只查询自己发送的信息
                        boolean isQuerySelf = false;
                        if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                            isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                            if(isQuerySelf && u.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                                pageUtil.setCreateBy(u.getId());
                            }
                        }
			pageUtil.setMerchantPin(u.getMerchantPin());
			if(!StringUtil.isEmpty(searchBycontacts)){
				pageUtil.setContactName(URLDecoder.decode(searchBycontacts,"UTF-8"));
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
			Page page = mbnSmsReadySendService.pageVO(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MbnSmsReadySendVO> datas = (List<MbnSmsReadySendVO>) page.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", page.getRecords());
	            if( datas == null ){
	            	datas = new ArrayList<MbnSmsReadySendVO>();
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
	/**
	 * 分页查询回复信息
	 * @return entityMap、contentType
	 */
	@Action(value = "getReplyInfo",
			results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String replyInfo(){
		if(entityMap==null)
			entityMap = new HashMap<String, Object>();
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
			Page m=mbnSmsReadySendService.replyPage(pageUtil); 
			if(m!=null){
				@SuppressWarnings("unchecked")
				List<MbnSmsReadySendVO> mshs=(List<MbnSmsReadySendVO>) m.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", m.getRecords());
	            if( mshs == null ){
	            	mshs = new ArrayList<MbnSmsReadySendVO>();
	            }
	            entityMap.put("rows", mshs); 
	            entityMap.put("totalrecords", m.getTotal());
	            entityMap.put("currpage", m.getStart());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	} 
	
	/**
	 * 根据ID查询短信详细
	 * @return
	 */
	@Action(value = "edit",
	results = { @Result(name = SUCCESS, location = "/sms/smssend/jsp/sms_write.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String editSms(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{
				mbnSmsReadySendVO = mbnSmsReadySendService.queryByPk(Long.parseLong(selectedId));
				this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(mbnSmsReadySendVO.getContent()));
				this.getRequest().setAttribute("title", mbnSmsReadySendVO.getTitle());
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
	 * 待发箱导出列表
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
			//设定只查询自己发送的信息
            boolean isQuerySelf = false;
            if(!StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO))){
                isQuerySelf = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.QUERYSELFINFO));
                if(isQuerySelf && u.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL){
                	pageUtil.setCreateBy(u.getId());
                }
            }
			if(!StringUtil.isEmpty(searchBycontacts)){
				pageUtil.setContactName(URLDecoder.decode(searchBycontacts,"UTF-8"));
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
			Page page = mbnSmsReadySendService.extPortAll(pageUtil);
			if( page != null && page.getData()!=null){
				@SuppressWarnings("unchecked")
				List<MbnSmsReadySendVO> datas = (List<MbnSmsReadySendVO>) page.getData();
				entityMap = new HashMap<String, Object>();
	            if( datas == null ){
	            	datas = new ArrayList<MbnSmsReadySendVO>();
	            }
	            int size = datas.size();
	            //通道名称
	            String tunnelName="";
	            String[][] billsInArr = new String[size][7];
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            for (int j = 0; j < size; j++) {
	            	MbnSmsReadySendVO mbnSmsReadySend = datas.get(j);
	            	int status = mbnSmsReadySend.getSendResult();
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
	            	
	            	switch (mbnSmsReadySend.getTunnelType()) {
	            		case 1: tunnelName="话机";
	            			break;
	            		case 3: tunnelName="猫池";
	            			break;
            			case 2: tunnelName="移动";
            				break;
            			case 4: tunnelName="联通";
            				break;
            			case 6: tunnelName="电信";
            				break;
            			default:
            				tunnelName="无";
            		}
	            	billsInArr[j][0]=mbnSmsReadySend.getTosName();//用户名称
	            	billsInArr[j][1]=mbnSmsReadySend.getTos();//手机号码 
	            	billsInArr[j][2]=mbnSmsReadySend.getTitle();// 短信标题
	                billsInArr[j][3] = mbnSmsReadySend.getContent();		//  短信内容 
	                billsInArr[j][4] =  smsStatus;	// 状态 -1取消发送,0未发送,1已提交网关,2成功,3失败 
	                billsInArr[j][5] = (null == mbnSmsReadySend.getReadySendTime()  ? "" : sdf.format(mbnSmsReadySend.getReadySendTime()));	// 发送时间
	                billsInArr[j][6] = tunnelName; 
	             // billsInArr[j][3] = ""+mbnSmsReadySend.getContent().length();	//短信长度
		         // billsInArr[j][5] = ""+mbnSmsReadySend.getReplyCount();		//  回复
	               
	            }
	            String[] cols = {"用户名称","用户手机号码","短信标题", "短信内容", "发送状态", "发送时间","发送通道名称"};
	            String downLoadPath = ExportUtil.exportToExcel(getRequest(), "SMS_READY_SEND", cols, billsInArr);
	            entityMap.put("fileName", downLoadPath);
	            entityMap.put("message", "导出"+size+"条待发箱记录成功！");
				entityMap.put("resultcode", SUCCESS);
			}
        }catch(Exception e){
        	entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", ERROR);
			entityMap.put("message", "导出待发箱出错，请稍后再试");
            e.printStackTrace();
            return ERROR;
        }
		return SUCCESS;
	}
	
	/**
	 * 导出待发箱结果列表 
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "exportResult", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String exportResult(){
		//if(!StringUtil.isEmpty(batchId)){
			PageUtil pageUtil = new PageUtil();
			pageUtil.setColumn3("export");	// 用于判断，sql是否分页
			pageUtil.setBatchId(Long.valueOf(batchId));
			try{
				Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
				pageUtil.setMerchantPin(u.getMerchantPin());
				if(!StringUtil.isEmpty(searchBycontacts)){
					pageUtil.setContactName(URLDecoder.decode(searchBycontacts,"UTF-8"));
				}
				if(!StringUtil.isEmpty(sendResult)&&!sendResult.equals("4")){
					pageUtil.setSendResult(Integer.valueOf(sendResult));
				}
				Page page = mbnSmsReadySendService.batchPage(pageUtil);
				if( page != null ){
					@SuppressWarnings("unchecked")
					List<MbnSmsReadySend> datas = (List<MbnSmsReadySend>) page.getData();
					entityMap = new HashMap<String, Object>();
		            if( datas == null ){
		            	datas = new ArrayList<MbnSmsReadySend>();
		            }
		            int size = datas.size();
		            String[][] billsInArr = new String[size][4];
		            for (int j = 0; j < size; j++) {
		            	MbnSmsReadySend mbnSmsReadySend = datas.get(j);
		            	int status = mbnSmsReadySend.getSendResult();
		            	String smsStatus = "";
		            	switch (status) {
						case -1:
							smsStatus = "已取消";
							break;
						case 0:
							smsStatus = "等待发送";
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
		            	billsInArr[j][0] = mbnSmsReadySend.getTos();		// 手机号码
		            	billsInArr[j][1] = 
		            			(mbnSmsReadySend.getTosName() == null || mbnSmsReadySend.getTosName() == ""? 
		            					"(未知)" : mbnSmsReadySend.getTosName());		// 接收人姓名
		                billsInArr[j][2] = smsStatus;	// 状态 -1取消发送,0未发送,1已提交网关,2成功,3失败 
		                billsInArr[j][3] = (mbnSmsReadySend.getFailReason() == null || mbnSmsReadySend.getFailReason() == ""? 
	        					"(无)" : mbnSmsReadySend.getFailReason());	//  失败原因
		            }
		            String[] cols = {"手机号码", "接收人姓名", "发送结果", "失败原因"};
		            String downLoadPath = ExportUtil.exportToExcel(getRequest(), "SMS_READY_SEND_RESULT", cols, billsInArr);
		            entityMap.put("fileName", downLoadPath);
		            entityMap.put("message", "导出"+size+"条待发箱结果记录成功！");
					entityMap.put("resultcode", SUCCESS);
				}
	        }catch(Exception e){
	        	entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", ERROR);
				entityMap.put("message", "导出待发箱结果出错，请稍后再试");
	            e.printStackTrace();
	            return ERROR;
	        }
		//}
			return SUCCESS;
	}
	
	/**
	 * 导出回复列表
	 * @return entityMap、contentType
	 */
	@Action(value = "exportReply",
			results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String exportReply(){
		if(entityMap==null)
			entityMap = new HashMap<String, Object>();
		try{ 
			PageUtil pageUtil=new PageUtil();
			pageUtil.setColumn3("export");	// 用于判断，sql是否分页
			pageUtil.setBatchId(Long.parseLong(batchId));
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
			Page m=mbnSmsReadySendService.extPortAll(pageUtil); 
			if(m!=null){
				@SuppressWarnings("unchecked")
				List<MbnSmsReadySendVO> mshs=(List<MbnSmsReadySendVO>) m.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", m.getRecords());
	            if( mshs == null ){
	            	mshs = new ArrayList<MbnSmsReadySendVO>();
	            }
	            int size = mshs.size();
	            String[][] billsInArr = new String[size][3];
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            for (int j = 0; j <size; j++) {
	            	MbnSmsReadySendVO mbnSmsReadySendVO = mshs.get(j);
	            	billsInArr[j][0] = 
	            			(mbnSmsReadySendVO.getTosName()== null || mbnSmsReadySendVO.getTosName() == ""? 
	            					mbnSmsReadySendVO.getTos(): mbnSmsReadySendVO.getTos()+"<"+mbnSmsReadySendVO.getTosName()+">");		// 回复人
	                billsInArr[j][1] = mbnSmsReadySendVO.getContent();	// 回复内容
	                billsInArr[j][2] = (null == mbnSmsReadySendVO.getReceiveTime()  ? "" : sdf.format( mbnSmsReadySendVO.getReceiveTime()));	// 回复时间
	            }
	            String[] cols = {"回复人", "回复内容", "回复时间"};
	            String downLoadPath = ExportUtil.exportToExcel(getRequest(), "SMS_READY_SEND_REPLY", cols, billsInArr);
	            entityMap.put("fileName", downLoadPath);
	            entityMap.put("message", "导出"+size+"条待发箱回复记录成功！");
				entityMap.put("resultcode", SUCCESS);
			}
        }catch(Exception e){
        	entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", ERROR);
			entityMap.put("message", "导出待发箱回复列表出错，请稍后再试");
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
	
	public MbnSmsReadySend getMbnSmsReadySend() {
		return mbnSmsReadySend;
	}
	public void setMbnSmsReadySend(MbnSmsReadySend mbnSmsReadySend) {
		this.mbnSmsReadySend = mbnSmsReadySend;
	}
	public List<MbnSmsReadySend> getSmsReadySendList() {
		return smsReadySendList;
	}
	public void setSmsReadySendList(List<MbnSmsReadySend> smsReadySendList) {
		this.smsReadySendList = smsReadySendList;
	}
	public HashMap<String, Integer> getSmsSendResult() {
		return smsSendResult;
	}
	public void setSmsSendResult(HashMap<String, Integer> smsSendResult) {
		this.smsSendResult = smsSendResult;
	}

	public MbnSmsReadySendVO getMbnSmsReadySendVO() {
		return mbnSmsReadySendVO;
	}

	public void setMbnSmsReadySendVO(MbnSmsReadySendVO mbnSmsReadySendVO) {
		this.mbnSmsReadySendVO = mbnSmsReadySendVO;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
}
