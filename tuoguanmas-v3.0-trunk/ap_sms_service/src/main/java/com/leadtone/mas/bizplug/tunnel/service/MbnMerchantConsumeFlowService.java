package com.leadtone.mas.bizplug.tunnel.service;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsumeFlow;

public interface MbnMerchantConsumeFlowService {
	Integer insert(MbnMerchantConsumeFlow param);
	/**
	 * 查询分页/模糊查询分页
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Page page(PageUtil pageUtil) throws Exception;
	
	/**
	 *  count
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Integer pageCount(PageUtil pageUtil) throws Exception;
}
