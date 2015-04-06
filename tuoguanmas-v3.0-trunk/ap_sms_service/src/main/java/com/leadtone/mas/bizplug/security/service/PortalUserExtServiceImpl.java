package com.leadtone.mas.bizplug.security.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;
import com.leadtone.mas.bizplug.security.dao.PortalUserExtDao;

@Service("portalUserExtService")
@Transactional
public class PortalUserExtServiceImpl implements PortalUserExtService {

	@Resource(name = "portalUserExtDao")
	private PortalUserExtDao portalUserExtDao;

	@Override
	public boolean delete(Long id) {
		return portalUserExtDao.delete(id);
	}

	@Override
	public PortalUserExtBean getByPk(Long id) {
		return portalUserExtDao.getByPk(id);
	}

	@Override
	public boolean save(PortalUserExtBean bean) {
		return portalUserExtDao.save(bean);
	}

	@Override
	public boolean update(PortalUserExtBean bean) {
		return portalUserExtDao.update(bean);
	}

}
