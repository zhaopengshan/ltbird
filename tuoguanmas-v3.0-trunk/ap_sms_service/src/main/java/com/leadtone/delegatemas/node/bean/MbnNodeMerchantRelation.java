package com.leadtone.delegatemas.node.bean;

import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

/**
 * 节点商户关联表
 * 
 */
public class MbnNodeMerchantRelation {
	private Long id;
	private Long nodeId;
	private Long merchantPin;

	@JSONFieldBridge(impl = StringBridge.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JSONFieldBridge(impl = StringBridge.class)
	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@JSONFieldBridge(impl = StringBridge.class)
	public Long getMerchantPin() {
		return merchantPin;
	}

	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}

}
