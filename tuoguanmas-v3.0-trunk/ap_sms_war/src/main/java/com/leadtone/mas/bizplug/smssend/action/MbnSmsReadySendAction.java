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
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.DateUtil;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendVO;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsSelectedService;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ConvertUtil;

@Component("com.leadtone.mas.bizplug.smssend.action.MbnSmsReadySendAction")
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
			results = { @Result(name = SUCCESS, location = "/smssend/jsp/readysend_result_details.jsp"),
						@Result(name = ERROR, location = "/error.jsp")})
	public String getBatchSms(){
		if( !StringUtil.isEmpty(batchId)){
			try{
				smsReadySendList = mbnSmsReadySendService.getByBatchId(Long.valueOf(batchId));
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
	results = { @Result(name = SUCCESS, location = "/smssend/jsp/readysend_content.jsp"),
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
				smsReadySendList = mbnSmsReadySendService.getByBatchId(mbnSmsReadySendVO.getBatchId());
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
	results = { @Result(name = SUCCESS, location = "/smssend/jsp/readysend_content.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String getSmsDetails(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{
				mbnSmsReadySendVO = mbnSmsReadySendService.queryByPk(Long.parseLong(selectedId));
				smsReadySendList = mbnSmsReadySendService.getByBatchId(mbnSmsReadySendVO.getBatchId());
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
			Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
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
	results = { @Result(name = SUCCESS, location = "/smssend/jsp/sms_write.jsp"),
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
