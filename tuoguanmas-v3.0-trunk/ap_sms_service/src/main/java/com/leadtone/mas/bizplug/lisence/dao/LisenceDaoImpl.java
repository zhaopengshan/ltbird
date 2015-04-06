package com.leadtone.mas.bizplug.lisence.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.lisence.bean.Lisence;

@Component("lisenceDao")
public class LisenceDaoImpl extends BaseDao implements LisenceDao {
	private static String namespace = "lisence";
	@SuppressWarnings("unchecked")
	@Override
	public List<Lisence> getAllLisence() {
		return this.getSqlMapClientTemplate().queryForList(namespace + ".list");
	}

	@Override
	public void insert(Lisence lis) {
		this.getSqlMapClientTemplate().insert(namespace + ".insert", lis);
	}

	@Override
	public void update(Lisence lis) {
		this.getSqlMapClientTemplate().update(namespace + ".update", lis);
	}

	@Override
	public void truncate() {
		this.getSqlMapClientTemplate().update(namespace + ".truncateArea");
	}
	

}
