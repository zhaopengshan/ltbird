/**
 * 
 */
package com.leadtone.mas.bizplug.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil; 
import com.leadtone.mas.bizplug.config.bean.MbnConfigSysDictionary;
import com.leadtone.mas.bizplug.config.dao.mbnConfigSysDictionaryDao;

/**
 * @author PAN-Z-G
 *
 */
@SuppressWarnings("unchecked")
@Service(value="mbnConfigSysDictionaryService")
public class MbnConfigSysDictionaryServiceImpl implements
		MbnConfigSysDictionaryService {

	@Resource
	private mbnConfigSysDictionaryDao mbnConfigSysDictionaryDao;
	@Override
	public Page page(PageUtil pageUtil) {
	 
		return mbnConfigSysDictionaryDao.page(pageUtil);
	}

	@Override
	public Integer pageCount(PageUtil pageUtil) { 
		return mbnConfigSysDictionaryDao.pageCount(pageUtil);
	}

	@Override
	public Integer insert(MbnConfigSysDictionary param) { 
		return mbnConfigSysDictionaryDao.insert(param);
	}

	@Override
	public Integer update(MbnConfigSysDictionary param) { 
		return mbnConfigSysDictionaryDao.update(param);
	}

	@Override
	public Integer delete(MbnConfigSysDictionary param) { 
		return mbnConfigSysDictionaryDao.delete(param);
	}

	@Override
	public MbnConfigSysDictionary queryByPk(Long pk) { 
		return mbnConfigSysDictionaryDao.queryByPk(pk);
	}

	@Override
	public MbnConfigSysDictionary queryByPin(Long pin) { 
		return mbnConfigSysDictionaryDao.queryByPin(pin);
	}

	@Override
	public Integer batchUpdateByList(List<MbnConfigSysDictionary> paramList) { 
		return mbnConfigSysDictionaryDao.batchUpdateByList(paramList);
	}

	@Override
	public Integer batchDeleteByPks(Long[] pks) { 
		return mbnConfigSysDictionaryDao.batchDeleteByPks(pks);
	}

	@Override
	public Integer batchDeleteByList(List<MbnConfigSysDictionary> entitys) { 
		return mbnConfigSysDictionaryDao.batchDeleteByList(entitys);
	}

	@Override
	public Integer batchSaveByList(List<MbnConfigSysDictionary> entitys) { 
		return mbnConfigSysDictionaryDao.batchSaveByList(entitys);
	}

	@Override
	public List<MbnConfigSysDictionary>   batchSelectByPks(Long[] pks) { 
		return (List<MbnConfigSysDictionary>) mbnConfigSysDictionaryDao.batchSelectByPks(pks);
	}

	public mbnConfigSysDictionaryDao getMbnConfigSysDictionaryDao() {
		return mbnConfigSysDictionaryDao;
	}

	public void setMbnConfigSysDictionaryDao(
			mbnConfigSysDictionaryDao mbnConfigSysDictionaryDao) {
		this.mbnConfigSysDictionaryDao = mbnConfigSysDictionaryDao;
	}

	
	@Override
	public List<MbnConfigSysDictionary> queryByPks(Long pk) {
		// 
		return (List<MbnConfigSysDictionary>) mbnConfigSysDictionaryDao.queryByPk(pk);
	}

	@Override
	public List<MbnConfigSysDictionary> queryByPins(Long pin) {
		// 
		return (List<MbnConfigSysDictionary>) mbnConfigSysDictionaryDao.queryByPin(pin);
	}

	@Override
	public List<MbnConfigSysDictionary> getByPks(String ids) {
		// 
		return null;
	}

	@Override
	public MbnConfigSysDictionary getByCoding(String coding) {
		// 
		return mbnConfigSysDictionaryDao.getByCoding(coding);
	}


}
