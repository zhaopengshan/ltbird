package com.leadtone.mas.bizplug.config.dao;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.dao.base.ConfigBaseDaoImpl;

@Repository
public class MbnThreeHCodeDaoImpl extends
		ConfigBaseDaoImpl<MbnThreeHCode, Long> implements MbnThreeHCodeDao {
	protected static final String QUERY_BY_MOBILEPREFIX = ".queryByBobilePrefix";

	@Override
	public MbnThreeHCode queryByBobilePrefix(String prefix) {
		return (MbnThreeHCode) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + QUERY_BY_MOBILEPREFIX, prefix);
	}

}
