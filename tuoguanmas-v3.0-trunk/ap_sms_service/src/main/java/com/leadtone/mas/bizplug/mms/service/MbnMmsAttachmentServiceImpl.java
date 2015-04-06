package com.leadtone.mas.bizplug.mms.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachment;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachmentVO;

@Service("mbnMmsAttachmentIService")
@Transactional
public class MbnMmsAttachmentServiceImpl implements MbnMmsAttachmentIService {

	
	@Override
	public void insert(MbnMmsAttachment mbnMms) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(MbnMmsAttachment mbnMms) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long mbnMms) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(MbnMmsAttachment mbnMms) {
		// TODO Auto-generated method stub

	}

	@Override
	public MbnMmsAttachment loadById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MbnMmsAttachmentVO loadVOById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page page(PageUtil pageUtil) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer pageCount(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
