package com.leadtone.mas.bizplug.config.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.dao.MbnThreeHCodeDao;

@Transactional
@SuppressWarnings("unchecked")
@Service(value = "mbnThreeHCodeService")
public class MbnThreeHCodeServiceImpl implements MbnThreeHCodeService {
	@Resource
	private MbnThreeHCodeDao mbnThreeHCodeDao;

	@Override
	public Integer batchDeleteByList(List<MbnThreeHCode> entitys) {

		return mbnThreeHCodeDao.batchDeleteByList(entitys);
	}

	@Override
	public List<MbnThreeHCode> batchSelectByPks(Long[] pks) {

		return (List<MbnThreeHCode>) mbnThreeHCodeDao.batchSelectByPks(pks);
	}

	@Override
	public Integer delete(MbnThreeHCode mbnThreeHCode) {

		return mbnThreeHCodeDao.delete(mbnThreeHCode);
	}

	@Override
	public Integer insert(MbnThreeHCode mbnThreeHCode) {

		return mbnThreeHCodeDao.insert(mbnThreeHCode);
	}

	@Override
	public Page page(PageUtil pageUtil) {

		return mbnThreeHCodeDao.page(pageUtil);
	}

	@Override
	public Integer pageCount(PageUtil pageUtil) {

		return mbnThreeHCodeDao.pageCount(pageUtil);
	}

	@Override
	public MbnThreeHCode queryByPk(Long pk) {

		return mbnThreeHCodeDao.queryByPk(pk);
	}
	
	@Override
	public MbnThreeHCode queryByBobilePrefix(String prefix) {
		return mbnThreeHCodeDao.queryByBobilePrefix(prefix);
	}


	@Override
	public Integer update(MbnThreeHCode mbnThreeHCode) {

		return mbnThreeHCodeDao.update(mbnThreeHCode);
	}

	public MbnThreeHCodeDao getMbnThreeHCodeDao() {
		return mbnThreeHCodeDao;
	}

	public void setMbnThreeHCodeDao(MbnThreeHCodeDao mbnThreeHCodeDao) {
		this.mbnThreeHCodeDao = mbnThreeHCodeDao;
	}


}
