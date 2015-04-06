package com.leadtone.mas.bizplug.mms.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysend;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysendVO;
import com.leadtone.mas.bizplug.mms.dao.MbnMmsIDao;
import com.leadtone.mas.bizplug.mms.dao.MbnMmsReadysendIDao;

@Service("mbnMmsReadysendIService")
@Transactional
public class MbnMmsReadysendServiceImpl implements MbnMmsReadysendIService {

	@Resource
	private MbnMmsReadysendIDao mbnMmsReadysendIDao;
	@Resource
	private MbnMmsIDao mbnMmsIDao;
	
	public Integer batchUpdateByList(final List<MbnMmsReadysend> paramList){
		return mbnMmsReadysendIDao.batchUpdateByList(paramList);
	}
	
	public List<MbnMmsReadysend> getByPks(String[] ids){
		return mbnMmsReadysendIDao.getByPks(ids);
	}
	
	@Override
	public void insert(MbnMmsReadysend mbnMms) {
		mbnMmsReadysendIDao.insert(mbnMms);
	}

	public boolean batchDeleteByPks(Long[] pks){
		boolean result = true;
		try{
			if(pks!=null && pks.length > 0 ){
				for(int i = 0; i < pks.length; i++){
					mbnMmsReadysendIDao.delete(pks[i]);
				}
			}
		}catch(Exception e){
			result =false;
		}
		return result;
	}
	
	@Override
	public void delete(MbnMmsReadysend mbnMms) {
		mbnMmsReadysendIDao.delete(mbnMms);
	}

	@Override
	public void delete(Long mbnMms) {
		mbnMmsReadysendIDao.delete(mbnMms);
	}

	@Override
	public void update(MbnMmsReadysend mbnMms) {
		mbnMmsReadysendIDao.update(mbnMms);
	}

	@Override
	public MbnMmsReadysend loadById(Long id) {
		// TODO Auto-generated method stub
		return mbnMmsReadysendIDao.loadById(id);
	}

	@Override
	public MbnMmsReadysendVO loadVOById(Long id) {
		return mbnMmsReadysendIDao.loadVOById(id);
	}

	@Override
	public Page page(PageUtil pageUtil) {
		return mbnMmsReadysendIDao.page(pageUtil);
	}

	@Override
	public Integer pageCount(Map<String, Object> paraMap) {
		return mbnMmsReadysendIDao.pageCount(paraMap);
	}

}
