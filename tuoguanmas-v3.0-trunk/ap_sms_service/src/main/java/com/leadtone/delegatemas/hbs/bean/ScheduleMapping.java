/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.hbs.bean;

/**
 *
 * @author blueskybluesea
 */
public class ScheduleMapping {
    private String gatewayId;
    private String provinceCode;
    private String gwIp;
    private Integer gwPort;
    private String gwUser;
    private String gwPasswd;
    private String enterpriseCode;
    private String protocolVersion;
    private String senderCode;
    private String serviceCode;
//    private String runStatus;

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getGwIp() {
        return gwIp;
    }

    public void setGwIp(String gwIp) {
        this.gwIp = gwIp;
    }

    public Integer getGwPort() {
        return gwPort;
    }

    public void setGwPort(Integer gwPort) {
        this.gwPort = gwPort;
    }

    public String getGwUser() {
        return gwUser;
    }

    public void setGwUser(String gwUser) {
        this.gwUser = gwUser;
    }

    public String getGwPasswd() {
        return gwPasswd;
    }

    public void setGwPasswd(String gwPasswd) {
        this.gwPasswd = gwPasswd;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getSenderCode() {
        return senderCode;
    }

    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

//	public String getRunStatus() {
//		return runStatus;
//	}
//
//	public void setRunStatus(String runStatus) {
//		this.runStatus = runStatus;
//	}
    
}
