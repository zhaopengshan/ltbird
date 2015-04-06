package com.leadtone.mas.bizplug.security.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.security.bean.Resources;

@Component("resourcesDao")
@SuppressWarnings("unchecked")
public class ResourcesDaoImpl extends BaseDao implements ResourcesDao {

    @Override
    public List<Resources> findAll() {
        return getSqlMapClientTemplate().queryForList("security.queryAllResources");
    }

    @Override
    public List<Resources> findTopMenus() {
        return getSqlMapClientTemplate().queryForList("security.queryTopResources");
    }

    @Override
    public List<Resources> findTopMenusByRoleIds(List<Long> roleIds) {
        Map map = new HashMap();
        map.put("roleIdList", roleIds);
        return getSqlMapClientTemplate().queryForList("security.queryTopResourcesByRoleIds", map);
    }

    @Override
    public List<Resources> findTopMenus(Integer isMobile, Integer activeFlag) {
        Map<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("isMobile", isMobile);
        paraMap.put("activeFlag", activeFlag);
        return getSqlMapClientTemplate().queryForList("security.queryTopResourcesByIsMobile",paraMap);
    }
}
