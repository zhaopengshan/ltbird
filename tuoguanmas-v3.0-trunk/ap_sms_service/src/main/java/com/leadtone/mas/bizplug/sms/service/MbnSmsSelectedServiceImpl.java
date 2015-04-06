/**
 * 
 */
package com.leadtone.mas.bizplug.sms.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil; 
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsSelectedDao;

/**
 * @author R
 * 
 */
@Service("mbnSmsSelectedService")
@SuppressWarnings("unchecked")
@Transactional
public class MbnSmsSelectedServiceImpl implements MbnSmsSelectedService {
	@Resource
	private MbnSmsSelectedDao mbnSmsSelectedDao;

	@Override
	public Integer delete(MbnSmsSelected mbnSmsSelected) {
		return mbnSmsSelectedDao.delete(mbnSmsSelected);
	}
	
	public List<MbnSmsSelected> followPage(HashMap<String,Object> page){
		return mbnSmsSelectedDao.followPage(page);
	}

	@Override
	public Integer insert(MbnSmsSelected mbnSmsSelected) {
		return mbnSmsSelectedDao.insert(mbnSmsSelected);
	}

	public Integer batchSaveByList( List<MbnSmsSelected> entitys){
		return mbnSmsSelectedDao.batchSaveByList(entitys);
	}
	
	@Override
	public Page page(PageUtil pageUtil) {
		return mbnSmsSelectedDao.page(pageUtil);
	}

	@Override
	public Integer pageCount(PageUtil pageUtil) {
		return mbnSmsSelectedDao.pageCount(pageUtil);
	}

	@Override
	public MbnSmsSelected queryByPk(Long pk) {
//		List<MbnSmsSelected> l = (List<MbnSmsSelected>) mbnSmsSelectedDao.queryByPk(pk);
//		return l.size() > 0 ? (MbnSmsSelected) l.get(0) : null;
		return (MbnSmsSelected) mbnSmsSelectedDao.queryByPk(pk);
	}

	@Override
	public Integer update(MbnSmsSelected mbnSmsSelected) {
		return mbnSmsSelectedDao.update(mbnSmsSelected);
	}

	public MbnSmsSelectedDao getMbnSmsDraftDao() {
		return mbnSmsSelectedDao;
	}

	public void setMbnSmsDraftDao(MbnSmsSelectedDao mbnSmsSelectedDao) {
		this.mbnSmsSelectedDao = mbnSmsSelectedDao;
	}
	@Override
	public Integer batchUpdateByList(List<MbnSmsSelected> paramList) {
		// TODO Auto-generated method stub
		return mbnSmsSelectedDao.batchUpdateByList(paramList);
	}


	@Override
	public Integer batchDeleteByPks(Long[] pks) {
		// TODO Auto-generated method stub
		return mbnSmsSelectedDao.batchDeleteByPks(pks);
	}


	@Override
	public Integer batchDeleteByList(List<MbnSmsSelected> entitys) {
		// TODO Auto-generated method stub
		return mbnSmsSelectedDao.batchDeleteByList(entitys);
	}


	
	@Override
	public List<MbnSmsSelected> batchSelectByPks(Long[] pks) {
		// TODO Auto-generated method stub
		return (List<MbnSmsSelected>) mbnSmsSelectedDao.batchSelectByPks(pks);
	}
 
}
