/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.security.service;

import com.leadtone.mas.bizplug.security.bean.Resources;
import java.util.List;

/**
 *
 * @author blueskybluesea
 */
public interface IMasResourceService {
    public List<Resources> findTopMenusByIsMobile(Integer mobileMenu, Integer activeFlag);
}
