/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.merchant.bean;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

/**
 *
 * @author blueskybluesea
 */
public class PointsCityStatistic {
    private Long cityId;
    private String cityName;
    private Integer corpCount;
    private String loginAccount;
    private String loginPwd;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCorpCount() {
        return corpCount;
    }

    public void setCorpCount(Integer corpCount) {
        this.corpCount = corpCount;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
    @JSONFieldBridge(impl = StringBridge.class)
    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    
}
