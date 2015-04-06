/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.security.service;

import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.dao.ResourcesDao;
import com.leadtone.mas.bizplug.security.service.MenuService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author blueskybluesea
 */
@Service("masResourceServiceImpl")
public class MasResourceServiceImpl implements IMasResourceService,MenuService {
    @Resource(name="menuService")
    MenuService menuService;
    @Resource(name="resourcesDao")
    ResourcesDao resourcesDao;
    @Override
    public List<Resources> findAll() {
       return menuService.findAll();
    }

    @Override
    public List<Resources> findTopMenus() {
        return menuService.findTopMenus();
    }

    @Override
    public List<Resources> findTopMenusByAccount(String username) {
        return menuService.findTopMenusByAccount(username);
    }

    @Override
    public List<Resources> findTopMenusByIsMobile(Integer mobileMenu, Integer activeFlag) {
        return resourcesDao.findTopMenus(mobileMenu, activeFlag);
    }
    
}
