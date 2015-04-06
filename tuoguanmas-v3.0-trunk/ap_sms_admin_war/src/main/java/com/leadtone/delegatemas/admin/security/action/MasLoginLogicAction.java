package com.leadtone.delegatemas.admin.security.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.service.IRegionService;
import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.lisence.MacGetterUtil;
import com.leadtone.mas.bizplug.lisence.bean.Lisence;
import com.leadtone.mas.bizplug.lisence.service.LisenceService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.util.LoginCheckUtil;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
import com.leadtone.mas.bizplug.util.ResourcesComparator;
import com.leadtone.mas.bizplug.util.WebUtils;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("json-default")
@Namespace(value = "/masLoginLogicAction")
public class MasLoginLogicAction extends BaseAction { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String account;
	private String loginPwd;
	private String verifyCode;
	private String corpAccessNumber;
	@Autowired
	private UserService userService;
	@Autowired
	private MbnConfigMerchantIService mbnConfigMerchantIService;
	@Autowired
	private MbnMerchantVipIService MbnMerchantVipIService;
	@Autowired
	private IRegionService regionService;
	@Autowired
	private LisenceService lisenceService;
	@Resource
	private PortalUserExtService portalUserExtService;
	
	// 手机验证码
	public String mobileChecking;
	private UserVO userVO;  
	private int msg=0;
	private PortalUserExtBean tempUserExt;
	
