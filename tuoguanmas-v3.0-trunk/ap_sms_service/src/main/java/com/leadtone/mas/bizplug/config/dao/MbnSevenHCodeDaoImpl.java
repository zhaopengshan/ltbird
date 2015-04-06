package com.leadtone.mas.bizplug.config.dao;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.dao.base.ConfigBaseDaoImpl;
@Repository
public class MbnSevenHCodeDaoImpl extends
		ConfigBaseDaoImpl<MbnSevenHCode, Long> implements MbnSevenHCodeDao {

	protected static final String QUERY_BY_MOBILEPREFIX = ".queryByBobilePrefix";

	@Override
	public MbnSevenHCode queryByBobilePrefix(String prefix) {
		return (MbnSevenHCode) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + QUERY_BY_MOBILEPREFIX, prefix);
	}

}
