package com.leadtone.mas.bizplug.common.dao;

import java.util.List;

import com.leadtone.mas.bizplug.common.bean.MbnProvinceDictionary;

public interface MbnProvinceDictionaryDao {
	/**
	 * 根据pk查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	MbnProvinceDictionary queryByPk(Long pk);

	/**
	 * 根据省编码查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	MbnProvinceDictionary queryByCoding(String provinceCoding);
	/**
	 * 加载ALL
	 * @return
	 */
	List<MbnProvinceDictionary> load();

}
