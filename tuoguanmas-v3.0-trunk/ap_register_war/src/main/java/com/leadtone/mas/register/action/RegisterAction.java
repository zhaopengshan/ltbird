/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.register.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.leadtone.mas.register.bean.Lisence;
import com.leadtone.mas.register.service.LisenceService;
import com.leadtone.mas.register.utils.MacGetterUtil;
import com.leadtone.mas.register.utils.MasPasswordTool;
import com.leadtone.mas.register.utils.PinGen;

/**
 *
 * @author tabsuny
 */
@ParentPackage("json-default")
@Namespace(value = "/registerAction")
public class RegisterAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Resource
	private LisenceService lisenceService;
	private Integer online;
	private List<String> localList;
	private Lisence lisenceObj;
	
	
	@Action(value = "register", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String register() {
		entityMap = new HashMap<String, Object>();
		//判断输入不可为空
		if(!StringUtils.isEmpty(lisenceObj.getUserKey()) && !StringUtils.isEmpty(lisenceObj.getUserName()) && !StringUtils.isEmpty(lisenceObj.getUserPwd()) ){
			//保存key。
			
			lisenceObj.setUserPwd(MasPasswordTool.getEncString(lisenceObj.getUserName(), lisenceObj.getUserPwd()));
			String userKey = lisenceObj.getUserKey();
			lisenceObj.setUserKey(null);
			//查询用户lisence是否存在
			List<Lisence> lisenceList = lisenceService.getAllLisence(lisenceObj);
			if(lisenceList!=null && lisenceList.size()>0){
				lisenceObj = lisenceList.get(0);
				String dbLisence = lisenceObj.getLisenceValue();
				if(dbLisence==null||dbLisence.trim().equals("")){
					String lisence = MacGetterUtil.getLisenceByMac(userKey);
					if(lisence.equals("error")){
						entityMap.put("status", false);
						entityMap.put("message", "您输入的Key不合法，请输入产品提供的15位Key或联系客服 wuchuanzhong@leadtone.com！");
					}else{
						lisenceObj.setUserKey(userKey);
						lisenceObj.setLisenceValue(lisence);
						lisenceObj.setUpdateTime(new Date());
						lisenceService.update(lisenceObj);
						entityMap.put("status", true);
						entityMap.put("values", lisence);
						entityMap.put("message", "获取激活码成功，如有其他问题请联系客服 wuchuanzhong@leadtone.com！");
					}
				}else{
					entityMap.put("status", true);
					entityMap.put("values", dbLisence);
					entityMap.put("message", "用户已经激活过，如有其他问题请联系客服 wuchuanzhong@leadtone.com！");
				}
			}else{
				entityMap.put("status", false);
				entityMap.put("message", "此用户不存在或密码不正确！");
			}
		}else{
			entityMap.put("status", false);
			entityMap.put("message", "用户名、密码、密钥不允许为空！");
		}
		return SUCCESS;
	}
	
//	@Action(value = "register", results = {
//			@Result(name = SUCCESS, location = "/registerDirect.jsp")})
//	public String register() {
//		localList = new ArrayList<String>();
//		localList = MacGetterUtil.getFormatMAC();
//		return SUCCESS;
//	}

	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
	}
	public List<String> getLocalList() {
		return localList;
	}
	public void setLocalList(List<String> localList) {
		this.localList = localList;
	}

	public Lisence getLisenceObj() {
		return lisenceObj;
	}

	public void setLisenceObj(Lisence lisenceObj) {
		this.lisenceObj = lisenceObj;
	}

}
