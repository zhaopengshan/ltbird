package com.leadtone.delegatemas.node.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.delegatemas.node.bean.MbnNode;
import com.leadtone.delegatemas.node.dao.MbnNodeDao;

@Service("mbnNodeService")
@Transactional
public class MbnNodeServiceImpl implements MbnNodeService {
	@Resource
	private MbnNodeDao mbnNodeDao;

	@Override
	public int delete(MbnNode node) {
		return mbnNodeDao.delete(node);
	}

	@Override
	public MbnNode getByPk(Long id) {
		return mbnNodeDao.getByPk(id);
	}

	@Override
	public List<MbnNode> getNodeList() {
		return mbnNodeDao.getNodeList();
	}

	@Override
	public int insert(MbnNode node) {
		return mbnNodeDao.insert(node);
	}

	@Override
	public int update(MbnNode node) {
		return mbnNodeDao.update(node);
	}

}
