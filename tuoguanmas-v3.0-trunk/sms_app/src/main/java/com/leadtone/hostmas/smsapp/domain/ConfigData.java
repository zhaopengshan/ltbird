package com.leadtone.hostmas.smsapp.domain;

public class ConfigData {
	
	private int id ;
	
	private String gatewayID;
	
	private String province_code;
	
	private String area_code;
	
	private String ip;
	
	private int port;
	
	private String run_status;
	
	private String running_time;
	
	private int listen_port;
	
	private String mas_ip;
	
	private String gw_ip;
	
	private int gw_port;
	
	private String gw_user;
	
	private String gw_passwd;
	
	private String enterprise_code;
	
	private String protocol_version;
	
	private int tunnel_id;
	
	private String create_time;
	
	private String operateType;
	
	private String senderCode;
	
	private String sendSpeed;
	
	//彩信网关用户名
	private String mms_gw_user;
	//彩信网关密码	
	private String mms_gw_passwd;
	//彩信网关ip	
	private String mms_gw_ip;
	//彩信网关端口	
	private int mms_gw_port;
	
	private String mms_url;
	
	private String service_code;
	
	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getMms_url() {
		return mms_url;
	}

	public void setMms_url(String mms_url) {
		this.mms_url = mms_url;
	}

	public String getMms_gw_user() {
		return mms_gw_user;
	}

	public void setMms_gw_user(String mms_gw_user) {
		this.mms_gw_user = mms_gw_user;
	}

	public String getMms_gw_passwd() {
		return mms_gw_passwd;
	}

	public void setMms_gw_passwd(String mms_gw_passwd) {
		this.mms_gw_passwd = mms_gw_passwd;
	}

	public String getMms_gw_ip() {
		return mms_gw_ip;
	}

	public void setMms_gw_ip(String mms_gw_ip) {
		this.mms_gw_ip = mms_gw_ip;
	}

	public int getMms_gw_port() {
		return mms_gw_port;
	}

	public void setMms_gw_port(int mms_gw_port) {
		this.mms_gw_port = mms_gw_port;
	}
	
	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGatewayID() {
		return gatewayID;
	}

	public void setGatewayID(String gatewayID) {
		this.gatewayID = gatewayID;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRun_status() {
		return run_status;
	}

	public void setRun_status(String run_status) {
		this.run_status = run_status;
	}

	public String getRunning_time() {
		return running_time;
	}

	public void setRunning_time(String running_time) {
		this.running_time = running_time;
	}

	public int getListen_port() {
		return listen_port;
	}

	public void setListen_port(int listen_port) {
		this.listen_port = listen_port;
	}

	public String getMas_ip() {
		return mas_ip;
	}

	public void setMas_ip(String mas_ip) {
		this.mas_ip = mas_ip;
	}

	public String getGw_ip() {
		return gw_ip;
	}

	public void setGw_ip(String gw_ip) {
		this.gw_ip = gw_ip;
	}

	public int getGw_port() {
		return gw_port;
	}

	public void setGw_port(int gw_port) {
		this.gw_port = gw_port;
	}

	public String getGw_user() {
		return gw_user;
	}

	public void setGw_user(String gw_user) {
		this.gw_user = gw_user;
	}

	public String getGw_passwd() {
		return gw_passwd;
	}

	public void setGw_passwd(String gw_passwd) {
		this.gw_passwd = gw_passwd;
	}

	public String getEnterprise_code() {
		return enterprise_code;
	}

	public void setEnterprise_code(String enterprise_code) {
		this.enterprise_code = enterprise_code;
	}

	public int getTunnel_id() {
		return tunnel_id;
	}

	public void setTunnel_id(int tunnel_id) {
		this.tunnel_id = tunnel_id;
	}

	public String getProtocol_version() {
		return protocol_version;
	}

	public void setProtocol_version(String protocol_version) {
		this.protocol_version = protocol_version;
	}

	public String getSenderCode() {
		return senderCode;
	}

	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	public String getSendSpeed() {
		return sendSpeed;
	}

	public void setSendSpeed(String sendSpeed) {
		this.sendSpeed = sendSpeed;
	}
}
