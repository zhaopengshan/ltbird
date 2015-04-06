package com.leadtone.mas.bizplug.config.service;

import java.util.List; 
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.config.bean.MbnConfigSys;
import com.leadtone.mas.bizplug.config.dao.MbnConfigSysDao;

@Transactional
@SuppressWarnings("unchecked")
@Service(value="mbnConfigSysService")
public class MbnConfigSysServiceImpl   implements MbnConfigSysService{
	 
	 @Resource
	private MbnConfigSysDao mbnConfigSysDao; 
	 
	@Override
	public Integer pageCount(PageUtil pageUtil) {
		// 
		return mbnConfigSysDao.pageCount(pageUtil);
	}

	@Override
	public Page page(PageUtil pageUtil) {
		// 
		return mbnConfigSysDao.page(pageUtil);
	}

	@Override
	public MbnConfigSys queryByPk(Long pk) {
		// 
		return mbnConfigSysDao.queryByPk(pk);
	}

	
	@Override
	public List<MbnConfigSys> queryByPks(Long pk) {
		// 
		return (List<MbnConfigSys>)mbnConfigSysDao.queryByPk(pk);
	}

	@Override
	public MbnConfigSys queryByPin(Long pin) {
		// 
		return mbnConfigSysDao.queryByPin(pin);
	}

	@Override
	public List<MbnConfigSys> queryByPins(Long pk) {
		// 
		return (List<MbnConfigSys>)mbnConfigSysDao.queryByPin(pk);
	}

	@Override
	public Integer insert(MbnConfigSys mbnConfigSys) {
		// 
		return mbnConfigSysDao.insert(mbnConfigSys);
	}

	@Override
	public Integer update(MbnConfigSys mbnConfigSys) {
		// 
		return mbnConfigSysDao.update(mbnConfigSys);
	}

	@Override
	public Integer delete(MbnConfigSys mbnConfigSys) {
		// 
		return mbnConfigSysDao.delete(mbnConfigSys);
	}

	@Override
	public Integer batchUpdateByList(List<MbnConfigSys> paramList) {
		// 
		return mbnConfigSysDao.batchUpdateByList(paramList);
	}

	@Override
	public Integer batchDeleteByPks(Long[] pks) {
		// 
		return mbnConfigSysDao.batchDeleteByPks(pks);
	}

	@Override
	public Integer batchDeleteByList(List<MbnConfigSys> entitys) {
		// 
		return mbnConfigSysDao.batchDeleteByList(entitys);
	}

	@Override
	public List<MbnConfigSys> batchSelectByPks(Long[] pks) {
		// 
		return (List<MbnConfigSys>) mbnConfigSysDao.batchSelectByPks(pks);
	}

	@Override
	public List<MbnConfigSys> getByPks(String ids) {
		// 
		return null;
	}

/**
 * 访问器
 * @return
 */
	public MbnConfigSysDao getMbnConfigSysDao() {
		return mbnConfigSysDao;
	}

	public void setMbnConfigSysDao(MbnConfigSysDao mbnConfigSysDao) {
		this.mbnConfigSysDao = mbnConfigSysDao;
	}

}
