package com.leadtone.mas.bizplug.common.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.leadtone.mas.bizplug.common.bean.MbnProvinceDictionary;
import com.leadtone.mas.bizplug.dao.BaseDao;

@Repository
@SuppressWarnings("unchecked")
public class MbnProvinceDictionaryDaoImpl extends BaseDao implements
		MbnProvinceDictionaryDao {

	@Override
	public MbnProvinceDictionary queryByPk(Long pk) {
		//
		return (MbnProvinceDictionary) getSqlMapClientTemplate()
				.queryForObject("MbnProvinceDictionary.getById", pk);
	}

	@Override
	public MbnProvinceDictionary queryByCoding(String provinceCoding) {
		//
		return (MbnProvinceDictionary) getSqlMapClientTemplate()
				.queryForObject("MbnProvinceDictionary.getByCoding",
						provinceCoding);
	}

	@Override
	public List<MbnProvinceDictionary> load() {
		//
		return (List<MbnProvinceDictionary>) getSqlMapClientTemplate()
				.queryForList("MbnProvinceDictionary.load");
	}

}
