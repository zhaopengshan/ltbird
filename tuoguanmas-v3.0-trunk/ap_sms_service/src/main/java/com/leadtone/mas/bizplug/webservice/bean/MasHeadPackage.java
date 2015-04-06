package com.leadtone.mas.bizplug.webservice.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "head")
public class MasHeadPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5612116107417267662L;
	// head nodeid 1 String 20 业务系统ID
	// head password 1 String 32 密码
	// 密码使用32位MD5加密
	// head methodname 1 String 32 方法名称
	// head entid ? String 32768 商户ID列表 逗号分隔
	@XmlTransient
	private String nodeId;
	@XmlTransient
	private String password;
	@XmlTransient
	private String methodName;
	@XmlTransient
	private String entId;
	@XmlTransient
	private String returnCode;
	@XmlTransient
	private String returnMessage;

	@XmlElement(name = "nodeid")
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@XmlElement(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name = "methodname")
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@XmlElement(name = "entid")
	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}
	
	
	@XmlElement(name = "returncode")
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	@XmlElement(name = "returnmessage")
	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	@Override
	public String toString() {
		return "MasHeadPackage [entId=" + entId + ", methodName=" + methodName
				+ ", nodeId=" + nodeId + ", password=" + password
				+ ", returnCode=" + returnCode + ", returnMessage="
				+ returnMessage + "]";
	}

}
