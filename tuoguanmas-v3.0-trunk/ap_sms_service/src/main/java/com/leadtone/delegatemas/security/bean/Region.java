/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.security.bean;

import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import java.util.List;

/**
 *
 * @author blueskybluesea
 */
public class Region {
    private Long id;
    private String name;
    private String code;
    private Long parentId;
    private List<Region> subRegions;
    private List<MbnMerchantVip> merchantVip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Region> getSubRegions() {
        return subRegions;
    }

    public void setSubRegions(List<Region> subRegions) {
        this.subRegions = subRegions;
    }

    public List<MbnMerchantVip> getMerchantVip() {
        return merchantVip;
    }

    public void setMerchantVip(List<MbnMerchantVip> merchantVip) {
        this.merchantVip = merchantVip;
    }
    
}
