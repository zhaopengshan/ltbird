/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.security.service;

import com.leadtone.delegatemas.security.bean.Region;
import java.util.List;

/**
 *
 * @author blueskybluesea
 */
public interface IRegionService {
    public List<Region> findProvinces();
    public List<Region> findCityByProvinceId(Long provinceId);
    public Region findByProvinceId(Long provinceId);
}
