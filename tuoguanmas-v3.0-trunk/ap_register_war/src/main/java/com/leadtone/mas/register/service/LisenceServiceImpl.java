package com.leadtone.mas.register.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.register.bean.Lisence;
import com.leadtone.mas.register.dao.LisenceDao;
@Service("lisenceService")
@Transactional
public class LisenceServiceImpl implements LisenceService {
	@Resource
	private LisenceDao lisenceDao;
	@Override
	public List<Lisence> getAllLisence(Lisence lis) {
		return lisenceDao.getAllLisence(lis);
	}
	@Override
	public void insert(Lisence lis) {
		lisenceDao.insert(lis);
	}
	@Override
	public void update(Lisence lis) {
		lisenceDao.update(lis);
	}
	public String productRegister(String mxlStr){
		return "";
	}
}
