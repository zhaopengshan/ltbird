package com.leadtone.mas.bizplug.sms.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsOperationClassDao;

@Service("mbnSmsOperationClassService")
@Transactional
public class MbnSmsOperationClassServiceImpl implements
		MbnSmsOperationClassService {
	@Resource
	private MbnSmsOperationClassDao mbnSmsOperationClassDao;

	@Override
	public MbnSmsOperationClass findByCoding(String coding) {
		return mbnSmsOperationClassDao.queryByCoding(coding);
	}

	@Override
	public MbnSmsOperationClass findById(Long id) {
		return (MbnSmsOperationClass) mbnSmsOperationClassDao.queryByPk(id);
	}

}
