package com.leadtone.delegatemas.operation.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.delegatemas.operation.bean.PortalOperation;
import com.leadtone.delegatemas.operation.bean.UserOperationRelation;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;

public class PortalOperationDaoImpl extends BaseDao implements PortalOperationIDao {

	protected static String NAMESPACE = "operation";
	protected static String INSERT = ".operationInsert";
	protected static String DELETEBYID = ".operationDeleteById";
	protected static String UPDATE = ".operationUpdate";
	protected static String LOADBYID = ".operationLoadById";
	protected static String SELECT = ".operationSelect";
	@Override
	public Page page(PageUtil pageUtil) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Integer pageCount(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void insert(PortalOperation mbnMms) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().insert(NAMESPACE + INSERT, mbnMms);
	}

	@Override
	public void delete(PortalOperation mbnMms) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void delete(Long mbnMms) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().delete(NAMESPACE + DELETEBYID, mbnMms);
	}

	@Override
	public void update(PortalOperation mbnMms) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update(NAMESPACE + UPDATE, mbnMms);
	}

	@Override
	public PortalOperation loadById(Long id) {
		return (PortalOperation) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + LOADBYID, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PortalOperation> select(Map<String, Object> paraMap) {
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + SELECT, paraMap);
	}

}