	private boolean checkLisence(){
		List<Lisence> lisenceList = lisenceService.getAllLisence();
		if( lisenceList == null || lisenceList.size() ==0 ){
			return false;
		}else{
			List<String> localList = MacGetterUtil.getMACLisence();
			Iterator<Lisence> lisenceIterator = lisenceList.iterator();
			while(lisenceIterator.hasNext()){
				Lisence tempLisence = lisenceIterator.next();
				String lisence = tempLisence.getLisenceValue();
				if(lisence!=null){
					for(int i=0; i < localList.size(); i++){
						if(localList.get(i).equals(lisence)){
							return true;
						}
					}
				}else{
					continue;
				}
			}
		}
		return false;
	}
	/**
	 * 先根据用户输入的用户名获取用户信息，然后再决定是否允许用户登录
	 * 
	 * @return
	 */
	@Action(value = "loginSmsCheck", results = {
			@Result(name = SUCCESS, type="redirect", location = "/smsmain.jsp"),
			@Result(name = ERROR, location = "/smslogin.jsp"),
			@Result(name = "smsCheck", location="/dynclogin.jsp"),
			@Result(name = "lisence", location = "/register.jsp"),
            @Result(name = "fordlogin", location = "/smslogin_ford.jsp")})
	public String loginSmsCheck() {
		
		//TODO
		if (!StringUtils.equalsIgnoreCase(getMobileChecking(),
				(String) ActionContext.getContext().getSession().get(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER))) {
			msg = 1;
			return "smsCheck";
		}
		
		Users tempUser = userService.queryByUserId(tempUserExt.getId());
		UserVO u=new UserVO();
		//端口号开关
		String portLock= WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.LOGINPORTLOCK);
		boolean bool = org.apache.commons.lang3.StringUtils.isBlank(portLock);
		if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
			u.setCorpAccessNumber(tempUser.getCorpAccessNumber());
		}
		u.setAccount(tempUser.getAccount());
		List<UserVO> users = null;
		try {
			users = userService.findByUsers(u); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ActionContext.getContext().getSession().remove(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);
		// 获取用户菜单
		super.getSession()
				.setAttribute(ApSmsConstants.SESSION_USER_INFO, users.get(0));
		Set<RoleVO> roleVOs = users.get(0).getRoles();
		Set<Resources> tempResources = null;
		for (RoleVO roleVO : roleVOs) {
			Set<Resources> parentResources = roleVO.getResources();
			if (tempResources == null) {
				tempResources = parentResources;
			} else {
				tempResources.addAll(parentResources);
				for (Resources resource : tempResources) {
					for (Resources undoResource : parentResources) {
						if (resource.getId() == undoResource.getId()) {
							resource.getSubResources().addAll(
									undoResource.getSubResources());
						}
					}
				}
			}
		}
		if (tempResources == null || tempResources.isEmpty()) {
			this.getRequest().setAttribute("message", "对不起,用户未被赋予访问系统权限!");
                        if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
                            return ERROR;
                        } else {
                            return "fordlogin";
                        }
		}

		List<Resources> resList = new ArrayList<Resources>();
		ResourcesComparator comparator = new ResourcesComparator();
		// 排序二级菜单
		List<Resources> tempList = new ArrayList<Resources>(tempResources);
		for (Resources res : tempList) {
			// 只保留一级菜单 非管理功能菜单
			if (res.getParentId() > 0 || res.getIsManagementFun() == 1) {
				continue;
			}
			if (res.getSubResources() != null) {
				List<Resources> subList = new ArrayList<Resources>(
						res.getSubResources());
				Collections.sort(subList, comparator);
				res.setSortedSubRes(subList);
				resList.add(res);
			}
		}
		// 排序主菜单
		Collections.sort(resList, comparator);
		ActionContext.getContext().getSession().put("resources", resList);
		
		// 查询企业签名
		String smsSign = "";
		MbnConfigMerchant confMerchant = mbnConfigMerchantIService.loadByMerchantPin(users.get(0).getMerchantPin(), "sms_sign_content");
		if( confMerchant != null && confMerchant.getItemValue() != null){
			smsSign = confMerchant.getItemValue();
		}
		ActionContext.getContext().getSession().put("ent_sms_sign", smsSign);
		// 查询企业图标
			String homePageLogo = "/themes/mas3admin/images/main/logo.png";
			confMerchant = mbnConfigMerchantIService.loadByMerchantPin(users.get(0).getMerchantPin(), "home_page_logo");
			if( confMerchant != null && confMerchant.getItemValue() != null){
				homePageLogo = confMerchant.getItemValue();
			}
			ActionContext.getContext().getSession().put("home_page_logo", homePageLogo);
		// 查询企业省码
		MbnMerchantVip merchant = MbnMerchantVipIService.loadByMerchantPin(users.get(0).getMerchantPin());
		String prov = merchant.getProvince();
		Region region = regionService.findByProvinceId(Long.parseLong(prov));
		String provCode = region.getCode();
		ActionContext.getContext().getSession().put("prov_code", provCode);
		
		return "success";
	}
	/**
	 * 先根据用户输入的用户名获取用户信息，然后再决定是否允许用户登录
	 * 
	 * @return
	 */
	@Override
	@Action(value = "login", results = {
			@Result(name = SUCCESS, type="redirect", location = "/smsmain.jsp"),
			@Result(name = ERROR, location = "/smslogin.jsp"),
			@Result(name = "smsCheck", location="/dynclogin.jsp"),
			@Result(name = "lisence", location = "/register.jsp"),
            @Result(name = "fordlogin", location = "/smslogin_ford.jsp")})
	public String execute() {
		boolean isOnline = false;
		if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.ONLINE))){
			isOnline = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.ONLINE));                   
        }else{
        	isOnline = false;
        }
		if( !isOnline && !checkLisence()){
			return "lisence";
		}
		
		
		UserVO u=new UserVO();
		//端口号开关
		String portLock= WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.LOGINPORTLOCK);
		boolean bool = org.apache.commons.lang3.StringUtils.isBlank(portLock);
		if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
			u.setCorpAccessNumber(getCorpAccessNumber());
			if (!StringUtils.equalsIgnoreCase(
					this.getVerifyCode(),
					(String) ActionContext.getContext().getSession()
							.get("verifyCode"))){
				this.getRequest().setAttribute("message","图形验证码错误!");
				return ERROR;
			}
		}else{
			//验证码
			if (!StringUtils.equalsIgnoreCase(
					this.getVerifyCode(),
					(String) ActionContext.getContext().getSession()
							.get("verifyCode"))){
				this.getRequest().setAttribute("message","图形验证码错误!");
				return "fordlogin";
			}
		}
		u.setAccount(getAccount());
		List<UserVO> users = null;
		try {
			users = userService.findByUsers(u); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * if (!StringUtils.endsWithIgnoreCase(this.getVerifyCode(), (String)
		 * ActionContext.getContext().getSession().get("verifyCode"))) {
		 * this.getRequest().setAttribute("message", "验证码错误!"); return ERROR; }
		 */
		
		try {
 		// 登录次数限制及验证
			if (users.size()==0 || users==null) {
				this.getRequest().setAttribute("message", "用户名或密码错误!");
	                        if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
	                            return ERROR;
	                        } else {
	                            return "fordlogin";
	                        }
				
			} else if (!MasPasswordTool.getDesString(users.get(0).getPassword(),
					users.get(0).getAccount()).equals(this.getLoginPwd())) {
				String message = "用户名或密码错误!";
				if (users.get(0).getLimitTryCount()!=0 && LoginCheckUtil.isAccountlock(getSession(), users.get(0))) {
					UserVO uvo = users.get(0);
					uvo.setLockFlag(1);
					uvo.setActiveFlag(0);
					uvo.setPassword(MasPasswordTool.getDesString(
							users.get(0).getPassword(), users.get(0).getAccount()));
					userService.updateUser(uvo);
					message = "用户已被锁定，请联系管理员!";
					ActionContext.getContext().getSession().remove(users.get(0).getMerchantPin());
				}
				this.getRequest().setAttribute("message", message);
	                        if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
	                            return ERROR;
	                        } else {
	                            return "fordlogin";
	                        }
			} else if (users.get(0).getLockFlag() == 1) {
				this.getRequest().setAttribute("message", "用户已被锁定，请联系管理员!");
	                        if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
	                            return ERROR;
	                        } else {
	                            return "fordlogin";
	                        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(users.get(0).getWebService()!=null)
			if(users.get(0).getWebService()!=com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_NO){
				this.getRequest().setAttribute("message", "您当前的帐户不支持此登录方式!");
				return ERROR;
        }
		//TODO
		tempUserExt = portalUserExtService.getByPk(users.get(0).getId());
		if( tempUserExt.getSmsMobile() != null && !tempUserExt.getSmsMobile().trim().equals("") ){
			return "smsCheck";
		}
		
		
		// 当此用户的鉴权方式为用户名或密码时并且验证开关为false时，将不在验证手机号、/
        if(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.MOBILELOCK).equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE))
			if (users.get(0).getLoginType() != 1)
				// 手机验证码
				if (!StringUtils.endsWithIgnoreCase(
						getMobileChecking(),
						(String) ActionContext.getContext().getSession()
								.get(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER))) {
					this.getRequest().setAttribute("message", "手机验证码错误!");
	                                if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
	                                    return ERROR;
	                                } else {
	                                    return "fordlogin";
	                                }
				} else {
					// 清空手机验证码
					ActionContext.getContext().getSession()
							.remove(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);
				}
		// 获取用户菜单
		super.getSession()
				.setAttribute(ApSmsConstants.SESSION_USER_INFO, users.get(0));
		Set<RoleVO> roleVOs = users.get(0).getRoles();
		Set<Resources> tempResources = null;
		for (RoleVO roleVO : roleVOs) {
			Set<Resources> parentResources = roleVO.getResources();
			if (tempResources == null) {
				tempResources = parentResources;
			} else {
				tempResources.addAll(parentResources);
				for (Resources resource : tempResources) {
					for (Resources undoResource : parentResources) {
						if (resource.getId() == undoResource.getId()) {
							resource.getSubResources().addAll(
									undoResource.getSubResources());
						}
					}
				}
			}
		}
		if (tempResources == null || tempResources.isEmpty()) {
			this.getRequest().setAttribute("message", "对不起,用户未被赋予访问系统权限!");
                        if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
                            return ERROR;
                        } else {
                            return "fordlogin";
                        }
		}

		List<Resources> resList = new ArrayList<Resources>();
		ResourcesComparator comparator = new ResourcesComparator();
		// 排序二级菜单
		List<Resources> tempList = new ArrayList<Resources>(tempResources);
		for (Resources res : tempList) {
			// 只保留一级菜单 非管理功能菜单
			if (res.getParentId() > 0 || res.getIsManagementFun() == 1) {
				continue;
			}
			if (res.getSubResources() != null) {
				List<Resources> subList = new ArrayList<Resources>(
						res.getSubResources());
				Collections.sort(subList, comparator);
				res.setSortedSubRes(subList);
				resList.add(res);
			}
		}
		// 排序主菜单
		Collections.sort(resList, comparator);
		ActionContext.getContext().getSession().put("resources", resList);
		
		// 查询企业签名
		String smsSign = "";
		MbnConfigMerchant confMerchant = mbnConfigMerchantIService.loadByMerchantPin(users.get(0).getMerchantPin(), "sms_sign_content");
		if( confMerchant != null && confMerchant.getItemValue() != null){
			smsSign = confMerchant.getItemValue();
		}
		ActionContext.getContext().getSession().put("ent_sms_sign", smsSign);
		// 查询企业图标
			String homePageLogo = "/themes/mas3admin/images/main/logo.png";
			confMerchant = mbnConfigMerchantIService.loadByMerchantPin(users.get(0).getMerchantPin(), "home_page_logo");
			if( confMerchant != null && confMerchant.getItemValue() != null){
				homePageLogo = confMerchant.getItemValue();
			}
			ActionContext.getContext().getSession().put("home_page_logo", homePageLogo);
		// 查询企业省码
		MbnMerchantVip merchant = MbnMerchantVipIService.loadByMerchantPin(users.get(0).getMerchantPin());
		String prov = merchant.getProvince();
		Region region = regionService.findByProvinceId(Long.parseLong(prov));
		String provCode = region.getCode();
		ActionContext.getContext().getSession().put("prov_code", provCode);
		
		return "success";
	}
	
	/**
	 * 首登验证程序
	 * 
	 * @return
	 */
	@Action(value = "firstloginUser", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String firstloginUser() {
		entityMap = new HashMap<String, Object>();
		
		boolean isOnline = false;
		if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.ONLINE))){
			isOnline = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.ONLINE));                   
        }else{
        	isOnline = false;
        }
		
		entityMap.put("isOnline", isOnline);
		if( !isOnline && !checkLisence() ){
			entityMap.put("existFlag", false);
			entityMap.put("existMessage", "产品尚未激活!");
			return SUCCESS;
		}
		
		
