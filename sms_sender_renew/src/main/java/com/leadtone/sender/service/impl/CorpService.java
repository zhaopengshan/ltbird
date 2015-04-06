package com.leadtone.sender.service.impl;

import java.util.List;

import com.leadtone.sender.dao.mandatory.ICorpDao;
import com.leadtone.sender.service.ICorpService;

public class CorpService implements ICorpService {
    private ICorpDao corpDao;
    
	@Override
	public List getCorpPara(String name, String corpId) {
		return corpDao.getCorpPara(name, corpId);
	}

	public ICorpDao getCorpDao() {
		return corpDao;
	}

	public void setCorpDao(ICorpDao corpDao) {
		this.corpDao = corpDao;
	}

}
