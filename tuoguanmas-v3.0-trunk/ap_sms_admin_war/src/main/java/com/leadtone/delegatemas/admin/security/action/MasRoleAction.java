/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.admin.security.action;

import com.leadtone.delegatemas.security.service.IMasResourceService;
import com.leadtone.mas.admin.common.ApSmsConstants;
import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.IRoleService;
import com.leadtone.mas.bizplug.security.service.MenuService;
import com.leadtone.mas.bizplug.security.service.UserService;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

/**
 *
 * @author blueskybluesea
 */
@ParentPackage("json-default")
@Namespace(value = "/roleAction")
public class MasRoleAction extends BaseAction {

    private String flag;
    // 角色实体类
    private RoleVO portalRole;
    private HttpServletRequest request = ServletActionContext.getRequest();
    @Resource(name = "masResourceServiceImpl")
    private IMasResourceService masMenuService;
    @Resource
    private UserService userService;
    @Resource
    private IRoleService roleService;
    @Resource(name = "MbnMerchantVipIService")
    private MbnMerchantVipIService mbnMerchantVipIService;
    private Users loginUser = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);

    /**
     * 初始化新增角色，修改角色，授权页面 查询所有的用户和所有的资源
     */
    @Action(value = "masforward", results = {
        @Result(name = "forward", location = "/ap/role/roleadd.jsp"),
        @Result(name = "powerForward", location = "/ap/role/rolepower.jsp"),
        @Result(name = ERROR, location = "/error.jsp"),
        @Result(name = INPUT, location = "/ap/role/roleadd.jsp")})
    public String forward() {
        List<Resources> resources = null;
        MbnMerchantVip merchant = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
        List<Users> userList = null;        
        switch (loginUser.getUserType()) {
            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
                resources = masMenuService.findTopMenusByIsMobile(ApSmsConstants.IS_MOBILE_CITY, null);
                userList = userService.findUsersByUserTypeRegion(Long.parseLong(merchant.getProvince()), null, ApSmsConstants.USER_TYPE_CITY_ADMIN);
                break;
            case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
                resources = masMenuService.findTopMenusByIsMobile(ApSmsConstants.IS_MOBILE_PRIVILEGE, null);
                userList = userService.findUsersByUserTypeRegion(null, null, ApSmsConstants.USER_TYPE_PROVINCE_ADMIN);
                break;
            case ApSmsConstants.USER_TYPE_CITY_ADMIN:
                resources = masMenuService.findTopMenusByIsMobile(ApSmsConstants.NOT_IS_MOBILE_PRIVILEGE, null);
                userList = userService.findUsersByUserTypeRegion(Long.parseLong(merchant.getProvince()), Long.parseLong(merchant.getCity()), ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN);
                break;
            default:
                resources = masMenuService.findTopMenusByIsMobile(ApSmsConstants.NOT_IS_MOBILE_PRIVILEGE, 1);
                userList = userService.getAllUser(loginUser.getMerchantPin());
                break;
        }

        if ("addForward".equals(flag)) {
            // 初次进入新增页面，得初始化授权资源
            request.setAttribute("resourceList", resources);
            // 初始化用户列表
            request.setAttribute("userList", userList);
        } else if ("updateForward".equals(flag)) {
            // 修改角色前，查询角色信息，然后显示在修改(即新增)页面
            RoleVO roleVO = roleService.viewRoleInfo(portalRole.getId(), null);
            Set<Users> roleUsers = roleVO.getUsers();
            if (roleUsers != null) {
                List<Users> delList = new ArrayList<Users>();
                // 从所有的用户里删除此角色拥有的用户，待选 用户里显示
                for (Iterator<Users> uIterator = roleUsers.iterator(); uIterator.hasNext();) {
                    Users _roleUser = uIterator.next();
                    for (Users _user : userList) {
                        if (_roleUser.getId().equals(_user.getId())) {
                            delList.add(_user);
                        }
                    }
                }
                userList.removeAll(delList);
            }
            // 查询角色信息
            request.setAttribute("roleVO", roleVO);
            // 初次进入新增页面，得初始化授权资源
            request.setAttribute("resourceList", resources);
            // 初始化用户列表
            request.setAttribute("userList", userList);
            logger.debug("roleVO: " + roleVO);
        } else if ("powerForward".equals(flag)) {
            // 初次进入授权页面，得初始化授权资源
            request.setAttribute("resourceList", resources);
            RoleVO roleVO = roleService.viewRoleInfo(portalRole.getId(), loginUser.getMerchantPin());
            if (null == roleVO) {
                roleVO = new RoleVO();
                roleVO.setId(portalRole.getId());
                roleVO.setName(portalRole.getName());
            }
            logger.debug("givePower: roleVO->" + roleVO);
            // 查询角色信息
            request.setAttribute("roleVO", roleVO);
            return "powerForward";
        }
        logger.debug("resourcesList-->" + resources + "; userList->" + userList);
        return "forward";
    }

    /**
     * 查询角色信息 分页查询
     *
     * @return
     * @throws Exception
     */
    @Action(value = "masquery", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String query() {
        PageUtil pageUtil = new PageUtil();
        pageUtil.setStart(page);
        pageUtil.setPageSize(rows);
        pageUtil.setMerchantPin(loginUser.getMerchantPin());
        if (portalRole != null) {
            pageUtil.setRoleName(URLDecoder.decode(portalRole.getName()));
            pageUtil.setRoleDesc(URLDecoder.decode(portalRole.getDescription()));
        }
        try {
            logger.info("role query portalRole:" + portalRole);
            logger.info("role query PageUtil:" + pageUtil);
            Page page = roleService.page(pageUtil);
            if (page != null) {
                @SuppressWarnings("unchecked")
                List<Role> datas = (List<Role>) page.getData();
                entityMap = new HashMap<String, Object>();
                entityMap.put("total", page.getRecords());
                if (datas == null) {
                    datas = new ArrayList<Role>();
                }
                entityMap.put("rows", datas);
                entityMap.put("totalrecords", page.getTotal());
                entityMap.put("currpage", page.getStart());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public RoleVO getPortalRole() {
        return portalRole;
    }

    public void setPortalRole(RoleVO portalRole) {
        this.portalRole = portalRole;
    }
}
