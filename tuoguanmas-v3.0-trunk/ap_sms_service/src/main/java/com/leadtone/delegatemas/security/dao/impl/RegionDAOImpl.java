/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.security.dao.impl;

import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.dao.IRegionDAO;
import com.leadtone.mas.bizplug.dao.BaseDao;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author blueskybluesea
 */
@Repository("regionDAOImpl")
public class RegionDAOImpl extends BaseDao implements IRegionDAO{
    private static String namespace = "security";
    @Override
    public List<Region> queryTopRegions() {
        return this.getSqlMapClientTemplate().queryForList(namespace+".queryTopRegions");
    }

    @Override
    public List<Region> queryRegionsByTopRegionId(Long provinceId) {
        return this.getSqlMapClientTemplate().queryForList(namespace+".queryRegionsOfProvince", provinceId);
    }

    @Override
    public List<Region> queryRegionsByProvinceId(Long provinceId) {
        return this.getSqlMapClientTemplate().queryForList(namespace+".queryRegionsByProvinceId", provinceId);
    }

	@Override
	public Region queryByProvinceId(Long provinceId) {
		return (Region)this.getSqlMapClientTemplate().queryForObject(namespace+".queryRegionsByProvinceId", provinceId);
	}
    
}
