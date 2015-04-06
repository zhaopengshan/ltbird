package com.leadtone.mas.bizplug.mms.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachment;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsAttachmentVO;

@Repository(value="mbnMmsAttachmentIDao")
public class MbnMmsAttachmentDaoImpl extends BaseDao implements
		MbnMmsAttachmentIDao {

	protected static String NAMESPACE = "mms";
	protected static String INSERT = ".attachmentInsert";
	protected static String DELETEBYID = ".attachmentDeleteById";
	protected static String UPDATE = ".attachmentUpdate";
	protected static String PAGE = ".attachmentPage";
	protected static String PAGECOUNT = ".attachmentCount";
	protected static String LOADBYID = ".attachmentLoadById";
	protected static String LOADVOBYID = "";
	@Override
	public Page page(PageUtil pageUtil) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Integer pageCount(Map<String, Object> paraMap) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void insert(MbnMmsAttachment mbnMms) {
		this.getSqlMapClientTemplate().insert(NAMESPACE + INSERT, mbnMms);
	}

	@Override
	public void delete(MbnMmsAttachment mbnMms) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void delete(Long id) {
		this.getSqlMapClientTemplate().delete(NAMESPACE + DELETEBYID, id);
	}

	@Override
	public void update(MbnMmsAttachment mbnMms) {
		this.getSqlMapClientTemplate().update(NAMESPACE + UPDATE, mbnMms);
	}

	@Override
	public MbnMmsAttachment loadById(Long id) {
		return (MbnMmsAttachment) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + LOADBYID,id);
	}

	@Override
	public MbnMmsAttachmentVO loadVOById(Long id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
