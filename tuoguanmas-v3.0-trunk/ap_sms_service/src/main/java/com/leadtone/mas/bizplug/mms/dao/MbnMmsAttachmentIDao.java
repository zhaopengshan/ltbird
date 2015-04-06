package com.leadtone.mas.bizplug.mms.dao;

import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachment;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachmentVO;
import com.leadtone.mas.bizplug.mms.dao.base.PageBaseIDao;

public interface MbnMmsAttachmentIDao extends PageBaseIDao {

	public void insert(MbnMmsAttachment mbnMms);
	public void delete(MbnMmsAttachment mbnMms);
	public void delete(Long mbnMms);
	public void update(MbnMmsAttachment mbnMms);
	public MbnMmsAttachment loadById(Long id);
	public MbnMmsAttachmentVO loadVOById(Long id);
}
