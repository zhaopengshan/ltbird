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
import com.leadtone.mas.bizplug.sms.bean.MbnSmsDraft;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsDraftDao;

/**
 * @author R
 * 
 */
@Service("mbnSmsDraftService")
@SuppressWarnings("unchecked")
@Transactional
public class MbnSmsDraftServiceImpl implements MbnSmsDraftService {
	@Resource
	private MbnSmsDraftDao mbnSmsDraftDao;

	@Override
	public Integer delete(MbnSmsDraft mbnSmsDraft) {
		return mbnSmsDraftDao.delete(mbnSmsDraft);
	}

	@Override
	public Integer insert(MbnSmsDraft mbnSmsDraft) {
		return mbnSmsDraftDao.insert(mbnSmsDraft);
	}

	@Override
	public Page page(PageUtil pageUtil) {
		return mbnSmsDraftDao.page(pageUtil);
	}

	@Override
	public Integer pageCount(PageUtil pageUtil) {
		return mbnSmsDraftDao.pageCount(pageUtil);
	}

	@Override
	public MbnSmsDraft queryByPk(Long pk) {
//		List<MbnSmsDraft> l = (List<MbnSmsDraft>) mbnSmsDraftDao.queryByPk(pk);
//		return l.size() > 0 ? (MbnSmsDraft) l.get(0) : null;
		return (MbnSmsDraft) mbnSmsDraftDao.queryByPk(pk);
	}

	@Override
	public Integer update(MbnSmsDraft mbnSmsDraft) {
		return mbnSmsDraftDao.update(mbnSmsDraft);
	}

	public MbnSmsDraftDao getMbnSmsDraftDao() {
		return mbnSmsDraftDao;
	}

	public void setMbnSmsDraftDao(MbnSmsDraftDao mbnSmsDraftDao) {
		this.mbnSmsDraftDao = mbnSmsDraftDao;
	}
	public List<MbnSmsDraft> getByPks(Long[] ids){
		return mbnSmsDraftDao.getByPks(ids);
	}
	
	public List<MbnSmsDraft> followPage(HashMap<String,Object> page){
		return mbnSmsDraftDao.followPage(page);
	}

	@Override
	public Integer batchUpdateByList(List<MbnSmsDraft> paramList) {
		// 
		return null;
	}

	@Override
	public Integer batchDeleteByPks(Long[] pks) {
		// 
		return mbnSmsDraftDao.batchDeleteByPks(pks);
	}

	@Override
	public Integer batchDeleteByList(List<MbnSmsDraft> entitys) {
		// 
		return mbnSmsDraftDao.batchDeleteByList(entitys);
	}

	
	@Override
	public List<MbnSmsDraft> batchSelectByPks(Long[] pks) {
		// 
		return (List<MbnSmsDraft>) mbnSmsDraftDao.batchSelectByPks(pks);
	}
 
}
