/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.statistic.bean;

/**
 *
 * @author blueskybluesea
 */
public class SmSummary {

    private Long smSendSummary;
    private Long smSendSegmentsSummary;
    private Long smSendSuccessSummary;
    private Long smSendFailurSummary;
    private Long smReceiveSummary;
    private float smSuccessPercent;
    private float smFailurPercent;

    public Long getSmSendSummary() {
        return smSendSummary;
    }

    public void setSmSendSummary(Long smSendSummary) {
        this.smSendSummary = smSendSummary;
    }

    public Long getSmSendSegmentsSummary() {
        return smSendSegmentsSummary;
    }

    public void setSmSendSegmentsSummary(Long smSendSegmentsSummary) {
        this.smSendSegmentsSummary = smSendSegmentsSummary;
    }

    public Long getSmSendSuccessSummary() {
        return smSendSuccessSummary;
    }

    public void setSmSendSuccessSummary(Long smSendSuccessSummary) {
        this.smSendSuccessSummary = smSendSuccessSummary;
    }

    public Long getSmSendFailurSummary() {
        return smSendFailurSummary;
    }

    public void setSmSendFailurSummary(Long smSendFailurSummary) {
        this.smSendFailurSummary = smSendFailurSummary;
    }

    public Long getSmReceiveSummary() {
        return smReceiveSummary;
    }

    public void setSmReceiveSummary(Long smReceiveSummary) {
        this.smReceiveSummary = smReceiveSummary;
    }

    public float getSmSuccessPercent() {
        return smSuccessPercent;
    }

    public void setSmSuccessPercent(float smSuccessPercent) {
        this.smSuccessPercent = smSuccessPercent;
    }

    public float getSmFailurPercent() {
        return smFailurPercent;
    }

    public void setSmFailurPercent(float smFailurPercent) {
        this.smFailurPercent = smFailurPercent;
    }
    
}
