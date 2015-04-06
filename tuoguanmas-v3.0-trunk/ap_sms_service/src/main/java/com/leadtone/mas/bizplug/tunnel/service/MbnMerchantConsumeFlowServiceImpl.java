package com.leadtone.mas.bizplug.tunnel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsumeFlow;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantConsumeFlowDao;

@Service(value = "mbnMerchantConsumeFlowService")
@Transactional
public class MbnMerchantConsumeFlowServiceImpl implements
		MbnMerchantConsumeFlowService {
	@Autowired
	private MbnMerchantConsumeFlowDao mbnMerchantConsumeFlowDao;

	@Override
	public Integer insert(MbnMerchantConsumeFlow param) {
		return mbnMerchantConsumeFlowDao.insert(param);
	}

	public Integer pageCount(PageUtil pageUtil) throws Exception{
		Integer count=0;
		try {
			count= mbnMerchantConsumeFlowDao.pageCount(pageUtil);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public Page page(PageUtil pageUtil) throws Exception{ 
		Page page=null;
		try {
			page =  mbnMerchantConsumeFlowDao.page(pageUtil); 
		} catch (Exception e) {
			throw e;
		} 
		// 
		return page;
	}
}
