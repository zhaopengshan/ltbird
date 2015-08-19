/**
 * Project Name: ws-server <br/>
 * File Name: ProvisionBean.java <br/>
 * Package Name: com.changyuan.misc.bean <br/>
 * Date: 2015年8月17日上午9:46:30 <br/>
 * Copyright (c) 2015, lenovo All Rights Reserved.
 */
package com.changyuan.misc.model.bean;

import java.io.Serializable;

import org.joda.time.DateTime;

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
    /**
     * serialVersionUID: TODO(用一句话描述这个变量表示什么).
     */
    private static final long serialVersionUID = 1L;
    private Integer id; // 主键
    private String platUserId;// 平台侧用户id 暂时为空
    private String MsgType; // 消息类型SyncOrderRelationReq，SyncOrderRelationResp
    // <FeeUser_ID><UserIDType>1</UserIDType><MSISDN>13681463548</MSISDN><PseudoCode></PseudoCode></FeeUser_ID>
    private String MSISDN_U; // 计费用户标识=MSISDN
    private Integer MSISDN_U_UserIDType; // 计费用户类型
    private String MSISDN_U_PseudoCode; // 计费用户伪码
    // <DestUser_ID><UserIDType>1</UserIDType><MSISDN>13681463548</MSISDN><PseudoCode></PseudoCode></DestUser_ID>
    private String MSISDN_D; // 使用用户标识=MSISDN
    private Integer MSISDN_D_UserIDType; // 使用用户类型
    private String MSISDN_D_PseudoCode; // 使用用户伪码
    /*
     * 服务状态管理动作代码，具体值如下： 1： 开通服务； 2： 停止服务； 3： 激活服务； 4： 暂停服务； 5: 免费试用； 8: 第三方确认
     */
    private Integer ActionID;
    /*
     * 产生服务状态管理动作原因的代码，具体值如下： 1：用户发起行为 2：系统发起行为 3：扣费失败导致的服务取消 4：其他
     */
    private Integer ActionReasonID;
    private String Pno; // 产品编号
    private String SPServiceID; // SP中该服务的服务代码
    private String SPID; // SP的企业代码
    private String Version;// 该接口消息的版本号，本次所有的接口消息的版本都为“1.5.0”
    private DateTime createtime; // 创建时间
    private Integer status;// 状态0为订购成功，1为未成功
    private String TransactionID; // 消息编号
    // --------------------
    private String LinkID; // 临时订购关系的事务ID
    private String FeatureStr; // 服务订购参数在短信MO流程中，FeatureStr中传送的是AccessNo+短信内容。
    // <Send_Address><DeviceType>0</DeviceType><DeviceID>0001</DeviceID></Send_Address>
    private Integer Send_DeviceType;
    private String Send_DeviceID;
    // <Dest_Address><DeviceType>400</DeviceType><DeviceID>0</DeviceID></Dest_Address>
    private Integer Dest_DeviceType;
    private String Dest_DeviceID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatUserId() {
        return platUserId;
    }

    public void setPlatUserId(String platUserId) {
        this.platUserId = platUserId;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getMSISDN_U() {
        return MSISDN_U;
    }

    public void setMSISDN_U(String mSISDN_U) {
        MSISDN_U = mSISDN_U;
    }

    public Integer getMSISDN_U_UserIDType() {
        return MSISDN_U_UserIDType;
    }

    public void setMSISDN_U_UserIDType(Integer mSISDN_U_UserIDType) {
        MSISDN_U_UserIDType = mSISDN_U_UserIDType;
    }

    public String getMSISDN_U_PseudoCode() {
        return MSISDN_U_PseudoCode;
    }

    public void setMSISDN_U_PseudoCode(String mSISDN_U_PseudoCode) {
        MSISDN_U_PseudoCode = mSISDN_U_PseudoCode;
    }

    public String getMSISDN_D() {
        return MSISDN_D;
    }

    public void setMSISDN_D(String mSISDN_D) {
        MSISDN_D = mSISDN_D;
    }

    public Integer getMSISDN_D_UserIDType() {
        return MSISDN_D_UserIDType;
    }

    public void setMSISDN_D_UserIDType(Integer mSISDN_D_UserIDType) {
        MSISDN_D_UserIDType = mSISDN_D_UserIDType;
    }

    public String getMSISDN_D_PseudoCode() {
        return MSISDN_D_PseudoCode;
    }

    public void setMSISDN_D_PseudoCode(String mSISDN_D_PseudoCode) {
        MSISDN_D_PseudoCode = mSISDN_D_PseudoCode;
    }

    public Integer getActionID() {
        return ActionID;
    }

    public void setActionID(Integer actionID) {
        ActionID = actionID;
    }

    public Integer getActionReasonID() {
        return ActionReasonID;
    }

    public void setActionReasonID(Integer actionReasonID) {
        ActionReasonID = actionReasonID;
    }

    public String getPno() {
        return Pno;
    }

    public void setPno(String pno) {
        Pno = pno;
    }

    public String getSPServiceID() {
        return SPServiceID;
    }

    public void setSPServiceID(String sPServiceID) {
        SPServiceID = sPServiceID;
    }

    public String getSPID() {
        return SPID;
    }

    public void setSPID(String sPID) {
        SPID = sPID;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public DateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(DateTime createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getLinkID() {
        return LinkID;
    }

    public void setLinkID(String linkID) {
        LinkID = linkID;
    }

    public String getFeatureStr() {
        return FeatureStr;
    }

    public void setFeatureStr(String featureStr) {
        FeatureStr = featureStr;
    }

    public Integer getSend_DeviceType() {
        return Send_DeviceType;
    }

    public void setSend_DeviceType(Integer send_DeviceType) {
        Send_DeviceType = send_DeviceType;
    }

    public String getSend_DeviceID() {
        return Send_DeviceID;
    }

    public void setSend_DeviceID(String send_DeviceID) {
        Send_DeviceID = send_DeviceID;
    }

    public Integer getDest_DeviceType() {
        return Dest_DeviceType;
    }

    public void setDest_DeviceType(Integer dest_DeviceType) {
        Dest_DeviceType = dest_DeviceType;
    }

    public String getDest_DeviceID() {
        return Dest_DeviceID;
    }

    public void setDest_DeviceID(String dest_DeviceID) {
        Dest_DeviceID = dest_DeviceID;
    }

    @Override
    public String toString() {
        return "ProvisionBean [id=" + id + ", platUserId=" + platUserId + ", MsgType=" + MsgType + ", MSISDN_U="
                + MSISDN_U + ", MSISDN_U_UserIDType=" + MSISDN_U_UserIDType + ", MSISDN_U_PseudoCode="
                + MSISDN_U_PseudoCode + ", MSISDN_D=" + MSISDN_D + ", MSISDN_D_UserIDType=" + MSISDN_D_UserIDType
                + ", MSISDN_D_PseudoCode=" + MSISDN_D_PseudoCode + ", ActionID=" + ActionID + ", ActionReasonID="
                + ActionReasonID + ", Pno=" + Pno + ", SPServiceID=" + SPServiceID + ", SPID=" + SPID + ", Version="
                + Version + ", createtime=" + createtime + ", status=" + status + ", TransactionID=" + TransactionID
                + ", LinkID=" + LinkID + ", FeatureStr=" + FeatureStr + ", Send_DeviceType=" + Send_DeviceType
                + ", Send_DeviceID=" + Send_DeviceID + ", Dest_DeviceType=" + Dest_DeviceType + ", Dest_DeviceID="
                + Dest_DeviceID + "]";
    }
}