//		List<UserVO> usersList = null;
//		try {
////			usersList = userService.validateLogin(userVO); 
//			//daiyouhua, mark
//			Map<String,Object> paraMap = new HashMap<String,Object>();
//			paraMap.put("account", userVO.getAccount());
//			paraMap.put("systype", 0);
//			usersList = userService.validateLogin(paraMap); 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		//验证码
		if ( !StringUtils.equalsIgnoreCase( this.getVerifyCode(), (String) ActionContext.getContext().getSession().get("verifyCode") ) ){
			entityMap.put("existFlag", false);
			entityMap.put("existMessage","图形验证码错误!");
			return SUCCESS;
		}
		
		// 登录次数限制及验证
//		if (usersList==null || usersList.size()==0) {
//			entityMap.put("existFlag", false);
//			entityMap.put("existMessage", userVO.getAccount()+"用户不存在!");
//			return SUCCESS;
//		}else{
			//端口号开关
			String portLock= WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.LOGINPORTLOCK);
			boolean bool = org.apache.commons.lang3.StringUtils.isBlank(portLock);
			if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
				userVO.setCorpAccessNumber(corpAccessNumber);
			}
//			String pas = MasPasswordTool.getEncString(userVO.getPassword(),
//					userVO.getAccount());
			userVO.setPassword(MasPasswordTool.getEncString(userVO.getPassword(),
					userVO.getAccount()));
			UserVO userTemp = new UserVO();
			userTemp.setAccount(userVO.getAccount());
			userTemp.setCorpAccessNumber(userVO.getCorpAccessNumber());
			Users users = (Users) userService.validateUser(userVO);
			if( users==null ){
				entityMap.put("existFlag", false);
				entityMap.put("existMessage","用户不存在!");
				return SUCCESS;
			}
