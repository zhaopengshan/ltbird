package com.leadtone.mas.bizplug.mms.service;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysend;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysendVO;

public interface MbnMmsReadysendIService {

	public void insert(MbnMmsReadysend mbnMms);
	public void delete(MbnMmsReadysend mbnMms);
	public void delete(Long mbnMms);
	public void update(MbnMmsReadysend mbnMms);
	public MbnMmsReadysend loadById(Long id);
	public MbnMmsReadysendVO loadVOById(Long id);
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
	public boolean batchDeleteByPks(Long[] pks);
	
	public List<MbnMmsReadysend> getByPks(String[] ids);
	public Integer batchUpdateByList(final List<MbnMmsReadysend> paramList);
}
