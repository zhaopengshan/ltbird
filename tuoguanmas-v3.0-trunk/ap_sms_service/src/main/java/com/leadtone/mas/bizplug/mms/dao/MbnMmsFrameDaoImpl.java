package com.leadtone.mas.bizplug.mms.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrame;
import com.leadtone.mas.bizplug.mms.bean.MbnMmsFrameVO;

@Repository(value="mbnMmsFrameIDao")
public class MbnMmsFrameDaoImpl extends BaseDao implements MbnMmsFrameIDao {

	protected static String NAMESPACE = "mms";
	protected static String INSERT = ".frameInsert";
	protected static String DELETEBYID = ".frameDeleteById";
	protected static String UPDATE = ".frameUpdate";
	protected static String PAGE = ".framePage";
	protected static String PAGECOUNT = ".frameCount";
	protected static String LOADBYID = ".frameLoadById";
	protected static String LOADVOBYID = "";
	
	@Override
	public Page page(PageUtil pageUtil) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Integer pageCount(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void insert(MbnMmsFrameVO mbnMms){
		this.getSqlMapClientTemplate().insert(NAMESPACE + INSERT, mbnMms);
	}
	@Override
	public void insert(MbnMmsFrame mbnMms) {
		this.getSqlMapClientTemplate().insert(NAMESPACE + INSERT, mbnMms);
	}

	@Override
	public void delete(MbnMmsFrame mbnMms) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void delete(Long id) {
		this.getSqlMapClientTemplate().delete(NAMESPACE + DELETEBYID, id);
	}

	@Override
	public void update(MbnMmsFrame mbnMms) {
		this.getSqlMapClientTemplate().update(NAMESPACE + UPDATE, mbnMms);
	}

	@Override
	public MbnMmsFrame loadById(Long id) {
		return (MbnMmsFrame) this.getSqlMapClientTemplate().queryForObject(NAMESPACE + LOADBYID,id);
	}

	@Override
	public MbnMmsFrameVO loadVOById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