//			else{
//				//验证码
//				if (!StringUtils.endsWithIgnoreCase( this.getVerifyCode(), (String) ActionContext.getContext().getSession().get("verifyCode"))){
//					entityMap.put("existFlag", false);
//					entityMap.put("existMessage","图形验证码错误!");
//					return SUCCESS;
//				}
//			}
			
			if (!users.getPassword().equals(userVO.getPassword())){
				entityMap.put("existFlag", false);
				entityMap.put("existMessage", "密码错误!");
				return SUCCESS;
			}
			ActionContext.getContext().getSession().remove("pwd_security_policy");
			entityMap.put("existFlag", true);
//			String pas = MasPasswordTool.getEncString(userVO.getPassword(),
//					userVO.getAccount());
//			userVO.setPassword(pas);
//			Users users = (Users) userService.validateUser(userVO);
			entityMap.put("users", users);
			// 首登是否检验
//			if (users != null) {
				//IP地址锁定
//				MbnConfigMerchant ipLock = this.configMerchantValue(users, ApSmsConstants.IP_LIMIT_LOCK);
//				entityMap.put(ApSmsConstants.IP_LIMIT_LOCK, ipLock == null ? 2:ipLock.getValidFlag());
				//密码过期判断
				MbnConfigMerchant pwdModify = this.configMerchantValue(users,ApSmsConstants.PWD_MODIFY_PERIOD);
				if( pwdModify !=null && pwdModify.getValidFlag() == 1 ){
					int itemValue = Integer.parseInt( pwdModify.getItemValue() );
					Date endDate = users.getUpdateTime();//.getNextPwdModifyTime();
					Date curDate = new Date();
					if( endDate !=null && (curDate.getTime() - endDate.getTime())/(24*3600*1000)  >= itemValue ){
						entityMap.put("modifyPeriod", true);
						entityMap.put("modifyPeriodValue", pwdModify.getItemValue());
						ActionContext.getContext().getSession().put("pwd_security_policy", users.getId());
					}else{
						entityMap.put("modifyPeriod", false);
					}
				}else{
					//未设置 密码过期 天
					entityMap.put("modifyPeriod", false);
				}
