/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.bizplug.security.service;

import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.RoleVO;

/**
 *
 * @author blueskybluesea
 */
public interface IRoleService {
    public void createRoleInfo(RoleVO roleVO);
    public void updateRoleInfo(RoleVO roleVO);
    public void removeRoleInfo(Long[] deleteIds);
    public RoleVO viewRoleInfo(Long roleId, Long pinId);
    public void assignRolePrevilege(RoleVO roleVO);
    public Page paginateViewRoles(Role role, int pageSize, int pageNo);
    
    public List<Role> queryRoleByName(String name);
    
	/**
	 * 查询分页/模糊查询分页
	 * 
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Page page(PageUtil pageUtil);
        public List<Role> queryRoleByNameMerchantPin(String name,Long merchantPin);
}
