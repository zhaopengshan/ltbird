package com.leadtone.mas.bizplug.mms.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.mms.bean.MbnMms;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsVO;

@Repository(value="mbnMmsIDao")
public class MbnMmsDaoImpl extends BaseDao implements MbnMmsIDao {

	protected static String NAMESPACE = "mms";
	
	protected static String INSERT = ".mmsInsert";
	protected static String DELETEBYID = ".mmsDeleteById";
	protected static String UPDATE = ".mmsUpdate";
	protected static String PAGE = ".mmsPage";
	protected static String PAGECOUNT = ".mmsCount";
	protected static String LOADBYID = ".mmsLoadById";
	protected static String LOADVOBYID = ".mmsvoLoadById";
	
	@Override
	public Page page(PageUtil pageUtil) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Integer pageCount(Map<String, Object> paraMap) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void insert(MbnMms mbnMms) {
		this.getSqlMapClientTemplate().insert(NAMESPACE + INSERT, mbnMms);
	}

	@Override
	public void delete(MbnMms mbnMms) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void delete(Long id) {
		this.getSqlMapClientTemplate().delete(NAMESPACE + DELETEBYID, id);
	}

	@Override
	public void update(MbnMms mbnMms) {
		this.getSqlMapClientTemplate().update(NAMESPACE + UPDATE, mbnMms);
	}

	@Override
	public MbnMms loadById(Long id) {
		return (MbnMms) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + LOADBYID,id);
	}

	@Override
	public MbnMmsVO loadVOById(Long id) {
		return (MbnMmsVO) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + LOADVOBYID,id);
	}

}
