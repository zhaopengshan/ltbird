package com.leadtone.mas.bizplug.security.action;

import com.leadtone.delegatemas.admin.security.action.MasLoginLogicAction;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.util.LoginCheckUtil;
import com.leadtone.mas.bizplug.util.ResourcesComparator;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("unused")
@ParentPackage("json-default")
@Namespace(value = "/apsms")
public class LoginAction extends BaseAction{ 
	// 手机验证码
	public String mobileChecking;  
    private static Logger log = Logger.getLogger(LoginAction.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String account;
    private String loginPwd;
    private String verifyCode;
    @Autowired
    private UserService userService;
	
	private UserVO userVO;
	 
	/**
	 * 先根据用户输入的用户名获取用户信息，然后再决定是否允许用户登录
	 * 
	 * @return
	 */

	@Override
	@Action(value = "login", results = {
			@Result(name = SUCCESS, location = "/smsmain.jsp"),
			@Result(name = ERROR, location = "/smslogin.jsp") })
	public String execute() {
		UserVO users = userService.getUserByAccount(this.account);

		/*
		 * if (!StringUtils.endsWithIgnoreCase(this.getVerifyCode(), (String)
		 * ActionContext.getContext().getSession().get("verifyCode"))) {
		 * this.getRequest().setAttribute("message", "验证码错误!"); return ERROR; }
		 */
		// 登录次数限制及验证
		if (users == null) {
			this.getRequest().setAttribute("message", "用户名或密码错误!");
			return ERROR;
		} else if (!MasPasswordTool.getDesString(users.getPassword(),
				users.getAccount()).equals(this.getLoginPwd())) {
			String message = "用户名或密码错误!";
			if (LoginCheckUtil.isAccountlock(getSession(), users)) {
				UserVO uvo = users;
				uvo.setLockFlag(1);
				uvo.setActiveFlag(0);
				uvo.setPassword(MasPasswordTool.getDesString(
						users.getPassword(), users.getAccount()));
				userService.updateUser(uvo);
				message = "用户已被锁定，请联系管理员!";
			}
			this.getRequest().setAttribute("message", message);
			return ERROR;
		} else if (users.getLockFlag() == 1) {
			this.getRequest().setAttribute("message", "用户已被锁定，请联系管理员!");
			return ERROR;
		}
		// 当此用户的鉴权方式为用户名或密码时，将不在验证手机号、/
		if (users.getLoginType() != 1)
			// 手机验证码
			if (!StringUtils.endsWithIgnoreCase(
					getMobileChecking(),
					(String) ActionContext.getContext().getSession()
							.get(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER))) {
				this.getRequest().setAttribute("message", "手机验证码错误!");
				return ERROR;
			} else {
				// 清空手机验证码
				ActionContext.getContext().getSession()
						.remove(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);
			}
		// 获取用户菜单
		super.getSession()
				.setAttribute(ApSmsConstants.SESSION_USER_INFO, users);
		Set<RoleVO> roleVOs = users.getRoles();
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
			return ERROR;
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
		return "success";
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

}
