package com.leadtone.mas.bizplug.security.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.dao.ResourcesDao;
import com.leadtone.mas.bizplug.security.dao.UsersDao;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuService {
	@Resource
	private ResourcesDao resourcesDao;
	@Resource
	private UsersDao userDao;

	@Override
	public List<Resources> findAll() {
		return resourcesDao.findAll();
	}

	public ResourcesDao getResourcesDao() {
		return resourcesDao;
	}

	public void setResourcesDao(ResourcesDao resourcesDao) {
		this.resourcesDao = resourcesDao;
	}


	@Override
	public List<Resources> findTopMenus() {
		// TODO Auto-generated method stub
		return this.resourcesDao.findTopMenus();
	}

	@Override
	public List<Resources> findTopMenusByAccount(String username) {
		// TODO Auto-generated method stub
		List<Long> roleIds = new ArrayList<Long>();
		UserVO user = this.userDao.findByName(username);
		if (user != null && user.getRoles() != null) {
			for(RoleVO r: user.getRoles()){
				roleIds.add(r.getId());
			}
		}
		return this.resourcesDao.findTopMenusByRoleIds(roleIds);
	}

}
