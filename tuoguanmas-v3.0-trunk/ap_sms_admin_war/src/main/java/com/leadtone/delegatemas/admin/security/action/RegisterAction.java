/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.admin.security.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.lisence.MacGetterUtil;
import com.leadtone.mas.bizplug.lisence.bean.Lisence;
import com.leadtone.mas.bizplug.lisence.bean.LisenceVO;
import com.leadtone.mas.bizplug.lisence.service.LisenceService;
import com.leadtone.mas.bizplug.security.action.BaseAction;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
import com.leadtone.mas.bizplug.util.WebUtils;
import com.leadtone.mas.bizplug.util.Xml_Bean;
/**
 *
 * @author tabsuny
 */
@ParentPackage("json-default")
@Namespace(value = "/registerAction")
public class RegisterAction extends BaseAction{
	private static Logger log = Logger.getLogger(RegisterAction.class);
	private static final long serialVersionUID = 1L;
	private int online;
	private List<String> localList;
	private Lisence lisenceObj;
	@Resource
	private LisenceService lisenceService;
	private String userName;
	private String userPwd;
	
	@Action(value = "offlineRegister", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String offlineRegister() {
		entityMap = new HashMap<String, Object>();
		//判断输入不可为空
		if( !StringUtils.isEmpty(lisenceObj.getLisenceValue()) ){
			lisenceService.truncate();
			lisenceObj.setLisenceId(PinGen.getSerialPin());
			lisenceService.insert(lisenceObj);
			entityMap.put("status", true);
			entityMap.put("message", "离线激活成功");
		}else{
			entityMap.put("status", false);
			entityMap.put("message", "数字证书不允许为空！");
		}
		return SUCCESS;
	}
	@Action(value = "onlineRegister", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
	public String onlineRegister() {
		entityMap = new HashMap<String, Object>();
		//判断输入不可为空userPwd
		if( !StringUtils.isEmpty("userName") && !StringUtils.isEmpty("userPwd") ){
			localList = new ArrayList<String>();
			localList = MacGetterUtil.getFormatMAC();
			if(localList!=null && localList.size()>0){
				String userKey = localList.get(0);
				RPCServiceClient serviceClient = null;
				try {
					serviceClient = new RPCServiceClient();
			        Options options = serviceClient.getOptions();  
			        //specify URL for invoking  
			        String wsUrl = "";
			        if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.WSURL))){
			        	wsUrl = WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.WSURL);                   
			        }
			        EndpointReference targetEPR = new EndpointReference(wsUrl);  
			        options.setTo(targetEPR);
			        LisenceVO tempLisence = new LisenceVO();
			        tempLisence.setUserName(userName);
			        tempLisence.setUserPwd(MasPasswordTool.getEncString(userName, userPwd));
			        tempLisence.setUserKey(userKey);
			        String lisenceXml = null;
			        try {
			        	lisenceXml = Xml_Bean.java2xml(tempLisence);
					} catch (JAXBException e) {
						e.printStackTrace();
					} catch (XMLStreamException e) {
						e.printStackTrace();
					} catch (FactoryConfigurationError e) {
						e.printStackTrace();
					}
			        Object[] opAddEntryArgs = new Object[]{lisenceXml};  
			        Class[] classes = new Class[]{String.class};  
			        QName opAddEntry = new QName("http://ws.apache.org/axis2","productRegister");  
			        Object resultStr = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
					try {
						Lisence resultLisence = (Lisence)Xml_Bean.xml2java(Lisence.class, resultStr.toString());
						if(resultLisence.getLisenceId()!=null){
							lisenceService.truncate();
							lisenceService.insert(resultLisence);
							entityMap.put("status", true);
							entityMap.put("message", "在线激活成功");
							return SUCCESS;
						}else{
							entityMap.put("status", false);
							entityMap.put("message", resultLisence.getLisenceValue());
							return SUCCESS;
						}
					} catch (JAXBException e) {
						e.printStackTrace();
					} catch (XMLStreamException e) {
						e.printStackTrace();
					} catch (FactoryConfigurationError e) {
						e.printStackTrace();
					}
				} catch (AxisFault e1) {
					log.info(e1.getMessage());
					entityMap.put("status", false);
					entityMap.put("message", "在线激活失败，请重试或联系管理员！");
					return SUCCESS;
				}catch (Exception e1) {
					log.info(e1.getMessage());
					entityMap.put("status", false);
					entityMap.put("message", "在线激活失败，请重试或联系管理员！");
					return SUCCESS;
				}
				//TODO
				entityMap.put("status", true);
				entityMap.put("message", "在线激活成功！");
			}else{
				entityMap.put("status", false);
				entityMap.put("message", "用户密钥不存在，请联系管理员！");
			}
		}else{
			entityMap.put("status", false);
			entityMap.put("message", "用户名、密码不允许为空！");
		}
		return SUCCESS;
	}
	
	@Action(value = "register", results = {
			@Result(name = "offline", location = "/registerOffline.jsp"),
			@Result(name = SUCCESS, location = "/register.jsp"),
			@Result(name = "online", location = "/registerOnline.jsp")})
	public String register() {
		localList = new ArrayList<String>();
		localList = MacGetterUtil.getFormatMAC();
		if(online==1){
			return "online";
		}
		if(online==0){
			return "offline";
		}
		return SUCCESS;
	}

	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

}
