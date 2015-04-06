package com.leadtone.mas.bizplug.mms.dao;

import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrame;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrameVO;
import com.leadtone.mas.bizplug.mms.dao.base.PageBaseIDao;

public interface MbnMmsFrameIDao extends PageBaseIDao {

	public void insert(MbnMmsFrame mbnMms);
	public void insert(MbnMmsFrameVO mbnMms);
	public void delete(MbnMmsFrame mbnMms);
	public void delete(Long mbnMms);
	public void update(MbnMmsFrame mbnMms);
	public MbnMmsFrame loadById(Long id);
	public MbnMmsFrameVO loadVOById(Long id);
}