//				entityMap.put("modifyPeriod", this.configMerchantValue(users,
//						ApSmsConstants.PWD_MODIFY_PERIOD));
				//首次登陆验证
				MbnConfigMerchant firstLogin = this.configMerchantValue(users, ApSmsConstants.FIRST_LOGIN_MODIFY_PWD_FLAG);
				if(firstLogin != null){
					entityMap.put("firstFlag", firstLogin.getValidFlag());
					ActionContext.getContext().getSession().put("pwd_security_policy", users.getId());
				}else{
					entityMap.put("firstFlag", 2);
				}
//			}
//			entityMap.put("remoteIp", MasCommonFunction.getIpAddr(getRequest()));
			return SUCCESS;
//		}
	}
	
	private MbnConfigMerchant configMerchantValue(Users users, String str) {
		MbnConfigMerchant mbnConfigMerchant = mbnConfigMerchantIService
				.loadByMerchantPin(users.getMerchantPin(), str);
		return mbnConfigMerchant;
	}
	
	/**
	 * 退出函数
	 * @return
	 */
	@Action(value = "logout", results = {
			@Result(name = SUCCESS, location = "/smslogin.jsp"),
                        @Result(name = "fordlogin", location = "/smslogin_ford.jsp")})
	public String logout() {
                //设定是否为托管mas处理方式
                boolean isDelegateMas = false;
                if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.DELEGATEMAS))){
                    isDelegateMas = Boolean.valueOf(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.DELEGATEMAS));                   
                }
		ActionContext.getContext().getSession().clear();
                if(isDelegateMas) {
                    return SUCCESS;
                } else {
                    return "fordlogin";
                }
	}
	
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	public String getMobileChecking() {
		return mobileChecking;
	}
	public void setMobileChecking(String mobileChecking) {
		this.mobileChecking = mobileChecking;
	}

	/**
	 * 先根据用户输入的用户名获取用户信息，然后再决定是否允许用户登录
	 * 
	 * @return
	 */
	@Action(value = "smsLogin", results = { @Result(name = SUCCESS, location = "/smslogin.jsp") })
	public String toLogin() {
		return SUCCESS;
	}
	public String getCorpAccessNumber() {
		return corpAccessNumber;
	}
	public void setCorpAccessNumber(String corpAccessNumber) {
		this.corpAccessNumber = corpAccessNumber;
	}
	public int getMsg() {
		return msg;
	}
	public void setMsg(int msg) {
		this.msg = msg;
	}
	public PortalUserExtBean getTempUserExt() {
		return tempUserExt;
	}
	public void setTempUserExt(PortalUserExtBean tempUserExt) {
		this.tempUserExt = tempUserExt;
	}

}
