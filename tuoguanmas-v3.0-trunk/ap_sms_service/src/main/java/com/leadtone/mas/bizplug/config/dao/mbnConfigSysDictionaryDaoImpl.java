package com.leadtone.mas.bizplug.config.dao;
 

import org.springframework.stereotype.Repository;
 
import com.leadtone.mas.bizplug.config.bean.MbnConfigSysDictionary;
import com.leadtone.mas.bizplug.config.dao.base.ConfigBaseDaoImpl;
@Repository
public class mbnConfigSysDictionaryDaoImpl extends ConfigBaseDaoImpl<MbnConfigSysDictionary, Long>
		implements mbnConfigSysDictionaryDao {
	private static final String GETBYCODING=".getByCoding";
	@Override
	public MbnConfigSysDictionary getByCoding(String coding) {
		// TODO Auto-generated method stub
		return (MbnConfigSysDictionary) getSqlMapClientTemplate().queryForObject(this.sqlMapNamespace+GETBYCODING, coding);
	}
	
}
