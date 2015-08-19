package com.changyuan.misc.model.pojo;

import java.util.Date;

public class TbProvision {
    private Integer id;

    private Integer platuserid;

    private String msgtype;

    private String msisdnU;

    private Integer msisdnUUseridtype;

    private String msisdnUPseudocode;

    private String msisdnD;

    private Integer msisdnDUseridtype;

    private String msisdnDPseudocode;

    private Integer actionid;

    private Integer actionreasonid;

    private String pno;

    private String spserviceid;

    private String spid;

    private String version;

    private Date createtime;

    private Integer status;

    private String transactionid;

    private String linkid;

    private String featurestr;

    private Integer sendDevicetype;

    private String sendDeviceid;

    private Integer destDevicetype;

    private String destDeviceid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlatuserid() {
        return platuserid;
    }

    public void setPlatuserid(Integer platuserid) {
        this.platuserid = platuserid;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype == null ? null : msgtype.trim();
    }

    public String getMsisdnU() {
        return msisdnU;
    }

    public void setMsisdnU(String msisdnU) {
        this.msisdnU = msisdnU == null ? null : msisdnU.trim();
    }

    public Integer getMsisdnUUseridtype() {
        return msisdnUUseridtype;
    }

    public void setMsisdnUUseridtype(Integer msisdnUUseridtype) {
        this.msisdnUUseridtype = msisdnUUseridtype;
    }

    public String getMsisdnUPseudocode() {
        return msisdnUPseudocode;
    }

    public void setMsisdnUPseudocode(String msisdnUPseudocode) {
        this.msisdnUPseudocode = msisdnUPseudocode == null ? null : msisdnUPseudocode.trim();
    }

    public String getMsisdnD() {
        return msisdnD;
    }

    public void setMsisdnD(String msisdnD) {
        this.msisdnD = msisdnD == null ? null : msisdnD.trim();
    }

    public Integer getMsisdnDUseridtype() {
        return msisdnDUseridtype;
    }

    public void setMsisdnDUseridtype(Integer msisdnDUseridtype) {
        this.msisdnDUseridtype = msisdnDUseridtype;
    }

    public String getMsisdnDPseudocode() {
        return msisdnDPseudocode;
    }

    public void setMsisdnDPseudocode(String msisdnDPseudocode) {
        this.msisdnDPseudocode = msisdnDPseudocode == null ? null : msisdnDPseudocode.trim();
    }

    public Integer getActionid() {
        return actionid;
    }

    public void setActionid(Integer actionid) {
        this.actionid = actionid;
    }

    public Integer getActionreasonid() {
        return actionreasonid;
    }

    public void setActionreasonid(Integer actionreasonid) {
        this.actionreasonid = actionreasonid;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno == null ? null : pno.trim();
    }

    public String getSpserviceid() {
        return spserviceid;
    }

    public void setSpserviceid(String spserviceid) {
        this.spserviceid = spserviceid == null ? null : spserviceid.trim();
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid == null ? null : spid.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid == null ? null : transactionid.trim();
    }

    public String getLinkid() {
        return linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid == null ? null : linkid.trim();
    }

    public String getFeaturestr() {
        return featurestr;
    }

    public void setFeaturestr(String featurestr) {
        this.featurestr = featurestr == null ? null : featurestr.trim();
    }

    public Integer getSendDevicetype() {
        return sendDevicetype;
    }

    public void setSendDevicetype(Integer sendDevicetype) {
        this.sendDevicetype = sendDevicetype;
    }

    public String getSendDeviceid() {
        return sendDeviceid;
    }

    public void setSendDeviceid(String sendDeviceid) {
        this.sendDeviceid = sendDeviceid == null ? null : sendDeviceid.trim();
    }

    public Integer getDestDevicetype() {
        return destDevicetype;
    }

    public void setDestDevicetype(Integer destDevicetype) {
        this.destDevicetype = destDevicetype;
    }

    public String getDestDeviceid() {
        return destDeviceid;
    }

    public void setDestDeviceid(String destDeviceid) {
        this.destDeviceid = destDeviceid == null ? null : destDeviceid.trim();
    }
}