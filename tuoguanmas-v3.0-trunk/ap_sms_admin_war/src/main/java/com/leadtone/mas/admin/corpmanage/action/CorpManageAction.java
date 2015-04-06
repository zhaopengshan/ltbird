package com.leadtone.mas.admin.corpmanage.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.config.bean.MbnConfigSysDictionary;
import com.leadtone.mas.bizplug.config.service.MbnConfigSysDictionaryService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVipVO;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsumeFlow;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelConsumerVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeFlowService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.DateUtils;

@ParentPackage("json-default")
@Namespace(value = "/corpManageAction")
public class CorpManageAction extends BaseAction {
	private Map<String, Object> entityMap = new HashMap<String, Object>();

	private static Logger logger = Logger.getLogger(CorpManageAction.class.getName());
	private HttpServletRequest request = ServletActionContext.getRequest();
	@Resource
	private MbnMerchantVipIService mbnMerchantVipIService;
	@Resource
	private MbnConfigSysDictionaryService mbnConfigSysDictionaryService;
	@Resource
	private SmsMbnTunnelService smsMbnTunnelService;
	@Resource
	private MbnMerchantTunnelRelationService  mbnMerchantTunnelRelationService;
	@Resource
	private MbnMerchantConsumeFlowService mbnMerchantConsumeFlowService;
	private static final long serialVersionUID = 1L;
	private MbnMerchantVipVO mbnMerchantVipVO;
	private PageUtil pageUtil;
	private List<MbnConfigSysDictionary> provinceList;
	private SmsMbnTunnelConsumerVO smsMbnTunnelConsumerVO;
	private MbnMerchantTunnelRelation mbnMerchantTunnelRelation;
	private MbnMerchantConsumeFlow mbnMerchantConsumeFlow;
	private Float chargePrice;//交易金额
	private String dateFrom;
	private String dateTo;
	private String operationType;
	private String merchantPin;
	/**
	 * 查询商户信息  分页查询
	 * @return
	 * @throws Exception
	 */
	@Action(value = "queryMerchant", results = {@Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" })})
	public String queryMerchant(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setStart(page);
		pageUtil.setPageSize(rows);
		try{
			if(mbnMerchantVipVO != null){
				if(mbnMerchantVipVO.getName()!=null&& !mbnMerchantVipVO.getName().equals("")){
					pageUtil.setName(URLDecoder.decode(mbnMerchantVipVO.getName(),"UTF-8"));
				}
				if(mbnMerchantVipVO.getUser()!=null){
					if(mbnMerchantVipVO.getUser().getMobile()!=null&&!mbnMerchantVipVO.getUser().getMobile().equals("")){
						pageUtil.setMobile(URLDecoder.decode(mbnMerchantVipVO.getUser().getMobile(),"UTF-8"));
					}
				}
			}
			Page page = mbnMerchantVipIService.page(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MbnMerchantVipVO> datas = (List<MbnMerchantVipVO>) page.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", page.getRecords());
	            if( datas == null ){
	            	datas = new ArrayList<MbnMerchantVipVO>();
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
	 * 跳转到更新页面
	 * @return
	 */
	 @Action(value = "preForUpdate", results = { 
			 @Result(name = SUCCESS, location = "/ap/corpmanage/editMerchant.jsp") })
     public String preForUpdate() {
		 try{ 
			 	mbnMerchantVipVO.setName(URLDecoder.decode(mbnMerchantVipVO.getName(),"UTF-8"));
				mbnMerchantVipVO.getUser().setAccount(URLDecoder.decode(mbnMerchantVipVO.getUser().getAccount(), "UTF-8"));
				mbnMerchantVipVO.getUser().setPassword(URLDecoder.decode(mbnMerchantVipVO.getUser().getPassword(),"UTF-8"));
				mbnMerchantVipVO.getUser().setMobile(URLDecoder.decode(mbnMerchantVipVO.getUser().getMobile(),"UTF-8"));
			 	PageUtil pageUtil = new PageUtil();
				pageUtil.setType(String.valueOf(1));
				Page province = mbnConfigSysDictionaryService.page(pageUtil);
				provinceList = (List<MbnConfigSysDictionary>) province.getData();
	            if( provinceList == null ){
	            	provinceList = new ArrayList<MbnConfigSysDictionary>();
	            }
			}catch(Exception e){
				e.printStackTrace();
				logger.info("省份初始化异常:"+e);
				return SUCCESS;
			}
		 return SUCCESS;
	 }
	 /**
	  * 显示全部通道列表
	  * @return
	  */
	 @Action(value = "queryAllTunnel", results = {@Result(type = "json", params = {
				"root", "entityMap", "contentType", "text/html" })})
	public String queryAllTunnel(){
		 PageUtil pageUtil = new PageUtil();
			pageUtil.setStart(page);
			pageUtil.setPageSize(rows);
			try{
				pageUtil.setMerchantPin(mbnMerchantTunnelRelation.getMerchantPin());
				if(!StringUtil.isEmpty(smsMbnTunnelConsumerVO.getName())){
					pageUtil.setName(URLDecoder.decode(smsMbnTunnelConsumerVO.getName(),"UTF-8"));
				}
				if(!StringUtil.isEmpty(String.valueOf(smsMbnTunnelConsumerVO.getState()))&&smsMbnTunnelConsumerVO.getState()!=-1){
					pageUtil.setState(smsMbnTunnelConsumerVO.getState());
				}
				if(!StringUtil.isEmpty(String.valueOf(smsMbnTunnelConsumerVO.getAttribute()))&&smsMbnTunnelConsumerVO.getAttribute()!=-1){
					pageUtil.setAttribute(Long.valueOf(smsMbnTunnelConsumerVO.getAttribute()));
				}
				if(!StringUtil.isEmpty(String.valueOf(smsMbnTunnelConsumerVO.getClassify()))&&smsMbnTunnelConsumerVO.getClassify()!=-1){
					pageUtil.setClassify(Long.valueOf(smsMbnTunnelConsumerVO.getClassify()));
				}
				if(!StringUtil.isEmpty(smsMbnTunnelConsumerVO.getProvince())&&!smsMbnTunnelConsumerVO.getProvince().equals("-1")){
					pageUtil.setProvince(smsMbnTunnelConsumerVO.getProvince());
				}
				Page page = smsMbnTunnelService.pageConsumer(pageUtil);
				if( page != null ){
					@SuppressWarnings("unchecked")
					List<SmsMbnTunnelConsumerVO> datas = (List<SmsMbnTunnelConsumerVO>) page.getData();
					entityMap = new HashMap<String, Object>();
		            entityMap.put("total", page.getRecords());
		            if( datas == null ){
		            	datas = new ArrayList<SmsMbnTunnelConsumerVO>();
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
	  * 为企业充短信
	  * @return
	  */
	 @Action(value = "updateMerchantAndTunnel", results = {@Result(type = "json", params = {
				"root", "entityMap", "contentType", "text/html" })})
	public String updateMerchantAndTunnel(){
		 entityMap=new HashMap<String,Object>();
		 //mbnMerchantTunnelRelation;pin,tunnelid,price
		//mbnMerchantConsumeFlow;operationType,number
		 Users loginUser = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		 this.mbnMerchantTunnelRelationService.corpCharge(mbnMerchantTunnelRelation,mbnMerchantConsumeFlow,entityMap,chargePrice,loginUser);
		 return SUCCESS;
	 }
	 /**
	 * 跳转到用户消费流水页
	 * @return
		*/
		 @Action(value = "preForCorpDetail", results = { 
				 @Result(name = SUCCESS, location = "/ap/corpmanage/merchantDetail.jsp") })
	     public String preForCorpDetail() {
			 try {
				mbnMerchantVipVO.setName(URLDecoder.decode(mbnMerchantVipVO.getName(),"UTF-8"));
				mbnMerchantVipVO.getUser().setAccount(URLDecoder.decode(mbnMerchantVipVO.getUser().getAccount(), "UTF-8"));
				mbnMerchantVipVO.getUser().setPassword(URLDecoder.decode(mbnMerchantVipVO.getUser().getPassword(),"UTF-8"));
				mbnMerchantVipVO.getUser().setMobile(URLDecoder.decode(mbnMerchantVipVO.getUser().getMobile(),"UTF-8"));
			 } catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.info("中文转码异常:"+e);
				return SUCCESS;
			}
			 return SUCCESS;
		 }
	
		 /**
			 * @param page,rows,...
			 * @description 提供查询分页/模糊查询分页
			 * @return entityMap 
			 * @type Json
			 */
			@Action(value = "listTunnelFlow", results = {
					@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
			public String listTunnelFlow(){
				PageUtil pageUtil = new PageUtil();
				pageUtil.setStart(page);
				pageUtil.setPageSize(rows);
				try{
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if(!StringUtil.isEmpty(dateTo)){
						//pageUtil.setEndDate(DateUtils.getTomorrow(format.parse(dateTo)));
						pageUtil.setEndDate(format.parse(dateTo));
					}
					if(!StringUtil.isEmpty(dateFrom)){
						pageUtil.setStartDate(format.parse(dateFrom));
					}
					if(!StringUtil.isEmpty(operationType)&&!operationType.equals("-1")){
						pageUtil.setOperationType(Long.valueOf(operationType));
					}
					if(!StringUtil.isEmpty(merchantPin)){
						pageUtil.setMerchantPin(Long.valueOf(merchantPin));
					}
					Page page = mbnMerchantConsumeFlowService.page(pageUtil);
					if( page != null ){
						@SuppressWarnings("unchecked")
						List<SmsMbnTunnelAccountFlow> datas = (List<SmsMbnTunnelAccountFlow>) page.getData();
						entityMap = new HashMap<String, Object>();
			            entityMap.put("total", page.getRecords());
			            if( datas == null ){
			            	datas = new ArrayList<SmsMbnTunnelAccountFlow>();
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
	@Action(value="forwardCorpList",results={@Result(name=SUCCESS,location="/ap/corpmanage/showMerchantVip.jsp")})	 
	public String forwardAddCorp(){
            return SUCCESS;
        }	 
	public MbnMerchantVipIService getMbnMerchantVipIService() {
		return mbnMerchantVipIService;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public MbnMerchantVipVO getMbnMerchantVipVO() {
		return mbnMerchantVipVO;
	}

	public void setMbnMerchantVipVO(MbnMerchantVipVO mbnMerchantVipVO) {
		this.mbnMerchantVipVO = mbnMerchantVipVO;
	}

	public PageUtil getPageUtil() {
		return pageUtil;
	}

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	public Map<String, Object> getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(Map<String, Object> entityMap) {
		this.entityMap = entityMap;
	}
	public List<MbnConfigSysDictionary> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<MbnConfigSysDictionary> provinceList) {
		this.provinceList = provinceList;
	}
	public MbnConfigSysDictionaryService getMbnConfigSysDictionaryService() {
		return mbnConfigSysDictionaryService;
	}

	public SmsMbnTunnelConsumerVO getSmsMbnTunnelConsumerVO() {
		return smsMbnTunnelConsumerVO;
	}
	public void setSmsMbnTunnelConsumerVO(
			SmsMbnTunnelConsumerVO smsMbnTunnelConsumerVO) {
		this.smsMbnTunnelConsumerVO = smsMbnTunnelConsumerVO;
	}
	public SmsMbnTunnelService getSmsMbnTunnelService() {
		return smsMbnTunnelService;
	}
	public MbnMerchantTunnelRelation getMbnMerchantTunnelRelation() {
		return mbnMerchantTunnelRelation;
	}
	public void setMbnMerchantTunnelRelation(
			MbnMerchantTunnelRelation mbnMerchantTunnelRelation) {
		this.mbnMerchantTunnelRelation = mbnMerchantTunnelRelation;
	}
	public MbnMerchantConsumeFlow getMbnMerchantConsumeFlow() {
		return mbnMerchantConsumeFlow;
	}
	public void setMbnMerchantConsumeFlow(
			MbnMerchantConsumeFlow mbnMerchantConsumeFlow) {
		this.mbnMerchantConsumeFlow = mbnMerchantConsumeFlow;
	}
	public MbnMerchantTunnelRelationService getMbnMerchantTunnelRelationService() {
		return mbnMerchantTunnelRelationService;
	}
	public Float getChargePrice() {
		return chargePrice;
	}
	public void setChargePrice(Float chargePrice) {
		this.chargePrice = chargePrice;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(String merchantPin) {
		this.merchantPin = merchantPin;
	}
	public MbnMerchantConsumeFlowService getMbnMerchantConsumeFlowService() {
		return mbnMerchantConsumeFlowService;
	}

	
	
}
