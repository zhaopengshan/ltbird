package com.leadtone.mas.bizplug.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.dao.MbnSevenHCodeDao;

@Transactional
@SuppressWarnings("unchecked")
@Service(value = "mbnSevenHCodeService")
public class MbnSevenHCodeServiceImpl implements MbnSevenHCodeService {
	@Resource
	private MbnSevenHCodeDao mbnSevenHCodeDao;

	@Override
	public Integer batchDeleteByList(List<MbnSevenHCode> entitys) {

		return mbnSevenHCodeDao.batchDeleteByList(entitys);
	}

	@Override
	public List<MbnSevenHCode> batchSelectByPks(Long[] pks) {

		return (List<MbnSevenHCode>) mbnSevenHCodeDao.batchSelectByPks(pks);
	}

	@Override
	public Integer delete(MbnSevenHCode mbnThreeHCode) {

		return mbnSevenHCodeDao.delete(mbnThreeHCode);
	}

	@Override
	public Integer insert(MbnSevenHCode mbnThreeHCode) {

		return mbnSevenHCodeDao.insert(mbnThreeHCode);
	}

	@Override
	public Page page(PageUtil pageUtil) {

		return mbnSevenHCodeDao.page(pageUtil);
	}

	@Override
	public Integer pageCount(PageUtil pageUtil) {

		return mbnSevenHCodeDao.pageCount(pageUtil);
	}

	@Override
	public MbnSevenHCode queryByPk(Long pk) {
		return mbnSevenHCodeDao.queryByPk(pk);
	}
	
	@Override
	public MbnSevenHCode queryByBobilePrefix(String prefix) {
		return mbnSevenHCodeDao.queryByBobilePrefix(prefix);
	}


	@Override
	public Integer update(MbnSevenHCode mbnThreeHCode) {

		return mbnSevenHCodeDao.update(mbnThreeHCode);
	}

	public MbnSevenHCodeDao getMbnSevenHCodeDao() {
		return mbnSevenHCodeDao;
	}

	public void setMbnSevenHCodeDao(MbnSevenHCodeDao mbnSevenHCodeDao) {
		this.mbnSevenHCodeDao = mbnSevenHCodeDao;
	}

}
