package com.leadtone.mas.bizplug.security.dao.base;

import java.util.Map;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;

public interface PageBaseIDao {


	/**
	 * 查询分页/模糊查询分页
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Page page(PageUtil pageUtil);
	/**
	 * 查询分页/模糊查询分页 count
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	public Integer pageCount(Map<String,Object> paraMap);
}
