package com.leadtone.delegatemas.node.dao;

import java.util.List;

import com.leadtone.delegatemas.node.bean.MbnNode;

public interface MbnNodeDao {
	
	public MbnNode getByPk(Long id);

	public int insert(MbnNode node);

	public int delete(MbnNode node);

	public int update(MbnNode node);

	public List<MbnNode> getNodeList();
}
