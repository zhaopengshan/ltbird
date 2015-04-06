package com.leadtone.mas.bizplug.common.service;

import java.util.List;

import com.leadtone.mas.bizplug.common.bean.MbnProvinceDictionary;

public interface MbnProvinceDictionaryService {
	/**
	 * 根据pk查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	MbnProvinceDictionary queryByPk(Long pk)throws Exception;

	/**
	 * 根据pk查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	MbnProvinceDictionary queryByCoding(String provinceCoding)throws Exception;
	/**
	 * 加载
	 * @return
	 */
	List<MbnProvinceDictionary> load()throws Exception;

}
