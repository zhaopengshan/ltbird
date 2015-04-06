package com.leadtone.mas.bizplug.security.dao;

import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;

@Component("portalUserExtDao")
public class PortalUserExtDaoImpl extends BaseDao implements PortalUserExtDao {
	
	 protected static final String GET_BY_PK = "portalUserExt.getByPk";
	 protected static final String SAVE = "portalUserExt.insert";
	 protected static final String DELETE = "portalUserExt.deleteById";
	 protected static final String UPDATE = "portalUserExt.update";


	@Override
	public boolean delete(Long id) {
		getSqlMapClientTemplate().delete(DELETE, id);
		return true;
	}

	@Override
	public PortalUserExtBean getByPk(Long id) {
		Object o = getSqlMapClientTemplate().queryForObject(GET_BY_PK, id);
		if( o != null && o instanceof PortalUserExtBean){
			return (PortalUserExtBean)o;
		}
		return null;
	}

	@Override
	public boolean save(PortalUserExtBean bean) {
		getSqlMapClientTemplate().update(SAVE, bean);
		return true;
	}

	@Override
	public boolean update(PortalUserExtBean bean) {
		getSqlMapClientTemplate().update(UPDATE, bean);
		return true;
	}

}
