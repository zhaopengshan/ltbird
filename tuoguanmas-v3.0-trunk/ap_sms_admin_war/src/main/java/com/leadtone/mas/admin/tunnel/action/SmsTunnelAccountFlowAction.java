package com.leadtone.mas.admin.tunnel.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelAccountFlowService;
import com.leadtone.mas.bizplug.tunnel.service.SmsTunnelAccountService;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace(value = "/smsTunnelAccountFlowAction")
public class SmsTunnelAccountFlowAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private SmsMbnTunnelAccountFlowService smsMbnTunnelAccountFlowService;
	@Resource
	private SmsTunnelAccountService smsTunnelAccountService;
	
	private String dateFrom;
	private String dateTo;
	private String operationType;
	private String tunnelId;
	private SmsMbnTunnelAccountFlow smsMbnTunnelAccountFlow;
	

	@Action(value = "addTunnelAccountFlow", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String addTunnelAccountFlow(){
		String result = SUCCESS;
		entityMap = new HashMap<String, Object>();
		Users loginUser = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		try{ 
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			
			smsMbnTunnelAccountFlow.setId(PinGen.getSerialPin());
			smsMbnTunnelAccountFlow.setModifyTime(currentTime);
			smsMbnTunnelAccountFlow.setModifyBy(loginUser.getAccount());
			smsMbnTunnelAccountFlow.setModifyByPin(loginUser.getMerchantPin());
			
			List<SmsTunnelAccount> tunnelAccountList = smsTunnelAccountService.getByTunnelIdList(smsMbnTunnelAccountFlow.getTunnelId());
			if(tunnelAccountList!=null){
				SmsTunnelAccount tunnelAccount = tunnelAccountList.get(0);
				switch(smsMbnTunnelAccountFlow.getOperationType()){
				case 1: 
					tunnelAccount.setBalanceAmount(tunnelAccount.getBalanceAmount() + smsMbnTunnelAccountFlow.getAmount());
					tunnelAccount.setBalanceNumber(tunnelAccount.getBalanceNumber() + smsMbnTunnelAccountFlow.getNumber());
					smsMbnTunnelAccountFlow.setBalanceNumber(tunnelAccount.getBalanceNumber());
					entityMap.put("message", "充值成功！");
					break;
				case 2: 
					tunnelAccount.setBalanceAmount(tunnelAccount.getBalanceAmount() + smsMbnTunnelAccountFlow.getAmount());
					tunnelAccount.setBalanceNumber(tunnelAccount.getBalanceNumber() + smsMbnTunnelAccountFlow.getNumber());
					smsMbnTunnelAccountFlow.setBalanceNumber(tunnelAccount.getBalanceNumber());
					entityMap.put("message", "充值成功！");
					break;
				case 3: 
					long resultNumber = tunnelAccount.getBalanceNumber() - smsMbnTunnelAccountFlow.getNumber();
					float resultAccount = tunnelAccount.getBalanceAmount() - smsMbnTunnelAccountFlow.getAmount();
					if( resultNumber <0 || resultAccount<0 ){
						entityMap.put("message", "代理商退款失败，请确认条数和钱数是否正确！");
						entityMap.put("resultcode", "success" );
				        return result;
					}
					tunnelAccount.setBalanceAmount(resultAccount);
					tunnelAccount.setBalanceNumber(resultNumber);
					smsMbnTunnelAccountFlow.setBalanceNumber(tunnelAccount.getBalanceNumber());
					entityMap.put("message", "代理商退款成功！");
					break;
				}
				tunnelAccount.setModifyTime(currentTime);
				if(smsTunnelAccountService.update(smsMbnTunnelAccountFlow, tunnelAccount)){
					entityMap.put("balanceNumber", tunnelAccount.getBalanceNumber());
					entityMap.put("resultcode", "success" );
			        return result;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			result = ERROR;
		}
		entityMap.put("resultcode", "error" );
        entityMap.put("message", "充值失败！");
		return result;
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
				pageUtil.setEndDate(format.parse(dateTo));
			}
			if(!StringUtil.isEmpty(dateFrom)){
				pageUtil.setStartDate(format.parse(dateFrom));
			}
			if(!StringUtil.isEmpty(operationType)){
				pageUtil.setOperationType(Long.valueOf(operationType));
			}//
			if(!StringUtil.isEmpty(tunnelId)){
				pageUtil.setTunnelId(Long.valueOf(tunnelId));
			}
			Page page = smsMbnTunnelAccountFlowService.page(pageUtil);
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
	public String getTunnelId() {
		return tunnelId;
	}
	public void setTunnelId(String tunnelId) {
		this.tunnelId = tunnelId;
	}

	public SmsMbnTunnelAccountFlow getSmsMbnTunnelAccountFlow() {
		return smsMbnTunnelAccountFlow;
	}

	public void setSmsMbnTunnelAccountFlow(
			SmsMbnTunnelAccountFlow smsMbnTunnelAccountFlow) {
		this.smsMbnTunnelAccountFlow = smsMbnTunnelAccountFlow;
	}
}
