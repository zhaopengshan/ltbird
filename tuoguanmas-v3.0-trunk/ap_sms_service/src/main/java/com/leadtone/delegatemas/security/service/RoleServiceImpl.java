/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.security.service;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.service.IRoleService;
import java.util.List;

/**
 *
 * @author blueskybluesea
 */
public class RoleServiceImpl  implements IRoleService{

    @Override
    public void createRoleInfo(RoleVO roleVO) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateRoleInfo(RoleVO roleVO) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeRoleInfo(Long[] deleteIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RoleVO viewRoleInfo(Long roleId, Long pinId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assignRolePrevilege(RoleVO roleVO) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Page paginateViewRoles(Role role, int pageSize, int pageNo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Role> queryRoleByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Page page(PageUtil pageUtil) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Role> queryRoleByNameMerchantPin(String name, Long merchantPin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
