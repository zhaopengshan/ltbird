package com.leadtone.mas.bizplug.dati.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.BaseAction;
import com.leadtone.mas.bizplug.common.MasCommonFunction;
import com.leadtone.mas.bizplug.common.MasSmsBean;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDati;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiTiKu;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiTiKuInfo;
import com.leadtone.mas.bizplug.dati.service.MasSmsDatiService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.smssend.util.ContactsUtil;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;

/**
 *@author Chenxuezheng
 *TODO:创建答题试卷 
 * 
 * */


@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/masDatiShiJuanAction")
public class MasDatiShiJuanAction extends BaseAction {

	private static final Log log = LogFactory.getLog(MasDatiShiJuanAction.class);
	@Resource
	private MasSmsDatiService masSmsDatiService;
	
	@Resource(name = "mbnSmsTaskNumberService")
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	
	@Autowired
	private MbnSmsOperationClassService mbnSmsOperationClassService;
	@Autowired
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
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
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private PortalUserExtService portalUserExtService;
	
	//private MasSmsDati masSmsDati;
	//private MasSmsDatiTiKuInfo masSmsDat
	
	private String title;
	private String content;
	private String datiIds;
	private String tos;
	private String datireplyCode;
	private String datiStartTime;
	private String datiEndTime;
	private String entSign;
	private String datireplyText;
	private String ydTunnel;
	private String tdTunnel;
	private String addrUploadFileName;
	private File addrUpload;
	
	
	
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
	public String getYdTunnel() {
		return ydTunnel;
	}
	public void setYdTunnel(String ydTunnel) {
		this.ydTunnel = ydTunnel;
	}
	public String getTdTunnel() {
		return tdTunnel;
	}
	public void setTdTunnel(String tdTunnel) {
		this.tdTunnel = tdTunnel;
	}
	public String getDatireplyText() {
		return datireplyText;
	}
	public void setDatireplyText(String datireplyText) {
		this.datireplyText = datireplyText;
	}
	public String getEntSign() {
		return entSign;
	}
	public void setEntSign(String entSign) {
		this.entSign = entSign;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDatiIds() {
		return datiIds;
	}
	public void setDatiIds(String datiIds) {
		this.datiIds = datiIds;
	}
	public String getTos() {
		return tos;
	}
	public void setTos(String tos) {
		this.tos = tos;
	}
	public String getDatireplyCode() {
		return datireplyCode;
	}
	public void setDatireplyCode(String datireplyCode) {
		this.datireplyCode = datireplyCode;
	}
	public String getDatiStartTime() {
		return datiStartTime;
	}
	public void setDatiStartTime(String datiStartTime) {
		this.datiStartTime = datiStartTime;
	}
	public String getDatiEndTime() {
		return datiEndTime;
	}
	public void setDatiEndTime(String datiEndTime) {
		this.datiEndTime = datiEndTime;
	}
	
	@Action(value = "addShiJuan", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String addDatiShiJuan(){
		Users loginUser = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		try{
			List<String> colsList =  new ArrayList<String>();
			Set<Contacts> userSet =  new HashSet<Contacts>();
			if(tos !=""){
				userSet = ContactsUtil.getContactsSet(contactsService, getTos(), loginUser.getMerchantPin(),loginUser.getId());
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
			entityMap = new HashMap<String, Object>();
			if (userSet.size() == 0) {
				entityMap = new HashMap<String, Object>();
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "无有效接收人！");
		        this.getRequest().setAttribute("entityMap", entityMap);
				return SUCCESS;
			}
			MasSmsDati masSmsDati = new MasSmsDati();
			SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			masSmsDati.setBeginTime(smt.parse(datiStartTime));
			masSmsDati.setEndTime(smt.parse(datiEndTime));
			masSmsDati.setCommitTime(new Date());
			masSmsDati.setContent(content);
			masSmsDati.setCreateBy(loginUser.getId());
			masSmsDati.setTaskNumber(datireplyCode);
			masSmsDati.setTitle(title);
			StringBuilder sb = new StringBuilder();
			for(Contacts contact:userSet){
				sb.append(contact.getMobile()+",");
			}
			if(StringUtils.endsWith(sb.toString(), ",")){
				masSmsDati.setTos(StringUtils.removeEndIgnoreCase(sb.toString(), ","));
			}
			
			long id_info = PinGen.getSerialPin();
			masSmsDati.setId(id_info);
			
			String[] datiIdArray = StringUtils.split(datiIds, ",");
			//问题数量
			masSmsDati.setDtSum(datiIdArray.length);
			List<MasSmsDatiTiKuInfo> masSmsDatiTiKuInfoList = new ArrayList<MasSmsDatiTiKuInfo>();
			int count = 1;
			List<Long> tikuIdLong = new ArrayList<Long>();
			for(String dati_id:datiIdArray){
				tikuIdLong.add(Long.parseLong(dati_id));
				MasSmsDatiTiKuInfo tikuInfo = new MasSmsDatiTiKuInfo();
				tikuInfo.setSerialId(count);
				tikuInfo.setTikuId(Long.parseLong(dati_id));
				masSmsDatiTiKuInfoList.add(tikuInfo);
				count++;
			}
			Map<String,Object> mapInfo = new HashMap<String,Object>();
			mapInfo.put("createdBy", loginUser.getId());
			mapInfo.put("tikuIdList", tikuIdLong);
			int sumInfo = masSmsDatiService.getMasSmsDatiSumBySearchInfo(mapInfo);
			masSmsDati.setScore(sumInfo);//答题总分数
			//创建答题试卷信息
			masSmsDatiService.createDatiInfo(masSmsDati,masSmsDatiTiKuInfoList);
			
			//取出第一条数据  进行封装短信发送bean
			if(datiIdArray.length>0){
				long datiLongInfo = Long.parseLong(datiIdArray[0]);
				MasSmsDatiTiKu tiku = masSmsDatiService.getTiKuById(datiLongInfo, loginUser.getId());
				//插入短信bean
				MasSmsBean masSmsBean = new MasSmsBean();
				masSmsBean.setBatchId(id_info);
				initMasSmsBean(content+tiku.getQuestion(),masSmsBean,loginUser,userSet);
				MasCommonFunction masCommonFunction=null;
				masCommonFunction=new MasCommonFunction(masSmsBean);
				entityMap = masCommonFunction.makeMbnSmsSendTask(loginUser,new Date(),colsList);
				this.getRequest().setAttribute("entityMap", entityMap);
				List<SmsMbnTunnelVO>  tunnelList = masCommonFunction.getTunnelList(loginUser.getMerchantPin());
				this.getRequest().setAttribute("tunnelList", tunnelList);
				
				
				//entityMap.put("resultcode", "success" );
		       // entityMap.put("message", "提交成功");
			}
			
		}catch(Exception e){
			e.printStackTrace();
	    }
		
		return SUCCESS;
	}
	
	
	private void initMasSmsBean(String smsText,MasSmsBean masSmsBean,Users loginUser,Set<Contacts> userSet){
		masSmsBean.setTitle(title);
		masSmsBean.setSmsText(smsText);
		masSmsBean.setYdTunnel(NumberUtils.toLong(ydTunnel));
		masSmsBean.setTdTunnel(NumberUtils.toLong(tdTunnel));
		masSmsBean.setEntSign(entSign);
		masSmsBean.setReplyText(datireplyText);
		masSmsBean.setFlag(null);
		
		masSmsBean.setReplyCode(StringUtils.removeStart(datireplyCode,ApSmsConstants.OPERATION_CODING_DT));
		masSmsBean.setOperationType(ApSmsConstants.OPERATION_CODING_TYPE_DT);
		masSmsBean.setCodeType(ApSmsConstants.OPERATION_CODING_DT);//提醒
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
}
