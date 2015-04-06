/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.bizplug.security.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

/**
 *
 * @author blueskybluesea
 */
public class Role {
    private Long id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private Long createBy;
    private Long updateBy;
    private String description;
    private int activeFlag;
    private Long merchantPin;
    
    @JSONFieldBridge(impl = StringBridge.class)
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getMerchantPin() {
        return merchantPin;
    }

    public void setMerchantPin(Long merchantPin) {
        this.merchantPin = merchantPin;
    }
    
}
