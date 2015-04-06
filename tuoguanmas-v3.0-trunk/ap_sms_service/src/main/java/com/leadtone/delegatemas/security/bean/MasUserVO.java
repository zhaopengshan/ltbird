/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.security.bean;

import com.leadtone.mas.bizplug.security.bean.Users;
import java.util.List;

/**
 *
 * @author blueskybluesea
 */
public class MasUserVO extends Users{
    private List<Region> regions;

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
    
}
