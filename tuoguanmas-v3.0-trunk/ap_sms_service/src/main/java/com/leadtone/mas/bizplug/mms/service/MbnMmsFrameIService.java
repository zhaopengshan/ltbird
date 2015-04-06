package com.leadtone.mas.bizplug.mms.service;

import java.util.Map;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrame;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrameVO;

public interface MbnMmsFrameIService {

	public void insert(MbnMmsFrame mbnMms);
	public void delete(MbnMmsFrame mbnMms);
	public void delete(Long mbnMms);
	public void update(MbnMmsFrame mbnMms);
	public MbnMmsFrame loadById(Long id);
	public MbnMmsFrameVO loadVOById(Long id);
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
