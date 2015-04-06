package com.leadtone.mas.bizplug.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.common.bean.MbnProvinceDictionary;
import com.leadtone.mas.bizplug.common.dao.MbnProvinceDictionaryDao;
@Service(value="MbnProvinceDictionaryService")
public class MbnProvinceDictionaryServiceImpl implements
		MbnProvinceDictionaryService { 
	
	@Resource
	private MbnProvinceDictionaryDao mbnProvinceDictionaryDao;
	
	@Override
	public MbnProvinceDictionary queryByPk(Long pk)throws Exception{
		MbnProvinceDictionary mbnProvinceDictionary=null;
		try {
			mbnProvinceDictionary=mbnProvinceDictionaryDao.queryByPk(pk);
		} catch (Exception e) {
			throw e;
		}
	
		return mbnProvinceDictionary;
	}

	@Override
	public MbnProvinceDictionary queryByCoding(String provinceCoding)throws Exception{
		MbnProvinceDictionary mbnProvinceDictionary=null;
		try {
			mbnProvinceDictionary=mbnProvinceDictionaryDao.queryByCoding(provinceCoding);
		} catch (Exception e) {
			throw e;
		}
	
		return mbnProvinceDictionary;
	}

	@Override
	public List<MbnProvinceDictionary> load()throws Exception{
		List<MbnProvinceDictionary> mList=null;
		try {
			mList=mbnProvinceDictionaryDao.load();
		} catch (Exception e) {
			throw e;
		}
		return mList;
	}

}
