package com.leadtone.delegatemas.node.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

/**
 * 节点表
 * 
 */
public class MbnNode {
	private Long id;				// 主键
	private String name;			// 节点名称
	private String password;		// md5加密密码
	private String webServiceUrl;	// 业务节点服务地址
	private String ip;				// 业务节点IP
	private String location;		// 节点部署位置 
	private Integer status;			// 节点状态
	private Integer useWebService;	// 业务节点是否使用WebService方式
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String memo;			// 备注

	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWebServiceUrl() {
		return webServiceUrl;
	}

	public void setWebServiceUrl(String webServiceUrl) {
		this.webServiceUrl = webServiceUrl;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getUseWebService() {
		return useWebService;
	}

	public void setUseWebService(Integer useWebService) {
		this.useWebService = useWebService;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
