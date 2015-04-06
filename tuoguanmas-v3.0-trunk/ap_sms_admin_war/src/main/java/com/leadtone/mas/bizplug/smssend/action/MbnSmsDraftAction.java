package com.leadtone.mas.bizplug.smssend.action;

import com.leadtone.mas.bizplug.util.WebUtils;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
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
import com.leadtone.mas.bizplug.sms.bean.MbnSmsDraft;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.service.MbnSmsDraftService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsSelectedService;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ConvertUtil;
import com.leadtone.mas.bizplug.util.ExportUtil;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/mbnSmsDraftAction")
public class MbnSmsDraftAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private MbnSmsDraftService mbnSmsDraftService;
	@Resource
	private MbnSmsSelectedService mbnSmsSelectedService;
	@Resource
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Resource	
	private SmsMbnTunnelService smsMbnTunnelService;
	@Resource
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	
	private String title;
	private String smsText;
	private  MbnSmsDraft  mbnSmsDraft;
	private List<MbnSmsDraft> mbnSmsDraftList;
	
	/**
	 * 根据ID查询下一条，上一条短信详细
	 * @return
	 */
	@Action(value = "followPage",
	results = { @Result(name = SUCCESS, location = "/sms/smssend/jsp/draft_content.jsp"),
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
				List<MbnSmsDraft> smsList = mbnSmsDraftService.followPage(followPage);
				if( !smsList.isEmpty() ){
					mbnSmsDraft = smsList.get(0);
				}else{
					mbnSmsDraft = mbnSmsDraftService.queryByPk(Long.valueOf(selectedId));
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
	results = { @Result(name = SUCCESS, location = "/sms/smssend/jsp/draft_content.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String getSmsDetails(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{
				mbnSmsDraft = mbnSmsDraftService.queryByPk(Long.parseLong(selectedId));
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@Action(value = "saveDraft", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String saveDraft() {
		Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		MbnSmsDraft mbnSmsDraft = new MbnSmsDraft();
		mbnSmsDraft.setId(PinGen.getSerialPin());
		mbnSmsDraft.setMerchantPin(u.getMerchantPin());
		mbnSmsDraft.setTitle(title);
		mbnSmsDraft.setContent(smsText);
		mbnSmsDraft.setCreateTime(new Date());
		mbnSmsDraft.setCreateBy(u.getId());
		mbnSmsDraftService.insert(mbnSmsDraft);
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("message", "保存成功！");
		return "success";
	}
	
	/**
	 * 收藏收件箱短信，
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
				List<MbnSmsDraft> smsInList = mbnSmsDraftService.getByPks(ids);
				List<MbnSmsSelected> smsSelectedList = new ArrayList<MbnSmsSelected>();
				ListIterator<MbnSmsDraft> smsInIterator = smsInList.listIterator();
				while(smsInIterator.hasNext()){
					MbnSmsDraft tempInSms = smsInIterator.next();
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
	 * 删除收件箱短信
	 * @return
	 */
	@Action(value="deleteByIds", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String deleteByIds(){
		if(!StringUtil.isEmpty(smsIds)){
			try{
				String[] smsIdsArray = smsIds.split(",");
				Long[] smsIds = ConvertUtil.arrStringToLong(smsIdsArray);
				mbnSmsDraftService.batchDeleteByPks(smsIds);
			}catch( Exception e ){
				e.printStackTrace();
			}
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "success" );
	        entityMap.put("message", "删除成功！");
			
		}else{
			entityMap = new HashMap<String, Object>();
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
	@Action(value = "listSmsDraft", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String listSmsDraft(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setStart(page);
		pageUtil.setPageSize(rows);
		try{
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
			if(!StringUtil.isEmpty(searchBySmsTitle)){
				pageUtil.setSmsTitle(java.net.URLDecoder.decode(searchBySmsTitle,"UTF-8"));
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
			Page page = mbnSmsDraftService.page(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MbnSmsDraft> datas = (List<MbnSmsDraft>) page.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", page.getRecords());
	            if( datas == null ){
	            	datas = new ArrayList<MbnSmsDraft>();
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
	 * 根据ID查询短信详细
	 * @return
	 */
	@Action(value = "edit",
	results = { @Result(name = SUCCESS, location = "/sms/smssend/jsp/sms_write.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String edit(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{
				mbnSmsDraft = mbnSmsDraftService.queryByPk(Long.parseLong(selectedId));
				this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(mbnSmsDraft.getContent()));
				this.getRequest().setAttribute("title", mbnSmsDraft.getTitle());
				Users loginUser = (Users) super.getSession().getAttribute(
						ApSmsConstants.SESSION_USER_INFO);
		        //this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(getSmsText()));
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
	 * 导出
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "export", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String export(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setColumn3("export");	// 用于判断，sql是否分页
		try{
			Users u = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
			pageUtil.setMerchantPin(u.getMerchantPin());
			if(!StringUtil.isEmpty(searchBySmsTitle)){
				pageUtil.setSmsTitle(java.net.URLDecoder.decode(searchBySmsTitle,"UTF-8"));
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
			Page page = mbnSmsDraftService.page(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MbnSmsDraft> datas = (List<MbnSmsDraft>) page.getData();
				if(null == datas){
					datas = new ArrayList<MbnSmsDraft>();
				}
				int size = datas.size();
				entityMap = new HashMap<String, Object>();
				String[][] billsInArr = new String[size][4];
		        for (int j = 0; j < size; j++) {
		        	MbnSmsDraft smsDraft = datas.get(j);
		        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            //billsInArr[j][0] = String.valueOf(smsDraft.getMerchantPin());	// 商户PIN码
		            billsInArr[j][0] = smsDraft.getTitle();		// 短信标题
		            billsInArr[j][1] = smsDraft.getContent();		//  短信内容 
		            billsInArr[j][2] = String.valueOf(smsDraft.getContent().length());	//短信长度
		            billsInArr[j][3] = (null == smsDraft.getCreateTime()  ? "" : sdf.format(smsDraft.getCreateTime()));	// 保存时间
		        }
		        String[] cols = {"短信标题", "短信内容", "短信长度", "保存时间"};
		        String downLoadPath = ExportUtil.exportToExcel(getRequest(), "SMS_DRAFT", cols, billsInArr);
				entityMap.put("fileName", downLoadPath);
				entityMap.put("message", "导出"+ size +"条草稿箱记录成功！");
				entityMap.put("resultcode", SUCCESS);
			}
        }catch(Exception e){
        	entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", ERROR);
			entityMap.put("message", "导出草稿箱出错，请稍后再试");
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
	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	public MbnSmsDraft getMbnSmsDraft() {
		return mbnSmsDraft;
	}
	public void setMbnSmsDraft(MbnSmsDraft mbnSmsDraft) {
		this.mbnSmsDraft = mbnSmsDraft;
	}
	public List<MbnSmsDraft> getMbnSmsDraftList() {
		return mbnSmsDraftList;
	}
	public void setMbnSmsDraftList(List<MbnSmsDraft> mbnSmsDraftList) {
		this.mbnSmsDraftList = mbnSmsDraftList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
