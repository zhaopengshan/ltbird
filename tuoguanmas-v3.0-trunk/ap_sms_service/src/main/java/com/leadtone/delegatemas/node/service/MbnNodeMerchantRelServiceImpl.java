package com.leadtone.delegatemas.node.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.delegatemas.node.bean.MbnNodeMerchantRelation;
import com.leadtone.delegatemas.node.dao.MbnNodeMerchantRelDao;

@Service("mbnNodeMerchantRelService")
@Transactional
public class MbnNodeMerchantRelServiceImpl implements MbnNodeMerchantRelService {
	@Resource
	private MbnNodeMerchantRelDao mbnNodeMerchantRelDao;

	@Override
	public int delete(Long id) {
		return mbnNodeMerchantRelDao.delete(id);
	}

	@Override
	public List<MbnNodeMerchantRelation> getAll() {
		return mbnNodeMerchantRelDao.getAll();
	}

	@Override
	public MbnNodeMerchantRelation getByPk(Long id) {
		return mbnNodeMerchantRelDao.getByPk(id);
	}

	@Override
	public int insert(MbnNodeMerchantRelation rel) {
		return mbnNodeMerchantRelDao.insert(rel);
	}

	@Override
	public List<MbnNodeMerchantRelation> getByNodeId(Long nodeId) {
		return mbnNodeMerchantRelDao.getByNodeId(nodeId);
	}

	@Override
	public List<MbnNodeMerchantRelation> getByMerchantPin(Long merchantPin) {
		return mbnNodeMerchantRelDao.getByMerchantPin(merchantPin);
	}

}
