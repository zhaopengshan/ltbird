package com.leadtone.mas.bizplug.webservice.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;

/**
 * 
 * @author PAN-Z-G 通道类
 */
public class TunnelInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlTransient
	private Long id; // '通道标识'
	@XmlTransient
	private String name; // '通道名称',
	@XmlTransient
	private String accessNumber;// '特服号',
	@XmlTransient
	private Long state; // '0不可用；1可用',
	@XmlTransient
	private Integer corpExtLen; // '允许的企业扩展码位数',
	@XmlTransient
	private Integer classify; // '1本省移动；2移动；3本省联通；4联通；5本省电信；6电信；7全网',
	@XmlTransient
	private String gatewayAddr; // '网关地址',
	@XmlTransient
	private Long gatewayPort; // '端口',
	@XmlTransient
	private String user; // '用户名',
	@XmlTransient
	private String passwd; // '密码，DES算法加密',
	@XmlTransient
	private String province; // '所属省份',
	@XmlTransient
	private Integer type; // '1短信；2彩信；',
	@XmlTransient
	private String description; // '记录通道的成本等情况',
	@XmlTransient
	private String smsSendPath; // '短信网关发送URL',
	@XmlTransient
	private String smsReceivePath; // '短信网关接收URL',
	@XmlTransient
	private String smsReportPath; // '短信网关状态报告URL',
	@XmlTransient
	private Integer delStatus; // '表示删除(0：显示，1：删除)',
	@XmlTransient
	private Integer attribute; // '通道属性：1直连网关；2第三方通道',
	@XmlTransient
	private String serviceId; // 短信业务代码
	@XmlTransient
	private String smsCorpIdent; // 短信企业代码
	@XmlTransient
	private String protocalVersion; // 协议版本
	@XmlTransient
	private Long merchantPin;
	@XmlTransient
	private Integer tunnelRange;

	public TunnelInfo() {
		super();

	}

	/**
	 *ID构造，一般用于根据ID进行查询
	 * 
	 * @param id
	 */
	public TunnelInfo(Long id) {
		super();
		this.id = id;
	}

	public TunnelInfo(SmsMbnTunnelVO tunnelVo) {
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
		this.tunnelRange = tunnelVo.getTunnelRange();
	}

	/**
	 * 访问器
	 * 
	 * @return
	 */
	@JSONFieldBridge(impl = StringBridge.class)
	@XmlElement(name = "id")
	public Long getId() {
		return id;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	@XmlElement(name = "accessnumber")
	public String getAccessNumber() {
		return accessNumber;
	}

	@XmlElement(name = "state")
	public Long getState() {
		return state;
	}

	@XmlElement(name = "corpextlen")
	public Integer getCorpExtLen() {
		return corpExtLen;
	}

	@XmlElement(name = "classify")
	public Integer getClassify() {
		return classify;
	}

	@XmlElement(name = "gatewayaddr")
	public String getGatewayAddr() {
		return gatewayAddr;
	}

	@XmlElement(name = "gatewayport")
	public Long getGatewayPort() {
		return gatewayPort;
	}

	@XmlElement(name = "user")
	public String getUser() {
		return user;
	}

	@XmlElement(name = "password")
	public String getPasswd() {
		return passwd;
	}

	@XmlElement(name = "province")
	public String getProvince() {
		return province;
	}

	@XmlElement(name = "type")
	public Integer getType() {
		return type;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	@XmlElement(name = "smssendpath")
	public String getSmsSendPath() {
		return smsSendPath;
	}

	@XmlElement(name = "smsreceivepath")
	public String getSmsReceivePath() {
		return smsReceivePath;
	}

	@XmlElement(name = "smsreportpath")
	public String getSmsReportPath() {
		return smsReportPath;
	}

	@XmlElement(name = "delstatus")
	public Integer getDelStatus() {
		return delStatus;
	}

	@XmlElement(name = "tunnelrange")
	public Integer getTunnelRange() {
		return tunnelRange;
	}
	
	@XmlElement(name = "attribute")
	public Integer getAttribute() {
		return attribute;
	}

	@XmlElement(name = "serviceid")
	public String getServiceId() {
		return serviceId;
	}

	@XmlElement(name = "smcorpident")
	public String getSmsCorpIdent() {
		return smsCorpIdent;
	}

	@XmlElement(name = "protocalversion")
	public String getProtocalVersion() {
		return protocalVersion;
	}

	@XmlElement(name = "entid")
	public Long getMerchantPin() {
		return merchantPin;
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

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setSmsCorpIdent(String smsCorpIdent) {
		this.smsCorpIdent = smsCorpIdent;
	}

	public void setProtocalVersion(String protocalVersion) {
		this.protocalVersion = protocalVersion;
	}

	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}

	public void setTunnelRange(Integer tunnelRange) {
		this.tunnelRange = tunnelRange;
	}

}
