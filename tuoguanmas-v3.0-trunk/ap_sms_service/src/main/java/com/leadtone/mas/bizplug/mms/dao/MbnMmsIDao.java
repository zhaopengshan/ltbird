package com.leadtone.mas.bizplug.mms.dao;

import com.leadtone.mas.bizplug.mms.bean.MbnMms;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsVO;
import com.leadtone.mas.bizplug.mms.dao.base.PageBaseIDao;

public interface MbnMmsIDao extends PageBaseIDao {

	public void insert(MbnMms mbnMms);
	public void delete(MbnMms mbnMms);
	public void delete(Long mbnMms);
	public void update(MbnMms mbnMms);
	public MbnMms loadById(Long id);
	public MbnMmsVO loadVOById(Long id);
}
