/**
 * 
 */
package com.leadtone.mas.bizplug.sms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsKeywords;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsKeywordsDao;

/**
 * @author R
 * 
 */
@Service("mbnSmsKeywordsService")
@SuppressWarnings("unchecked")
@Transactional
public class MbnSmsKeywordsServiceImpl implements MbnSmsKeywordsService {
	@Resource
	private MbnSmsKeywordsDao mbnSmsKeywordsDao;

	@Override
	public Integer delete(MbnSmsKeywords mbnSmsKeywords) {
		return mbnSmsKeywordsDao.delete(mbnSmsKeywords);
	}

	@Override
	public Integer insert(MbnSmsKeywords mbnSmsKeywords) {
		return mbnSmsKeywordsDao.insert(mbnSmsKeywords);
	}

	public Integer batchSaveByList(List<MbnSmsKeywords> entitys) {
		return mbnSmsKeywordsDao.batchSaveByList(entitys);
	}

	@Override
	public Page page(PageUtil pageUtil) {
		return mbnSmsKeywordsDao.page(pageUtil);
	}

	@Override
	public Integer pageCount(PageUtil pageUtil) {
		return mbnSmsKeywordsDao.pageCount(pageUtil);
	}

	@Override
	public MbnSmsKeywords queryByPk(Long pk) {
		// List<MbnSmsKeywords> l = (List<MbnSmsKeywords>)
		// mbnSmsKeywordsDao.queryByPk(pk);
		// return l.size() > 0 ? (MbnSmsKeywords) l.get(0) : null;
		return (MbnSmsKeywords) mbnSmsKeywordsDao.queryByPk(pk);
	}

	@Override
	public Integer update(MbnSmsKeywords mbnSmsKeywords) {
		return mbnSmsKeywordsDao.update(mbnSmsKeywords);
	}

	public MbnSmsKeywordsDao getMbnSmsDraftDao() {
		return mbnSmsKeywordsDao;
	}

	public void setMbnSmsDraftDao(MbnSmsKeywordsDao mbnSmsKeywordsDao) {
		this.mbnSmsKeywordsDao = mbnSmsKeywordsDao;
	}

	@Override
	public Integer batchUpdateByList(List<MbnSmsKeywords> paramList) {
		// TODO Auto-generated method stub
		return mbnSmsKeywordsDao.batchUpdateByList(paramList);
	}

	@Override
	public Integer batchDeleteByPks(Long[] pks) {
		// TODO Auto-generated method stub
		return mbnSmsKeywordsDao.batchDeleteByPks(pks);
	}

	@Override
	public Integer batchDeleteByList(List<MbnSmsKeywords> entitys) {
		// TODO Auto-generated method stub
		return mbnSmsKeywordsDao.batchDeleteByList(entitys);
	}

	@Override
	public List<MbnSmsKeywords> batchSelectByPks(Long[] pks) {
		// TODO Auto-generated method stub
		return (List<MbnSmsKeywords>) mbnSmsKeywordsDao.batchSelectByPks(pks);
	}

	@Override
	public boolean checkSms(Long pin, String smsText) {
		if (smsText == null || smsText.trim().length() == 0) {
			return true;
		}
		List<MbnSmsKeywords> list = (List<MbnSmsKeywords>) mbnSmsKeywordsDao
				.queryByPin(pin);
		if (list == null || list.size() == 0) {
			return true;
		}

		for (MbnSmsKeywords key : list) {
			if (smsText.indexOf(key.getKeywords()) >= 0) {
				return false;
			}
		}
		return true;
	}

}
