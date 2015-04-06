package com.leadtone.mas.bizplug.mms.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.mms.bean.MbnMms;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachment;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrameVO;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsVO;
import com.leadtone.mas.bizplug.mms.dao.MbnMmsAttachmentIDao;
import com.leadtone.mas.bizplug.mms.dao.MbnMmsFrameIDao;
import com.leadtone.mas.bizplug.mms.dao.MbnMmsIDao;

@Service("mbnMmsIService")
@Transactional
public class MbnMmsServiceImpl implements MbnMmsIService {

	@Resource
	private MbnMmsIDao mbnMmsIDao;
	
	@Resource
	private MbnMmsFrameIDao mbnMmsFrameIDao;
	@Resource
	private MbnMmsAttachmentIDao mbnMmsAttachmentIDao;
	
	
	public boolean addMmsTransaction(MbnMms mms,List<MbnMmsFrameVO> frameList){
		boolean result = true;
		try{
			mbnMmsIDao.insert(mms);
			Iterator<MbnMmsFrameVO> frameIterator = frameList.iterator();
			while( frameIterator.hasNext() ){
				MbnMmsFrameVO frameVO = frameIterator.next();
				mbnMmsFrameIDao.insert(frameVO);
				List<MbnMmsAttachment> attachmentList = frameVO.getMbnMmsAttachment();
				Iterator<MbnMmsAttachment> attachmentIterator = attachmentList.iterator();
				while(attachmentIterator.hasNext()){
					MbnMmsAttachment attachement = attachmentIterator.next();
					mbnMmsAttachmentIDao.insert(attachement);
				}
			}
		}catch(Exception e){
			result = false;
		}
		return result;
	}
	
	@Override
	public void insert(MbnMms mbnMms) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(MbnMms mbnMms) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long mbnMms) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(MbnMms mbnMms) {
		// TODO Auto-generated method stub

	}

	@Override
	public MbnMms loadById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MbnMmsVO loadVOById(Long id) {
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
