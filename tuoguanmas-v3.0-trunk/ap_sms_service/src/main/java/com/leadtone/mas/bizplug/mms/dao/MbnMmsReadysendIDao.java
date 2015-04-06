package com.leadtone.mas.bizplug.mms.dao;

import java.util.List;

import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysend;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsReadysendVO;
import com.leadtone.mas.bizplug.mms.dao.base.PageBaseIDao;

public interface MbnMmsReadysendIDao extends PageBaseIDao {
	public void insert(MbnMmsReadysend mbnMms);
	public void delete(MbnMmsReadysend mbnMms);
	public void delete(Long mbnMms);
	public void update(MbnMmsReadysend mbnMms);
	public MbnMmsReadysend loadById(Long id);
	public MbnMmsReadysendVO loadVOById(Long id);
	public List<MbnMmsReadysend> getByPks(String[] ids);
	public Integer batchUpdateByList(final List<MbnMmsReadysend> paramList);
}
