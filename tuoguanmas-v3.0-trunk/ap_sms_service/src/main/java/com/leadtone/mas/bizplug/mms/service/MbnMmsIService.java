package com.leadtone.mas.bizplug.mms.service;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.mms.bean.MbnMms;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrameVO;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsVO;

public interface MbnMmsIService {

	public void insert(MbnMms mbnMms);
	public void delete(MbnMms mbnMms);
	public void delete(Long mbnMms);
	public void update(MbnMms mbnMms);
	public MbnMms loadById(Long id);
	public MbnMmsVO loadVOById(Long id);
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
	
	public boolean addMmsTransaction(MbnMms mms,List<MbnMmsFrameVO> frameList);
}
