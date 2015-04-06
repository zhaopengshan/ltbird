package com.leadtone.mas.bizplug.webservice.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "maspackage")
public class MasPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7252270589444831002L;

	@XmlTransient
	private MasHeadPackage head;
	@XmlTransient
	private MasBodyPackage body;
	@XmlTransient
	private String version;

	@XmlElement(name = "head")
	public MasHeadPackage getHead() {
		return head;
	}

	public void setHead(MasHeadPackage head) {
		this.head = head;
	}

	@XmlElement(name = "body")
	public MasBodyPackage getBody() {
		return body;
	}

	public void setBody(MasBodyPackage body) {
		this.body = body;
	}

	@XmlAttribute(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
