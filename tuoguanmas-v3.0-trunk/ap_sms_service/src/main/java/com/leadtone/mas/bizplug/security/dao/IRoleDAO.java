/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.bizplug.security.dao;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.dao.base.PageBaseIDao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author blueskybluesea
 */
public interface IRoleDAO extends PageBaseIDao{
    public List<Role> findAllRoles();
    public List<Role> pageFindRoles(Role queryCondition,int PageNo, int PageSize);
    public void addRole(Role role);
    public void updateRole(Role role);
    public void addRoleUsers(RoleVO roleVO);
    public void removeRoleUsers(List<Long> roleUserIds);
    public RoleVO viewRoleResources(Long roleId);
    public RoleVO viewRoleUsers(Long roleId, Long pinId);
    public void removeRoles(List<Long> roleIds);
    public List<RoleVO> findRolesByUserId(Long userId);
    public void addRoleResources(RoleVO roleVO);
    public void removeRoleResources(List<Long> roleResourceIds);
    public List<Role> queryRoleByName(String name);
    public List<Role> queryRoleByNameMerchantPin(Map<String,Object> paraMap);
}
