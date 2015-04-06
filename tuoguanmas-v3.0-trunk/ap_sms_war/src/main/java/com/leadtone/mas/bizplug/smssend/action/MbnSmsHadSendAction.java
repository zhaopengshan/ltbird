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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.DateUtil;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSendVO;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendVO;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.service.MbnSmsHadSendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsInboxService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsSelectedService;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ConvertUtil;

@Component("com.leadtone.mas.bizplug.smssend.action.MbnSmsHadSendAction")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/mbnSmsHadSendAction")
public class MbnSmsHadSendAction extends BaseAction {
	 
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
	
	private MbnSmsHadSend mbnSmsHadSend;
	private MbnSmsHadSendVO mbnSmsHadSendVO; 
	private List<MbnSmsHadSend> smsHadSendList;
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
				if(!StringUtil.isEmpty(searchBycontacts)){
					pageUtil.setContactName(URLDecoder.decode(searchBycontacts,"UTF-8"));
				}
				if(!StringUtil.isEmpty(sendResult) && !sendResult.equals("4")){
					pageUtil.setSendResult(Integer.valueOf(sendResult));
				}
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
			results = { @Result(name = SUCCESS, location = "/smssend/jsp/hadsend_result_details.jsp"),
						@Result(name = ERROR, location = "/error.jsp")})
	public String getBatchSms(){
		if( !StringUtil.isEmpty(batchId)){
			try{
				smsHadSendList = mbnSmsHadSendService.getByBatchId(Long.valueOf(batchId));
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
	results = { @Result(name = SUCCESS, location = "/smssend/jsp/hadsend_content.jsp"),
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
				smsHadSendList =  mbnSmsHadSendService.getByBatchId(mbnSmsHadSendVO.getBatchId());
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
	results = { @Result(name = SUCCESS, location = "/smssend/jsp/hadsend_content.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String getSmsDetails(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{ 
				mbnSmsHadSendVO = mbnSmsHadSendService.queryByPk(Long.parseLong(selectedId));
				smsHadSendList =  mbnSmsHadSendService.getByBatchId(mbnSmsHadSendVO.getBatchId());
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
				mbnSmsHadSendService.updateDel(smsIds);
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
	 		pageUtil.setBatchId((long)Integer.parseInt(batchId));
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
	
	/**
	 * 根据ID查询短信详细
	 * @return
	 */
	@Action(value = "edit",
	results = { @Result(name = SUCCESS, location = "/smssend/jsp/sms_write.jsp"),
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
}
