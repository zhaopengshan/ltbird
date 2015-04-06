package com.leadtone.delegatemas.node.service;

import java.util.List;

import com.leadtone.delegatemas.node.bean.MbnNode;

public interface MbnNodeService {
	public MbnNode getByPk(Long id);

	public int insert(MbnNode node);

	public int delete(MbnNode node);

	public int update(MbnNode node);

	public List<MbnNode> getNodeList();
}
