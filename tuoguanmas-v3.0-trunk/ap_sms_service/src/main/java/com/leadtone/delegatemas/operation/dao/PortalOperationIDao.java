package com.leadtone.delegatemas.operation.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.delegatemas.operation.bean.PortalOperation;
import com.leadtone.delegatemas.operation.dao.base.PageBaseIDao;

public interface PortalOperationIDao extends PageBaseIDao {

	public void insert(PortalOperation mbnMms);
	public void delete(PortalOperation mbnMms);
	public void delete(Long mbnMms);
	public void update(PortalOperation mbnMms);
	public PortalOperation loadById(Long id);
	public List<PortalOperation> select(Map<String, Object> paraMap);
}
