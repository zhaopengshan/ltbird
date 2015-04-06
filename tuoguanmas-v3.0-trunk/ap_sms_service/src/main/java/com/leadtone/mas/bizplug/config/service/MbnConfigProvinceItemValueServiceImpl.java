package com.leadtone.mas.bizplug.config.service;

import java.util.List;

import javax.annotation.Resource;

 import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.config.bean.MbnConfigProvinceItemValue;
import com.leadtone.mas.bizplug.config.dao.MbnConfigProvinceItemValueDao;

 
@SuppressWarnings("unchecked")
@Service(value="mbnConfigProvinceItemValueService")
public class MbnConfigProvinceItemValueServiceImpl implements
		MbnConfigProvinceItemValueService {
	@Resource
	private MbnConfigProvinceItemValueDao mbnConfigProvinceItemValueDao;

	@Override
	public Integer pageCount(PageUtil pageUtil) {
		//
		return mbnConfigProvinceItemValueDao.pageCount(pageUtil);
	}

	@Override
	public Page page(PageUtil pageUtil) {
		//
		return mbnConfigProvinceItemValueDao.page(pageUtil);
	}

	@Override
	public MbnConfigProvinceItemValue queryByPk(Long pk) {
		//  
		return mbnConfigProvinceItemValueDao.queryByPk(pk);
	}

	
	@Override
	public List<MbnConfigProvinceItemValue> queryByPks(Long pk) {
		//  
		return (List<MbnConfigProvinceItemValue>) mbnConfigProvinceItemValueDao.queryByPk(pk);
	}

	@Override
	public MbnConfigProvinceItemValue queryByPin(Long pin) {
		//  
		return mbnConfigProvinceItemValueDao.queryByPin(pin);
	}

	@Override
	public List<MbnConfigProvinceItemValue> queryByPins(Long pin) {
		//  
		return (List<MbnConfigProvinceItemValue>) mbnConfigProvinceItemValueDao.queryByPin(pin);
	}

	@Override
	public Integer insert(MbnConfigProvinceItemValue mbnConfigProvinceItemValue) {
		//  
		return mbnConfigProvinceItemValueDao.insert(mbnConfigProvinceItemValue);
	}

	@Override
	public Integer update(MbnConfigProvinceItemValue mbnConfigProvinceItemValue) {
		//  
		return mbnConfigProvinceItemValueDao.update(mbnConfigProvinceItemValue) ;
	}

	@Override
	public Integer delete(MbnConfigProvinceItemValue mbnConfigProvinceItemValue) {
		//  
		return mbnConfigProvinceItemValueDao.delete(mbnConfigProvinceItemValue);
	}

	@Override
	public Integer batchUpdateByList(List<MbnConfigProvinceItemValue> paramList) {
		//  
		return mbnConfigProvinceItemValueDao.batchUpdateByList(paramList);
	}

	@Override
	public Integer batchDeleteByPks(Long[] pks) {
		//  
		return mbnConfigProvinceItemValueDao.batchDeleteByPks(pks);
	}

	@Override
	public Integer batchDeleteByList(List<MbnConfigProvinceItemValue> entitys) {
		//  
		return mbnConfigProvinceItemValueDao.batchDeleteByList(entitys);
	}

	@Override
	public List<MbnConfigProvinceItemValue> batchSelectByPks(Long[] pks) {
		//  
		return (List<MbnConfigProvinceItemValue>) mbnConfigProvinceItemValueDao.batchSelectByPks(pks);
	}

	@Override
	public List<MbnConfigProvinceItemValue> getByPks(String ids) {
		//  
		return null;
	}

	@Override
	public Integer batchSaveByList(List<MbnConfigProvinceItemValue> entitys) {
		//  
		return mbnConfigProvinceItemValueDao.batchSaveByList(entitys);
	} 
	 

}
