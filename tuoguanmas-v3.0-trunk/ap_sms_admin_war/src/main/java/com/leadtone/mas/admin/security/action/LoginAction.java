package com.leadtone.mas.admin.security.action;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.util.LoginCheckUtil;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
import com.leadtone.mas.bizplug.util.ResourcesComparator;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@ParentPackage("json-default")
@Namespace("/security")
public class LoginAction extends BaseAction {

	private static Logger log = Logger.getLogger(LoginAction.class);
	private static final long serialVersionUID = 1L;
	private UserVO userVO;
	private List<Resources> resources;
	private String verifyCode;
	@Resource(name = "userService")
	private UserService userServiceImpl;

	/**
	 * 先根据用户输入的用户名获取用户信息，然后再决定是否允许用户登录
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	@Action(value = "login", results = {
			@Result(name = SUCCESS, type = "redirect", location = "/main.jsp"),
			@Result(name = ERROR, location = "/login.jsp") })
	public String login() {
		MasPasswordTool pwdTool = new MasPasswordTool();
 		try {
			if (!StringUtils.endsWithIgnoreCase(
					this.getVerifyCode(),
					(String) ActionContext.getContext().getSession()
							.get("verifyCode"))) {
				this.getRequest().setAttribute("message", "验证码错误!");
				return ERROR;
			}
			userVO.setPassword(pwdTool.getEncString(userVO.getPassword(),
					userVO.getAccount()));
//			List<UserVO> users = userServiceImpl.validateLogin(userVO);
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("account", userVO.getAccount());
			paraMap.put("systype", 1);
			List<UserVO> users = userServiceImpl.validateLogin(paraMap);
			// 登录次数限制及验证
			if (users == null || users.isEmpty()) {
				this.getRequest().setAttribute("message", "用户名或密码错误!");
				return ERROR;
			} else if (!users.get(0).getPassword().equals(userVO.getPassword())) {
				String message = "用户名或密码错误!";
				if (LoginCheckUtil.isAccountlock(getSession(), users.get(0))) {
					UserVO uvo = users.get(0);
					uvo.setLockFlag(1);
					uvo.setPassword(pwdTool.getDesString(users.get(0).getPassword(),users.get(0).getAccount()));
					userServiceImpl.updateUser(uvo);
					message = "用户已被锁定，请联系管理员!";
				}
				this.getRequest().setAttribute("message", message);
				return ERROR;
			} else if (users.get(0).getLockFlag() == 1) {
				this.getRequest().setAttribute("message", "用户已被锁定，请联系管理员!");
				return ERROR;
			}
			// 组织系统菜单
			UserVO sessinUser = users.get(0);
			Set<RoleVO> roleVOs = sessinUser.getRoles();
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
			resources = new ArrayList<Resources>(tempResources);
			ResourcesComparator comparator = new ResourcesComparator();
			Collections.sort(resources, comparator);
			ActionContext.getContext().getSession()
					.put(ApSmsConstants.SESSION_USER_INFO, sessinUser);
			ActionContext.getContext().getSession().put("resources", resources);
		} catch (Exception e) {
			e.printStackTrace();
			this.entityMap = new HashMap<String, Object>();
			entityMap.put("message", "用户名或密码错误!");
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 退出系统，销毁SESSION，退回到登录界面
	 * 
	 * @return
	 */
	@Action(value = "logout", results = { @Result(name = SUCCESS, location = "/", type = "redirect") })
	public String logout() {
		ActionContext.getContext().getSession().clear();
		return SUCCESS;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public List<Resources> getResources() {
		return resources;
	}

	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}

}
