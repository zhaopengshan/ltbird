package com.leadtone.mas.bizplug.security.dao;

import com.leadtone.mas.bizplug.security.bean.Resources;
import java.util.List;

public interface ResourcesDao {

    public List<Resources> findAll();

    public List<Resources> findTopMenus();
    
    public List<Resources> findTopMenus(Integer isMobile, Integer activeFlag);

    public List<Resources> findTopMenusByRoleIds(List<Long> roleIds);
}
