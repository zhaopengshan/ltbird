package com.leadtone.delegatemas.node.dao;

import java.util.List;

import com.leadtone.delegatemas.node.bean.MbnNodeMerchantRelation;

public interface MbnNodeMerchantRelDao {
	public int insert(MbnNodeMerchantRelation rel);

	public int delete(Long id);

	public MbnNodeMerchantRelation getByPk(Long id);

	public List<MbnNodeMerchantRelation> getAll();
	
	public List<MbnNodeMerchantRelation> getByNodeId(Long nodeId);
	
	public List<MbnNodeMerchantRelation> getByMerchantPin(Long merchantPin);
}
