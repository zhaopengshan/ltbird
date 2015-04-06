/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.security.service;

import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.dao.IRegionDAO;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author blueskybluesea
 */
@Service("regionService")
@Transactional
public class RegionServiceImpl implements IRegionService {
    @Resource(name="regionDAOImpl")
    IRegionDAO regionDAO;
    @Override
    public List<Region> findProvinces() {
       return regionDAO.queryTopRegions();
    }

    @Override
    public List<Region> findCityByProvinceId(Long provinceId) {
        return regionDAO.queryRegionsByTopRegionId(provinceId);
    }

	@Override
	public Region findByProvinceId(Long provinceId) {
		// TODO Auto-generated method stub
		return regionDAO.queryByProvinceId(provinceId);
	}
    
}
