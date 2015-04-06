package com.leadtone.mas.bizplug.vote.action;

import static com.leadtone.mas.bizplug.common.MasCommonFunction.responseMapByMsg;

import java.text.DecimalFormat;
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
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.MasCommonFunction;
import com.leadtone.mas.bizplug.common.MasSmsBean;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.smssend.util.ContactsUtil;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ExportUtil;
import com.leadtone.mas.bizplug.util.WebUtils;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaodiaocha;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaoxuanxiang;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaojieguo;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteExportList;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteList;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteResult;
import com.leadtone.mas.bizplug.vote.service.MasVoteService;
import com.leadtone.mas.bizplug.common.BaseAction;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;


@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/voteAction")
public class VoteAction  extends BaseAction {
	private static final String PARA_DATA_FORMAT="yyyy-MM-dd HH:mm";
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private MasVoteService masVoteService;
	@Autowired
	private MbnSmsOperationClassService mbnSmsOperationClassService;
	@Autowired
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Autowired
	private MbnThreeHCodeService mbnThreeHCodeService;
	@Autowired
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	@Autowired
	private SmsMbnTunnelService smsMbnTunnelService;
	@Autowired
	private MbnSevenHCodeService mbnSevenHCodeService;
	@Autowired
	private MbnSmsReadySendService mbnSmsReadySendService;
	@Autowired
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	@Autowired
	private PortalUserExtService portalUserExtService;
	
