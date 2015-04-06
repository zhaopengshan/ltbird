package com.leadtone.delegatemas.node.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.leadtone.delegatemas.node.bean.MbnNodeMerchantRelation;
import com.leadtone.mas.bizplug.dao.BaseDao;

@Repository
public class MbnNodeMerchantRelDaoImpl extends BaseDao implements
		MbnNodeMerchantRelDao {
	protected static final String GET_BY_PK = "mbnNodeMerchantRelation.getByPk";
	protected static final String DELETE_BY_ID = "mbnNodeMerchantRelation.delete";
	protected static final String INSERT = "mbnNodeMerchantRelation.insert";
	protected static final String UPDATE = "mbnNodeMerchantRelation.update";
	protected static final String GET_LIST = "mbnNodeMerchantRelation.getAll";
	protected static final String GET_BY_NODE_ID = "mbnNodeMerchantRelation.getByNodeId";
	protected static final String GET_BY_MERCHANT_PIN = "mbnNodeMerchantRelation.getByMerchantPin";
	

	@Override
	public int delete(Long id) {
		return getSqlMapClientTemplate().delete(DELETE_BY_ID, id);
	}

	@Override
	public List<MbnNodeMerchantRelation> getAll() {
		return getSqlMapClientTemplate().queryForList(GET_LIST);
	}

	@Override
	public MbnNodeMerchantRelation getByPk(Long id) {
		return (MbnNodeMerchantRelation)getSqlMapClientTemplate().queryForObject(GET_BY_PK, id);
	}

	@Override
	public int insert(MbnNodeMerchantRelation rel) {
		return getSqlMapClientTemplate().update(INSERT, rel);
	}

	@Override
	public List<MbnNodeMerchantRelation> getByNodeId(Long nodeId) {
		return getSqlMapClientTemplate().queryForList(GET_BY_NODE_ID, nodeId);
	}
	
	@Override
	public List<MbnNodeMerchantRelation> getByMerchantPin(Long merchantPin) {
		return getSqlMapClientTemplate().queryForList(GET_BY_MERCHANT_PIN, merchantPin);
	}
}
