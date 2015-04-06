/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.bizplug.security.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.dao.IRoleDAO;
import com.leadtone.mas.bizplug.security.dao.ResourcesDao;
import com.leadtone.mas.bizplug.security.dao.UsersDao;

/**
 *
 * @author blueskybluesea
 */
@Service("roleServiceImpl")
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Resource(name="roleDAOImpl")
    IRoleDAO roleDAO;
    @Resource(name="resourcesDao")
    ResourcesDao resourceDAO;
    @Resource(name="usersDao")
    UsersDao userDAO;
    @Override
    public void createRoleInfo(RoleVO roleVO) {
        roleVO.setId(PinGen.getSerialPin());
        roleVO.setCreateTime(Calendar.getInstance().getTime());
        roleDAO.addRole(roleVO);
        if(roleVO.getUsers().size() != 0){
        	roleDAO.addRoleUsers(roleVO);
        }
        roleDAO.addRoleResources(roleVO);        
    }

    @Override
    public void updateRoleInfo(RoleVO roleVO) {
        List<Long> readyRemoveRoles = new ArrayList<Long>();
        readyRemoveRoles.add(roleVO.getId());
        roleVO.setUpdateTime(Calendar.getInstance().getTime());
        roleDAO.updateRole(roleVO);
        roleDAO.removeRoleUsers(readyRemoveRoles);
        roleDAO.removeRoleResources(readyRemoveRoles);
        roleDAO.addRoleUsers(roleVO);
        roleDAO.addRoleResources(roleVO);
    }

    @Override
    public void removeRoleInfo(Long[] deleteIds) {
        List<Long> deleteRoles = new ArrayList<Long>();
        for(Long roleIds : deleteIds) {
        	deleteRoles.add(roleIds);
        }
        System.out.println("delete roles List: "+ deleteIds);
        roleDAO.removeRoleUsers(deleteRoles);
        roleDAO.removeRoleResources(deleteRoles);
        roleDAO.removeRoles(deleteRoles);
    }

    @Override
    public RoleVO viewRoleInfo(Long roleId, Long pinId) {
        RoleVO roleUsers = roleDAO.viewRoleUsers(roleId, pinId);
        RoleVO roleResources = roleDAO.viewRoleResources(roleId);
        if(roleUsers != null)
        	roleResources.setUsers(roleUsers.getUsers());
        return roleResources;
    }

    @Override
    public void assignRolePrevilege(RoleVO roleVO) {
        List<Long> readyRemoveRoleResources = new ArrayList<Long>();      
        readyRemoveRoleResources.add(roleVO.getId());
        roleDAO.removeRoleResources(readyRemoveRoleResources);
        roleDAO.addRoleResources(roleVO);
    }

	@Override
	public List<Role> queryRoleByName(String name) {
		return roleDAO.queryRoleByName(name);
	}

	@Override
	public Page page(PageUtil pageUtil) {
		return roleDAO.page(pageUtil);
	}

	@Override
	public Page paginateViewRoles(Role role, int pageSize, int pageNo) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<Role> queryRoleByNameMerchantPin(String name, Long merchantPin) {
        Map<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("name", name);
        paraMap.put("merchantPin", merchantPin);
        return this.roleDAO.queryRoleByNameMerchantPin(paraMap);
    }
}