	@Action(value = "writeSms", results = {
			@Result(name = SUCCESS, location = "/sms/vote/jsp/vote_write.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String writeSms() {
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
	@Action(value = "editVote", results = {
			@Result(name = SUCCESS, location = "/sms/vote/jsp/vote_write.jsp"),
			@Result(name = ERROR, location = "/error.jsp")})
	public String editVote() {
		try{
			if(!StringUtil.isEmpty(selectedId)){
				MasSmsToupiaodiaocha vote=masVoteService.queryVoteById(new Long(selectedId));
				this.getRequest().setAttribute("smsText", StringEscapeUtils.escapeJavaScript(vote.getContent()));
				Users loginUser = (Users)super.getSessionUsers();
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
	
	@Action(value = "voteResultList", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String voteResultList() {
		PageUtil pageUtil=new PageUtil();
		pageUtil.setBatchId(id);
		try {
			if(tp_Name!=null && !"".equals(tp_Name))
				pageUtil.setContactName(URLDecoder.decode(tp_Name,"UTF-8"));
			else
				pageUtil.setContactName(null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageUtil.setMobile(tp_Num);
		pageUtil.setStart(page);
		pageUtil.setPageSize(rows);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			if(tp_DateFrom!=null && !"".equals(tp_DateFrom))
				pageUtil.setStartDate(sdf.parse(tp_DateFrom));
			else
				pageUtil.setStartDate(null);
			if(tp_DateTo!=null && !"".equals(tp_DateTo))
				pageUtil.setEndDate(addDate(tp_DateTo,"yyyy-MM-dd"));
			else
				pageUtil.setEndDate(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		Page page=masVoteService.pageResult(pageUtil);
		if( page != null ){
			@SuppressWarnings("unchecked")
			List<MasSmsToupiaojieguo> datas = (List<MasSmsToupiaojieguo>) page.getData();
			entityMap = new HashMap<String, Object>();
            entityMap.put("total", page.getRecords());
            if( datas == null ){
            	datas = new ArrayList<MasSmsToupiaojieguo>();
            }
            entityMap.put("rows", datas);
            entityMap.put("totalrecords", page.getTotal());
            entityMap.put("currpage", page.getStart());
		}
		return SUCCESS;
	}
	@Action(value = "saveVote", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String saveVote() {
		Set<Contacts> userSet =  new HashSet<Contacts>();
		List<String> colsList =  new ArrayList<String>();
		entityMap = new HashMap<String, Object>();
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		if(tos !=""){
			Long createBy = loginUser.getId();
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
					|| loginUser.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				createBy = null;
			}
			userSet = ContactsUtil.getContactsSet(contactsService, getTos(), loginUser.getMerchantPin(),createBy);
		}
		if (getAddrUpload() != null) {
			String filename = getAddrUploadFileName();
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
		Date sendTime = new Date();
		if(getTxi()==1){
			SimpleDateFormat sdf = new SimpleDateFormat(PARA_DATA_FORMAT);
			try {
				sendTime = sdf.parse(getReady_send_time());
				if(sendTime.compareTo(new Date())==-1){
					entityMap=responseMapByMsg("定时发送时间已过期!");
					return SUCCESS;
				}
			} catch (ParseException e) {
				entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", "error" );
			    entityMap.put("message", "定时发送时间错误!");
				return SUCCESS;
			}
		}
		Date startTime = new Date();
		Calendar c=Calendar.getInstance();
		Date date=new Date();
		c.setTime(date);
		c.add(Calendar.MONTH,1); //将当前日期加一个月
		Date endTime = c.getTime();
		if(getVoteTime()==1){
			SimpleDateFormat sdf = new SimpleDateFormat(PARA_DATA_FORMAT);
			try {
				System.out.println(getBegin_time());
				startTime = sdf.parse(getBegin_time());
				endTime = sdf.parse(getEnd_time());
				if(startTime.compareTo(endTime)!=-1){
					entityMap = new HashMap<String, Object>();
					entityMap.put("resultcode", "error" );
				    entityMap.put("message", "开始时间不能大于结束时间!");
					return SUCCESS;
				}
			} catch (ParseException e) {
				e.printStackTrace();
		        entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", "error" );
			    entityMap.put("message", "有效期时间错误!");
				return SUCCESS;
			}
		}
		if(getTxi()==1 && getVoteTime()==1){
			if(sendTime.compareTo(endTime)!=-1){
				entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", "error" );
			    entityMap.put("message", "定时时间不可大于结束时间!");
				return SUCCESS;
			}
		}
		String tempTxt=content;
		
		String[] optionList=new String[optionCount];
		for(int i=1;i<=Integer.valueOf(optionCount);i++){
			optionList[i-1]=this.getRequest().getParameter("optionContent_"+i);
			String optionContent=this.getRequest().getParameter("optionContent_"+i);
			tempTxt+=i+":"+optionContent+";";
		}
		if(!checkRepeat(optionList)){
			entityMap=responseMapByMsg("选项重复!");
			return SUCCESS;
		}
		this.smsText=tempTxt;
		
		try{
			Long id=PinGen.getSerialPin();
			insertVote(id,loginUser,sendTime,startTime,endTime,this.getRequest(),userSet);
			MasCommonFunction masCommonFunction=null;
			MasSmsBean masSmsBean=new MasSmsBean();
			tp_batchId=id;
			initMasSmsBean(masSmsBean,loginUser,userSet);
			masCommonFunction=new MasCommonFunction(masSmsBean);
			
			entityMap = masCommonFunction.makeMbnSmsSendTask(loginUser,sendTime,colsList);
			this.getRequest().setAttribute("entityMap", entityMap);
			List<SmsMbnTunnelVO>  tunnelList = masCommonFunction.getTunnelList(loginUser.getMerchantPin());
			this.getRequest().setAttribute("tunnelList", tunnelList);
		}catch(Exception e){
			LOG.error(e.getMessage());
			entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", "error" );
		    entityMap.put("message", "其他错误!");
			return SUCCESS;
		}
		
		//entityMap = new HashMap<String, Object>();
		//entityMap.put("resultcode", "success" );
        //entityMap.put("message", "提交成功");
        
		return SUCCESS;
	}
	private boolean checkRepeat(String[] array){

		Set<String> set = new HashSet<String>();
		for(String str : array){
			set.add(str);
		}
		if(set.size() != array.length){
			return false;//有重复
		}else{
			return true;//不重复
		}

	}
	private Date addDate(String strDate,String sdf) {
    	Date date=null;
        SimpleDateFormat df = new SimpleDateFormat(sdf);
        try {
            date = df.parse(strDate);
            Calendar canlendar = Calendar.getInstance();
            canlendar.setTime(date);
            canlendar.add(Calendar.DATE, 1);
            date = canlendar.getTime();
        } catch (Exception e) {
        }
        return date;
    }
	@Action(value = "voteList", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String getVoteList() {
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		String searchTitle=null;
		String searchCreateBy=null;
		try {
			if(this.getRequest().getParameter("searchTitle")!=null)
				searchTitle=URLDecoder.decode(this.getRequest().getParameter("searchTitle"),"UTF-8");
			if(this.getRequest().getParameter("searchCreateBy")!=null)
				searchCreateBy=URLDecoder.decode(this.getRequest().getParameter("searchCreateBy"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String searchDateFrom=this.getRequest().getParameter("searchDateFrom")== null?"":(String)this.getRequest().getParameter("searchDateFrom");
		String searchDateTo=this.getRequest().getParameter("searchDateTo")== null?"":(String)this.getRequest().getParameter("searchDateTo");
		String listType=this.getRequest().getParameter("listType");
		PageUtil pageUtil=new PageUtil();
		pageUtil.setColumn1(listType);
		pageUtil.setColumn2(searchTitle);
		pageUtil.setStart(page);
		pageUtil.setOperationId(loginUser.getId());
		pageUtil.setCreateByName(searchCreateBy);
		pageUtil.setPageSize(rows);
		try{
			if(!"".equals(searchDateFrom))
				pageUtil.setStartDate(sdf.parse(searchDateFrom));
			else
				pageUtil.setStartDate(null);
			if(!"".equals(searchDateTo))
				pageUtil.setEndDate(addDate(searchDateTo,"yyyy-MM-dd"));
			else
				pageUtil.setEndDate(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		Page page=masVoteService.page(pageUtil);
		if( page != null ){
			@SuppressWarnings("unchecked")
			List<MasSmsVoteList> datas = (List<MasSmsVoteList>) page.getData();
			entityMap = new HashMap<String, Object>();
            entityMap.put("total", page.getRecords());
            if( datas == null ){
            	datas = new ArrayList<MasSmsVoteList>();
            }
            entityMap.put("rows", datas);
            entityMap.put("totalrecords", page.getTotal());
            entityMap.put("currpage", page.getStart());
		}
   
		return SUCCESS;
	
	}
	@Action(value = "queryTaskNumber", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String getTaskNumber() {
		Users loginUser = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		String code = mbnSmsTaskNumberService.getTaskNumber(loginUser.getMerchantPin(), ApSmsConstants.OPERATION_CODING_TP);
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("code", code);
		return SUCCESS;
	}
	@Action(value = "drawVoteResult", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String drawVoteResult() {
		MasSmsToupiaodiaocha vote=masVoteService.queryVoteById(id);
		List<MasSmsToupiaoxuanxiang> options=masVoteService.getOptionList(id);
		List<MasSmsVoteResult> resultList=new ArrayList();
		
		for(int i=0;i<options.size();i++){
			MasSmsVoteResult result=new MasSmsVoteResult();
			result.setAnswer(options.get(i).getAnswer_content());
			result.setCount(0);
			resultList.add(result);
		}
		
		List<MasSmsToupiaojieguo> results=masVoteService.getVoteResultByID(id);
		
		for(int i=0;i<results.size();i++){
			MasSmsToupiaojieguo result=results.get(i);
			String[] answers=result.getAnswer_content().split(",");
			for(int j=0;j<answers.length;j++){
				if(!"".equals(answers[j])){
					int answer=Integer.valueOf(answers[j]);
					resultList.get(answer-1).setCount(resultList.get(answer-1).getCount()+1);
				}
				
			}
		}
		//if(vote.getEffective_mode()!=1){
		entityMap= new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
		entityMap.put("voteTile", vote.getTitle() );
		entityMap.put("tocalCount", vote.getTos().split(";").length*vote.getMulti_selected_number());
		entityMap.put("voteMode",vote.getEffective_mode());
		entityMap.put("rows", resultList);
		return SUCCESS;
	}
	@Action(value = "deleteVoteResult", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String deleteVoteResult(){
		String[] tempids=smsIds.split(",");
		List<Long> ids = new ArrayList<Long>();
		for(int i=0;i<tempids.length;i++){
			ids.add(new Long(tempids[i]));
		}
		try{
			masVoteService.deleteBatchVoteResult(ids);
		}catch( Exception e ){
			e.printStackTrace();
		}
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("message", "删除成功！");
		return SUCCESS;
	}
	@Action(value = "deleteVote", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String deleteVote(){
		String[] tempids=smsIds.split(",");
		List<Long> ids = new ArrayList<Long>();
		List<Long> lists=new ArrayList<Long>();
		for(int i=0;i<tempids.length;i++){
			ids.add(new Long(tempids[i]));
			
			
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
			
			List<MbnSmsReadySend> list=mbnSmsReadySendService.getByBatchId(new Long(tempids[i]), u.getMerchantPin(), createById);
			for(int j=0;j<list.size();j++){
				lists.add(list.get(j).getId());
			}
		}
		try{
			masVoteService.deleteBatchVote(ids);
			Long [] pks = new Long[lists.size()];
			for(int i=0;i<lists.size();i++){
				pks[i]=lists.get(i);
			}
			mbnSmsReadySendService.batchDeleteByPks(pks);
		}catch( Exception e ){
			e.printStackTrace();
		}
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("message", "删除成功！");
		return SUCCESS;
	}
	@Action(value = "closeVote", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String closeVote(){
		try{
			masVoteService.closeVote(id);
			
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
			List<MbnSmsReadySend> list=mbnSmsReadySendService.getByBatchId(id, u.getMerchantPin(), createById);
			Long[] pks=new Long[list.size()];
			for(int i=0;i<pks.length;i++){
				pks[i]=list.get(i).getId();
			}
			mbnSmsReadySendService.batchDeleteByPks(pks);
		}catch( Exception e ){
			e.printStackTrace();
		}
		entityMap = new HashMap<String, Object>();
		entityMap.put("resultcode", "success" );
        entityMap.put("message", "关闭投票成功！");
		return SUCCESS;
	}
	/**
	 * 导出列表
	 * @return
	 */
	@Action(value = "exportVoteList", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String exportVoteList(){
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		String searchTitle=null;
		String searchCreateBy=null;
		try {
			if(this.getRequest().getParameter("searchTitle")!=null)
				searchTitle=URLDecoder.decode(this.getRequest().getParameter("searchTitle"),"UTF-8");
			if(this.getRequest().getParameter("searchCreateBy")!=null)
				searchCreateBy=URLDecoder.decode(this.getRequest().getParameter("searchCreateBy"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String searchDateFrom=this.getRequest().getParameter("searchDateFrom")== null?"":(String)this.getRequest().getParameter("searchDateFrom");
		String searchDateTo=this.getRequest().getParameter("searchDateTo")== null?"":(String)this.getRequest().getParameter("searchDateTo");
		String listType=this.getRequest().getParameter("listType");
		
		Map<String,Object> paraMap=new HashMap<String,Object>();
		paraMap.put("createby", loginUser.getId());
		paraMap.put("title", searchTitle);
		paraMap.put("createByName", searchCreateBy);
		try{
			if(!"".equals(searchDateFrom))
				paraMap.put("startDate",sdf.parse(searchDateFrom));
			else
				paraMap.put("startDate",null);
			if(!"".equals(searchDateTo))
				paraMap.put("endDate",sdf.parse(searchDateTo));
			else
				paraMap.put("endDate",null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			List<MasSmsVoteExportList> datas=masVoteService.exportVoteList(listType, paraMap);
			if( datas != null ){
				//@SuppressWarnings("unchecked")
				//List<MasSmsVoteList> datas = (List<MasSmsVoteList>) page.getData();
				entityMap = new HashMap<String, Object>();
	            if( datas == null ){
	            	datas = new ArrayList<MasSmsVoteExportList>();
	            }
	            int size = datas.size();
	            String[][] billsInArr = new String[size][8];
	            for (int j = 0; j < size; j++) {
	            	MasSmsVoteExportList vote=datas.get(j);
	            	Map<String,String> tos=getVotePerson(vote.getTos());
	            	if(tos!=null){
	            		billsInArr[j][0]=tos.get("name");
	            		billsInArr[j][1]=tos.get("num");
	            	}else{
	            		billsInArr[j][0]="";
	            		billsInArr[j][1]="";
	            	}
	            	billsInArr[j][2]=vote.getTitle()==null?"无":vote.getTitle();
	            	billsInArr[j][3]=vote.getContent()==null?"无":vote.getContent();
	            	billsInArr[j][4]=vote.getVoteoptions()==null?"无":vote.getVoteoptions();
	            	billsInArr[j][5]=vote.getBegin_time()!=null&&!"".equals(vote.getBegin_time())?sdf.format(vote.getBegin_time()):"";
	            	billsInArr[j][6]=vote.getEnd_time()!=null&&!"".equals(vote.getEnd_time())?sdf.format(vote.getEnd_time()):"";
	            	billsInArr[j][7]=vote.getCreate_by();
	            }
	            
	            String[] cols = {"接收人姓名", "接收人手机号码", "任务名称", "邀请内容", "投票选项","开始时间","结束时间","创建人"};
	            String downLoadPath = ExportUtil.exportToExcel(getRequest(), listType.toUpperCase(), cols, billsInArr);
	            entityMap.put("fileName", downLoadPath);
	            entityMap.put("message", "导出"+size+"条投票成功！");
				entityMap.put("resultcode", SUCCESS);
			}
		}catch(Exception e){
        	entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", ERROR);
			entityMap.put("message", "导出投票出错，请稍后再试");
            e.printStackTrace();
            return ERROR;
        }
		return SUCCESS;
	}
	@Action(value = "exportVoteResultList", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String exportVoteResultList(){
		PageUtil pageUtil=new PageUtil();
		pageUtil.setBatchId(id);
		try {
			if(tp_Name!=null && !"".equals(tp_Name))
				pageUtil.setContactName(URLDecoder.decode(tp_Name,"UTF-8"));
			else
				pageUtil.setContactName(null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageUtil.setMobile(tp_Num);
		pageUtil.setColumn3("export");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			if(tp_DateFrom!=null && !"".equals(tp_DateFrom))
				pageUtil.setStartDate(sdf.parse(tp_DateFrom));
			else
				pageUtil.setStartDate(null);
			if(tp_DateTo!=null && !"".equals(tp_DateTo))
				pageUtil.setEndDate(sdf.parse(tp_DateTo));
			else
				pageUtil.setEndDate(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
		Page page=masVoteService.pageResult(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<MasSmsToupiaojieguo> datas = (List<MasSmsToupiaojieguo>) page.getData();
				entityMap = new HashMap<String, Object>();
	            if( datas == null ){
	            	datas = new ArrayList<MasSmsToupiaojieguo>();
	            }
	            int size = datas.size();
	            String[][] billsInArr = new String[size][4];
	            for (int j = 0; j < size; j++) {
	            	MasSmsToupiaojieguo result=datas.get(j);
	            	billsInArr[j][0]=result.getName()==null?"无":result.getName();
	            	billsInArr[j][1]=result.getMobile()==null?"无":result.getMobile();
	            	billsInArr[j][2]=result.getAnswer_content();
	            	billsInArr[j][3]=sdf.format(result.getCreate_time());
	            }
	            String[] cols = {"回复人姓名", "回复人手机号码", "投票内容", "回复时间"};
	            String downLoadPath = ExportUtil.exportToExcel(getRequest(), "vote_result", cols, billsInArr);
	            entityMap.put("fileName", downLoadPath);
	            entityMap.put("message", "导出"+size+"条投票结果成功！");
				entityMap.put("resultcode", SUCCESS);
			}
		}catch(Exception e){
        	entityMap = new HashMap<String, Object>();
			entityMap.put("resultcode", ERROR);
			entityMap.put("message", "导出投票结果出错，请稍后再试");
            e.printStackTrace();
            return ERROR;
        }
		return SUCCESS;
	}
	private void insertVote(Long id,Users loginUser,Date readySendTime,Date startTime,Date endTime,HttpServletRequest request,Set<Contacts> userSet) throws Exception{
		try{
			MasSmsToupiaodiaocha masSmsToupiaodiaocha=new MasSmsToupiaodiaocha();
			
			masSmsToupiaodiaocha.setId(id);
			masSmsToupiaodiaocha.setTitle(title);
			String tmpTos="";
			for(Object obj:userSet){
				Contacts contact=(Contacts)obj;
				tmpTos+=(contact.getName()==null?contact.getMobile():contact.getName())+"<"+contact.getMobile()+">;";
			}
			masSmsToupiaodiaocha.setTos(tmpTos);
			masSmsToupiaodiaocha.setCommit_time(new Date());
			masSmsToupiaodiaocha.setReady_send_time(readySendTime);
			masSmsToupiaodiaocha.setBegin_time(startTime);
			masSmsToupiaodiaocha.setEnd_time(endTime);
			masSmsToupiaodiaocha.setContent(content);
			masSmsToupiaodiaocha.setCreate_by(loginUser.getId());
			masSmsToupiaodiaocha.setMulti_selected_number(multi_selected_number);
			masSmsToupiaodiaocha.setSupport_repeat(support_repeat);
			masSmsToupiaodiaocha.setEffective_mode(effective_mode);
			masSmsToupiaodiaocha.setNeed_successful_remind(need_successful_remind);
			if(need_successful_remind==1){
				masSmsToupiaodiaocha.setNeed_successful_content(need_successful_content);
			}else{
				masSmsToupiaodiaocha.setNeed_successful_content("");
			}
			masSmsToupiaodiaocha.setNeed_not_permmit_remind(need_not_permmit_remind);
			if(need_not_permmit_remind==1){
				masSmsToupiaodiaocha.setNeed_not_permmit_content(need_not_permmit_content);
			}else{
				masSmsToupiaodiaocha.setNeed_not_permmit_content("");
			}
			masSmsToupiaodiaocha.setNeed_content_error_remind(need_content_error_remind);
			if(need_content_error_remind==1){
				masSmsToupiaodiaocha.setNeed_content_error_content(need_content_error_content);
			}else{
				masSmsToupiaodiaocha.setNeed_content_error_content("");
			}
			masSmsToupiaodiaocha.setTaskNumber(replyCode);
			masSmsToupiaodiaocha.setDeleted(0);
			masVoteService.insertVote(masSmsToupiaodiaocha);
			List<MasSmsToupiaoxuanxiang> options=new ArrayList<MasSmsToupiaoxuanxiang>();
			for(int i=1;i<=optionCount;i++){
				String optionContent=request.getParameter("optionContent_"+i);
				MasSmsToupiaoxuanxiang option=new MasSmsToupiaoxuanxiang();
				option.setId(PinGen.getSerialPin());
				option.setTpdc_id(id);
				option.setAnswer_content(optionContent);
				option.setOrder_number(i+"");
				option.setCreate_by(loginUser.getId());
				options.add(option);
			}
			masVoteService.insertBatchVoteOptions(options);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	private Map<String,String> getVotePerson(String tos){
		if(tos!=null){
			String[] toArray=tos.split(";");
			String names="";
			String nums="";
			for(int i=0;i<toArray.length;i++){
				String to=toArray[i];
				names+=to.substring(0, to.indexOf("<"))+",";
				nums+=to.substring(to.indexOf("<")+1).replace(">", "")+",";
			}
			Map<String,String> result=new HashMap<String,String>();
			result.put("name", names);
			result.put("num", nums);
			return result;
		}
		return null;
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
		masSmsBean.setReplyCode(replyCode);
		masSmsBean.setOperationType(ApSmsConstants.OPERATION_CODING_TYPE_TP);
		masSmsBean.setCodeType(ApSmsConstants.OPERATION_CODING_TP);//提醒
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
	private String tos;
	private String content;
	private int optionCount;
	private int voteTime;
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
	private File addrUpload;
	private Long id;
	private String replyCode;
	private String replyText;
	private String smsText;
	private String entSign;
	private String flag;
	private Long tdTunnel=0L;
	private Long ydTunnel=0L;
	
	private String tp_Name;
	private String tp_Num;
	private String tp_DateFrom;
	private String tp_DateTo;
	private Long tp_batchId=0L;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public int getVoteTime() {
		return voteTime;
	}
	public void setVoteTime(int voteTime) {
		this.voteTime = voteTime;
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
	public MasVoteService getMasVoteService() {
		return masVoteService;
	}
	public void setMasVoteService(MasVoteService masVoteService) {
		this.masVoteService = masVoteService;
	}
	public String getAddrUploadFileName() {
		return addrUploadFileName;
	}
	public void setAddrUploadFileName(String addrUploadFileName) {
		this.addrUploadFileName = addrUploadFileName;
	}
	
	public File getAddrUpload() {
		return addrUpload;
	}
	public void setAddrUpload(File addrUpload) {
		this.addrUpload = addrUpload;
	}
	public Long getTdTunnel() {
		return tdTunnel;
	}
	public void setTdTunnel(Long tdTunnel) {
		this.tdTunnel = tdTunnel;
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
	public Long getYdTunnel() {
		return ydTunnel;
	}
	public void setYdTunnel(Long ydTunnel) {
		this.ydTunnel = ydTunnel;
	}
	public String getTp_Name() {
		return tp_Name;
	}
	public void setTp_Name(String tp_Name) {
		this.tp_Name = tp_Name;
	}
	public String getTp_Num() {
		return tp_Num;
	}
	public void setTp_Num(String tp_Num) {
		this.tp_Num = tp_Num;
	}
	public String getTp_DateFrom() {
		return tp_DateFrom;
	}
	public void setTp_DateFrom(String tp_DateFrom) {
		this.tp_DateFrom = tp_DateFrom;
	}
	public String getTp_DateTo() {
		return tp_DateTo;
	}
	public void setTp_DateTo(String tp_DateTo) {
		this.tp_DateTo = tp_DateTo;
	}
	public Long getTp_batchId() {
		return tp_batchId;
	}
	public void setTp_batchId(Long tp_batchId) {
		this.tp_batchId = tp_batchId;
	}
	
}
