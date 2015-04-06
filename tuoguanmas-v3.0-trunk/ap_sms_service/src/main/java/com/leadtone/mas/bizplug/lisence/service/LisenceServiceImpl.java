package com.leadtone.mas.bizplug.lisence.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.lisence.bean.Lisence;
import com.leadtone.mas.bizplug.lisence.dao.LisenceDao;

@Service("lisenceService")
@Transactional
public class LisenceServiceImpl implements LisenceService {
	@Resource
	private LisenceDao lisenceDao;
	@Override
	public List<Lisence> getAllLisence() {
		return lisenceDao.getAllLisence();
	}
	@Override
	public void insert(Lisence lis) {
		lisenceDao.insert(lis);
	}
	@Override
	public void update(Lisence lis) {
		lisenceDao.update(lis);
	}
	@Override
	public void truncate() {
		lisenceDao.truncate();
	}

}
