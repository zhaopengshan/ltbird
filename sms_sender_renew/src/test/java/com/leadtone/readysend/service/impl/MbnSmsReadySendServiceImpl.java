package com.leadtone.readysend.service.impl;

import com.leadtone.readysend.bean.MbnSmsReadySend;
import com.leadtone.readysend.dao.MbnSmsReadySendDao;
import com.leadtone.readysend.service.MbnSmsReadySendService;

public class MbnSmsReadySendServiceImpl implements MbnSmsReadySendService{

	private MbnSmsReadySendDao mbnSmsReadySendDao;
	
	@Override
	public void save(MbnSmsReadySend mbnSmsReadySend) {
		mbnSmsReadySendDao.save(mbnSmsReadySend);
	}
	
	public MbnSmsReadySendDao getMbnSmsReadySendDao() {
		return mbnSmsReadySendDao;
	}
	public void setMbnSmsReadySendDao(MbnSmsReadySendDao mbnSmsReadySendDao) {
		this.mbnSmsReadySendDao = mbnSmsReadySendDao;
	}

}
