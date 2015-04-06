/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.security.dao;

import com.leadtone.delegatemas.security.bean.Region;
import java.util.List;

/**
 *
 * @author blueskybluesea
 */
public interface IRegionDAO {
    public List<Region> queryTopRegions();
    public List<Region> queryRegionsByTopRegionId(Long provinceId);
    public List<Region> queryRegionsByProvinceId(Long provinceId);
    public Region queryByProvinceId(Long provinceId);
}

