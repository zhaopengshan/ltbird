/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.statistic.bean;

import java.util.Date;
import org.apache.struts2.json.annotations.JSON;

/**
 *
 * @author blueskybluesea
 */
public class SmQueryResult {

    private String accountName;
    private Date smTime;
    private String communicationWay;
    private String smType;
    private String intfType;
    private String oppositeMobile;
    private int smSegments;
    private String result;
    private String failurReason;
    private String content;
    private int tunnelType;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getSmTime() {
        return smTime;
    }

    public void setSmTime(Date smTime) {
        this.smTime = smTime;
    }

    public String getCommunicationWay() {
        return communicationWay;
    }

    public void setCommunicationWay(String communicationWay) {
        this.communicationWay = communicationWay;
    }

    public String getSmType() {
        return smType;
    }

    public void setSmType(String smType) {
        this.smType = smType;
    }

    public String getIntfType() {
		return intfType;
	}

	public void setIntfType(String intfType) {
		this.intfType = intfType;
	}

	public String getOppositeMobile() {
        return oppositeMobile;
    }

    public void setOppositeMobile(String oppositeMobile) {
        this.oppositeMobile = oppositeMobile;
    }

    public int getSmSegments() {
        return smSegments;
    }

    public void setSmSegments(int smSegments) {
        this.smSegments = smSegments;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFailurReason() {
        return failurReason;
    }

    public void setFailurReason(String failurReason) {
        this.failurReason = failurReason;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTunnelType() {
		return tunnelType;
	}

	public void setTunnelType(int tunnelType) {
		this.tunnelType = tunnelType;
	}
}
