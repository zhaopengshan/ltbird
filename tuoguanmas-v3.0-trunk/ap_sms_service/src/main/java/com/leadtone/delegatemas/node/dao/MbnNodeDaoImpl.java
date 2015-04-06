package com.leadtone.delegatemas.node.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.leadtone.delegatemas.node.bean.MbnNode;
import com.leadtone.mas.bizplug.dao.BaseDao;

@Repository("mbnNodeDao")
public class MbnNodeDaoImpl extends BaseDao implements MbnNodeDao {
	protected static final String GET_BY_PK = "mbnNode.getByPk";
	protected static final String DELETE = "mbnNode.delete";
	protected static final String INSERT = "mbnNode.insert";
	protected static final String UPDATE = "mbnNode.update";
	protected static final String GET_LIST = "mbnNode.getAll";
	 
	@Override
	public int delete(MbnNode node) {
		return getSqlMapClientTemplate().delete( DELETE, node);
	}

	@Override
	public MbnNode getByPk(Long id) {
		return (MbnNode) getSqlMapClientTemplate().queryForObject(GET_BY_PK, id);
	}

	@Override
	public List<MbnNode> getNodeList() {
		return getSqlMapClientTemplate().queryForList(GET_LIST);
	}

	@Override
	public int insert(MbnNode node) {
		return getSqlMapClientTemplate().update(INSERT, node);
	}

	@Override
	public int update(MbnNode node) {
		return getSqlMapClientTemplate().update(UPDATE, node);
	}

}
