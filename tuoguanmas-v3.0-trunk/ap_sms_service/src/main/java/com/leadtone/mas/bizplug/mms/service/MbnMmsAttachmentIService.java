package com.leadtone.mas.bizplug.mms.service;

import java.util.Map;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachment;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachmentVO;

public interface MbnMmsAttachmentIService {

	public void insert(MbnMmsAttachment mbnMms);
	public void delete(MbnMmsAttachment mbnMms);
	public void delete(Long mbnMms);
	public void update(MbnMmsAttachment mbnMms);
	public MbnMmsAttachment loadById(Long id);
	public MbnMmsAttachmentVO loadVOById(Long id);
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
