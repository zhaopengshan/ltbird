package com.leadtone.mas.bizplug.dati.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.BaseAction;
import com.leadtone.mas.bizplug.common.MasCommonFunction;
import com.leadtone.mas.bizplug.common.MasSmsBean;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDati;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiTiKu;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiBean;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiResultBean;
import com.leadtone.mas.bizplug.dati.service.MasSmsDatiService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ExportUtil;
import com.leadtone.mas.bizplug.util.WebUtils;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteList;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/masDatiAction")
public class MasDatiAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(MasDatiAction.class);
	private MasSmsDati masSmsDati;
	@Resource
	private MasSmsDatiService masSmsDatiService;
	@Resource(name = "MbnMerchantVipIService")
    private MbnMerchantVipIService mbnMerchantVipIService;
	@Resource(name = "mbnSmsTaskNumberService")
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	@Resource(name = "mbnMerchantTunnelRelationService")
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	
	@Autowired
	private MbnSmsOperationClassService mbnSmsOperationClassService;
	
	@Autowired
	private MbnThreeHCodeService mbnThreeHCodeService;
	
	@Autowired
	private SmsMbnTunnelService smsMbnTunnelService;
	@Autowired
	private MbnSevenHCodeService mbnSevenHCodeService;
	@Autowired
	private MbnSmsReadySendService mbnSmsReadySendService;
	@Autowired
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	private MbnMerchantVip merchant;
	private MasSmsDatiTiKu tiku;
	private String dtId;
	private String createInfo;
	
	
	
	
	

	public String getCreateInfo() {
		return createInfo;
	}

	public void setCreateInfo(String createInfo) {
		this.createInfo = createInfo;
	}

	public String getDtId() {
		return dtId;
	}

	public void setDtId(String dtId) {
		this.dtId = dtId;
	}

	public MasSmsDatiTiKu getTiku() {
		return tiku;
	}

	public void setTiku(MasSmsDatiTiKu tiku) {
		this.tiku = tiku;
	}

	public MbnMerchantVip getMerchant() {
		return merchant;
	}

	public void setMerchant(MbnMerchantVip merchant) {
		this.merchant = merchant;
	}

	private Users loginUser = (Users) super.getSession().getAttribute(com.leadtone.mas.admin.common.ApSmsConstants.SESSION_USER_INFO);
	public MasSmsDati getMasSmsDati() {
		return masSmsDati;
	}

	public void setMasSmsDati(MasSmsDati masSmsDati) {
		this.masSmsDati = masSmsDati;
	}
	
	@Action(value = "pageDatiList", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String paginateDaTiList(){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		
		try{
			
			paraMap.put("startPage", (page - 1) * 20);
			paraMap.put("pageSize", rows);
			paraMap.put("createBy", loginUser.getId());
			paraMap.put("createInfo", StringUtils.trimToNull(URLDecoder.decode(createInfo,"UTF-8")));
			paraMap.put("title", StringUtils.trimToNull(URLDecoder.decode(searchBySmsTitle,"UTF-8")));
			//paraMap.put("startDate", dateFrom);
			//paraMap.put("endDate",dateTo);
			
			entityMap = new HashMap<String, Object>();
			if(log.isDebugEnabled()){
				log.debug("用户的登录信息："+loginUser.getId());
			}
			int totalCount = masSmsDatiService.getMasSmsDatiCountBySearchMap(paraMap);
			List<MasSmsDatiBean> masSmsDatiList =  masSmsDatiService.getMasSmsDatiBeanListBySearchMap(paraMap);
			
			entityMap.put("rows", masSmsDatiList);
			entityMap.put("totalrecords", totalCount);
			entityMap.put("currpage", page);
		}catch(Exception e){
			e.printStackTrace();
		}
        return SUCCESS;
	}
	
	@Action(value = "exportDatiList", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String exportDaTiList(){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String listType=this.getRequest().getParameter("listType");
		
		try{
			
			//paraMap.put("startPage", (page - 1) * 20);
			//paraMap.put("pageSize", rows);
			paraMap.put("createBy", loginUser.getId());
			paraMap.put("createInfo", StringUtils.trimToNull(URLDecoder.decode(createInfo,"UTF-8")));
			paraMap.put("title", StringUtils.trimToNull(URLDecoder.decode(searchBySmsTitle,"UTF-8")));
			//paraMap.put("startDate", dateFrom);
			//paraMap.put("endDate",dateTo);
			
			entityMap = new HashMap<String, Object>();
			if(log.isDebugEnabled()){
				log.debug("用户的登录信息："+loginUser.getId());
			}
			int totalCount = masSmsDatiService.getMasSmsDatiCountBySearchMap(paraMap);
			List<MasSmsDatiBean> masSmsDatiList =  masSmsDatiService.getMasSmsDatiBeanListBySearchMap(paraMap);
			String[][] billsInArr = new String[totalCount][4];
			int record = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(MasSmsDatiBean dati:masSmsDatiList){
				billsInArr[record][0] = StringUtils.trimToEmpty(dati.getTitle());
				billsInArr[record][1] = StringUtils.trimToEmpty(dati.getContent());
				
				billsInArr[record][2] = StringUtils.trimToEmpty(sdf.format(dati.getBeginTime()));
				billsInArr[record][3] = StringUtils.trimToEmpty(sdf.format(dati.getEndTime()));
				record++;
			}
			String[] cols = {"标题", "内容", "开始时间","结束时间"};
            String downLoadPath = ExportUtil.exportToExcel(getRequest(), listType.toUpperCase(), cols, billsInArr);
            entityMap.put("fileName", downLoadPath);
            entityMap.put("message", "导出"+totalCount+"条答题成功！");
            entityMap.put("resultcode", SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
		}
        return SUCCESS;
	}
	
	
	@Action(value = "pageDatiTiKuList", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String paginateDaTiTiKuList(){
		try{
			
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("startPage", (page - 1) * 20);
			paraMap.put("pageSize", rows);
			
			paraMap.put("title", StringUtils.trimToNull(URLDecoder.decode(searchBySmsTitle,"UTF-8")));
			paraMap.put("startDate", StringUtils.trimToNull(dateFrom));
			paraMap.put("endDate",StringUtils.trimToNull(dateTo));
			paraMap.put("createdBy",loginUser.getId());
			
			entityMap = new HashMap<String, Object>();
			//List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getAllTiKuByCreatorId(loginUser.getMerchantPin());
			int totalCount = masSmsDatiService.getAllTiKuCountByCreatorIdAndKeywordAndTime(paraMap);
			List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getAllTiKuListByCreatorIdAndKeywordAndTime(paraMap);
			//List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getSmsDatiTiKuListByKeywordAndCreatorForPage(loginUser.getMerchantPin(),null,null,null,(page - 1) * 20,rows);
			entityMap.put("rows", masSmsDatiTiKuList);
			entityMap.put("totalrecords", totalCount);
			entityMap.put("currpage", page);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	@Action(value = "exportDatiTiKuList", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String exportDaTiTiKuList(){
		try{
			String listType=this.getRequest().getParameter("listType");
			Map<String, Object> paraMap = new HashMap<String, Object>();
			//paraMap.put("startPage", (page - 1) * 20);
			//paraMap.put("pageSize", rows);
			
			paraMap.put("title", StringUtils.trimToNull(URLDecoder.decode(searchBySmsTitle,"UTF-8")));
			paraMap.put("startDate", StringUtils.trimToNull(dateFrom));
			paraMap.put("endDate",StringUtils.trimToNull(dateTo));
			paraMap.put("createdBy",loginUser.getId());
			
			entityMap = new HashMap<String, Object>();
			//List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getAllTiKuByCreatorId(loginUser.getMerchantPin());
			int totalCount = masSmsDatiService.getAllTiKuCountByCreatorIdAndKeywordAndTime(paraMap);
			List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getAllTiKuListByCreatorIdAndKeywordAndTime(paraMap);
			
			//List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getSmsDatiTiKuListByKeywordAndCreatorForPage(loginUser.getMerchantPin(),null,null,null,(page - 1) * 20,rows);
			String[][] billsInArr = new String[totalCount][3];
			int record = 0;
			for(MasSmsDatiTiKu tiku:masSmsDatiTiKuList){
				billsInArr[record][0] = StringUtils.trimToEmpty(tiku.getQuestion());
				billsInArr[record][1] = StringUtils.trimToEmpty(tiku.getAnswer());
				billsInArr[record][2] = tiku.getScore()+"";
				record++;
			} 
            
            
            String[] cols = {"问题", "答案", "分数"};
            String downLoadPath = ExportUtil.exportToExcel(getRequest(), listType.toUpperCase(), cols, billsInArr);
            entityMap.put("fileName", downLoadPath);
            entityMap.put("message", "导出"+totalCount+"条题库成功！");
			entityMap.put("resultcode", SUCCESS);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	@Action(value = "pageDatiResultList", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String paginateDaTiResultList(){
		try{
			
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("startPage", (page - 1) * 20);
			paraMap.put("pageSize", rows);
			
			//paraMap.put("title", StringUtils.trimToNull(URLDecoder.decode(searchBySmsTitle,"UTF-8")));
			if(StringUtils.isNotBlank(searchBySmsTitle)){
				paraMap.put("title", StringUtils.trimToNull(URLDecoder.decode(searchBySmsTitle,"UTF-8")));
			}
			paraMap.put("createBy",loginUser.getId());
			paraMap.put("dxdtId", Long.parseLong(dtId));
			entityMap = new HashMap<String, Object>();
			//List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getAllTiKuByCreatorId(loginUser.getMerchantPin());
			int totalCount = masSmsDatiService.getDatiResultGroupCountBySearchInfo(paraMap);
			List<MasSmsDatiResultBean> masSmsDatiResultList = masSmsDatiService.getDatiResultGroupInfoListBySearchInfo(paraMap);
			//List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getSmsDatiTiKuListByKeywordAndCreatorForPage(loginUser.getMerchantPin(),null,null,null,(page - 1) * 20,rows);
			entityMap.put("rows", masSmsDatiResultList);
			entityMap.put("totalrecords", totalCount);
			entityMap.put("currpage", page);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Action(value = "exportDatiResultList", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String exportDaTiResultList(){
		try{
			String listType=this.getRequest().getParameter("listType");
			Map<String, Object> paraMap = new HashMap<String, Object>();
			
			
			//paraMap.put("title", StringUtils.trimToNull(URLDecoder.decode(searchBySmsTitle,"UTF-8")));
			if(StringUtils.isNotBlank(searchBySmsTitle)){
				paraMap.put("title", StringUtils.trimToNull(URLDecoder.decode(searchBySmsTitle,"UTF-8")));
			}
			paraMap.put("createBy",loginUser.getId());
			paraMap.put("dxdtId", Long.parseLong(dtId));
			entityMap = new HashMap<String, Object>();
			//List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getAllTiKuByCreatorId(loginUser.getMerchantPin());
			int totalCount = masSmsDatiService.getDatiResultGroupCountBySearchInfo(paraMap);
			List<MasSmsDatiResultBean> masSmsDatiResultList = masSmsDatiService.getDatiResultGroupInfoListBySearchInfo(paraMap);
			//List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getSmsDatiTiKuListByKeywordAndCreatorForPage(loginUser.getMerchantPin(),null,null,null,(page - 1) * 20,rows);
			String[][] billsInArr = new String[totalCount][3];
			int record = 0;
			for(MasSmsDatiResultBean bean :masSmsDatiResultList){
				billsInArr[record][0] = bean.getMobile();
				billsInArr[record][1] = StringUtils.trimToEmpty(bean.getName());
				billsInArr[record][2] = bean.getSumScore()+"";
				record++;
			}
			
			String[] cols = {"手机号", "姓名", "分数"};
            String downLoadPath = ExportUtil.exportToExcel(getRequest(), listType.toUpperCase(), cols, billsInArr);
            entityMap.put("fileName", downLoadPath);
            entityMap.put("message", "导出"+totalCount+"条结果成功！");
			entityMap.put("resultcode", SUCCESS);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Action(value = "addTiKu", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String addTiKu(){
		
		
		if(tiku != null){
			entityMap = new HashMap<String, Object>();
			tiku.setCreateBy(loginUser.getId());
			tiku.setCreateTime(new Date());
			try{
				masSmsDatiService.createDatiTiKu(tiku);
				entityMap.put("resultcode", "success" );
				if(tiku.getId() == null){
					entityMap.put("message", "创建成功");
				}else{
					entityMap.put("message", "更新成功");
				}
			}catch(Exception e){
				e.printStackTrace();
				entityMap.put("resultcode", "fail" );
				//entityMap.put("resultcode", "success" );
				if(tiku.getId() == null){
					entityMap.put("message", "创建失败");
				}else{
					entityMap.put("message", "更新失败");
				}
			}
		}
		return SUCCESS;
	}
	
	
	@Action(value = "updateTiKu", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String updateTiKu(){
		if(tiku != null){
			//tiku.setCreateBy(loginUser.getMerchantPin());
			//tiku.setCreateTime(new Date());
			//masSmsDatiService.createDatiTiKu(tiku);
			entityMap = new HashMap<String, Object>();
			masSmsDatiService.updateTiKuShortInfo(tiku.getQuestion(), tiku.getAnswer(), tiku.getScore(), tiku.getId(), loginUser.getId());
			entityMap.put("resultcode", "success" );
			if(tiku.getId() == null){
				entityMap.put("message", "创建成功");
			}else{
				entityMap.put("message", "更新成功");
			}
		}
		return SUCCESS;
	}
	
	
	
	
	@Action(value = "updateDeleteStatusTiKu", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String updateTiKuDeleteStatus(){
		//现在先简单处理 
		for(String id:StringUtils.split(smsIds, ",")){
			masSmsDatiService.updateTiKuDeleteStatus(NumberUtils.toLong(id), loginUser.getId());
		}
		return SUCCESS;
	}
	
	@Action(value = "updateResultDeleteStatus", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String updateResultDeleteStatus(){
		//现在先简单处理 
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createBy",loginUser.getId());
		map.put("mobileList", StringUtils.split(smsIds, ","));
		map.put("dxdtId", dtId);
		try{
			entityMap = new HashMap<String, Object>();
			masSmsDatiService.updateSmsDatiResultDeleteStatus( map);
			entityMap.put("resultcode", "success" );
			entityMap.put("message", "删除成功");
			
		}catch(Exception e){
			e.printStackTrace();
			entityMap.put("resultcode", "fail" );
			entityMap.put("message", "删除失败");
		}
		
		return SUCCESS;
	}
	
	@Action(value = "updateDeleteStatusShiJuan", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String updateShiJuanDeleteStatus(){
		//现在先简单处理 
		masSmsDatiService.updateSmsDatiDeleteStatus( loginUser.getId(),StringUtils.split(smsIds, ","));
		
		return SUCCESS;
	}
	
	@Action(value = "getTiKu", results = {
	        @Result(type = "json", params = {
	            "root", "entityMap", "contentType", "text/html"})})
	public String getTiKuById(){
		entityMap = new HashMap<String, Object>();
		MasSmsDatiTiKu smsDatiTiKu = masSmsDatiService.getTiKuById(NumberUtils.toLong(selectedId), loginUser.getId());
		entityMap.put("info", smsDatiTiKu);
		return SUCCESS;
	}
	
	@Action(value = "writeDatiInfoPage", results = { @Result(name = SUCCESS, location = "/sms/answer/jsp/answer_write.jsp"),
	@Result(name = ERROR, location = "/error.jsp")})
	public String writeDatiPage(){
		//加载题库list
		List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getAllTiKuByCreatorId(loginUser.getId());
		//加载上行编号
		String maxCode = mbnSmsTaskNumberService.getTaskNumber(loginUser.getMerchantPin(),ApSmsConstants.OPERATION_CODING_DT);
		if(log.isDebugEnabled()){
			log.debug("maxCode:"+maxCode);
		}
		
		MasCommonFunction masCommonFunction=null;
		MasSmsBean masSmsBean=new MasSmsBean();
		initMasSmsBean(masSmsBean,loginUser,null);
		masCommonFunction=new MasCommonFunction(masSmsBean);
		
		List<SmsMbnTunnelVO>  tunnelList = masCommonFunction.getTunnelList(loginUser.getMerchantPin());
		this.getRequest().setAttribute("tunnelList", tunnelList);
		this.getRequest().setAttribute("tikuList", masSmsDatiTiKuList);
		if( WebUtils.getExtCodeStyle() == com.leadtone.mas.admin.common.ApSmsConstants.USER_EXT_CODE_TYPE){
			this.getRequest().setAttribute("maxCode", null);
		}else{
			this.getRequest().setAttribute("maxCode", ApSmsConstants.OPERATION_CODING_DT+maxCode.toString());
		}
		return SUCCESS;
	}
	
	@Action(value = "writeDatiInfoById", results = { @Result(name = SUCCESS, location = "/sms/answer/jsp/answer_write.jsp"),
			@Result(name = ERROR, location = "/error.jsp")})
	public String writeDatiPageById(){
		//加载题库list
		List<MasSmsDatiTiKu> masSmsDatiTiKuList = masSmsDatiService.getAllTiKuByCreatorId(loginUser.getId());
		//加载上行编号
		String maxCode = mbnSmsTaskNumberService.getTaskNumber(loginUser.getMerchantPin(),ApSmsConstants.OPERATION_CODING_DT);
		if(log.isDebugEnabled()){
			log.debug("maxCode:"+maxCode);
		}
		
		MasCommonFunction masCommonFunction=null;
		MasSmsBean masSmsBean=new MasSmsBean();
		initMasSmsBean(masSmsBean,loginUser,null);
		masCommonFunction=new MasCommonFunction(masSmsBean);
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("create_by", loginUser.getId());
		searchMap.put("shiJuanId", smsIds);
		MasSmsDatiBean beanInfo = masSmsDatiService.getMasSmsDatiBySearchInfo(searchMap);
		if(beanInfo != null){
			this.getRequest().setAttribute("contentInfo", beanInfo.getContent());
		}
		List<SmsMbnTunnelVO>  tunnelList = masCommonFunction.getTunnelList(loginUser.getMerchantPin());
		this.getRequest().setAttribute("tunnelList", tunnelList);
		this.getRequest().setAttribute("tikuList", masSmsDatiTiKuList);
		if( WebUtils.getExtCodeStyle() == com.leadtone.mas.admin.common.ApSmsConstants.USER_EXT_CODE_TYPE){
			this.getRequest().setAttribute("maxCode", null);
		}else{
			this.getRequest().setAttribute("maxCode", ApSmsConstants.OPERATION_CODING_DT+maxCode.toString());
		}
		return SUCCESS;
	}
	
	private void initMasSmsBean(MasSmsBean masSmsBean,Users loginUser,Set<Contacts> userSet){
		
		masSmsBean.setOperationType(ApSmsConstants.OPERATION_CODING_TYPE_DT);
		masSmsBean.setCodeType(ApSmsConstants.OPERATION_CODING_DT);//
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
	}

}
