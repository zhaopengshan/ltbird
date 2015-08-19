/**
 * Project Name: ws-server <br/>
 * File Name: ProvisionBean.java <br/>
 * Package Name: com.changyuan.misc.bean <br/>
 * Date: 2015年8月17日上午9:46:30 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.changyuan.misc.model.bean;

import java.io.Serializable;

/**
 * ClassName: ProvisionBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月17日 上午9:46:30 <br/>
 * 
 * @author lenovo
 * @version 1.0.0
 * @see
 */
public class ProvisionBean implements Serializable {
    @Override
    public String toString() {
        return "ProvisionBean [id=" + id + ", msisdnu=" + MSISDN_U + ", MSISDN_D=" + MSISDN_D + ", TransactionID="
                + TransactionID + ", MsgType=" + MsgType + ", Version=" + Version + ", LinkID=" + LinkID
                + ", ActionID=" + ActionID + ", ActionReasonID=" + ActionReasonID + ", SPID=" + SPID + ", SPServiceID="
                + SPServiceID + ", FeatureStr=" + FeatureStr + ", platUserId=" + platUserId + ", createtime="
                + createtime + ", status=" + status + ", Pno=" + Pno + "]";
    }

    /**
     * serialVersionUID: TODO(用一句话描述这个变量表示什么).
     */
    private static final long serialVersionUID = 1L;
    private Long id; // 主键
    private String MSISDN_U; // 计费用户标识
    private String MSISDN_D; // 使用用户标识
    private String MsgType; // 消息类型
    private String TransactionID; // 消息编号
    private String Version;// 该接口消息的版本号，本次所有的接口消息的版本都为“1.5.0”
    // --------------------
    private String LinkID; // 临时订购关系的事务ID
    /*
     * 服务状态管理动作代码，具体值如下： 1： 开通服务； 2： 停止服务； 3： 激活服务； 4： 暂停服务； 5: 免费试用； 8: 第三方确认
     */
    private String ActionID;
    /*
     * 产生服务状态管理动作原因的代码，具体值如下： 1：用户发起行为 2：系统发起行为 3：扣费失败导致的服务取消 4：其他
     */
    private String ActionReasonID;
    private String SPID; // SP的企业代码
    private String SPServiceID; // SP中该服务的服务代码
    private String FeatureStr; // 服务订购参数在短信MO流程中，FeatureStr中传送的是AccessNo+短信内容。
    private String platUserId;// 平台侧用户id
    private String createtime; // 创建时间
    private String status;// 状态0为订购成功，1为未成功
    private String Pno; // 产品编号

    public String getMSISDN_U() {
        return MSISDN_U;
    }

    public void setMSISDN_U(String msisdnu) {
        this.MSISDN_U = msisdnu;
    }

    public String getMSISDN_D() {
        return MSISDN_D;
    }

    public void setMSISDN_D(String mSISDND) {
        MSISDN_D = mSISDND;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getLinkID() {
        return LinkID;
    }

    public void setLinkID(String linkID) {
        LinkID = linkID;
    }

    public String getActionID() {
        return ActionID;
    }

    public void setActionID(String actionID) {
        ActionID = actionID;
    }

    public String getActionReasonID() {
        return ActionReasonID;
    }

    public void setActionReasonID(String actionReasonID) {
        ActionReasonID = actionReasonID;
    }

    public String getSPID() {
        return SPID;
    }

    public void setSPID(String sPID) {
        SPID = sPID;
    }

    public String getSPServiceID() {
        return SPServiceID;
    }

    public void setSPServiceID(String sPServiceID) {
        SPServiceID = sPServiceID;
    }

    public String getFeatureStr() {
        return FeatureStr;
    }

    public void setFeatureStr(String featureStr) {
        FeatureStr = featureStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatUserId() {
        return platUserId;
    }

    public void setPlatUserId(String platUserId) {
        this.platUserId = platUserId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPno() {
        return Pno;
    }

    public void setPno(String pno) {
        Pno = pno;
    }
}
