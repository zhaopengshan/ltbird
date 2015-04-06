package com.leadtone.delegatemas.operation.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.delegatemas.operation.bean.UserOperationRelation;
import com.leadtone.delegatemas.operation.bean.UserOperationRelationVO;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;

public class UserOperationRelationDaoImpl extends BaseDao implements UserOperationRelationIDao {

	protected static String NAMESPACE = "operation";
	protected static String INSERT = ".operationRelationInsert";
	protected static String DELETEBYID = ".operationRelationDeleteById";
	protected static String DELETEBYUSERID = ".operationRelationDeleteByUserId";
	protected static String UPDATE = ".operationRelationUpdate";
	//operationRelationLoadById
	protected static String LOADBYID = ".operationRelationLoadById";
	protected static String LOADVOBYID = ".operationRelationVoLoadById";
	protected static String SELECT = ".operationRelationSelect";
	
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
	public void insert(UserOperationRelation mbnMms) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().insert(NAMESPACE + INSERT, mbnMms);
	}

	@Override
	public void delete(UserOperationRelation mbnMms) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void delete(Long mbnMms) {
		this.getSqlMapClientTemplate().delete(NAMESPACE + DELETEBYID, mbnMms);
	}

	@Override
	public void deleteByUserId(Long mbnMms) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().delete(NAMESPACE + DELETEBYUSERID, mbnMms);
	}
	
	@Override
	public void update(UserOperationRelation mbnMms) {
		this.getSqlMapClientTemplate().update(NAMESPACE + UPDATE, mbnMms);
	}

	@Override
	public UserOperationRelation loadById(Long id) {
		return (UserOperationRelation) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + LOADBYID, id);
	}

	@Override
	public UserOperationRelationVO loadVoById(Long id) {
		return (UserOperationRelationVO) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + LOADVOBYID, id);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<UserOperationRelationVO> select(Map<String, Object> paraMap) {
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + SELECT, paraMap);
	}

}
