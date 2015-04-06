package com.leadtone.mas.admin.tunnel.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;

import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.config.bean.MbnConfigSysDictionary;
import com.leadtone.mas.bizplug.config.service.MbnConfigSysDictionaryService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/smsTunnelAction")
public class SmsTunnelAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private SmsMbnTunnelService smsMbnTunnelService;
	@Resource
	private MbnConfigSysDictionaryService mbnConfigSysDictionaryService;
	@Resource
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	
	private SmsMbnTunnelVO smsMbnTunnelVO;
	//删除通道IDS
	private String smsIds;
	private List<MbnConfigSysDictionary> provinceList;
	//查询通道名
	private String tunnelName;
	//查询通道使用状态
	private String tunnelState;
	//查询通道 所属 状态
	private String tunnelAttribute;
	//查询运营商 标识
	private String tunnelClassify;
	//查询 通道省 编码
	private String tunnelProvince;
	//
	private Integer forJson;
	// 查询通道ID
	private Long selTunnelId;
	private int proName;
	
	private String selectedId;
	/**
	 * @param page,rows,...
	 * @description 提供查询分页/模糊查询分页
	 * @return entityMap 
	 * @type Json
	 */
	@Action(value = "listTunnelInfo", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String listTunnelInfo(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setStart(page);
		pageUtil.setPageSize(rows);
		try{
			if(!StringUtil.isEmpty(tunnelName)){
				pageUtil.setName(URLDecoder.decode(tunnelName,"UTF-8"));
			}
			if(!StringUtil.isEmpty(tunnelState)){
				pageUtil.setState(Long.valueOf(tunnelState));
			}
			if(!StringUtil.isEmpty(tunnelAttribute)){
				pageUtil.setAttribute(Long.valueOf(tunnelAttribute));
			}
			if(!StringUtil.isEmpty(tunnelClassify)){
				pageUtil.setClassify(Long.valueOf(tunnelClassify));
			}
			if(!StringUtil.isEmpty(tunnelProvince)){
				pageUtil.setProvince(tunnelProvince);
			}
			Page page = smsMbnTunnelService.page(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<SmsMbnTunnelVO> datas = (List<SmsMbnTunnelVO>) page.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", page.getRecords());
	            if( datas == null ){
	            	datas = new ArrayList<SmsMbnTunnelVO>();
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
	 * 增加通道
	 * @return
	 */
	@Action(value="deleteTunnelByIds", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String deleteTunnelByIds(){
		String result = SUCCESS;
		entityMap = new HashMap<String, Object>();
		try{ 
			if(!StringUtil.isEmpty(smsIds)){
				Timestamp currentTime = new Timestamp(System.currentTimeMillis());
				String[] tunnelIds = smsIds.split(",");
				List<SmsMbnTunnel> updateList = new ArrayList<SmsMbnTunnel>();
				for(int i =0; i < tunnelIds.length; i++){
					SmsMbnTunnel temp = new SmsMbnTunnel();
					temp.setId(Long.valueOf(tunnelIds[i]));
					temp.setDelStatus(ApSmsConstants.TUNNEL_DEL_STATUS_Y);
					temp.setUpdateTime(currentTime);
					updateList.add(temp);
				}
				smsMbnTunnelService.batchUpdateByList(updateList);
				entityMap.put("resultcode", "success" );
		        entityMap.put("message", "删除成功！");
		        return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			result = ERROR;
			entityMap.put("resultcode", "error" );
			entityMap.put("message", "删除失败！");
		}
		return result;
	}
	/**
	 * 增加通道
	 * @return
	 */
	@Action(value="editTunnelInfo", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String editTunnelInfo(){
		String result = SUCCESS;
		entityMap = new HashMap<String, Object>();
		try{ 
			if(!StringUtil.isEmpty(smsMbnTunnelVO.getName())){
				List<SmsMbnTunnel> tunnelList = smsMbnTunnelService.getTunnelByName(smsMbnTunnelVO.getName());
				if(tunnelList!=null&&tunnelList.size()>0){
					SmsMbnTunnel tunnelTemp = tunnelList.get(0);
					long tunnelDB = tunnelTemp.getId();
					long tunnelEdit = smsMbnTunnelVO.getId();
					if(tunnelDB != tunnelEdit){
						entityMap.put("resultcode", "error" );
				        entityMap.put("message", "修改失败,此通道名称已存在！");
				        return result;
					}
				}
				Timestamp currentTime = new Timestamp(System.currentTimeMillis());
				SmsMbnTunnel smsMbnTunnel = new SmsMbnTunnel(smsMbnTunnelVO);
				smsMbnTunnel.setUpdateTime(currentTime);
				smsMbnTunnelService.update(smsMbnTunnel);
				entityMap.put("resultcode", "success" );
		        entityMap.put("message", "修改成功！");
			}else{
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "通道名称不允许为空！");
			}
		}catch(Exception e){
			e.printStackTrace();
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", "修改失败！");
		}
		return result;
	}
	/**
	 * 增加通道
	 * @return
	 */
	@Action(value="addTunnelInfo", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String addTunnelInfo(){
		String result = SUCCESS;
		entityMap = new HashMap<String, Object>();
		try{ 
			if(!StringUtil.isEmpty(smsMbnTunnelVO.getName()) && smsMbnTunnelService.isTunnelName(smsMbnTunnelVO.getName())){
				entityMap.put("resultcode", "error" );
		        entityMap.put("message", "此通道名称已存在！");
		        return result;
			}
			SmsMbnTunnel smsMbnTunnel = new SmsMbnTunnel(smsMbnTunnelVO);
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			smsMbnTunnel.setCreateTime(currentTime);
			smsMbnTunnel.setUpdateTime(currentTime);
			smsMbnTunnel.setDelStatus(ApSmsConstants.TUNNEL_DEL_STATUS_N);
			smsMbnTunnel.setType(ApSmsConstants.TUNNEL_TYPE_SMS);//短信
			smsMbnTunnel.setId(PinGen.getSerialPin());
			SmsTunnelAccount tunnelAccount = new SmsTunnelAccount();
			tunnelAccount.setId(PinGen.getSerialPin());
			tunnelAccount.setBalanceAmount(0f);
			tunnelAccount.setBalanceNumber(0l);
			tunnelAccount.setModifyTime(currentTime);
			smsMbnTunnelService.insert(smsMbnTunnel, tunnelAccount);
			entityMap.put("resultcode", "success" );
	        entityMap.put("message", "添加成功！");
		}catch(Exception e){
			e.printStackTrace();
			result = ERROR;
			entityMap.put("resultcode", "error" );
	        entityMap.put("message", "添加失败！");
		}
		return result;
	}
	
	/**
	 * 根据ID查询通道详细
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "initTunnelList",
	results = { @Result(name = SUCCESS, location = "/ap/tunnel/smstunnel_info.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String initTunnelList(){
		String result = SUCCESS;
		try{ 
			PageUtil pageUtil = new PageUtil();
			pageUtil.setType(String.valueOf(ApSmsConstants.CONFIG_DICTIONARY_PROVINCE));
			Page province = mbnConfigSysDictionaryService.page(pageUtil);
			provinceList = (List<MbnConfigSysDictionary>) province.getData();
            if( provinceList == null ){
            	provinceList = new ArrayList<MbnConfigSysDictionary>();
            }
		}catch(Exception e){
			e.printStackTrace();
			result = ERROR;
		}
		return result;
	}
	/**
	 * 根据ID查询通道详细
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "dialogInit",
	results = { @Result(name = SUCCESS, location = "/ap/tunnel/smstunnel_dialog.jsp"),
				@Result(name = ERROR, location = "/error.jsp")})
	public String dialogInit(){
		String result = SUCCESS;
		try{ 
			PageUtil pageUtil = new PageUtil();
			pageUtil.setType(String.valueOf(ApSmsConstants.CONFIG_DICTIONARY_PROVINCE));
			Page province = mbnConfigSysDictionaryService.page(pageUtil);
			provinceList = (List<MbnConfigSysDictionary>) province.getData();
            if( provinceList == null ){
            	provinceList = new ArrayList<MbnConfigSysDictionary>();
            }
		}catch(Exception e){
			e.printStackTrace();
			result = ERROR;
		}
		return result;
	}
	/**
	 * 根据ID查询通道详细
	 * @return
	 */
	@Action(value = "getTunnelDetails",
	results = { @Result(name = SUCCESS, location = "/ap/tunnel/smstunnel_flow.jsp"),
				@Result(name = ERROR, location = "/error.jsp"),
				@Result(name = "json", type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String getTunnelDetails(){
		String result = SUCCESS;
		if(!StringUtil.isEmpty(selectedId)){
			try{ 
				smsMbnTunnelVO = smsMbnTunnelService.queryByPk(Long.valueOf(selectedId));
				if(forJson!=null&&forJson==1){
					entityMap = new HashMap<String, Object>();
					entityMap.put("resultcode", "success" );
			        entityMap.put("data", smsMbnTunnelVO);
			        return "json";
				}
			}catch(Exception e){
				e.printStackTrace();
				result = ERROR;
			}
		}
		return result;
	}
	
	/**
	 * 增加通道
	 * @return
	 */
	@Action(value="queryTunnelRemain", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String queryTunnelRemain(){
		String result = SUCCESS;
		entityMap = new HashMap<String, Object>();
		try{ 
			Users loginUser = (Users) super.getSession().getAttribute(
					ApSmsConstants.SESSION_USER_INFO);
			MbnMerchantConsume mbnMerchantConsume = mbnMerchantConsumeService.findByTunnelId(loginUser.getMerchantPin(), selTunnelId);
			if( mbnMerchantConsume!=null){
				entityMap.put("remain", mbnMerchantConsume.getRemainNumber());
			}else{
				entityMap.put("remain", "n/a");
			}
			entityMap.put("resultcode", "success" );
		}catch(Exception e){
			e.printStackTrace();
			entityMap.put("resultcode", "error" );
		}
		return result;
	}
	public SmsMbnTunnelVO getSmsMbnTunnelVO() {
		return smsMbnTunnelVO;
	}
	public void setSmsMbnTunnelVO(SmsMbnTunnelVO smsMbnTunnelVO) {
		this.smsMbnTunnelVO = smsMbnTunnelVO;
	}

	public List<MbnConfigSysDictionary> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<MbnConfigSysDictionary> provinceList) {
		this.provinceList = provinceList;
	}
	public String getTunnelName() {
		return tunnelName;
	}
	public void setTunnelName(String tunnelName) {
		this.tunnelName = tunnelName;
	}
	public String getTunnelState() {
		return tunnelState;
	}
	public void setTunnelState(String tunnelState) {
		this.tunnelState = tunnelState;
	}
	public String getTunnelAttribute() {
		return tunnelAttribute;
	}
	public void setTunnelAttribute(String tunnelAttribute) {
		this.tunnelAttribute = tunnelAttribute;
	}
	public String getTunnelClassify() {
		return tunnelClassify;
	}
	public void setTunnelClassify(String tunnelClassify) {
		this.tunnelClassify = tunnelClassify;
	}
	public String getTunnelProvince() {
		return tunnelProvince;
	}
	public void setTunnelProvince(String tunnelProvince) {
		this.tunnelProvince = tunnelProvince;
	}

	public String getSmsIds() {
		return smsIds;
	}

	public void setSmsIds(String smsIds) {
		this.smsIds = smsIds;
	}

	public Integer getForJson() {
		return forJson;
	}

	public void setForJson(Integer forJson) {
		this.forJson = forJson;
	}

	public Long getSelTunnelId() {
		return selTunnelId;
	}

	public void setSelTunnelId(Long selTunnelId) {
		this.selTunnelId = selTunnelId;
	}
	public void setSelTunnelId(String selTunnelId) {
		Long temp = 0L;
		try{
			temp = Long.parseLong(selTunnelId);
		}catch(Exception e){
			temp = 0L;
		}
		this.selTunnelId = temp;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public int getProName() {
		return proName;
	}

	public void setProName(int proName) {
		this.proName = proName;
	}
	
}
