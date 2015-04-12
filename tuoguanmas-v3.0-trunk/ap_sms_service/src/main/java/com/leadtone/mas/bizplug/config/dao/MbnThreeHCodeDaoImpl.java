package com.leadtone.mas.bizplug.config.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.dao.base.ConfigBaseDaoImpl;

@Repository
public class MbnThreeHCodeDaoImpl extends
		ConfigBaseDaoImpl<MbnThreeHCode, Long> implements MbnThreeHCodeDao {
	protected static final String QUERY_BY_MOBILEPREFIX = ".queryByBobilePrefix";
	protected static final String QUERY_ALL = ".queryAll";

	@Override
	public MbnThreeHCode queryByBobilePrefix(String prefix) {
		return (MbnThreeHCode) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + QUERY_BY_MOBILEPREFIX, prefix);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MbnThreeHCode> queryAll() {
		return getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + QUERY_ALL);
	}

}
