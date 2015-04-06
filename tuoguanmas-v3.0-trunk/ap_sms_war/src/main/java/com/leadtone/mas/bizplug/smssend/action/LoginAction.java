package com.leadtone.mas.bizplug.smssend.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.service.MenuService;
import com.leadtone.mas.bizplug.security.service.SecurityUserHolder;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.util.ResourcesComparator;

@ParentPackage("json-default")
@Namespace(value = "/loginAction")
public class LoginAction extends BaseAction {
	private static Logger log = Logger.getLogger(LoginAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;

	/**
	 * 先根据用户输入的用户名获取用户信息，然后再决定是否允许用户登录
	 * 
	 * @return
	 */
	@Override
	@Action(value = "login", results = {
			@Result(name = SUCCESS, location = "/main.jsp"),
			@Result(name = INPUT, location = "/login.jsp") })
	public String execute() {
		// 设置登陆用户信息
		User securityUser = SecurityUserHolder.getCurrentUser();
		// Users user = userService.getUserByAccount(securityUser.getUsername());
		// 获取用户菜单
		UserVO userVo = userService.getUserByAccount(securityUser.getUsername());
		super.getSession().setAttribute(ApSmsConstants.SESSION_USER_INFO, userVo);
        Set<RoleVO> roleVOs = userVo.getRoles();
        Set<Resources> tempResources = null;
        for(RoleVO roleVO : roleVOs) {
             Set<Resources> parentResources = roleVO.getResources();
             if(tempResources == null) {
                 tempResources = parentResources;
             } else {
                 tempResources.addAll(parentResources);
                 for(Resources resource : tempResources) {
                     for(Resources undoResource : parentResources) {
                         if(resource.getId() == undoResource.getId()) {
                             resource.getSubResources().addAll(undoResource.getSubResources());
                         }
                     }
                 }
             }
        }
        if(tempResources == null || tempResources.isEmpty()) {
            this.getRequest().setAttribute("message", "对不起,用户未被赋予访问系统权限!");
            return ERROR;
        }

        List<Resources> resList = new ArrayList<Resources>();
        ResourcesComparator comparator = new ResourcesComparator();
        // 排序二级菜单
        List<Resources> tempList = new ArrayList<Resources>(tempResources);
        for( Resources res: tempList){
        	// 只保留一级菜单 非管理功能菜单
        	if( res.getParentId() > 0 || res.getIsManagementFun() == 1){
        		continue;
        	}
        	if( res.getSubResources() != null){
        		List<Resources> subList = new ArrayList<Resources>(res.getSubResources());
        		Collections.sort(subList, comparator);
        		res.setSortedSubRes(subList);
        		resList.add(res);
        	}
        }
        // 排序主菜单
        Collections.sort(resList, comparator);
		this.getRequest().setAttribute("resourcesList", resList);
		return "success";
	}

	/**
	 * 退出系统，销毁SESSION，退回到登录界面
	 * 
	 * @return
	 */
	public String logout() {
		return "true";
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
