/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.tunnel.bean;

import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.webservice.bean.TunnelInfo;

import java.io.Serializable;
import java.util.Date;
import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

/**
 *
 * @author blueskybluesea
 */
public class MasTunnel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;			// '通道标识'
	private String name;		// '通道名称',
	private String accessNumber;// '特服号',
	private Long state;			// '0不可用；1可用',
	private Integer corpExtLen;	// '允许的企业扩展码位数',
	private Integer classify;	// '1本省移动；2移动；3本省联通；4联通；5本省电信；6电信；7全网',
	private String gatewayAddr;	// '网关地址',
	private Long gatewayPort;	// '端口',
	private String user;		// '用户名',
	private String passwd;		// '密码，DES算法加密',
	private String province;	// '所属省份',
	private Integer type;		// '1短信；2彩信；',
	private String description;	// '记录通道的成本等情况',
	private String smsSendPath;	// '短信网关发送URL',
	private String smsReceivePath;	// '短信网关接收URL',
	private String smsReportPath;	// '短信网关状态报告URL',
	private Date updateTime;	// '更新时间',
	private Date createTime;	// '创建时间',
	private Integer delStatus;	// '表示删除(0：显示，1：删除)',
	private Integer attribute;	// '通道属性：1直连网关；2第三方通道', 
    private String serviceId;
    private String smsCorpIdent;
    private String protocalVersion;
    private Integer tunnelRange;
	
	public MasTunnel() {
		super();
		 
	}
	/**
	 *ID构造，一般用于根据ID进行查询
	 * @param id
	 */
	public MasTunnel(Long id) {
		super();
		this.id = id;
	}
	public MasTunnel(TunnelInfo tunnelVo) {
		this.accessNumber = tunnelVo.getAccessNumber();
		this.attribute = tunnelVo.getAttribute();
		this.classify = tunnelVo.getClassify();
		this.corpExtLen = tunnelVo.getCorpExtLen();
		this.delStatus = tunnelVo.getDelStatus();
		this.description = tunnelVo.getDescription();
		this.gatewayAddr = tunnelVo.getGatewayAddr();
		this.gatewayPort = tunnelVo.getGatewayPort();
		this.id = tunnelVo.getId();
		this.name = tunnelVo.getName();
		this.passwd = tunnelVo.getPasswd();
		this.province = tunnelVo.getProvince();
		this.smsReceivePath = tunnelVo.getSmsReceivePath();
		this.smsReportPath = tunnelVo.getSmsReportPath();
		this.smsSendPath = tunnelVo.getSmsSendPath();
		this.state = tunnelVo.getState();
		this.type = tunnelVo.getType();
		this.user = tunnelVo.getUser();
		this.serviceId = tunnelVo.getServiceId();
		this.smsCorpIdent = tunnelVo.getSmsCorpIdent();
		this.protocalVersion = tunnelVo.getProtocalVersion();
		this.tunnelRange = tunnelVo.getTunnelRange();
	}
	public MasTunnel(SmsMbnTunnelVO tunnelVo) {
		this.accessNumber = tunnelVo.getAccessNumber();
		this.attribute = tunnelVo.getAttribute();
		this.classify = tunnelVo.getClassify();
		this.corpExtLen = tunnelVo.getCorpExtLen();
		this.createTime = tunnelVo.getCreateTime();
		this.delStatus = tunnelVo.getDelStatus();
		this.description = tunnelVo.getDescription();
		this.gatewayAddr = tunnelVo.getGatewayAddr();
		this.gatewayPort = tunnelVo.getGatewayPort();
		this.id = tunnelVo.getId();
		this.name = tunnelVo.getName();
		this.passwd = tunnelVo.getPasswd();
		this.province = tunnelVo.getProvince();
		this.smsReceivePath = tunnelVo.getSmsReceivePath();
		this.smsReportPath = tunnelVo.getSmsReportPath();
		this.smsSendPath = tunnelVo.getSmsSendPath();
		this.state = tunnelVo.getState();
		this.type = tunnelVo.getType();
		this.updateTime = tunnelVo.getUpdateTime();
		this.user = tunnelVo.getUser();
		this.tunnelRange = tunnelVo.getTunnelRange();
	}
	 
	/**
	 * 访问器
	 * @return
	 */
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAccessNumber() {
		return accessNumber;
	}
	public Long getState() {
		return state;
	}
	public Integer getCorpExtLen() {
		return corpExtLen;
	}
	public Integer getClassify() {
		return classify;
	}
	public String getGatewayAddr() {
		return gatewayAddr;
	}
	public Long getGatewayPort() {
		return gatewayPort;
	}
	public String getUser() {
		return user;
	}
	public String getPasswd() {
		return passwd;
	}
	public String getProvince() {
		return province;
	}
	public Integer getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public String getSmsSendPath() {
		return smsSendPath;
	}
	public String getSmsReceivePath() {
		return smsReceivePath;
	}
	public String getSmsReportPath() {
		return smsReportPath;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public Integer getDelStatus() {
		return delStatus;
	}
	public Integer getAttribute() {
		return attribute;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAccessNumber(String accessNumber) {
		this.accessNumber = accessNumber;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public void setCorpExtLen(Integer corpExtLen) {
		this.corpExtLen = corpExtLen;
	}
	public void setClassify(Integer classify) {
		this.classify = classify;
	}
	public void setGatewayAddr(String gatewayAddr) {
		this.gatewayAddr = gatewayAddr;
	}
	public void setGatewayPort(Long gatewayPort) {
		this.gatewayPort = gatewayPort;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setSmsSendPath(String smsSendPath) {
		this.smsSendPath = smsSendPath;
	}
	public void setSmsReceivePath(String smsReceivePath) {
		this.smsReceivePath = smsReceivePath;
	}
	public void setSmsReportPath(String smsReportPath) {
		this.smsReportPath = smsReportPath;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSmsCorpIdent() {
        return smsCorpIdent;
    }

    public void setSmsCorpIdent(String smsCorpIdent) {
        this.smsCorpIdent = smsCorpIdent;
    }

    public String getProtocalVersion() {
        return protocalVersion;
    }

    public void setProtocalVersion(String protocalVersion) {
        this.protocalVersion = protocalVersion;
    }
	public Integer getTunnelRange() {
		return tunnelRange;
	}
	public void setTunnelRange(Integer tunnelRange) {
		this.tunnelRange = tunnelRange;
	}
}
