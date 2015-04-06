package com.leadtone.delegatemas.operation.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.delegatemas.operation.bean.UserOperationRelation;
import com.leadtone.delegatemas.operation.bean.UserOperationRelationVO;
import com.leadtone.delegatemas.operation.dao.base.PageBaseIDao;

public interface UserOperationRelationIDao extends PageBaseIDao {

	public void insert(UserOperationRelation mbnMms);
	public void delete(UserOperationRelation mbnMms);
	public void delete(Long mbnMms);
	public void deleteByUserId(Long mbnMms);
	public void update(UserOperationRelation mbnMms);
	public UserOperationRelation loadById(Long id);
	public UserOperationRelationVO loadVoById(Long id);
	public List<UserOperationRelationVO> select(Map<String, Object> paraMap);
}
